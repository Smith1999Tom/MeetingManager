import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Diary {

	LinkedList<Meeting> meetings;
	Stack<Meeting> undoStack = new Stack<Meeting>();
	Charset cs = StandardCharsets.UTF_8;
	Iterator<Meeting> iterator;
	private ArrayList<Meeting> clashes;
	ArrayList<Meeting> possibleMeetings;
	Scanner sc = new Scanner(System.in);
	
	Diary()
	{
		meetings = new LinkedList<Meeting>();
		clashes = new ArrayList<Meeting>();
		possibleMeetings = new ArrayList<Meeting>();
	}
	
	boolean addEntry(Meeting addMeeting)
	{
		if(!checkIfValid(addMeeting))
		{
			Meeting undoMeeting = new Meeting(addMeeting);
			undoStack.push(undoMeeting);
			return meetings.add(addMeeting);
		}
		else
		{
			System.out.println("Meeting clashes with an existing meeting.");
			return false;
		}
		
		
		
	}
	
	Diary loadDiary(String pathString)
	{
		Path diaryFile = Paths.get(pathString);
		
		try(BufferedReader br = Files.newBufferedReader(diaryFile, cs);)
		{
			Diary bufferDiary = new Diary();
			while(br.ready())
			{
				String l1 = br.readLine();
				String l2 = br.readLine();
				String l3 = br.readLine();
				String l4 = br.readLine();
				bufferDiary.addEntry(new Meeting(l1, l2, l3, l4));
			}
			
			return bufferDiary;
		}
		catch(IOException e)
		{
			System.out.println("IOEXCEPTION " + e);
			return null;
		}
		catch(NullPointerException e)
		{
			System.out.println("NULLPOINTEREXCEPTION " + e);
			return null;
		}
	}
	
	boolean saveDiary(String pathString)
	{
		Path diaryFile = Paths.get(pathString);
		
		try(BufferedWriter bw = Files.newBufferedWriter(diaryFile, cs);)
		{
			iterator = meetings.iterator();
			while(iterator.hasNext())
			{
				Meeting saveMeeting = iterator.next();
				String l1 = saveMeeting.getDateOfMeeting().toString();
				String l2 = saveMeeting.getStartTime().toString();
				String l3 = saveMeeting.getEndTime().toString();
				String l4 = saveMeeting.getDescription();
				bw.write(l1);
				bw.newLine();
				bw.write(l2);
				bw.newLine();
				bw.write(l3);
				bw.newLine();
				bw.write(l4);
				bw.newLine();
			}
			return true;
		}
		catch(IOException e)
		{
			System.out.println("IOEXCEPTION " + e);
			return false;
		}
		catch(NullPointerException e)
		{
			System.out.println("NULLPOINTEREXCEPTION " + e);
			return false;
		}
	}
	
	//At employee menu when the user selects add or delete, enter this method along with either "Edit" or "Delete" for this method to work
	
	/**
	 * searches for a meeting the employee has on the date and time entered. Depending on if the employee wants to edit or delete the meeting the method will call an edit or delete method using the meeting that was found.
	 * @param editOrDelete given from menu to check if the user wants to edit or delete the found meeting
	 */
		public void searchWithDateTime(String editOrDelete) 
		{
			//used to find the meeting
			System.out.println("Enter date to search");
			String dateString = sc.nextLine();
			LocalDate date = LocalDate.parse(dateString);
			System.out.println("Enter start time to search");
			String timeString = sc.nextLine();
			LocalTime time = LocalTime.parse(timeString);
			
			boolean meetingFound = false;
			
			//for length of for loop
			int maxSize = meetings.size();
			
			for(int i = 0; i < maxSize; i++) 
			{
				//each for loop the search meeting is replaced with the next meeting in the list
				Meeting search = meetings.get(i);
				
				//if date and time are the same as what the user inputs
				if (search.getDateOfMeeting().equals(date) && search.getStartTime().equals(time)) 
				{
					if(editOrDelete.equals("Edit"))
					{
						//calls edit method with the meeting that was found
						editMeeting(search);
						meetingFound = true;
						return;
					}
					else if(editOrDelete.equals("Delete"))
					{
						//calls delete method with the meeting that was found
						deleteMeeting(search);
						meetingFound = true;
						return;
					}
					
						
				}
			}
			if (meetingFound == false) 
			{
				System.out.println("there is no meeting at that time on that day");
				return;
			}
		}
	
		/**
		 * asks the user what wants to be edited, user responds through a switch case, the program then asks what the new version of the meeting will be, the user responds and checks if it is valid.
		 * if the new version is valid the change will be made and the program will ask if the user wants to edit another part of the meeting, the user can continue editing or stop and exit the method
		 * if the new version is not valid there will be no change to the meeting and the method will restart at the program asking what part will be edited
		 * @param editMeeting The meeting that will be edited
		 */
		public void editMeeting(Meeting editMeeting) 
		{
			//temporary fields of each part of the unedited meeting 
			LocalDate tempDate = editMeeting.getDateOfMeeting();
			LocalTime tempStart = editMeeting.getStartTime();
			LocalTime tempEnd = editMeeting.getEndTime();
			String tempDescription = editMeeting.getDescription();
			
			//removes the unedited meeting from the list 
			meetings.remove(editMeeting);
			
			
			boolean stop = false;
			boolean editHappened;
			// new meeting that will take the place of the removed meeting
			Meeting newMeeting = null;
			//while loop that will only stop when the user wants to stop editing the meeting
			while (stop == false) 
			{
				editHappened = false;
				printMeeting(editMeeting);
				//options for the switch case
				System.out.println("What would you like to edit?");
				System.out.println("1 - Date of meeting");
				System.out.println("2 - Start time");
				System.out.println("3 - End time");
				System.out.println("4 - Description");
				//reads user input
				String partEditedString = sc.nextLine();
				int partEdited = Integer.parseInt(partEditedString);
				LocalDate formattedDateEdit;
				LocalTime formattedTimeEdit;
				String edit;
				boolean valid;
				
				//switch case to process user choices
				switch(partEdited) 
				{
						//changing the date
				case 1:	System.out.println("Enter new meeting date:");			
						edit = sc.nextLine();
						formattedDateEdit = LocalDate.parse(edit);
						//replacement meeting is given the new edited part along with the other temporary old parts
						newMeeting = new Meeting(formattedDateEdit, tempStart, tempEnd, tempDescription);
						//checks if this new meeting is valid
						valid = checkIfValid(newMeeting);
						if (valid == false) 
						{
							//adds the new meeting to the list
							meetings.add(newMeeting);
							//allows the user to exit
							editHappened = true;
							break;
						}
						else 
						{
							//adds the old meeting back to the list
							newMeeting = new Meeting(tempDate, tempStart, tempEnd, tempDescription);
							System.out.println("the new meeting clashes with another one");
						}
						
						
						//changing the start time
				case 2: System.out.println("Enter new start time:");			
						edit = sc.nextLine();
						
						formattedTimeEdit = LocalTime.parse(edit);
						//replacement meeting is given the new edited part along with the other temporary old parts
						newMeeting = new Meeting(tempDate, formattedTimeEdit, tempEnd, tempDescription);
						//checks if this new meeting is valid
						valid = checkIfValid(editMeeting);
						if (valid == false) 
						{
							//adds the new meeting to the list
							meetings.add(newMeeting);
							//allows the user to exit
							editHappened = true;
							break;
						}
						else 
						{
							//adds the old meeting back to the list
							newMeeting = new Meeting(tempDate, tempStart, tempEnd, tempDescription);
							System.out.println("the new meeting cashes with another one");
						}
					
						//changing the end time
				case 3: System.out.println("Enter new end time:");			
						edit = sc.nextLine();
						
						formattedTimeEdit = LocalTime.parse(edit);					
						editMeeting.setStartTime(formattedTimeEdit);
						//replacement meeting is given the new edited part along with the other temporary old parts
						newMeeting = new Meeting(tempDate, tempStart, formattedTimeEdit, tempDescription);
						//checks if this new meeting is valid
						valid = checkIfValid(editMeeting);
						if (valid == false) 
						{
							//adds the new meeting to the list
							meetings.add(newMeeting);
							//allows the user to exit
							editHappened = true;
							break;
						}
						else 
						{
							//adds the old meeting back to the list
							newMeeting = new Meeting(tempDate, tempStart, tempEnd, tempDescription);
							System.out.println("the new meeting cashes with another one");
						}
					
						//changing the description
				case 4: System.out.println("Enter new description:");			
						edit = sc.nextLine();
						//replacement meeting is given the new edited part along with the other temporary old parts
						newMeeting = new Meeting(tempDate, tempStart, tempEnd, edit);	
						//adds the new meeting to the list
						meetings.add(newMeeting);
						//allows the user to exit
						editHappened = true;
						break;
					
						//option if 1-4 is not selected
				default:System.out.println("invalid selection:");
						break;
				}
				
				//if an edit is made
				if (editHappened == true) 
				{
					
					System.out.println("Would you like to edit another part of this meeting?");
					System.out.println("Enter Y to edit another part");
					String Y = sc.nextLine();
					//if the user does not enter "Y" or "y"
					if (Y.compareTo("Y") !=0 || Y.compareTo("y") != 0) 
					{
						//stops the while loop
						stop = true;
					}
					
				}
			}
			//adds this to the stack of things that happened to the diary
			editMeeting.setUndoReference(newMeeting);
			undoStack.push(editMeeting);
			
			
		}
		
		/**
		 * if there is a meeting to delete it will be removed from the diary
		 * @param deletedMeeting the meeting that will be deleted
		 */
		public void deleteMeeting(Meeting deletedMeeting) 
		{
			
			if(meetings != null) 
			{
				
				if(meetings.remove(deletedMeeting))
				{
					deletedMeeting.setUndoReference(null);
					undoStack.push(deletedMeeting);
				}
				
			}
			else 
			{
				System.out.println("there are no meetings to delete");			
			}
		}
		
		public boolean checkIfValid(Meeting meeting)
		{
			LocalTime start = meeting.getStartTime();
			LocalTime end = meeting.getEndTime();
			LocalDate day = meeting.getDateOfMeeting();
			ArrayList<Meeting> hold = new ArrayList<Meeting>();
			int clash = 0;
			if(meetings.isEmpty())
			{
				return false;
			}
			else
			{
				for(int x = 0; x<meetings.size();x++)
				{
					meeting = meetings.pop();
					LocalTime timeS = meeting.getStartTime();
					LocalTime timeE = meeting.getEndTime();
					LocalDate date = meeting.getDateOfMeeting();
					if(date.isEqual(day))
					{
						if(timeS.equals(start) || timeS.equals(end))
						{
							hold.add(meeting);
							clashes.add(meeting);
							clash++;
						}
						else if(timeE.equals(start) || timeE.equals(end))
						{
							hold.add(meeting);
							clashes.add(meeting);
							clash++;
						}
						else
						{
							if(timeS.isAfter(start)  && timeS.isBefore(end) || timeE.isAfter(start)  && timeE.isBefore(end))
							{
								hold.add(meeting);
								clashes.add(meeting);
								clash++;
							}
							
							else
							{
								hold.add(meeting);
							}
						}
					}
					else
					{
						hold.add(meeting);
					}
					}
				}
				meetings.addAll(hold);
				hold.removeAll(hold);
				if(clash != 0)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
				
		
	public void print()
	{
		iterator = meetings.iterator();
		while(iterator.hasNext())
		{
			Meeting entryToPrint = iterator.next();
			System.out.println(entryToPrint.getDateOfMeeting());
			System.out.println(entryToPrint.getStartTime());
			System.out.println(entryToPrint.getEndTime());
			System.out.println(entryToPrint.getDescription());
		}
		System.out.println();
	}
	
	public void printDiary(Employee diary)
	{
		Diary diary2 = diary.getEmployeeDiary();
		System.out.println("Diary of ID: " + diary.getEmployeeID());
		System.out.println(" ");
		meetings = diary2.getMeetings();
		print();
		
}
	
	public LinkedList<Meeting> getMeetings() {
		return meetings;
	}

	public void sort()
	{
		Meeting temp;
		int n = meetings.size();
		for (int i = 0; i < n; i++) {
	        for (int j = 1; j < (n - i); j++) {
	            if (meetings.get(j-1).compareTo(meetings.get(j)) == 1) {
	                temp = meetings.get(j-1);
	                meetings.set(j - 1, meetings.get(j));
	                meetings.set(j, temp);
	            }
	        }
	    }	
	}
	
	public void printMeeting(Meeting meeting)
	{
		LocalDate date = meeting.getDateOfMeeting();
		LocalTime sTime = meeting.getStartTime();
		LocalTime eTime = meeting.getEndTime();
		String desc = meeting.getDescription();
		System.out.println("Date: " + date + "\nStarts at: " + sTime + "\nEnds at: " + eTime + "\nDescription: " + desc);
		System.out.println(" ");
		
	}
	
	public void undo()
	{
		if(!undoStack.isEmpty())
		{
			Meeting undoMeeting = undoStack.pop();
			if(undoMeeting.getUndoReference() == null)	//Meeting reference doesn't exist, so meeting was removed
			{
				meetings.add(undoMeeting);
			}
			else if(undoMeeting.compareTo(undoMeeting.getUndoReference()) == 0)	//Meetings are same, so meeting was added
			{
				meetings.remove(undoMeeting.getUndoReference());
			}
			else	//Meeting was edited
			{
				meetings.remove(undoMeeting.getUndoReference());
				meetings.add(undoMeeting);
			}
			
		}
		else
			System.out.println("Cannot undo any further.");
		
		
		
		
	}

	public ArrayList<Meeting> checkIfValidForMultiple(Employee held,ArrayList<Meeting> theMeetingsList)
	{
		ArrayList<Meeting> hold = new ArrayList<Meeting>();
		for(int x = 0; x <theMeetingsList.size(); x++)
		{
			int clash = 0;
			Meeting test =	theMeetingsList.get(x);
			LocalTime start = test.getStartTime();
			LocalTime end =test.getEndTime();
			LocalDate day = test.getDateOfMeeting();
			if(meetings.isEmpty())
			{
			
			}
			else
			{
				for(int x1 = 0; x1<meetings.size();x1++)
				{
					Meeting meeting2 = meetings.pop();
					LocalTime timeS = meeting2.getStartTime();
					LocalTime timeE = meeting2.getEndTime();
				    LocalDate date = meeting2.getDateOfMeeting();
					if(date.isEqual(day))
					{
						if(timeS.equals(start) || timeS.equals(end))
						{
							clash++;
							hold.add(meeting2);
						}
						else if(timeE.equals(start) || timeE.equals(end))
						{
							clash++;
							hold.add(meeting2);
						}
						else
						{
							if(timeS.isAfter(start)  && timeS.isBefore(end) || timeE.isAfter(start)  && timeE.isBefore(end))
							{
								clash++;
								hold.add(meeting2);
							}
							
							else
							{
								hold.add(meeting2);
							}
						}
					}
					else
					{
						hold.add(meeting2);
					}
				}
				}
		
				meetings.addAll(hold);
				hold.removeAll(hold);
				if(clash != 0)
				{
					theMeetingsList.remove(x);
				}
				else
				{
					
				}
			}
			return theMeetingsList;
}

}

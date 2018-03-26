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

/**
 * Represents an employees diary. Contains all meetings an employee has. 
 * @author Ryan Crampton, Christian Hegarty, Tom Smith
 */
public class Diary {

	LinkedList<Meeting> meetings;
	Stack<Meeting> undoStack = new Stack<Meeting>();
	Charset cs = StandardCharsets.UTF_8;
	Iterator<Meeting> iterator;
	private ArrayList<Meeting> clashes;
	ArrayList<Meeting> possibleMeetings;
	Scanner sc = new Scanner(System.in);
	/**
	 * Constructor
	 */
	Diary()
	{
		meetings = new LinkedList<Meeting>();
		clashes = new ArrayList<Meeting>();
		possibleMeetings = new ArrayList<Meeting>();
	}
	/**
	 * This adds an entry to the meetings list
	 */	
	boolean addEntry(Meeting addMeeting)
	{
		if(!checkIfValid(addMeeting))
		{
			Meeting undoMeeting = new Meeting(addMeeting);
			boolean validDetails = false;
			validDetails = meetings.add(addMeeting);
			if(validDetails)
				undoStack.push(undoMeeting);
			return validDetails;
		}
		else
		{
			System.out.println("Meeting clashes with an existing meeting.");
			return false;
		}
	}
	/**
	 * This allows the user to load the diary from a file
	 * 
	 * @param diaryFile 	This detects the appropriate name from the user and loads the diary
	 * @param br			This reads the file on a buffer, and checks each line 
	 * @param bufferDiary	This recreates the info gained in br to make a new diary, adding the information to it
	 * @param l1			This reads the appropriate line
	 * @param l2			This reads the appropriate line
	 * @param l3			This reads the appropriate line
	 * @param l4			This reads the appropriate line
	 */	
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
	/**
	 * This allows the user to save the diary to a file
	 * 
	 * @param diaryFile 	This detects the appropriate name from the user and loads the diary
	 * @param bw			This writes the file on a buffer, and checks each line 
	 * @param bufferDiary	This recreates the info gained in bw to make a new file, adding the information to it
	 * @param iterator		This helps to determine the length, and checks if more are to be added
	 * @param l1			This reads the appropriate line
	 * @param l2			This reads the appropriate line
	 * @param l3			This reads the appropriate line
	 * @param l4			This reads the appropriate line
	 */
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
	/**
	 * This allows the user to search for the undo, edit or delete work with this
	 * @param dateString		This detects the user input
	 * @param sc				This is the scanner, detects input
	 * @param date 				This parses the info gained in date string to a LocalDate Input
	 * @param timeString		This parses info to time, String type
	 * @param time				This parses information gained in the previous string to LocalTime format
	 * @param maxSize			This checks the size of the meetings located in the linked list, returns an int
	 * @param search			This looks for the meeting and holds the meeting that it is appropriate to
	 * @param meetings			This is the LinkedList that holds all the meetings for the user
	 * @param editOrDelete		This holds the info from the inputted info the user got earlier
	 * 
	 */
	//At employee menu when the user selects add or delete, enter this method along with either "Edit" or "Delete" for this method to work
		public void searchWithDateTime(String editOrDelete) 
		{
			
			try
			{
				System.out.println("Enter date to search");
				String dateString = sc.nextLine();
				LocalDate date = LocalDate.parse(dateString);
				System.out.println("Enter start time to search");
				String timeString = sc.nextLine();
				LocalTime time = LocalTime.parse(timeString);
				
				int maxSize = meetings.size();
				
				for(int i = 0; i < maxSize; i++) 
				{
					Meeting search = meetings.get(i);
					if (search.getDateOfMeeting().equals(date) && search.getStartTime().equals(time)) 
					{
						if(editOrDelete.equals("Edit"))
						{
							editMeeting(search);
							return;
						}
						else if(editOrDelete.equals("Delete"))
						{
							deleteMeeting(search);
							return;
						}
							
					}
				}
			}
			catch(java.time.format.DateTimeParseException e)
			{
				System.out.println("Invalid data entered - " + e);
			}
			
			
		}
	
		/**
		 * This allows the user to search for the undo, edit or delete work with this
		 * 
		 * @param tempDate				This holds the current date of the meeting
		 * @param tempStart				This holds the current start time of the meeting 
		 * @param tempEnd 				This hold the current end time of the meeting
		 * @param tempDescription		This holds the current description for the meeting
		 * @param meetings				This is the LinkedList that holds all the meetings for the user
		 * @param stop					This is done at the end when the user confirms they have finished all edits
		 * @param editHappened			This determines if an edit has happened to the meeting, and confirms to the user it has
		 * @param newMeeting			This puts back in the edited node
		 * @param partEdited			This determines which part needs editing, depending on user input
		 * @param partEditedString		This is where the user inputs the new info
		 * @param formattedDateEdit		This edits the date to the appropriate format
		 * @param edit					This edits the node
		 * @param valid					This confirms if the edits are valid or not 
		 * @param Y						This checks if the user wants to keep editing, if Y or y is input, they do
		 */
		public void editMeeting(Meeting editMeeting) 
		{
			
			try
			{
				LocalDate tempDate = editMeeting.getDateOfMeeting();
				LocalTime tempStart = editMeeting.getStartTime();
				LocalTime tempEnd = editMeeting.getEndTime();
				String tempDescription = editMeeting.getDescription();
				
				
				meetings.remove(editMeeting);
				
				boolean stop = false;
				boolean editHappened;
				Meeting newMeeting = null;
				while (stop == false) 
				{
					
					editHappened = false;
					printMeeting(editMeeting);
					System.out.println("What would you like to edit?");
					System.out.println("1 - Date of meeting");
					System.out.println("2 - Start time");
					System.out.println("3 - End time");
					System.out.println("4 - Description");
					String partEditedString = sc.nextLine();
					int partEdited = Integer.parseInt(partEditedString);
					LocalDate formattedDateEdit;
					LocalTime formattedTimeEdit;
					String edit;
					boolean valid;
					
					switch(partEdited) 
					{
					case 1:	System.out.println("Enter new meeting date:");			
							edit = sc.nextLine();
							formattedDateEdit = LocalDate.parse(edit);
							newMeeting = new Meeting(formattedDateEdit, tempStart, tempEnd, tempDescription);
							valid = checkIfValid(newMeeting);
							if (valid == false) 
							{
								meetings.add(newMeeting);
								editHappened = true;
								break;
							}
							else 
							{
								newMeeting = new Meeting(tempDate, tempStart, tempEnd, tempDescription);
								System.out.println("the new meeting clashes with another one");
							}
							
							
						
					case 2: System.out.println("Enter new start time:");			
							edit = sc.nextLine();
							
							formattedTimeEdit = LocalTime.parse(edit);
							newMeeting = new Meeting(tempDate, formattedTimeEdit, tempEnd, tempDescription);
							valid = checkIfValid(editMeeting);
							if (valid == false) 
							{
								meetings.add(newMeeting);
								editHappened = true;
								break;
							}
							else 
							{
								newMeeting = new Meeting(tempDate, tempStart, tempEnd, tempDescription);
								System.out.println("the new meeting cashes with another one");
							}
						
					case 3: System.out.println("Enter new end time:");			
							edit = sc.nextLine();
							
							formattedTimeEdit = LocalTime.parse(edit);					
							editMeeting.setStartTime(formattedTimeEdit);
							newMeeting = new Meeting(tempDate, tempStart, formattedTimeEdit, tempDescription);
							valid = checkIfValid(editMeeting);
							if (valid == false) 
							{
								meetings.add(newMeeting);
								editHappened = true;
								break;
							}
							else 
							{
								newMeeting = new Meeting(tempDate, tempStart, tempEnd, tempDescription);
								System.out.println("the new meeting cashes with another one");
							}
						
					case 4: System.out.println("Enter new description:");			
							edit = sc.nextLine();
							newMeeting = new Meeting(tempDate, tempStart, tempEnd, edit);		
							meetings.add(newMeeting);
							editHappened = true;
							break;
						
					default:System.out.println("invalid selection:");
							break;
					}
					
					if (editHappened == true) 
					{
						
						System.out.println("Would you like to edit another part of this meeting?");
						System.out.println("Enter Y to edit another part");
						String Y = sc.nextLine();
						if(Y.equalsIgnoreCase("y"))
							editMeeting(newMeeting);
							
						stop = true;
						
					}
				}
				editMeeting.setUndoReference(newMeeting);
				undoStack.push(editMeeting);
			}
			catch(java.time.format.DateTimeParseException e)
			{
				System.out.println("Invalid data entered - " + e);
			}
			
		
			
			
			
		}
		/**
		 * This deletes a meeting from the List
		 * @param meetings			This is the LinkedList that holds all the users meetings
		 * @param deletedMeeting	This allows the meeting to be deleted, passed on from the previous user inputs
		 * @param undoStack			Holds the meeting incase the user wants to undo
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
		
		/**
		 * This checks if it is possible for the meeting to be added, returns true or false
		 * @param start 		This determines the nodes start time
		 * @param end 			This determines the nodes end time
		 * @param day			This determines the date of the meeting
		 * @param clash			This determines the amount of clashes in the data, if one is found it cannot add it
		 * @param meetings		This is the LinkedList that holds all the meetings for the appropriate user 
		 * @param x				This is the iterator for the for loop
		 * @param timeS			This is from the node in the meetings, compares start time
		 * @param timeE			This is from the node in the meetings, compares end time
		 * @param date 			This is from the node that holds meeting info. this is the date of it
		 * @param hold			This acts as a means to save the meeting while its being checked
		 * @param clashes 		This acts as a mechanism if a meeting already exists, this will refuse to add it if its there
		 * @return true or false
		 */	
		public boolean checkIfValid(Meeting meeting)
		{
			
			try
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
			catch(NullPointerException e)
			{
				return true;
			}
			
			
			}
				
	/**
	 * This prints the meetings to the user
	 * @param entryToPrint 		This is the node it is printing to the user, gathers all info
	 * @param iterator			This allows the nodes to check if there is more, and gather that info
	 */		
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
	/**
	 * This adds formatting to the print
	 * @param diary2	This gets the info from the diary of the selected user 
	 * @param diary		This gets the info from the scanned input to the method, this is selected by the user
	 * @param meetings 	This gets the info of the LinkedList, it holds all the info on the meetings
	 */	
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
	/**
	 * This sorts the meetings into order of the appropriate formatting
	 */	
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
	/**
	 * This prints out and individual meeting
	 * @param date 		This gets the date of the meeting, LocalDate type
	 * @param sTime 	This gets the start time of the meeting, LocalTime type
	 * @param eTime		This gets the end time of the meeting, LocalTime type
	 * @param desc		This gets the description of the meeting, String type
	 */	
	public void printMeeting(Meeting meeting)
	{
		LocalDate date = meeting.getDateOfMeeting();
		LocalTime sTime = meeting.getStartTime();
		LocalTime eTime = meeting.getEndTime();
		String desc = meeting.getDescription();
		System.out.println("Date: " + date + "\nStarts at: " + sTime + "\nEnds at: " + eTime + "\nDescription: " + desc);
		System.out.println(" ");
		
	}
	/**
	 * This allows the system to decide what to undo, depending on the input
	 * @param undoStack		This is used to determine what stack needs undoing
	 * @param undoMeeting 	This is used to determine the meetings origin, and what happened to it
	 */	
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
	/**
	 * This checks if it is possible for the meeting to be added, returns true or false
	 * @param start 			This determines the nodes start time
	 * @param end 				This determines the nodes end time
	 * @param day				This determines the date of the meeting
	 * @param meeting			This gets the meeting from the list of arrays, gets all the info for them
	 * @param clash				This determines the amount of clashes in the data, if one is found it cannot add it
	 * @param meetings			This is the LinkedList that holds all the meetings for the appropriate user 
	 * @param x					This is the iterator for the for loop
	 * @param x1				This is the iterator for the for loop
	 * @param timeS				This is from the node in the meetings, compares start time
	 * @param timeE				This is from the node in the meetings, compares end time
	 * @param date 				This is from the node that holds meeting info. this is the date of it
	 * @param hold				This acts as a means to save the meeting while its being checked
	 * @param theMeetingList	This holds all the info on the possible meetings, and holds them in an ArrayList
	 * @return ArrayList of all possible meetings
	 */	
	public ArrayList<Meeting> checkIfValidForMultiple(Employee held,ArrayList<Meeting> theMeetingsList)
	{
		ArrayList<Meeting> hold = new ArrayList<Meeting>();
		for(int x = 0; x <theMeetingsList.size(); x++)
		{
			int clash = 0;
			Meeting meeting =	theMeetingsList.get(x);
			LocalTime start = meeting.getStartTime();
			LocalTime end = meeting.getEndTime();
			LocalDate day = meeting.getDateOfMeeting();
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

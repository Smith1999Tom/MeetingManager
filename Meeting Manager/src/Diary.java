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
	Stack<Meeting> undoStack;
	Charset cs = StandardCharsets.UTF_8;
	Iterator<Meeting> iterator;
	private ArrayList<Meeting> clashes;
	ArrayList<Meeting> possibleMeetings;
	Scanner sc = new Scanner(System.in);
	
	Diary()
	{
		meetings = new LinkedList<Meeting>();
	}
	
	boolean addEntry(Meeting addMeeting)
	{
		return meetings.add(addMeeting);
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
		public void searchWithDateTime(String editOrDelete) 
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
						editMeeting(search);
					else if(editOrDelete.equals("Delete"))
						deleteMeeting(search);
				}
			}
		}
	
		
		public void editMeeting(Meeting editMeeting) 
		{
		
			LocalDate tempDate = editMeeting.getDateOfMeeting();
			LocalTime tempStart = editMeeting.getStartTime();
			LocalTime tempEnd = editMeeting.getEndTime();
			String tempDescription = editMeeting.getDescription();
			
			meetings.remove(editMeeting);
			
			
			boolean stop = false;
			boolean editHappened;
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
				Meeting newMeeting;
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
					if (Y != "Y" || Y != "y") 
					{
						stop = true;
					}
					
				}
			}
		}
		
		public void deleteMeeting(Meeting deletedMeeting) 
		{
			
			if(meetings != null) 
			{
				meetings.remove(deletedMeeting);
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
	
	
}

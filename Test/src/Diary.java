import java.time.LocalDate;
import java.util.Scanner;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class Diary {

	private LinkedList<Meeting> meetings;
	private Stack<Meeting> undoStack;
	private ArrayList<Meeting> clashes;
	ArrayList<Meeting> possibleMeetings;
	Scanner sc = new Scanner(System.in);
	
	public LinkedList<Meeting> getMeetings() 
	{
		return meetings;
	}

	public ArrayList<Meeting> getClashes() 
	{
		return clashes;
	}

	Diary()
	{
		 meetings = new LinkedList<Meeting>();
		 clashes = new ArrayList<Meeting>();
		 possibleMeetings = new ArrayList<Meeting>();
	}
	Boolean isEmpty()
	{
		return meetings.isEmpty();
	}
	boolean addEntry(Meeting addMeeting)
	{
		return meetings.add(addMeeting);
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
			switch(partEdited) 
			{
			case 1:	System.out.println("Enter new meeting date:");			
					edit = sc.nextLine();
					LocalDate tempDate = editMeeting.getDateOfMeeting();
					formattedDateEdit = LocalDate.parse(edit);
					editMeeting.setDateOfMeeting(formattedDateEdit);
					valid = checkIfValid(editMeeting);
					if (valid == true) 
					{
						editHappened = true;
						break;
					}
					else 
					{
						editMeeting.setDateOfMeeting(tempDate);
						System.out.println("the new meeting cashes with another one");
					}
					
					
				
			case 2: System.out.println("Enter new start time:");			
					edit = sc.nextLine();
					LocalTime tempStartTime = editMeeting.getStartTime();
					formattedTimeEdit = LocalTime.parse(edit);
					editMeeting.setStartTime(formattedTimeEdit);
					valid = checkIfValid(editMeeting);
					if (valid == true) 
					{
						editHappened = true;
						break;
					}
					else 
					{
						editMeeting.setStartTime(tempStartTime);
						System.out.println("the new meeting cashes with another one");
					}
				
			case 3: System.out.println("Enter new end time:");			
					edit = sc.nextLine();
					LocalTime tempEndTime = editMeeting.getEndTime();
					formattedTimeEdit = LocalTime.parse(edit);					
					editMeeting.setStartTime(formattedTimeEdit);
					valid = checkIfValid(editMeeting);
					if (valid == true) 
					{
						editHappened = true;
						break;
					}
					else 
					{
						editMeeting.setStartTime(tempEndTime);
						System.out.println("the new meeting cashes with another one");
					}
				
			case 4: System.out.println("Enter new description:");			
					edit = sc.nextLine();
					editMeeting.setDescription(edit);					
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
				
		
	
	public void printDiary(Employee diary)
	{
		Diary diary2 = diary.getEmployeeDiary();
		System.out.println("Diary of ID: " + diary.getEmployeeID());
		System.out.println(" ");
		meetings = diary2.getMeetings();
		print();
		
	}
	public void print()
	{
		Iterator<Meeting> iterator = meetings.iterator();
		while(iterator.hasNext())
		{
			Meeting entryToPrint = iterator.next();
			printMeeting(entryToPrint);
		}
		System.out.println();
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

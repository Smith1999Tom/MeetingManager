import java.time.LocalDate;
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
	
	public LinkedList<Meeting> getMeetings() {
		return meetings;
	}

	public ArrayList<Meeting> getClashes() {
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

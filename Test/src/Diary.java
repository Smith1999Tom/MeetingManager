import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Diary {

	private LinkedList<Meeting> meetings;
	private Stack<Meeting> undoStack;
	private ArrayList<Meeting> clashes;
	
	public LinkedList<Meeting> getMeetings() {
		return meetings;
	}

	public ArrayList<Meeting> getClashes() {
		return clashes;
	}

	Diary()
	{
		 meetings = new LinkedList<Meeting>();
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
			
			if(timeS.isAfter(start) && timeS.isBefore(end) && date.equals(day)|| timeE.isAfter(start) && timeE.isBefore(end) && date.equals(day))
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
	}
	public void printDiary(Employee diary)
	{
		Diary diary2;
		diary2 = diary.getEmployeeDiary();
		LinkedList<Meeting> test = diary2.getMeetings();
		test.pop();
		
		
		
	}
	
}
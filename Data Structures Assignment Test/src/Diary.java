import java.util.LinkedList;
import java.util.Stack;

public class Diary {

	LinkedList<Meeting> meetings;
	Stack<Meeting> undoStack;
	
	Diary()
	{
		meetings = new LinkedList<Meeting>();
	}
	
	boolean addEntry(Meeting addMeeting)
	{
		return meetings.add(addMeeting);
	}
	
	
}

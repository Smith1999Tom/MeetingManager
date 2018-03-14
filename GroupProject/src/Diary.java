import java.util.TreeSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;

public class Diary {

	TreeSet<Meeting> diaryTree;
	Iterator<Meeting> iterator;
	
	Diary()
	{
		diaryTree = new TreeSet<Meeting>();
	}
	
	
	Boolean isEmpty()
	{
		return diaryTree.isEmpty();
	}
	
	Meeting findMeeting(LocalTime startTime, LocalDate dateOfMeeting, LocalTime endTime, String description)
	{
		Meeting findMeeting = new Meeting(dateOfMeeting, startTime, endTime,description);
		iterator = diaryTree.iterator();
		while(iterator.hasNext())
		{
			findMeeting = iterator.next();
			if(findMeeting.getStartTime() == startTime && findMeeting.getEndTime() == endTime && findMeeting.getDateOfMeeting() == dateOfMeeting && findMeeting.getDescription() == description)
			{
				return findMeeting;
			}
		}
		return null;
	}
	
	void addMeeting(Meeting addMeeting)
	{
		diaryTree.add(addMeeting);
	}
	
	
}
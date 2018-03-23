import java.time.*;

public class Meeting implements Comparable<Meeting>{

	LocalDate dateOfMeeting;
	LocalTime startTime;
	LocalTime endTime;
	String description;
	Meeting undoReference;
	
	
	public Meeting getUndoReference() {
		return undoReference;
	}

	public void setUndoReference(Meeting undoReference) {
		this.undoReference = undoReference;
	}

	Meeting(Meeting copyMeeting)
	{
		dateOfMeeting = copyMeeting.getDateOfMeeting();
		startTime = copyMeeting.getStartTime();
		endTime = copyMeeting.getEndTime();
		description = copyMeeting.getDescription();
		undoReference = copyMeeting;
	}
	
	
	Meeting(LocalDate date, LocalTime start, LocalTime end, String desc)
	{
		dateOfMeeting = date;
		startTime = start;
		endTime = end;
		description = desc;
		undoReference = this;
	}
	
	Meeting(String date, String start, String end, String desc)
	{
		try
		{
			dateOfMeeting = LocalDate.parse(date);
			startTime = LocalTime.parse(start);
			endTime = LocalTime.parse(end);
			description = desc;
			undoReference = this;
		}
		catch(java.time.format.DateTimeParseException e)
		{
			System.out.println("Invalid data entered - " + e);
		}
		
		
	}
	
	public LocalDate getDateOfMeeting() {
		return dateOfMeeting;
	}
	public void setDateOfMeeting(LocalDate dateOfMeeting) {
		this.dateOfMeeting = dateOfMeeting;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int compareTo(Meeting compareMeeting)
	{
		if(this.dateOfMeeting.isBefore(compareMeeting.getDateOfMeeting()))
			return -1;
		else if(this.dateOfMeeting.isAfter(compareMeeting.getDateOfMeeting()))
			return 1;
		else
			if(this.startTime.isBefore(compareMeeting.getStartTime()))
			{
				return -1;
			}
			else if(this.startTime.isAfter(compareMeeting.getStartTime()))
			{
				return 1;
			}
			else return 0;
	}
	
	
}

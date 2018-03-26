
import java.time.*;

public class Meeting implements Comparable<Meeting>{

	LocalDate dateOfMeeting;
	LocalTime startTime;
	LocalTime endTime;
	String description;
	Meeting undoReference;
	
	
	/**
	 * Gets the reference to the appropriate meeting in the undoStack
	 * @return Reference to a meeting in undoStack
	 */
	public Meeting getUndoReference() {
		return undoReference;
	}

	/** Sets the reference to the appropriate meeting in the undoStack
	 * @param undoReference Reference to a meeting in undoStack
	 */
	public void setUndoReference(Meeting undoReference) {
		this.undoReference = undoReference;
	}

	/**Creates a copy of a meeting
	 * @param copyMeeting Meeting to copy
	 */
	Meeting(Meeting copyMeeting)
	{
		dateOfMeeting = copyMeeting.getDateOfMeeting();
		startTime = copyMeeting.getStartTime();
		endTime = copyMeeting.getEndTime();
		description = copyMeeting.getDescription();
		undoReference = copyMeeting;
	}
	
	
	/**Creates a new meeting from dates given
	 * @param date Date of meeting
	 * @param start Start time of meeting
	 * @param end End time of meeting
	 * @param desc Description of meeting
	 */
	Meeting(LocalDate date, LocalTime start, LocalTime end, String desc)
	{
		dateOfMeeting = date;
		startTime = start;
		endTime = end;
		description = desc;
		undoReference = this;
	}
	
	/**Creates a new meeting from strings given
	 * @param date Date of meeting
	 * @param start Start time of meeting
	 * @param end End time of meeting
	 * @param desc Description of meeting
	 */
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
	
	/**Get the date of a meeting
	 * @return	Date of meeting
	 */
	public LocalDate getDateOfMeeting() {
		return dateOfMeeting;
	}
	/** Set the date of a meeting
	 * @param dateOfMeeting Date of meeting
	 */
	public void setDateOfMeeting(LocalDate dateOfMeeting) {
		this.dateOfMeeting = dateOfMeeting;
	}
	/**Gets the start time of a meeting
	 * @return The start time of a meeting
	 */
	public LocalTime getStartTime() {
		return startTime;
	}
	/**Sets start time of a meeting
	 * @param startTime Start time of a meeting
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	/**Gets the end time of a meeting
	 * @return End time of a meeting
	 */
	public LocalTime getEndTime() {
		return endTime;
	}
	/**Sets the end time of a meeting
	 * @param endTime End time of a meeting
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	/**Gets the description of a meeting
	 * @return Description of a meeting
	 */
	public String getDescription() {
		return description;
	}
	/**Sets the description of a meeting
	 * @param description Description of a meeting
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * This allows the system to compare meetings, without it, find, add or delete wouldn't be able to look at other nodes
	 * @param compareMeeting	The meeting to compare to
	 * @param dateOfMeeting 	The date to compare to
	 * @param startTime			The start time to compare to
	 */	
	public int compareTo(Meeting compareMeeting)
	{
		try
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
		catch(NullPointerException e)
		{
			return 0;
		}
		
		
	}
	
	
}


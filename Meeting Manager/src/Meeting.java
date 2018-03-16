import java.time.*;

public class Meeting {

	LocalDate dateOfMeeting;
	LocalTime startTime;
	LocalTime endTime;
	String description;
	
	Meeting(LocalDate date, LocalTime start, LocalTime end, String desc)
	{
		dateOfMeeting = date;
		startTime = start;
		endTime = end;
		description = desc;
	}
	
	Meeting(String date, String start, String end, String desc)
	{
		dateOfMeeting = LocalDate.parse(date);
		startTime = LocalTime.parse(start);
		endTime = LocalTime.parse(end);
		description = desc;
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
	
}

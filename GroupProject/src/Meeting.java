import java.time.LocalDate;

import java.time.LocalTime;

public class Meeting {
private LocalDate dateOfMeeting;
private LocalTime startTime;
private LocalTime endTime;
private String description;

public Meeting(LocalDate dateOfMeeting, LocalTime startTime, LocalTime endTime, String description)
{
	this.dateOfMeeting = dateOfMeeting;
	this.description = description;
	this.startTime = startTime;
	this.endTime = endTime;
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
public int compareTo(Meeting arg0) {
	// TODO Auto-generated method stub
	if(arg0.getStartTime().isBefore(this.startTime))
		return -1;
	else if(arg0.getStartTime().isAfter(this.startTime))
		return 1;
	else
return 0;
}
}

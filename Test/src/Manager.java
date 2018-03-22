import java.util.TreeSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Manager {

	TreeSet<Employee> employeeTree;
	Iterator<Employee> iterator;
	Long timeAddMin;
	Long timeAddHrs;
	Integer i = (int) (long) 1;
	LocalDate sDate;
	LocalDate eDate;
	
	public LocalDate getsDate() {
		return sDate;
	}


	public void setsDate(LocalDate sDate) {
		this.sDate = sDate;
	}


	public LocalDate geteDate() {
		return eDate;
	}


	public void seteDate(LocalDate eDate) {
		this.eDate = eDate;
	}
	
	public Long getTimeAddMin() {
		return timeAddMin;
	}


	public void setTimeAddMin(Long timeAddMin) {
		this.timeAddMin = timeAddMin;
	}


	public Long getTimeAddHrs() {
		return timeAddHrs;
	}


	public void setTimeAddHrs(Long timeAddHrs) {
		this.timeAddHrs = timeAddHrs;
	}

	Manager()
	{
		employeeTree = new TreeSet<Employee>();
	}
	
	
	Boolean isEmpty()
	{
		return employeeTree.isEmpty();
	}
	
	Employee findEmployee(int employeeID)
	{
		Employee findEmployee = new Employee(employeeID);
		iterator = employeeTree.iterator();
		while(iterator.hasNext())
		{
			findEmployee = iterator.next();
			if(findEmployee.getEmployeeID() == employeeID)
			{
				return findEmployee;
			}
		}
		return null;
	}
	
	void addEmployee(Employee addEmployee)
	{
		employeeTree.add(addEmployee);
	}
	public void addAMeeting() 
	{
		boolean validID = false;
		int ID =0;
		initSomeEmployees();
		Scanner s = new Scanner(System.in);
		String meeting;
		System.out.println("Please input the Date of the meeting (YYYY-MM-DD)");
		meeting = s.nextLine();
		LocalDate date = LocalDate.parse(meeting);	
		System.out.println("Please input the Start time of the meeting(HH:MM:SS)");
		meeting = s.nextLine();
		LocalTime sTime = LocalTime.parse(meeting);
		System.out.println("Please input the End time of the meeting,(HH:MM:SS)");
		meeting = s.nextLine();
		LocalTime eTime = LocalTime.parse(meeting);
		System.out.println("Please input the description of the meeting");
		meeting = s.nextLine();
		String desc = meeting;
		Meeting groupMeeting = new Meeting(date, sTime, eTime, desc);
		Diary diary = new Diary();
		diary.printMeeting(groupMeeting);
		do
		{
			System.out.println("Please input an employee ID");
			ID = s.nextInt();
			if(findEmployee(ID) == null)
			{
				System.out.println("This wasnt found, please try again");
			}
			else
			{
				validID = true;
			}
		}
		while(validID == false);
		Employee employeeToAddTo = null;
		employeeToAddTo = findEmployee(ID);
		Diary diaryToAddTo = employeeToAddTo.getEmployeeDiary();
		if(checkThisIsPossible(employeeToAddTo, groupMeeting) == true)
		{
			diaryToAddTo.addEntry(groupMeeting);
			//diaryToAddTo.printDiary(employeeToAddTo);
		}
		else
		{
			System.out.println("Unfortunatly, this clashed with another meeting:");
		}
	}
	public boolean checkThisIsPossible(Employee employee ,Meeting check)
	{
		Diary theEmployeeDiary  = employee.getEmployeeDiary();
		boolean test =theEmployeeDiary.checkIfValid(check);
		if(test == true)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
	
	public void hold()
	{
		
		LocalTime closingTime = LocalTime.parse("20:00");
		LocalTime openingTime = LocalTime.parse("10:00");
		Scanner s = new Scanner(System.in);
		String meeting;
		System.out.println("Please input a description of the meeting");
		String desc = s.nextLine();
		System.out.println("Please input the begining Date of the potential meeting (YYYY-MM-DD)");
		meeting = s.nextLine();
		sDate = LocalDate.parse(meeting);
		System.out.println("Please input the ending Date of the potential meeting (YYYY-MM-DD)");
		meeting = s.nextLine();
		eDate = LocalDate.parse(meeting);
		System.out.println("Please enter how long the meeting will last (Hours)");
		timeAddHrs = s.nextLong();
		System.out.println("Please enter how long the meeting will last (Min)");
		timeAddMin = s.nextLong();
		long startTime = System.currentTimeMillis();
		LocalTime ReOccuringTimeS = openingTime.plusHours(timeAddHrs).plusMinutes(timeAddMin);
		LocalTime ReOccuringTimeE = ReOccuringTimeS.plusHours(timeAddHrs).plusMinutes(timeAddMin);
		ArrayList<Meeting> theMeetingsList = new ArrayList<Meeting>();
		Meeting possibleMeetings = new Meeting(sDate, openingTime, openingTime.plusHours(timeAddHrs).plusMinutes(timeAddMin), desc);
		theMeetingsList.add(possibleMeetings);
		boolean complete = false;
		do
		{
			possibleMeetings = new Meeting(sDate, ReOccuringTimeS, ReOccuringTimeE, desc);
			if(possibleMeetings.getEndTime().isAfter(closingTime) != true)
			{
				theMeetingsList.add(possibleMeetings);
				ReOccuringTimeS = ReOccuringTimeS.plusHours(timeAddHrs).plusMinutes(timeAddMin);
				ReOccuringTimeE = ReOccuringTimeE.plusHours(timeAddHrs).plusMinutes(timeAddMin);
			}
			else
			{
				sDate = sDate.plusDays(1);
				ReOccuringTimeS = openingTime;
				ReOccuringTimeE = ReOccuringTimeS.plusHours(timeAddHrs).plusMinutes(timeAddMin);
				if(sDate.isAfter(eDate))
				{
					complete = true;
				}
			}
		}
		while(complete == false);
		System.out.println("How many Employees do you intend to attend the meeting?");
		int inputs = s.nextInt();
		ArrayList<Integer> employeesAdded = new ArrayList<Integer>();
		for(int x = 0; x<inputs; x++)
		{
			System.out.println("Enter an employee ID:");
			int ID = s.nextInt();
			employeesAdded.add(ID);
			if(findEmployee(ID) == null)
			{
				System.out.println("Could not find the employee: " + x);
				continue;
			}
			else
			{
				
				Employee held = findEmployee(ID);
				held.getEmployeeDiary();
				Diary pass = held.getEmployeeDiary();
				pass.checkIfValidForMultiple(held, theMeetingsList);
			}
		}
		System.out.println("Possible Meetings are as follows: ");
		for(int x = 0; x <theMeetingsList.size();x++)
		{
			System.out.println("Meeting Option " + x + ":");
			Meeting print =	theMeetingsList.get(x);
			Diary diary = new Diary();
			System.out.println(" ");
			diary.printMeeting(print);
			System.out.println(" ");
			
		}
		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime)/1000 + " seconds!");
		System.out.println("Please select a number from the list: ");
		int x = s.nextInt();
		Meeting addMeeting = theMeetingsList.get(x);
		for(int y = 0; y<employeesAdded.size();y++)
		{
			int ID = employeesAdded.get(y);
			if(findEmployee(ID) == null)
			{
				System.out.println("Could not find the employee: " + x);
				continue;
			}
			else
			{
				Employee held = findEmployee(ID);
				Diary employeeAdd = held.getEmployeeDiary();
				employeeAdd.addEntry(addMeeting);
				employeeAdd.printDiary(held);
			}
		}
		

		
	} 
	


	public void initSomeEmployees()
	{
		Employee kev = new Employee(2000);
		kev.setEmployeeDiary(new Diary());
		Diary diary = kev.getEmployeeDiary();
		LocalTime testTime = LocalTime.parse("12:00");
		LocalTime testTime2 = LocalTime.parse("13:00");
		LocalDate test3 = LocalDate.parse("2008-12-12");
		Meeting test = new Meeting(test3, testTime, testTime2, "test");
		diary.addEntry(test);
		Employee sam = new Employee(4000);
		sam.setEmployeeDiary(new Diary());
		Employee jeff = new Employee(1000);
		jeff.setEmployeeDiary(new Diary());
		Employee sally = new Employee(3000);
		sally.setEmployeeDiary(new Diary());
		employeeTree.add(kev);
		employeeTree.add(sam);
		employeeTree.add(jeff);
		employeeTree.add(sally);
	}
			
	public static void main(String[] args)
	{
		Manager testMenu = new Manager();
		testMenu.initSomeEmployees();
		//testMenu.addAMeeting();
		testMenu.hold();
	}	
}
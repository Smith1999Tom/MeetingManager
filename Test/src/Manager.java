import java.util.TreeSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Manager {

	TreeSet<Employee> employeeTree;
	Iterator<Employee> iterator;
	
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
		printMeeting(groupMeeting);
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
	public void printMeeting(Meeting meeting)
	{
		LocalDate date = meeting.getDateOfMeeting();
		LocalTime sTime = meeting.getStartTime();
		LocalTime eTime = meeting.getEndTime();
		String desc = meeting.getDescription();
	
		System.out.println("Date: " + date + "\nStarts at: " + sTime + "\nEnds at: " + eTime + "\nDescription: " + desc);
	}
	/**
	public void hold()
	{
		initSomeEmployees();
		int value;
		Scanner s = new Scanner(System.in);
		System.out.println("How many employees would you like to add this?");
		value = s.nextInt();		
		for(int x=0;x<value;x++)
		{
			boolean validID = false;
			boolean clash = false;
			int ID =0;
			do
			{
				System.out.println("Please input an employee ID");
				ID = s.nextInt();
				if(findEmployee(ID) !=null)
				{
					System.out.println("This wasnt found, please try again");
				}
			}
			while(validID == false);
			Employee employeeToAddTo = null;
			employeeToAddTo = findEmployee(ID);
			Diary diaryToAddTo = employeeToAddTo.getEmployeeDiary();
			if(diaryToAddTo.findInDiary(groupMeeting) == false)
			{
				clash = true;
			}
			else
			{
				clash = false;
			}
			if(clash == false)
			{
				diaryToAddTo.addEntry(groupMeeting);
			}
			else
			{
				System.out.println("Unfortunatly, This employee: ");
			}
			
			
		}
	} */
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
		testMenu.addAMeeting();
	}	
}
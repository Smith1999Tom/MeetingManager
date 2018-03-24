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
	
	Manager()
	{
		employeeTree = new TreeSet<Employee>();
		addEmployeesOnStartup();
	}
	
	private void addEmployeesOnStartup()
	{
		employeeTree.add(new Employee(100));
		employeeTree.add(new Employee(200));
		employeeTree.add(new Employee(300));
		employeeTree.add(new Employee(400));
		employeeTree.add(new Employee(500));
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


}
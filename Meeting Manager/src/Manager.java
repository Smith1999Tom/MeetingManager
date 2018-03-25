import java.util.TreeSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
	/**
	 * This is the method that adds employees to the tree, done on startup
	 */
	private void addEmployeesOnStartup()
	{
		employeeTree.add(new Employee(100));
		employeeTree.add(new Employee(200));
		employeeTree.add(new Employee(300));
		employeeTree.add(new Employee(400));
		employeeTree.add(new Employee(500));
	}
	
	/**
	 * This checks if the tree is empty, called when it needs to confirm if it can do an action requested
	 */	
	Boolean isEmpty()
	{
		return employeeTree.isEmpty();
	}
	/**
	 * This searches for the employee the user has specified, this will return the result
	 */
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
	/**
	 * This is the method that adds employees to the tree, done on startup
	 */
	void addEmployee(Employee addEmployee)
	{
		employeeTree.add(addEmployee);
	}
	/**
	 * This is the method that searches the tree for employees, and adds meetings to multiple employees
	 * @param closingTime 		This acts as an end time for the add meeting search
	 * @param openingTime 		This acts as a beginning time for the user to add meetings to
	 * @param s					This is the scanner that detects the user input
	 * @param meeting			This detects the user input and parses it to other types of data
	 * @param desc				This detects the user input for the description and puts it into the meeting
	 * @param sDate				This is the start date, this accepts LocalDate type data, parsed by meeting
	 * @param eDate				This is the end date, this accepts LocalDate type data, parsed by meeting
	 * @param timeAddHrs		This is the unit of hours the system adds to each meeting
	 * @param timeAddMins 		This is the unit of hours that the system adds to each meeting
	 * @param startTime			This begins the time that the method runs the search
	 * @param endTime			This ends the time the method was searched
	 * @param reOccuringTimeS	This is the LocalTime variable that starts at the beginning of the day and adds the meeting time
	 * @param reOccuringTimeE 	This local time acts the full meeting time ahead of reOccuringTimeS, checks against end values
	 * @param theMeetingList	This is the arrayList that holds all the possible meetings before the check, this will remove meetings if clashes occur
	 * @param possibleMeetings	This is the meetings that are added to the ArrayList, all possible times are added here
	 * @param complete			This determines if the arrayList has been completed for all possible meetings
	 * @param held				This holds the info for the user diary that it is appropriate to
	 * @param diary				This calls on the diary, located within the held employee
	 * @param inputs 			This detects the user input for the ID
	 * @param x					This determines how long the for loop runs for 
	 * @param employeeAdded		This is the list of employees that it has added it to, helps the print determine who to print for
	 * @param print				This is the method that prints the given info to the user, all new meeting participants will be shown their diary
	 * @param pass 				This acts as an in-between to pass on the information
	 * @param employeeAdd		This adds the employees to the list
	 */
	public void hold()
	{
<<<<<<< HEAD
		try
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
			LocalTime reOccuringTimeS = openingTime.plusHours(timeAddHrs).plusMinutes(timeAddMin);
			LocalTime reOccuringTimeE = reOccuringTimeS.plusHours(timeAddHrs).plusMinutes(timeAddMin);
			ArrayList<Meeting> theMeetingsList = new ArrayList<Meeting>();
			Meeting possibleMeetings = new Meeting(sDate, openingTime, openingTime.plusHours(timeAddHrs).plusMinutes(timeAddMin), desc);
			theMeetingsList.add(possibleMeetings);
			boolean complete = false;
			do
			{
				possibleMeetings = new Meeting(sDate, reOccuringTimeS, reOccuringTimeE, desc);
				if(possibleMeetings.getEndTime().isAfter(closingTime) != true)
				{
					theMeetingsList.add(possibleMeetings);
					reOccuringTimeS = reOccuringTimeS.plusHours(timeAddHrs).plusMinutes(timeAddMin);
					reOccuringTimeE = reOccuringTimeE.plusHours(timeAddHrs).plusMinutes(timeAddMin);
				}
				else
				{
					sDate = sDate.plusDays(1);
					reOccuringTimeS = openingTime;
					reOccuringTimeE = reOccuringTimeS.plusHours(timeAddHrs).plusMinutes(timeAddMin);
					if(sDate.isAfter(eDate))
					{
						complete = true;
					}
				}
=======
		
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
		LocalTime reOccuringTimeS = openingTime.plusHours(timeAddHrs).plusMinutes(timeAddMin);
		LocalTime reOccuringTimeE = reOccuringTimeS.plusHours(timeAddHrs).plusMinutes(timeAddMin);
		ArrayList<Meeting> theMeetingsList = new ArrayList<Meeting>();
		Meeting possibleMeetings = new Meeting(sDate, openingTime, openingTime.plusHours(timeAddHrs).plusMinutes(timeAddMin), desc);
		theMeetingsList.add(possibleMeetings);
		boolean complete = false;
		do
		{
			possibleMeetings = new Meeting(sDate, reOccuringTimeS, reOccuringTimeE, desc);
			if(possibleMeetings.getEndTime().isAfter(closingTime) != true)
			{
				theMeetingsList.add(possibleMeetings);
				reOccuringTimeS = reOccuringTimeS.plusHours(timeAddHrs).plusMinutes(timeAddMin);
				reOccuringTimeE = reOccuringTimeE.plusHours(timeAddHrs).plusMinutes(timeAddMin);
>>>>>>> 2e763b7e012c35d93398a5de2b83762c33e8c030
			}
			while(complete == false);
			System.out.println("How many Employees do you intend to attend the meeting?");
			int inputs = s.nextInt();
			ArrayList<Integer> employeesAdded = new ArrayList<Integer>();
			for(int x = 0; x<inputs; x++)
			{
<<<<<<< HEAD
				System.out.println("Enter an employee ID:");
				int ID = s.nextInt();
				employeesAdded.add(ID);
				if(findEmployee(ID) == null)
=======
				sDate = sDate.plusDays(1);
				reOccuringTimeS = openingTime;
				reOccuringTimeE = reOccuringTimeS.plusHours(timeAddHrs).plusMinutes(timeAddMin);
				if(sDate.isAfter(eDate))
>>>>>>> 2e763b7e012c35d93398a5de2b83762c33e8c030
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
		catch(java.time.format.DateTimeParseException e)
		{
			System.out.println("Invalid data entered - " + e);
		}
		catch(InputMismatchException e)
		{
			System.out.println("Invalid data entered - " + e);
		}
		
		
<<<<<<< HEAD
		

		
=======
>>>>>>> 2e763b7e012c35d93398a5de2b83762c33e8c030
}
}
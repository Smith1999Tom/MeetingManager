import java.util.Scanner;

public class Menu {

	static Manager managerTree = new Manager();
	static Employee currentEmployee;

	public static void main(String[] args)
	{
		
		/*
		//Add an employee with a pre set diary
		managerTree = new Manager();
		Employee employee5 = new Employee(5);
		employee5.setEmployeeDiary(new Diary());
		managerTree.addEmployee(employee5);
		
		//Add a diary to an employee already in the tree
		Employee employee10 = new Employee(10);
		managerTree.addEmployee(employee10);
		
		Employee findEmployee = managerTree.findEmployee(10);
		findEmployee.setEmployeeDiary(new Diary());
		
		Employee loadEmployee = new Employee(15);
		loadEmployee.setEmployeeDiary(loadEmployee.loadDiary("./Files/Test.txt"));
		loadEmployee.printDiary();
		loadEmployee.getEmployeeDiary().saveDiary("./Files/Test2.txt");
		*/
		mainMenu();
		
		
		
	}
	
	/**
	 * This is the menu choice the user makes based on the user ID selected
	 * 
	 * @param userChoice  	This is the value of the scanner, this is what it compares the options to
	 * @param Case 1		This lets the user access a specific employee menu  
	 * @param Case 2		This allows the user to search and mass add a meeting
	 * @param Case 3		This exits the program
	 * @param exitCondition	This runs the menu until the user selects exit
	 * 
	 * Each if statement checks for the value the user has entered, if it is not matching 
	 * its records, it will tell the user to try again
	 */
	private static void mainMenu()
	{
		boolean exitCondition = false;
		while(!exitCondition)
		{
			System.out.println("Select an option from the list below");
			System.out.println("");
			System.out.println("1 - Select an employee to manage");
			System.out.println("2 - Search for available meetings");
			System.out.println("3 - Exit program");
			
			int userChoice = getUserIntInput();
			
			switch(userChoice)
			{
			case 1:
				System.out.println("Enter your employee ID");
				int inputID = getUserIntInput();
				currentEmployee = managerTree.findEmployee(inputID);
				if(currentEmployee != null)
					employeeMenu();
				break;
			case 2:
				managerTree.hold();
				break;
			case 3:
				exitCondition = true;
				break;
			}
		}
		
		
		
				
		
	}
	/**
	 * This is the menu choice the user makes based on the user ID selected
	 * @param userChoice  This is the value of the scanner, this is what it compares the options to
	 * @param Case 1	This Adds a meeting   
	 * @param Case 2	This removes a meeting
	 * @param Case 3	This allows the user to edit the meeting
	 * @param Case 4    This is the undo, it undoes the previous action
	 * @param Case 5	This saves an employee diary
	 * @param Case 6	This loads an employee diary
	 * @param Case 7	This prints the diary to the employee
	 * @param Case 8    This exit method, it take the user back to the top
	 * 
	 * Each if statement checks for the value the user has entered, if it is not matching 
	 * its records, it will tell the user to try again
	 */
	private static void employeeMenu()
	{
		boolean exitCondition = false;
		while(!exitCondition)
		{
			System.out.println("Select an option from the list below");
			System.out.println("");
			System.out.println("1 - Add a meeting");
			System.out.println("2 - Remove a meeting");
			System.out.println("3 - Edit a meeting");
			System.out.println("4 - Undo last meeting");
			System.out.println("5 - Save employee diary");
			System.out.println("6 - Load employee diary");
			System.out.println("7 - Print employee diary");
			System.out.println("8 - Return to menu");
			
			int userChoice = getUserIntInput();
			switch(userChoice)
			{
			case 1:	//Add
				System.out.println("Enter the date of the meeting");
				String date = getUserStringInput();
				System.out.println("Enter the start time");
				String startTime = getUserStringInput();
				System.out.println("Enter the end time");
				String endTime = getUserStringInput();
				System.out.println("Enter a description");
				String description = getUserStringInput();
				currentEmployee.getEmployeeDiary().addEntry(new Meeting(date, startTime, endTime, description));
				break;
				
			case 2: //Delete
				currentEmployee.getEmployeeDiary().searchWithDateTime("Delete");
				break;
			
			case 3: //Edit
				currentEmployee.getEmployeeDiary().searchWithDateTime("Edit");
				break;
			case 4: //Undo
				currentEmployee.getEmployeeDiary().undo();
				break;
			case 5:	//Save
				String saveString = getUserStringInput();
				currentEmployee.saveDiary(saveString);
				break;
			case 6:	//Load
				String loadString = getUserStringInput();
				currentEmployee.loadDiary(loadString);
				break;
			case 7: //Print
				//currentEmployee.getEmployeeDiary().print();
				currentEmployee.getEmployeeDiary().sort();
				currentEmployee.getEmployeeDiary().print();
				break;
			case 8: //Exit
				exitCondition = true;
				break;
			}
		}
		
	}
	/**
	 * This method waits on user inputs and determines what the user will be inputing in the event they want to select and option
	 * @param sc	This is the scanner, it senses the user input
	 * @param userInput 	This detects the user input and provides feedback to the system
	 */	
	private static int getUserIntInput()
	{
		Scanner sc = new Scanner(System.in);
		int userInput;
		while(true)
		{
			if(sc.hasNextInt())
			{
				userInput = sc.nextInt();
				return userInput;
			}
			else
			{
				sc.next();  
				System.out.println("Invalid input. Please enter a number");
			}
		}
	}
	/**
	 * This method waits on user inputs and determines what the user will be inputing in the event they want to select and option
	 * @param sc	This is the scanner, it senses the user input
	 * @param userInput 	This detects the user input and provides feedback to the system
	 */	
	private static String getUserStringInput()
	{
		Scanner sc = new Scanner(System.in);
		String userInput = sc.nextLine();
		return userInput;
	}
	
	
	
	
	
}

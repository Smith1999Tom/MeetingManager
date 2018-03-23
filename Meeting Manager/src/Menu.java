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
		Employee addEmployee = new Employee(100);
		managerTree.addEmployee(addEmployee);
		mainMenu();
		
		
		
	}
	
	
	private static void mainMenu()
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
			
		}
				
		
	}
	
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
				
			case 2: 
				currentEmployee.getEmployeeDiary().searchWithDateTime("Delete");
				break;
			
		case 3:currentEmployee.getEmployeeDiary().searchWithDateTime("Edit");
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
				currentEmployee.getEmployeeDiary().print();
				currentEmployee.getEmployeeDiary().sort();
				currentEmployee.getEmployeeDiary().print();
			}
		}
		
	}
	
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
	
	private static String getUserStringInput()
	{
		Scanner sc = new Scanner(System.in);
		String userInput = sc.nextLine();
		return userInput;
	}
	
	
	
	
	
}

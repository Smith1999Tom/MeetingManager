
public class Menu {

	Manager managerTree;

	public static void main(String[] args)
	{
		Menu testMenu = new Menu();
		
		//Add an employee with a pre set diary
		testMenu.managerTree = new Manager();
		Employee employee5 = new Employee(5);
		employee5.setEmployeeDiary(new Diary());
		testMenu.managerTree.addEmployee(employee5);
		
		//Add a diary to an employee already in the tree
		Employee employee10 = new Employee(10);
		testMenu.managerTree.addEmployee(employee10);
		
		Employee findEmployee = testMenu.managerTree.findEmployee(10);
		findEmployee.setEmployeeDiary(new Diary());
		
		System.out.println("Done");
		
	}
	
	
}

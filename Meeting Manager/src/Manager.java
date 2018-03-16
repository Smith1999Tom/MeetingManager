import java.util.TreeSet;
import java.util.Iterator;

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
	
	
}


import java.util.LinkedList;
import java.util.Stack;

public class Manager 
{
	LinkedList<Employee> employees;
	Stack<Employee> stack;
	public void Diary()
	{
		employees = new LinkedList<Employee>();
	}
	public void addEmployee(Employee addEmployee)
	{
		employees.add(addEmployee);
	}
}
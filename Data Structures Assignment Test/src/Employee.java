
public class Employee implements Comparable<Employee>{

	Diary employeeDiary;
	int employeeID;
	
	Employee(int ID)
	{
		this.employeeID = ID;
	}

	public Diary getEmployeeDiary() {
		return employeeDiary;
	}

	public void setEmployeeDiary(Diary employeeDiary) {
		this.employeeDiary = employeeDiary;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	@Override
	public int compareTo(Employee arg0) {
		// TODO Auto-generated method stub
		if(arg0.getEmployeeID() > this.employeeID)
			return -1;
		else if(arg0.getEmployeeID() < this.employeeID)
			return 1;
		else
			return 0;
	}
	
	
	
}

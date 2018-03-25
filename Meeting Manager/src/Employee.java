
public class Employee implements Comparable<Employee>{

	Diary employeeDiary;
	int employeeID;
	
	Employee(int ID)
	{
		this.employeeID = ID;
		this.employeeDiary = new Diary();
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
	/**
	 * This allows the system to compare employees, without it, find, add or delete wouldn't be able to look at other nodes
	 * @param arg0			The employee to compare to
	 * @param employeeID	The ID to compare
	 */	
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
	/**
	 * This allows the diary to load and calls the method for it, creating it a new diary 
	 * @param loadDiary		This calls the diary as a new diary, and sets it for the employee
	 */	
	void loadDiary(String loadString)
	{
		Diary loadDiary = new Diary();
		loadDiary = loadDiary.loadDiary(loadString);
		if(loadDiary != null)
			this.employeeDiary = loadDiary;
	}
	/**
	 * This allows the diary to save and calls the method for it, saving it to a file 
	 * @param saveString 	This is the input it will pass onto the save method
	 */	
	void saveDiary(String saveString)
	{
		employeeDiary.saveDiary(saveString);
	}
	/**
	 * This allows the diary to print 
	 */	
	void printDiary()
	{
		this.employeeDiary.print();
	}	
}

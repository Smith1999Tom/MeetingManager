
public class Employee
{
private Diary employeeDiary;
private int ID;

public Diary getEmployeeDiary() {
	return employeeDiary;
}

public void setEmployeeDiary(Diary employeeDiary) {
	this.employeeDiary = employeeDiary;
}

public int getID() {
	return ID;
}

public void setID(int iD) {
	ID = iD;
}

public Employee(Diary employeeDiary, int ID)
{
	this.employeeDiary = employeeDiary;
	this.ID = ID;
}
}

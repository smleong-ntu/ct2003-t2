package main.staff;

public abstract class Task {
	
	protected final String name;
	
	//Placeholder
	public Task(String name) {
		this.name = name;
	}
	
	public void execute() {}
	public boolean isTaskValid(StringBuilder reason) { return true; }
	public String taskName() { return name; }
}

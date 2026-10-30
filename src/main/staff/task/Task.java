package main.staff.task;

public abstract class Task {
	
	protected final String name;
	
	//Placeholder
	public Task(String name) {
		this.name = name;
	}
	
	public void execute() {
		System.out.printf("Executing %s: ", this.getClass().getSimpleName());
	}
	public boolean isTaskValid(StringBuilder reason) { return true; }
	public String taskName() { return name; }
}

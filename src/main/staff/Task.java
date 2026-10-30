package main.staff;

public abstract class Task {
	
	protected final String name;
	
	//Placeholder
	public Task(String name) {
		this.name = name;
	}
	
	public void execute() {
		System.out.printf("Executing %s: %s%n", this.getClass().getSimpleName(), name);
	}
	public boolean isTaskValid(StringBuilder reason) { return true; }
	public String taskName() { return name; }
}

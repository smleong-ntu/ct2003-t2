package main.staff.task;

import java.util.List;

public abstract class Task {
	
	protected final String name;
	protected List<Task> taskQueue;
	
	//Placeholder
	public Task(String name) {
		this.name = name;
	}
	
	public void execute(int turn) {
		System.out.printf("Executing %s: ", this.getClass().getSimpleName());
	}
	
	/*this information can determine choices that was already set before current task*/
	public boolean isTaskValid(StringBuilder reason, List<Task> taskQueue) { return true; }
	public String taskName() { return name; }
}

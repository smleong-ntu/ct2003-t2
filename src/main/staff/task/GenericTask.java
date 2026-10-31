package main.staff.task;

import java.util.List;

//THIS IS A PLACEHOLDER CLASS TO TEST TASK IMPLEMENTATIONS
public class GenericTask extends Task{
	public GenericTask(String name) {
		super(name);
	}
	
	@Override
	public void execute(int turn) {
		System.out.printf("Executing Task: %s%n", name);
	}
	
	@Override
	public boolean isTaskValid(StringBuilder reason, List<Task> taskQueue) 
	{ 
		reason.append("Reason from Task Validation");
		return true; 
	}
}

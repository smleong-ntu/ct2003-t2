package main.staff;

//THIS IS A PLACEHOLDER CLASS TO TEST TASK IMPLEMENTATIONS
public class GenericTask extends Task{
	public GenericTask(String name) {
		super(name);
	}
	
	public void execute() {
		System.out.printf("Executing Task: %s%n", name);
	}
	
	@Override
	public boolean isTaskValid(StringBuilder reason) 
	{ 
		reason.append("Reason from Task Validation");
		return true; 
	}
}

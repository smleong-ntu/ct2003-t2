package main.staff;

public abstract class Task {	
	public void execute() {}
	public boolean isTaskValid() { return true; }
	public String taskName() { return "default task"; }
}

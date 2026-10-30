package main.staff.task;

import main.staff.*;

public class WaitTask extends Task{
	public WaitTask (String name) {
		super(name);
	}
	
	public void execute() {
		super.execute();
		System.out.printf("Nothing was done by ", this.getClass().getSimpleName(), name);
	}
}

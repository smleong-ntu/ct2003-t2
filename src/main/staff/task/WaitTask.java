package main.staff.task;

import main.staff.*;

public class WaitTask extends Task{
	
	protected Staff staff;
	
	public WaitTask (String name, Staff staff) {
		super(name);
		this.staff = staff;
	}
	
	public void execute() {
		super.execute();
		System.out.printf("Nothing was done by >> %s [ID:%d]<<%n", staff.getClass().getSimpleName(), staff.getID());
	}
}

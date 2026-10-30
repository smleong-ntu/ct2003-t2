package main.staff.task;

import main.staff.*;

public class WaitTask extends Task{
	
	protected Staff staff;
	
	public WaitTask (String name, Staff staff) {
		super(name);
		this.staff = staff;
	}
	
	@Override
	public void execute(int turn) {
		super.execute(turn);
		staff.waitTurn();
		System.out.printf("Nothing was done by >> %s [ID:%d] <<%n", staff.getClass().getSimpleName(), staff.getID());
	}
}

package main.staff.task;

import main.staff.Waiter;

public class SeatTask extends WaiterTask{
	
	public SeatTask(String name, Waiter waiter) {
		super(name, waiter);
	}
	
	@Override
	public void execute(int turn) {
		super.execute(turn);
	}
	
	@Override
	public boolean isTaskValid(StringBuilder reason) {
		return true;
	}
}

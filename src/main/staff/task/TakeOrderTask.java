package main.staff.task;

import main.staff.Waiter;

public class TakeOrderTask extends WaiterTask {
	public TakeOrderTask(String name, Waiter waiter) {
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

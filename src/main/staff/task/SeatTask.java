package main.staff.task;

import main.gameflow.GameSession;
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
		
		if (GameSession.session().restaurant().getWaitingQueue().size() == 0) {
			
			reason.append("Invalid Task: No customer to seat");
			return false;
		}
		
		//If there is a customer, without table, cannot seat customer
		if (GameSession.session().restaurant().getFreeTables().size() == 0) {
			
			reason.append("Invalid Task: No free tables to seat customer");
			return false;
		}
		
		//There is a customer in queue AND an available table
		return true;
	}
}

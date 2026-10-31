package main.staff.task;

import main.gameflow.GameSession;
import main.staff.Waiter;

public class ServeOrderTask extends WaiterTask{
	public ServeOrderTask(String name, Waiter waiter) {
		super(name, waiter);
	}
	
	@Override
	public void execute(int turn) {
		super.execute(turn);
	}
	
	@Override
	public boolean isTaskValid(StringBuilder reason) {
		
		if (GameSession.session().restaurant().getReadyOrders().size() == 0) {
			reason.append("Invalid Task: No ready orders to serve");
			return false;
		}
		
		//At least one order in READY state
		return true;
	}
}

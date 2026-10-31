package main.staff.task;

import java.util.List;

import main.gameflow.GameSession;
import main.restaurant.*;
import main.order.*;
import main.staff.Waiter;

public class ServeOrderTask extends WaiterTask{
	public ServeOrderTask(String name, Waiter waiter) {
		super(name, waiter);
	}
	
	@Override
	public void execute(int turn) {
		super.execute(turn);
		
		Restaurant restaurant = GameSession.session().restaurant();
		Order order = waiter.findReadyOrder();
		
		if (order != null) {
			waiter.serveOrder(order);			
		}
	}
	
	@Override
	public boolean isTaskValid(StringBuilder reason, List<Task> taskQueue) {
		
		if (GameSession.session().restaurant().getReadyOrders().size() == 0) {
			reason.append("Invalid Task: No ready orders to serve");
			return false;
		}
		
		//At least one order in READY state
		return true;
	}
}

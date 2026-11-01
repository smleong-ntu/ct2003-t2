package main.staff.task;

import java.util.List;

import main.gameflow.GameSession;
import main.restaurant.*;
import main.order.*;
import main.staff.Waiter;
import main.staff.task.Task.Pending;

public class ServeOrderTask extends WaiterTask{
	public ServeOrderTask(String name, Waiter waiter) {
		super(name, waiter);
	}
	
	@Override
	public void execute(int turn) {
		super.execute(turn);
		
		waiter.serveOrder(GameSession.session().restaurant().getReadyOrders().get(target));
	}
	
	@Override
	public boolean isTaskValid(StringBuilder reason, List<Task> taskQueue) {
		
		if (GameSession.session().restaurant().getReadyOrders().size() == 0) {
			reason.append(String.format(">>%s<< No ready orders to serve", name));
			return false;
		}
		
		//At least one order in READY state
		return true;
	}
	
	@Override
	public List<String> targets(List<Task> taskQueue) {
		return GameSession.session().restaurant().getReadyOrders().stream()
				.map(o -> String.format("Customer %d: %s", o.getOwner().getCustomerId(), o))
				.toList();
	}
	
	@Override
	protected void setPendingInfo() {
		super.setPendingInfo();
		pending.add(Pending.SERVE);
	}
}

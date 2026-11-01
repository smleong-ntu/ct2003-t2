package main.staff.task;

import java.util.List;
import java.util.ArrayList;
import main.gameflow.GameSession;
import main.restaurant.*;
import main.order.*;
import main.staff.Chef;

public class PrepareOrderTask extends ChefTask {
	
	public PrepareOrderTask(String name, Chef chef) {
		super(name,chef);
	}
	
	@Override
	public void execute(int turn) {
		super.execute(turn);
		
		Restaurant restaurant = GameSession.session().restaurant();		
		chef.prepareOrder(restaurant.getOrdersInKitchen().get(target));
	}
	
	@Override
	public boolean isTaskValid(StringBuilder reason, List<Task> taskQueue) {
		
		int pendingOrders = 0;
		for (Task t : taskQueue) {
			if (t.getPendingInfo().contains(Pending.ORDER)){
				pendingOrders ++;
			}
		}
		
		int validOrders = GameSession.session().restaurant().getOrdersInKitchen().size() + pendingOrders;
		
		if (validOrders == 0) {
			reason.append(String.format(">>%s<< No available orders can be prepared in kitchen", name));
			return false;
		}
		
		//There is at least one order that is PLACED or IN_PREPARATION or pending to be created
		return true;
	}
	
	@Override
	public List<String> targets(List<Task> taskQueue) {
		List<String> targets = new ArrayList<>();

		GameSession.session().restaurant().getOrdersInKitchen().forEach(o -> {
			targets.add(String.format("Customer %d: %s", o.getOwner().getCustomerId(), o));
		});

		//A new order joins the end of the kitchen list when the Waiter acts, so its index still works
		for (Task t : taskQueue) {
			if (t.getPendingInfo().contains(Pending.ORDER)) {
				targets.add("The order being taken this turn");
			}
		}

		return targets;
	}
	
	@Override
	protected void setPendingInfo() {
		super.setPendingInfo();
		pending.add(Pending.PREPARE);
	}
}

package main.staff.task;

import java.util.List;

import main.gameflow.GameSession;
import main.restaurant.*;
import main.order.*;
import main.staff.Chef;
import main.staff.task.Task.Pending;

public class PrepareOrderTask extends ChefTask {
	
	public PrepareOrderTask(String name, Chef chef) {
		super(name,chef);
	}
	
	@Override
	public void execute(int turn) {
		super.execute(turn);
		
		Restaurant restaurant = GameSession.session().restaurant();
		Order order = chef.findOrderToPrepare(restaurant.getOrdersInKitchen());
		
		if (order != null) {
			chef.prepareOrder(order);			
		}
	}
	
	@Override
	public boolean isTaskValid(StringBuilder reason, List<Task> taskQueue) {
		
		if (GameSession.session().restaurant().getOrdersInKitchen().size() == 0) {
			reason.append("Invalid Task: No available orders can be prepared in kitchen");
			return false;
		}
		
		//There is at least one order that is PLACED or IN_PREPARATION
		return true;
	}
	
	@Override
	protected void setPendingInfo() {
		super.setPendingInfo();
		pending.add(Pending.PREPARE);
	}
}

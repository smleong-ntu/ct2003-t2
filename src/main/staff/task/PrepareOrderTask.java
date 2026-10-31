package main.staff.task;

import main.gameflow.GameSession;
import main.staff.Chef;

public class PrepareOrderTask extends ChefTask {
	
	public PrepareOrderTask(String name, Chef chef) {
		super(name,chef);
	}
	
	@Override
	public void execute(int turn) {
		super.execute(turn);
	}
	
	@Override
	public boolean isTaskValid(StringBuilder reason) {
		
		if (GameSession.session().restaurant().getOrdersInKitchen().size() == 0) {
			reason.append("Invalid Task: No available orders can be prepared in kitchen");
			return false;
		}
		
		//There is at least one order that is PLACED or IN_PREPARATION
		return true;
	}
}

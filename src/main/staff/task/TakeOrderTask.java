package main.staff.task;

import java.util.List;
import main.gameflow.GameSession;
import main.staff.Waiter;
import main.restaurant.*;
import main.customer.*;

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
		
		List<Table> occupiedTables = GameSession.session().restaurant().getOccupiedTables();
		
		if (occupiedTables.size() == 0) {
			reason.append("Invalid Task: No table is occupied for an order to be taken");
			return false;
		}
		
		for (int i = 0; i < occupiedTables.size(); i++) {
			
			//At least one customer is in seated state - ready for order to be taken
			if (occupiedTables.get(i).getOccupant().getState() == CustomerState.SEATED) {
				return true;
			}
		}
		
		//All customers in occupied tables have already been served
		reason.append("Invalid Task: All customers seated at tables have been served");
		return false;
	}
}

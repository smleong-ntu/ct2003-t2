package main.gameflow.turns;

import java.util.List;

import main.gameflow.*;
import main.restaurant.*;
import main.staff.*;
import main.staff.task.*;

public class DiningStep extends TurnStep{

	private Restaurant restaurant;
	private TaskManager taskManager;
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		name = "Dining and Payment";
		
		//save reference to restaurant to get customers
		restaurant = GameSession.session().restaurant();
		taskManager = GameSession.session().placeholderTaskManager();
	}
	
	@Override
	public void execute() {
		
		//Get seated customers
		//Only valid when already served
		restaurant.getOccupiedTables().forEach(table -> table.getOccupant().finishEating(turn));
		//This makes customers READY_TO_PAY but cannot pay this round
		
		//Get cashiers
		List<Staff> cashiers = restaurant.getStaffList(Cashier.class);
		cashiers.forEach(staff -> {
			
			for (Task t : staff.getTasks()) {
				StringBuilder reason = new StringBuilder();
				
				//Return first valid task
				if (t.isTaskValid(reason)) {
					t.execute(turn);
					taskManager.addAsCompletedTask(t);
					break;
				}
				
				System.out.printf("Invalid task: %s%n", reason.toString());
			}
			
			
		});
		
		postExecuteAction();
	}
}

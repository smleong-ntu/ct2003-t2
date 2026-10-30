package main.gameflow.turns;

import java.util.List;

import main.gameflow.GameSession;
import main.gameflow.TurnManager;
import main.staff.*;
import main.staff.task.TaskManager;

public class DiningStep extends TurnStep{

	//private Restaurant restaurant;
	private TaskManager taskManager;
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		name = "Dining and Payment";
		
		//save reference to restaurant to get customers
		//restaurant = GameSession().session().getRestaurant();
		
		taskManager = GameSession.session().placeholderTaskManager();
	}
	
	@Override
	public void execute() {
		
		//Get seated customers
		//forEach(customers -> if customer is SERVED, customer.eat()
		//This makes customers READY_TO_PAY but cannot pay this round
		
		//Get cashiers
		//List<Staff> cashiers = restaurant.getStaffList.get(Cashier.class);
//		cashiers.forEach(staff -> {
//			//Create task for cashier and inject dependency
//			Task t = new CashierTask(staff);
//			t.execute();
//			taskManager.addAsCompletedTask(t);
//		});
		
		postExecuteAction();
	}
}

package main.gameflow.turns;

import java.util.List;

import main.gameflow.*;
import main.customer.*;
import main.restaurant.*;
import main.staff.Host;
import main.staff.Staff;
import main.staff.task.*;

public class ArrivalStep extends TurnStep {
	
	private int count = 1;
	private Restaurant restaurant;
	private TaskManager taskManager;
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		name = "Arrivals";
		
		//save reference to restaurant
		restaurant = GameSession.session().restaurant();
		taskManager = GameSession.session().placeholderTaskManager();
	}
	
	@Override
	public void execute() {
		
		//Customer id is set as turn number, could be anything really
		Customer customer = GameSession.data().arrivals().get(turn).apply(GameSession.seed() + count);
		
		if (customer == null) {
			System.out.println("N/A");
		}
		
		else {
			//System.out.println("Customer arrival");
			
			//Push to restaurant queue
			restaurant.addArrival(customer);
			count ++;
		}
		
		//Get hosts, hosts can seat customers from restaurants automatically in this step
		List<Staff> hosts = restaurant.getStaffList(Host.class);
		hosts.forEach(staff -> {
			
			for (Task t : staff.getTasks()) {
				StringBuilder reason = new StringBuilder();
				
				//Return first valid task
				if (t.isTaskValid(reason, taskManager.getQueuedTasks())) {
					t.execute(turn);
					taskManager.addAsCompletedTask(t);
					break;
				}
				
				System.out.printf("Invalid: %s%n", reason.toString());
			}
		});

		postExecuteAction();
	}
}

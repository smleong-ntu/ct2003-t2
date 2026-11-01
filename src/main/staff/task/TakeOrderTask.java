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
		
		Restaurant restaurant = GameSession.session().restaurant();
		Customer customer = seatedCustomers().get(target);
		waiter.takeOrder(restaurant, customer, customer.chooseMenuItem(restaurant.getMenu()), turn);
	}
	
	@Override
	public boolean isTaskValid(StringBuilder reason, List<Task> taskQueue) {
		
		List<Table> occupiedTables = GameSession.session().restaurant().getOccupiedTables();
		
		if (occupiedTables.size() == 0) {
			reason.append(String.format(">>%s<< No table is occupied for an order to be taken", name));
			return false;
		}
		
		for (int i = 0; i < occupiedTables.size(); i++) {
			
			//At least one customer is in seated state - ready for order to be taken
			if (occupiedTables.get(i).getOccupant().getState() == CustomerState.SEATED) {
				return true;
			}
		}
		
		//All customers in occupied tables have already been served
		reason.append(String.format(">>%s<<  All customers seated at tables have been served", name));
		return false;
	}
	
	@Override
	public List<String> targets(List<Task> taskQueue) {
		return seatedCustomers().stream()
				.map(c -> String.format("Customer %d", c.getCustomerId()))
				.toList();
	}
	
	@Override
	protected void setPendingInfo() {
		super.setPendingInfo();
		pending.add(Pending.ORDER);
	}
	
	private List<Customer> seatedCustomers() {
		return GameSession.session().restaurant().getOccupiedTables().stream()
				.map(Table::getOccupant)
				.filter(c -> c.getState() == CustomerState.SEATED)
				.toList();
	}
}

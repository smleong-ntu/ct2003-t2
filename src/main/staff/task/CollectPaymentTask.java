package main.staff.task;

import java.util.List;
import java.util.ArrayList;
import main.gameflow.GameSession;
import main.customer.*;
import main.staff.*;

public class CollectPaymentTask extends CashierTask{
	public CollectPaymentTask(String name, Cashier cashier) {
		super(name, cashier);
	}
	
	@Override
	public void execute(int turn) {
		super.execute(turn);
		System.out.printf("Collect Payment done by >> %s [ID:%d] <<%n", cashier.getClass().getSimpleName(), cashier.getID());
		
		List<Customer> customers = new ArrayList<>();	
		GameSession.session().restaurant().getOccupiedTables().forEach(table -> customers.add(table.getOccupant()));
		
		Customer readyCustomer = cashier.findReadyCustomer(customers, turn);
		
		if (readyCustomer != null) {
			cashier.handlePayment(readyCustomer, turn);
		}
	}
	
	@Override
	public boolean isTaskValid(StringBuilder reason, List<Task> taskQueue) {
		//Do checks here
		List<Customer> customers = new ArrayList<>();	
		GameSession.session().restaurant().getOccupiedTables().forEach(table -> customers.add(table.getOccupant()));
		
		for (int i = 0; i < customers.size(); i++) {
			if (customers.get(i).getState() == CustomerState.READY_TO_PAY) {
				return true;
			}
		}
		
		reason.append(String.format(">>%s<< No paying customers available ", name));
		return false;
	}
	
	@Override
	protected void setPendingInfo() {
		super.setPendingInfo();
		pending.add(Pending.PAYMENT);
	}
}

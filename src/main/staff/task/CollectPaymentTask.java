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
	
	public boolean isTaskValid(StringBuilder reason) {
		//Do checks here
		reason.append(String.format("No paying customers available >>%s<<", name));
		return false;
	}
}

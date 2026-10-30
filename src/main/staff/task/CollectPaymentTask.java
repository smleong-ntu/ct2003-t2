package main.staff.task;

import main.staff.*;

public class CollectPaymentTask extends CashierTask{
	public CollectPaymentTask(String name, Cashier cashier) {
		super(name, cashier);
	}
	
	public void execute() {
		super.execute();
		System.out.printf("Collect Payment done by ", this.getClass().getSimpleName(), name);
	}
	
	public boolean isTaskValid() {
		//Do checks here
		return true;
	}
}

package main.staff.task;

import main.staff.*;

public class CollectPaymentTask extends CashierTask{
	public CollectPaymentTask(String name, Cashier cashier) {
		super(name, cashier);
	}
	
	@Override
	public void execute(int turn) {
		super.execute(turn);
		System.out.printf("Collect Payment done by ", this.getClass().getSimpleName(), name);
	}
	
	public boolean isTaskValid() {
		//Do checks here
		return true;
	}
}

package main.staff.task;

import main.staff.*;

public abstract class CashierTask extends Task{
	
	//access to cashier here
	protected Cashier cashier;
		
	public CashierTask(String name, Cashier cashier) {
		super(name);
		this.cashier = cashier;
	}
}

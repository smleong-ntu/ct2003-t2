package main.staff.task;

import java.util.List;
import java.util.EnumSet;

public abstract class Task {
		
	/*
	 * Pending allocation of the following items
	 * Item may not have been created, is currently vacant or has limited quantity / spots
	 */
	public static enum Pending {
		ORDER, 		//creates a new order
		TABLE,		//takes a free table
		CUSTOMER,	//takes a customer from the queue
		PREPARE,	//takes an order to prepare
		SERVE,		//takes an order that is ready
		PAYMENT		//collecting payment from customer
	}
	
	protected final String name;
	protected EnumSet<Pending> pending;
	
	public Task(String name) {
		this.name = name;
		setPendingInfo();
	}
	
	public void execute(int turn) {
		System.out.printf("Executing %s: ", this.getClass().getSimpleName());
	}
		
	/*this information can determine choices that was already set before current task*/
	public boolean isTaskValid(StringBuilder reason, List<Task> taskQueue) { return true; }
	public String taskName() { return name; }
	
	/*
	 * Allows each task to set their own pending flags for future tasks to see in the same turn
	 */
	protected void setPendingInfo() {
		pending = EnumSet.noneOf(Pending.class);
	}
	
	/*
	 * Returns a copy of pending info to ensure that it does not get modified
	 */
	public EnumSet<Pending> getPendingInfo(){
		return EnumSet.copyOf(pending);
	}
}

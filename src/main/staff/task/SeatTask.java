package main.staff.task;

import main.gameflow.GameSession;
import main.restaurant.*;

import java.util.List;

import main.customer.*;
import main.staff.Waiter;
import main.staff.task.Task.Pending;

public class SeatTask extends WaiterTask{
	
	public SeatTask(String name, Waiter waiter) {
		super(name, waiter);
	}
	
	@Override
	public void execute(int turn) {
		super.execute(turn);
		
		Restaurant restaurant = GameSession.session().restaurant();
		Customer c = waiter.findWaitingCustomer();
		Table t = waiter.findAvailableTable();
		waiter.seatCustomer(restaurant, c, t);
	}
	
	@Override
	public boolean isTaskValid(StringBuilder reason, List<Task> taskQueue) {
		
		if (GameSession.session().restaurant().getWaitingQueue().size() == 0) {
			
			reason.append(String.format(">>%s<< No customer to seat",name));
			return false;
		}
		
		//If there is a customer, without table, cannot seat customer
		if (GameSession.session().restaurant().getFreeTables().size() == 0) {
			
			reason.append(String.format(">>%s<< No free tables to seat customer", name));
			return false;
		}
		
		//There is a customer in queue AND an available table
		return true;
	}
	
	@Override
	protected void setPendingInfo() {
		super.setPendingInfo();
		pending.add(Pending.CUSTOMER);
		pending.add(Pending.TABLE);
	}
}

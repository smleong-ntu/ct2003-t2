package main.staff.task;

import java.util.List;

import main.customer.Customer;
import main.restaurant.Table;
import main.gameflow.GameSession;
import main.staff.Host;

public class SeatAutoTask extends HostTask{
	public SeatAutoTask(String name, Host host) {
		super(name, host);
	}
	
	//How do you make this task execution to be overriden?
	//What happens in this task?
	
	//What makes this task valid or invalid?
	//What is the reason why it is invalid?
	
	@Override
	public void execute(int turn) {
	    super.execute(turn);

	    Customer customer = host.findWaitingCustomer();
	    Table table = host.findAvailableTable();

	    if (customer != null && table != null) {
	        GameSession.session()
	                   .restaurant()
	                   .seatCustomer(customer, table);

	        customer.seatAtTable(table.getTableNumber());
	    }
	}
	
	@Override
	public boolean isTaskValid(StringBuilder reason, List<Task> taskQueue) {

	    if (GameSession.session().restaurant().getWaitingQueue().size() == 0) {

	        reason.append(String.format(
	                ">>%s<< No customer to seat",
	                name));

	        return false;
	    }

	    if (GameSession.session().restaurant().getFreeTables().size() == 0) {

	        reason.append(String.format(
	                ">>%s<< No free tables to seat customer",
	                name));

	        return false;
	    }

	    return true;
	}
	
	@Override
	protected void setPendingInfo() {
		super.setPendingInfo();
		pending.add(Pending.CUSTOMER);
		pending.add(Pending.TABLE);
	}
}

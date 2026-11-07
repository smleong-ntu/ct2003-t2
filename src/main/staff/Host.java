package main.staff;

import java.util.List;

import main.customer.Customer;
import main.restaurant.Table;
import main.staff.task.TaskType;

public class Host extends Staff{
	
	//How does the host choose the customer from queue to seat?
	public Customer findWaitingCustomer() {

	    List<Customer> waitingCustomers =
	            restaurant.getWaitingQueue()
	                      .getCustomers();

	    if (waitingCustomers.isEmpty()) {
	        return null;
	    }

	    return waitingCustomers.get(0);
	}
	
	//How does host choose a table from queue to seat?
	public Table findAvailableTable() {

	    List<Table> freeTables =
	            restaurant.getFreeTables();

	    if (freeTables.isEmpty()) {
	        return null;
	    }

	    return freeTables.get(0);
	}
	
	/*Override the setting on each staff type*/
	@Override
    protected TaskType.Setting getTaskType() {
        return TaskType.HOST;
    }
}

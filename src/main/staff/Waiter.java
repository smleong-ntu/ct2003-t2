package main.staff;

import main.staff.task.*;

public class Waiter extends Staff {
	
    public void seatCustomer() {
        // Seat customer at available table
    }

    public void takeOrder() {
        // Take customer order
    }

    public void updateOrder() {
        // Update existing order
    }

    public void serveOrder() {
        // Serve ready order to customer
    }
    
    @Override
    protected TaskType.Setting getTaskType(){
    	return TaskType.WAITER;
    }
}

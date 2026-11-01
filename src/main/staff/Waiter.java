package main.staff;

import java.util.List;

import main.customer.Customer;
import main.menu.MenuItem;
import main.order.Order;
import main.restaurant.Restaurant;
import main.restaurant.Table;
import main.staff.task.TaskType;


public class Waiter extends Staff {

	/*
	 * Get the next customer waiting to be seated.
	 *
	 * WaitingQueue follows FIFO
	 * (First In First Out).
	 *
	 * The first customer in the queue
	 * should be seated first.
	 *
	 * Returns null if nobody is waiting.
	 */
	public Customer findWaitingCustomer() {

		List<Customer> waitingCustomers =
		        restaurant.getWaitingQueue()
		                  .getCustomers();

	    if (waitingCustomers.isEmpty()) {

	        return null;
	    }

	    // Oldest customer in the queue
	    return waitingCustomers.get(0);
	}
	
	/*
	 * Get the next available table.
	 *
	 * Restaurant already tracks which tables
	 * are currently free.
	 *
	 * Returns the first available table.
	 * Returns null if all tables are occupied.
	 */
	public Table findAvailableTable() {

		List<Table> freeTables =
		        restaurant.getFreeTables();

	    if (freeTables.isEmpty()) {

	        return null;
	    }

	    return freeTables.get(0);
	}


    /*
     * Get the next READY order to serve.
     *
     * Restaurant already provides READY orders
     * in the correct serving order.
     *
     * According to Restaurant.java:
     * READY orders are returned oldest first.
     *
     * Returns null if no READY orders exist.
     */
    public Order findReadyOrder() {

    	List<Order> readyOrders =
    	        restaurant.getReadyOrders();

        if (readyOrders.isEmpty()) {

            return null;
        }

        // Serve the oldest READY order first
        return readyOrders.get(0);
    }

    /*
     * Seat a customer at a table.
     * Returns true if successful.
     * Returns false if any validation fails.
     */
    public boolean seatCustomer(
            Restaurant restaurant,
            Customer customer,
            Table table) {

        // Basic null checks
        if (restaurant == null
                || customer == null
                || table == null) {

            return false;
        }

        try {
        	
        	//Informs restaurant of new customer mapping to table
        	//Makes the customer actually sit at the reserved table
            restaurant.seatCustomer(customer, table);
            customer.seatAtTable(table.getTableNumber());

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    /*
     * Create an order through Restaurant.
     * Returns the new Order object.
     * Returns null if order creation fails.
     */
    public boolean takeOrder(
            Restaurant restaurant,
            Customer customer,
            MenuItem item,
            int turn) {

        if (restaurant == null
                || customer == null
                || item == null) {

            return false;
        }

        try {

            Order order = restaurant.placeOrder(
                    customer,
                    item,
                    turn);
            
            customer.placeOrder(order);
            
            return true;

        } catch (Exception e) {

            return false;
        }
    }

    /*
     * Placeholder for future enhancements.
     */
    public void updateOrder() {
        // Future implementation
    }

    /*
     * Serve a READY order.
     * Returns true if serving succeeds.
     * Returns false if the order is invalid.
     */
    public boolean serveOrder(Order order) {

        if (order == null) {

            return false;
        }

        try {

            order.markServed();
            order.getOwner().receiveOrder();

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    @Override
    protected TaskType.Setting getTaskType() {

        return TaskType.WAITER;
    }
}
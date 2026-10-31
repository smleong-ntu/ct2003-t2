package main.staff;

import java.util.List;

import main.customer.Customer;
import main.customer.CustomerState;
import main.menu.MenuItem;
import main.order.Order;
import main.restaurant.Restaurant;
import main.restaurant.Table;
import main.staff.task.TaskType;

public class Waiter extends Staff {

    /*
     * Find the first customer still waiting in the queue.
     * Returns null if everybody has already been seated.
     */
    public Customer findWaitingCustomer(
            List<Customer> customers) {

        for (Customer customer : customers) {

            // Only customers in QUEUING state
            // are waiting for a table
            if (customer.getState() ==
                    CustomerState.QUEUING) {

                return customer;
            }
        }

        return null;
    }

    /*
     * Find the first free table.
     * Returns null if all tables are occupied.
     */
    public Table findAvailableTable(
            List<Table> tables) {

        for (Table table : tables) {

            if (table.isFree()) {

                return table;
            }
        }

        return null;
    }

    /*
     * Find the first order that is READY to be served.
     * Returns null if no order is ready.
     */
    public Order findReadyOrder(
            List<Order> orders) {

        for (Order order : orders) {

            if (order.isReady()) {

                return order;
            }
        }

        return null;
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

            restaurant.seatCustomer(
                    customer,
                    table);

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
    public Order takeOrder(
            Restaurant restaurant,
            Customer customer,
            MenuItem item,
            int turn) {

        if (restaurant == null
                || customer == null
                || item == null) {

            return null;
        }

        try {

            return restaurant.placeOrder(
                    customer,
                    item,
                    turn);

        } catch (Exception e) {

            return null;
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
    public boolean serveOrder(
            Order order) {

        if (order == null) {

            return false;
        }

        try {

            order.markServed();

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
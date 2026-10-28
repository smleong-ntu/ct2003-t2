package main.restaurant;

import main.customer.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * First-in, first-out queue of customers waiting for a table.
 * enqueue() and remove() are package-private: other packages go through
 * Restaurant.addArrival, seatCustomer and applyEndOfTurnWaiting.
 */
public class WaitingQueue {

    private final List<Customer> customers = new ArrayList<>();

    /**
     * Adds a customer at the back of the queue.
     *
     * @throws IllegalArgumentException if the customer is null
     * @throws IllegalStateException if the customer is already in the queue
     */
    void enqueue(Customer c) {
        if (c == null) {
            throw new IllegalArgumentException("Cannot queue a null customer");
        }
        if (contains(c)) {
            throw new IllegalStateException("Customer is already in the waiting queue");
        }
        customers.add(c);
    }

    /** Removes the customer and returns true, or returns false if they were not queued. */
    boolean remove(Customer c) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i) == c) {
                customers.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean contains(Customer c) {
        for (Customer queued : customers) {
            if (queued == c) {
                return true;
            }
        }
        return false;
    }

    /** Read-only view, oldest first. */
    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    public int size() {
        return customers.size();
    }

    public boolean isEmpty() {
        return customers.isEmpty();
    }
}

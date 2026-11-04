package main.staff;

import java.util.List;
import main.customer.Customer;
import main.staff.task.TaskType;

/** Cashier: automatically handles at most one payment per turn. */
public class Cashier extends Staff {
    private int lastPaymentTurn = -1;

    /**
     * Finds the customer who became ready earliest. Ties use the smaller ID.
     * Integration: obtain this list from Restaurant.getPayableCustomers(turn).
     * Returns null if nobody is eligible yet.
     */
    public Customer findReadyCustomer(List<Customer> customers, int turn) {
        Customer oldest = null;
        for (Customer customer : customers) {
            if (!customer.canPay(turn)) {
                continue;
            }
            if (oldest == null
                    || customer.getReadyToPayTurn() < oldest.getReadyToPayTurn()
                    || (customer.getReadyToPayTurn() == oldest.getReadyToPayTurn()
                        && customer.getCustomerId() < oldest.getCustomerId())) {
                oldest = customer;
            }
        }
        return oldest;
    }

    /**
     * Marks the customer PAID and has the Restaurant charge the order's locked price,
     * which already includes any discount (VIP 10% off). Returns true only for a
     * successful payment.
     */
    public boolean handlePayment(Customer customer, int turn) {
        if (turn < 1 || turn <= lastPaymentTurn
                || customer == null || !customer.canPay(turn)) {
            return false;
        }
        customer.markPaid();
        restaurant.recordPayment(customer);
        lastPaymentTurn = turn;
        return true;
    }
    
    @Override
    protected TaskType.Setting getTaskType(){
    	return TaskType.CASHIER;
    }
}

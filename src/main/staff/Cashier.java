package main.staff;

import java.util.List;
import main.customer.Customer;

/** Stage 1 cashier: automatically handles at most one payment per turn. */
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
     * Returns true only for a successful payment. The caller then records
     * customer.getLockedPriceCents() as revenue, exactly once. The mock demo
     * does this locally; the real Restaurant will own the payment transaction.
     * Stage 1 uses full price: no VIP or Happy Hour discounts.
     */
    public boolean handlePayment(Customer customer, int turn) {
        if (turn < 1 || turn <= lastPaymentTurn
                || customer == null || !customer.canPay(turn)) {
            return false;
        }
        customer.markPaid();
        lastPaymentTurn = turn;
        return true;
    }
}

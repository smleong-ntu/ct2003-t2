package main.customer;

import main.menu.Money;

/**
 * Stage 2 VIP customer: loses 5 satisfaction per waiting turn and receives
 * 10% off the bill when the order price is locked.
 */
public class VIPCustomer extends Customer {

    public VIPCustomer(int customerId) {
        super(customerId);
    }

    @Override
    public int getWaitingLoss() {
        return 5;
    }

    @Override
    public int adjustBill(int cents) {
        return Money.percentOf(cents, 90);
    }
}

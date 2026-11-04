package main.customer;

import main.menu.Menu;
import main.menu.MenuItem;

/**
 * Stage 2 critic: loses 12 satisfaction per waiting turn, pays full price,
 * and always chooses the most expensive base dish (Pasta in the base menu).
 * Price ties follow the menu's registration order.
 */
public class CriticCustomer extends Customer {

    public CriticCustomer(int customerId) {
        super(customerId);
    }

    @Override
    public int getWaitingLoss() {
        return 12;
    }

    @Override
    public MenuItem chooseMenuItem(Menu menu) {
        return menu.getMostExpensive();
    }
}

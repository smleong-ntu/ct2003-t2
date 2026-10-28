package main.order;

import main.customer.Customer;
import main.menu.MenuItem;
import main.menu.Money;

/**
 * One customer's order: what was ordered, kitchen progress, status and locked price.
 * <p>
 * The price is worked out by {@code Restaurant.placeOrder} and passed in.
 * It has no setter, because the brief says
 * "The price is locked when the order is taken."
 * <p>
 * Every method that changes status checks the current status and throws
 * IllegalStateException with a readable reason if the move is not allowed.
 * The UI can show that message and let the manager choose again
 * (brief, Section 3.4).
 */
public class Order {

    private final MenuItem menuItem;
    private final Customer owner;
    private final int lockedPriceCents;
    private final int placedOnTurn;

    private OrderStatus status;
    private int unitsRemaining;

    /**
     * @param menuItem         the dish
     * @param owner            the customer who placed it
     * @param lockedPriceCents the amount charged when this order is paid
     * @param placedOnTurn     turn the Waiter took the order (1-based)
     */
    public Order(MenuItem menuItem, Customer owner, int lockedPriceCents, int placedOnTurn) {
        if (menuItem == null) {
            throw new IllegalArgumentException("An order needs a menu item");
        }
        if (owner == null) {
            throw new IllegalArgumentException("An order needs a customer");
        }
        if (lockedPriceCents < 0) {
            throw new IllegalArgumentException("Price cannot be negative: " + lockedPriceCents);
        }
        if (placedOnTurn < 1) {
            throw new IllegalArgumentException("Turn numbers start at 1: " + placedOnTurn);
        }
        this.menuItem = menuItem;
        this.owner = owner;
        this.lockedPriceCents = lockedPriceCents;
        this.placedOnTurn = placedOnTurn;
        this.status = OrderStatus.PLACED;
        this.unitsRemaining = menuItem.getPrepUnits();
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public Customer getOwner() {
        return owner;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getUnitsRemaining() {
        return unitsRemaining;
    }

    public int getLockedPriceCents() {
        return lockedPriceCents;
    }

    public int getPlacedOnTurn() {
        return placedOnTurn;
    }

    /**
     * The Chef completes one preparation unit.
     * PLACED becomes IN_PREPARATION; when the last unit is done the order becomes READY.
     *
     * @throws IllegalStateException if the order is not in the kitchen
     */
    public void advancePreparation() {
        if (!isInKitchen()) {
            throw new IllegalStateException(
                    "Cannot prepare " + menuItem.getName() + ": the order is " + status);
        }
        status = OrderStatus.IN_PREPARATION;
        unitsRemaining--;
        if (unitsRemaining == 0) {
            status = OrderStatus.READY;
        }
    }

    /**
     * The Waiter serves the dish. Only valid when READY.
     * The rule "a dish finished this turn is served next turn at the earliest"
     * comes from the turn order (Waiter acts before Chef), not from this class.
     *
     * @throws IllegalStateException if the order is not READY
     */
    public void markServed() {
        if (status != OrderStatus.READY) {
            throw new IllegalStateException(
                    "Cannot serve " + menuItem.getName() + ": it is " + status + ", not READY");
        }
        status = OrderStatus.SERVED;
    }

    /**
     * The Cashier has collected payment. Only valid when SERVED.
     * (Not in the original diagram; required by the brief, Section 3.2 step 4.)
     *
     * @throws IllegalStateException if the order has not been served
     */
    public void markPaid() {
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException(
                    "Cannot charge for " + menuItem.getName() + ": it is " + status + ", not SERVED");
        }
        status = OrderStatus.PAID;
    }

    /**
     * Cancels the order because its customer left (brief, Section 3.2 step 5:
     * "cancel any unfinished order"). Valid from PLACED, IN_PREPARATION or READY.
     * Calling it again on a CANCELLED order does nothing, so two classes can both
     * call it safely.
     *
     * @throws IllegalStateException if the order was already SERVED or PAID
     */
    public void cancel() {
        if (status == OrderStatus.CANCELLED) {
            return;
        }
        if (status == OrderStatus.SERVED || status == OrderStatus.PAID) {
            throw new IllegalStateException(
                    "Cannot cancel " + menuItem.getName() + ": it was already " + status);
        }
        status = OrderStatus.CANCELLED;
    }

    public boolean isReady() {
        return status == OrderStatus.READY;
    }

    /** True while the Chef can still work on it (PLACED or IN_PREPARATION). */
    public boolean isInKitchen() {
        return status == OrderStatus.PLACED || status == OrderStatus.IN_PREPARATION;
    }

    /** True once the order can no longer change (PAID or CANCELLED). */
    public boolean isClosed() {
        return status == OrderStatus.PAID || status == OrderStatus.CANCELLED;
    }

    /** e.g. "Pasta [IN_PREPARATION, 1 unit left, $18.00]". */
    @Override
    public String toString() {
        String progress = isInKitchen()
                ? ", " + unitsRemaining + " unit" + (unitsRemaining == 1 ? "" : "s") + " left"
                : "";
        return String.format("%s [%s%s, %s]",
                menuItem.getName(), status, progress, Money.format(lockedPriceCents));
    }
}

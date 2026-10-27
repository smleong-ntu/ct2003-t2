package main.customer;

import java.util.List;

/**
 * A Stage 1 regular customer. Rules stay here rather than in the user interface.
 * Table numbers, item names and prices temporarily replace the team's pending
 * Table, MenuItem and Order classes. See INTEGRATION.md before linking systems.
 */
public class Customer {
    private final int customerId;
    private int satisfaction = 100;
    private CustomerState state = CustomerState.QUEUING;
    private int tableId = -1;
    private String menuItemName;
    private int lockedPriceCents;
    private int servedTurn = -1;
    private int readyToPayTurn = -1;
    private boolean orderCancelled;

    public Customer(int customerId) {
        if (customerId < 1) {
            throw new IllegalArgumentException("Customer ID must be positive.");
        }
        this.customerId = customerId;
    }

    /** Called by seating logic only after it has reserved a free table. */
    public boolean seatAtTable(int tableId) {
        if (state != CustomerState.QUEUING || tableId < 1) {
            return false;
        }
        this.tableId = tableId;
        state = CustomerState.SEATED;
        return true;
    }

    /** Deterministic choice: customer 1 picks item 1, 2 picks item 2, etc. */
    public String chooseMenuItem(List<String> menu) {
        if (menu == null || menu.isEmpty()) {
            throw new IllegalArgumentException("The menu must have items.");
        }
        return menu.get((customerId - 1) % menu.size());
    }

    /** Called when the Waiter records this customer's chosen item and price. */
    public boolean placeOrder(String itemName, int priceCents) {
        if (state != CustomerState.SEATED || itemName == null
                || itemName.isBlank() || priceCents < 0) {
            return false;
        }
        menuItemName = itemName;
        lockedPriceCents = priceCents;
        state = CustomerState.WAITING_FOR_FOOD;
        return true;
    }

    /** Waiter must check the real Order is READY before calling this method. */
    public boolean receiveOrder(int turn) {
        if (state != CustomerState.WAITING_FOR_FOOD || turn < 1) {
            return false;
        }
        servedTurn = turn;
        state = CustomerState.EATING;
        return true;
    }

    /** Dining step: a customer cannot finish eating on the serving turn. */
    public void finishEating(int turn) {
        if (state == CustomerState.EATING && turn > servedTurn) {
            readyToPayTurn = turn;
            state = CustomerState.READY_TO_PAY;
        }
    }

    /** A newly ready customer must wait until the following turn to pay. */
    public boolean canPay(int turn) {
        return state == CustomerState.READY_TO_PAY && turn > readyToPayTurn;
    }

    /** Called by Cashier after checking canPay(turn). */
    public void markPaid() {
        if (state != CustomerState.READY_TO_PAY) {
            throw new IllegalStateException("Customer is not ready to pay.");
        }
        state = CustomerState.PAID;
        tableId = -1;
        // Integration: Restaurant must also mark the real Order PAID,
        // free the real Table and record satisfaction at payment time.
    }

    /** Applies Stage 1 waiting loss once at the end of each game turn. */
    public void endTurn() {
        if (isWaitingForService()) {
            updateSatisfaction(-8);
            if (satisfaction == 0) {
                leaveUnpaid();
            }
        }
    }

    /** Keeps satisfaction in the inclusive range 0 to 100. */
    public void updateSatisfaction(int change) {
        satisfaction = (int) Math.max(0L, Math.min(100L, (long) satisfaction + change));
    }

    /** Removes an unserved customer; actual Order/Table cleanup is pending. */
    public void leaveUnpaid() {
        if (!isWaitingForService()) {
            return;
        }
        orderCancelled = menuItemName != null;
        tableId = -1;
        state = CustomerState.LEFT;
        // Integration: Restaurant.handleDeparture must cancel the real Order,
        // release the real Table and remove this customer from the queue.
    }

    private boolean isWaitingForService() {
        return state == CustomerState.QUEUING || state == CustomerState.SEATED
                || state == CustomerState.WAITING_FOR_FOOD;
    }

    public int getCustomerId() { return customerId; }
    public int getSatisfaction() { return satisfaction; }
    public CustomerState getState() { return state; }
    public int getTableId() { return tableId; }
    public String getMenuItemName() { return menuItemName; }
    public int getLockedPriceCents() { return lockedPriceCents; }
    public int getServedTurn() { return servedTurn; }
    public int getReadyToPayTurn() { return readyToPayTurn; }
    public boolean isOrderCancelled() { return orderCancelled; }
}

package main.customer;

import java.util.List;
import main.menu.*;

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

    private CustomerState pendingTransition = null;
    
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
    public MenuItem chooseMenuItem(Menu menu) {
    	
    	if (menu == null || menu.getAllItems().size() == 0) {
            throw new IllegalArgumentException("The menu must have items.");
        }
        return menu.getAllItems().get((customerId - 1) % menu.getAllItems().size());
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

    //REVIEW: Intermediate state to decouple from turns
    /** Waiter must check the real Order is READY before calling this method. */
    public boolean receiveOrder(int turn) {
        if (state != CustomerState.WAITING_FOR_FOOD || turn < 1) {
            return false;
        }
        servedTurn = turn;
        
        //Whenever need to go to transition state, will need to call state to pending
        state = CustomerState.PENDING;
        pendingTransition = CustomerState.EATING;
        
        return true;
    }

  //REVIEW: Intermediate state to decouple from turns
    /** Dining step: a customer cannot finish eating on the serving turn. */
    public void finishEating(int turn) {
        if (state == CustomerState.EATING/* && turn > servedTurn*/) {
        	
        	//By using a intermediate state, tasks can validate 
        	//if this customer is able to pay or not independent of turn
        	//can change the name of state if needed
        	state = CustomerState.PENDING;
        	
        	//transition to this state automatically in the next turn
        	pendingTransition = CustomerState.READY_TO_PAY; //
        	
        	//readyToPayTurn = turn;
        	//state = CustomerState.READY_TO_PAY;
        }
    }

    /** A newly ready customer must wait until the following turn to pay. */
    public boolean canPay(int turn) {
    	//REVIEW: Decouple state and turns
    	return state == CustomerState.READY_TO_PAY/* && turn > readyToPayTurn*/;
        //return state == CustomerState.READY_TO_PAY && turn > readyToPayTurn;
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
    
    //REVIEW: Represents a "turn" that has passed
    //Added this to allow transitions to new states when a new turn begins
    //Equivalent to "startTurn", rename if desired
    public void tick() {
    	//skip this since customer no longer part of turn order
    	if (state == CustomerState.LEFT) {
    		return; 
    	}
    	
    	if (pendingTransition != null) {
    		state = pendingTransition;
    		System.out.printf("Transition customer [%d] -> [%s]%n", customerId, state.toString());
    		pendingTransition = null;
    		return;
    	}
    	
    	System.out.printf("No transition pending for customer [%d]%n", customerId);
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

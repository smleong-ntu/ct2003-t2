package main.restaurant;

import main.customer.Customer;

/**
 * A table that holds at most one seated customer.
 * The occupant only changes through seat() and vacate(), so a table can never be double-seated.
 * Both are package-private: other packages seat and free tables through Restaurant,
 * which keeps the waiting queue, orders and tables in step.
 */
public class Table {

    private final int tableNumber;
    private Customer occupant;   // null when free

    public Table(int tableNumber) {
        if (tableNumber < 1) {
            throw new IllegalArgumentException("Table numbers start at 1: " + tableNumber);
        }
        this.tableNumber = tableNumber;
        this.occupant = null;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    /** The seated customer, or null if the table is free. */
    public Customer getOccupant() {
        return occupant;
    }

    public boolean isFree() {
        return occupant == null;
    }

    /**
     * @throws IllegalArgumentException if c is null
     * @throws IllegalStateException    if the table is already occupied
     */
    void seat(Customer c) {
        if (c == null) {
            throw new IllegalArgumentException("Cannot seat a null customer");
        }
        if (!isFree()) {
            throw new IllegalStateException("Table " + tableNumber + " is already occupied");
        }
        occupant = c;
    }

    /** @throws IllegalStateException if the table is already free */
    void vacate() {
        if (isFree()) {
            throw new IllegalStateException("Table " + tableNumber + " is already free");
        }
        occupant = null;
    }

    /** e.g. "Table 1: free" or "Table 2: " followed by the customer's own toString(). */
    @Override
    public String toString() {
        return "Table " + tableNumber + ": " + (isFree() ? "free" : occupant.toString());
    }
}

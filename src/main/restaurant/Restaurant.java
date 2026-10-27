package main.restaurant;

import main.customer.Customer;
import main.customer.CustomerState;
import main.menu.Menu;
import main.menu.MenuItem;
import main.order.Order;
import main.order.OrderStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Aggregate root: owns the tables, waiting queue, menu, orders, revenue and every
 * customer who has arrived. Paid and departed customers are worked out from each
 * customer's own state rather than kept in separate lists.
 * <p>
 * It has NO turn-sequencing logic; TurnManager and the turn steps decide what happens and when.
 * Each method here keeps the restaurant consistent, so callers cannot, for example,
 * add revenue without recording a payment, or leave a departed customer at a table.
 */
public class Restaurant {

    private final List<Table> tables;
    private final WaitingQueue waitingQueue;
    private final Menu menu;
    private final List<Order> orders = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();   // every arrival, in arrival order
    private final List<Integer> paidSatisfactions = new ArrayList<>();   // recorded at payment time
    private int revenueCents;

    /** Creates a restaurant with the given number of tables, an empty queue, the base menu and $0. */
    public Restaurant(int tableCount) {
        if (tableCount < 1) {
            throw new IllegalArgumentException("A restaurant needs at least 1 table: " + tableCount);
        }
        List<Table> created = new ArrayList<>();
        for (int i = 1; i <= tableCount; i++) {
            created.add(new Table(i));
        }
        this.tables = Collections.unmodifiableList(created);
        this.waitingQueue = new WaitingQueue();
        this.menu = new Menu();
        this.revenueCents = 0;
    }

    // ------------------------------------------------------------------ arrivals

    /**
     * Registers a newly arrived customer and puts them at the back of the waiting queue.
     *
     * @throws IllegalArgumentException if the customer is null
     * @throws IllegalStateException    if the customer already arrived or is not queuing
     */
    public void addArrival(Customer c) {
        if (c == null) {
            throw new IllegalArgumentException("Cannot add a null customer");
        }
        if (customers.contains(c)) {
            throw new IllegalStateException("This customer has already arrived");
        }
        if (c.getState() != CustomerState.QUEUING) {
            throw new IllegalStateException(
                    "A new arrival must be queuing, but this customer is " + c.getState());
        }
        customers.add(c);
        waitingQueue.enqueue(c);
    }

    // ------------------------------------------------------------------ tables

    public List<Table> getTables() {
        return tables;
    }

    public List<Table> getFreeTables() {
        List<Table> free = new ArrayList<>();
        for (Table t : tables) {
            if (t.isFree()) {
                free.add(t);
            }
        }
        return Collections.unmodifiableList(free);
    }

    public List<Table> getOccupiedTables() {
        List<Table> occupied = new ArrayList<>();
        for (Table t : tables) {
            if (!t.isFree()) {
                occupied.add(t);
            }
        }
        return Collections.unmodifiableList(occupied);
    }

    /** The table this customer sits at, if any. (Addition: needed for payment and departures.) */
    public Optional<Table> getTableOf(Customer c) {
        for (Table t : tables) {
            if (!t.isFree() && t.getOccupant() == c) {
                return Optional.of(t);
            }
        }
        return Optional.empty();
    }

    /**
     * Seats a queued customer at a free table and removes them from the waiting queue.
     * Does not change the customer's own state; the staff member who seats them does that.
     *
     * @throws IllegalArgumentException if the table is not part of this restaurant
     * @throws IllegalStateException    if the customer is not queued, or the table is occupied
     */
    public void seatCustomer(Customer c, Table t) {
        requireOwnTable(t);
        if (!waitingQueue.contains(c)) {
            throw new IllegalStateException("Only a customer in the waiting queue can be seated");
        }
        t.seat(c);   // throws if occupied
        waitingQueue.remove(c);
    }

    // ------------------------------------------------------------------ orders (addition)

    /**
     * Creates, prices and registers the order the Waiter takes. The price is locked here.
     * Does not change the customer's own state.
     *
     * @throws IllegalArgumentException if the customer or item is null, the item is not on
     *                                  the menu, or the turn is below 1
     * @throws IllegalStateException    if the customer has not arrived, is not seated,
     *                                  or already has an open order
     */
    public Order placeOrder(Customer c, MenuItem item, int turn) {
        if (c == null || item == null) {
            throw new IllegalArgumentException("An order needs a customer and a menu item");
        }
        if (!customers.contains(c)) {
            throw new IllegalStateException("This customer has not arrived");
        }
        if (getTableOf(c).isEmpty()) {
            throw new IllegalStateException("Only a seated customer can order");
        }
        if (!menu.getAllItems().contains(item)) {
            throw new IllegalArgumentException("\"" + item.getName() + "\" is not on the menu");
        }
        int priceCents = item.getPriceCents();
        Order order = new Order(item, c, priceCents, turn);
        addOrder(order);
        return order;
    }

    private void addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Cannot add a null order");
        }
        Optional<Order> existing = getOrderOf(order.getOwner());
        if (existing.isPresent() && !existing.get().isClosed()) {
            throw new IllegalStateException("This customer has already ordered: " + existing.get());
        }
        orders.add(order);
    }

    /** Every order placed this game, oldest first (read-only). */
    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }

    /** The customer's most recent order, if any. */
    public Optional<Order> getOrderOf(Customer c) {
        for (int i = orders.size() - 1; i >= 0; i--) {
            if (orders.get(i).getOwner() == c) {
                return Optional.of(orders.get(i));
            }
        }
        return Optional.empty();
    }

    /** Orders the Chef can work on (PLACED or IN_PREPARATION), oldest first. */
    public List<Order> getOrdersInKitchen() {
        List<Order> result = new ArrayList<>();
        for (Order o : orders) {
            if (o.isInKitchen()) {
                result.add(o);
            }
        }
        return Collections.unmodifiableList(result);
    }

    /** Orders the Waiter can serve (READY), oldest first. */
    public List<Order> getReadyOrders() {
        List<Order> result = new ArrayList<>();
        for (Order o : orders) {
            if (o.isReady()) {
                result.add(o);
            }
        }
        return Collections.unmodifiableList(result);
    }

    /** Number of orders that have been served (SERVED or PAID), for the end-of-turn display. */
    public int getServedCount() {
        int count = 0;
        for (Order o : orders) {
            if (o.getStatus() == OrderStatus.SERVED || o.getStatus() == OrderStatus.PAID) {
                count++;
            }
        }
        return count;
    }

    // ------------------------------------------------------------------ money and results

    public WaitingQueue getWaitingQueue() {
        return waitingQueue;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getRevenueCents() {
        return revenueCents;
    }

    public List<Customer> getPaidCustomers() {
        List<Customer> paid = new ArrayList<>();
        for (Customer c : customers) {
            if (c.getState() == CustomerState.PAID) {
                paid.add(c);
            }
        }
        return Collections.unmodifiableList(paid);
    }

    public List<Customer> getDepartedCustomers() {
        List<Customer> departed = new ArrayList<>();
        for (Customer c : customers) {
            if (c.getState() == CustomerState.LEFT) {
                departed.add(c);
            }
        }
        return Collections.unmodifiableList(departed);
    }

    /**
     * Average satisfaction of paid customers, using the value recorded when each one paid.
     * Empty when nobody has paid yet, so the UI can show "N/A".
     */
    public OptionalDouble getAveragePaidSatisfaction() {
        return paidSatisfactions.stream().mapToInt(Integer::intValue).average();
    }

    private void addRevenue(int cents) {
        if (cents < 0) {
            throw new IllegalArgumentException("Revenue cannot decrease: " + cents);
        }
        revenueCents += cents;
    }

    /**
     * True if the restaurant side of a payment is in place: the customer has arrived, is
     * seated and has a SERVED order. Does not look at the customer's own state.
     * The Cashier calls this before {@code customer.markPaid()}, so a payment that
     * {@link #recordPayment(Customer)} would reject never marks the customer PAID.
     */
    public boolean canRecordPayment(Customer c) {
        if (c == null || !customers.contains(c) || getTableOf(c).isEmpty()) {
            return false;
        }
        Optional<Order> order = getOrderOf(c);
        return order.isPresent() && order.get().getStatus() == OrderStatus.SERVED;
    }

    /**
     * Records a payment the Cashier has already taken (customer state PAID): charges the
     * order's locked price, records the customer's satisfaction, marks the order PAID and
     * frees the table. The Cashier checks {@link #canRecordPayment(Customer)} first.
     *
     * @throws IllegalArgumentException if the customer is null
     * @throws IllegalStateException    if the customer has not arrived, is not PAID, is not
     *                                  seated, has no order, or the order is not SERVED
     */
    public void recordPayment(Customer c) {
        if (c == null) {
            throw new IllegalArgumentException("Cannot record payment for a null customer");
        }
        if (!customers.contains(c)) {
            throw new IllegalStateException("This customer has not arrived");
        }
        if (c.getState() != CustomerState.PAID) {
            throw new IllegalStateException("The Cashier has not taken this customer's payment yet");
        }
        Table table = getTableOf(c).orElseThrow(
                () -> new IllegalStateException("Only a seated customer can pay"));
        Order order = getOrderOf(c).orElseThrow(
                () -> new IllegalStateException("This customer has no order to pay for"));

        order.markPaid();   // throws if not SERVED; nothing has changed before this line
        addRevenue(order.getLockedPriceCents());
        paidSatisfactions.add(c.getSatisfaction());
        table.vacate();
    }

    /**
     * Applies the end-of-turn waiting loss to every queued or seated customer who has not been
     * served, and cleans up after anyone who leaves. Returns the customers who left.
     */
    public List<Customer> applyEndOfTurnWaiting() {
        List<Customer> left = new ArrayList<>();
        for (Customer c : new ArrayList<>(customers)) {
            CustomerState state = c.getState();
            if (state == CustomerState.QUEUING || state == CustomerState.SEATED
                    || state == CustomerState.WAITING_FOR_FOOD) {
                c.endTurn();
                if (c.getState() == CustomerState.LEFT) {
                    recordDeparture(c);
                    left.add(c);
                }
            }
        }
        return Collections.unmodifiableList(left);
    }

    /**
     * Records a customer leaving unhappy: cancels their unfinished order (if registered),
     * frees their table (if seated) and removes them from the waiting queue.
     * A SERVED order is finished, so it is left alone instead of failing the cancel,
     * which would stop the end-of-turn loop for every later customer.
     */
    private void recordDeparture(Customer c) {
        getOrderOf(c).filter(o -> o.isInKitchen() || o.isReady()).ifPresent(Order::cancel);
        getTableOf(c).ifPresent(Table::vacate);
        waitingQueue.remove(c);
    }

    // ------------------------------------------------------------------ helpers

    private void requireOwnTable(Table t) {
        if (t == null || !tables.contains(t)) {
            throw new IllegalArgumentException("That table does not belong to this restaurant");
        }
    }
}

package main.staff;

import main.customer.Customer;
import main.menu.MenuItem;
import main.order.Order;
import main.restaurant.Restaurant;
import main.restaurant.Table;
import main.staff.task.TaskType;

public class Waiter extends Staff {

    public void seatCustomer(
            Restaurant restaurant,
            Customer customer,
            Table table) {

        restaurant.seatCustomer(customer, table);
    }

    public Order takeOrder(
            Restaurant restaurant,
            Customer customer,
            MenuItem item,
            int turn) {

        return restaurant.placeOrder(customer, item, turn);
    }

    public void updateOrder() {
        // Future implementation
    }

    public void serveOrder(Order order) {

        order.markServed();
    }

    @Override
    protected TaskType.Setting getTaskType() {
        return TaskType.WAITER;
    }
}

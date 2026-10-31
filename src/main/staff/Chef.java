package main.staff;

import java.util.List;

import main.order.Order;
import main.staff.task.TaskType;

public class Chef extends Staff {

    /*
     * Find the first order still being prepared.
     * Returns null if no order requires cooking.
     */
    public Order findOrderToPrepare(
            List<Order> orders) {

        for (Order order : orders) {

            // Orders in kitchen are either
            // PLACED or IN_PREPARATION
            if (order.isInKitchen()) {

                return order;
            }
        }

        return null;
    }

    /*
     * Advance cooking progress by one unit.
     * Returns true if successful.
     * Returns false if cooking cannot proceed.
     */
    public boolean prepareOrder(
            Order order) {

        if (order == null) {

            return false;
        }

        try {

            // Order handles its own state changes:
            // PLACED -> IN_PREPARATION -> READY
            order.advancePreparation();

            return true;

        } catch (Exception e) {

            return false;
        }
    }

    @Override
    protected TaskType.Setting getTaskType() {

        return TaskType.CHEF;
    }
}
package main.staff;

import main.order.Order;
import main.staff.task.TaskType;

public class Chef extends Staff {

    public void prepareOrder(Order order) {

        order.advancePreparation();
    }

    public boolean markOrderReady(Order order) {

        return order.isReady();
    }

    @Override
    protected TaskType.Setting getTaskType() {
        return TaskType.CHEF;
    }
}
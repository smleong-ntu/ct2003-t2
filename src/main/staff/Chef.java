package main.staff;

import main.staff.task.TaskType;

public class Chef extends Staff {

    public void prepareOrder() {
        // Prepare selected order
    }

    public void markOrderReady() {
        // Mark completed order as READY
    }
    
    @Override
    protected TaskType.Setting getTaskType(){
    	return TaskType.CHEF;
    }
}
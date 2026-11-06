package main.staff;

import main.staff.task.*;

public class Host extends Staff{
	
	//How does the host choose the customer from queue to seat?
	
	//How does host choose a table from queue to seat?
	
	/*Override the setting on each staff type*/
	@Override
    protected TaskType.Setting getTaskType() {
        return TaskType.HOST;
    }
}

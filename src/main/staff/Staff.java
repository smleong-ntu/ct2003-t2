package main.staff;

import java.util.List;
import java.util.ArrayList;
import main.staff.task.*;

public abstract class Staff {
	
    private int staffID;
    private String name;
    protected List<Task> tasks = new ArrayList<>(); //holds all possible tasks
    
    public Staff() {
    	
    	//Each staff hold an entire copy of reference of a set of tasks
    	getTaskType().taskList.forEach(type -> {
    		tasks.add(type.apply(this));
    	});
    }
    
    public void waitTurn() {
        // No action for this turn
    }
    
    public final List<Task> getTasks() { return tasks; }
    public int getID() { return staffID; }
    
    /*Override the setting on each staff type*/
    protected TaskType.Setting getTaskType(){
    	return TaskType.WAITER;
    }
}	



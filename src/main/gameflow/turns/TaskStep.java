package main.gameflow.turns;

import main.gameflow.GameSession;
import main.gameflow.TurnManager;
import main.staff.Task;
import main.staff.TaskManager;

public class TaskStep extends TurnStep{
	
	TaskManager taskManager;
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		name = "Task execution";
		
		taskManager = GameSession.session().placeholderTaskManager();
	}
	
	@Override
	public void execute() {
		
		while (taskManager.numQueued() > 0) {
			Task t = taskManager.dequeue();
			
			t.execute();
			taskManager.addAsCompletedTask(t);
		}
		
		postExecuteAction();
	}
}

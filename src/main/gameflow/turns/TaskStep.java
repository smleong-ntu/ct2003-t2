package main.gameflow.turns;

import main.gameflow.TurnManager;

public class TaskStep extends TurnStep{
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		name = "Task execution";
	}
	
	@Override
	public void execute() {
		postExecuteAction();
	}
}

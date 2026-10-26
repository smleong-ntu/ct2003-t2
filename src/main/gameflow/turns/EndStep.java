package main.gameflow.turns;

import main.gameflow.TurnManager;

public class EndStep extends TurnStep{
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		name = "End";
	}
	
	@Override
	public void execute() {
		postExecuteAction();
	}
}

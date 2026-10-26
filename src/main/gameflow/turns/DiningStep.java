package main.gameflow.turns;

import main.gameflow.TurnManager;

public class DiningStep extends TurnStep{

	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		name = "Dining and Payment";
	}
	
	@Override
	public void execute() {
		postExecuteAction();
	}
}

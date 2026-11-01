package main.gameflow.turns;

import main.gameflow.*;

public class EndStep extends TurnStep{
		
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		name = "End";
		
		//save reference to restaurant to get customers
		//restaurant = GameSession.session().restaurant();
	}
	
	@Override
	public void execute() {
		
		//Get customers from restaurant
		//restaurant.applyEndOfTurnWaiting();
		
		postExecuteAction();
	}
}

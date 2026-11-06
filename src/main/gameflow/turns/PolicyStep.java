package main.gameflow.turns;

import main.gameflow.*;
import main.restaurant.*;

public class PolicyStep extends TurnStep{
	
	private Restaurant restaurant;
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		name = "Policy";
		
		//save reference to restaurant to get customers
		restaurant = GameSession.session().restaurant();
	}
	
	@Override
	public void execute() {
		
		//Get customers from restaurant
		//restaurant.applyEndOfTurnWaiting();
		
		postExecuteAction();
	}
}

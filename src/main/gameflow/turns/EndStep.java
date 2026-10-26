package main.gameflow.turns;

import main.gameflow.TurnManager;

public class EndStep extends TurnStep{
	
	//private final Restaurant restaurant;
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		name = "End";
		
		//save reference to restaurant to get customers
		//restaurant = GameSession().session().getRestaurant();
	}
	
	@Override
	public void execute() {
		
		//Get customers from restaurant
		
		//Tick allows the customer to move one "turn"
		//forEach(customer -> customer.Tick())
		
		postExecuteAction();
	}
}

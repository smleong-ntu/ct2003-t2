package main.gameflow.turns;

import main.gameflow.*;
import main.customer.*;
import main.restaurant.*;

public class ArrivalStep extends TurnStep {
	
	private Restaurant restaurant;
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		name = "Arrivals";
		
		//save reference to restaurant
		restaurant = GameSession.session().restaurant();
	}
	
	@Override
	public void execute() {
		
		//Customer id is set as turn number, could be anything really
		Customer customer = GameSession.data().arrivals().get(turn).apply(turn);
		
		if (customer == null) {
			System.out.printf("No customer [Turn %d]%n", turn);
		}
		
		else {
			System.out.printf("Customer arrival [Turn %d]%n", turn);
			
			//Push to restaurant queue
			restaurant.addArrival(customer);
		}

		postExecuteAction();
	}
}

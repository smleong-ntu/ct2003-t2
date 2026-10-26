package main.gameflow.turns;

import main.gameflow.*;

public class ArrivalStep extends TurnStep {
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		name = "Arrivals";
		
		//save reference to restaurant
	}
	
	@Override
	public void execute() {
		String customer = GameSession.data().arrivals().get(turn);
		
		if (customer != "None") {
			System.out.printf("Customer arrival [Turn %d]%n", turn);
			
			//Push to restaurant queue
		}
		
		postExecuteAction();
	}
}

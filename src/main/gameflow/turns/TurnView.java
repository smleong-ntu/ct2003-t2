package main.gameflow.turns;

import main.events.*;
import main.gameflow.GameSession;

public abstract class TurnView implements GameEventListener{
	protected int turn;
	protected int revenue;
	protected float satisfaction;
	
	protected abstract void display();
	
	protected void displayRevenue() {
		revenue = GameSession.session().restaurant().getRevenueCents();
		
		System.out.printf("Revenue: $%.2f%n", revenue / 100.0);
	}
	
	protected void displayAverageSatisfaction() {
		var avg = GameSession.session().restaurant().getAveragePaidSatisfaction();
		
		String displayValue = avg.isPresent() 
			    ? String.format("%.2f", avg.getAsDouble())
			    : "N/A";
		
		System.out.printf("Average Satisfaction (Paid Customers): %s%n", displayValue);
	}
	
	public void onEvent(GameEvent event) {}
}

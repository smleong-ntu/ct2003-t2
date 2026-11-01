package main.gameflow.turns;

import main.events.*;
import main.gameflow.GameSession;

//tracks cumulative turn information
//updates self information based on events rather than polling
public class TurnEndView extends TurnView {
	
	private int served;
	private int unhappyDepartures;
		
	protected void display() {
		System.out.println(">>-------------------------<<");
		System.out.printf("Turns Used: %d/%d%n", turn, GameSession.data().turns());
		System.out.printf("Total Served: %d%n", served);
		System.out.printf("Unhappy Departures: %d%n", unhappyDepartures);
		displayRevenue();
		displayAverageSatisfaction();
	}
	
	public void onEvent (GameEvent event) {
		switch (event) {
			case OnTurn.Start onTurnStart -> {turn = onTurnStart.turn();}
			case OnTurn.Completed onTurnCompleted -> { display(); }
			case OnRestaurant.Departure onDeparture -> { unhappyDepartures = onDeparture.departed(); }
			case OnRestaurant.ServedOrder onServed -> { served = onServed.count(); }
			default -> {}
		}
	}
}

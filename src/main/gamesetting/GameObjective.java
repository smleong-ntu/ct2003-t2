package main.gamesetting;

import main.events.*;
import main.gameflow.GameSession;

public class GameObjective implements GameEventListener {
	
	public static class Setting {
		public String name;
		public int paidCustomers;
		public int revenue;
		public int satisfaction;
	}
	
	private final String name;
	private final int paidCustomers;
	private final int revenue;
	private final int satisfaction;
	
	private boolean isCompleted = false;
	
	public GameObjective(Setting setting) {
		name = setting.name;
		paidCustomers = setting.paidCustomers;
		revenue = setting.revenue;
		satisfaction = setting.satisfaction;
	}
	
	public void display() {
		System.out.printf("Displaying Target Objective [%s]%n", name);
		System.out.printf("Paid Customers: %d%n", paidCustomers);
		System.out.printf("Revenue: %d%n", revenue);
		System.out.printf("Average Satisfaction: %d%n", satisfaction);
		System.out.println("End Display GameObjective Targets");
	}
	
	public void status() {
		System.out.printf("Objective [%s] completion status %b%n", name, isCompleted);
		System.out.printf("Total paid customers: %d/%d%n", paidCustomers, paidCustomers);
		System.out.printf("Total revenue: %d/%d%n", revenue, revenue);
		System.out.printf("Average satisfaction: %d/%d%n", satisfaction, satisfaction);
	}
	
	@Override
	public void onEvent(GameEvent event) {
		switch (event) {
			case OnTurn.End onTurnEnd -> {				
				//should be checking if it can be completed by referencing restaurant, it could be listening to restaurant instead probably
				if (onTurnEnd.turn() == GameSession.data().turns()) {
					isCompleted = true;
				}
			}
			
			default -> {
				//System.out.println("payload none: generic event");
			}
		}
	}
	
	public boolean isCompleted() { return isCompleted; }
}

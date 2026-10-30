package main.gamesetting;

import java.util.function.Function;
import main.events.*;
import main.gameflow.GameSession;

public class GameObjective implements GameEventListener {
	
	public static class Setting {
		public String name;
		public int paidCustomers;
		public int revenue;
		public float satisfaction;
	}
	
	private final String name;
	private final int paidCustomers;
	private final int revenue;
	private final float satisfaction;
	
	private Setting tracked;
	private boolean isCompleted = false;
	
	public GameObjective(Setting setting) {
		name = setting.name;
		paidCustomers = setting.paidCustomers;
		revenue = setting.revenue;
		satisfaction = setting.satisfaction;
		
		tracked = new Setting();
	}
	
	public void display() {
		System.out.printf("Displaying Target Objective [%s]%n", name);
		System.out.printf("Paid Customers: %d%n", paidCustomers);
		System.out.printf("Revenue: %d%n", revenue);
		System.out.printf("Average Satisfaction: %d%n", satisfaction);
		System.out.println("End Display GameObjective Targets");
	}
	
	public void status() {
		
		Function<Integer, String> revenueConversion = (i) -> String.format("$%.2f", i / 100.0);
		
		System.out.println(">>-------------------------<<");
		System.out.printf("Objective [%s] completion status %b%n", name, isCompleted);
		System.out.printf("Total paid customers: %d/%d%n", tracked.paidCustomers, paidCustomers);
		System.out.printf("Total revenue: %s/%s%n", revenueConversion.apply(tracked.revenue), revenueConversion.apply(revenue));
		System.out.printf("Average satisfaction: %.2f/%.2f%n", tracked.satisfaction, satisfaction);
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
			case OnRestaurant.PaymentReceived onPayment -> {
				tracked.revenue = onPayment.revenue();
				tracked.paidCustomers = onPayment.paidCustomerCount();
			}
			
			case OnRestaurant.SatisfactionUpdated onSatisfactionUpdated -> {
				tracked.satisfaction = (float)onSatisfactionUpdated.satisfaction();
			}
			
			default -> {
				//System.out.println("payload none: generic event");
			}
		}
	}
	
	public boolean isCompleted() { return isCompleted; }
}

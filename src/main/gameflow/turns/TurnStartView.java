package main.gameflow.turns;

import java.util.List;
import java.util.ArrayList;
import main.events.*;
import main.gameflow.GameSession;

//tracks cumulative turn information
//updates self information based on events rather than polling
public class TurnStartView extends TurnView {
	
	//can retrieve all customers and their order status here
	private List<Integer> queue = new ArrayList<>(List.of(19, 27, 80, 24));
	private List<Integer> tables = new ArrayList<>(List.of(0, 1));
	
	//Staff tasks???
	//Just list all options?
	
	@Override
	protected void display() {
		System.out.printf("TURN [%d/%d]%n", turn, GameSession.data().turns());
		System.out.println(">>--Start-of-turn display--<<");
		System.out.printf("Revenue: %d%n", revenue);
		System.out.printf("Average Satisfaction %d%n", satisfaction);
		displayQueue();
		displayOccupiedTables();
		displayCustomerInfo();
		System.out.println(">>-------------------------<<");
	}
	
	private void displayQueue() {
		System.out.printf("Queue: %s%n", queue.toString());
	}
	
	private void displayOccupiedTables() {
		System.out.printf("Tables: %s%n", tables.toString());
	}
	
	private void displayCustomerInfo() {
		List<String> customers = getCustomers();
		customers.forEach(customer -> {
			displayCustomerSatisfaction(customer);
			displayCustomerOrderStatus(customer);
		});
	}
	
	private void displayCustomerSatisfaction(String customer) {
		System.out.printf("%s satisfaction:%n", customer);
	}
	
	private void displayCustomerOrderStatus(String customer) {
		System.out.printf("%s order:%n", customer);
	}
	
	private List<String> getCustomers() {
		return new ArrayList<>(List.of("Customer 1", "Customer 2", "Customer 3"));
	}
	
	public void onEvent(GameEvent event) {
		super.onEvent(event);
		
		switch (event) {
			case OnTurn.Start onTurnStart -> { turn = onTurnStart.turn(); display(); }
			default -> {}
		}
	}
}

package main.gameflow.turns;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import main.events.*;
import main.restaurant.*;
import main.order.*;
import main.customer.*;
import main.gameflow.GameSession;

//tracks cumulative turn information
//updates self information based on events rather than polling
public class TurnStartView extends TurnView {
	
	//can retrieve all customers and their order status here
	private List<Customer> queue = new ArrayList<>();
	
	//Staff tasks???
	//Just list all options?
	
	@Override
	protected void display() {
		System.out.println(">>-------------------------<<");
		System.out.printf("TURN [%d/%d]%n", turn, GameSession.data().turns());
		System.out.println(">>--Start-of-turn display--<<");
		displayRevenue();
		displayAverageSatisfaction();
		displayQueue();
		displayOccupiedTables();
		displayCustomerInfo();
		System.out.println(">>-------------------------<<");
	}
		
	private void displayQueue() {
		System.out.printf("Queue: ");		
		queue.forEach(customer -> {
			System.out.printf("[%s %d], ", customer.getClass().getSimpleName(), customer.getCustomerId());		
		});
		System.out.println("-");		
	}
	
	private void displayOccupiedTables() {
		List<Table> tables = GameSession.session().restaurant().getTables();
		
		System.out.printf("Tables: >> ");
		tables.forEach(table -> {
			System.out.printf("[%d:%s] >> ", table.getTableNumber(), table.getOccupant() != null ? "Occupied" : "Free");			
		});
		System.out.println("-");
	}
	
	private void displayCustomerInfo() {
		
		/*This should just be remaining customers in Restaurant*/
		List<Customer> customers = new ArrayList<>();
		customers.addAll(queue);
		
		GameSession.session().restaurant().getOccupiedTables().forEach(t -> {
			customers.add(t.getOccupant());
		});
		
		customers.forEach(customer -> {
			System.out.printf("Customer %d >> ", customer.getCustomerId());
			displayCustomerSatisfaction(customer);
			displayCustomerOrderStatus(customer);
			System.out.println("-");
		});
	}
	
	private void displayCustomerSatisfaction(Customer customer) {
		System.out.printf("Satisfaction: %d/100 >> ", customer.getSatisfaction());
	}
	
	private void displayCustomerOrderStatus(Customer customer) {

		Optional<Order> validateOrder = GameSession.session().restaurant().getOrderOf(customer);
		
		validateOrder.ifPresent(order -> {
			System.out.printf("Order: %s", order.getStatus());	
		});
							
	}
	
	public void onEvent(GameEvent event) {
		super.onEvent(event);
		
		switch (event) {
			case OnTurn.Start onTurnStart -> { turn = onTurnStart.turn(); display(); }
			case OnRestaurant.QueueUpdated onQueueUpdated -> {
				//Ensures snapshot copy instead
				queue = List.copyOf(onQueueUpdated.waitingQueue().getCustomers());
				displayQueue();
			}
			default -> {}
		}
	}
}

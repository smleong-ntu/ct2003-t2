package main.gameflow.turns;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import main.events.*;
import main.restaurant.*;
import main.staff.*;
import main.staff.task.*;
import main.order.*;
import main.customer.*;
import main.gameflow.GameSession;

//tracks cumulative turn information
//updates self information based on events rather than polling
public class TurnStartView extends TurnView {
	
	//can retrieve all customers and their order status here
	private List<Customer> queue = new ArrayList<>();
	private String queueDisplay;
	
	//Staff tasks???
	Map<Class<? extends Task>, String> taskMap = new HashMap<>();
	
	@Override
	protected void display() {
		
		System.out.println(">>-------------------------<<");
		System.out.printf("TURN [%d/%d]%n", turn, GameSession.data().turns());
		//System.out.println(">>--Start-of-turn display--<<");
		displayRevenue();
		displayAverageSatisfaction();
		displayQueue();
		displayOccupiedTables();
		displayCustomerInfo();
		displayAllTasks();
		System.out.println(">>-------------------------<<");
	}
		
	private void displayQueue() {
		System.out.println(queueDisplay);	
	}
	
	private void displayOccupiedTables() {
		List<Table> tables = GameSession.session().restaurant().getTables();
		
		System.out.printf("Tables: ");
		tables.forEach(table -> {
			System.out.printf("[%d:%s] >> ", 
					table.getTableNumber(), 
					table.getOccupant() != null ? 
							String.format("Customer %d", table.getOccupant().getCustomerId()) : 
							"Free"
			);			
		});
		System.out.println();
	}
	
	private void displayCustomerInfo() {
		
		/*This should just be remaining customers in Restaurant*/
		List<Customer> customers = new ArrayList<>();
		customers.addAll(queue);
		
		GameSession.session().restaurant().getOccupiedTables().forEach(t -> {
			customers.add(t.getOccupant());
		});
		
		customers.forEach(customer -> {
			System.out.printf("Customer %d [%s] >> ", customer.getCustomerId(), customer.getStateFull());
			displayCustomerSatisfaction(customer);
			displayCustomerOrderStatus(customer);
		});
	}
	
	private void displayCustomerSatisfaction(Customer customer) {
		System.out.printf("Satisfaction: %d/100 >> ", customer.getSatisfaction());
	}
	
	private void displayCustomerOrderStatus(Customer customer) {

		Optional<Order> validateOrder = GameSession.session().restaurant().getOrderOf(customer);
		
		validateOrder.ifPresentOrElse(
			//Present
			order -> {
				System.out.printf("Order: %s%n", order.toString()); 
			},
			
			//Or else
			() -> { 
				System.out.println("Order[X]"); 
			}
		);
//		validateOrder.ifPresent(order -> {
//			System.out.printf("Order: %s", order.getStatus());	
//		});							
	}
	
	private void displayAllTasks() {
		
		if (taskMap.isEmpty()) {
			populateTaskMap();
		}
		
		System.out.printf("Tasks: ");
		taskMap.forEach((key, value) -> {
			System.out.printf("%s", value);
			System.out.print(" | ");
		});
		System.out.println();
	}
	
	private void populateTaskMap() {
		var staffList = GameSession.session().restaurant().getStaffList();
		
		//Go through every type of waiter and its tasks to display
		List<Staff> waiters = staffList.get(Waiter.class);
		
		waiters.forEach(waiter -> {
			waiter.getTasks().forEach(task ->{
				taskMap.putIfAbsent(task.getClass(), task.taskName());
			});
		});

		List<Staff> chefs = staffList.get(Chef.class);
		
		//Go through every type of chef and its tasks to display
		chefs.forEach(chef -> {
			chef.getTasks().forEach(task ->{
				taskMap.putIfAbsent(task.getClass(), task.taskName());
			});
		});
	}
	
	public void onEvent(GameEvent event) {
		super.onEvent(event);
		
		switch (event) {
			case OnTurn.Start onTurnStart -> { turn = onTurnStart.turn(); display(); }
			case OnRestaurant.QueueUpdated onQueueUpdated -> {
				//Ensures snapshot copy instead
				queue = List.copyOf(onQueueUpdated.waitingQueue().getCustomers());
				queueDisplay = onQueueUpdated.waitingQueue().toString();
			}
			
			default -> {}
		}
	}
}

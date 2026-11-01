package main.events;

import main.restaurant.*;

public class OnRestaurant {

	public static class QueueUpdated implements GameEvent{
		private final WaitingQueue queue;
		
		public QueueUpdated(WaitingQueue queue) { this.queue = queue; }
		public WaitingQueue waitingQueue() { return queue; }
	}
	
	public static class PaymentReceived implements GameEvent{
		private final int revenue;
		private final int received;
		private final int paidCustomers;
		
		public PaymentReceived(int received, int revenue, int paidCustomerCount) {
			this.revenue = revenue; 
			this.received = received;
			this.paidCustomers = paidCustomerCount;
		}
		public int revenue() { return revenue; }
		public int received() { return received; }
		public int paidCustomerCount() { return paidCustomers; }
	}
	
	public static class SatisfactionUpdated implements GameEvent{
		private final double satisfaction;
		
		public SatisfactionUpdated(double satisfaction) { this.satisfaction = satisfaction; }
		public double satisfaction() { return satisfaction; }
	}
	
	public static class Departure implements GameEvent{
		private final int departed;
		
		public Departure(int departed) { this.departed = departed; }
		public final int departed() { return departed; }
	}
	
	public static class ServedOrder implements GameEvent{
		private final int count;
		
		public ServedOrder(int count) { this.count = count; }
		public final int count() { return count; }
	}
}

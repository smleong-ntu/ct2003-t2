package main.events;

import main.restaurant.*;

public class OnRestaurant {
	//Called when turn just ended, handle validations before completed notify
	public static class QueueUpdated implements GameEvent{
		private final WaitingQueue queue;
		
		public QueueUpdated(WaitingQueue queue) { this.queue = queue; }
		public WaitingQueue waitingQueue() { return queue; }
	}
}

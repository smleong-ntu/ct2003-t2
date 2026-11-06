package main.restaurant;

import main.events.*;

public class RestaurantView implements GameEventListener{
	public void onEvent(GameEvent event) {
		switch (event) {
			case OnRestaurant.QueueUpdated onQueueUpdated -> {
				if (onQueueUpdated.waitingQueue().size() > 0){
					System.out.println(onQueueUpdated.waitingQueue().toString());	
				}
			}
			
			case OnRestaurant.PolicyUpdated onPolicyUpdated -> {
//				if (policy.isActive(turn)) {
					System.out.printf("Happy hour active. Turns remaining %d%n", onPolicyUpdated.policy().remainingTurns(0));
//				}
			}
			default -> {}
		}
	}
}

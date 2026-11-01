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
			default -> {}
		}
	}
}

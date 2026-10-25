package main.gameflow;

import main.events.*;
import main.gamesetting.*;

public class GameSession implements GameEventListener{
	
	private GameObjective objective;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//GameSession session = new GameSession();
		
		//TEST EVENT RECEIVED
		//session.onEvent(new OnTurn.End(599));
		
	}
	
	public GameSession(GameObjective.Setting setting) {
		System.out.println("New session created!");
		
		objective = new GameObjective(setting);
		objective.Display();
	}
	
	//TEST EVENT EVALUATION >> TO REMOVE LATER <<
	@Override
	public void onEvent(GameEvent event) {
		System.out.printf("Event Received: %s%n", event.getClass().getSimpleName());
		
		switch (event) {
			case OnTurn.End onTurnEnd -> {
				System.out.printf("payload %s%n", onTurnEnd.turn());
			}
			
			default -> {
				System.out.println("payload none: generic event");
			}
		}
	}

}

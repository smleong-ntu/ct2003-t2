package main.gameflow;

import main.events.*;
import main.gamesetting.*;

public class GameSession implements GameEventListener{
	
	private static GameData data;
	
	private GameObjective objective;
	private TurnManager turnManager;
		
	public GameSession(GameData.Setting dataSetting, GameObjective.Setting objectiveSetting) {
		System.out.println("New session created!");
		
		initialize(dataSetting, objectiveSetting);
		startSession();
	}
	
	//creation of various components
	private void initialize(GameData.Setting dataSetting, GameObjective.Setting objectiveSetting) {
		data = new GameData(dataSetting);
		//data.display();
		
		objective = new GameObjective(objectiveSetting);
		//objective.display();
		
		//create restaurant
		//restaurant = new Restaurant(dataSetting);
		
		turnManager = new TurnManager(data);
		turnManager.subscribe(objective);
		turnManager.subscribe(this);
	}
	
	//starts different components after linking and creation
	private void startSession() {
		turnManager.initialize();
	}
	
	private void endSession() {
		
		//Display objective status
		objective.status();
		
		//perform cleanup of remaining classes where needed
		//turnManager.unsubscribe(objective);
	}
	
	@Override
	public void onEvent(GameEvent event) {		
		switch (event) {
			case OnTurn.End onTurnEnd -> {
				System.out.printf("payload %d%n", onTurnEnd.turn());
				if (onTurnEnd.turn() == data.turns()) {
					endSession();
				}
			}
			
			default -> {
				//System.out.println("payload none: generic event");
			}
		}
	}

	public static GameData data() { return data; }
}

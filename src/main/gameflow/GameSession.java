package main.gameflow;

import main.events.*;
import main.gamesetting.*;

public class GameSession implements GameEventListener{
	
	private GameObjective objective;
	private GameData data;
	private TurnManager turnManager;
		
	public GameSession(GameData.Setting dataSetting, GameObjective.Setting objectiveSetting) {
		System.out.println("New session created!");
		
		initialize(dataSetting, objectiveSetting);
		startSession();
	}
	
	//creation of various components
	private void initialize(GameData.Setting dataSetting, GameObjective.Setting objectiveSetting) {
		objective = new GameObjective(objectiveSetting);
		objective.Display();
		
		data = new GameData(dataSetting);
		data.Display();
		
		turnManager = new TurnManager(data);
		turnManager.subscribe(objective);
	}
	
	//starts different components after linking and creation
	private void startSession() {
		turnManager.initialize();
	}
	
	private void endSession() {
		
		//Display objective status
		objective.status();
		
		//perform cleanup of remaining classes where needed
		turnManager.unsubscribe(objective);
	}
	
	@Override
	public void onEvent(GameEvent event) {		
		switch (event) {
			case OnTurn.End onTurnEnd -> {
				System.out.printf("payload %s%n", onTurnEnd.turn());
				if (onTurnEnd.turn() == data.turns()) {
					endSession();
				}
			}
			
			default -> {
				//System.out.println("payload none: generic event");
			}
		}
	}

	public GameData data() { return data; }
}

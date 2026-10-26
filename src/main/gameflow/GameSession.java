package main.gameflow;

import main.events.*;
import main.gamesetting.*;
import main.staff.TaskManager;

public class GameSession implements GameEventListener{
	
	private static GameData data;
	private static GameSession session;
	
	private GameObjective objective;
	private TurnManager turnManager;
	
	//Placeholder
	private TaskManager placeholderTaskManager;
		
	private GameSession(GameData.Setting dataSetting, GameObjective.Setting objectiveSetting) {
		System.out.println("New session created!");
		
		session = this;
		initialize(dataSetting, objectiveSetting);
		startSession();
	}
	
	public static GameSession Create (GameData.Setting dataSetting, GameObjective.Setting objectiveSetting) {
		
		if (session == null) {
			session = new GameSession(dataSetting, objectiveSetting);
		}
		
		return session;
	}
	
	//creation of various components
	private void initialize(GameData.Setting dataSetting, GameObjective.Setting objectiveSetting) {
		data = new GameData(dataSetting);
		//data.display();
		
		objective = new GameObjective(objectiveSetting);
		//objective.display();
		
		//create restaurant
		//restaurant = new Restaurant(dataSetting);
		placeholderTaskManager = new TaskManager();
				
		
		turnManager = new TurnManager(data);
		turnManager.subscribe(objective);
		turnManager.subscribe(placeholderTaskManager);
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
				if (onTurnEnd.turn() == data.turns()) {
					endSession();
				}
			}
			
			default -> {}
		}
	}

	public static GameData data() { return data; }
	public static GameSession session() { return session; }
	
	public TaskManager placeholderTaskManager() { return placeholderTaskManager; }
}

package main.gameflow;

import main.events.*;
import main.gameflow.turns.*;
import main.gamesetting.*;
import main.restaurant.*;
import main.staff.task.TaskManager;

public class GameSession implements GameEventListener{
	
	private static GameSession session;
	private static GameData data;
	
	private GameObjective objective;
	private TurnManager turnManager;
	private TurnEndView turnEndView;
	private TurnView turnStartView;
	private Restaurant restaurant;
	
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
		
		//Immutable game data to dependency inject into main components
		data = new GameData(dataSetting);		
		objective = new GameObjective(objectiveSetting);
		
		//Displays for turn start and end
		turnEndView = new TurnEndView();
		turnStartView = new TurnStartView();
		
		//create restaurant
		restaurant = new Restaurant(data);
		restaurant.subscribe(objective);
		restaurant.subscribe(turnEndView);
		restaurant.subscribe(turnStartView);
		placeholderTaskManager = new TaskManager();
				
		
		turnManager = new TurnManager(data);
		turnManager.subscribe(objective);
		turnManager.subscribe(placeholderTaskManager);
		turnManager.subscribe(turnEndView);
		turnManager.subscribe(turnStartView);
		turnManager.subscribe(restaurant);
		turnManager.subscribe(this);
	}
	
	//starts different components after linking and creation
	private void startSession() {
		turnManager.initialize();
	}
	
	private void endSession() {
		
		//Display objective status
		objective.status();
		System.out.printf("%s%n", objective.isCompleted() ? "VICTORY" : "DEFEAT");
		
		//perform cleanup of remaining classes where needed
		//turnManager.unsubscribe(objective);
	}
	
	@Override
	public void onEvent(GameEvent event) {		
		switch (event) {
			case OnTurn.Completed onTurnCompleted -> {
				if (onTurnCompleted.turn() == data.turns()) {
					endSession();
				}
			}
	
			default -> {}
		}
	}

	public static GameData data() { return data; }
	public static GameSession session() { return session; }
	
	public final Restaurant restaurant() { return restaurant; }
	public TaskManager placeholderTaskManager() { return placeholderTaskManager; }
}

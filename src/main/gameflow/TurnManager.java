package main.gameflow;

import java.util.List;
import java.util.ArrayList;
import main.gamesetting.*;
import main.events.*;

public class TurnManager {
	
	private final int MAX_TURN;
	private List<GameEventListener> listeners = new ArrayList<GameEventListener>();
	
	private int currentTurn = 1;
	
	public TurnManager(GameData setting) {
		MAX_TURN = setting.turns();
	}
	
	//specific call to start turns
	public void initialize() {
		start();
	}
	
	//SUBSCRIPTION FOR EVENT LISTENERS
	public void subscribe(GameEventListener listener) {
		listeners.add(listener);
	}
	
	public void unsubscribe(GameEventListener listener) {
		listeners.remove(listener);
	}
	
	//TURN METHODS INTERNAL
	//starts a fresh turn
	private void start() {
		notify(new OnTurn.Start(currentTurn));
	}
	
	//completes the turn
	private void complete() {
		
		notify(new OnTurn.End(currentTurn));
		
		currentTurn ++;
		
		if (currentTurn <= MAX_TURN) {
			//Go next turn
			start();
		}
	}
	
	private void notify(GameEvent event) {
		for (GameEventListener listener : listeners) {
			listener.onEvent(event);
		}
	}
}

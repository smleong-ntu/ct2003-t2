package main.gameflow;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Supplier;
import main.gamesetting.*;
import main.events.*;
import main.gameflow.turns.*;

public class TurnManager {
	
	private final int MAX_TURN;
	private int currentTurn = 1;
	private int currentStep = 0;
	
	private List<TurnStep> steps = new ArrayList<>();
	private List<GameEventListener> listeners = new ArrayList<GameEventListener>();
	
	public TurnManager(GameData setting) {
		MAX_TURN = setting.turns();
		
		for (Supplier<? extends TurnStep> step : setting.stepOrder()) {
			TurnStep newStep = step.get();
			newStep.initialize(this);
			
			steps.add(newStep);
		}
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
		
		currentStep = 0;
		currentTurn ++;
		
		notify(new OnTurn.Start(currentTurn));
	}
	
	//completes the turn
	private void complete() {
		
		notify(new OnTurn.End(currentTurn));
		
		if (currentTurn < MAX_TURN) {
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

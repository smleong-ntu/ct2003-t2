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
		
		//Use data setting to create steps in the order reflected in setting
		//Then initialize and set post execute action
		for (Supplier<? extends TurnStep> step : setting.stepOrder()) {
			TurnStep newStep = step.get();
			newStep.initialize(this);
			newStep.setPostExecuteAction(this::nextStep);
			
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
		steps.get(currentStep).execute();
	}
	
	//updates the step then checks if turn should be completed
	//if not completed execute next step
	private void nextStep() {
		currentStep ++;
		System.out.printf("to next step %d on turn %d%n", currentStep, currentTurn);
		
		if (currentStep > steps.size() - 1) 
		{
			complete();
			return;
		}
		
		steps.get(currentStep).execute();
	}
	
	//completes the turn, notify all listeners
	//determine if have any remaining turns, start a new turn if turns are available
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

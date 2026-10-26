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
	private int currentStep = -1;
	
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
		System.out.printf("TURN [%d/%d]%n", currentTurn, MAX_TURN);
		notify(new OnTurn.Start(currentTurn));
		
		//Update turns for all steps, then execute nextStep (-1 -> 0)
		steps.forEach(step -> step.setTurn(currentTurn));
		nextStep(); //turns currentStep to 0
	}
	
	//updates the step then checks if turn should be completed
	//if not completed execute next step
	private void nextStep() {
		currentStep ++;
		
		if (currentStep > steps.size() - 1) 
		{
			notify(new OnTurn.End(currentTurn));
			complete();
			return;
		}
		
		System.out.printf("[%d: %s]%n", currentStep + 1, steps.get(currentStep).getName());
		steps.get(currentStep).execute();
	}
	
	//completes the turn, notify all listeners
	//determine if have any remaining turns, start a new turn if turns are available
	private void complete() {
		
		notify(new OnTurn.Completed(currentTurn));
		
		if (currentTurn < MAX_TURN) {
			
			//Go next turn
			nextTurn();
		}
	}
	
	//Reset to -1 for step and increment turn. then call start to loop
	private void nextTurn() {
		currentStep = -1;
		currentTurn ++;
		
		notify(new OnTurn.Reset(currentTurn));
		start();
	}
	
	private void notify(GameEvent event) {
		for (GameEventListener listener : listeners) {
			listener.onEvent(event);
		}
	}
}

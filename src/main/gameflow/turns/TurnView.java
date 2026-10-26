package main.gameflow.turns;

import main.events.*;

public abstract class TurnView implements GameEventListener{
	protected int turn;
	protected int revenue;
	protected int satisfaction;
	
	protected abstract void display();
	
	public void onEvent(GameEvent event) {}
}

package main.gameflow.turns;

import main.gameflow.*;

public abstract class TurnStep {
	
	protected int turn;
	protected TurnManager manager;
	
	public void initialize(TurnManager manager) {
		this.manager = manager;
		System.out.printf("Step initialized: %s%n", this.getClass().getSimpleName());
	}
	
	public final void setStep(int turn) {
		this.turn = turn;
	}
}

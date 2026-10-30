package main.gameflow.turns;

import main.gameflow.*;

public abstract class TurnStep {
	
	protected int turn;
	protected TurnManager manager;
	protected Runnable postAction;
	protected String name;
	
	public void initialize(TurnManager manager) {
		this.manager = manager;
		//System.out.printf("Step initialized: %s%n", this.getClass().getSimpleName());
	}
	
	public final void setTurn(int turn) {
		this.turn = turn;
	}
	
	public final void setPostExecuteAction(Runnable action) {
		postAction = action;
	}
	
	public void execute() {}
	
	protected final void postExecuteAction() {
		if (postAction == null) {
			return;
		}
		
		postAction.run();
	}
	
	public String getName() { return name; }
}

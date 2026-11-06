package main.gameflow.turns;

import java.util.Scanner;

import main.gameflow.*;

public abstract class TurnStep {
	
	protected int turn;
	protected TurnManager manager;
	protected Runnable postAction;
	protected String name;
	
	//One reader shared by every step: a second Scanner on System.in would read ahead and swallow piped input
	protected static final Scanner input = new Scanner(System.in);
	
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
	
	//Reads the next number; ends the game cleanly when there is no more input (Ctrl-D, or a key list that runs out)
	protected static int nextInt() {
		if (!input.hasNext()) {
			System.out.printf("%nInput ended before the game finished. Game stopped.%n");
			System.exit(0);
		}
		return input.nextInt();
	}
}

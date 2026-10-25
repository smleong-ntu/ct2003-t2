package main.gameflow.turns;

public class EndStep extends TurnStep{

	@Override
	public void execute() {
		postExecuteAction();
	}
}

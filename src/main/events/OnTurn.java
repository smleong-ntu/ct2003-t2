package main.events;

//holder for all turn related events
public class OnTurn {
	
	//Equivalent to OnTurnEnd, to test
	public static class End implements GameEvent{
		private final int turn;
		
		public End(int turn) { this.turn = turn; }
		public int turn() { return turn; }
	}
	
}

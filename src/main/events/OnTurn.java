package main.events;

//holder for all turn related events
public class OnTurn {
	
	//Called when turn just ended, handle validations before completed notify
	public static class End implements GameEvent{
		private final int turn;
		
		public End(int turn) { this.turn = turn; }
		public int turn() { return turn; }
	}
	
	//Called when turn is completed, all validation is completed, safe to display
	public static class Completed implements GameEvent{
		private final int turn;
		
		public Completed(int turn) { this.turn = turn; }
		public int turn() { return turn; }
	}
	
	//Called when a new turn starts
	public static class Start implements GameEvent{
		private final int turn;
		
		public Start(int turn) { this.turn = turn; }
		public int turn() { return turn; }
	}
	
	//Called when attributes of turn is reset for new turn
	public static class Reset implements GameEvent{
		private final int turn;

		public Reset(int turn) { this.turn = turn; }
		public int turn() { return turn; }
	}
	
}

package main.staff.task;

import main.staff.Waiter;

public abstract class WaiterTask extends Task {
		//access to waiter here
		protected Waiter waiter;
			
		public WaiterTask(String name, Waiter waiter) {
			super(name);
			this.waiter = waiter;
		}
		
		@Override
		public void execute(int turn) {
			super.execute(turn);
			System.out.printf("%s done by >> %s [ID:%d] <<%n", name, waiter.getClass().getSimpleName(), waiter.getID());
		}
}

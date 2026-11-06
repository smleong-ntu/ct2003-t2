package main.staff.task;

import main.staff.Host;

public class HostTask extends Task{
		//access to host here
		protected Host host;
			
		public HostTask(String name, Host host) {
			super(name);
			this.host = host;
		}
		
		@Override
		public void execute(int turn) {
			super.execute(turn);
			System.out.printf("%s done by >> %s [ID:%d] <<%n", name, host.getClass().getSimpleName(), host.getID());
		}
}

package main.staff.task;

import java.util.List;

import main.staff.Host;
import main.staff.task.*;

public class SeatAutoTask extends HostTask{
	public SeatAutoTask(String name, Host host) {
		super(name, host);
	}
	
	//How do you make this task execution to be overriden?
	//What happens in this task?
	
	//What makes this task valid or invalid?
	//What is the reason why it is invalid?
	@Override
	public boolean isTaskValid(StringBuilder reason, List<Task> taskQueue) {
		return true;
	}
	
	@Override
	protected void setPendingInfo() {
		super.setPendingInfo();
		pending.add(Pending.CUSTOMER);
		pending.add(Pending.TABLE);
	}
}

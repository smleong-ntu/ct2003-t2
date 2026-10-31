package main.staff.task;

import java.util.List;
import java.util.function.Function;
import main.staff.*;

public class TaskType {
	public static class Setting{
		public List<Function<Staff, ? extends Task>> taskList;
	}
	
	public static final TaskType.Setting WAITER = new TaskType.Setting() {{
		taskList = List.of(
			staff -> new SeatTask("Seat Customer", (Waiter)staff),
			staff -> new TakeOrderTask("Take Order", (Waiter)staff),
			staff -> new ServeOrderTask("Serve Order", (Waiter)staff),
			staff -> new WaitTask("Wait", staff)
		);
	}};
	
	public static final TaskType.Setting CHEF = new TaskType.Setting() {{
		taskList = List.of(
			staff -> new GenericTask("Prepare Order"),
			staff -> new WaitTask("Wait", staff)
		);
	}};
	
	public static final TaskType.Setting CASHIER = new TaskType.Setting() {{
		taskList = List.of(
			staff -> new CollectPaymentTask("Collect Payment", (Cashier)staff),
			staff -> new WaitTask("Wait", staff)
		);
	}};
}

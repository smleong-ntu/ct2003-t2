package main.staff.task;

import java.util.List;
import java.util.function.Function;
import main.staff.*;

public class TaskType {
	private static class Setting{
		public List<Function<Staff, ? extends Task>> taskList;
	}
	
	public static final TaskType.Setting WAITER = new TaskType.Setting() {{
		taskList = List.of(
			staff -> new GenericTask("Waiter [SEAT]"),
			staff -> new GenericTask("Waiter [SERVE]"),
			staff -> new WaitTask("Waiter Wait")
		);
	}};
	
	public static final TaskType.Setting CHEF = new TaskType.Setting() {{
		taskList = List.of(
			staff -> new GenericTask("Chef [PREP]"),
			staff -> new WaitTask("Chef Wait")
		);
	}};
	
	public static final TaskType.Setting CASHIER = new TaskType.Setting() {{
		taskList = List.of(
			staff -> new CollectPaymentTask("Cashier [COLLECT]", (Cashier)staff),
			staff -> new WaitTask("Cashier Wait")
		);
	}};
}

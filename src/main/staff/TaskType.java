package main.staff;

import java.util.List;
import java.util.function.Supplier;

public class TaskType {
	private static class Setting{
		public List<Supplier<? extends Task>> taskList;
	}
	
	public static final TaskType.Setting WAITER = new TaskType.Setting() {{
		taskList = List.of(
			() -> new GenericTask("Waiter [SEAT]"),
			() -> new GenericTask("Waiter [SERVE]"),
			() -> new GenericTask("Waiter [WAIT]")
		);
	}};
	
	public static final TaskType.Setting CHEF = new TaskType.Setting() {{
		taskList = List.of(
			() -> new GenericTask("Chef [PREP]"),
			() -> new GenericTask("Chef [WAIT]")
		);
	}};
	
	public static final TaskType.Setting CASHIER = new TaskType.Setting() {{
		taskList = List.of(
			() -> new GenericTask("Cashier [COLLECT]"),
			() -> new GenericTask("Cashier [WAIT]")
		);
	}};
}

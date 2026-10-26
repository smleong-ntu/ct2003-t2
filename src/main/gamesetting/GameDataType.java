package main.gamesetting;

import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

import main.gameflow.turns.*;
import main.staff.*;

public class GameDataType {
	public static final GameData.Setting DEFAULTS = new GameData.Setting() {{
		name = "default setting";
		turns = 18;
		tables = 2;
		
		stepOrder = List.of(
		    ArrivalStep::new,
		    ManagerStep::new,
		    TaskStep::new,
		    DiningStep::new,
		    EndStep::new
		);
		
		staffList = Map.of(
			Waiter::new, 1,
			Chef::new, 1
			//Cashier::new, 1
		);
		
		arrivals = Map.ofEntries(
			entry(1, "Customer"),
			entry(2, "None"),
			entry(3, "None"),
			entry(4, "Customer"),
			entry(5, "None"),
			entry(6, "None"),
			entry(7, "Customer"),
			entry(8, "None"),
			entry(9, "None"),
			entry(10, "Customer"),
			entry(11, "None"),
			entry(12, "None"),
			entry(13, "Customer"),
			entry(14, "None"),
			entry(15, "None"),
			entry(16, "Customer"),
			entry(17, "None"),
			entry(18, "None")
		);		
	}};
}

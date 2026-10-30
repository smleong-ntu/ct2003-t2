package main.gamesetting;

import java.util.List;
import java.util.Map;
import static java.util.Map.entry;

import main.gameflow.turns.*;
import main.customer.*;
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
		
		//Can change type of customer to spawn here
		arrivals = Map.ofEntries(
			entry(1, id ->  new Customer(id)),
			entry(2, id ->  null),
			entry(3, id ->  null),
			entry(4, id ->  new Customer(id)),
			entry(5, id ->  null),
			entry(6, id ->  null),
			entry(7, id ->  new Customer(id)),
			entry(8, id ->  null),
			entry(9, id ->  null),
			entry(10, id ->  new Customer(id)),
			entry(11, id ->  null),
			entry(12, id ->  null),
			entry(13, id ->  new Customer(id)),
			entry(14, id ->  null),
			entry(15, id ->  null),
			entry(16, id ->  new Customer(id)),
			entry(17, id ->  null),
			entry(18, id ->  null)
		);		
	}};
}

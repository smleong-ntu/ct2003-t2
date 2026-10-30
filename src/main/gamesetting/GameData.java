package main.gamesetting;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Supplier;
import java.util.function.Function;
import main.gameflow.turns.*;
import main.customer.*;
import main.staff.*;

public class GameData {
	public static class Setting{
		public String name;
		public int turns;
		public int tables;
		public List<Supplier<? extends TurnStep>> stepOrder;
		public Map<Supplier<? extends Staff>, Integer> staffList;
		public Map<Integer,Function<Integer, Customer>> arrivals;
	}
	
	private final String name;
	private final int turns;
	private final int tables;
	private final List<Supplier<? extends TurnStep>> stepOrder;
	private final Map<Supplier<? extends Staff>, Integer> staffList;
	private final Map<Integer,Function<Integer, Customer>> arrivals;
	
	public GameData(Setting setting) {
		name = setting.name;
		turns = setting.turns;
		tables = setting.tables;
		stepOrder = new ArrayList<>(setting.stepOrder);
		staffList = new HashMap<>(setting.staffList);
		arrivals = new HashMap<>(setting.arrivals);
	}
	
	public void display() {
		System.out.printf("Displaying Setting [%s]%n", name);
		System.out.printf("Turns: %d%n", turns);
		System.out.printf("Tables: %d%n", tables);
				
//		staffList.forEach((key, value) -> {
//			System.out.printf("Staff List: %s - %d%n", key, value);	
//		});
		
		arrivals.forEach((key, value) -> {
			System.out.printf("Arrival List: %d - %s%n", key, value);					
		});
		
		System.out.println("End Display DataSetting Targets");
	}
	
	public String name() { return name; }
	public int turns() { return turns; }
	public int tables() { return tables; }
	public List<Supplier<? extends TurnStep>> stepOrder() { return stepOrder; }
	public Map<Supplier<? extends Staff>, Integer> staffList() { return staffList; }
	public Map<Integer,Function<Integer, Customer>> arrivals() { return arrivals; };
}

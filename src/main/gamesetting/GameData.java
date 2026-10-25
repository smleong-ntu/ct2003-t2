package main.gamesetting;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class GameData {
	public static class Setting{
		public String name;
		public int turns;
		public int tables;
		public int[] stepOrder;
		public Map<String, Integer> staffList;
		public Map<Integer,String> arrivals;
	}
	
	private final String name;
	private final int turns;
	private final int tables;
	private final int[] stepOrder;
	private final Map<String, Integer> staffList;
	private final Map<Integer, String> arrivals;
	
	public GameData(Setting setting) {
		name = setting.name;
		turns = setting.turns;
		tables = setting.tables;
		stepOrder = Arrays.copyOf(setting.stepOrder, setting.stepOrder.length);
		staffList = new HashMap<>(setting.staffList);
		arrivals = new HashMap<>(setting.arrivals);
	}
	
	public void Display() {
		System.out.printf("Displaying Setting [%s]%n", name);
		System.out.printf("Turns: %d%n", turns);
		System.out.printf("Tables: %d%n", tables);
		
		for (int s : stepOrder) {
			System.out.printf("Step Order: %s%n", s);			
		}
		
		staffList.forEach((key, value) -> {
			System.out.printf("Staff List: %s - %d%n", key, value);	
		});
		
		arrivals.forEach((key, value) -> {
			System.out.printf("Arrival List: %d - %s%n", key, value);					
		});
		
		System.out.println("End Display DataSetting Targets");
	}
	
	public String name() { return name; }
	public int turns() { return turns; }
	public int tables() { return tables; }
	public int[] stepOrder() { return stepOrder; }
	public Map<String, Integer> staffList() { return staffList; }
}

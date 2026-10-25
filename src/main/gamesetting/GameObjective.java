package main.gamesetting;

public class GameObjective {
	
	public static class Setting {
		public String name;
		public int paidCustomers;
		public int revenue;
		public int satisfaction;
	}
	
	private final String name;
	private final int paidCustomers;
	private final int revenue;
	private final int satisfaction;
	
	public GameObjective(Setting setting) {
		name = setting.name;
		paidCustomers = setting.paidCustomers;
		revenue = setting.revenue;
		satisfaction = setting.satisfaction;
	}
	
	public void Display() {
		System.out.printf("Displaying Target Objective [%s]%n", name);
		System.out.printf("Paid Customers: %d%n", paidCustomers);
		System.out.printf("Revenue: %d%n", revenue);
		System.out.printf("Average Satisfaction: %d%n", satisfaction);
		System.out.println("End Display GameObjective Targets");
	}
	
}

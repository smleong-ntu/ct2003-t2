package main.gameflow.turns;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;

import main.gameflow.GameSession;
import main.gameflow.TurnManager;
import main.staff.*;

public class ManagerStep extends TurnStep {
	
	private Scanner input;
	
	//placeholder staff list, this should come from restaurant
	Map<Class<? extends Staff>, List<Staff>> placeholderStaffList = new HashMap<>();
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		
		input = new Scanner(System.in);

		//Placeholder initialization of staff list, can be brought over to restaurant
		GameSession.data().staffList().forEach((key, value) -> {
			
			for (int i = 0; i < value; i++) {
				Staff newStaff = key.get();
				placeholderStaffList.computeIfAbsent(newStaff.getClass(), _ -> new ArrayList<>()).add(newStaff);
			}
		});
		
		//save reference to restaurant or staff list <- since it is non dynamic
	}
	
	public void execute() {
				
		//Get list of staff of staff class type from restaurant
		//Queue task for waiters
		List<Staff> waiters = placeholderStaffList.get(Waiter.class);
		waiters.forEach(this::queueTask);
		
		//Queue task for chefs
		List<Staff> chefs = placeholderStaffList.get(Chef.class);
		chefs.forEach(this::queueTask);
				
		postExecuteAction();
	}
	
	private void queueTask(Staff s) {
		List<Task> tasks = findTasks(s);
		displayTasks(tasks, s);
		
		Task selectedTask = selectTask(tasks);
		
		if (selectedTask != null) {
			GameSession.session().placeholderTaskManager().queue(selectedTask);
		}
	}
	
	private List<Task> findTasks(Staff s) {
		
		//Get task list of staff object
		//Placeholder task list, should be directly defined in staff
		List<Task> tasks = new ArrayList<>();
		
		if (s instanceof Waiter) {
			
			tasks.add(new GenericTask("task1"));
			tasks.add(new GenericTask("task2"));
			return tasks;
		}
		
		if (s instanceof Chef) {
			tasks.add(new GenericTask("task1"));
			tasks.add(new GenericTask("task2"));
			tasks.add(new GenericTask("task3"));
			tasks.add(new GenericTask("task4"));
			return tasks;
		}

		return tasks;
	}
	
	private void displayTasks(List<Task> tasks, Staff s) {
		
		System.out.printf("Select a task for Staff: [%s]%n", s.getClass().getSimpleName());
		
		//Display if no choices
		if (tasks.size() == 0) {
			System.out.printf("Staff: [%s] has no tasks", s.getClass().getSimpleName());
			return;
		}
		
		for (int i = 0; i < tasks.size(); i++) {
			System.out.printf("[%d] %s%n", i+1, tasks.get(i).taskName());
		}
	}
	
	//Retrieves selected choice by input
	private Task selectTask(List<Task> tasks) {
		
		if (tasks.size() == 0) {
			return null;
		}
		
		//builds a reason should the task be invalid
		StringBuilder reason = new StringBuilder();
		
		boolean isValidChoice = false;
		int selection;
		
		do {
			selection = -1;
			
			try {
				selection = input.nextInt() - 1;			
				
				//This version will check task valid every time this is called
				isValidChoice = tasks.get(selection).isTaskValid(reason);
			}
			
			catch (InputMismatchException e){
				System.out.printf("Mismatch input %s. Please enter an integer between 1-%d%n", e.getMessage(), tasks.size());
				input.nextLine();
				continue;
			}
			
			//For some reason this is still not triggering, i think is due to the error only being below
			catch (IndexOutOfBoundsException e) {
				System.out.printf("Invalid integer %s. Please enter an integer between 1-%d%n", e.getMessage(), tasks.size());
				input.nextLine();
				continue;
			}
	
			System.out.printf("Selected [%d]%n", selection + 1);
			
			if (!isValidChoice) {
				System.out.printf("Invalid choice due to %s. Try again%n", reason.toString()); //need show reason for task invalid
			}
			
		} while (!isValidChoice);
		
		return tasks.get(selection);
	}
}

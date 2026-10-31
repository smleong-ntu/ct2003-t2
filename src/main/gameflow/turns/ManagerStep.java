package main.gameflow.turns;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.InputMismatchException;

import main.gameflow.*;
import main.restaurant.*;
import main.staff.*;
import main.staff.task.*;

public class ManagerStep extends TurnStep {
	
	private Scanner input;
	private Map<Class<? extends Staff>, List<Staff>> staffList = new HashMap<>();
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		
		name = "Manager Selection";
		input = new Scanner(System.in);
		
		staffList = GameSession.session().restaurant().getStaffList();
	}
	
	public void execute() {
				
		//Get list of staff of staff class type from restaurant
		//Queue task for waiters
		List<Staff> waiters = staffList.get(Waiter.class);
		waiters.forEach(this::queueTask);
		
		//Queue task for chefs
		List<Staff> chefs = staffList.get(Chef.class);
		chefs.forEach(this::queueTask);
				
		postExecuteAction();
	}
	
	private void queueTask(Staff s) {
		List<Task> tasks = s.getTasks();
		displayTasks(tasks, s);
		
		Task selectedTask = selectTask(tasks);
		
		if (selectedTask != null) {
			GameSession.session().placeholderTaskManager().queue(selectedTask);
		}
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
		
		boolean isValidChoice = false;
		int selection;
		
		do {
			//builds a reason should the task be invalid
			StringBuilder reason = new StringBuilder();
			
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
				System.out.printf("%s. Try another choice%n", reason.toString()); //need show reason for task invalid
			}
			
		} while (!isValidChoice);
		
		return tasks.get(selection);
	}
}

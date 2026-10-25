package main.gameflow.turns;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;
import main.gameflow.TurnManager;
import main.staff.*;

public class ManagerStep extends TurnStep {
	
	private Scanner input;
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		
		input = new Scanner(System.in);
		
		//save reference to restaurant or staff list <- since it is non dynamic
	}
	
	public void execute() {
		
		List<Task> tasks;
		Task selectedTask;
		
		//Find Waiter choices
		tasks = findTasks(0);
		displayTasks(tasks, 0);

		selectedTask = selectTask(tasks);
		
		if (selectedTask != null) {
			//get the staff object then staff.setTask(selectedTask);
			//add into "task queue"
			System.out.printf("Adding %s to task queue%n", selectedTask.taskName());
		}
		
		//Find Chef choices
		tasks = findTasks(1);
		displayTasks(tasks, 1);
		
		selectedTask = selectTask(tasks);

		if (selectedTask != null) {
			//get the staff object then staff.setTask(selectedTask);
			//add into "task queue"
			System.out.printf("Adding %s to task queue%n", selectedTask.taskName());
		}
		
		postExecuteAction();
	}
	
	private List<Task> findTasks(/*staff class here*/ int staffID) {
		
		//Get list of staff of staff class type from restaurant
		
		//Get task list of staff object
		//Placeholder task list
		List<Task> tasks = new ArrayList<>();
		
		switch (staffID) {
			case 0:
				tasks.add(new GenericTask("task1"));
				tasks.add(new GenericTask("task2"));
				break;
				
			case 1:
				tasks.add(new GenericTask("task1"));
				tasks.add(new GenericTask("task2"));
				tasks.add(new GenericTask("task3"));
				tasks.add(new GenericTask("task4"));
				break;
			
			default:
				break;
		}
		
		return tasks;
	}
	
	private void displayTasks(List<Task> tasks, int staffID /*staff object here*/) {
		
		System.out.printf("Select a task for Staff: [%d]%n", staffID);
		
		//Display if no choices
		if (tasks.size() == 0) {
			System.out.printf("Staff: [%d] has no tasks", staffID);
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

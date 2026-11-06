package main.gameflow.turns;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.InputMismatchException;

import main.gameflow.*;
import main.restaurant.*;
import main.staff.*;
import main.staff.task.*;

public class ManagerStep extends TurnStep {
	
	private Map<Class<? extends Staff>, List<Staff>> staffList = new HashMap<>();
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		
		name = "Manager Selection";
		
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
			selectTarget(selectedTask);
			GameSession.session().placeholderTaskManager().queue(selectedTask);
		}
	}
		
	private void displayTasks(List<Task> tasks, Staff s) {
		
		System.out.printf("Select a task for [%s]%n", s.getClass().getSimpleName());
		
		//Display if no choices
		if (tasks.size() == 0) {
			System.out.printf("[%s] has no tasks", s.getClass().getSimpleName());
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
		
		List<Task> queuedTasks = GameSession.session().placeholderTaskManager().getQueuedTasks();
		
		do {
			//builds a reason should the task be invalid
			StringBuilder reason = new StringBuilder();
			
			selection = -1;
			
			try {
				selection = nextInt() - 1;
			}
			
			catch (InputMismatchException e){
				System.out.printf("Not a number. Please enter an integer between 1-%d%n", tasks.size());
				input.nextLine();
				continue;
			}
			
			if (selection < 0 || selection >= tasks.size()) {
				System.out.printf("There is no task %d. Please enter an integer between 1-%d%n", selection + 1, tasks.size());
				input.nextLine();
				continue;
			}
			
			//This version will check task valid every time this is called
			isValidChoice = tasks.get(selection).isTaskValid(reason, queuedTasks);
	
			//System.out.printf("Selected [%d]%n", selection + 1);
			
			if (!isValidChoice) {
				System.out.printf("Invalid: %s. Try another choice%n", reason.toString()); //need show reason for task invalid
			}
			
		} while (!isValidChoice);
		
		return tasks.get(selection);
	}
	
	//Asks which customer or order the task acts on, when there is more than one
	private void selectTarget(Task task) {
		//Not all tasks need to see the queue, but those that do require this param
		List<String> targets = task.targets(GameSession.session().placeholderTaskManager().getQueuedTasks());
		task.setTarget(0);

		if (targets.size() < 2) {
			return;
		}

		for (int i = 0; i < targets.size(); i++) {
			System.out.printf("[%d] %s%n", i + 1, targets.get(i));
		}
		System.out.printf("Who should %s act on? (1-%d)%n", task.taskName(), targets.size());

		while (true) {
			try {
				int choice = nextInt();

				if (choice >= 1 && choice <= targets.size()) {
					task.setTarget(choice - 1);
					return;
				}
			}
			catch (InputMismatchException e) {
				input.nextLine();
			}

			System.out.printf("Invalid: enter a number between 1-%d%n", targets.size());
		}
	}
}

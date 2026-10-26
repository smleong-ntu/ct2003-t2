package main.staff;

import java.util.List;
import java.util.ArrayList;
import main.events.*;

public class TaskManager implements GameEventListener {
	private List<Task> queuedTasks = new ArrayList<>();
	private List<Task> completedTasks = new ArrayList<>();
	
	public void queue(Task t) {
		queuedTasks.add(t);
		System.out.printf("Adding %s to task queue%n", t.taskName());
	}
	
	public Task dequeue() {
		
		Task t = queuedTasks.removeFirst();
		return t;
	}
	
	public void addAsCompletedTask(Task t) {
		completedTasks.add(t);
	}
	
	private void displayCompletedTasks() {
		completedTasks.forEach(task -> {
			System.out.printf("Task: %s", task.name);
		});
	}
	
	@Override
	public void onEvent(GameEvent event) {
		switch (event) {
			case OnTurn.Start onTurnStart -> { completedTasks.clear(); System.out.printf("Reset completed task for turn %d%n", onTurnStart.turn()); }
			case OnTurn.End onTurnEnd -> { System.out.printf("Completed tasks for turn %d%n", onTurnEnd.turn()); displayCompletedTasks(); }
			default -> {}
		}
	}
}

package main.gameflow.turns;

import java.util.InputMismatchException;
import main.gameflow.*;
import main.restaurant.*;

public class PolicyStep extends TurnStep{
	
	private Restaurant restaurant;
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		name = "Policy";
		
		//save reference to restaurant to get customers
		restaurant = GameSession.session().restaurant();
		
	}
	
	@Override
	public void execute() {
		
		HappyHour policy = restaurant.getHappyHour();
		
		//only run if policy has never been used
		if (!policy.hasBeenUsed()) {			
			
			System.out.printf("%s is available. Activate?%n", policy.getClass().getSimpleName());
			if (selectPolicy()) {
				policy.activate();
				System.out.printf("Policy %s activated%n", policy.getClass().getSimpleName());
			}
		}
		
		postExecuteAction();
	}
	
	private boolean selectPolicy() {
		//No policy has ever been used, ask for user input (shared reader from TurnStep)
		String[] choiceDisplay = {"Yes", "No"};
		
		for (int i = 0; i < choiceDisplay.length; i++) {
			System.out.printf("[%d] %s%n", i+1, choiceDisplay[i]);
		}
		
		boolean isValidChoice = false;
		int selection;
		
		do {		
			selection = -1;
			
			try {
				selection = nextInt() - 1;
			}
			
			catch (InputMismatchException e){
				System.out.printf("Not a number. Please enter an integer between 1-%d%n", choiceDisplay.length);
				input.nextLine();
				continue;
			}
			
			if (selection < 0 || selection >= choiceDisplay.length) {
				System.out.printf("There is no task %d. Please enter an integer between 1-%d%n", selection + 1, choiceDisplay.length);
				input.nextLine();
				continue;
			}
			
			isValidChoice = true;
			
		} while (!isValidChoice);
		
		return selection == 0;
	}
}

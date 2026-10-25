package main.gameflow.turns;

import java.util.Scanner;
import java.util.InputMismatchException;
import main.gameflow.TurnManager;

public class ManagerStep extends TurnStep {
	
	private Scanner input;
	
	@Override
	public void initialize(TurnManager manager) {
		super.initialize(manager);
		
		input = new Scanner(System.in);
		
		//save reference to restaurant or staff list <- since it is non dynamic
	}
	
	public void execute() {
		
		//Find Waiter choices
		displayChoices();
		int choice1 = selectChoice(5);
		
		//Find Chef choices
		displayChoices();
		int choice2 = selectChoice(3);
		
	}
	
	private void displayChoices() {
		System.out.println("[1] Placeholder selection");
	}
	
	//Retrieves selected choice by input
	private int selectChoice(int max) {
		
		boolean isValidChoice = false;
		int selection;
		
		do {
			selection = -1;
			
			try {
				selection = input.nextInt();			
			}
			
			catch (InputMismatchException e){
				System.out.printf("Mismatch input %s%n", e.getMessage());
				input.nextLine();
				continue;
			}
			
			if (selection != 1) {
				System.out.println("Invalid choice. Try again");
				continue;
			}
			
			System.out.printf("Selected [%d]%n", selection);
			isValidChoice = true;
		} while (!isValidChoice);
		
		return selection;
	}
}

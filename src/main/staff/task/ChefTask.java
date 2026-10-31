package main.staff.task;

import main.staff.Chef;

public abstract class ChefTask extends Task{
		protected Chef chef;
		
		public ChefTask(String name, Chef chef) {
			super(name);
			this.chef = chef;
		}
		
		@Override
		public void execute(int turn) {
			super.execute(turn);
			System.out.printf("%s done by >> %s [ID:%d] <<%n", name, chef.getClass().getSimpleName(), chef.getID());
		}
}

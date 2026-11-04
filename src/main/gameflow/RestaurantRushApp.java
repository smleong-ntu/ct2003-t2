package main.gameflow;

import main.gamesetting.*;

public class RestaurantRushApp {

	public static void main(String[] args) {
		// if we input data configs here, a session can be created
		GameSession.Create(GameDataType.STAGE_2, GameObjectiveType.DEFAULTS, 0); 
	}

}

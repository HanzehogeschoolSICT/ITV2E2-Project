package controller;

//Project libs
import view.Window;
import view.GameScreen;

//System libs
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	Window window;
	
	public static void main(String args[]){
		System.out.println("Starting ...");
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		this.window = new Window(this, primaryStage);
		this.window.init();
	}
	
	 /**
     * Setup the connection and creates the game
     * @param game the game of the game to be played
     * @param ipadres Ipadres of game server
     * @param portnumber Portnumber of gameserver
     * @param player If player is a AI or Player
     * @param playername The name of player
     * @return boolean True if game is created false is something went wrong
     */
	public boolean createGame(String game, String ipaddress, String portnumber, String playertype, String playername){
		return true;
	}

	 /**
     * Gets the Game screen from the game.
     * @return Returns the screen of the game
     */
	public GameScreen getGameScreen() {
		return null;
	}
}

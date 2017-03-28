package controller;

//Project libs
import view.Window;

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
     */
	public void createGame(String game, String ipadres, String portnumber, String playertype, String playername){
		return;
	}
}

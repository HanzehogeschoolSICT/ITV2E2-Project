package controller;

//System libraries
import javafx.application.Application;
import javafx.stage.Stage;
import model.Connection;
//Project libraries
import view.Window;

public class Main extends Application {
	private Window window;
	private Game game;
	private Connection connectionModel;
	
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
     * @param ipadres IP-address of game server
     * @param portnumber Port-number of game-server
     * @param player If player is a AI or Player
     * @param playername The name of player
     * @return boolean True if game is created false is something went wrong
     */
	public Game createGame(String game, String ipaddress, String portnumber, String playertype, String playername){
		boolean isHuman = playertype.equals("Player");
		this.game = new TicTacToe(this);
		this.game.setHuman(isHuman);
		this.connectionModel = new Connection(ipaddress, Integer.parseInt(portnumber));
		return this.game;
	}
	
	public Connection getConnection(){
		return this.connectionModel;
	}
	
	public Window getWindow(){
		return this.window;
	}
	
	public boolean challengePlayer(String playername){
		return false;
	}
}

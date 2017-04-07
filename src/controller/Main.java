package controller;

import java.io.IOException;

//System libraries
import javafx.application.Application;
import javafx.stage.Stage;
import model.io.Connection;
import model.io.GameObserver;
//Project libraries
import view.Window;

public class Main extends Application {
	private Window window;
	private Game game;
	private Connection connectionModel;
	private GameObserver gameObserver;
	
	public static void main(String args[]){
		System.out.println("Starting ...");
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		this.window = new Window(this, primaryStage);
		this.window.init();
	}
	
	@Override
	public void stop(){
	    System.out.println("Exiting game");
	    try {
			this.connectionModel.stop();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		this.setGame(game);
		
		this.game.setHuman(isHuman);
		this.connectionModel = new Connection(ipaddress, Integer.parseInt(portnumber));
		this.gameObserver = new GameObserver(this, this.game);
		this.connectionModel.setObserver(this.gameObserver);
		
		try {
			this.connectionModel.establish(playername);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERROR establishing game");
			return null;
		}

		return this.game;
	}
	
	private void setGame(String type){
		if (type.equals("Tic-tac-toe")){
			this.game = new TicTacToe(this);
		} else if (type.equals("Reversi")){
			this.game = new Othello(this);
		}
	}
	
	public Connection getConnection(){
		return this.connectionModel;
	}
	
	public Window getWindow(){
		return this.window;
	}
	
	
}

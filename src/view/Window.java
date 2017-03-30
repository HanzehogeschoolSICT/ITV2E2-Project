package view;

import controller.Game;
//Project libs
import controller.Main;

//System libs
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Window {
	private WindowScreen currentWindow; 
	private Main main;
	private Stage primaryStage;
	private Scene scene;
	private Game game;
	
	public Window(Main main, Stage primaryStage){
		this.main = main;
		this.primaryStage = primaryStage;
	}
	
	public void init(){
		primaryStage.setTitle("ITV2E2");
		//primaryStage.setScene(mainScene);
		primaryStage.setResizable(false);
		
		getMainMenu();
		primaryStage.show();
	}
	
	public void createGame(String gametype, String ipaddress, String portnumber, String playertype, String playername){
		Game game = this.main.createGame(gametype, ipaddress, portnumber, playertype, playername);
		if (game != null){
			this.game = game;
			getGameScreen();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("Could not start game");
			alert.setContentText("Maybe the server is down ? or you just entered the wrong details. Please try again.");
			alert.showAndWait();
		}
	}
	
	public void getMainMenu(){
		this.currentWindow = new MainMenu(this);
		this.scene = new Scene(this.currentWindow.getPane());
		primaryStage.setScene(this.scene);
	}
	
	public void getGameScreen(){
		this.currentWindow = new MainGame(this);
		this.scene = new Scene(this.currentWindow.getPane());
		primaryStage.setScene(this.scene);
	}
	
	public Game getGame(){
		return this.game;
	}
	
	public Main getMain(){
		return this.main;
	}
	
	public void update(){
		this.currentWindow.update();
		return;
	}
}
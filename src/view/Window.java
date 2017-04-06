package view;

//Project libs
import controller.Game;
import controller.Main;
import model.io.Connection;

//System libs
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Optional;

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
		primaryStage.getIcons().add(getIcon());
		//primaryStage.setScene(mainScene);
		//primaryStage.setResizable(false);
		//primaryStage.setHeight(650);
		//primaryStage.setWidth(1000);
		
		getMainMenu();
		primaryStage.show();
	}
	
	private Image getIcon() {
		FileInputStream file = null;
		try{
			file = new FileInputStream("src/view/images/logo.png");
		} catch (Exception e){
			System.out.println("Cant find image: src/view/images/logo.png");
		}
		Image logoImage = new Image(file);
		return logoImage;
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
	
	public Connection getConnection() {
		return this.main.getConnection();
	}
	
	public boolean getChallenged(String opponentname, int challengenumber){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Challenge request");
		alert.setHeaderText("You got challenged");
		alert.setContentText("You got challenged by player " + opponentname + ". You up for it ?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    return true;
		}else{
			return false;
		}
	}
	
	public void stopChallenge(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Challenge revoked");
		alert.setHeaderText("Challenge got revoked");
		alert.setContentText("A challenge you were involved in, either because you requested it or because you were requested, got revoked. This could be because the other player is already in a match or because he disconnected.");
		alert.showAndWait();
	}
}
package view;

import java.util.ArrayList;

import controller.Game;
import controller.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Connection;

public class MainGame implements WindowScreen {
	private Window window;
	private BorderPane pane;
	
	private TextArea playerlist;
	private TextField challegePlayername;
	private TextField inputOponent;
	private TextField inputScore;
	private TextField inputTurn;
	private Button buttonDefeat;
	
	public MainGame(Window window) {
		this.window = window;
		this.create();
	}

	public void create(){
		this.pane = new BorderPane();
		
		Pane paneLeft = createLeftmenu();
		this.pane.setLeft(paneLeft);
		
		Pane paneCenter = createCenterpane();
		this.pane.setCenter(paneCenter);
		
		Pane menuPane = createRightPane();
		this.pane.setRight(menuPane);
	}
	
	public void update(){
		Connection conn = this.window.getConnection();
		ArrayList<String> playerlist = conn.getPlayerList();
		if (playerlist != null){
			this.playerlist.setText(playerlist.toString());
		} else {
			this.playerlist.setText("");
		}
		
		Pane paneCenter = updateCenterpane();
		this.pane.setCenter(paneCenter);
		
		updateRightPane();
		
		updateDefeatButton();
	}
	
	private void updateRightPane(){
		Game game = this.window.getGame();
		if (game.getGameStart() == true){
			this.inputOponent.setText("Oponent name");
			this.inputScore.setText("0");
			if (game.getTurn() == true){
				this.inputTurn.setText("Yours");
			} else {
				this.inputTurn.setText("Oponents");
			} 
		} else {
			this.inputOponent.setText("");
			this.inputScore.setText("0");
			this.inputTurn.setText("");
		}
	}
	
	public Pane getPane(){
		return this.pane;
	}
	
	private Pane createLeftmenu(){
		VBox pane = new VBox(); 
		
		Pane panePlayerlist = createPlayerlist();
		pane.getChildren().add(panePlayerlist);
		
		Pane paneChallege = createChallegepane();
		pane.getChildren().add(paneChallege);
		
		return pane;
	}
	
	private Pane createPlayerlist(){
		VBox pane = new VBox();
		pane.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, null, null)));
		pane.setPadding(new Insets(5, 5, 5, 5));
		
		Label labelHeader = new Label("Players");
		labelHeader.setFont(new Font("Calibri", 16));
		pane.getChildren().add(labelHeader);
		
		this.playerlist = new TextArea();
		this.playerlist.setDisable(true);
		this.playerlist.setMaxWidth(120);
		pane.getChildren().add(this.playerlist);
		
		return pane;
	}
	
	private Pane createChallegepane(){
		VBox pane = new VBox();
		pane.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, null, null)));
		pane.setPadding(new Insets(5, 5, 5, 5));
		Label labelHeader = new Label("Challege");
		labelHeader.setFont(new Font("Calibri", 16));
		pane.getChildren().add(labelHeader);	
		Label labelPlayername = new Label("Playername");
		pane.getChildren().add(labelPlayername);
		this.challegePlayername = new TextField();
		pane.getChildren().add(this.challegePlayername);
		Button buttonChallege = new Button("Challege");
		buttonChallege.setOnAction(new ChallegeButtonHandlerClass());
		pane.getChildren().add(buttonChallege);
		return pane;
	}
	
	private void challegePlayer(){
		String playername = this.challegePlayername.getText();
		Main main = window.getMain();
		boolean challege = main.challengePlayer(playername);
		if (challege == true){
			//NEW GAME ?
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Sorry");
			alert.setHeaderText("Could not challege player");
			alert.setContentText("Either we could not challege the player or the player declined. Either way, sorry.");
			alert.showAndWait();
		}
	}
	
	private Pane createCenterpane(){
		VBox pane = new VBox(); 
		
		Game game = window.getGame();
		Pane gamePane = game.createGameScreen();
		pane.getChildren().add(gamePane);
		
		return pane;
	}

	private Pane updateCenterpane(){
		VBox pane = new VBox(); 
		
		Game game = window.getGame();
		Pane gamePane = game.updateGameScreen();
		pane.getChildren().add(gamePane);
		
		return pane;
	}
	
	private Pane createRightPane(){
		VBox pane = new VBox();
		pane.setPadding(new Insets(5, 5, 5, 5));
		
		Label labelOponent = new Label("Oponent");
		pane.getChildren().add(labelOponent);
		this.inputOponent = new TextField("0");
		this.inputOponent.setDisable(true);
		pane.getChildren().add(this.inputOponent);
		
		Label labelScore = new Label("Score");
		pane.getChildren().add(labelScore);
		this.inputScore = new TextField("0");
		this.inputScore.setDisable(true);
		pane.getChildren().add(this.inputScore);
		
		Label labelTurn = new Label("Turn");
		pane.getChildren().add(labelTurn);
		this.inputTurn = new TextField("");
		this.inputTurn.setDisable(true);
		pane.getChildren().add(this.inputTurn);
		
		this.buttonDefeat = new Button("Defeat");
		this.buttonDefeat.setDisable(true);
		this.buttonDefeat.setOnAction(new DefeatButtonHandlerClass());
		pane.getChildren().add(this.buttonDefeat);

		Button buttonMenu = new Button("Menu");
		buttonMenu.setOnAction(new MenuButtonHandlerClass());
		pane.getChildren().add(buttonMenu);
		
		return pane;
	}
	
	private void updateDefeatButton(){
		Game game = this.window.getGame();
		if (game.getGameStart() == true){
			this.buttonDefeat.setDisable(false);
		} else {
			this.buttonDefeat.setDisable(true);
		}
	}
	
	//Event Handlers
	class ChallegeButtonHandlerClass implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			//START
			System.out.println("Pressed challege button");
			challegePlayer();
		}
	}
	
	class DefeatButtonHandlerClass implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			//START
			System.out.println("Pressed defeat button");
			Game game = window.getGame();
			game.setDefeat();
		}
	}
	
	class MenuButtonHandlerClass implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			//START
			System.out.println("Pressed menu button");
			Game game = window.getGame();
			game.setDefeat();
			game.logout();
			window.getMainMenu();
		}
	}
}

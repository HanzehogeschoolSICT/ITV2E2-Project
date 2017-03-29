package view;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MainGame implements WindowScreen {
	private Window window;
	private BorderPane pane;
	
	private TextArea playerlist;
	private TextField challegePlayername;
	
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
	}
	
	public void update(){
		
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
		this.playerlist.setEditable(false);
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
		boolean challege = main.challegePlayer(playername);
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
	
	class ChallegeButtonHandlerClass implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			//START
			System.out.println("Pressed challege button");
			challegePlayer();
		}
	}
	
	private Pane createCenterpane(){
		VBox pane = new VBox(); 
		
		Game game = window.getGame();
		Pane gamePane = game.createGameScreen();
		pane.getChildren().add(gamePane);
		
		return pane;
	}
}

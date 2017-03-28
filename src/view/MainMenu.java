package view;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MainMenu implements WindowScreen {
	private Window window;
	private BorderPane pane;
	
	private TextField inputIPaddress;
	private TextField inputPortnumber;
	private TextField inputPlayername;
	private ChoiceBox<String> selectGameType;
	private ToggleGroup radioAI;
	
	
	public MainMenu(Window window){
		this.window = window;
		this.create();
	}
	
	public void create(){
		this.pane = new BorderPane();
		Pane pane2 = new VBox();
		pane2.setPadding(new Insets(15, 15, 15, 15)); 
		
		Label labelHeader = new Label("ITV2E-2");
		labelHeader.setFont(new Font("Calibri", 24));
		pane2.getChildren().add(labelHeader);
		
		Pane paneInputs = createInputs();
		pane2.getChildren().add(paneInputs);
		
		Pane paneGameType = createGameTypeSelect();
		pane2.getChildren().add(paneGameType);
		
		Pane paneAI = createAIRadios();
		pane2.getChildren().add(paneAI);
		
		Button startButton = createStartButton(); 
		pane2.getChildren().add(startButton);
		
		this.pane.setCenter(pane2);		
	}
	
	public void update(){
		return;
	}
	
	public Pane getPane(){
		return this.pane;
	}
	
	private Pane createInputs(){
		VBox boxInputs = new VBox();
		
		Label labelIpadres = new Label("IP-Address");
		boxInputs.getChildren().add(labelIpadres);
		this.inputIPaddress = new TextField();
		boxInputs.getChildren().add(this.inputIPaddress);
		
		Label labelPort = new Label("Port number");
		boxInputs.getChildren().add(labelPort);
		this.inputPortnumber = new TextField();
		boxInputs.getChildren().add(this.inputPortnumber);
		
		Label labelPlayerName = new Label("Playername");
		boxInputs.getChildren().add(labelPlayerName);
		this.inputPlayername = new TextField();
		boxInputs.getChildren().add(this.inputPlayername);
		
		return boxInputs;
	}
	
	private Pane createGameTypeSelect(){
		VBox boxGameType = new VBox();
		Label GameType = new Label("Game");
		boxGameType.getChildren().add(GameType);
		
		this.selectGameType = new ChoiceBox<String>(FXCollections.observableArrayList("Tic-Tac-Toe", "Othello"));
		this.selectGameType.getSelectionModel().selectFirst();
		boxGameType.getChildren().add(this.selectGameType);
		
		return boxGameType; 
	}
	
	private Pane createAIRadios(){
		VBox boxRadios = new VBox();
		
		Label Ai = new Label("Player or AI");
		boxRadios.getChildren().add(Ai);
		
		HBox boxAI = new HBox();
		this.radioAI = new ToggleGroup();

		RadioButton radioPlayer = new RadioButton("Player");
		radioPlayer.setUserData("Player");
		radioPlayer.setToggleGroup(this.radioAI);
		radioPlayer.setSelected(true);
		boxAI.getChildren().add(radioPlayer);

		RadioButton radioAI = new RadioButton("AI");
		radioAI.setUserData("AI");
		radioAI.setToggleGroup(this.radioAI);
		boxAI.getChildren().add(radioAI);
		
		boxRadios.getChildren().add(boxAI);
		return boxRadios;
	}
	
	public void createGame(){
		String ipaddress = this.inputIPaddress.getText();
		String portnumber = this.inputPortnumber.getText();
		String playername = this.inputPlayername.getText();
		String game = this.selectGameType.getValue();
		String playertype = this.radioAI.getSelectedToggle().getUserData().toString();
		
		if (ipaddress.length() > 6 && portnumber.length() > 2 && playername.length() > 5  && game != null && playertype != null){
			window.createGame(game, ipaddress, portnumber, playertype, playername);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("Missing details");
			alert.setContentText("Did you enter all the field ? Please try again.");
			alert.showAndWait();
		}
	}
	
	public Button createStartButton(){
		Button startButton = new Button("Start");
		startButton.setOnAction(new StartHandlerClass());
		return startButton;
	}
	
	class StartHandlerClass implements EventHandler<ActionEvent> {

		@Override

		public void handle(ActionEvent e) {
			//START
			System.out.println("Pressed start button");
			createGame();
		}

	}
}

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MainMenu extends AbstractWindowScreen {
	private Window window;
	private BorderPane pane;
	
	private TextField inputIPaddress;
	private TextField inputPortnumber;
	private TextField inputPlayername;
	private TextField inputAIDepth;
	private ChoiceBox<String> selectGameType;
	private ToggleGroup radioAI;
	
	
	public MainMenu(Window window){
		this.window = window;
		this.create();
	}
	
	public void create(){
		this.pane = new BorderPane();
		this.pane.setBackground(this.getBackground());
		this.pane.setTop(this.getHeader());
		this.pane.setCenter(this.createPane());		
	}
	
	private Pane createPane(){
		Pane pane = new VBox();
		pane.setPadding(new Insets(100, 300, 150, 300)); 
		
		Pane pane2 = new VBox();
		pane2.setPadding(new Insets(5, 5, 5, 5)); 
		pane2.setStyle("-fx-border-color: gray;-fx-border-width: 1;-fx-border-style: solid;");
		pane.getChildren().add(pane2);
		
		Pane paneInputs = createInputs();
		pane2.getChildren().add(paneInputs);
		
		Pane paneGameType = createGameTypeSelect();
		pane2.getChildren().add(paneGameType);
		
		Pane paneAI = createAIRadios();
		pane2.getChildren().add(paneAI);
		
		Pane paneAIDepth = createAIDepth();
		pane2.getChildren().add(paneAIDepth);
		
		Button startButton = createStartButton(); 
		pane2.getChildren().add(startButton);
		
		return pane;
	}
	
	public Pane getHeaderButtons(){
		Pane pane = new Pane();
		return pane;
	}
	
	public void update(){
		return;
	}
	
	public Pane getPane(){
		return this.pane;
	}
	
	private Pane createInputs(){
		VBox boxInputs = new VBox();
		
		Label labelIpadres = new Label("Server IP-Address ");
		labelIpadres.setFont(Font.font(null, FontWeight.BOLD, 12));
		boxInputs.getChildren().add(labelIpadres);
		this.inputIPaddress = new TextField();
		//this.inputIPaddress.setText("145.33.225.170");
		this.inputIPaddress.setText("127.0.0.1");
		boxInputs.getChildren().add(this.inputIPaddress);
		
		Label labelPort = new Label("Server port number ");
		labelPort.setFont(Font.font(null, FontWeight.BOLD, 12));
		boxInputs.getChildren().add(labelPort);
		this.inputPortnumber = new TextField();
		this.inputPortnumber.setText("7789");
		boxInputs.getChildren().add(this.inputPortnumber);
		
		Label labelPlayerName = new Label("Playername ");
		labelPlayerName.setFont(Font.font(null, FontWeight.BOLD, 12));
		boxInputs.getChildren().add(labelPlayerName);
		this.inputPlayername = new TextField();
		boxInputs.getChildren().add(this.inputPlayername);
		
		return boxInputs;
	}
	
	private Pane createGameTypeSelect(){
		VBox boxGameType = new VBox();
		Label GameType = new Label("Gametype ");
		GameType.setFont(Font.font(null, FontWeight.BOLD, 12));
		boxGameType.getChildren().add(GameType);
		
		this.selectGameType = new ChoiceBox<String>(FXCollections.observableArrayList("Tic-tac-toe", "Reversi"));
		this.selectGameType.getSelectionModel().selectFirst();
		boxGameType.getChildren().add(this.selectGameType);
		
		return boxGameType; 
	}
	
	private Pane createAIRadios(){
		VBox boxRadios = new VBox();
		boxRadios.setPadding(new Insets(0, 0, 5, 0));
		
		Label Ai = new Label("Player or AI");
		Ai.setFont(Font.font(null, FontWeight.BOLD, 12));
		boxRadios.getChildren().add(Ai);
		
		GridPane boxAI = new GridPane();
		boxAI.setHgap(10);
		this.radioAI = new ToggleGroup();

		RadioButton radioPlayer = new RadioButton("Player");
		radioPlayer.setUserData("Player");
		radioPlayer.setToggleGroup(this.radioAI);
		radioPlayer.setSelected(true);
		boxAI.add(radioPlayer, 0, 0);

		RadioButton radioAI = new RadioButton("AI");
		radioAI.setUserData("AI");
		radioAI.setToggleGroup(this.radioAI);
		boxAI.add(radioAI, 1, 0);
		
		boxRadios.getChildren().add(boxAI);
		return boxRadios;
	}
	
	private Pane createAIDepth(){
		VBox pane = new VBox();
		
		Label labelPlayerName = new Label("AI Depth ");
		labelPlayerName.setFont(Font.font(null, FontWeight.BOLD, 12));
		pane.getChildren().add(labelPlayerName);
		this.inputAIDepth = new TextField();
		inputAIDepth.setText("6");
		pane.getChildren().add(this.inputAIDepth);
		
		return pane;
	}
	
	public void createGame(){
		String ipaddress = this.inputIPaddress.getText();
		String portnumber = this.inputPortnumber.getText();
		String playername = this.inputPlayername.getText();
		String game = this.selectGameType.getValue();
		String playertype = this.radioAI.getSelectedToggle().getUserData().toString();
		String aidepth = this.inputAIDepth.getText();
		
		if (ipaddress.length() > 6 && portnumber.length() > 1 && playername.length() > 2  && game != null && playertype != null){
			window.createGame(game, ipaddress, portnumber, playertype, playername, aidepth);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setHeaderText("Missing details");
			alert.setContentText("Did you enter all the field ? Please try again.");
			alert.showAndWait();
		}
	}
	
	public Button createStartButton(){
		Button startButton = new Button("Login");
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

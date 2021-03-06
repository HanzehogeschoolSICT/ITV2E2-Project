package view;

import controller.Game;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.io.Connection;

public class MainGame extends AbstractWindowScreen {
	private Window window;
	private BorderPane pane;
	
	private TextArea playerlist;
	private int playerlistCount;
	private ChoiceBox<String> challegePlayername;
	private Label inputOponent;
	private Label inputScore;
	private Label inputTurn;
	private Button buttonDefeat;
	private ImageView playerIcon;
	
	public MainGame(Window window) {
		this.window = window;
		this.create();
	}

	public void create(){
		this.pane = new BorderPane();
		this.pane.setBackground(this.getBackground());
		
		this.pane.setTop(this.getHeader());
		this.pane.setLeft(createLeftmenu());
		this.pane.setCenter(createCenterpane());
		this.pane.setRight(createRightPane());
	}
	
	public Pane getHeaderButtons(){
		Pane pane = new Pane();
		Button buttonMenu = new Button("Menu");
		buttonMenu.setOnAction(new MenuButtonHandlerClass());
		pane.getChildren().add(buttonMenu);
		return pane;
	}
	
	public void update(){
		updateLeftPane();
		
		Pane paneCenter = getCenterpane();
		this.pane.setCenter(paneCenter);
		
		updateRightPane();
		updateDefeatButton();
	}
	

	private Pane getCenterpane(){
		Pane paneCenter = updateCenterpane();

		Label text = getGameStatusLabel();
		if (text != null){
			StackPane pane = new StackPane();
			pane.getChildren().add(paneCenter);
			pane.setAlignment(Pos.CENTER);
			
			pane.getChildren().add(text);
			
			return pane;
		} else {
			return paneCenter;
		}
	}
	
	private Label getGameStatusLabel() {
		Game game = this.window.getGame();
		Game.GameStatus gameStatus = game.getGameStatus();

		Label labelText = new Label();
		labelText.setFont(new Font("Calibri", 200));
		labelText.setRotate(45);
		
		if (gameStatus == Game.GameStatus.LOSE){
			labelText.setText("Lost");
			labelText.setTextFill(Color.YELLOW);
			return labelText;
		} else if (gameStatus == Game.GameStatus.WIN){
			labelText.setText("Won");
			labelText.setTextFill(Color.GREEN);
			return labelText;
		} else if (gameStatus == Game.GameStatus.DRAW){
			labelText.setText("Tie");
			labelText.setTextFill(Color.GRAY);
			return labelText;
		}
		return null;
	}
	
	public Pane getPane(){
		return this.pane;
	}
	
	private Pane createLeftmenu(){
		VBox pane = new VBox(); 
		
		Game game = window.getGame();
		Label gametype = new Label(game.getGameType());
		gametype.setFont(new Font("Calibri", 24));
		pane.getChildren().add(gametype);
		
		Pane paneGameinfo = createLeftPaneGameInfo();
		pane.getChildren().add(paneGameinfo);
		
		Pane panePlayerlist = createPlayerlist();
		pane.getChildren().add(panePlayerlist);
		
		Pane paneChallege = createChallegepane();
		pane.getChildren().add(paneChallege);
		
		return pane;
	}
	
	private void updateLeftPane(){
		Game game = window.getGame();
		String players = "";
		for(String player : game.getPlayerList()){
			players = players + player + "\n";
		}
		this.playerlist.setText(players);
		this.playerlistCount = this.playerlistCount + 1;
		if (this.playerlistCount > 16){
			Connection conn = this.window.getConnection();
			conn.getPlayerList();
			this.playerlistCount = 0;
		}
		this.challegePlayername.setItems(FXCollections.observableArrayList(game.getPlayerList()));
	}
	
	private Pane createLeftPaneGameInfo() {
		VBox pane = new VBox();
		pane.setPadding(new Insets(5, 5, 10, 5));
		
		Label labelHeader = new Label("Server setup");
		labelHeader.setFont(new Font("Calibri", 16));
		pane.getChildren().add(labelHeader);
		
		pane.getChildren().add(createLeftPaneServerLabel());

		pane.getChildren().add(createLeftPanePlayerTypeLabel());
		
		pane.getChildren().add(createLeftPanePlayernameLabel());
		
		return pane;
	}

	private Pane createLeftPaneServerLabel(){
		VBox pane = new VBox();
		Connection conn = this.window.getConnection();
		
		Label labelServer = new Label("IP Address ");
		labelServer.setFont(Font.font(null, FontWeight.BOLD, 12));
		pane.getChildren().add(labelServer);
		
		Label labelIpaddress = new Label(conn.getServerIpAddress());
		pane.getChildren().add(labelIpaddress);
		
		return pane;
	}
	
	private Pane createLeftPanePlayerTypeLabel(){
		VBox pane = new VBox();
		Game game = window.getGame();
		
		String playertype = "";
		if (game.getHuman() == true){
			playertype = "You";
		} else {
			playertype = "AI";
		}
		
		Label labelPlayertype = new Label("Player type ");
		labelPlayertype.setFont(Font.font(null, FontWeight.BOLD, 12));
		pane.getChildren().add(labelPlayertype);
		
		Label labelType = new Label(playertype);
		pane.getChildren().add(labelType);
		
		return pane;
	}
	
	private Pane createLeftPanePlayernameLabel(){
		VBox pane = new VBox();
		Connection conn = this.window.getConnection();
		
		Label labelPlayername = new Label("Playername ");
		labelPlayername.setFont(Font.font(null, FontWeight.BOLD, 12));
		pane.getChildren().add(labelPlayername);
		
		Label name = new Label(conn.getPlayerName());
		pane.getChildren().add(name);
		
		return pane;
	}
	
	private Pane createPlayerlist(){
		VBox pane = new VBox();
		//pane.setBorder(new Border(new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, null, null)));
		pane.setPadding(new Insets(5, 5, 10, 5));
		
		Connection conn = window.getConnection();
		conn.getPlayerList();
		this.playerlistCount = 0;
		
		Label labelHeader = new Label("Players");
		labelHeader.setFont(new Font("Calibri", 16));
		pane.getChildren().add(labelHeader);
		
		pane.getChildren().add(createPlayerListTextArea());
		
		Button refreshPlayerlist = new Button("Refresh");
		refreshPlayerlist.setOnAction(new RefreshPlayerlistButtonHandlerClass());
		pane.getChildren().add(refreshPlayerlist);
		
		return pane;
	}
	
	private Pane createPlayerListTextArea(){
		VBox pane = new VBox();
		this.playerlist = new TextArea();
		this.playerlist.setEditable(false);
		this.playerlist.setMaxWidth(120);
		pane.getChildren().add(this.playerlist);
		return pane;
	}
	
	private Pane createChallegepane(){
		VBox pane = new VBox();
		pane.setPadding(new Insets(5, 5, 5, 5));
		Label labelHeader = new Label("Challenge");
		labelHeader.setFont(new Font("Calibri", 16));
		pane.getChildren().add(labelHeader);	
		Label labelPlayername = new Label("Playername");
		labelPlayername.setFont(Font.font(null, FontWeight.BOLD, 12));
		pane.getChildren().add(labelPlayername);
		pane.getChildren().add(createChallengeChoicebox());
		Button buttonChallege = new Button("Challenge");
		buttonChallege.setOnAction(new ChallegeButtonHandlerClass());
		pane.getChildren().add(buttonChallege);
		return pane;
	}
	
	private Pane createChallengeChoicebox(){
		VBox pane = new VBox();
		this.challegePlayername = new ChoiceBox<String>();
		this.challegePlayername.getSelectionModel().selectFirst();
		pane.getChildren().add(this.challegePlayername);
		
		return pane;
	}
	
	private void challengePlayer(){
		String playername = this.challegePlayername.getValue();
		Game game = this.window.getGame();
		boolean challenge = game.challengePlayer(playername);
		if (challenge == true){
			//NEW GAME ?
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Sorry");
			alert.setHeaderText("Could not challenge player");
			alert.setContentText("Either we could not challenge the player or the player declined. Either way, sorry.");
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
		pane.setPadding(new Insets(5, 5, 5, 50));
		pane.setAlignment(Pos.TOP_RIGHT);
		
		Label labelHeader = new Label("Match info");
		labelHeader.setFont(new Font("Calibri", 16));
		pane.getChildren().add(labelHeader);
		
		pane.getChildren().add(createRightPaneOponentName());
		pane.getChildren().add(createRightPaneScore());
		pane.getChildren().add(createRightPaneTurn());
		pane.getChildren().add(createRightPaneIcon());
		
		this.buttonDefeat = new Button("Defeat");
		this.buttonDefeat.setDisable(true);
		this.buttonDefeat.setOnAction(new DefeatButtonHandlerClass());
		pane.getChildren().add(this.buttonDefeat);
		
		return pane;
	}
	
	private void updateRightPane(){
		Game game = this.window.getGame();
		if (game.getGameStart() == true){
			this.inputOponent.setText(game.getOpponent());
			this.inputScore.setText(((Integer) game.getScorePlayer()).toString());
			if (game.getTurn() == true){
				this.inputTurn.setText("Yours");
			} else {
				this.inputTurn.setText("Oponents");
			} 
			updateRightPaneIcon();
		} else {
			this.inputOponent.setText("");
			this.inputScore.setText("");
			this.inputTurn.setText("");
		}
	}
	
	private Pane createRightPaneIcon(){
		VBox pane = new VBox();
		pane.setAlignment(Pos.TOP_RIGHT);
		
		Label labelPlayerIcon = new Label("Your icon");
		labelPlayerIcon.setFont(Font.font(null, FontWeight.BOLD, 12));
		pane.getChildren().add(labelPlayerIcon);
		
		this.playerIcon = new ImageView();
		this.playerIcon.setFitHeight(25);
		this.playerIcon.setPreserveRatio(true);
		pane.getChildren().add(this.playerIcon);
		
		return pane;
	}
	
	private void updateRightPaneIcon(){
		Game game = this.window.getGame();
		GameScreen screen = game.getGameScreen();
		Image image = null;
		if (game.getPlayerFirstMove() == true){
			image = screen.getPlayerImage();
		} else {
			image = screen.getOponentImage();
		}
		this.playerIcon.setImage(image);
	}
	
	private Pane createRightPaneOponentName(){
		VBox pane = new VBox();
		pane.setAlignment(Pos.TOP_RIGHT);
		
		Label labelOponent = new Label(" Oponentname");
		labelOponent.setFont(Font.font(null, FontWeight.BOLD, 12));
		pane.getChildren().add(labelOponent);
		
		this.inputOponent = new Label("");
		pane.getChildren().add(this.inputOponent);
		
		return pane;
	}
	
	private Pane createRightPaneScore(){
		VBox pane = new VBox();
		pane.setAlignment(Pos.TOP_RIGHT);
		
		Label labelScore = new Label(" Score");
		labelScore.setFont(Font.font(null, FontWeight.BOLD, 12));
		pane.getChildren().add(labelScore);
	
		this.inputScore = new Label("");
		pane.getChildren().add(this.inputScore);
		
		return pane;
	}
	
	private Pane createRightPaneTurn(){
		VBox pane = new VBox();
		pane.setAlignment(Pos.TOP_RIGHT);
		
		Label labelTurn = new Label(" Turn");
		labelTurn.setFont(Font.font(null, FontWeight.BOLD, 12));
		pane.getChildren().add(labelTurn);
		
		this.inputTurn = new Label("");
		pane.getChildren().add(this.inputTurn);
		
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
			System.out.println("Pressed challenge button");
			challengePlayer();
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
	
	class RefreshPlayerlistButtonHandlerClass implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent e) {
			//START
			System.out.println("Pressed refresh player button");
			Connection conn = window.getConnection();
			conn.getPlayerList();
		}
	}
}

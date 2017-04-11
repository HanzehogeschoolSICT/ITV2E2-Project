package controller;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import model.Board;
import model.GameAI;
import model.io.Connection;
import view.GameScreen;
import view.Window;

abstract class AbstractGame implements Game{
	protected GameScreen gamescreen;
	protected Board board;
	protected String gameType;
	protected Main main;
	protected boolean isHuman;
	protected GameAI gameAI;
	protected boolean myTurn;
	protected ArrayList<String> playerList;
	protected String opponent;
	private String playerFirstMove;
	protected GameStatus status;
	
	public GameScreen getGameScreen(){
		return this.gamescreen;
	}

	public AbstractGame() {
		this.status = GameStatus.NOT_STARTED;
	}

	public Pane updateGameScreen(){
		return this.gamescreen.update();
	}
	
	public Pane createGameScreen(){
		return this.gamescreen.create();
	}
	
	public Board getBoard(){
		return this.board;
	}
	
	public void setMove(int y, int x){
		if(this.myTurn){
			if(this.board.isValid(y, x)){
				Connection conn = this.main.getConnection();
				Integer move = (y * this.board.getColumns()) + x;
				conn.move(move);
				this.setTurn(false);			
			}
		}
		return;
	}
	
	public void serverMove(int y, int x, boolean yourTurn){
		if(yourTurn){
			this.board.set(1, y, x);
		}else{
			this.board.set(2, y, x);
		}
		this.updateView();
		return;
	}
	
	public void updateView(){
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	Window window = main.getWindow();
            	window.update();
            }
		});
	}
	
	public void setDefeat(){
		Connection conn = this.main.getConnection();
		conn.forfeit();
		this.setGameStatus(GameStatus.LOSE);
		this.updateView();
	}
	
	public void logout(){
		Connection conn = this.main.getConnection();
		conn.logout();
	}
	
	public String getGameType(){
		return this.gameType;
	}
	
	public void setHuman(boolean human){
		this.isHuman = human;
	}
	
	public boolean getHuman(){
		return this.isHuman;
	}
	
	public GameStatus getGameStatus(){
		return this.status;
	}

	public void setGameStatus(GameStatus status){
	    this.status = status;
	    updateView();
	}
	public boolean getGameStart(){
		if(this.status == GameStatus.STARTED){
			return true;
		}
		return false;
	}

	public boolean getTurn(){
		return this.myTurn;
	}
	
	public void setTurn(boolean turn){		
    	this.myTurn = turn;
    	this.updateView();
    	if (this.isHuman == false && this.myTurn == true){
    		this.gameAI.move();
    	} else if (this.myTurn == true && this.isHuman == true){
    		Platform.runLater(new Runnable() {
                @Override
                public void run() {
            		gamescreen.playTurnSound();
                }
    		});
    	}
	}

	public void getChallenged(String opponentname, int challengenumber){
		Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	boolean accept = main.getWindow().getChallenged(opponentname, challengenumber);
        		if(accept){
        			Connection conn = main.getConnection();
        		    conn.accept_challenge(challengenumber);
        		    
        		}
            }
        });
	}
	
	public void stopChallenge(){
		this.main.getWindow().stopChallenge();
		return;
	}
	
	public boolean challengePlayer(String playername){
		Connection connection = this.main.getConnection();
		connection.challenge(playername, this.getGameType());
		return true;
	}
	
	public void resetBoard(){
		this.createEmptyBoard();
		this.setTurn(false);
	}
	
	public void createEmptyBoard(){
		this.board.createEmptyBoard();
	}
	
	public void setPlayerList(ArrayList<String> players) {
		this.playerList = players;
		updateView();
	}
	
	public ArrayList<String> getPlayerList() {
		return this.playerList;
	}
	
	public void setOpponent(String opponent){
		this.opponent = opponent;
	}
	
	public String getOpponent(){
		return this.opponent;
	}
	
	public void setPlayerFirstMove(String playername){
		this.playerFirstMove = playername;
	}
	
	public boolean getPlayerFirstMove(){
		if(this.playerFirstMove.equals(this.opponent)){
			return false;
		} else {
			return true;
		}
	}
}
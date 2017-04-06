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
	/**
	 * Status -1 	-> 	Lose
	 * Status 0 	-> 	Not started
	 * Status 1 	-> 	Started
	 * Status 2 	-> 	Won
	 * Status 3 	-> 	Draw
	 */
	protected int gameStatus = 0;
	
	public Pane updateGameScreen(){
		return this.gamescreen.update();
	}
	
	public Pane createGameScreen(){
		return this.gamescreen.create();
	}
	
	public Board getBoard(){
		return this.board;
	}
	
	public boolean getGameStart(){
		 if(this.gameStatus == 1){
		 	return true;
		 }
		 return false;
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
		return;
	}
	
	public void logout(){
		return;
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
	
	public int getGameStatus(){
		return this.gameStatus;
	}

	public void setGameStatus(int status){
	    this.gameStatus = status;
	    updateView();
	}
	
	public boolean getTurn(){
		return this.myTurn;
	}
	
	public void setTurn(boolean turn){		
    	this.myTurn = turn;
    	this.updateView();
    	if (this.isHuman == false){
    		this.gameAI.move();
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
		this.board.createEmptyBoard();
		this.setTurn(false);
	}
	
	
	public void setPlayerList(ArrayList<String> players) {
		this.playerList = players;	
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
}
package controller;

import javafx.scene.layout.Pane;
import model.Board;
import model.io.Connection;
import view.GameScreen;
import view.Window;

abstract class AbstractGame implements Game{
	protected GameScreen gamescreen;
	protected Board board;
	protected String gameType;
	protected Main main;
	protected boolean isHuman;
	protected boolean myTurn;
	
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
		return true;
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
	
	public void serverMove(int y, int x){
		if(!this.myTurn){
			this.board.set(2, y, x);
		}else{
			this.board.set(1, y, x);
		}
		this.updateView();
		return;
	}
	
	public void updateView(){
		Window window = this.main.getWindow();
		window.update();
		return;
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
		return 1;
	}
	
	public boolean getTurn(){
		return this.myTurn;
	}
	
	public boolean setTurn(boolean turn){
		this.myTurn = turn;
		return this.myTurn;
	}
	
	public void getChallenged(String opponentname, int challengenumber){
		boolean accept = this.main.getWindow().getChallenged(opponentname, challengenumber);
		if(accept){
			Connection conn = this.main.getConnection();
		    conn.accept_challenge(challengenumber);
		}
		return;
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
	}
}
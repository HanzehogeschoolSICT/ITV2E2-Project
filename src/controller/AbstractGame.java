package controller;

import javafx.scene.layout.Pane;
import model.Board;
import model.Connection;
import view.GameScreen;
import view.Window;

abstract class AbstractGame implements Game{
	protected GameScreen gamescreen;
	protected Board board;
	protected String gameType;
	protected Main main;
	protected boolean isHuman;
	
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
		if(this.board.isValid(y, x)){
			Connection conn = this.main.getConnection();
			conn.move(y, x);
			this.board.set(1, y, x);
			Window window = this.main.getWindow();
			window.update();
		}
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
		return true;
	}
}
package controller;

import javafx.scene.layout.Pane;

public class Othello implements Game{
	public Othello(){}
	
	public int getBoardRows(){
		return 0;
	}
	
	public int getBoardColumns(){
		return 0;
	}
	
	public Pane createGameScreen(){
		return null;
	}

	public Pane updateGameScreen(){
		return null;
	}
	
	public boolean getGameStart(){
		return false;
	}
	
	public void setMove(int y, int x){
		return;
	}
}

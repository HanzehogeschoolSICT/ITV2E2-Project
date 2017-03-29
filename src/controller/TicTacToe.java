package controller;

import javafx.scene.layout.Pane;
import model.Board;
import view.GameScreen;
import view.TicTacToeScreen;

public class TicTacToe implements Game{
	private GameScreen gamescreen;
	private Board board;
	
	public TicTacToe(){
		this.gamescreen = new TicTacToeScreen(this);
		this.board = new Board(3,3);
	}
	
	public Pane updateGameScreen(){
		return this.gamescreen.update();
	}
	
	public Pane createGameScreen(){
		return this.gamescreen.create();
	}

	public boolean challegePlayer(String playername){
		return false;
	}
	
	public int[] getBoardSize(){
		int[] size = null;
		size['x'] = this.board.getColumns();
		size['y'] = this.board.getRows();
		return size;
	}
}
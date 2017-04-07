package controller;

import model.Board;
import view.OthelloScreen;
import model.OthelloAI;

public class Othello extends AbstractGame{

    public Othello(Main main) {
    	this.main = main;
		this.gamescreen = new OthelloScreen(this);
		this.gameType = "Reversi";
		this.board = new Board(8,8);
		this.gameAI = null;
    }
    
    @Override
    public void createEmptyBoard(){
    	this.board.createEmptyBoard();
    	if (this.getPlayerFirstMove() == true){
    		this.board.set(2, 3, 3);
    		this.board.set(2, 4, 4);
    		this.board.set(1, 3, 4);
    		this.board.set(1, 4, 3);
    	} else {
    		this.board.set(1, 3, 3);
    		this.board.set(1, 4, 4);
    		this.board.set(2, 3, 4);
    		this.board.set(2, 4, 3);
    	}
    }
}
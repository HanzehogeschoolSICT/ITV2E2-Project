package controller;

import model.Board;
import view.OthelloScreen;

public class Othello extends AbstractGame{


    public Othello(Main main) {
    	this.main = main;
		this.gamescreen = new OthelloScreen(this);
		this.board = new Board(8,8);
		this.gameType = "Reversi";
    }

}

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
}
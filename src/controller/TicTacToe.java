package controller;

import model.Board;
import model.TicTacToeAI;
import view.TicTacToeScreen;

public class TicTacToe extends AbstractGame{
	public TicTacToe(Main main){
		this.main = main;
		this.gamescreen = new TicTacToeScreen(this);
		this.board = new Board(3,3);
		this.gameType = "Tic-tac-toe";
		this.gameAI = new TicTacToeAI(this);
	}
}
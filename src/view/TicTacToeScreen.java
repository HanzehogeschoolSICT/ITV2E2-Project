package view;

import controller.Game;
import controller.TicTacToe;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class TicTacToeScreen implements GameScreen{
	private Game game;
	private BorderPane pane;
	
	public TicTacToeScreen(TicTacToe ticTacToe) {
		this.game = ticTacToe;
	}

	public Pane create(){
		this.pane = new BorderPane();
		
		Label labelHeader = new Label("Tic-Tac-Toe");
		labelHeader.setFont(new Font("Calibri", 24));
		this.pane.setCenter(labelHeader);
		
		return this.pane;
	}
	
	public Pane update(){
		return null;
	}
}

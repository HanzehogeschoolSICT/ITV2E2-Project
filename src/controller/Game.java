package controller;

import javafx.scene.layout.Pane;
import model.Board;
import view.GameScreen;

public interface Game {
	public int[] getBoardSize();
	
	public Pane createGameScreen();
	public Pane updateGameScreen();

	public boolean challegePlayer(String playername);
}

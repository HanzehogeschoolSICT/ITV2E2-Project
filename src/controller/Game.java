package controller;

import javafx.scene.layout.Pane;
import model.Board;
import view.GameScreen;

public interface Game {
	public boolean getGameStart(); 
	public Pane createGameScreen();
	public Pane updateGameScreen();
	public int getBoardRows();
	public int getBoardColumns();
	public void setMove(int y, int x);
}

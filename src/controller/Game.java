package controller;

import javafx.scene.layout.Pane;
import model.Board;

public interface Game {
	public boolean getGameStart(); 
	public Pane createGameScreen();
	public Pane updateGameScreen();
	public Board getBoard();
	public void setMove(int y, int x);
	public void setDefeat();
	public void logout();
}

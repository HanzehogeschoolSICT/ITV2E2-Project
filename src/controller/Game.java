package controller;

import java.util.ArrayList;

import javafx.scene.layout.Pane;
import model.Board;
import view.GameScreen;

public interface Game {
	public boolean getGameStart(); 
	public Pane createGameScreen();
	public Pane updateGameScreen();
	public GameScreen getGameScreen();
	public Board getBoard();
	public void setMove(int y, int x);
	public void setDefeat();
	public void logout();
	public void setHuman(boolean human);
	public boolean getHuman();
	public boolean getTurn();
	public void setTurn(boolean turn);
	public String getGameType(); 
	public boolean challengePlayer(String playername);
	public void getChallenged(String opponentname, int challengenumber);
	public void serverMove(int y, int x, boolean yourTurn);
	public void setPlayerList(ArrayList<String> players);
	public ArrayList<String> getPlayerList();
	public void stopChallenge();
	public void resetBoard();
	public String getOpponent();
	public void setOpponent(String opponent);
	public void setPlayerFirstMove(String playername);
	public boolean getPlayerFirstMove();
	public GameStatus getGameStatus();
	public void setGameStatus(GameStatus status);
	public enum GameStatus {
		NOT_STARTED, STARTED,
		WIN, LOSE, DRAW
	}
	
	public int getScorePlayer();
	public int getScoreOpponent();
	public void setScore();
	public void setAIDepth(int depth);
	public boolean isValid(int x, int y);
}

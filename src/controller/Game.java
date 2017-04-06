package controller;

import java.util.ArrayList;

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
	public void setHuman(boolean human);
	public boolean getHuman();
	public int getGameStatus();
	
	public boolean getTurn();
	public void setTurn(boolean turn);
	
	public String getGameType(); 
	public boolean challengePlayer(String playername);
	public void getChallenged(String opponentname, int challengenumber);
	
	public void serverMove(int y, int x, boolean yourTurn);
	public void setPlayerList(ArrayList<String> players);
	public ArrayList<String> getPlayerList();
	public void stopChallenge();
	public void setGameStatus(int status);
	public void resetBoard();
	public String getOpponent();
	public void setOpponent(String opponent);
}

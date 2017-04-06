package model.io;

import java.util.ArrayList;

import model.io.Connection.Observer;

public class GameObserver implements Observer{

	@Override
	public void onMove(String player, String details, int move) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onYourTurn(String comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChallenge(String opponentName, int challengeNumber, String gameType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChallengeCancelled(int challengeNumber, String comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHelp(String info) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameList(ArrayList<String> games) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerList(ArrayList<String> players) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameEnd(int statusCode, String comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String comment) {
		// TODO Auto-generated method stub
		
	}

}

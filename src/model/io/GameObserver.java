package model.io;

import java.util.ArrayList;

import controller.Game;
import controller.Main;
import model.Board;
import model.io.Connection.Observer;

public class GameObserver implements Observer{
	private Main main;
	private Game game;

	public GameObserver(Main main, Game game){
		this.main = main;
		this.game = game;
	}
		
	@Override
	public void onMove(String player, String details, int move) {
		Board board = this.game.getBoard();
		int x = move % board.getColumns();
		int y = move / board.getColumns();
		this.game.serverMove(y, x);
		System.out.println(details);
		
	}

	@Override
	public void onYourTurn(String comment) {
		System.out.println(comment);
		this.game.setTurn(true);
	}

	@Override
	public void onChallenge(String opponentName, int challengeNumber, String gameType) {
		if(gameType.equals(this.game.getGameType())){
			this.game.getChallenged(opponentName, challengeNumber);
		}
	}

	@Override
	public void onChallengeCancelled(int challengeNumber, String comment) {
		this.game.stopChallenge();
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
		this.game.setPlayerList(players);
	}

	@Override
	public void onGameEnd(int statusCode, String comment) {
		this.game.setGameStatus(1);
		
	}

	@Override
	public void onError(String comment) {
		// TODO Auto-generated method stub
		
	}

}

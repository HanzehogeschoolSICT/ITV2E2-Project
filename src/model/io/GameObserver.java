package model.io;

import java.util.ArrayList;

import controller.Game;
import controller.Main;
import model.Board;
import model.io.Connection.Observer;

import static controller.Game.GameStatus.*;

public class GameObserver implements Observer{
	private Game game;

	public GameObserver(Main main, Game game){
		this.game = game;
	}
		
	@Override
	public void onMove(String player, String details, int move) {
		Board board = this.game.getBoard();
		int x = move % board.getColumns();
		int y = move / board.getColumns();
		boolean yourTurn;
		if(player.equals(this.game.getOpponent())){
			yourTurn = false;
		} else {
			yourTurn = true;
		}
		this.game.serverMove(y, x, yourTurn);		
	}

	@Override
	public void onYourTurn(String comment) {
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
	public void onGameList(ArrayList<String> games) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerList(ArrayList<String> players) {
		this.game.setPlayerList(players);
	}

	@Override
	public void onGameEnd(int statusCode, String comment) {
		switch(statusCode){
		case 1:
			this.game.setGameStatus(WIN);
			break;
		case 0:
			this.game.setGameStatus(DRAW);
			break;
		case -1:
			this.game.setGameStatus(LOSE);
			break;
		}
		
	}

	@Override
	public void onError(String comment) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onGameMatch(String playerMove, String gameType, String Opponent){
		if(this.game.getGameType().equals(gameType)){
			this.game.setOpponent(Opponent);
			this.game.setPlayerFirstMove(playerMove);
			this.game.setGameStatus(STARTED);
			this.game.resetBoard();
		} else {
			System.out.println("Game type not equal: " + gameType);
		}
	}

}

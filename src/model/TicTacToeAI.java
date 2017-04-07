package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import controller.Game;

public class TicTacToeAI implements GameAI{
	private int[] nextMove;
	private Game gameController;
	
	public TicTacToeAI(Game gameController){
		this.gameController = gameController;
	}
	
	/*Calculates the best possible move using MiniMax
	 * 
	 * Needs the board as input
	 * result will be the nextMove variable
	 * 
	 */
	@Override
	public void move() {
		Board board = this.gameController.getBoard();
		int[][] inputBoard = board.getSpaces();
		
		int player = 1;
		this.nextMove = new int[2];
		if(this.emptyBoard(inputBoard)){
			this.randomMove();
		}else{
			this.minMax(inputBoard, player);
		}
		this.gameController.setMove(this.nextMove[0], this.nextMove[1]);
	}
	
	public void randomMove(){
		int randomIndex = ThreadLocalRandom.current().nextInt(0, 8 + 1);
		if(randomIndex == 6){
			this.randomMove();
		}else{
			this.nextMove[0] = Math.round(randomIndex / 3);
			this.nextMove[1] = randomIndex % 3;
		}
	}
		
	public boolean emptyBoard(int[][] inputBoard){
		for(int y = 0; y< inputBoard.length; y++){
			for(int x = 0; x< inputBoard[y].length; x++){
				if(inputBoard[y][x] != 0){
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public Integer checkWin(int[][] inputBoard) {
		int winDiagnal = this.checkWinDiagnal(inputBoard);
		int winRows = this.checkWinRows(inputBoard);
		int winColumns = this.checkWinColumns(inputBoard);
		boolean winTie = this.checkEmptySpots(inputBoard);
		if(winDiagnal == 2 || winRows == 2 || winColumns == 2){
			return -1;
		}else if(winDiagnal == 1 || winRows == 1 || winColumns == 1){
			return 1;
		}else if(winDiagnal == -1 || winRows == -1 || winColumns == -1 && winTie == false){
			return 0;
		}
		return 2;
	}
	
	private boolean checkEmptySpots(int[][] inputBoard){
		for(int y = 0; y< inputBoard.length; y++){
			for(int x = 0; x< inputBoard[y].length; x++){
				if(inputBoard[y][x] == 0){
					return true;
				}
			}
		}
		return false;
	}
	
	public int checkWinDiagnal(int[][] inputBoard){
		if(inputBoard[0][0] == inputBoard[1][1] && inputBoard[1][1] == inputBoard[2][2] && inputBoard[2][2] != 0){
			return inputBoard[0][0];
		}else if(inputBoard[0][2] == inputBoard[1][1] && inputBoard[1][1] == inputBoard[2][0] && inputBoard[2][0] != 0){
			return inputBoard[2][0];
		}
		return -1;
	}
	
	public int checkWinRows(int[][] inputBoard){
		if(inputBoard[0][0] == inputBoard[0][1] && inputBoard[0][1] == inputBoard[0][2] && inputBoard[0][2] != 0){
			return inputBoard[0][2];
		}else if(inputBoard[1][0] == inputBoard[1][1] && inputBoard[1][1] == inputBoard[1][2] && inputBoard[1][2] != 0){
			return inputBoard[1][2];
		}else if(inputBoard[2][0] == inputBoard[2][1] && inputBoard[2][1] == inputBoard[2][2] && inputBoard[2][2] != 0){
			return inputBoard[2][2];
		}
		return -1;
	}
	
	public int checkWinColumns(int[][] inputBoard){
		if(inputBoard[0][0] == inputBoard[1][0] && inputBoard[1][0] == inputBoard[2][0] && inputBoard[2][0] != 0){
			return inputBoard[2][0];
		}else if(inputBoard[0][1] == inputBoard[1][1] && inputBoard[1][1] == inputBoard[2][1] && inputBoard[2][1] != 0){
			return inputBoard[2][1];
		}if(inputBoard[0][2] == inputBoard[1][2] && inputBoard[1][2] == inputBoard[2][2] && inputBoard[2][2] != 0){
			return inputBoard[2][2];
		}
		return -1;
	}
	
	private int nextPlayer(int player){
		if(player == 1){
			return 2;
		}else{
			return 1;
		}
	}
	
	private int getPosWin(int player){
		int posWin;
		if(player == 1){
			posWin = -99;
		}else{
			posWin = 99;
		}
		return posWin;
	}
	
	private int setPosWin(int checkWin, int posWin, int player, int x, int y){
		if(checkWin > posWin && player == 1){
			posWin = checkWin;
			this.nextMove[0] = y;
			this.nextMove[1] = x;
		}else if(checkWin < posWin && player == 2){
			posWin = checkWin;
		}
		return posWin;
	}

	@Override
	public Integer minMax(int[][] inputBoard, int player) {
		ArrayList<Integer> possibleOutcomes = new ArrayList<Integer>();
		for(int y = 0; y< inputBoard.length; y++){
			for(int x = 0; x< inputBoard[y].length; x++){
				if(inputBoard[y][x] == 0){
					int[][] tempBoard = inputBoard;
					tempBoard[y][x] = player;
					Integer checkWin = this.checkWin(tempBoard);
					
					
					if(checkWin != 2){
						possibleOutcomes.add(checkWin);
					}else{
						Integer minMax = minMax(inputBoard, this.nextPlayer(player));
						possibleOutcomes.add(minMax);
					}
				}
			}
		}
		
		if(player == 1){
			return Collections.max(possibleOutcomes);
		}else{
			return Collections.min(possibleOutcomes);
		}
	}

}

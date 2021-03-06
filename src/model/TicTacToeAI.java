package model;

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
		int[][] inputBoard = copyBoard(board.getSpaces());
		
		int player = 1;
		this.nextMove = new int[2];
		if(this.emptyBoard(inputBoard)){
			this.randomMove();
		}else if(this.nextPossWin(inputBoard) == false && this.nextMoveWin(inputBoard) == false){
			this.minMax(inputBoard, player);
		}
		this.gameController.setMove(this.nextMove[0], this.nextMove[1]);
	}
	
	private boolean nextPossWin(int[][] inputBoard) {
		for(int y = 0; y< inputBoard.length; y++){
			for(int x = 0; x< inputBoard[y].length; x++){
				if(inputBoard[y][x] == 0){
					
					inputBoard[y][x] = 1;
					if(this.checkWin(inputBoard) == 1){
						this.nextMove[0] = y;
						this.nextMove[1] = x;
						return true;
					}
					inputBoard[y][x] = 0;
				}
			}
		}
		return false;
	}

	public int[][] copyBoard(int[][] inputBoard){
		int[][] outputBoard = new int[inputBoard.length][];
		for (int y = 0; y<inputBoard.length;y++){
			outputBoard[y] = new int[inputBoard[y].length];
			for (int x = 0; x < inputBoard[y].length; x++){
				outputBoard[y][x] = inputBoard[y][x];
			}
		}
		return outputBoard;
	}
	
	public boolean nextMoveWin(int[][] inputBoard){
		for(int y = 0; y< inputBoard.length; y++){
			for(int x = 0; x< inputBoard[y].length; x++){
				if(inputBoard[y][x] == 0){
					int[][] tempBoard = copyBoard(inputBoard);
					tempBoard[y][x] = 2;
					if(this.checkWin(tempBoard) == -1){
						this.nextMove[0] = y;
						this.nextMove[1] = x;
						return true;
					}
					tempBoard = null;
				}
			}
		}
		return false;
	}
	
	public void randomMove(){
		int randomIndex = ThreadLocalRandom.current().nextInt(0, 8 + 1);
		if(randomIndex == 5){
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

	public Integer checkWin(int[][] inputBoard) {
		int winDiagnal = this.checkWinDiagnal(inputBoard);
		int winRows = this.checkWinRows(inputBoard);
		int winColumns = this.checkWinColumns(inputBoard);
		boolean winTie = this.checkEmptySpots(inputBoard);
		if(winDiagnal == 2 || winRows == 2 || winColumns == 2){
			return -1;
		}else if(winDiagnal == 1 || winRows == 1 || winColumns == 1){
			return 1;
		}else if((winDiagnal == -1 || winRows == -1 || winColumns == -1) && winTie == false){
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
	
	public Integer minMax(int[][] inputBoard, int player) {
		MinMaxResult possibleOutcomes = new MinMaxResult();
		for(int y = 0; y< inputBoard.length; y++){
			for(int x = 0; x< inputBoard[y].length; x++){
				if(inputBoard[y][x] == 0){
					int[][] tempBoard = copyBoard(inputBoard);
					tempBoard[y][x] = player;
					int checkWin = this.checkWin(tempBoard);
					if(checkWin != 2){
						possibleOutcomes.addResult(x, y, checkWin);
					}else{
						int minMax = this.minMax(tempBoard, this.nextPlayer(player));
						possibleOutcomes.addResult(x, y, minMax);
					}
					tempBoard = null;
				}
			}
		}
		
		int[] details;
		if(player == 1){
			details = possibleOutcomes.getMax();
		}else{
			details = possibleOutcomes.getMin();
		}
		this.nextMove[0] = details[1];
		this.nextMove[1] = details[0];
		return possibleOutcomes.getResult();
	}

	public void setAIDepth(int depth){
		return;
	}
	
}

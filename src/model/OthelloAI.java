package model;

import java.util.ArrayList;

import controller.Game;
import controller.Othello;
import controller.Othello.Direction;

public class OthelloAI implements GameAI{
	private int[] nextMove;
	private Othello gameController;
	private static int MINMAX_DEPTH = 6;
	
	public OthelloAI(Game gameController){
		this.gameController = (Othello) gameController;
	}
	
	@Override
	public void move() {
		this.nextMove = new int[2];
		Board board = this.gameController.getBoard();
		int[][] inputBoard = copyBoard(board.getSpaces());
		ArrayList<int[]> possMoves = this.getPossibleCoords(inputBoard, 1);
		if(!this.checkCorners(inputBoard, possMoves)){
			if(!this.checkEdges(inputBoard, possMoves)){
				this.minMax(inputBoard, 1, 1);
			}
		}
		
		this.gameController.setMove(this.nextMove[0], this.nextMove[1]);
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
	
	private ArrayList<int[]> getPossibleCoords(int[][] inputBoard, int player){
		ArrayList<int[]> possMoves = new ArrayList<int[]>();
		for(int y = 0; y<inputBoard.length;y++){
			for (int x = 0;x<inputBoard[y].length;x++){
				if(inputBoard[y][x] == 0){
					
					Board tempBoard = new Board(inputBoard);
					if(this.gameController.isValidMove(tempBoard, x, y, player)){
						possMoves.add(new int[]{y,x});
					};
					tempBoard = null;
				}
			}
		}
		return possMoves;
	}
	
	private boolean checkCorners(int[][] inputBoard, ArrayList<int[]> possMoves){
		int maxY = inputBoard.length - 1;
		int maxX = inputBoard[maxY].length - 1;
		
		if(possMoves.contains(new int[]{0,0})){
			this.nextMove[0] = 0;
			this.nextMove[1] = 0;
			return true;
		}else if(possMoves.contains(new int[]{0,maxX})){
			this.nextMove[0] = 0;
			this.nextMove[1] = maxX;
			return true;
		}else if(possMoves.contains(new int[]{maxY,0})){
			this.nextMove[0] = maxY;
			this.nextMove[1] = 0;
			return true;
		}else if(possMoves.contains(new int[]{maxY,maxX})){
			this.nextMove[0] = maxY;
			this.nextMove[1] = maxX;
			return true;
		}
		
		return false;
	}
	
	private boolean checkEdges(int[][] inputBoard, ArrayList<int[]> possMoves) {
		int maxY = inputBoard.length - 1;
		int maxX = inputBoard[maxY].length - 1;
		for(int[] possMove : possMoves){
			if(possMove[0] == 0 || possMove[0] == maxY || possMove[1] == 0 || possMove[1] == maxX){
				int[][] tempBoard = this.copyBoard(inputBoard);
				tempBoard[possMove[0]][possMove[1]] = 1;
				Board tempBoard2 = new Board(tempBoard);
				this.gameController.turnStones(tempBoard2, possMove[1], possMove[0], 1);
				tempBoard = tempBoard2.getSpaces();
				ArrayList<int[]> possOppMoves = this.getPossibleCoords(tempBoard, 2);
				if(!this.checkCorners(tempBoard, possOppMoves) && !this.checkEdgeOverTake(possMove[0], possMove[1], tempBoard, 2)){
					this.nextMove[0] = possMove[0];
					this.nextMove[1] = possMove[1];
					possOppMoves = null;
					return true;
				}
				tempBoard = null;
				tempBoard2 = null;
				possOppMoves = null;
			}
		}
		return false;
	}
	
	private boolean checkEdgeOverTake(int y, int x, int[][] inputBoard, int player) {
		Board inputBoard2 = new Board(inputBoard);
		if(x == 0 || x == inputBoard[0].length){
			if(this.gameController.isValidMove(inputBoard2, Direction.UP, x, y, player) || this.gameController.isValidMove(inputBoard2, Direction.DOWN, x, y, player)){
				return true;
			}
		}else if(y == 0 || y == inputBoard.length){
			if(this.gameController.isValidMove(inputBoard2, Direction.RIGHT, x, y, player) || this.gameController.isValidMove(inputBoard2, Direction.LEFT, x, y, player)){
				return true;
			}
		}
		return false;
	}


	
	public Integer minMax(int[][] inputBoard, int player, int depth) {
		ArrayList<int[]> possMoves = this.getPossibleCoords(inputBoard, player);
		MinMaxResult possibleOutcomes = new MinMaxResult();
		for(int[] possMove : possMoves){
			int[][] tempBoard = this.copyBoard(inputBoard);
			tempBoard[possMove[0]][possMove[1]] = player;
			Board tempBoard2 = new Board(tempBoard);
			this.gameController.turnStones(tempBoard2, possMove[1], possMove[0], player);
			tempBoard = tempBoard2.getSpaces();
			
			if(depth == OthelloAI.MINMAX_DEPTH || this.checkWin(tempBoard) == 1){
				int scoreSelf = this.gameController.amountOfStones(1, tempBoard2);
				int scoreOpp = this.gameController.amountOfStones(2, tempBoard2);
				possibleOutcomes.addResult(possMove[1], possMove[0], (scoreSelf-scoreOpp));
			}else{
				possibleOutcomes.addResult(possMove[1], possMove[0], this.minMax(tempBoard, (player == 1 ? 2 : 1), depth + 1));
			}
			tempBoard = null;
			tempBoard2 = null;
			
		}
		if(possibleOutcomes.getSize() != 0){
			int[] minMaxRes = (player == 1)? possibleOutcomes.getMax() : possibleOutcomes.getMin();
			if(depth == 1){
				this.nextMove[0] = minMaxRes[1];
				this.nextMove[1] = minMaxRes[0];
			}
			return minMaxRes[2];
		}else{
			return 0;
		}
		
	}

	@Override
	public Integer checkWin(int[][] inputBoard) {
		if(this.checkAllEmpty(inputBoard) == true || (this.getPossibleCoords(inputBoard, 1) == null && this.getPossibleCoords(inputBoard, 2) == null)){
			return 1;
		}
		return 0;
	}
	
	public boolean checkAllEmpty(int[][] inputBoard){
		for(int y=0; y < inputBoard.length; y++){
			for(int x=0; x < inputBoard[0].length; x++){
				if(inputBoard[y][x] == 0){
					return false;
				}
			}
		}
		
		return true;
	}

	@Override
	public Integer minMax(int[][] inputBoard, int player) {
		// TODO Auto-generated method stub
		return null;
	}

}

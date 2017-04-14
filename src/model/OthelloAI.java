package model;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import controller.Game;
import controller.Othello;
import controller.Othello.Direction;

public class OthelloAI implements GameAI{
	protected int[] nextMove;
	protected Othello gameController;
	protected static int MINMAX_DEPTH = 8;
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
	
	protected ArrayList<int[]> getPossibleCoords(int[][] inputBoard, int player){
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
	
	protected boolean checkCorners(int[][] inputBoard, ArrayList<int[]> possMoves){
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

	
	public Integer minMax(int[][] inputBoard, int player, int depth){
		ArrayList<int[]> possMoves = this.getPossibleCoords(inputBoard, player);
		MinMaxResult possibleOutcomes = new MinMaxResult();
		ArrayList<MinMaxThread> minMaxThreads = new ArrayList<MinMaxThread>();
		ExecutorService executor = Executors.newFixedThreadPool(possMoves.size());
		for(int[] possMove : possMoves){
			int[][] tempBoard = this.copyBoard(inputBoard);
			tempBoard[possMove[0]][possMove[1]] = player;
			Board tempBoard2 = new Board(tempBoard);
			this.gameController.turnStones(tempBoard2, possMove[1], possMove[0], player);
			tempBoard = tempBoard2.getSpaces();
			MinMaxThread _minMaxThread = new MinMaxThread(tempBoard, (player == 1 ? 2 : 1), depth + 1, this);
			minMaxThreads.add(_minMaxThread);
			_minMaxThread.addCoords(possMove[0], possMove[1]);
			executor.execute(_minMaxThread);
			
			
		}
		
		executor.shutdown();
        try {
        	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    	} catch (InterruptedException e) {
    	  System.out.println(e);
    	}
		for(MinMaxThread minmaxthread : minMaxThreads){
			possibleOutcomes.addResult(minmaxthread.getx(), minmaxthread.gety(), minmaxthread.getResult());
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
	
	public void setAIDepth(int depth){
		OthelloAI.MINMAX_DEPTH = depth;
	}

}

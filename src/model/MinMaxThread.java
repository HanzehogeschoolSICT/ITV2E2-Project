package model;

import java.util.ArrayList;

public class MinMaxThread implements Runnable{
	int[][] board;
	int player2;
	int depth;
	OthelloAI othelloai;
	int result;
	int x;
	int y;
	
	public MinMaxThread(int[][] inputBoard, int player, int depth, OthelloAI othelloai){
		this.board = inputBoard;
		this.player2 = player;
		this.depth = depth;
		this.othelloai = othelloai;
	}
	
	public void addCoords(int y, int x){
		this.y = y;
		this.x = x;
	}
	
	public int getx(){
		return this.x;
	}
	
	public int gety(){
		return this.y;
	}
	
	@Override
	public void run() {
		this.result = this.minMax(this.board, this.player2, this.depth);
	}
	
	public int getResult(){
		return this.result;
	}
	
	private Integer minMax(int[][] inputBoard, int player, int depth) {
		ArrayList<int[]> possMoves = this.othelloai.getPossibleCoords(inputBoard, player);
		MinMaxResult possibleOutcomes = new MinMaxResult();
		for(int[] possMove : possMoves){
			int[][] tempBoard = this.othelloai.copyBoard(inputBoard);
			tempBoard[possMove[0]][possMove[1]] = player;
			Board tempBoard2 = new Board(tempBoard);
			this.othelloai.gameController.turnStones(tempBoard2, possMove[1], possMove[0], player);
			tempBoard = tempBoard2.getSpaces();
			
			if (depth == this.othelloai.MINMAX_DEPTH || this.othelloai.checkWin(tempBoard) == 1){
				int scoreSelf = this.othelloai.gameController.amountOfStones(1, tempBoard2);
				int scoreOpp = this.othelloai.gameController.amountOfStones(2, tempBoard2);
				possibleOutcomes.addResult(possMove[1], possMove[0], (scoreSelf-scoreOpp));
			} else if(depth == 1 && possMoves.size() > 1){
			
					System.out.println("Depth > 1 if");
					ArrayList<int[]> possEdges = this.othelloai.getPossibleCoords(tempBoard, 2);
					if(!this.othelloai.checkCorners(tempBoard, possEdges)){
						System.out.println("Checked corners false");
						int minmax = this.minMax(tempBoard, (player == 1 ? 2 : 1), depth + 1);
						possibleOutcomes.addResult(possMove[1], possMove[0], minmax);
					}
				
			}else{
				
				int minmax = this.minMax(tempBoard, (player == 1 ? 2 : 1), depth + 1);
				possibleOutcomes.addResult(possMove[1], possMove[0], minmax);
			}
				
			
			tempBoard = null;
			tempBoard2 = null;
			
		}
		if(possibleOutcomes.getSize() != 0){
			int[] minMaxRes = (player == 1)? possibleOutcomes.getMax() : possibleOutcomes.getMin();
			return minMaxRes[2];
		}else{
			return 0;
		}
		
	}

}

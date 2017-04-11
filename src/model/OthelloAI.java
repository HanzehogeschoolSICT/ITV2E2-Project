package model;

import java.util.ArrayList;

import controller.Game;

public class OthelloAI implements GameAI{
	private int[] nextMove;
	private Game gameController;
	private static int MINMAX_DEPTH = 4;
	
	public OthelloAI(Game gameController){
		this.gameController = gameController;
	}
	
	@Override
	public void move() {
		Board board = this.gameController.getBoard();
		int[][] inputBoard = copyBoard(board.getSpaces());
		ArrayList<int[]> possMoves = this.getPossibleCoords(inputBoard, 1);
		if(!this.checkCorners(inputBoard, possMoves)){
			if(!this.checkEdges(inputBoard, possMoves)){
				
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
					//IMPLEMENT HARJAN FUNCTIE(y,x,player)
					possMoves.add(new int[]{y,x});
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
				inputBoard[possMove[0]][possMove[1]] = 1;
				//TURN STONES HARJAN
				ArrayList<int[]> possOppMoves = this.getPossibleCoords(inputBoard, 2);
				if(!this.checkCorners(inputBoard, possOppMoves) && !this.checkEdgeOverTake(possMove[0], possMove[1], inputBoard, 2)){
					this.nextMove[0] = possMove[0];
					this.nextMove[1] = possMove[1];
					possOppMoves = null;
					return true;
				}
				inputBoard[possMove[0]][possMove[1]] = 0;
				possOppMoves = null;
			}
		}
		return false;
	}
	
	private boolean checkEdgeOverTake(int y, int x, int[][] inputBoard, int player) {
		
		if(x == 0 || x == inputBoard[0].length){
			//IMPLEMENT HARJAN FUNCTIE-SOUTH(intputBoard, y - 1,x,2)
			//IMPLEMENT HARJAN FUNCTIE-NORTH(intputBoard, y + 1,x,2)
			if(true){
				return true;
			}
		}else if(y == 0 || y == inputBoard.length){
			//IMPLEMENT HARJAN FUNCTIE-EAST(intputBoard, y,x - 1,2)
			//IMPLEMENT HARJAN FUNCTIE-WEST(intputBoard, y,x + 1,2)
			//HARJAN CATCH OUT OF BOUNDS
			if(true){
				return true;
			}
		}
		return false;
	}


	
	public Integer minMax(int[][] inputBoard, int player, int depth) {
		
		ArrayList<int[]> possMoves = this.getPossibleCoords(inputBoard, player);
		MinMaxResult possibleOutcomes = new MinMaxResult();
		for(int[] possMove : possMoves){
			inputBoard[possMove[0]][possMove[1]] = player;
			//HARJAN TURN STONES(inputBoard)
			
			if(depth == OthelloAI.MINMAX_DEPTH){
				//RUTGER CALC SCORE(inputBoard)
				int scoreSelf = 0;
				int scoreOpp = 0;
				possibleOutcomes.addResult(possMove[1], possMove[0], (scoreSelf-scoreOpp));
			}else{
				possibleOutcomes.addResult(possMove[1], possMove[0], this.minMax(inputBoard, (player == 1 ? 2 : 1), depth + 1));
			}
			
		}
		int[] minMaxRes;
		if(player == 1){
			minMaxRes = possibleOutcomes.getMax();
		}else{
			minMaxRes = possibleOutcomes.getMin();
		}
		if(depth == 1){
			this.nextMove[0] = minMaxRes[1];
			this.nextMove[1] = minMaxRes[0];
		}
		return minMaxRes[2];
	}

	@Override
	public Integer checkWin(int[][] inputBoard) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer minMax(int[][] inputBoard, int player) {
		// TODO Auto-generated method stub
		return null;
	}

}

package model;

public interface GameAI {
	public int[] nextMove = null;
	
	
	public void move();
	
	public int checkWin(int[][] inputBoard);
	
	public int minMax(int[][] inputBoard, int player);
}

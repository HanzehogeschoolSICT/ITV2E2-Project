package model;

public interface GameAI {
	public int[] nextMove = null;
	
	
	public void move();
	
	public Integer checkWin(int[][] inputBoard);
	
	public Integer minMax(int[][] inputBoard, int player);
}

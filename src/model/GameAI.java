package model;

public interface GameAI {
	public int[] nextMove = null;
	
	public void move();
	public void setAIDepth(int depth);
}

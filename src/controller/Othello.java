package controller;

import model.Board;
import model.OthelloAI;
import view.OthelloScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Othello extends AbstractGame{

    public Othello(Main main) {
    	this.main = main;
		this.gamescreen = new OthelloScreen(this);
		this.gameType = "Reversi";
		this.board = new Board(8,8);
		this.gameAI = new OthelloAI(this);
    }
    
    @Override
    public void createEmptyBoard(){
    	this.board.createEmptyBoard();
    	if (this.getPlayerFirstMove()){
    		this.board.set(2, 3, 3);
    		this.board.set(2, 4, 4);
    		this.board.set(1, 3, 4);
    		this.board.set(1, 4, 3);
    	} else {
    		this.board.set(1, 3, 3);
    		this.board.set(1, 4, 4);
    		this.board.set(2, 3, 4);
    		this.board.set(2, 4, 3);
    	}
    }

	public void turnStones(Board inputBoard, int x, int y, int player) {
		turnDiagonal(inputBoard, x, y, player);
		turnHorizontal(inputBoard, x, y, player);
		turnVertical(inputBoard, x, y, player);
	}

	private void turnDiagonal(Board inputBoard, int x, int y, int player) {
		turnDiagonalUp(inputBoard, x, y, player);
		turnDiagonalDown(inputBoard, x, y, player);
	}

	private void turnDiagonalDown(Board inputBoard, int x, int y, int player) {
		int pos = y * inputBoard.getColumns() + x;
		if (isValidMove(inputBoard, Direction.LEFT_DOWN, x, y, player)) {
			for (int i = (pos + 7); ((i < 64) && (i % 8 != 0)); i = i + 7) {
				boolean check = this.swapStones(inputBoard, i, player);
				if (check == false){
					break;
				}		
			}
		}

		if (isValidMove(inputBoard, Direction.RIGHT_DOWN, x, y, player)) {
			for (int i = (pos + 9); ((i < 64) && (i % 8 != 0)); i = i + 9) {
				boolean check = this.swapStones(inputBoard, i, player);
				if (check == false){
					break;
				}
			}
		}
	}

	private void turnDiagonalUp(Board inputBoard, int x, int y, int player) {
		int pos = y * inputBoard.getColumns() + x;
		if (isValidMove(inputBoard, Direction.LEFT_UP, x, y, player)) {
			for (int i = (pos - 9); ((i > 0) && (i % 8 != 0)); i = i - 9) {
				boolean check = this.swapStones(inputBoard, i, player);
				if (check == false){
					break;
				}
			}
		}
		if (isValidMove(inputBoard, Direction.RIGHT_UP, x, y, player)) {
			for (int i = (pos - 7); ((i > 0) && (i % 8 != 0)); i = i - 7) {
				boolean check = this.swapStones(inputBoard, i, player);
				if (check == false){
					break;
				}
			}
		}
	}

	private void turnHorizontal(Board inputBoard, int x, int y, int player) {
		int pos = y * inputBoard.getColumns() + x;
		if (isValidMove(inputBoard, Direction.RIGHT, x, y, player)) {
			for (int i = (pos + 1); ((i) % 8 != 0); i++) {
				boolean check = this.swapStones(inputBoard, i, player);
				if (check == false){
					break;
				}
			}
		}
		
		if (isValidMove(inputBoard, Direction.LEFT, x, y, player)) {
			for (int i = (pos - 1); ((i) % 8 != 0); i--) {
				boolean check = this.swapStones(inputBoard, i, player);
				if (check == false){
					break;
				}
			}
		}
	}

	private void turnVertical(Board inputBoard, int x, int y, int player) {
		int pos = y * inputBoard.getColumns() + x;
		if (isValidMove(inputBoard, Direction.DOWN, x, y, player)) {
			for (int i = (pos + 8); i < 63; i = i + 8) {
				boolean check = this.swapStones(inputBoard, i, player);
				if (check == false){
					break;
				}
			}
		}
		
		if (isValidMove(inputBoard, Direction.UP, x, y, player)) {
			for (int i = (pos - 8); i > 0; i = i - 8) {
				boolean check = this.swapStones(inputBoard, i, player);
				if (check == false){
					break;
				}
			}
		}
	}

	private boolean swapStones(Board inputBoard, int pos, int player){
		int x2 = pos % inputBoard.getColumns();
		int y2 = pos / inputBoard.getColumns();
		if(inputBoard.get(y2, x2) == player){
			return false;
		}else{
			inputBoard.set(player, y2, x2);
			return true;
		}
	}
	
	/**
	 * Checks if the given move with x and y is valid and in range of the board.
	 * @param inputBoard The board to check the move on.
	 * @param x The x value of the origin point to check from.
	 * @param y The y value of the origin point to check from.
	 * @param player The player that is doing the move.
	 * @return True if the move is valid = within the board, not empty and not the current player, else false.
	 */
	public boolean isValidMove(Board inputBoard, int x, int y, int player) {
		boolean result = false;

		for (Direction direction : Direction.directions) {
			if (isValidMove(inputBoard, direction, x, y, player)) {
				
				result = true;
				break;
			}
		}

		return result;
	}

	public boolean isValidMove(Board inputBoard, Direction direction, int x, int y, int player) {
		boolean result = false;
		try {
			int contX = x + direction.getWidth_offset();
			int contY = y + direction.getHeight_offset();
			int valuePos = inputBoard.get(contY, contX);
			int count = 0;

			while (!result) {

				if (count > 0) {
					if (valuePos == player) {
						result = true;
						break;
					} else if (valuePos == 0) {
						result = false;
						break;
					}
				} else {
					if(valuePos == player) {
						result = false;
						break;
					} else if (valuePos == 0) {
						result = false;
						break;
					}
				}

				contX += direction.getWidth_offset();
				contY += direction.getHeight_offset();
				valuePos = inputBoard.get(contY, contX);
				count++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			result = false;
		}

		return result;
	}

	/**
	 * Check the board for the amount of stones on the board owned by the given player.
	 * @param stoneToCheck The stone to check for, either 0, 1 or 2
	 * @param inputBoard The board to check the stones of.
	 * @return The amount of found stones.
	 */
	public int amountOfStones(int stoneToCheck, Board inputBoard) {
		int count = 0;
		for(int[] row : inputBoard.getSpaces()) {
			for (int column : row) {
				if (column == stoneToCheck) count++;
			}
		}
		return count;
	}

	/**
	 * Check the board for the amount of your stones on the board.
	 * @param inputBoard The board to check the stones of.
	 * @return The amount of found stones.
	 */
	public int amountOfYourStones(Board inputBoard) {
		return amountOfStones(1, inputBoard);
	}

	/**
	 * Check the board for the amount of your opponents stones on the board.
	 * @param inputBoard The board to check the stones of.
	 * @return The amount of found stones.
	 */
	public int amountOfOpponentStones(Board inputBoard) {
		return amountOfStones(2, inputBoard);
	}

	/**
	 * Check the board for the amount of empty spaces on the board.
	 * @param inputBoard The board to check the stones of.
	 * @return The amount of found stones.
	 */
	public int amountOfEmptySpaces(Board inputBoard) {
		return amountOfStones(0, inputBoard);
	}

	public void setScore(){
		this.scorePlayer = this.amountOfStones(1, this.board);
		this.scoreOpponent = this.amountOfStones(2, this.board);
	}
	
	public void serverMove(int y, int x, boolean yourTurn){
		if(yourTurn){
			this.board.set(1, y, x);
			this.turnStones(this.board, x, y, 1);
		} else {
			this.board.set(2, y, x);
			this.turnStones(this.board, x, y, 2);
		}
		this.setScore();
		this.updateView();
		return;
	}
	
	public enum Direction implements Iterable<Direction> {
		LEFT(0, -1, 0),
		RIGHT(1, 1, 0),
		UP(2, 0, -1),
		DOWN(3, 0, 1),
		LEFT_UP(4, -1, -1),
		LEFT_DOWN(5, -1, 1),
		RIGHT_UP(6, 1, -1),
		RIGHT_DOWN(7, 1, 1);

		public static final ArrayList<Direction> directions = new ArrayList<>(Arrays.asList(
				LEFT,
				RIGHT,
				UP,
				DOWN,
				LEFT_UP,
				LEFT_DOWN,
				RIGHT_UP,
				RIGHT_DOWN));

		private int identifier;
		private int width_offset;
		private int height_offset;

		Direction(int identifier, int width_offset, int height_offset) {
			this.identifier = identifier;
			this.width_offset = width_offset;
			this.height_offset = height_offset;
		}

		@Override
		public Iterator<Direction> iterator() {
			return directions.iterator();
		}

		public int getIdentifier() {
			return identifier;
		}

		public int getWidth_offset() {
			return width_offset;
		}

		public int getHeight_offset() {
			return height_offset;
		}

	}
	
	

}
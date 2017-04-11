package controller;

import model.Board;
import view.OthelloScreen;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
//import model.OthelloAI;

public class Othello extends AbstractGame{

    public Othello(Main main) {
    	this.main = main;
		this.gamescreen = new OthelloScreen(this);
		this.gameType = "Reversi";
		this.board = new Board(8,8);
		this.gameAI = null;
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

	private void turnDiagonalDown(Board inputboard, int x, int y, int player) {
		ArrayList<Integer[]> bottomLeft = new ArrayList<>();
		ArrayList<Integer[]> bottomRight = new ArrayList<>();
		int pos = y * inputboard.getColumns() + x;

		for (int i = (pos + 7); ((i < 64) && (i % 8 != 0)); i = i + 7) {
			if (!isValidMove(inputboard, bottomLeft, x, y, player)) {
				break;
			}
		}

		for (int i = (pos + 9); ((i < 64) && ((i - 1) % 8 != 0)); i = i + 9) {
			if (!isValidMove(inputboard, bottomRight, x, y, player)) {
				break;
			}
		}
	}

	private void turnDiagonalUp(Board inputBoard, int x, int y, int player) {
		ArrayList<Integer[]> topLeft = new ArrayList<>();
		ArrayList<Integer[]> topRight = new ArrayList<>();
		int pos = y * inputBoard.getColumns() + x;

		for (int i = (pos - 9); ((i > 0) && ((i + 1) % 8 != 0)); i = i - 9) {
			if (!isValidMove(inputBoard, topLeft, x, y, player)) {
				break;
			}
		}

		for (int i = (pos - 7); ((i > 0) && (i % 8 != 0)); i = i - 7) {
			if (!isValidMove(inputBoard, topRight, x, y, player)) {
				break;
			}
		}
	}

	private void turnHorizontal(Board inputBoard, int x, int y, int player) {
		ArrayList<Integer[]> horizontalLeft = new ArrayList<>();
		ArrayList<Integer[]> horizontalRight = new ArrayList<>();
		int pos = y * inputBoard.getColumns() + x;

		for (int i = (pos + 1); ((i) % 8 != 0); i++) {
			if (!isValidMove(inputBoard, horizontalRight, x, y, player)) {
				break;
			}
		}

		for (int i = (pos - 1); ((i + 1) % 8 != 0); i--) {
			if (!isValidMove(inputBoard, horizontalLeft, x, y, player)) {
				break;
			}
		}
	}

	private void turnVertical(Board inputBoard, int x, int y, int player) {
		ArrayList<Integer[]> verticalUp = new ArrayList<>();
		ArrayList<Integer[]> verticalDown = new ArrayList<>();
		int pos = y * inputBoard.getColumns() + x;

		for (int i = (pos + 8); i < 63; i = i + 8) {
			if (!isValidMove(inputBoard, verticalUp, x, y, player)) {
				break;
			}
		}

		for (int i = (pos - 8); i > 0; i = i - 8) {
			if (!isValidMove(inputBoard, verticalDown, x, y, player)) {
				break;
			}
		}
	}

	/**
	 * Checks if the given move with x and y is valid and in range of the board.
	 * @param inputBoard The board to check the move on.
	 * @param swabAbles The indexes to swap.
	 * @param x The x value of the origin point to check from.
	 * @param y The y value of the origin point to check from.
	 * @param player The player that is doing the move.
	 * @return True if the move is valid = within the board, not empty and not the current player, else false.
	 */
	private boolean isValidMove(Board inputBoard, ArrayList<Integer[]> swabAbles, int x, int y, int player) {
		int valueAtPos = inputBoard.get(x, y);

		if (valueAtPos == player) {
			if (swabAbles.size() > 0) {
				swapTiles(swabAbles, player, inputBoard);
				swabAbles.clear();
				return false;
			} else {
				return false;
			}
		} else if (valueAtPos != 0) {
			swabAbles.add(new Integer[]{x, y});
			return true;
		} else {
			return false;
		}
	}

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
		int contX = x + direction.getWidth_offset();
		int contY = y + direction.getHeight_offset();
		int valuePos = inputBoard.get(contX, contY);
		int count = 0;
		boolean result = false;

		try {
			while (!result) {

				if (count == 0) {
					if (valuePos != 0 && valuePos != player) {
						result = false;
						break;
					}
				} else {
					if (!(count > 0 && valuePos != 0 && valuePos != player)) {
						result = true;
						break;
					}
				}

				contX += direction.getWidth_offset();
				contY += direction.getHeight_offset();
				valuePos = inputBoard.get(contX, contY);
				count++;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			result = false;
		}

		return result;
	}

	private void swapTiles(ArrayList<Integer[]> swapTilesAt, int swapToPlayer, Board board) {
		for (Integer[] array : swapTilesAt) {
			int value = swapToPlayer == 1 ? 2 : 1;
			board.set(value, array[0], array[1]);
		}
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
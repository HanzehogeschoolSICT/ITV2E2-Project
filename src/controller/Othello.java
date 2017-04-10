package controller;

import model.Board;
import view.OthelloScreen;
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
		turnDiagonalLeft(inputBoard, x, y, player);
		turnDiagonalRight(inputBoard, x, y, player);
	}

	private void turnDiagonalLeft(Board inputBoard, int x, int y, int player) {
		int p = y * inputBoard.getColumns() + x;
		for (int i = (p - 9); ((i > 0) && ((i + 1) % 8 != 0)); i = i - 9) {
			if (!isValidMove(inputBoard, x, y, Direction.LEFT_UP, player)) {
				break;
			} else {
				swap(inputBoard, x, y, player);
			}
		}

		for (int i = (p + 7); i < 64; i = i + 7) {
			if (!isValidMove(inputBoard, x, y, Direction.LEFT_DOWN, player)) {
				break;
			} else {
				swap(inputBoard, x, y, player);
			}
		}
	}

	private void turnHorizontal(Board inputBoard, int x, int y, int player) {
		int p = y * inputBoard.getColumns() + x;
		for (int i = (p + 1); ((i) % 8 != 0); i++) {
			if (!isValidMove(inputBoard, x, y, Direction.RIGHT, player)) {
				break;
			} else {
				swap(inputBoard, x, y, player);
			}
		}

		for (int i = (p - 1); ((i + 1) % 8 != 0); i--) {
			if (!isValidMove(inputBoard, x, y, Direction.LEFT, player)) {
				break;
			} else {
				swap(inputBoard, x, y, player);
			}
		}
	}

	private void turnDiagonalRight(Board inputBoard, int x, int y, int player) {
		int p = y * inputBoard.getColumns() + x;
		for (int i = (p - 7); ((i > 0) && (i % 8 != 0)); i = i - 7) {
			if (!isValidMove(inputBoard, x, y, Direction.RIGHT_UP, player)) {
				break;
			} else {
				swap(inputBoard, x, y, player);
			}
		}

		for (int i = (p + 9); i < 64; i = i + 9) {
			if (!isValidMove(inputBoard, x, y, Direction.RIGHT_DOWN, player)) {
				break;
			} else {
				swap(inputBoard, x, y, player);
			}
		}
	}

	private void turnVertical(Board inputBoard, int x, int y, int player) {
		int p = y * inputBoard.getColumns() + x;
		for (int i = (p + 8); i < 63; i = i + 8) {
			if (!isValidMove(inputBoard, x, y, Direction.UP, player)) {
				break;
			} else {
				swap(inputBoard, x, y, player);
			}
		}

		for (int i = (p - 8); i > 0; i = i - 8) {
			if (!isValidMove(inputBoard, x, y, Direction.DOWN, player)) {
				break;
			} else {
				swap(inputBoard, x, y, player);
			}
		}
	}

	private void swap(Board inputBoard, int x, int y, int player) {
    	int current = inputBoard.get(y, x);
    	inputBoard.set((current == player ? 2 : 1), y, x);
	}


	/**
	 * Checks if the given move with x and y is valid and in range of the board.
	 * @param inputBoard The board to check the move on.
	 * @param x The x value of the origin point to check from.
	 * @param y The y value of the origin point to check from.
	 * @param direction The direction to check for.
	 * @param player The player that is doing the move.
	 * @return True if the move is valid = within the board, not empty and not the current player, else false.
	 */
	public boolean isValidMove(Board inputBoard, int x, int y, Direction direction, int player) {
		try {
			int foundSpace = inputBoard.get(
					x + direction.getWidth_offset(),
					y + direction.getHeight_offset()
			); //ToDo this is probably wrong.
			return ((foundSpace != 0) && (foundSpace != player));
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
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

	public enum Direction {
		LEFT(0, -1, 0),
		RIGHT(1, 1, 0),
		UP(2, 0, -1),
		DOWN(3, 0, 1),
		LEFT_UP(4, -1, -1),
		LEFT_DOWN(5, -1, 1),
		RIGHT_UP(6, 1, -1),
		RIGHT_DOWN(7, 1, 1);

		private int identifier;
		private int width_offset;
		private int height_offset;

		Direction(int identifier, int width_offset, int height_offset) {
			this.identifier = identifier;
			this.width_offset = width_offset;
			this.height_offset = height_offset;
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
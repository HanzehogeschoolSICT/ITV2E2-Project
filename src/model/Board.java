package model;

public class Board {

    private int rows;
    private int columns;
    private Integer[][] spaces;

    private Board(Integer rows, Integer columns) {
        this.rows = rows;
        this.columns = columns;
        this.spaces = new Integer[rows - 1][columns - 1];
    }

    public Integer get(int row, int column) {
        return this.spaces[row][column];
    }

    public void set(int value, int row, int column) {
        this.spaces[row][column] = value;
    }

    //<editor-fold desc="Factory">
    public static class Factory {

        public Factory() {
        }

        public Board newBoard(int row, int column) {
            return new Board(row, column);
        }

        public static Board newTicTacToeBoard() {
            return new Board(3, 3);
        }

        public static Board newOthelloBoard(){
            return new Board(8, 8);
        }

    }
    //</editor-fold>

    //<editor-fold desc="Getters and Setters">
    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Integer[][] getSpaces() {
        return spaces;
    }
    //</editor-fold>

}

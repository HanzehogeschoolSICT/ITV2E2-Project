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

package model;

public class Board {

    private int rows;
    private int columns;
    private int[][] spaces;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.createEmptyBoard();
    }

    public int get(int row, int column) {
        return this.spaces[row][column];
    }

    public void set(int value, int row, int column) {
        this.spaces[row][column] = value;
    }
    
    public boolean isValid(int y, int x){
    	if(y > rows || y < 0 || x > columns || x < 0 || this.get(y,x) != 0){
    		return false;
    	}
    	return true;
    }

    //<editor-fold desc="Getters and Setters">
    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public int[][] getSpaces() {
        return this.spaces;
    }
    //</editor-fold>
    
    public void createEmptyBoard(){
    	this.spaces = new int[rows][columns];
    }

}

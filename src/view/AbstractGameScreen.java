package view;

import controller.Game;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Board;

public abstract class AbstractGameScreen implements GameScreen {
	protected Game game;
	protected BorderPane pane;
	
	public Pane createBoard(){
		Board board = this.game.getBoard(); 
		GridPane boardPane = new GridPane();
		boardPane.setHgap(5);
		boardPane.setVgap(5);
		boardPane.setAlignment(Pos.CENTER);
		boardPane.setPadding(new Insets(5, 5, 5, 5));
		
		for (int y=0;y<board.getRows();y++){
			for(int x=0;x<board.getColumns();x++){
				Pane box = getBoardValue(y,x);
				boardPane.add(box, x, y);
			}
		}
		return boardPane;
	}
	
	private Pane getBoardValue(int y, int x){
		HBox pane = new HBox();
		Board board = this.game.getBoard();
		int value = 0;
		try{
			value = board.get(y, x);
		} catch(Exception e){
			System.out.println(y + "," + x + " resulted in null pointer exception");
		}
		if (value == 0){
			Rectangle col = this.createEmpty(y, x);
			pane.getChildren().add(col);
		} else if (value == 1){
			ImageView xView = this.getPlayer();
			pane.getChildren().add(xView);
		} else if (value == 2){
			ImageView oView = this.getOponent();
			pane.getChildren().add(oView);
		}
		return pane;
	}
	
	private ImageView getPlayer(){
		ImageView xView = null;
		if (this.game.getPlayerFirstMove() == true){
			xView = createPlayer();
		} else {
			xView = createOponent();
		}
		return xView;
	}
	
	private ImageView getOponent(){
		ImageView oView = null;
		if (this.game.getPlayerFirstMove() == true){
			oView = createOponent();
		} else {
			oView = createPlayer();
		}
		return oView;
	}
	
	protected abstract ImageView createPlayer();
	protected abstract ImageView createOponent();
	protected abstract int getSquareSize();
	
	private Rectangle createEmpty(int y, int x){
		Rectangle col = new Rectangle();
		col.setHeight(this.getSquareSize());
		col.setWidth(this.getSquareSize());
		col.setFill(Color.DARKCYAN);
		col.setStyle("-fx-cursor: hand;");
		col.setUserData(y+","+x);
		col.setOnMouseClicked(new SquareHandlerClass());
		return col;
	}
	
	public Pane create(){
		this.pane = new BorderPane();
		pane.setPadding(new Insets(5, 5, 5, 5));
		Pane boardPane = createBoard();
		this.pane.setCenter(boardPane);
		return this.pane;
	}
	
	public Pane update(){
		Pane boardPane = createBoard();
		this.pane.setCenter(boardPane);
		return this.pane;
	}
	
	class SquareHandlerClass implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent e) {
			Object obj = e.getSource();  
			if ( obj instanceof Rectangle ){
				String userdata = (String) ((Rectangle) obj).getUserData();
				String[] coords = userdata.split(",");
				System.out.println("Pressed button" + userdata);
				if (game.getGameStart() == true){
					if (game.getHuman() == true && game.getTurn() == true){
						game.setMove(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
					}
				} else {
					System.out.println("Game hasnt started");
				}
			}
		}
	}
}

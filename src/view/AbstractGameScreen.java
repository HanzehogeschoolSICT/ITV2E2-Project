package view;

import controller.Game;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Board;

public abstract class AbstractGameScreen implements GameScreen {
	protected Game game;
	protected BorderPane pane;
	
	public Pane createBoard(){
		Board board = this.game.getBoard(); 
		VBox boardPane = new VBox();
		boardPane.setPadding(new Insets(5, 5, 5, 5));
		
		boardPane.setSpacing(5);
		for (int y=0;y<board.getRows();y++){
			HBox row = new HBox();
			row.setSpacing(5);
			for(int x=0;x<board.getColumns();x++){
				Pane box = getBoardValue(x,y);
				row.getChildren().add(box);
			}
			boardPane.getChildren().add(row);
		}
		return boardPane;
	}
	
	private Pane getBoardValue(int x, int y){
		HBox pane = new HBox();
		Board board = this.game.getBoard();
		int value = 0;
		try{
			value = board.get(y, x);
		} catch(Exception e){
			System.out.println(y + "," + x + " resulted in null pointer exception");
		}
		if (value == 0){
			Rectangle col = createEmpty(x,y);
			pane.getChildren().add(col);
		} else if (value == 1){
			ImageView xView = createPlayer();
			pane.getChildren().add(xView);
		} else if (value == 2){
			ImageView oView = createOponent();
			pane.getChildren().add(oView);
		}
		return pane;
	}
	
	protected abstract ImageView createPlayer();
	protected abstract ImageView createOponent();
	
	private Rectangle createEmpty(int x, int y){
		Rectangle col = new Rectangle();
		col.setHeight(150);
		col.setWidth(150);
		col.setFill(Color.YELLOW);
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

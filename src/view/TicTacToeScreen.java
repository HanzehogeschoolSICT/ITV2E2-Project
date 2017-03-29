package view;

import controller.Game;
import controller.TicTacToe;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import view.MainMenu.StartHandlerClass;

public class TicTacToeScreen implements GameScreen{
	private Game game;
	private BorderPane pane;
	
	public TicTacToeScreen(TicTacToe ticTacToe) {
		this.game = ticTacToe;
	}

	public Pane create(){
		this.pane = new BorderPane();
		
		Label labelHeader = new Label("Tic-Tac-Toe");
		labelHeader.setFont(new Font("Calibri", 24));
		this.pane.setTop(labelHeader);
		
		Pane boardPane = createBoard();
		this.pane.setCenter(boardPane);
		return this.pane;
	}
	
	public Pane createBoard(){
		VBox board = new VBox();
		board.setSpacing(5);
		for (int y=0;y<this.game.getBoardRows();y++){
			HBox row = new HBox();
			row.setSpacing(5);
			for(int x=0;x<this.game.getBoardColumns();x++){
				Rectangle col = new Rectangle();
				col.setHeight(150);
				col.setWidth(150);
				col.setFill(Color.YELLOW);
				col.setUserData(y+","+x);
				col.setOnMouseClicked(new SquareHandlerClass());
				row.getChildren().add(col);
			}
			board.getChildren().add(row);
		}
		
		return board;
	}
	
	public Pane update(){
		return null;
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
					game.setMove(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
				} else {
					System.out.println("Game hasnt started");
				}
			}
		}
	}
}

package view;

import controller.Othello;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class OthelloScreen extends AbstractGameScreen{
	public OthelloScreen(Othello othello) {
		this.game = othello;
	}

	@Override
	public Pane create(){
		this.pane = new BorderPane();
		pane.setPadding(new Insets(5, 5, 5, 5));
		
		Label labelHeader = new Label("Othello");
		labelHeader.setFont(new Font("Calibri", 24));
		this.pane.setTop(labelHeader);
		
		Pane boardPane = createBoard();
		this.pane.setCenter(boardPane);
		
		return this.pane;
	}
	
	@Override
	protected ImageView createPlayer(){
		ImageView xView = new ImageView();
		Image xImage = new Image("images/black.png");
		xView.setImage(xImage);
		xView.setPreserveRatio(true);
		xView.setFitHeight(150);
		return xView;
	}
	
	@Override
	protected ImageView createOponent(){
		ImageView oView = new ImageView();
		Image oImage = new Image("images/white.png");
		oView.setImage(oImage);
		oView.setPreserveRatio(true);
		oView.setFitHeight(150);
		return oView;
	}
}

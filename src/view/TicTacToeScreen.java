package view;

import java.io.FileInputStream;

import controller.TicTacToe;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class TicTacToeScreen extends AbstractGameScreen{
	public TicTacToeScreen(TicTacToe ticTacToe) {
		this.game = ticTacToe;
	}

	@Override
	public Pane create(){
		this.pane = new BorderPane();
		pane.setPadding(new Insets(5, 5, 5, 5));
		
		Label labelHeader = new Label("Tic-Tac-Toe");
		labelHeader.setFont(new Font("Calibri", 24));
		this.pane.setTop(labelHeader);
		
		Pane boardPane = createBoard();
		this.pane.setCenter(boardPane);
		
		return this.pane;
	}
	
	@Override
	protected ImageView createPlayer(){
		ImageView xView = new ImageView();
		FileInputStream file = null;
		try{
			file = new FileInputStream("src/view/images/x.png");
		} catch (Exception e){
			System.out.println("Cant find image: src/view/images/x.png");
		}
		Image xImage = new Image(file);
		xView.setImage(xImage);
		xView.setPreserveRatio(true);
		xView.setFitHeight(150);
		return xView;
	}
	
	@Override
	protected ImageView createOponent(){
		ImageView oView = new ImageView();
		FileInputStream file = null;
		try{
			file = new FileInputStream("src/view/images/o.png");
		} catch (Exception e){
			System.out.println("Cant find image: src/view/images/o.png");
		}
		Image oImage = new Image(file);
		oView.setImage(oImage);
		oView.setPreserveRatio(true);
		oView.setFitHeight(150);
		return oView;
	}
}

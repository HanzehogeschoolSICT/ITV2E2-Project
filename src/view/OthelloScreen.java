package view;

import java.io.FileInputStream;

import controller.Othello;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class OthelloScreen extends AbstractGameScreen{
	public OthelloScreen(Othello othello) {
		this.game = othello;
	}

	@Override
	public Pane create(){
		this.pane = new BorderPane();
		pane.setPadding(new Insets(5, 5, 5, 5));
		
		Pane boardPane = createBoard();
		this.pane.setCenter(boardPane);
		
		return this.pane;
	}
	
	@Override
	protected ImageView createPlayer(){
		ImageView xView = new ImageView();
		FileInputStream file = null;
		try{
			file = new FileInputStream("src/view/images/black.png");
		} catch (Exception e){
			System.out.println("Cant find image: src/view/images/black.png");
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
			file = new FileInputStream("src/view/images/white.png");
		} catch (Exception e){
			System.out.println("Cant find image: src/view/images/white.png");
		}
		Image oImage = new Image(file);
		oView.setImage(oImage);
		oView.setPreserveRatio(true);
		oView.setFitHeight(150);
		return oView;
	}
}

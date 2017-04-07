package view;

import java.io.InputStream;
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
	public int getSquareSize(){
		return 50;
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
	public Image getPlayerImage(){
		InputStream file = null;
		try{
			file = this.getClass().getResourceAsStream("/view/images/black.png");
		} catch (Exception e){
			System.out.println("Cant find image: /view/images/black.png");
		}
		Image xImage = new Image(file);
		return xImage;
	}
	
	@Override
	protected ImageView createPlayer(){
		ImageView xView = new ImageView();
		Image xImage = getPlayerImage();
		xView.setImage(xImage);
		xView.setPreserveRatio(true);
		xView.setFitHeight(this.getSquareSize());
		return xView;
	}
	
	@Override
	public Image getOponentImage(){
		InputStream file = null;
		try{
			file = this.getClass().getResourceAsStream("/view/images/white.png");
		} catch (Exception e){
			System.out.println("Cant find image: /view/images/white.png");
		}
		Image oImage = new Image(file);
		return oImage;
	}
	
	@Override
	protected ImageView createOponent(){
		ImageView oView = new ImageView();
		Image oImage = getOponentImage();
		oView.setImage(oImage);
		oView.setPreserveRatio(true);
		oView.setFitHeight(this.getSquareSize());
		return oView;
	}
}

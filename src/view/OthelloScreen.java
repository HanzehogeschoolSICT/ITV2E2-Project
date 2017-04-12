package view;

import java.io.InputStream;
import controller.Othello;
import javafx.scene.image.Image;

public class OthelloScreen extends AbstractGameScreen{
	private Image whiteImage;
	private Image blackImage;
	
	public OthelloScreen(Othello othello) {
		this.game = othello;
		this.setBlackImage();
		this.setWhiteImage();
	}

	@Override
	public int getSquareSize(){
		return 50;
	}
	
	@Override
	public Image getPlayerImage(){
		return this.blackImage;
	}
	
	private void setBlackImage(){
		InputStream file = null;
		try{
			file = this.getClass().getResourceAsStream("/view/images/black.png");
		} catch (Exception e){
			System.out.println("Cant find image: /view/images/black.png");
		}
		this.blackImage = new Image(file);
		
	}
	
	@Override
	public Image getOponentImage(){
		return this.whiteImage;
		
	}
	
	private void setWhiteImage(){
		InputStream file = null;
		try{
			file = this.getClass().getResourceAsStream("/view/images/white.png");
		} catch (Exception e){
			System.out.println("Cant find image: /view/images/white.png");
		}
		this.whiteImage = new Image(file);
	}
}

package view;

import java.io.InputStream;
import controller.TicTacToe;
import javafx.scene.image.Image;

public class TicTacToeScreen extends AbstractGameScreen{
	private Image oImage;
	private Image xImage;
	
	public TicTacToeScreen(TicTacToe ticTacToe) {
		this.game = ticTacToe;
		this.setOImage();
		this.setXImage();
	}

	@Override
	public int getSquareSize(){
		return 150;
	}
	
	@Override
	public Image getPlayerImage(){
		return this.xImage;
	}
	
	private void setXImage(){
		InputStream file = null;
		try{
			file = this.getClass().getResourceAsStream("/view/images/x-red.png");
		} catch (Exception e){
			System.out.println("Cant find image: /view/images/x-red.png");
		}
		this.xImage = new Image(file);
	}
	
	@Override
	public Image getOponentImage(){
		return this.oImage;
	}
	
	private void setOImage(){
		InputStream file = null;
		try{
			file = this.getClass().getResourceAsStream("/view/images/o.png");
		} catch (Exception e){
			System.out.println("Cant find image: /images/o.png");
		}
		this.oImage = new Image(file);
	}
}

package view;

import java.io.InputStream;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public abstract class AbstractWindowScreen implements WindowScreen{

	private Image logoImage = null;
	private Image backgroundImage = null;

	public Pane getHeader(){
		HBox leftPane = new HBox();
		leftPane.setPadding(new Insets(15, 0, 0, 0));
		
		ImageView logoview = createLogo();
		leftPane.getChildren().add(logoview);
		
		Label name = new Label("ITV2E2");
		name.setPadding(new Insets(7, 0, 0, 15));
		name.setFont(new Font("Calibri", 30));
		leftPane.getChildren().add(name);
		
		HBox rightPane = new HBox();
		rightPane.setPadding(new Insets(25, 0, 0, 0));
		rightPane.getChildren().add(this.getHeaderButtons());
		
		AnchorPane pane = new AnchorPane(leftPane, rightPane);
		pane.setStyle("-fx-background-color: deepskyblue;");
		pane.setPadding(new Insets(0, 15, 10, 15));
		
		AnchorPane.setLeftAnchor(leftPane, 0.0);
		AnchorPane.setRightAnchor(rightPane, 0.0);
		
		return pane;
	}
	
	abstract public Pane getHeaderButtons();
	
	protected void setLogo(){
		InputStream file = null;
		try{
			file = this.getClass().getResourceAsStream("/view/images/logo.png");
		} catch (Exception e){
			System.out.println("Cant find image: /view/images/logo.png");
		}
		this.logoImage = new Image(file);
	}
	
	protected ImageView createLogo(){
		if (this.logoImage == null){this.setLogo();}
		ImageView logoView = new ImageView();
		logoView.setImage(this.logoImage);
		logoView.setPreserveRatio(true);
		logoView.setFitHeight(50);
		return logoView;
	}
	
	private void setBackground(){
		InputStream file = null;
		try{
			file = this.getClass().getResourceAsStream("/view/images/background.jpg");
		} catch (Exception e){
			System.out.println("Cant find image: /view/images/background.jpg");
		}
		this.backgroundImage = new Image(file);
	}
	
	protected Background getBackground(){
		if (this.backgroundImage == null){setBackground();}
		BackgroundImage myBI= new BackgroundImage(this.backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		return new Background(myBI);
	}
	
}

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
	
	protected ImageView createLogo(){
		ImageView logoView = new ImageView();
		InputStream file = null;
		try{
			file = this.getClass().getResourceAsStream("/view/images/logo.png");
		} catch (Exception e){
			System.out.println("Cant find image: /view/images/logo.png");
		}
		Image logoImage = new Image(file);
		logoView.setImage(logoImage);
		logoView.setPreserveRatio(true);
		logoView.setFitHeight(50);
		return logoView;
	}
	
	protected Background getBackground(){
		//Image: http://docs.oracle.com/javafx/2/get_started/background.jpg.html
		InputStream file = null;
		try{
			file = this.getClass().getResourceAsStream("/view/images/background.jpg");
		} catch (Exception e){
			System.out.println("Cant find image: /view/images/background.jpg");
		}
		Image logoImage = new Image(file);
		
		//http://stackoverflow.com/questions/9738146/javafx-how-to-set-scene-background-image
		BackgroundImage myBI= new BackgroundImage(logoImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		return new Background(myBI);
	}
	
}

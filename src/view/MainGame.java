package view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class MainGame implements WindowScreen {
	private Window window;
	private BorderPane pane;
	
	public MainGame(Window window) {
		this.window = window;
		this.create();
	}

	public void create(){
		this.pane = new BorderPane();
		Pane panePlayerlist = createPlayerlist();
		this.pane.setLeft(panePlayerlist);
	}
	
	public void update(){
		
	}
	
	public Pane getPane(){
		return this.pane;
	}
	
	private Pane createPlayerlist(){
		HBox pane = new HBox();
		
		Label labelHeader = new Label("Players");
		labelHeader.setFont(new Font("Calibri", 16));
		pane.getChildren().add(labelHeader);
		
		return pane;
	}
}

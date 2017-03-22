package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.Window;

public class Main extends Application {
	Window window;
	
	public static void main(String args[]){
		System.out.println("Starting ...");
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		this.window = new Window(this, primaryStage);
		this.window.init();
	}
}

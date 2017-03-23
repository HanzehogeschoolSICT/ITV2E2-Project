package view;

import controller.Main;
import javafx.application.Application;
import javafx.stage.Stage;

public class Window {
	private String currentWindow; 
	private Main main;
	private Stage primaryStage;
	
	public Window(Main main, Stage primaryStage){
		this.main = main;
		this.primaryStage = primaryStage;
	}
	
	public void init(){
		primaryStage.setTitle("ITV2E2");
		//primaryStage.setScene(mainScene);
		primaryStage.setResizable(false);
		primaryStage.show();
		
	}
}
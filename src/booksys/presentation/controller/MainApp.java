package booksys.presentation.controller;

import javafx.application.Application;
import javafx.stage.Stage;


public class MainApp extends Application{

	@Override
	public void start(Stage primaryStage) {
		
		Window.getInstance().loginUI(primaryStage);
		
	}
	
	public static void main(String[] args) {
		launch();
	}

}


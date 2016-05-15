package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFrame extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("MyCalculator");
			Parent root = 
					FXMLLoader.load(getClass().getClassLoader().getResource("view/Calculator.fxml"));
			primaryStage.setScene(new Scene(root));
	//		primaryStage.setResizable(false);
			primaryStage.show();
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

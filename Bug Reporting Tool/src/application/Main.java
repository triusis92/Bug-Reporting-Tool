package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Login.fxml"));

		Stage window = primaryStage;
		window.setTitle("Bug Reporting System");		
		Scene scene = new Scene(root, 372, 227);
		window.setScene(scene);
		window.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

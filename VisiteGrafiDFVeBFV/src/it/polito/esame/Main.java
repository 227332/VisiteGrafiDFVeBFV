package it.polito.esame;

import java.io.IOException;

import it.polito.esame.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
		
		FXMLLoader loader = new FXMLLoader( this.getClass().getResource("paroleT1.fxml")) ;
		loader.load() ;
		
		Parent root = loader.getRoot() ;
		Scene scene = new Scene(root) ;
		primaryStage.setScene(scene) ;
		primaryStage.show() ;
		
		
		ControllerT1 controller = loader.getController() ;
		
		Model model = new Model() ;
		controller.setModel(model) ;
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}

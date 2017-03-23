package semiPJ;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Layout 파일 로드 - 파일 위치 : 현재 
		Parent root = FXMLLoader.load(getClass().getResource("mainFxml.fxml"));
		Scene scene = new Scene(root);
				
		primaryStage.setTitle("Acorn PC cafe");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) { 
		launch(args);
	}

}

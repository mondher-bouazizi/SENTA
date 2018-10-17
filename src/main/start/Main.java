package main.start;
	
import java.io.IOException;

import commons.constants.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import windows.others.ConfirmBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	
	public static Parent root;
	public static Stage primaryStage;
	
	public static String user;
	
	@Override
	public void start(Stage primaryStage) {
		
		Loader.load();
		
		try {
			Main.primaryStage = new Stage();
			Main.primaryStage.setResizable(false);
			Main.primaryStage.setOnCloseRequest(e -> {
				e.consume();
				closeProgram();
			});
			root = FXMLLoader.load(Main.class.getResource("/windows/main/ConfigurationWindow.fxml"));
			
			 // Loader.setProjectGoal(Loader.ProjectGoal.QUANTIFICATION);
			 // root = FXMLLoader.load(Main.class.getResource("/windows/main/ClassifiersWindow.fxml"));
			
			Main.primaryStage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
			Main.primaryStage.setTitle("Twitter Classifier");
			Main.primaryStage.setScene(new Scene(root, 800, 600));
			Main.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
	
	/**
	 * Private method that handles the exit situations
	 */
	private void closeProgram() {
		if (ConfirmBox.display( "Exit", "Are you sure you want to exit?")) {
			System.exit(0);
		}
	}
	

}

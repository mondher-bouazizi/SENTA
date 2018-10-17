package windows.features.custompatterns;

import java.io.IOException;

import commons.constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomizePatterns {
	
	public static Parent root;
	public static Stage stage;
	
	public static void customize(int numberOfCategories) {
		
		CustomPatternsWindow.setParameters(numberOfCategories);
		
		CustomizePatterns.stage = new Stage();
		CustomizePatterns.stage.setResizable(false);
		CustomizePatterns.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
		CustomizePatterns.stage.setTitle("Customize Patterns Rules");
		try {
			CustomizePatterns.root = FXMLLoader.load(CustomizePatterns.class.getResource("/windows/features/custompatterns/CustomPatternsWindow.fxml"));
		} catch (IOException e) {
			System.out.println("Could not load the FXML file!!");
			e.printStackTrace();
		}
		CustomizePatterns.stage.setScene(new Scene(root, 480, 320));
		CustomizePatterns.stage.initModality(Modality.APPLICATION_MODAL);
		CustomizePatterns.stage.show();
	}

}

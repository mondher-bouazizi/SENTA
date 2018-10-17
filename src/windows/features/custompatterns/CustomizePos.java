package windows.features.custompatterns;

import java.io.IOException;

import commons.constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomizePos {
	
	public static Parent root;
	public static Stage stage;
	
	public static void customize(int numberOfCategories) {
		
		CustomPosWindow.setParameters(numberOfCategories);
		
		CustomizePos.stage = new Stage();
		CustomizePos.stage.setResizable(false);
		CustomizePos.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
		CustomizePos.stage.setTitle("Customize PoS categories");
		try {
			CustomizePos.root = FXMLLoader.load(CustomizePos.class.getResource("/windows/features/custompatterns/CustomPosWindow.fxml"));
		} catch (IOException e) {
			System.out.println("Could not load the FXML file!!");
			e.printStackTrace();
		}
		CustomizePos.stage.setScene(new Scene(root, 480, 320));
		CustomizePos.stage.initModality(Modality.APPLICATION_MODAL);
		CustomizePos.stage.show();
	}

}

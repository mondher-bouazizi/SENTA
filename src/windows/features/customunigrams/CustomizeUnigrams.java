package windows.features.customunigrams;

import java.io.IOException;

import commons.constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CustomizeUnigrams {
	
	public static Parent root;
	public static Stage stage;
	
	public static void customize() {
		
		CustomizeUnigrams.stage = new Stage();
		CustomizeUnigrams.stage.setResizable(false);
		CustomizeUnigrams.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
		CustomizeUnigrams.stage.setTitle("Add/remove unigrams");
		try {
			CustomizeUnigrams.root = FXMLLoader.load(CustomizeUnigrams.class.getResource("/windows/features/customunigrams/CustomUnigramsWindow.fxml"));
		} catch (IOException e) {
			System.out.println("Could not load the FXML file!!");
			e.printStackTrace();
		}
		CustomizeUnigrams.stage.setScene(new Scene(root, 480, 460));
		CustomizeUnigrams.stage.initModality(Modality.APPLICATION_MODAL);
		CustomizeUnigrams.stage.show();
	}

}

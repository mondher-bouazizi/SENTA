package windows.classifiers.selectfeatures;

import java.io.IOException;

import commons.constants.Commons;
import commons.constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FeatureSelectorWindowsManager {
	
	public static Parent root;
	public static Stage stage;
	
	public static void displayParametersWindow() {
		
		FeatureSelectorWindowsManager.stage = new Stage();
		FeatureSelectorWindowsManager.stage.setResizable(false);
		FeatureSelectorWindowsManager.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
		FeatureSelectorWindowsManager.stage.setTitle("Classifier parameters");
		
		
		try {
			FeatureSelectorWindowsManager.root = FXMLLoader.load(FeatureSelectorWindowsManager.class.getResource("/windows/classifiers/selectfeatures/FeatureSelectorWindow.fxml"));
			Commons.print("FXML File loaded successfully!");
		} catch (IOException e) {
			System.out.println("Could not load the FXML file!!");
			e.printStackTrace();
		}
		FeatureSelectorWindowsManager.stage.setScene(new Scene(root, 480, 700));
		FeatureSelectorWindowsManager.stage.initModality(Modality.APPLICATION_MODAL);
		FeatureSelectorWindowsManager.stage.show();
		


	}

}

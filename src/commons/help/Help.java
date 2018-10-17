package commons.help;

import java.io.IOException;

import commons.constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Help {
	
	public static Parent root;
	public static Stage stage;
	
	public static void help(HelpConstants.FeaturesSets featureSet) {
		HelpWindow.setParameters(featureSet);
		
		Help.stage = new Stage();
		Help.stage.setResizable(false);
		Help.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
		Help.stage.setTitle("Twitter Classifier - Help");
		try {
			Help.root = FXMLLoader.load(Help.class.getResource("/classifier/help/HelpWindow.fxml"));
		} catch (IOException e) {
			System.out.println("Could not load the FXML file!!");
			e.printStackTrace();
		}
		Help.stage.setScene(new Scene(root, 640, 480));
		Help.stage.initModality(Modality.APPLICATION_MODAL);
		Help.stage.show();
	}

}

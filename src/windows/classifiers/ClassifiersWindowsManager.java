package windows.classifiers;

import java.io.IOException;

import commons.constants.Commons;
import commons.constants.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.items.ClassifierParameters;

public class ClassifiersWindowsManager {
	
	public static Parent root;
	public static Stage stage;
	
	public static void displayParametersWindow(ClassifierParameters.Classifier classifier) {
		
		ClassifiersWindowsManager.stage = new Stage();
		ClassifiersWindowsManager.stage.setResizable(false);
		ClassifiersWindowsManager.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
		ClassifiersWindowsManager.stage.setTitle("Classifier parameters");
		
		if (classifier.equals(ClassifierParameters.Classifier.RANDOM_FOREST)) {
			try {
				ClassifiersWindowsManager.root = FXMLLoader.load(ClassifiersWindowsManager.class.getResource("/windows/classifiers/RandomForestParametersWindow.fxml"));
				Commons.print("FXML File loaded successfully!");
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			ClassifiersWindowsManager.stage.setScene(new Scene(root, 480, 700));
			ClassifiersWindowsManager.stage.initModality(Modality.APPLICATION_MODAL);
			ClassifiersWindowsManager.stage.show();
		}
		
		if (classifier.equals(ClassifierParameters.Classifier.NAIVE_BAYES)) {
			try {
				ClassifiersWindowsManager.root = FXMLLoader.load(ClassifiersWindowsManager.class.getResource("/windows/classifiers/NaiveBayesParametersWindow.fxml"));
				Commons.print("FXML File loaded successfully!");
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			ClassifiersWindowsManager.stage.setScene(new Scene(root, 480, 700));
			ClassifiersWindowsManager.stage.initModality(Modality.APPLICATION_MODAL);
			ClassifiersWindowsManager.stage.show();
		}
		
		if (classifier.equals(ClassifierParameters.Classifier.NAIVE_BAYES_UPDATEABLE)) {
			try {
				ClassifiersWindowsManager.root = FXMLLoader.load(ClassifiersWindowsManager.class.getResource("/windows/classifiers/NaiveBayesUpdateableParametersWindow.fxml"));
				Commons.print("FXML File loaded successfully!");
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			ClassifiersWindowsManager.stage.setScene(new Scene(root, 480, 700));
			ClassifiersWindowsManager.stage.initModality(Modality.APPLICATION_MODAL);
			ClassifiersWindowsManager.stage.show();
		}
		
		if (classifier.equals(ClassifierParameters.Classifier.J48)) {
			try {
				ClassifiersWindowsManager.root = FXMLLoader.load(ClassifiersWindowsManager.class.getResource("/windows/classifiers/J48ParametersWindow.fxml"));
				Commons.print("FXML File loaded successfully!");
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			ClassifiersWindowsManager.stage.setScene(new Scene(root, 480, 700));
			ClassifiersWindowsManager.stage.initModality(Modality.APPLICATION_MODAL);
			ClassifiersWindowsManager.stage.show();
		}
		
		if (classifier.equals(ClassifierParameters.Classifier.K_STAR)) {
			try {
				ClassifiersWindowsManager.root = FXMLLoader.load(ClassifiersWindowsManager.class.getResource("/windows/classifiers/KStarParametersWindow.fxml"));
				Commons.print("FXML File loaded successfully!");
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			ClassifiersWindowsManager.stage.setScene(new Scene(root, 480, 700));
			ClassifiersWindowsManager.stage.initModality(Modality.APPLICATION_MODAL);
			ClassifiersWindowsManager.stage.show();
		}
		
		if (classifier.equals(ClassifierParameters.Classifier.HOEFFDING_TREE)) {
			try {
				ClassifiersWindowsManager.root = FXMLLoader.load(ClassifiersWindowsManager.class.getResource("/windows/classifiers/HoeffdingTreeParametersWindow.fxml"));
				Commons.print("FXML File loaded successfully!");
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			ClassifiersWindowsManager.stage.setScene(new Scene(root, 480, 700));
			ClassifiersWindowsManager.stage.initModality(Modality.APPLICATION_MODAL);
			ClassifiersWindowsManager.stage.show();
		}

	}

}

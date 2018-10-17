package windows.main;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import main.items.Features;
import main.items.Parameters;
import main.start.Main;
import main.start.ClassificationProblem;
import windows.others.AlertBox;
import windows.others.ConfirmBox;

public class EndWindow implements Initializable {
	
	// =====================================//
	//          Non-FXML Components         //
	// =====================================//
	
	private String tempProjectLocation;
	
	
	// =====================================//
	//            FXML Components           //
	// =====================================//
	
	@FXML Label projectLocation;
	@FXML Label projectType;
	@FXML Label classes;
	
	@FXML Label trainingSetLocation;
	@FXML Label trainingSetSize;
	@FXML Label testSetLocation;
	@FXML Label testSetSize;
	
	@FXML Label isSaveCsv;
	@FXML Label isSaveTxt;
	@FXML Label isSaveArff;
	@FXML Label isSaveTopWords;
	@FXML Label isSaveBasicPatterns;
	@FXML Label isSaveAdvancedPatterns;
	
	@FXML Button openInSystemExplorer;
	@FXML Button goToMainMenu;
	
	@FXML Button backButton;
	@FXML Button nextButton;
	@FXML Button exitButton;
	
	
	// =====================================//
	//       FXML Components ACTIONS        //
	// =====================================//
	
	// Handle the select button for the Project directory
	@FXML public void handleOpenInSystemExplorer() {
		try {
			Desktop.getDesktop().open(new File(Parameters.getProjectLocation() + "\\" + Parameters.getProjectName()));
		} catch (IOException e) {
			AlertBox.display("Error", "No default System Explorer is set!", "OK");
			e.printStackTrace();
		}
	}
	
	// Handle the "Proceed to classification" button
	@FXML public void handleGoToClassification() {
		try {
			Main.root = FXMLLoader.load(getClass().getResource("/windows/main/ClassifiersWindow.fxml"));
			Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
			Main.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Handle the select button for the Project directory
	@FXML public void handleGoToMainMenu() {
		if (ConfirmBox.display("Go to the main menu?", "Are you sure you want to go to the main menu?")) {
			try {
				Parameters.reinitialize();
				Features.reinitialize();
				ClassificationProblem.reinitialize();
				Main.root = FXMLLoader.load(getClass().getResource("/windows/main/ConfigurationWindow.fxml"));
				Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
				Main.primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Handle Back
	@FXML public void handleBackButton() {
		System.out.println("Back Button clicked");
		try {
			Main.root = FXMLLoader.load(getClass().getResource("/windows/main/CreateProjectWindow.fxml"));
			Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
			Main.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Handle Exit
	@FXML public void handleExitButton() {
		System.out.println("Exit Button clicked");
		closeProgram();
	}
	
	
	//======================================//
	//            INITIALIZATION            //
	//======================================//
	
	/**
	 * Initialize the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Project Location
		tempProjectLocation = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + Parameters.getProjectName() + "-Config.senta"  ;
		projectLocation.setText(tempProjectLocation);
		
		// Project type
		if (Parameters.getTypeOfProject()!=null) {
			if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
				projectType.setText("Training set + Test set");
			} else {
				projectType.setText("Training set + Non-annotated set");
			}
		} else {
			projectType.setText("#N/A");
		}
		
		// Classes
		if (Parameters.getClasses()!=null && !Parameters.getClasses().isEmpty()) {
			classes.setText(Parameters.getClasses().toString());
		} else {
			classes.setText("#N/A");
		}
		
		// Training set Location
		if (Parameters.getTrainingSetLocation()!=null) {
			trainingSetLocation.setText(Parameters.getTrainingSetLocation());

		} else {
			trainingSetLocation.setText("#N/A");
		}
		
		if (Parameters.getTrainingSet()!=null) {
			trainingSetSize.setText(Parameters.getTrainingSet().size() + "");
		}
		
		// Test set Location
		if (Parameters.getTestSetLocation()!=null) {
			testSetLocation.setText(Parameters.getTestSetLocation());

		} else {
			testSetLocation.setText("#N/A");
		}
		
		if (Parameters.getTestSet()!=null) {
			testSetSize.setText(Parameters.getTestSet().size() + "");
		}
		
		// Extra files
		if (Parameters.isSaveCsv()) {
			isSaveCsv.setText("TRUE");
			isSaveCsv.setTextFill(Color.GREEN);
		} else {
			isSaveCsv.setText("FALSE");
			isSaveCsv.setTextFill(Color.RED);
		}
		
		if (Parameters.isSaveTxt()) {
			isSaveTxt.setText("TRUE");
			isSaveTxt.setTextFill(Color.GREEN);
		} else {
			isSaveTxt.setText("FALSE");
			isSaveTxt.setTextFill(Color.RED);
		}
		
		if (Parameters.isSaveArff()) {
			isSaveArff.setText("TRUE");
			isSaveArff.setTextFill(Color.GREEN);
		} else {
			isSaveArff.setText("FALSE");
			isSaveArff.setTextFill(Color.RED);
		}
		
		if (Parameters.isSaveTopWords()) {
			isSaveTopWords.setText("TRUE");
			isSaveTopWords.setTextFill(Color.GREEN);
		} else {
			isSaveTopWords.setText("FALSE");
			isSaveTopWords.setTextFill(Color.RED);
		}
		
		if (Parameters.isSaveBasicPatterns()) {
			isSaveBasicPatterns.setText("TRUE");
			isSaveBasicPatterns.setTextFill(Color.GREEN);
		} else {
			isSaveBasicPatterns.setText("FALSE");
			isSaveBasicPatterns.setTextFill(Color.RED);
		}
		
		if (Parameters.isSaveAdvancedPatterns()) {
			isSaveAdvancedPatterns.setText("TRUE");
			isSaveAdvancedPatterns.setTextFill(Color.GREEN);
		} else {
			isSaveAdvancedPatterns.setText("FALSE");
			isSaveAdvancedPatterns.setTextFill(Color.RED);
		}
		
		// Disable the next button because it is useless
		nextButton.setDisable(true);
		
	}
	
	
	//======================================//
	//            PRIVATE METHODS           //
	//======================================//
	
	/**
	 * Private method that handles the exit situations
	 */
	private void closeProgram() {
		System.out.println("Cancel Button clicked");
		if (ConfirmBox.display( "Exit", "Are you sure you want to exis?")) {
			System.exit(0);
		}
	}

}

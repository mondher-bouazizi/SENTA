package windows.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import commons.constants.Commons;
import commons.constants.Constants;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import main.start.Loader;
import main.start.Main;
import windows.others.AlertBox;
import windows.others.ConfirmBox;

public class ConfigurationWindow implements Initializable {
	
	// =====================================//
	//            FXML Components           //
	// =====================================//
	
	@FXML ToggleGroup projectSelectionGroup;
	@FXML ToggleGroup projectTypeGroup;
	
	@FXML RadioButton openExistingProjectRadioButton;
	@FXML RadioButton openWekaFileRadioButton;
	@FXML RadioButton startNewProjectRadioButton;
	
	@FXML RadioButton classificationRadio;
	@FXML RadioButton quantificationRadio;
	
	@FXML Button backButton;
	@FXML Button nextButton;
	@FXML Button cancelButton;
	
	
	// =====================================//
	//        FXML Components Actions       //
	// =====================================//
	
	/**
	 * Handles the case if any of the radios is selected ("Next" Button will be enabled)
	 */
	@FXML public void handleRadioButtons() {
		System.out.println("A Radio Button is selected");
		if (openExistingProjectRadioButton.isSelected() || openWekaFileRadioButton.isSelected()) {
			classificationRadio.setSelected(false);
			quantificationRadio.setSelected(false);
		}
		nextButton.setDisable(false);
	}
	
	@FXML public void handleSubRadioButtons() {
		if (classificationRadio.isSelected() || quantificationRadio.isSelected()) {
			startNewProjectRadioButton.setSelected(true);
		}
		nextButton.setDisable(false);
	}
	
	@FXML public void handleBackButton() {
		System.out.println("Back Button clicked");
		// The back button will not work on this window
	}
	
	@FXML public void handleNextButton() {
		System.out.println("Next Button clicked");
		if (!openExistingProjectRadioButton.isSelected() && !openWekaFileRadioButton.isSelected() && !startNewProjectRadioButton.isSelected()) {
			System.out.println("Nothing is selected");
		} else {
			if(openExistingProjectRadioButton.isSelected()) {
				// TODO add the "Open existing project" option
				Loader.setOption(Constants.TypeOfProject.OPEN_PROJECT);
				try {
					Main.root = FXMLLoader.load(getClass().getResource("/windows/main/OpenProjectWindow.fxml"));
					Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
					Main.primaryStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
			if(openWekaFileRadioButton.isSelected()) {
				Loader.setOption(Constants.TypeOfProject.OPEN_FEATURES_FILE);
				 AlertBox.display("Feature Deactivated for now!", "This feature been deactivated for now until it is fixed!", "OK");
//				try {
//					Main.root = FXMLLoader.load(getClass().getResource("/windows/main/OpenFeaturesFileWindow.fxml"));
//					Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
//					Main.primaryStage.show();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
				
			}
			
			if(startNewProjectRadioButton.isSelected()) {
				if (classificationRadio.isSelected() || quantificationRadio.isSelected()) {
					if (classificationRadio.isSelected()) {
						Loader.setProjectGoal(Loader.ProjectGoal.CLASSIFICATION);
					} else if (quantificationRadio.isSelected()) {
						Loader.setProjectGoal(Loader.ProjectGoal.QUANTIFICATION);
					}
					
					Loader.setOption(Constants.TypeOfProject.START_NEW_PROJECT);
					try {
						Main.root = FXMLLoader.load(getClass().getResource("/windows/main/StartNewProjectWindow.fxml"));
						Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
						Main.primaryStage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				} else {
					AlertBox.display("Choose your project type", "You have not chosen which task you are going to perform (Classification or Quantification). Make sure to choose one before proceeding", "OK");
				}
			}
			
		}

	}
	
	@FXML public void handleCancelButton() {
		closeProgram();
	}

	
	// =====================================//
	//            INITIALIZATION            //
	// =====================================//
	
	/**
	 * Initialize the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		backButton.setDisable(true);
		nextButton.setDisable(true);
		
		if (Loader.getOption()!=null) {
			if (Loader.getOption().equals(Constants.TypeOfProject.OPEN_PROJECT)) {
				openExistingProjectRadioButton.setSelected(true);
				nextButton.setDisable(false);
			}
			if (Loader.getOption().equals(Constants.TypeOfProject.OPEN_FEATURES_FILE)) {
				openWekaFileRadioButton.setSelected(true);
				nextButton.setDisable(false);
			}
			if (Loader.getOption().equals(Constants.TypeOfProject.START_NEW_PROJECT)) {
				startNewProjectRadioButton.setSelected(true);
				nextButton.setDisable(false);
			} 
		}
		
		if (Loader.getProjectGoal() != null) {
			Commons.print("Type of project is not null!");
			if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
				classificationRadio.setSelected(true);
			} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
				quantificationRadio.setSelected(true);
			}
		} else {
			Commons.print("null, this is the first time running the program or you made something wrong!");
		}
		
		// Key pressing
		openExistingProjectRadioButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {
					keyEvent.consume();
					nextButton.fire();
				}
			}
		});
		
		openWekaFileRadioButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {
					keyEvent.consume();
					nextButton.fire();
				}
			}
		});
		
		startNewProjectRadioButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {
					keyEvent.consume();
					nextButton.fire();
				}
			}
		});
		
	}
	
	
	// =====================================//
	//            OTHER METHODS             //
	// =====================================//
	
	/**
	 * Handle the exit situations
	 */
	private void closeProgram() {
		System.out.println("Cancel Button clicked");
		if (ConfirmBox.display( "Exit", "Are you sure you want to exis?")) {
			System.exit(0);
		}
	}

}

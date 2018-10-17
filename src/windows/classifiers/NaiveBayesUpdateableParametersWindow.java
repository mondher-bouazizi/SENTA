package windows.classifiers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import main.items.ClassifierParameters;
import windows.others.AlertBox;

public class NaiveBayesUpdateableParametersWindow implements Initializable {
	
	// =====================================//
	//          Non-FXML Components         //
	// =====================================//
	
	private static enum ErrorCode {
		BatchSize,
		NumDecimalPlaces,
		Fine
	}
	
	
	// =====================================//
	//            FXML Components           //
	// =====================================//
	
	@FXML TextField batchSizeField;
	
	@FXML ToggleButton debugButton;
	@FXML ToggleButton displayModelInOldFormatButton;
	@FXML ToggleButton doNotCheckCapabilitiesButton;
	
	@FXML TextField numDecimalPlacesField;
	
	@FXML ToggleButton userKernelEstimatorButton;
	@FXML ToggleButton useSupervisedDiscButton;
	
	@FXML Button def;
	@FXML Button ok;
	@FXML Button cancel;
	
	
	// =====================================//
	//        FXML Components Actions       //
	// =====================================//
	
	/**
	 * Action when any of the toggle buttons is pressed
	 */
	@FXML public void handleToggleButtons() {
		
		// debugButton
		if (debugButton.isSelected()) {
			debugButton.setText("True");
		} else {
			debugButton.setText("False");
		}
		
		// displayModelInOldFormatButton
		if (displayModelInOldFormatButton.isSelected()) {
			displayModelInOldFormatButton.setText("True");
		} else {
			displayModelInOldFormatButton.setText("False");
		}
		
		// doNotCheckCapabilitiesButton
		if (doNotCheckCapabilitiesButton.isSelected()) {
			doNotCheckCapabilitiesButton.setText("True");
		} else {
			doNotCheckCapabilitiesButton.setText("False");
		}
		
		// breakTizeRandomlyButton
		if (userKernelEstimatorButton.isSelected()) {
			userKernelEstimatorButton.setText("True");
		} else {
			userKernelEstimatorButton.setText("False");
		}
		
		// calcOutOfBagButton
		if (useSupervisedDiscButton.isSelected()) {
			useSupervisedDiscButton.setText("True");
		} else {
			useSupervisedDiscButton.setText("False");
		}
		
	}
	
	/**
	 * Set the default parameters
	 */
	@FXML public void handleDefault() {
		System.out.println("Default clicked!");
		
		batchSizeField.setText("100");
		
		debugButton.setText("False");
		debugButton.setSelected(false);
		
		displayModelInOldFormatButton.setText("False");
		displayModelInOldFormatButton.setSelected(false);
		
		doNotCheckCapabilitiesButton.setText("False");
		doNotCheckCapabilitiesButton.setSelected(false);
		
		numDecimalPlacesField.setText("2");
		
		
		userKernelEstimatorButton.setText("False");
		userKernelEstimatorButton.setSelected(false);
		
		useSupervisedDiscButton.setText("False");
		useSupervisedDiscButton.setSelected(false);
		
	}
	
	/**
	 * Save the parameters and close the window
	 */
	@FXML public void handleOk() {
		System.out.println("OK clicked!");
		
		if (checkParameters().equals(ErrorCode.BatchSize)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Batch Size]", "OK");
		} else if (checkParameters().equals(ErrorCode.NumDecimalPlaces)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Num Decimal Places]", "OK");
		} else if (checkParameters().equals(ErrorCode.Fine)) {
			saveParameters();
			ClassifiersWindowsManager.stage.close();
		}
	}
	
	/**
	 * Close the window without saving the parameters
	 */
	@FXML public void handleCancel() {
		System.out.println("Cancel clicked!");
		ClassifiersWindowsManager.stage.close();
	}
	
	
	// =====================================//
	//            Initialization            //
	// =====================================//
	
	/**
	 * Initialize the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Load default parameters if first time
		ClassifierParameters.naiveBayesUpdateableInitialize();
		
		// Enable entering Digits only
		batchSizeField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	batchSizeField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		numDecimalPlacesField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	numDecimalPlacesField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		// Initialize the Text Field values
		initializeTextFields();
		
		// Initialize the Toggle Button values
		initializeToggleButtons();
		
		
	}
	
	/**
	 * Initialize the different text fields in the scene with reference to the class {@link main.items.ClassifierParameters.java}
	 */
	private void initializeTextFields() {
		batchSizeField.setText(ClassifierParameters.getNbuBatchSize() + "");
		numDecimalPlacesField.setText(ClassifierParameters.getNbuNumDecimalPlaces() + "");
	}
	
	/**
	 * Initialize the different Toggle Buttons in the scene with reference to the class {@link main.items.ClassifierParameters.java}
	 */
	private void initializeToggleButtons() {
		
		if(ClassifierParameters.isNbuDebug()) {
			debugButton.setSelected(true);
			debugButton.setText("True");
		} else {
			debugButton.setSelected(false);
			debugButton.setText("False");
		}
		
		if(ClassifierParameters.isNbuDisplayModelInOldFormat()) {
			displayModelInOldFormatButton.setSelected(true);
			displayModelInOldFormatButton.setText("True");
		} else {
			displayModelInOldFormatButton.setSelected(false);
			displayModelInOldFormatButton.setText("False");
		}
		
		if(ClassifierParameters.isNbuDoNotCheckCapabilities()) {
			displayModelInOldFormatButton.setSelected(true);
			displayModelInOldFormatButton.setText("True");
		} else {
			displayModelInOldFormatButton.setSelected(false);
			displayModelInOldFormatButton.setText("False");
		}

	}
	
	
	// =====================================//
	//             Other Methods            //
	// =====================================//
	
	/**
	 * Checks if any of the Text Fields presents an error (empty or containing non allowed characters
	 * @return the error code corresponding to which Text Field has an error (first one checked first)
	 */
	private ErrorCode checkParameters() {
		
		// Check Batch Size
		try {
			Integer.parseInt(batchSizeField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.BatchSize;
		}
		
		// Check numDecimalPlaces
		try {
			Integer.parseInt(numDecimalPlacesField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.NumDecimalPlaces;
		}
		
		// Return "Everything is fine"
		return ErrorCode.Fine;
		
	}

	/**
	 * Save the different parameters as specified by the user in the class {@link main.items.ClassifierParameters.java}
	 */
	private void saveParameters() {
		
		int batchSize = 100;
		int numDecimalPlaces = 2;
		
		boolean debug = debugButton.isSelected();
		boolean computeAttributeImportance = displayModelInOldFormatButton.isSelected();
		boolean displayModelInOldFormat = displayModelInOldFormatButton.isSelected();
		boolean userKernelEstimator = userKernelEstimatorButton.isSelected();
		boolean useSupervisedDisc = useSupervisedDiscButton.isSelected();
		
		// batchSize
		try {
			batchSize = Integer.parseInt(batchSizeField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}

		
		// numDecimalPlaces
		try {
			numDecimalPlaces = Integer.parseInt(numDecimalPlacesField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// Set Parameters
		ClassifierParameters.naiveBayesUpdateableSetParameters(batchSize, numDecimalPlaces,
				debug, computeAttributeImportance, displayModelInOldFormat, userKernelEstimator, useSupervisedDisc);
	}

	
}

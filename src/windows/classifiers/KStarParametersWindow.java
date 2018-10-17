package windows.classifiers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import main.items.ClassifierParameters;
import windows.others.AlertBox;

public class KStarParametersWindow implements Initializable {
	
	// =====================================//
	//          Non-FXML Components         //
	// =====================================//
	
	private static enum ErrorCode {
		BatchSize,
		GlobalBlend,
		MissingMode,
		NumDecimalPlaces,
		Fine
	}
	
	
	// =====================================//
	//            FXML Components           //
	// =====================================//
	
	@FXML TextField batchSizeField;
	
	@FXML ToggleButton debugButton;
	@FXML ToggleButton doNotCheckCapabilitiesButton;
	@FXML ToggleButton entropicAutoBlendButton;
	
	@FXML TextField globalBlendField;
	
	@FXML ComboBox<String> missingModeCombo;
	
	@FXML TextField numDecimalPlacesField;
		
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
		
		// doNotCheckCapabilitiesButton
		if (doNotCheckCapabilitiesButton.isSelected()) {
			doNotCheckCapabilitiesButton.setText("True");
		} else {
			doNotCheckCapabilitiesButton.setText("False");
		}
		
		// entropicAutoBlendButton
		if (entropicAutoBlendButton.isSelected()) {
			entropicAutoBlendButton.setText("True");
		} else {
			entropicAutoBlendButton.setText("False");
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
		
		doNotCheckCapabilitiesButton.setText("False");
		doNotCheckCapabilitiesButton.setSelected(false);
		
		entropicAutoBlendButton.setText("False");
		entropicAutoBlendButton.setSelected(false);
		
		globalBlendField.setText("20");
		
		missingModeCombo.setValue("Average column entropy curves");
		
		numDecimalPlacesField.setText("2");

	}
	
	/**
	 * Save the parameters and close the window
	 */
	@FXML public void handleOk() {
		System.out.println("OK clicked!");
		
		if (checkParameters().equals(ErrorCode.BatchSize)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Batch Size]", "OK");
		} else if (checkParameters().equals(ErrorCode.GlobalBlend)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Global Blend]", "OK");
		} else if (checkParameters().equals(ErrorCode.MissingMode)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Missing Mode]", "OK");
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
		ClassifierParameters.kStarInitialize();
		
		if (missingModeCombo.getItems().isEmpty()) {
			missingModeCombo.getItems().addAll(
					"Ignore the instances with missing values",
					"Treat missing values as maximally different",
					"Normalize over the attributes",
					"Average column entropy curves"
					);
		}
		
		missingModeCombo.setValue("Average column entropy curves");
		
		// Enable entering Digits only
		batchSizeField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	batchSizeField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		globalBlendField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*.")) {
		        	globalBlendField.setText(newValue.replaceAll("[^\\d.]", ""));
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
		batchSizeField.setText(ClassifierParameters.getkStarbatchSize() + "");
		globalBlendField.setText(ClassifierParameters.getkStarglobalBlend() + "");
		numDecimalPlacesField.setText(ClassifierParameters.getkStarnumDecimalPlaces() + "");
	}
	
	/**
	 * Initialize the different Toggle Buttons in the scene with reference to the class {@link main.items.ClassifierParameters.java}
	 */
	private void initializeToggleButtons() {
		
		
		if (ClassifierParameters.iskStardebug()) {
			debugButton.setSelected(true);
			debugButton.setText("True");
		} else {
			debugButton.setSelected(false);
			debugButton.setText("False");
		}
		
		if (ClassifierParameters.iskStardoNotCheckCapabilities()) {
			doNotCheckCapabilitiesButton.setSelected(true);
			doNotCheckCapabilitiesButton.setText("True");
		} else {
			doNotCheckCapabilitiesButton.setSelected(false);
			doNotCheckCapabilitiesButton.setText("False");
		}
		
		if (ClassifierParameters.iskStarentropicAutoBlend()) {
			entropicAutoBlendButton.setSelected(true);
			entropicAutoBlendButton.setText("True");
		} else {
			entropicAutoBlendButton.setSelected(false);
			entropicAutoBlendButton.setText("False");
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
		
		// Check Global Blend
		try {
			Integer.parseInt(globalBlendField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.GlobalBlend;
		}
		
		// Check numDecimalPlaces
		try {
			Integer.parseInt(numDecimalPlacesField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.NumDecimalPlaces;
		}
		
		// Check missing mode
		ArrayList<String> values = new ArrayList<String>(Arrays.asList("Ignore the instances with missing values",
					"Treat missing values as maximally different",
					"Normalize over the attributes",
					"Average column entropy curves"));
		
		if (!values.contains(missingModeCombo.getValue())) {
					return ErrorCode.MissingMode;
				}
		
		// Everything is fine
		return ErrorCode.Fine;
		
	}

	/**
	 * Save the different parameters as specified by the user in the class {@link main.items.ClassifierParameters.java}
	 */
	private void saveParameters() {
		
		// batchSize
		int batchSize = 100;
		try {
			batchSize = Integer.parseInt(batchSizeField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// globalBlend
		int globalBlend = 20;
		try {
			globalBlend = Integer.parseInt(globalBlendField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// numDecimalPlaces
		int numDecimalPlaces = 2;
		try {
			numDecimalPlaces = Integer.parseInt(numDecimalPlacesField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// debug
		boolean debug = debugButton.isSelected();
		
		// doNotCheckCapabilities
		boolean doNotCheckCapabilities = doNotCheckCapabilitiesButton.isSelected();
		
		// entropicAutoBlend
		boolean entropicAutoBlend = entropicAutoBlendButton.isSelected();
		
		// missingMode
		ClassifierParameters.KStarMissingMode missingMode = ClassifierParameters.KStarMissingMode.Avg_Column_Entropy_Curves;
		
		if (missingModeCombo.getValue().equals("Ignore the instances with missing values")) {
			missingMode = ClassifierParameters.KStarMissingMode.Ignore_The_Instances_With_Missing_Values;
		} else if (missingModeCombo.getValue().equals("Treat missing values as maximally different")) {
			missingMode = ClassifierParameters.KStarMissingMode.Treat_Missing_Values_As_Maximally_Different;
		} else if (missingModeCombo.getValue().equals("Normalize over the attributes")) {
			missingMode = ClassifierParameters.KStarMissingMode.Normalize_Over_The_Attributes;
		} else if (missingModeCombo.getValue().equals("Average column entropy curves")) {
			missingMode = ClassifierParameters.KStarMissingMode.Avg_Column_Entropy_Curves;
		}
		
		ClassifierParameters.kStarSetParameters(batchSize, debug, doNotCheckCapabilities, entropicAutoBlend, globalBlend, missingMode, numDecimalPlaces);
	}

	
}

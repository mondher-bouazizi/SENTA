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

public class J48ParametersWindow implements Initializable {
	
	// =====================================//
	//          Non-FXML Components         //
	// =====================================//
	
	private static enum ErrorCode {
		BatchSize,
		ConfidenceFactor,
		MinNumObj,
		NumDecimalPlaces,
		NumFolds,
		Seed,
		Fine
	}
	
	
	// =====================================//
	//            FXML Components           //
	// =====================================//
	
	@FXML TextField batchSizeField;
	
	@FXML ToggleButton binarySplitButton;
	@FXML ToggleButton collapseTreeButton;
	
	@FXML TextField confidenceFactorField;
	
	@FXML ToggleButton debugButton;
	@FXML ToggleButton doNotCheckCapabilitiesButton;
	@FXML ToggleButton doNotMakeSplitPAVButton;
	
	@FXML TextField minNumObjField;
	@FXML TextField numDecimalPlacesField;
	@FXML TextField numFoldsField;
	
	@FXML ToggleButton reduceErrorPruningButton;
	@FXML ToggleButton saveInstanceDataButton;
	
	@FXML TextField seedField;
	
	@FXML ToggleButton subTreeRaisingButton;
	@FXML ToggleButton unprunedButton;
	@FXML ToggleButton useLaplaceButton;
	@FXML ToggleButton useMDLcorrectionButton;
	
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
		
		// binarySplitButton
		if (binarySplitButton.isSelected()) {
			binarySplitButton.setText("True");
		} else {
			binarySplitButton.setText("False");
		}
		
		// collapseTreeButton
		if (collapseTreeButton.isSelected()) {
			collapseTreeButton.setText("True");
		} else {
			collapseTreeButton.setText("False");
		}
		
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
		
		// doNotMakeSplitPAVButton
		if (doNotMakeSplitPAVButton.isSelected()) {
			doNotMakeSplitPAVButton.setText("True");
		} else {
			doNotMakeSplitPAVButton.setText("False");
		}
		
		// reduceErrorPruningButton
		if (reduceErrorPruningButton.isSelected()) {
			reduceErrorPruningButton.setText("True");
		} else {
			reduceErrorPruningButton.setText("False");
		}
		
		// saveInstanceDataButton
		if (saveInstanceDataButton.isSelected()) {
			saveInstanceDataButton.setText("True");
		} else {
			saveInstanceDataButton.setText("False");
		}
		
		// subTreeRaisingButton
		if (subTreeRaisingButton.isSelected()) {
			subTreeRaisingButton.setText("True");
		} else {
			subTreeRaisingButton.setText("False");
		}
		
		// unprunedButton
		if (unprunedButton.isSelected()) {
			unprunedButton.setText("True");
		} else {
			unprunedButton.setText("False");
		}
		
		// useLaplaceButton
		if (useLaplaceButton.isSelected()) {
			useLaplaceButton.setText("True");
		} else {
			useLaplaceButton.setText("False");
		}
		
		// useMDLcorrectionButton
		if (useMDLcorrectionButton.isSelected()) {
			useMDLcorrectionButton.setText("True");
		} else {
			useMDLcorrectionButton.setText("False");
		}
		
	}
	
	/**
	 * Set the default parameters
	 */
	@FXML public void handleDefault() {
		System.out.println("Default clicked!");
		
		batchSizeField.setText("100");
		
		binarySplitButton.setText("False");
		binarySplitButton.setSelected(false);
		
		collapseTreeButton.setText("True");
		collapseTreeButton.setSelected(true);
		
		confidenceFactorField.setText("0.25");
		
		debugButton.setText("False");
		debugButton.setSelected(false);
		
		doNotCheckCapabilitiesButton.setText("False");
		doNotCheckCapabilitiesButton.setSelected(false);
		
		doNotMakeSplitPAVButton.setText("False");
		doNotMakeSplitPAVButton.setSelected(false);
		
		minNumObjField.setText("2");
		
		numDecimalPlacesField.setText("2");
		
		numFoldsField.setText("3");
		
		reduceErrorPruningButton.setText("False");
		reduceErrorPruningButton.setSelected(false);
		
		saveInstanceDataButton.setText("False");
		saveInstanceDataButton.setSelected(false);
		
		seedField.setText("1");
		
		subTreeRaisingButton.setText("True");
		subTreeRaisingButton.setSelected(true);

		unprunedButton.setText("False");
		unprunedButton.setSelected(false);
		
		useLaplaceButton.setText("False");
		useLaplaceButton.setSelected(false);
		
		useMDLcorrectionButton.setText("True");
		useMDLcorrectionButton.setSelected(true);
		
	}
	
	/**
	 * Save the parameters and close the window
	 */
	@FXML public void handleOk() {
		System.out.println("OK clicked!");
		
		if (checkParameters().equals(ErrorCode.BatchSize)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Batch Size]", "OK");
		} else if (checkParameters().equals(ErrorCode.ConfidenceFactor)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Confidence Factor]", "OK");
		} else if (checkParameters().equals(ErrorCode.MinNumObj)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Min Num Obj]", "OK");
		} else if (checkParameters().equals(ErrorCode.NumDecimalPlaces)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Num Decimal Places]", "OK");
		} else if (checkParameters().equals(ErrorCode.NumFolds)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Num Folds]", "OK");
		} else if (checkParameters().equals(ErrorCode.Seed)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Seed]", "OK");
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
		ClassifierParameters.j48Initialize();
		
		
		// Enable entering Digits only
		batchSizeField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	batchSizeField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		confidenceFactorField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*.")) {
		        	confidenceFactorField.setText(newValue.replaceAll("[^\\d.]", ""));
		        }
		    }
		});
		
		minNumObjField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	minNumObjField.setText(newValue.replaceAll("[^\\d]", ""));
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
		
		numFoldsField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	numFoldsField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		seedField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	seedField.setText(newValue.replaceAll("[^\\d]", ""));
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
		batchSizeField.setText(ClassifierParameters.getJ48batchSize() + "");
		confidenceFactorField.setText(ClassifierParameters.getJ48confidenceFactor() + "");
		minNumObjField.setText(ClassifierParameters.getJ48minNumObj() + "");
		numDecimalPlacesField.setText(ClassifierParameters.getRfNumDecimalPlaces() + "");
		numFoldsField.setText(ClassifierParameters.getJ48numFolds() + "");
		seedField.setText(ClassifierParameters.getRfSeed() + "");
	}
	
	/**
	 * Initialize the different Toggle Buttons in the scene with reference to the class {@link main.items.ClassifierParameters.java}
	 */
	private void initializeToggleButtons() {
		if (ClassifierParameters.isJ48binarySplit()) {
			binarySplitButton.setSelected(true);
			binarySplitButton.setText("True");
		} else {
			binarySplitButton.setSelected(false);
			binarySplitButton.setText("False");
		}
		
		if (ClassifierParameters.isJ48collapseTree()) {
			collapseTreeButton.setSelected(true);
			collapseTreeButton.setText("True");
		} else {
			collapseTreeButton.setSelected(false);
			collapseTreeButton.setText("False");
		}
		
		if (ClassifierParameters.isJ48debug()) {
			debugButton.setSelected(true);
			debugButton.setText("True");
		} else {
			debugButton.setSelected(false);
			debugButton.setText("False");
		}
		
		if (ClassifierParameters.isJ48doNotCheckCapabilities()) {
			doNotCheckCapabilitiesButton.setSelected(true);
			doNotCheckCapabilitiesButton.setText("True");
		} else {
			doNotCheckCapabilitiesButton.setSelected(false);
			doNotCheckCapabilitiesButton.setText("False");
		}
		
		if (ClassifierParameters.isJ48doNotMakeSplitPAV()) {
			doNotMakeSplitPAVButton.setSelected(true);
			doNotMakeSplitPAVButton.setText("True");
		} else {
			doNotMakeSplitPAVButton.setSelected(false);
			doNotMakeSplitPAVButton.setText("False");
		}
		
		if (ClassifierParameters.isJ48reduceErrorPruning()) {
			reduceErrorPruningButton.setSelected(true);
			reduceErrorPruningButton.setText("True");
		} else {
			reduceErrorPruningButton.setSelected(false);
			reduceErrorPruningButton.setText("False");
		}
		
		if (ClassifierParameters.isJ48saveInstanceData()) {
			saveInstanceDataButton.setSelected(true);
			saveInstanceDataButton.setText("True");
		} else {
			saveInstanceDataButton.setSelected(false);
			saveInstanceDataButton.setText("False");
		}
		
		if (ClassifierParameters.isJ48subTreeRaising()) {
			subTreeRaisingButton.setSelected(true);
			subTreeRaisingButton.setText("True");
		} else {
			subTreeRaisingButton.setSelected(false);
			subTreeRaisingButton.setText("False");
		}
		
		if (ClassifierParameters.isJ48unpruned()) {
			unprunedButton.setSelected(true);
			unprunedButton.setText("True");
		} else {
			unprunedButton.setSelected(false);
			unprunedButton.setText("False");
		}
		
		if (ClassifierParameters.isJ48useLaplace()) {
			useLaplaceButton.setSelected(true);
			useLaplaceButton.setText("True");
		} else {
			useLaplaceButton.setSelected(false);
			useLaplaceButton.setText("False");
		}
		
		if (ClassifierParameters.isJ48useMDLcorrection()) {
			useMDLcorrectionButton.setSelected(true);
			useMDLcorrectionButton.setText("True");
		} else {
			useMDLcorrectionButton.setSelected(false);
			useMDLcorrectionButton.setText("False");
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
		
		// Check confidenceFactor
		try {
			Double.parseDouble(confidenceFactorField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.ConfidenceFactor;
		}
		
		// Check minNumObj
		try {
			Integer.parseInt(minNumObjField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.MinNumObj;
		}
		
		// Check numDecimalPlaces
		try {
			Integer.parseInt(numDecimalPlacesField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.NumDecimalPlaces;
		}
		
		// Check numFolds
		try {
			Integer.parseInt(numFoldsField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.NumFolds;
		}
		
		// Check seed
		try {
			Integer.parseInt(seedField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.Seed;
		}
		
		return ErrorCode.Fine;
		
	}

	/**
	 * Save the different parameters as specified by the user in the class {@link main.items.ClassifierParameters.java}
	 */
	private void saveParameters() {
		
		int batchSize = 100;
		int minNumObj = 2;
		int numDecimalPlaces = 2;
		int numFolds = 3;
		int seed = 1;
		
		double confidenceFactor = 0.25;
		
		boolean binarySplit = binarySplitButton.isSelected();
		boolean collapseTree = collapseTreeButton.isSelected();
		boolean debug = debugButton.isSelected();
		boolean doNotCheckCapabilities = doNotCheckCapabilitiesButton.isSelected();
		boolean doNotMakeSplitPAV = doNotMakeSplitPAVButton.isSelected();
		boolean reduceErrorPruning = reduceErrorPruningButton.isSelected();
		boolean saveInstanceData = saveInstanceDataButton.isSelected();
		boolean subtreeRaising = subTreeRaisingButton.isSelected();
		boolean unpruned = unprunedButton.isSelected();
		boolean useLaplace = useLaplaceButton.isSelected();
		boolean useMDLcorrection = useMDLcorrectionButton.isSelected();
		
		// batchSize
		try {
			batchSize = Integer.parseInt(batchSizeField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// minNumObj
		try {
			minNumObj = Integer.parseInt(minNumObjField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// numDecimalPlaces
		try {
			numDecimalPlaces = Integer.parseInt(numDecimalPlacesField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// numFolds
		try {
			numFolds = Integer.parseInt(numFoldsField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// seed
		try {
			seed = Integer.parseInt(seedField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// confidenceFactor
		try {
			confidenceFactor = Double.parseDouble(confidenceFactorField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		ClassifierParameters.j48SetParameters(batchSize, minNumObj, numDecimalPlaces, numFolds, seed, confidenceFactor,
				binarySplit, collapseTree, debug, doNotCheckCapabilities, doNotMakeSplitPAV, reduceErrorPruning, saveInstanceData,
				subtreeRaising, unpruned, useLaplace, useMDLcorrection);
	}

	
}

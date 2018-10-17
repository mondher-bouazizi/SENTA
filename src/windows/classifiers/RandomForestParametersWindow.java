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

public class RandomForestParametersWindow implements Initializable {
	
	// =====================================//
	//          Non-FXML Components         //
	// =====================================//
	
	private static enum ErrorCode {
		BagSizePercent,
		BatchSize,
		MaxDepth,
		NumDecimalPlaces,
		NumExecutionSlots,
		NumFeatures,
		NumIterations,
		Seed,
		Fine
	}
	
	
	// =====================================//
	//            FXML Components           //
	// =====================================//
	
	@FXML TextField bagSizePercentField;
	@FXML TextField batchSizeField;
	@FXML ToggleButton breakTizeRandomlyButton;
	@FXML ToggleButton calcOutOfBagButton;
	@FXML ToggleButton computeAttributeImportanceButton;
	@FXML ToggleButton debugButton;
	@FXML ToggleButton doNotCheckCapabilitiesButton;
	@FXML TextField maxDepthField;
	@FXML TextField numDecimalPlacesField;
	@FXML TextField numExecutionSlotsField;
	@FXML TextField numFeaturesField;
	@FXML TextField numIterationsField;
	@FXML ToggleButton outputComplexityStatsButton;
	@FXML ToggleButton printClassifierButton;
	@FXML TextField seedField;
	@FXML ToggleButton storeOutOfBagButton;
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
		
		// breakTizeRandomlyButton
		if (breakTizeRandomlyButton.isSelected()) {
			breakTizeRandomlyButton.setText("True");
		} else {
			breakTizeRandomlyButton.setText("False");
		}
		
		// calcOutOfBagButton
		if (calcOutOfBagButton.isSelected()) {
			calcOutOfBagButton.setText("True");
		} else {
			calcOutOfBagButton.setText("False");
		}
		
		// computeAttributeImportanceButton
		if (computeAttributeImportanceButton.isSelected()) {
			computeAttributeImportanceButton.setText("True");
		} else {
			computeAttributeImportanceButton.setText("False");
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
		
		// outputComplexityStatsButton
		if (outputComplexityStatsButton.isSelected()) {
			outputComplexityStatsButton.setText("True");
		} else {
			outputComplexityStatsButton.setText("False");
		}
		
		// printClassifierButton
		if (printClassifierButton.isSelected()) {
			printClassifierButton.setText("True");
		} else {
			printClassifierButton.setText("False");
		}
		
		// storeOutOfBagButton
		if (storeOutOfBagButton.isSelected()) {
			storeOutOfBagButton.setText("True");
		} else {
			storeOutOfBagButton.setText("False");
		}
		
	}
	
	/**
	 * Set the default parameters
	 */
	@FXML public void handleDefault() {
		System.out.println("Default clicked!");
		
		bagSizePercentField.setText("100");
		batchSizeField.setText("100");
		
		breakTizeRandomlyButton.setText("False");
		breakTizeRandomlyButton.setSelected(false);
		
		calcOutOfBagButton.setText("False");
		calcOutOfBagButton.setSelected(false);
		
		computeAttributeImportanceButton.setText("False");
		computeAttributeImportanceButton.setSelected(false);
		
		debugButton.setText("False");
		debugButton.setSelected(false);
		
		doNotCheckCapabilitiesButton.setText("False");
		doNotCheckCapabilitiesButton.setSelected(false);
		
		maxDepthField.setText("0");
		numDecimalPlacesField.setText("2");
		numExecutionSlotsField.setText("1");
		numFeaturesField.setText("0");
		numIterationsField.setText("100");
		
		
		outputComplexityStatsButton.setText("False");
		outputComplexityStatsButton.setSelected(false);
		
		printClassifierButton.setText("False");
		printClassifierButton.setSelected(false);
		
		seedField.setText("1");
		
		storeOutOfBagButton.setText("False");
		storeOutOfBagButton.setSelected(false);
		
		
	}
	
	/**
	 * Save the parameters and close the window
	 */
	@FXML public void handleOk() {
		System.out.println("OK clicked!");
		
		if (checkParameters().equals(ErrorCode.BagSizePercent)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Bag Size Percent]", "OK");
		} else if (checkParameters().equals(ErrorCode.BatchSize)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Batch Size]", "OK");
		} else if (checkParameters().equals(ErrorCode.MaxDepth)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Max Depth]", "OK");
		} else if (checkParameters().equals(ErrorCode.NumDecimalPlaces)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Num Decimal Places]", "OK");
		} else if (checkParameters().equals(ErrorCode.NumExecutionSlots)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Num Execution Slots]", "OK");
		} else if (checkParameters().equals(ErrorCode.NumFeatures)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Num Features]", "OK");
		} else if (checkParameters().equals(ErrorCode.NumIterations)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Num Iterations]", "OK");
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
		ClassifierParameters.randomForestInitialize();
		
		
		// Enable entering Digits only
		bagSizePercentField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	bagSizePercentField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		batchSizeField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	batchSizeField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		maxDepthField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	maxDepthField.setText(newValue.replaceAll("[^\\d]", ""));
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
		
		numExecutionSlotsField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	numExecutionSlotsField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		numFeaturesField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	numFeaturesField.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});
		
		numIterationsField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	numIterationsField.setText(newValue.replaceAll("[^\\d]", ""));
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
		bagSizePercentField.setText(ClassifierParameters.getRfBagSizePercent() + "");
		batchSizeField.setText(ClassifierParameters.getRfBatchSize() + "");
		maxDepthField.setText(ClassifierParameters.getRfMaxDepth() + "");
		numDecimalPlacesField.setText(ClassifierParameters.getRfNumDecimalPlaces() + "");
		numExecutionSlotsField.setText(ClassifierParameters.getRfNumExecutionSlots() + "");
		numFeaturesField.setText(ClassifierParameters.getRfNumFeatures() + "");
		numIterationsField.setText(ClassifierParameters.getRfNumIterations() + "");
		seedField.setText(ClassifierParameters.getRfSeed() + "");
	}
	
	/**
	 * Initialize the different Toggle Buttons in the scene with reference to the class {@link main.items.ClassifierParameters.java}
	 */
	private void initializeToggleButtons() {
		if(ClassifierParameters.isRfBreakTiesRandomly()) {
			breakTizeRandomlyButton.setSelected(true);
			breakTizeRandomlyButton.setText("True");
		} else {
			breakTizeRandomlyButton.setSelected(false);
			breakTizeRandomlyButton.setText("False");
		}
		
		if(ClassifierParameters.isRfCalcOutOfBag()) {
			calcOutOfBagButton.setSelected(true);
			calcOutOfBagButton.setText("True");
		} else {
			calcOutOfBagButton.setSelected(false);
			calcOutOfBagButton.setText("False");
		}
		
		if(ClassifierParameters.isRfComputeAttributeImportance()) {
			computeAttributeImportanceButton.setSelected(true);
			computeAttributeImportanceButton.setText("True");
		} else {
			computeAttributeImportanceButton.setSelected(false);
			computeAttributeImportanceButton.setText("False");
		}
		
		if(ClassifierParameters.isRfDebug()) {
			debugButton.setSelected(true);
			debugButton.setText("True");
		} else {
			debugButton.setSelected(false);
			debugButton.setText("False");
		}
		
		if(ClassifierParameters.isRfDoNotCheckCapabilities()) {
			doNotCheckCapabilitiesButton.setSelected(true);
			doNotCheckCapabilitiesButton.setText("True");
		} else {
			doNotCheckCapabilitiesButton.setSelected(false);
			doNotCheckCapabilitiesButton.setText("False");
		}
		
		if(ClassifierParameters.isRfOutOfBagComplexityStatistics()) {
			outputComplexityStatsButton.setSelected(true);
			outputComplexityStatsButton.setText("True");
		} else {
			outputComplexityStatsButton.setSelected(false);
			outputComplexityStatsButton.setText("False");
		}
		
		if(ClassifierParameters.isRfPrintClassifiers()) {
			printClassifierButton.setSelected(true);
			printClassifierButton.setText("True");
		} else {
			printClassifierButton.setSelected(false);
			printClassifierButton.setText("False");
		}
		
		if(ClassifierParameters.isRfStoreOutOfBagPredictions()) {
			storeOutOfBagButton.setSelected(true);
			storeOutOfBagButton.setText("True");
		} else {
			storeOutOfBagButton.setSelected(false);
			storeOutOfBagButton.setText("False");
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
		
		// Check Bag Size Percent
		try {
			Integer.parseInt(bagSizePercentField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.BagSizePercent;
		}
		
		// Check Batch Size
		try {
			Integer.parseInt(batchSizeField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.BatchSize;
		}
		
		// Check maxDepth
		try {
			Integer.parseInt(maxDepthField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.MaxDepth;
		}
		
		// Check numDecimalPlaces
		try {
			Integer.parseInt(numDecimalPlacesField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.NumDecimalPlaces;
		}
		
		// Check numExecutionSlots
		try {
			Integer.parseInt(numExecutionSlotsField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.NumExecutionSlots;
		}
		
		// Check numFeatures
		try {
			Integer.parseInt(numFeaturesField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.NumFeatures;
		}
		
		// Check numIterations
		try {
			Integer.parseInt(numIterationsField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.NumIterations;
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
		
		int bagSizePercent = 100;
		int batchSize = 100;
		int maxDepth = 0;
		int numDecimalPlaces = 2;
		int numExecutionSlots = 1;
		int numFeatures = 0;
		int numIterations = 100;
		int seed = 1;
		
		boolean breakTizeRandomly = breakTizeRandomlyButton.isSelected();
		boolean calcOutOfBag = calcOutOfBagButton.isSelected();
		boolean computeAttributeImportance = computeAttributeImportanceButton.isSelected();
		boolean debug = debugButton.isSelected();
		boolean doNotCheckCapabilities = doNotCheckCapabilitiesButton.isSelected();
		boolean outputComplexityStats = outputComplexityStatsButton.isSelected();
		boolean printClassifier = printClassifierButton.isSelected();
		boolean storeOutOfBag = storeOutOfBagButton.isSelected();
		
		
		// bagSizePercent
		try {
			bagSizePercent = Integer.parseInt(bagSizePercentField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// batchSize
		try {
			batchSize = Integer.parseInt(batchSizeField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// maxDepth
		try {
			maxDepth = Integer.parseInt(maxDepthField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// numDecimalPlaces
		try {
			numDecimalPlaces = Integer.parseInt(numDecimalPlacesField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// numExecutionSlots
		try {
			numExecutionSlots = Integer.parseInt(numExecutionSlotsField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// numFeatures
		try {
			numFeatures = Integer.parseInt(numFeaturesField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}

		// numIterations
		try {
			numIterations = Integer.parseInt(numIterationsField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// seed
		try {
			seed = Integer.parseInt(seedField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		ClassifierParameters.randomForestSetParameters(bagSizePercent, batchSize, maxDepth, numDecimalPlaces, numExecutionSlots, numFeatures, numIterations, seed,
				breakTizeRandomly, calcOutOfBag, computeAttributeImportance, debug, doNotCheckCapabilities, outputComplexityStats,
				printClassifier, storeOutOfBag);
	}

	
}

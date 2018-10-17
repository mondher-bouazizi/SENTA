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

public class HoeffdingTreeParametersWindow implements Initializable {
	
	// =====================================//
	//          Non-FXML Components         //
	// =====================================//
	
	private static enum ErrorCode {
		BatchSize,
		GracePeriod,
		HoeffdingTieThreshold,
		MinGain,
		NBThreshold,
		NumDecimalPlaces,
		SplitConfidence,
		LeafPredStrategy,
		SplitCriterion,
		Fine
	}
	
	
	// =====================================//
	//            FXML Components           //
	// =====================================//
	
	@FXML TextField batchSizeField;
	
	@FXML ToggleButton debugButton;
	@FXML ToggleButton doNotCheckCapabilitiesButton;
	
	@FXML TextField gracePeriodField;
	@FXML TextField hoeffdingTieThresholdField;
	
	@FXML ComboBox<String> leafPredictionStrategyCombo;
	
	@FXML TextField minimumFractionOfWeightInfoGainField;
	@FXML TextField naiveBayesPredictionThresholdField;
	@FXML TextField numDecimalPlacesField;

	@FXML ToggleButton printLeafModelButton;
	
	@FXML TextField splitConfidenceField;
	
	@FXML ComboBox<String> splitCriterionCombo;
	
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
		
		// printLeafModelButton
		if (printLeafModelButton.isSelected()) {
			printLeafModelButton.setText("True");
		} else {
			printLeafModelButton.setText("False");
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
		
		gracePeriodField.setText("200.0");
		hoeffdingTieThresholdField.setText("0.05");
		
		leafPredictionStrategyCombo.setValue("Naive Bayes adaptive");
		
		minimumFractionOfWeightInfoGainField.setText("0.01");
		naiveBayesPredictionThresholdField.setText("0.0");
		numDecimalPlacesField.setAccessibleHelp("2");
		
		printLeafModelButton.setText("False");
		printLeafModelButton.setSelected(false);
		
		splitConfidenceField.setText("1.0E-7");
		
		splitCriterionCombo.setValue("Info gain split");
		
	}
	
	/**
	 * Save the parameters and close the window
	 */
	@FXML public void handleOk() {
		System.out.println("OK clicked!");
				
		if (checkParameters().equals(ErrorCode.BatchSize)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Batch Size]", "OK");
		} else if (checkParameters().equals(ErrorCode.GracePeriod)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Grace Period]", "OK");
		} else if (checkParameters().equals(ErrorCode.HoeffdingTieThreshold)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Hoeffding Tie Threshold]", "OK");
		} else if (checkParameters().equals(ErrorCode.MinGain)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Min Fraction of Weight Info Gain]", "OK");
		} else if (checkParameters().equals(ErrorCode.NBThreshold)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Naive Bayes Threshold]", "OK");
		} else if (checkParameters().equals(ErrorCode.NumDecimalPlaces)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Num Decimal Places]", "OK");
		} else if (checkParameters().equals(ErrorCode.SplitConfidence)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Split Confidence]", "OK");
		} else if (checkParameters().equals(ErrorCode.LeafPredStrategy)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Leaf Prediction Strategy]", "OK");
		} else if (checkParameters().equals(ErrorCode.SplitCriterion)) {
			AlertBox.display("Error", "Please make sure to set valid values for the  parameters [Split Criterion]", "OK");
		}
		else if (checkParameters().equals(ErrorCode.Fine)) {
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
		ClassifierParameters.heoffdingTreeInitialize();
		
		// Create the lists for the comboboxes
		if (leafPredictionStrategyCombo.getItems().isEmpty()) {
			leafPredictionStrategyCombo.getItems().addAll(
					"Majority class",
					"Naive Bayes",
					"Naive Bayes adaptive"
					);
		}
				
		leafPredictionStrategyCombo.setValue("Naive Bayes adaptive");
		
		if (splitCriterionCombo.getItems().isEmpty()) {
			splitCriterionCombo.getItems().addAll(
					"Gini split",
					"Info gain split"
					);
		}
		splitCriterionCombo.setValue("Info gain split");
		
		
		// Enable entering Digits only
		batchSizeField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*.E-")) {
		        	batchSizeField.setText(newValue.replaceAll("[^\\d.E-]", ""));
		        }
		    }
		});
		
		gracePeriodField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*.E-")) {
		        	gracePeriodField.setText(newValue.replaceAll("[^\\d.E-]", ""));
		        }
		    }
		});
		
		gracePeriodField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*.E-")) {
		        	gracePeriodField.setText(newValue.replaceAll("[^\\d.E-]", ""));
		        }
		    }
		});
		
		hoeffdingTieThresholdField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*.E-")) {
		        	hoeffdingTieThresholdField.setText(newValue.replaceAll("[^\\d.E-]", ""));
		        }
		    }
		});
		
		minimumFractionOfWeightInfoGainField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*.E-")) {
		        	minimumFractionOfWeightInfoGainField.setText(newValue.replaceAll("[^\\d.E-]", ""));
		        }
		    }
		});
		
		naiveBayesPredictionThresholdField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*.E-")) {
		        	naiveBayesPredictionThresholdField.setText(newValue.replaceAll("[^\\d.E-]", ""));
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
		
		splitConfidenceField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (!newValue.matches("\\d*.E-")) {
		        	splitConfidenceField.setText(newValue.replaceAll("[^\\d.E-]", ""));
		        }
		    }
		});
		
		// Initialize the Text Field values
		initializeTextFields();
		
		// Initialize the Toggle Button values
		initializeToggleButtons();
		
		// Initialize the Combo boxes
		initializeComboBoxes();
		
	}
	
	/**
	 * Initialize the different text fields in the scene with reference to the class {@link main.items.ClassifierParameters.java}
	 */
	private void initializeTextFields() {
		batchSizeField.setText(ClassifierParameters.getHtbatchSize() + "");
		gracePeriodField.setText(ClassifierParameters.getHtgracePeriod() + "");
		hoeffdingTieThresholdField.setText(ClassifierParameters.getHthoeffdingTieThreshold() + "");
		minimumFractionOfWeightInfoGainField.setText(ClassifierParameters.getHtminimumFractionOfWeightInfoGain() + "");
		naiveBayesPredictionThresholdField.setText(ClassifierParameters.getHtnaiveBayesPredictionThreshold() + "");
		numDecimalPlacesField.setText(ClassifierParameters.getHtnumDecimalPlaces() + "");
		splitConfidenceField.setText(ClassifierParameters.getHtsplitConfidence() + "");
	}
	
	/**
	 * Initialize the different Toggle Buttons in the scene with reference to the class {@link main.items.ClassifierParameters.java}
	 */
	private void initializeToggleButtons() {
		
		if(ClassifierParameters.isHtdebug()) {
			debugButton.setSelected(true);
			debugButton.setText("True");
		} else {
			debugButton.setSelected(false);
			debugButton.setText("False");
		}
				
		if(ClassifierParameters.isHtdoNotCheckCapabilities()) {
			doNotCheckCapabilitiesButton.setSelected(true);
			doNotCheckCapabilitiesButton.setText("True");
		} else {
			doNotCheckCapabilitiesButton.setSelected(false);
			doNotCheckCapabilitiesButton.setText("False");
		}
		
		if(ClassifierParameters.isHtprintLeafModels()) {
			printLeafModelButton.setSelected(true);
			printLeafModelButton.setText("True");
		} else {
			printLeafModelButton.setSelected(false);
			printLeafModelButton.setText("False");
		}

	}
	
	/**
	 * Initialize the different Combo boxes in the scene with reference to the class {@link main.items.ClassifierParameters.java}
	 */
	private void initializeComboBoxes() {
		
		if(ClassifierParameters.getHtleafPredictionStrategy() != null) {
			if (ClassifierParameters.getHtleafPredictionStrategy().equals(ClassifierParameters.HoeffdingTreeLPS.Naive_Bayes_Adaptive)) {
				leafPredictionStrategyCombo.setValue("Naive Bayes adaptive");
			} else if (ClassifierParameters.getHtleafPredictionStrategy().equals(ClassifierParameters.HoeffdingTreeLPS.Naive_Bayes)) {
				leafPredictionStrategyCombo.setValue("Naive Bayes");
			} else if (ClassifierParameters.getHtleafPredictionStrategy().equals(ClassifierParameters.HoeffdingTreeLPS.Majority_Class)) {
				leafPredictionStrategyCombo.setValue("Majority class");
			}
			
			
		}
		if(ClassifierParameters.isHtdoNotCheckCapabilities()) {
			doNotCheckCapabilitiesButton.setSelected(true);
			doNotCheckCapabilitiesButton.setText("True");
		}
	}
	
	
	// =====================================//
	//             Other Methods            //
	// =====================================//
	
	/**
	 * Checks if any of the Text Fields presents an error (empty or containing non allowed characters, as well as the content of the Combo Boxes
	 * @return the error code corresponding to which Text Field has an error (first one checked first)
	 */
	private ErrorCode checkParameters() {
		
		// Check Batch Size
		try {
			Integer.parseInt(batchSizeField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.BatchSize;
		}
		
		// Check Grace Period
		try {
			Double.parseDouble(gracePeriodField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.GracePeriod;
		}
		
		// Check Hoeffding Tie Threshold
		try {
			Double.parseDouble(hoeffdingTieThresholdField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.HoeffdingTieThreshold;
		}
		
		// Check Min Fraction Of Weight Info Gain
		try {
			Double.parseDouble(minimumFractionOfWeightInfoGainField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.MinGain;
		}
		
		// Check Naive Bayes Prediction Threshold
		try {
			Double.parseDouble(naiveBayesPredictionThresholdField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.NBThreshold;
		}
		
		// Check numDecimalPlaces
		try {
			Integer.parseInt(numDecimalPlacesField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.NumDecimalPlaces;
		}
		
		// Check Split Confidence
		try {
			Double.parseDouble(splitConfidenceField.getText());
			
		} catch (NumberFormatException e) {
			return ErrorCode.SplitConfidence;
		}
		
		// Check Leaf Prediction Strategy
		ArrayList<String> strategyList = new ArrayList<>(Arrays.asList("Majority class", "Naive Bayes", "Naive Bayes adaptive"));
		
		if (!strategyList.contains(leafPredictionStrategyCombo.getValue())) {
			return ErrorCode.LeafPredStrategy;
		}
		
		// Check Split Criterion
		ArrayList<String> criteriaList = new ArrayList<>(Arrays.asList("Gini split", "Info gain split"));
		
		if (!criteriaList.contains(splitCriterionCombo.getValue())) {
			return ErrorCode.SplitCriterion;
		}
		
		// Return "Everything is fine"
		return ErrorCode.Fine;
		
	}

	/**
	 * Save the different parameters as specified by the user in the class {@link main.items.ClassifierParameters.java}
	 */
	private void saveParameters() {
		
		// Initialization just in case there has been an error that was not taken into account
		int batchSize = 100;
		
		boolean debug = debugButton.isSelected();
		boolean doNotCheckCapabilities = doNotCheckCapabilitiesButton.isSelected();
		
		double gracePeriod = 200.0;
		double hoeffdingTieThreshold = 0.05;
		
		ClassifierParameters.HoeffdingTreeLPS leafPredictionStrategy = ClassifierParameters.HoeffdingTreeLPS.Naive_Bayes_Adaptive;
		
		double minimumFractionOfWeightInfoGain = 0.01;
		double naiveBayesPredictionThreshold = 0.0;
		int numDecimalPlaces = 2;
		
		boolean printLeafModels = printLeafModelButton.isSelected();
		double splitConfidence = 1.0E-7;
		
		ClassifierParameters.HoeffdingTreeSplitCriterion splitCriterion = ClassifierParameters.HoeffdingTreeSplitCriterion.Info_Gain_Split;
		
		
		// batchSize
		try {
			batchSize = Integer.parseInt(batchSizeField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// gracePeriod
		try {
			gracePeriod = Double.parseDouble(batchSizeField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// hoeffdingTieThreshold
		try {
			hoeffdingTieThreshold = Double.parseDouble(hoeffdingTieThresholdField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}

		// minimumFractionOfWeightInfoGain
		try {
			minimumFractionOfWeightInfoGain = Double.parseDouble(minimumFractionOfWeightInfoGainField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// naiveBayesPredictionThreshold
		try {
			naiveBayesPredictionThreshold = Double.parseDouble(naiveBayesPredictionThresholdField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		// numDecimalPlaces
		try {
			numDecimalPlaces = Integer.parseInt(numDecimalPlacesField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		
		// splitConfidence
		try {
			splitConfidence = Double.parseDouble(splitConfidenceField.getText());
			
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}

		// leafPredictionStrategy
		if (leafPredictionStrategyCombo.getValue() != null) {
			if (leafPredictionStrategyCombo.getValue().equals("Majority class")) {
				leafPredictionStrategy = ClassifierParameters.HoeffdingTreeLPS.Majority_Class;
			} else if (leafPredictionStrategyCombo.getValue().equals("Naive Bayes")) {
				leafPredictionStrategy = ClassifierParameters.HoeffdingTreeLPS.Naive_Bayes;
			} else if (leafPredictionStrategyCombo.getValue().equals("Naive Bayes adaptive")) {
				leafPredictionStrategy = ClassifierParameters.HoeffdingTreeLPS.Naive_Bayes_Adaptive;
			}
		}
		
		// splitCriterion
		
		if (splitCriterionCombo.getValue() != null) {
			if (splitCriterionCombo.getValue().equals("Gini split")) {
				splitCriterion = ClassifierParameters.HoeffdingTreeSplitCriterion.Gini_Split;
			} else if (splitCriterionCombo.getValue().equals("Info gain split")) {
				splitCriterion = ClassifierParameters.HoeffdingTreeSplitCriterion.Info_Gain_Split;
			}
		}
		
		// Set Parameters
		ClassifierParameters.heoffdingTreeSetParameters(batchSize, debug, doNotCheckCapabilities, gracePeriod, hoeffdingTieThreshold, leafPredictionStrategy, minimumFractionOfWeightInfoGain, naiveBayesPredictionThreshold, numDecimalPlaces, printLeafModels, splitConfidence, splitCriterion);
	}

	
}

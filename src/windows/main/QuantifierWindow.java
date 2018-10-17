package windows.main;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import commons.constants.Commons;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import main.items.Features;
import main.start.Main;
import main.start.QuantificationProblem;
import windows.others.AlertBox;
import windows.others.ConfirmBox;

public class QuantifierWindow implements Initializable {
	
	
	// =====================================//
	//          Non-FXML Components         //
	// =====================================//
	protected static Thread background;
	
//	private static int countTasks;
	
	private static enum ErrorCode {
		FINE,
		NO_FEATURE_SELECTED,
		NUMBER_FORMAT,
		SUM,
		THRESHOLD_FORMAT,
		THRESHOLD_VALUE
	}
	
	
	// =====================================//
	//            FXML Components           //
	// =====================================//
	
	@FXML CheckBox unigramsCheckBox;
	@FXML CheckBox basicPatternsCheckBox;
	@FXML CheckBox advancedPatternsCheckBox;
	
	@FXML TextField unigramCoef;
	@FXML TextField basicPatternsCoef;
	@FXML TextField advancedPatternsCoef;
	
	@FXML Label quantifThresholdLabel;
	@FXML TextField quantifThreshold;
	
	@FXML ToggleButton automaticChoices;
	@FXML Button startButton;
	
	@FXML CheckBox outputPrediction;
	@FXML TextArea output;
	
//	@FXML ProgressBar progressBar;
//	@FXML Label currentTask;
//	@FXML Label numberOfTasks;
	
	@FXML Button backButton;
	@FXML Button nextButton;
	@FXML Button cancelButton;
	
	
	// =====================================//
	//        FXML Components Actions       //
	// =====================================//
	
	@FXML public void handleToggleButtons() {
		System.out.println("Automatic or not?");
		// debugButton
		if (automaticChoices.isSelected()) {
			automaticChoices.setText("On");
			
			unigramsCheckBox.setDisable(true);
			basicPatternsCheckBox.setDisable(true);
			advancedPatternsCheckBox.setDisable(true);
			
			unigramCoef.setDisable(true);
			basicPatternsCoef.setDisable(true);
			advancedPatternsCoef.setDisable(true);
			
			quantifThresholdLabel.setDisable(true);
			quantifThreshold.setDisable(true);
		} else {
			automaticChoices.setText("Off");
			
			unigramsCheckBox.setDisable(false);
			basicPatternsCheckBox.setDisable(false);
			advancedPatternsCheckBox.setDisable(false);
			
			unigramCoef.setDisable(false);
			basicPatternsCoef.setDisable(false);
			advancedPatternsCoef.setDisable(false);
			
			quantifThresholdLabel.setDisable(false);
			quantifThreshold.setDisable(false);
		}
	}
	
	@FXML public void handleStartButton() {
		System.out.println("Start Button clicked");
		
		ErrorCode e = checkParameters();
		
		if (e.equals(ErrorCode.FINE)) {
			saveParameters();
			startAction();
		} else {
			if (e.equals(ErrorCode.NO_FEATURE_SELECTED)) {
				AlertBox.display("No feature set selected", "You need to choose at least one set of features before proceeding", "OK");
			} else if (e.equals(ErrorCode.NUMBER_FORMAT) || e.equals(ErrorCode.SUM)) {
				AlertBox.display("Invalid coefficients", "The coefficients for the selected features should be between 0 and 1 and sum up to 1", "OK");
			} else if (e.equals(ErrorCode.THRESHOLD_VALUE) || e.equals(ErrorCode.THRESHOLD_FORMAT)) {
				AlertBox.display("Invalid threshold", "You need to choose a threshold between 0 and 1", "OK");
			}
		}
	}
	
	@FXML public void handleBackButton() {
		System.out.println("Back Button clicked");
		try {
			Main.root = FXMLLoader.load(getClass().getResource("/windows/main/ClassifiersWindow.fxml"));
			Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
			Main.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void handleNextButton() {
		System.out.println("Next Button clicked");

	}
	
	@FXML public void handleCancelButton() {
		System.out.println("Cancel Button clicked");
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
		
		automaticChoices.setText("On");
		automaticChoices.setSelected(true);
		
		unigramsCheckBox.setDisable(true);
		basicPatternsCheckBox.setDisable(true);
		advancedPatternsCheckBox.setDisable(true);
		
		unigramCoef.setDisable(true);
		basicPatternsCoef.setDisable(true);
		advancedPatternsCoef.setDisable(true);
		
		quantifThresholdLabel.setDisable(true);
		quantifThreshold.setDisable(true);
		
		nextButton.setDisable(true);
		cancelButton.setText("Exit");

		output.setEditable(false);
		//output.setMouseTransparent(true);
		//output.setFocusTraversable(false);
				
		QuantificationProblem.getTextProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue!=null) {
				if(!newValue.equals(oldValue)) {
					javafx.application.Platform.runLater( () -> {
						int start = 0;
						int end = 0;
						if (output.getSelectedText()!=null) {
							start = output.getSelection().getStart();
							end = output.getSelection().getEnd();
						}
						output.setText(QuantificationProblem.getTextProperty().get());
						if (output.getText()!=null) {
							if (output.getText().length()>end && start>0 && end>start) {
								output.selectRange(start, end);
							} else {
								output.requestFocus();
								output.end();
							}
						}
					});
				}
			}
			
		});
		
		initializeProgress();

	}
	
	private void initializeProgress() {
		
//		// Count of tasks
//		QuantificationProblem.getTotalNumberOfTasks().addListener((observable, oldValue, newValue) -> {
//			if(!newValue.equals(oldValue)) {
//				Commons.print("HELLO!!! I CHANGED!!!");
//				countTasks = Math.min(newValue.intValue(), 1);
//				numberOfTasks.setText(countTasks + "");
//			}
//		});
//		
//		// Current task
//		QuantificationProblem.getCurrentTask().addListener((observable, oldValue, newValue) -> {
//			if(!newValue.equals(oldValue)) {
//				Commons.print("HELLO!!! I CHANGED AS WELL!!!");
//				currentTask.setText(newValue.intValue() + "");
//				double progress = (double) newValue.intValue() / (double) countTasks;
//				progressBar.progressProperty().set(Math.min(progress, 1.0));
//			}
//		});
		
	}
	
	
	private static void startAction() {

		Runnable task = new Runnable() {
			public void run() {
				QuantificationProblem.run();
			}
		};

		background = new Thread(task);

		background.setDaemon(true);

		background.start();

	}
	
	
	// =====================================//
	//            OTHER METHODS             //
	// =====================================//
	/**
	 * Checks if any of the Text Fields presents an error (empty or containing non allowed characters
	 * @return the error code corresponding to which Text Field has an error (first one checked first)
	 */
	private ErrorCode checkParameters() {
		
		if (!automaticChoices.isSelected()) {
			// Check that at least one set of features is selected
			if (!unigramsCheckBox.isSelected() && !basicPatternsCheckBox.isSelected() && !advancedPatternsCheckBox.isSelected()) {
				return ErrorCode.NO_FEATURE_SELECTED;
			}
			// Check Numbers
			try {
				// TODO use java.math.BigDecimal instead to solve the double summation issue!
				double s1 = 0, s2 = 0, s3 = 0;

				if (unigramsCheckBox.isSelected()) {
					s1 = Double.parseDouble(unigramCoef.getText());
				}

				if (basicPatternsCheckBox.isSelected()) {
					s2 = Double.parseDouble(basicPatternsCoef.getText());
				}

				if (advancedPatternsCheckBox.isSelected()) {
					s3 = Double.parseDouble(advancedPatternsCoef.getText());
				}

				if (s1 > 1 || s1 < 0 || s2 > 1 || s2 < 0 || s3 > 1 || s3 < 0) {
					Commons.print("Format error!");
					return ErrorCode.NUMBER_FORMAT;
				} else if ((s1 + s2 + s3) > 1.0001 || (s1 + s2 + s3) < 0.9999) { // This is because double values might not sum up to 1 (weird double behavior)
					Commons.print("Sum error!");
					System.out.println(Arrays.asList(s1, s2, s3));
					return ErrorCode.SUM;
				}

			} catch (NumberFormatException e) {
				return ErrorCode.NUMBER_FORMAT;
			}
			
			try {

				double th = Double.parseDouble(quantifThreshold.getText());
				
				if ((th > 1) || (th <0)) {
					return ErrorCode.THRESHOLD_VALUE;
				}

			} catch (NumberFormatException e) {
				return ErrorCode.THRESHOLD_FORMAT;
			}
		}
		// Return "Everything is fine"
		return ErrorCode.FINE;
		
	}

	/**
	 * Save the different features as specified by the user {@link main.items.Features}
	 */
	private void saveParameters() {
		
		// Sets of features used
		Features.setUseQuantifUnigrams(unigramsCheckBox.isSelected());
		Features.setUseQuantifBasicPatterns(basicPatternsCheckBox.isSelected());
		Features.setUseQuantifAdvancedPatterns(advancedPatternsCheckBox.isSelected());
		
		// Coefficients
		int length = 0;
		
		if (unigramsCheckBox.isSelected()) length ++;
		if (basicPatternsCheckBox.isSelected()) length ++;
		if (advancedPatternsCheckBox.isSelected()) length ++;
		
		double[][] coefficients = new double[1][length];

		try {
			
			int i = 0;
			if (unigramsCheckBox.isSelected()) {
				coefficients[0][i] = Double.parseDouble(unigramCoef.getText());
				i++;
			}
			
			if (basicPatternsCheckBox.isSelected()) {
				coefficients[0][i] = Double.parseDouble(basicPatternsCoef.getText());
				i++;
			}
			
			if (advancedPatternsCheckBox.isSelected()) {
				coefficients[0][i] = Double.parseDouble(advancedPatternsCoef.getText());
			}
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
		}
		
		Features.setManualCoefficients(coefficients);
		Features.setUseManualParameters(!automaticChoices.isSelected());

		// Threshold
		try {
			Features.setSentimentsThreshold(Double.parseDouble(quantifThreshold.getText()));
		} catch (NumberFormatException e) {
			System.out.println("Text Field is empty");
			Features.setSentimentsThreshold(0);
		}
		
		// Output predictions
		Features.setOutputPredictions(outputPrediction.isSelected());
		
	}
	
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

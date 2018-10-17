package windows.features;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import main.items.Features;
import main.items.Parameters;
import windows.main.SelectAdvancedFeaturesWindow;
import windows.others.AlertBox;

public class AdvancedSentimentFeaturesWindow implements Initializable {

	// =====================================//
	//           FXML COMPONENTS            //
	// =====================================//
	
	@FXML Button defaultFeatures;
	@FXML Button allFeatures;
	@FXML Button clearFeatures;
	
	@FXML Button select;
	@FXML Button cancel;
	
	@FXML RadioButton sentimentPolarityRadio;
	@FXML RadioButton sentimentScoreRadio;
	
	@FXML TextField wordsBefore;
	@FXML TextField wordsAfter;
	
	@FXML RadioButton addSentimentInformation;
	@FXML RadioButton notAddSentimentInformation;
	
	
	// =====================================//
	//        FXML COMPONENTS ACTIONS       //
	// =====================================//
	
	/**
	 * Select the default features
	 */
	@FXML public void handleDefault() {
		System.out.println("Default clicked!");
		
		sentimentPolarityRadio.setSelected(true);
		sentimentScoreRadio.setSelected(false);
		
		wordsBefore.setText("3");
		wordsAfter.setText("3");
		
		addSentimentInformation.setSelected(false);
		notAddSentimentInformation.setSelected(true);
	}
	
	/**
	 * Select all the features
	 */
	@FXML public void handleAll() {
		System.out.println("Default clicked!");
		
		sentimentPolarityRadio.setSelected(false);
		sentimentScoreRadio.setSelected(true);
		
		wordsBefore.setText("3");
		wordsAfter.setText("3");
		
		addSentimentInformation.setSelected(true);
		notAddSentimentInformation.setSelected(false);

	}
	
	/**
	 * Clear all the features (parameters)
	 */
	@FXML public void handleClear() {
		System.out.println("Default clicked!");
		
		
		sentimentPolarityRadio.setSelected(false);
		sentimentScoreRadio.setSelected(false);
		
		wordsBefore.setText("");
		wordsAfter.setText("");
		
		addSentimentInformation.setSelected(false);
		notAddSentimentInformation.setSelected(false);

	}
	
	/**
	 * Handle the [Select] button
	 */
	@FXML public void handleSelect() {
		System.out.println("Select clicked!");
		
		int count = 0;
		
		if (checkParameters().equals("type")) {
			AlertBox.display("Error", "Please choose the type of features (Polarity vs Score) you want to use", "OK");
		} else if (checkParameters().equals("numberEmpty")) {
			AlertBox.display("Error", "Please choose the number of words before and after the subject", "OK");
		} else if (checkParameters().equals("numberFormat")) {
			AlertBox.display("Error", "Please enter a valid number of words before and after the subject", "OK");
		} else if (checkParameters().equals("sentimentInfo")) {
			AlertBox.display("Error", "Please choose whether or not you want to add the sentiment information", "OK");
		} else {
			
			// Type of the features
			if (sentimentPolarityRadio.isSelected()) {
				Features.setTypeOfAdvancedSentimentFeatures(Features.AdvancedTypeOfFeatures.POLARITY);
			} else if (sentimentScoreRadio.isSelected()) {
				Features.setTypeOfAdvancedSentimentFeatures(Features.AdvancedTypeOfFeatures.SCORE);
			}
			
			// Words before and after
			int numberOfWordsBefore = Integer.parseInt(wordsBefore.getText());
			Features.setNumberOfWordsBefore(numberOfWordsBefore);
			count = count + numberOfWordsBefore;
			
			int numberOfWordsAfter = Integer.parseInt(wordsAfter.getText());
			Features.setNumberOfWordsAfter(numberOfWordsAfter);
			count = count + numberOfWordsAfter;
			
			// Add Sentiment Information
			if (addSentimentInformation.isSelected()) {
				Features.setAddSentimentClassInformation(true);
				int extraFeatures = Parameters.getClasses().size();
				count = count + count*extraFeatures;
				
			} else if (notAddSentimentInformation.isSelected()) {
				Features.setAddSentimentClassInformation(false);
			}
			
			SelectAdvancedFeaturesWindow.setNumberOfSentimentRelatedFeatures(count);
			SelectAdvancedFeaturesWindow.countOfSentimentRelatedFeatures.set(count);
			
			FeaturesCustumization.stage.close();
			
		}

		
	}
	
	/**
	 * Handle the [Cancel] button
 	 */
	@FXML public void handleCancel() {
		System.out.println("Cancel clicked!");
		FeaturesCustumization.stage.close();
	}

	
	//=================================//
	//         INITIALIZATION          //
	//=================================//

	/**
	 * Initializes the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Type of sentiment features
		if (Features.getTypeOfAdvancedSentimentFeatures()!=null) {
			if (Features.getTypeOfAdvancedSentimentFeatures().equals(Features.AdvancedTypeOfFeatures.POLARITY)) {
				sentimentPolarityRadio.setSelected(true);
				sentimentScoreRadio.setSelected(false);
			} else if (Features.getTypeOfAdvancedSentimentFeatures().equals(Features.AdvancedTypeOfFeatures.SCORE)) {
				sentimentPolarityRadio.setSelected(false);
				sentimentScoreRadio.setSelected(true);
			}
		}
		
		// number of surrounding words
		wordsBefore.setText(Features.getNumberOfWordsBefore() + "");
		wordsAfter.setText(Features.getNumberOfWordsAfter() + "");
		
		// Add sentiment information
		if (Features.getTypeOfAdvancedSentimentFeatures()!=null) {
			// TODO the condition added above is just to make sure that the first time the user opens the window, no radio is selected!
			if (Features.isAddSentimentClassInformation()) {
				addSentimentInformation.setSelected(true);
				notAddSentimentInformation.setSelected(false);
			} else {
				addSentimentInformation.setSelected(false);
				notAddSentimentInformation.setSelected(true);
			}
		}
		
	}
	
	
	//=================================//
	//         PRIVATE METHODS         //
	//=================================//
	
	/**
	 * Checks whether the parameters entered are correct
	 * TODO add an enumeration so that the different error codes are in the enum
	 * @return
	 */
	private String checkParameters() {
		
		String errorCode = "fine";
		
		if (!sentimentPolarityRadio.isSelected() && !sentimentScoreRadio.isSelected()) {
			return "type";
		}
		
		if (wordsBefore.getText()==null || wordsAfter.getText()==null || wordsBefore.getText().equals("") || wordsAfter.getText().equals("")) {
			return "numberEmpty";
		}
		
		if (!isInteger(wordsBefore.getText()) || !isInteger(wordsAfter.getText())) {
			return "numberFormat";
		}
		
		if (!notAddSentimentInformation.isSelected() && !addSentimentInformation.isSelected()) {
			return "sentimentInfo";
		}
		
		return errorCode;
	}
	
	/**
	 * Check if a text entered in a TextFiled corresponds to a numbers [int]
	 * TODO change in the {initialize} method so that only numbers can be put
	 * @return
	 */
	private boolean isInteger(String text) {
		try {
			Integer.parseInt(text);
		} catch (NumberFormatException e1) {
			return false;
		}
		
		return true;
	}
}

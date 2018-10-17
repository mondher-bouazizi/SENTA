package windows.classifiers.selectfeatures;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import main.items.ClassifierFeatures;
import main.items.Features;
import windows.others.AlertBox;

public class FeatureSelectorWindow implements Initializable {
	
	// =====================================//
	//          Non-FXML Components         //
	// =====================================//
	
	private static enum ErrorCode {
		NoFeatureSelected,
		NonExtractedSelected,
		Fine
	}
	
	
	// =====================================//
	//            FXML Components           //
	// =====================================//
	
	
	@FXML CheckBox sentimentFeatures;
	@FXML CheckBox punctuationFeatures;
	@FXML CheckBox syntacticFeatures;
	@FXML CheckBox semanticFeatures;
	@FXML CheckBox unigramFeatures;
	@FXML CheckBox topWordsFeatures;
	@FXML CheckBox patternFeatures;
	@FXML CheckBox advancedSentimentFeatures;
	@FXML CheckBox advancedSemanticFeatures;
	@FXML CheckBox advancedPatternFeatures;
	@FXML CheckBox advancedUnigramFeatures;
	
	@FXML Button all;
	@FXML Button ok;
	@FXML Button cancel;
	
	
	// =====================================//
	//        FXML Components Actions       //
	// =====================================//
	
	/**
	 * Set all the features that have been extracted to be selected
	 */
	@FXML public void handleAll() {
		System.out.println("All clicked!");
		
		sentimentFeatures.setSelected(!sentimentFeatures.isDisable());
		punctuationFeatures.setSelected(!punctuationFeatures.isDisable());
		syntacticFeatures.setSelected(!syntacticFeatures.isDisable());
		semanticFeatures.setSelected(!semanticFeatures.isDisable());
		unigramFeatures.setSelected(!unigramFeatures.isDisable());
		topWordsFeatures.setSelected(!topWordsFeatures.isDisable());
		patternFeatures.setSelected(!patternFeatures.isDisable());
		
		advancedSentimentFeatures.setSelected(!advancedSentimentFeatures.isDisable());
		advancedSemanticFeatures.setSelected(!advancedSemanticFeatures.isDisable());
		advancedPatternFeatures.setSelected(!advancedPatternFeatures.isDisable());
		advancedUnigramFeatures.setSelected(!advancedUnigramFeatures.isDisable());		
	}
	
	/**
	 * Save the parameters and close the window
	 */
	@FXML public void handleOk() {
		System.out.println("OK clicked!");
		
		if (checkParameters().equals(ErrorCode.NoFeatureSelected)) {
			AlertBox.display("Error", "Please choose at least one (1) set of features.", "OK");
		} else if (checkParameters().equals(ErrorCode.NonExtractedSelected)) {
			AlertBox.display("Error", "You have selected a features set that has not been extracted, please uncheck it!", "OK");
		} else if (checkParameters().equals(ErrorCode.Fine)) {
			saveParameters();
			FeatureSelectorWindowsManager.stage.close();
		}
	}
	
	/**
	 * Close the window without saving the parameters
	 */
	@FXML public void handleCancel() {
		System.out.println("Cancel clicked!");
		FeatureSelectorWindowsManager.stage.close();
	}
	
	
	// =====================================//
	//            Initialization            //
	// =====================================//
	
	/**
	 * Initialize the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		initializeAllowedFeatures();
		
		initializeBasicFeatures();
		initializeAdvancedFeatures();
	}
	
	private void initializeAllowedFeatures() {

		sentimentFeatures.setDisable(!Features.isUseSentimentFeatures());
		punctuationFeatures.setDisable(!Features.isUsePunctuationFeatures());
		syntacticFeatures.setDisable(!Features.isUseStylisticFeatures());
		semanticFeatures.setDisable(!Features.isUseSemanticFeatures());
		unigramFeatures.setDisable(!Features.isUseUnigramFeatures());
		topWordsFeatures.setDisable(!Features.isUseTopWords());
		patternFeatures.setDisable(!Features.isUsePatternFeatures());
		
		advancedSentimentFeatures.setDisable(!Features.isUseAdvancedSentimentFeatures());
		advancedSemanticFeatures.setDisable(!Features.isUseAdvancedSemanticFeatures());
		advancedPatternFeatures.setDisable(!Features.isUseAdvancedPatternFeatures());
		advancedUnigramFeatures.setDisable(!Features.isUseAdvancedUnigramFeatures());
		
	}
	
	/**
	 * Initialize the check boxes of the basic features
	 */
	private void initializeBasicFeatures() {
		sentimentFeatures.setSelected(ClassifierFeatures.isSentimentFeatures());
		punctuationFeatures.setSelected(ClassifierFeatures.isPunctuationFeatures());
		syntacticFeatures.setSelected(ClassifierFeatures.isSyntacticFeatures());
		semanticFeatures.setSelected(ClassifierFeatures.isSemanticFeatures());
		unigramFeatures.setSelected(ClassifierFeatures.isUnigramFeatures());
		topWordsFeatures.setSelected(ClassifierFeatures.isTopWordsFeatures());
		patternFeatures.setSelected(ClassifierFeatures.isPatternFeatures());
				
	}
	
	/**
	 * Initialize the check boxes of the advanced features
	 */
	private void initializeAdvancedFeatures() {
		advancedSentimentFeatures.setSelected(ClassifierFeatures.isAdvancedSentimentFeatures());
		advancedSemanticFeatures.setSelected(ClassifierFeatures.isAdvancedSemanticFeatures());
		advancedPatternFeatures.setSelected(ClassifierFeatures.isAdvancedPatternFeatures());
		advancedUnigramFeatures.setSelected(ClassifierFeatures.isAdvancedUnigramFeatures());
	}
	
	
	// =====================================//
	//             Other Methods            //
	// =====================================//
	
	/**
	 * Checks if any of the Text Fields presents an error (empty or containing non allowed characters
	 * @return the error code corresponding to which Text Field has an error (first one checked first)
	 */
	private ErrorCode checkParameters() {
		
		// Case where no set of features is seletected
		if (noFeatures()) {
			return ErrorCode.NoFeatureSelected;
		}
		// Case where the user selects a set of features he hasn't asked to extract (This cannot happen, but in case he somehow managed to)
		if (noAllowedFeatures()) {
			return ErrorCode.NonExtractedSelected;
		}
		// Everything is fine!
		return ErrorCode.Fine;
		
	}
	
	/**
	 * Check if none of the sets of features is selected
	 * @return True if none of the sets is selected; False if any of them is selected
	 */
	private boolean noFeatures() {
		
		if (sentimentFeatures.isSelected()) {
			return false;
		}
		if (punctuationFeatures.isSelected()) {
			return false;
		}
		if (syntacticFeatures.isSelected()) {
			return false;
		}
		if (semanticFeatures.isSelected()) {
			return false;
		}
		if (unigramFeatures.isSelected()) {
			return false;
		}
		if (topWordsFeatures.isSelected()) {
			return false;
		}
		if (patternFeatures.isSelected()) {
			return false;
		}
		
		if (advancedSentimentFeatures.isSelected()) {
			return false;
		}
		if (advancedSemanticFeatures.isSelected()) {
			return false;
		}
		if (advancedPatternFeatures.isSelected()) {
			return false;
		}
		if (advancedUnigramFeatures.isSelected()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Check if the user selected a set of features he has not extracted
	 * @return True if the user selected a set of features he has not extracted, False otherwise
	 */
	private boolean noAllowedFeatures() {
		
		if (sentimentFeatures.isSelected() && ! Features.isUseSentimentFeatures()) {
			return true;
		}
		if (punctuationFeatures.isSelected() && ! Features.isUsePunctuationFeatures()) {
			return true;
		}
		if (syntacticFeatures.isSelected() && ! Features.isUseStylisticFeatures()) {
			return true;
		}
		if (semanticFeatures.isSelected() && ! Features.isUseSemanticFeatures()) {
			return true;
		}
		if (unigramFeatures.isSelected() && ! Features.isUseUnigramFeatures()) {
			return true;
		}
		if (topWordsFeatures.isSelected() && ! Features.isUseTopWords()) {
			return true;
		}
		if (patternFeatures.isSelected() && ! Features.isUsePatternFeatures()) {
			return true;
		}
		
		if (advancedSentimentFeatures.isSelected() && ! Features.isUseAdvancedSentimentFeatures()) {
			return true;
		}
		if (advancedSemanticFeatures.isSelected() && ! Features.isUseAdvancedSemanticFeatures()) {
			return true;
		}
		if (advancedPatternFeatures.isSelected() && ! Features.isUseAdvancedPatternFeatures()) {
			return true;
		}
		if (advancedUnigramFeatures.isSelected() && ! Features.isUseAdvancedUnigramFeatures()) {
			return true;
		}
		return false;
	}

	/**
	 * Save the different parameters as specified by the user in the class {@link main.items.ClassifierParameters.java}
	 */
	private void saveParameters() {
		// From now on, use the customized features, and customized files
		ClassifierFeatures.setUseCustomizedFeatures(true);

		ClassifierFeatures.setSentimentFeatures(sentimentFeatures.isSelected());
		ClassifierFeatures.setPunctuationFeatures(punctuationFeatures.isSelected());
		ClassifierFeatures.setSyntacticFeatures(syntacticFeatures.isSelected());
		ClassifierFeatures.setSemanticFeatures(semanticFeatures.isSelected());
		ClassifierFeatures.setUnigramFeatures(unigramFeatures.isSelected());
		ClassifierFeatures.setTopWordsFeatures(topWordsFeatures.isSelected());
		ClassifierFeatures.setPatternFeatures(patternFeatures.isSelected());
		
		ClassifierFeatures.setAdvancedSentimentFeatures(advancedSentimentFeatures.isSelected());
		ClassifierFeatures.setAdvancedSemanticFeatures(advancedSemanticFeatures.isSelected());
		ClassifierFeatures.setAdvancedPatternFeatures(advancedPatternFeatures.isSelected());
		ClassifierFeatures.setAdvancedUnigramFeatures(advancedUnigramFeatures.isSelected());

	}

	
}

package windows.features;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import main.items.Features;
import windows.main.SelectBasicFeaturesWindow;

public class PunctuationFeaturesWindow implements Initializable {
	
	//=================================//
	//         FXML COMPONENTS         //
	//=================================//
		
	@FXML CheckBox numberOfDots;
	@FXML CheckBox numberOfCommas;
	@FXML CheckBox numberOfSemicolons;
	@FXML CheckBox numberOfExclamationMarks;
	@FXML CheckBox numberOfQuestionMarks;
	
	@FXML CheckBox numberOfParentheses;
	@FXML CheckBox numberOfBrackets;
	@FXML CheckBox numberOfBraces;
	
	@FXML CheckBox totalNumberOfCharacters;
	@FXML CheckBox totalNumberOfWords;
	@FXML CheckBox totalNumberOfSentences;
	@FXML CheckBox averageNumberOfCharacters;
	@FXML CheckBox averageNumberOfWords;
	
	@FXML CheckBox numberOfApostrophes;
	@FXML CheckBox numberOfQuotationMarks;
	
	@FXML Button defaultFeatures;
	@FXML Button allFeatures;
	@FXML Button clearFeatures;
	
	@FXML Button select;
	@FXML Button cancel;
	
	
	//=================================//
	//     FXML COMPONENTS ACTIONS     //
	//=================================//
	
	/**
	 * Select the default features
	 */
	@FXML public void handleDefault() {
		System.out.println("Default clicked!");
		
		numberOfDots.setSelected(true);
		numberOfCommas.setSelected(false);
		numberOfSemicolons.setSelected(false);
		numberOfExclamationMarks.setSelected(true);
		numberOfQuestionMarks.setSelected(true);
		
		numberOfParentheses.setSelected(false);
		numberOfBrackets.setSelected(false);
		numberOfBraces.setSelected(false);
		
		totalNumberOfCharacters.setSelected(false);
		totalNumberOfWords.setSelected(true);
		totalNumberOfSentences.setSelected(false);
		averageNumberOfCharacters.setSelected(false);
		averageNumberOfWords.setSelected(false);
		
		numberOfApostrophes.setSelected(false);
		numberOfQuotationMarks.setSelected(true);
	}
	
	/**
	 * Select all the features
	 */
	@FXML public void handleAll() {
		System.out.println("Default clicked!");
		
		numberOfDots.setSelected(true);
		numberOfCommas.setSelected(true);
		numberOfSemicolons.setSelected(true);
		numberOfExclamationMarks.setSelected(true);
		numberOfQuestionMarks.setSelected(true);
		
		numberOfParentheses.setSelected(true);
		numberOfBrackets.setSelected(true);
		numberOfBraces.setSelected(true);
		
		totalNumberOfCharacters.setSelected(true);
		totalNumberOfWords.setSelected(true);
		totalNumberOfSentences.setSelected(true);
		averageNumberOfCharacters.setSelected(true);
		averageNumberOfWords.setSelected(true);
		
		numberOfApostrophes.setSelected(true);
		numberOfQuotationMarks.setSelected(true);
	}
	
	/**
	 * Clear all the features (parameters)
	 * TODO make it so if the parameters are cleared and the user clicks OK it works [for the different feature sets]
	 */
	@FXML public void handleClear() {
		System.out.println("Default clicked!");
		
		numberOfDots.setSelected(false);
		numberOfCommas.setSelected(false);
		numberOfSemicolons.setSelected(false);
		numberOfExclamationMarks.setSelected(false);
		numberOfQuestionMarks.setSelected(false);
		
		numberOfParentheses.setSelected(false);
		numberOfBrackets.setSelected(false);
		numberOfBraces.setSelected(false);
		
		totalNumberOfCharacters.setSelected(false);
		totalNumberOfWords.setSelected(false);
		totalNumberOfSentences.setSelected(false);
		averageNumberOfCharacters.setSelected(false);
		averageNumberOfWords.setSelected(false);
		
		numberOfApostrophes.setSelected(false);
		numberOfQuotationMarks.setSelected(false);
	}
	
	/**
	 * Handles the select button
	 */
	@FXML public void handleSelect() {
		System.out.println("Select clicked!");
		
		int count = 0;

		// Number of dots
		if (numberOfDots.isSelected()) {
			count++;
			Features.setNumberOfDots(true);
		} else {
			Features.setNumberOfDots(false);
		}
		
		// Number of commas
		if (numberOfCommas.isSelected()) {
			count++;
			Features.setNumberOfCommas(true);
		} else {
			Features.setNumberOfCommas(false);
		}
		
		// Number of semicolons
		if (numberOfSemicolons.isSelected()) {
			count++;
			Features.setNumberOfSemicolons(true);
		} else {
			Features.setNumberOfSemicolons(false);
		}
		
		// Number of Exclamation Marks
		if (numberOfExclamationMarks.isSelected()) {
			count++;
			Features.setNumberOfExclamationMarks(true);
		} else {
			Features.setNumberOfExclamationMarks(false);
		}
		
		// Number of Question Marks
		if (numberOfQuestionMarks.isSelected()) {
			count++;
			Features.setNumberOfQuestionMarks(true);
		} else {
			Features.setNumberOfQuestionMarks(false);
		}
		
		// Number of Parenthesis
		if (numberOfParentheses.isSelected()) {
			count++;
			Features.setNumberOfParentheses(true);
		} else {
			Features.setNumberOfParentheses(false);
		}
		
		// Number of Brackets
		if (numberOfBrackets.isSelected()) {
			count++;
			Features.setNumberOfBrackets(true);
		} else {
			Features.setNumberOfBrackets(false);
		}
		
		// Number of Braces
		if (numberOfBraces.isSelected()) {
			count++;
			Features.setNumberOfBraces(true);
		} else {
			Features.setNumberOfBraces(false);
		}

		// Total Number Of Characters
		if (totalNumberOfCharacters.isSelected()) {
			count++;
			Features.setTotalNumberOfCharacters(true);
		} else {
			Features.setTotalNumberOfCharacters(false);
		}
		
		// Total Number Of Words
		if (totalNumberOfWords.isSelected()) {
			count++;
			Features.setTotalNumberOfWords(true);
		} else {
			Features.setTotalNumberOfWords(false);
		}
		
		// Total Number Of Sentences
		if (totalNumberOfSentences.isSelected()) {
			count++;
			Features.setTotalNumberOfSentences(true);
		} else {
			Features.setTotalNumberOfSentences(false);
		}
		
		// Average Number Of Characters
		if (averageNumberOfCharacters.isSelected()) {
			count++;
			Features.setAverageNumberOfCharactersPerSentence(true);
		} else {
			Features.setAverageNumberOfCharactersPerSentence(false);
		}
		
		// Average Number Of Words
		if (averageNumberOfWords.isSelected()) {
			count++;
			Features.setAverageNumberOfWordsPerSentence(true);
		} else {
			Features.setAverageNumberOfWordsPerSentence(false);
		}

		if (numberOfApostrophes.isSelected()) {
			count++;
			Features.setNumberOfApostrophes(true);
		} else {
			Features.setNumberOfApostrophes(false);
		}
		
		if (numberOfQuotationMarks.isSelected()) {
			count++;
			Features.setNumberOfQuotationMarks(true);
		} else {
			Features.setNumberOfQuotationMarks(false);
		}
		
		SelectBasicFeaturesWindow.setNumberOfPunctuationFeatures(count);
		SelectBasicFeaturesWindow.countOfPunctuationFeatures.set(count);
		FeaturesCustumization.stage.close();
	}
	
	/**
	 * Handle the cancel button
	 */
	@FXML public void handleCancel() {
		System.out.println("Cancel clicked!");
		FeaturesCustumization.stage.close();
	}


	//=================================//
	//         INITIALIZATION          //
	//=================================//
	
	/**
	 * Initialize the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		numberOfDots.setSelected(Features.isNumberOfDots());
		numberOfCommas.setSelected(Features.isNumberOfCommas());
		numberOfSemicolons.setSelected(Features.isNumberOfSemicolons());
		numberOfSemicolons.setSelected(Features.isNumberOfSemicolons());
		numberOfExclamationMarks.setSelected(Features.isNumberOfExclamationMarks());
		numberOfQuestionMarks.setSelected(Features.isNumberOfQuestionMarks());
		numberOfParentheses.setSelected(Features.isNumberOfParentheses());
		numberOfBrackets.setSelected(Features.isNumberOfBrackets());
		numberOfBraces.setSelected(Features.isNumberOfBraces());
		totalNumberOfCharacters.setSelected(Features.isTotalNumberOfCharacters());
		totalNumberOfWords.setSelected(Features.isTotalNumberOfWords());
		totalNumberOfSentences.setSelected(Features.isTotalNumberOfSentences());
		averageNumberOfCharacters.setSelected(Features.isAverageNumberOfCharactersPerSentence());
		averageNumberOfWords.setSelected(Features.isAverageNumberOfWordsPerSentence());
		numberOfApostrophes.setSelected(Features.isNumberOfApostrophes());
		numberOfQuotationMarks.setSelected(Features.isNumberOfQuotationMarks());
	}
}

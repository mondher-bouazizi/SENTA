package windows.features;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import main.items.Features;
import windows.main.SelectBasicFeaturesWindow;

public class SentimentFeaturesWindow implements Initializable {

	//======================================//
	//          NON-FXML COMPONENTS         //
	//======================================//
	
	public static final String SENTIMENTFEATURESHELP = "Sentiment-Related Features";
	
	
	//======================================//
	//           FXML COMPONENTS            //
	//======================================//
	
	@FXML Button defaultFeatures;
	@FXML Button allFeatures;
	@FXML Button clearFeatures;
	
	@FXML Button select;
	@FXML Button cancel;
	
	@FXML CheckBox numberOfPositiveWords;
	@FXML CheckBox numberOfNegativeWords;
	@FXML CheckBox numberOfHighlyEmoPositiveWords;
	@FXML CheckBox numberOfHighlyEmoNegativeWords;
	@FXML CheckBox numberOfCapitalizedPositiveWords;
	@FXML CheckBox numberOfCapitalizedNegativeWords;
	@FXML CheckBox ratioOfEmotionalWords;
	
	@FXML CheckBox numberOfPositiveEmoticons;
	@FXML CheckBox numberOfNegativeEmoticons;
	@FXML CheckBox numberOfNeutralEmoticons;
	@FXML CheckBox numberOfJokingEmoticons;
	
	@FXML CheckBox numberOfPositiveSlangs;
	@FXML CheckBox numberOfNegativeSlangs;
	
	@FXML CheckBox numberOfPositiveHashtags;
	@FXML CheckBox numberOfNegativeHashtags;
	
	@FXML CheckBox contrastWordsVsWords;
	@FXML CheckBox contrastWordsVsHashtags;
	@FXML CheckBox contrastWordsVsEmoticons;
	@FXML CheckBox contrastHashtagsVsHashtags;
	@FXML CheckBox contrastHashtagsVsEmoticons;
	
	
	//======================================//
	//       FXML COMPONENTS ACTIONS        //
	//======================================//
	
	/**
	 * Handle the action of "Default" Button: Select the default features
	 */
	@FXML public void handleDefault() {
		System.out.println("Default clicked!");
		
		numberOfPositiveWords.setSelected(true);
		numberOfNegativeWords.setSelected(true);
		numberOfHighlyEmoPositiveWords.setSelected(true);
		numberOfHighlyEmoNegativeWords.setSelected(true);
		numberOfCapitalizedPositiveWords.setSelected(false);
		numberOfCapitalizedNegativeWords.setSelected(false);
		ratioOfEmotionalWords.setSelected(true);
		
		numberOfPositiveEmoticons.setSelected(true);
		numberOfNegativeEmoticons.setSelected(true);
		numberOfNeutralEmoticons.setSelected(false);
		numberOfJokingEmoticons.setSelected(false);
		
		numberOfPositiveSlangs.setSelected(true);
		numberOfNegativeSlangs.setSelected(true);
		
		numberOfPositiveHashtags.setSelected(true);
		numberOfNegativeHashtags.setSelected(true);
		
		contrastWordsVsWords.setSelected(false);
		contrastWordsVsHashtags.setSelected(false);
		contrastWordsVsEmoticons.setSelected(false);
		contrastHashtagsVsHashtags.setSelected(false);
		contrastHashtagsVsEmoticons.setSelected(false);
	}
	
	/**
	 * Handle the action of "Select all" Button: Select all the features
	 */
	@FXML public void handleAll() {
		System.out.println("Default clicked!");
		
		numberOfPositiveWords.setSelected(true);
		numberOfNegativeWords.setSelected(true);
		numberOfHighlyEmoPositiveWords.setSelected(true);
		numberOfHighlyEmoNegativeWords.setSelected(true);
		numberOfCapitalizedPositiveWords.setSelected(true);
		numberOfCapitalizedNegativeWords.setSelected(true);
		ratioOfEmotionalWords.setSelected(true);
		
		numberOfPositiveEmoticons.setSelected(true);
		numberOfNegativeEmoticons.setSelected(true);
		numberOfNeutralEmoticons.setSelected(true);
		numberOfJokingEmoticons.setSelected(true);
		
		numberOfPositiveSlangs.setSelected(true);
		numberOfNegativeSlangs.setSelected(true);
		
		numberOfPositiveHashtags.setSelected(true);
		numberOfNegativeHashtags.setSelected(true);
		
		contrastWordsVsWords.setSelected(true);
		contrastWordsVsHashtags.setSelected(true);
		contrastWordsVsEmoticons.setSelected(true);
		contrastHashtagsVsHashtags.setSelected(true);
		contrastHashtagsVsEmoticons.setSelected(true);
	}
	
	/**
	 * Handle the action of "Clear" Button: Clear all the features
	 * TODO make it so if the parameters are cleared and the user clicks OK it works [for the different feature sets]
	 */
	@FXML public void handleClear() {
		System.out.println("Default clicked!");
		
		numberOfPositiveWords.setSelected(false);
		numberOfNegativeWords.setSelected(false);
		numberOfHighlyEmoPositiveWords.setSelected(false);
		numberOfHighlyEmoNegativeWords.setSelected(false);
		numberOfCapitalizedPositiveWords.setSelected(false);
		numberOfCapitalizedNegativeWords.setSelected(false);
		ratioOfEmotionalWords.setSelected(false);
		
		numberOfPositiveEmoticons.setSelected(false);
		numberOfNegativeEmoticons.setSelected(false);
		numberOfNeutralEmoticons.setSelected(false);
		numberOfJokingEmoticons.setSelected(false);
		
		numberOfPositiveSlangs.setSelected(false);
		numberOfNegativeSlangs.setSelected(false);
		
		numberOfPositiveHashtags.setSelected(false);
		numberOfNegativeHashtags.setSelected(false);
		
		contrastWordsVsWords.setSelected(false);
		contrastWordsVsHashtags.setSelected(false);
		contrastWordsVsEmoticons.setSelected(false);
		contrastHashtagsVsHashtags.setSelected(false);
		contrastHashtagsVsEmoticons.setSelected(false);
	}
	
	
	/**
	 * Handle the action of "Select" button
	 */
	@FXML public void handleSelect() {
		System.out.println("Select clicked!");
		
		int count = 0;

		// Number of positive words
		if (numberOfPositiveWords.isSelected()) {
			count++;
			Features.setNumberOfPositiveWords(true);
		} else {
			Features.setNumberOfPositiveWords(false);
		}
		
		// Number of negative words
		if (numberOfNegativeWords.isSelected()) {
			count++;
			Features.setNumberOfNegativeWords(true);
		} else {
			Features.setNumberOfNegativeWords(false);
		}
		
		// Number of highly emotional positive words
		if (numberOfHighlyEmoPositiveWords.isSelected()) {
			count++;
			Features.setNumberOfHighlyEmoPositiveWords(true);
		} else {
			Features.setNumberOfHighlyEmoPositiveWords(false);
		}
		
		// Number of highly emotional negative words
		if (numberOfHighlyEmoNegativeWords.isSelected()) {
			count++;
			Features.setNumberOfHighlyEmoNegativeWords(true);
		} else {
			Features.setNumberOfHighlyEmoNegativeWords(false);
		}
		
		// Number of Capitalized positive words
		if (numberOfCapitalizedPositiveWords.isSelected()) {
			count++;
			Features.setNumberOfCapitalizedPositiveWords(true);
		} else {
			Features.setNumberOfCapitalizedPositiveWords(false);
		}
		
		// Number of Capitalized Negative Words
		if (numberOfCapitalizedNegativeWords.isSelected()) {
			count++;
			Features.setNumberOfCapitalizedNegativeWords(true);
		} else {
			Features.setNumberOfCapitalizedNegativeWords(false);
		}
		
		// Ratio of Emotional Words
		if (ratioOfEmotionalWords.isSelected()) {
			count++;
			Features.setRatioOfEmotionalWords(true);
		} else {
			Features.setRatioOfEmotionalWords(false);
		}
		
		// Number of Positive Emoticons
		if (numberOfPositiveEmoticons.isSelected()) {
			count++;
			Features.setNumberOfPositiveEmoticons(true);
		} else {
			Features.setNumberOfPositiveEmoticons(false);
		}
		
		// Number of Negative Emoticons
		if (numberOfNegativeEmoticons.isSelected()) {
			count++;
			Features.setNumberOfNegativeEmoticons(true);
		} else {
			Features.setNumberOfNegativeEmoticons(false);
		}
		
		// Number of "Neutral" Emoticons
		if (numberOfNeutralEmoticons.isSelected()) {
			count++;
			Features.setNumberOfNeutralEmoticons(true);
		} else {
			Features.setNumberOfNeutralEmoticons(false);
		}
		
		// Number of "Joking" Emoticons
		if (numberOfJokingEmoticons.isSelected()) {
			count++;
			Features.setNumberOfJokingEmoticons(true);
		} else {
			Features.setNumberOfJokingEmoticons(false);
		}
		
		// Number Of Positive Slangs
		if (numberOfPositiveSlangs.isSelected()) {
			count++;
			Features.setNumberOfPositiveSlangs(true);
		} else {
			Features.setNumberOfPositiveSlangs(false);
		}
		
		// Number of Negative Slangs
		if (numberOfNegativeSlangs.isSelected()) {
			count++;
			Features.setNumberOfNegativeSlangs(true);
		} else {
			Features.setNumberOfNegativeSlangs(false);
		}
		
		// Number Of Positive Hashtags
		if (numberOfPositiveHashtags.isSelected()) {
			count++;
			Features.setNumberOfPositiveHashtags(true);
		} else {
			Features.setNumberOfPositiveHashtags(false);
		}
		
		// Number Of Negative Hashtags
		if (numberOfNegativeHashtags.isSelected()) {
			count++;
			Features.setNumberOfNegativeHashtags(true);
		} else {
			Features.setNumberOfNegativeHashtags(false);
		}
		
		// Contrast Words Vs Words
		if (contrastWordsVsWords.isSelected()) {
			count++;
			Features.setContrastWordsVsWords(true);
		} else {
			Features.setContrastWordsVsWords(false);
		}
		
		// Contrast Words Vs Hashtags
		if (contrastWordsVsHashtags.isSelected()) {
			count++;
			Features.setContrastWordsVsHashtags(true);
		} else {
			Features.setContrastWordsVsHashtags(false);
		}
		
		// Contrast Words Vs Emoticons
		if (contrastWordsVsEmoticons.isSelected()) {
			count++;
			Features.setContrastWordsVsEmoticons(true);
		} else {
			Features.setContrastWordsVsEmoticons(false);
		}
		
		// Contrast Hashtags Vs Hashtags
		if (contrastHashtagsVsHashtags.isSelected()) {
			count++;
			Features.setContrastHashtagsVsHashtags(true);
		} else {
			Features.setContrastHashtagsVsHashtags(false);
		}
		
		// Contrast Hashtags Vs Emoticons
		if (contrastHashtagsVsEmoticons.isSelected()) {
			count++;
			Features.setContrastHashtagsVsEmoticons(true);
		} else {
			Features.setContrastHashtagsVsEmoticons(false);
		}
		
		SelectBasicFeaturesWindow.setNumberOfSentimentRelatedFeatures(count);
		SelectBasicFeaturesWindow.countOfSentimentRelatedFeatures.set(count);
		FeaturesCustumization.stage.close();
	}
	
	/**
	 * Handle the action of "Cancel" Button
	 */
	@FXML public void handleCancel() {
		System.out.println("Cancel clicked!");
		FeaturesCustumization.stage.close();
	}


	//======================================//
	//            INITIALIZATION            //
	//======================================//
	
	/**
	 * Initialize the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		numberOfPositiveWords.setSelected(Features.isNumberOfPositiveWords());
		numberOfNegativeWords.setSelected(Features.isNumberOfNegativeWords());
		numberOfHighlyEmoPositiveWords.setSelected(Features.isNumberOfHighlyEmoPositiveWords());
		numberOfHighlyEmoNegativeWords.setSelected(Features.isNumberOfHighlyEmoNegativeWords());
		numberOfCapitalizedPositiveWords.setSelected(Features.isNumberOfCapitalizedPositiveWords());
		numberOfCapitalizedNegativeWords.setSelected(Features.isNumberOfCapitalizedNegativeWords());
		ratioOfEmotionalWords.setSelected(Features.isRatioOfEmotionalWords());
		numberOfPositiveEmoticons.setSelected(Features.isNumberOfPositiveEmoticons());
		numberOfNegativeEmoticons.setSelected(Features.isNumberOfNegativeEmoticons());
		numberOfNeutralEmoticons.setSelected(Features.isNumberOfNeutralEmoticons());
		numberOfJokingEmoticons.setSelected(Features.isNumberOfJokingEmoticons());
		numberOfPositiveSlangs.setSelected(Features.isNumberOfPositiveSlangs());
		numberOfNegativeSlangs.setSelected(Features.isNumberOfNegativeSlangs());
		numberOfPositiveHashtags.setSelected(Features.isNumberOfPositiveHashtags());
		numberOfNegativeHashtags.setSelected(Features.isNumberOfNegativeHashtags());
		contrastWordsVsWords.setSelected(Features.isContrastWordsVsWords());
		contrastWordsVsHashtags.setSelected(Features.isContrastWordsVsHashtags());
		contrastWordsVsEmoticons.setSelected(Features.isContrastWordsVsEmoticons());
		contrastHashtagsVsHashtags.setSelected(Features.isContrastHashtagsVsHashtags());
		contrastHashtagsVsEmoticons.setSelected(Features.isContrastHashtagsVsEmoticons());
	}
}

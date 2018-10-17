package windows.features;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import main.items.Features;
import windows.main.SelectAdvancedFeaturesWindow;
import windows.others.AlertBox;

public class AdvancedSemanticFeaturesWindow implements Initializable {

	// =====================================//
	//           FXML Components            //
	// =====================================//
	
	@FXML Button defaultFeatures;
	@FXML Button allFeatures;
	@FXML Button clearFeatures;
	
	@FXML Button select;
	@FXML Button cancel;
	
	@FXML RadioButton onlyTopicWords;
	@FXML RadioButton allWords;
	
	@FXML RadioButton countOtherWords;
	@FXML RadioButton notCountOtherWords;
	
	@FXML RadioButton posSeparately;
	@FXML RadioButton posTogether;
	
	@FXML CheckBox useSentimentalPositiveWords;
	@FXML CheckBox useSentimentalNegativeWords;
	@FXML CheckBox useOfHighlySentimentalPositiveWords;
	@FXML CheckBox useOfHighlySentimentalNegativeWords;
	@FXML CheckBox useOfOpinionWords;
	@FXML CheckBox useOfUncertaintyWords;
	@FXML CheckBox useOfActiveForm;
	@FXML CheckBox useOfPassiveForm;
	
	
	// =====================================//
	//           BUTTON ACTIONS             //
	// =====================================//
	
	/**
	 * Select the default features
	 */
	@FXML public void handleDefault() {
		System.out.println("Default clicked!");
		
		onlyTopicWords.setSelected(true);
		allWords.setSelected(false);
		
		countOtherWords.setSelected(false);
		notCountOtherWords.setSelected(true);
		
		posSeparately.setSelected(false);
		posTogether.setSelected(true);
		
		useSentimentalPositiveWords.setSelected(true);
		useSentimentalNegativeWords.setSelected(true);
		useOfHighlySentimentalPositiveWords.setSelected(false);
		useOfHighlySentimentalNegativeWords.setSelected(false);
		useOfOpinionWords.setSelected(true);
		useOfUncertaintyWords.setSelected(true);
		useOfActiveForm.setSelected(false);
		useOfPassiveForm.setSelected(false);
		
	}
	
	/**
	 * Select all
	 */
	@FXML public void handleAll() {
		System.out.println("Default clicked!");
		
		onlyTopicWords.setSelected(true);
		allWords.setSelected(false);
		
		countOtherWords.setSelected(true);
		notCountOtherWords.setSelected(false);
		
		posSeparately.setSelected(true);
		posTogether.setSelected(false);
		
		useSentimentalPositiveWords.setSelected(true);
		useSentimentalNegativeWords.setSelected(true);
		useOfHighlySentimentalPositiveWords.setSelected(true);
		useOfHighlySentimentalNegativeWords.setSelected(true);
		useOfOpinionWords.setSelected(true);
		useOfUncertaintyWords.setSelected(true);
		useOfActiveForm.setSelected(true);
		useOfPassiveForm.setSelected(true);
	}
	
	/**
	 * Clear all
	 */
	@FXML public void handleClear() {
		System.out.println("Default clicked!");
		
		onlyTopicWords.setSelected(false);
		allWords.setSelected(false);
		
		countOtherWords.setSelected(false);
		notCountOtherWords.setSelected(false);
		
		posSeparately.setSelected(false);
		posTogether.setSelected(false);
		
		useSentimentalPositiveWords.setSelected(false);
		useSentimentalNegativeWords.setSelected(false);
		useOfHighlySentimentalPositiveWords.setSelected(false);
		useOfHighlySentimentalNegativeWords.setSelected(false);
		useOfOpinionWords.setSelected(false);
		useOfUncertaintyWords.setSelected(false);
		useOfActiveForm.setSelected(false);
		useOfPassiveForm.setSelected(false);

	}
	
	
	/**
	 * Handles the select button
	 */
	@FXML public void handleSelect() {
		System.out.println("Select clicked!");
		
		int count = 1;
		
		int 	c1 = 1,
				c2 = 1,
				c3 = 0;
		
		if (checkParameters().equals("type")) {
			AlertBox.display("Error", "Please choose whether you want to consider only topic-related words or all the words", "OK");
		} else if (checkParameters().equals("otherWords")) {
			AlertBox.display("Error", "Please choose an action for the other words", "OK");
		} else if (checkParameters().equals("pos")) {
			AlertBox.display("Error", "Please choose whether you want to consider the different PoS tags together or seperately", "OK");
		} else {
			
			if (onlyTopicWords.isSelected()) {
				Features.setWordsIntoAccount(Features.TopicRelated.ONLYTOPICRELATED);
			} else if (allWords.isSelected()) {
				Features.setWordsIntoAccount(Features.TopicRelated.ALL);
			}
			
			if (countOtherWords.isSelected()) {
				c1++;
				Features.setCountOtherWords(true);
			} else if (notCountOtherWords.isSelected()) {
				Features.setCountOtherWords(false);
			}
			
			if (posSeparately.isSelected()) {
				c2 = 4;
				Features.setAdvancedSemanticPos(Features.TypeOfPos.SEPARATED);
			} else if (posTogether.isSelected()) {
				c2 = 1;
				Features.setAdvancedSemanticPos(Features.TypeOfPos.TOGETHER);
			}
			
			
			if(useSentimentalPositiveWords.isSelected()) {
				Features.setAdvUseOfPositiveWords(true);
				c3++;
			} else {
				Features.setAdvUseOfPositiveWords(false);
			}
			
			if(useSentimentalNegativeWords.isSelected()) {
				Features.setAdvUseOfNegativeWords(true);
				c3++;
			} else {
				Features.setAdvUseOfNegativeWords(false);
			}
			
			if(useOfHighlySentimentalPositiveWords.isSelected()) {
				Features.setAdvUseOfHighlyPositiveWords(true);
				c3++;
			} else {
				Features.setAdvUseOfHighlyPositiveWords(false);
			}
			
			if(useOfHighlySentimentalNegativeWords.isSelected()) {
				Features.setAdvUseOfHighlyNegativeWords(true);
				c3++;
			} else {
				Features.setAdvUseOfHighlyNegativeWords(false);
			}
			
			if(useOfOpinionWords.isSelected()) {
				Features.setAdvUseOfOpinionWords(true);
				c3++;
			} else {
				Features.setAdvUseOfOpinionWords(true);
			}
			
			if(useOfUncertaintyWords.isSelected()) {
				Features.setAdvUseOfUncertaintyWords(true);
				c3++;
			} else {
				Features.setAdvUseOfUncertaintyWords(false);
			}
			
			if(useOfActiveForm.isSelected()) {
				Features.setAdvUseOfActiveForm(true);
				c3++;
			} else {
				Features.setAdvUseOfActiveForm(false);
			}
			
			if(useOfPassiveForm.isSelected()) {
				Features.setAdvUseOfPassiveForm(true);
				c3++;
			} else {
				Features.setAdvUseOfPassiveForm(false);
			}
			
			count = c3 * c2 * c1;
			
			
			SelectAdvancedFeaturesWindow.setNumberOfSemanticFeatures(count);
			SelectAdvancedFeaturesWindow.countOfSemanticFeatures.set(count);
			
			FeaturesCustumization.stage.close();
			
		}
	}
	
	@FXML public void handleCancel() {
		System.out.println("Cancel clicked!");
		FeaturesCustumization.stage.close();
	}


	// =====================================//
	//            OTHER METHODS             //
	// =====================================//
	
	/**
	 * Initialize of the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (Features.getWordsIntoAccount() != null) {
			if (Features.getWordsIntoAccount().equals(Features.TopicRelated.ONLYTOPICRELATED)) {
				onlyTopicWords.setSelected(true);
				allWords.setSelected(false);
			} else if (Features.getWordsIntoAccount().equals(Features.TopicRelated.ALL)) {
				onlyTopicWords.setSelected(false);
				allWords.setSelected(true);
			} 
		}
		
		if (Features.getWordsIntoAccount() != null) {
			if (Features.isCountOtherWords()) {
				countOtherWords.setSelected(true);
				notCountOtherWords.setSelected(false);
			} else {
				countOtherWords.setSelected(false);
				notCountOtherWords.setSelected(true);
			}
		}
		
		if (Features.getAdvancedSemanticPos() != null) {
			if (Features.getAdvancedSemanticPos().equals(Features.TypeOfPos.SEPARATED)) {
				posSeparately.setSelected(true);
				posTogether.setSelected(false);
			} else if (Features.getAdvancedSemanticPos().equals(Features.TypeOfPos.TOGETHER)) {
				posSeparately.setSelected(false);
				posTogether.setSelected(true);
			} 
		}
		
		useSentimentalPositiveWords.setSelected(Features.isAdvUseOfPositiveWords());
		useSentimentalNegativeWords.setSelected(Features.isAdvUseOfNegativeWords());
		useOfHighlySentimentalPositiveWords.setSelected(Features.isAdvUseOfHighlyPositiveWords());
		useOfHighlySentimentalPositiveWords.setSelected(Features.isAdvUseOfHighlyPositiveWords());
		useOfHighlySentimentalNegativeWords.setSelected(Features.isAdvUseOfHighlyNegativeWords());
		useOfOpinionWords.setSelected(Features.isAdvUseOfOpinionWords());
		useOfUncertaintyWords.setSelected(Features.isAdvUseOfUncertaintyWords());
		useOfActiveForm.setSelected(Features.isAdvUseOfActiveForm());
		useOfPassiveForm.setSelected(Features.isAdvUseOfPassiveForm());

		
	}

	/**
	 * Checks whether the parameters entered are correct
	 * TODO add an enumeration so that the different error codes are in the enum
	 * @return
	 */
	private String checkParameters() {
		
		String errorCode = "fine";
		
		if (!onlyTopicWords.isSelected() && !allWords.isSelected()) {
			return "type";
		}
		
		if (onlyTopicWords.isSelected() && !countOtherWords.isSelected() && !notCountOtherWords.isSelected()) {
			return "otherWords";
		}

		
		if (!posSeparately.isSelected() && !posTogether.isSelected()) {
			return "pos";
		}
		
		return errorCode;
	}

}

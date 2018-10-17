package windows.features;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import main.items.Features;
import main.items.Parameters;
import windows.main.SelectBasicFeaturesWindow;
import windows.others.AlertBox;

public class TopWordsFeaturesWindow implements Initializable {
		
	public static final String SENTIMENTFEATURESHELP = "Unigram Features";
	
	
	@FXML Button defaultFeatures;
	@FXML Button allFeatures;
	@FXML Button clearFeatures;
	
	@FXML Button select;
	@FXML Button cancel;
	
	@FXML CheckBox useTopWordsNouns;
	@FXML CheckBox useTopWordsVerbs;
	@FXML CheckBox useTopWordsAdjectives;
	@FXML CheckBox useTopWordsAdverbs;
	
	@FXML RadioButton together;
	@FXML RadioButton separated;
	
	@FXML TextField numberOfTopWordsPerClass;
	@FXML TextField numberOfTopWordsPerPos;
	
	@FXML TextField topWordsMinRatio;
	@FXML TextField topWordsMinOccurrence;
	
	@FXML RadioButton uniquefeature;
	@FXML RadioButton summedfeature;
	
	
	@FXML public void handleTogetherRadio() {
		numberOfTopWordsPerClass.setDisable(false);
		numberOfTopWordsPerPos.setDisable(true);
	}
	
	@FXML public void handleSeparatedRadio() {
			numberOfTopWordsPerPos.setDisable(false);
			numberOfTopWordsPerClass.setDisable(true);
	}
	
	
	/**
	 * Select the default features
	 */
	@FXML public void handleDefault() {
		System.out.println("Default clicked!");
		
		useTopWordsNouns.setSelected(true);
		useTopWordsVerbs.setSelected(true);
		useTopWordsAdjectives.setSelected(true);
		useTopWordsAdverbs.setSelected(true);
		
		together.setSelected(true);
		separated.setSelected(false);
		
		numberOfTopWordsPerClass.setText("50");
		numberOfTopWordsPerClass.setDisable(false);
		
		numberOfTopWordsPerPos.setText("20");
		numberOfTopWordsPerPos.setDisable(true);
		
		topWordsMinRatio.setText("1.2");
		topWordsMinOccurrence.setText("4");
		
		uniquefeature.setSelected(true);
		summedfeature.setSelected(false);
	}
	
	/**
	 * Select all
	 */
	@FXML public void handleAll() {
		System.out.println("All clicked!");
		
		useTopWordsNouns.setSelected(true);
		useTopWordsVerbs.setSelected(true);
		useTopWordsAdjectives.setSelected(true);
		useTopWordsAdverbs.setSelected(true);
		
		together.setSelected(true);
		separated.setSelected(false);
		
		numberOfTopWordsPerClass.setText("100");
		numberOfTopWordsPerClass.setDisable(false);
		
		numberOfTopWordsPerPos.setText("40");
		numberOfTopWordsPerPos.setDisable(true);
		
		topWordsMinRatio.setText("1.2");
		topWordsMinOccurrence.setText("4");
		
		uniquefeature.setSelected(true);
		summedfeature.setSelected(false);
	}
	
	/**
	 * Clear all
	 */
	@FXML public void handleClear() {
		System.out.println("clear clicked!");
		
		useTopWordsNouns.setSelected(false);
		useTopWordsVerbs.setSelected(false);
		useTopWordsAdjectives.setSelected(false);
		useTopWordsAdverbs.setSelected(false);
		
		together.setSelected(false);
		separated.setSelected(false);
		
		numberOfTopWordsPerClass.setText("0");
		numberOfTopWordsPerClass.setDisable(false);
		
		numberOfTopWordsPerPos.setText("0");
		numberOfTopWordsPerPos.setDisable(false);
		
		topWordsMinRatio.setText("0");
		topWordsMinOccurrence.setText("0");
		
		uniquefeature.setSelected(false);
		summedfeature.setSelected(false);
	}
	
	
	
	/**
	 * Handles the select button
	 */
	@FXML public void handleSelect() {
		System.out.println("Select clicked!");
		
		if (checkNumbers()) {
			
//			if (together.isSelected()) {
//				Features.setNumberOfTopWordsPerClass(Integer.parseInt(numberOfTopWordsPerClass.getText()));
//			} else if (separated.isSelected()) {
//				Features.setNumberOfTopWordsPerPos(Integer.parseInt(numberOfTopWordsPerPos.getText()));
//			} else {
//				
//			}
			
			// TODO Check if this makes more sense for the import/export thing!
			Features.setNumberOfTopWordsPerClass(Integer.parseInt(numberOfTopWordsPerClass.getText()));
			Features.setNumberOfTopWordsPerPos(Integer.parseInt(numberOfTopWordsPerPos.getText()));
			
			
			Features.setTopWordsMinRatio(Double.parseDouble(topWordsMinRatio.getText()));
			Features.setTopWordsMinOccurrence(Integer.parseInt(topWordsMinOccurrence.getText()));
			
			int 	c1 = 0,
					c2=0,
					c3=Parameters.getClasses().size();
			
			int count = 0;
			
			// Use nouns
			if (useTopWordsNouns.isSelected()) {
				c1++;
				Features.setUseTopWordsNouns(true);
			} else {
				Features.setUseTopWordsNouns(false);
			}
			// Use verbs
			if (useTopWordsVerbs.isSelected()) {
				c1++;
				Features.setUseTopWordsVerbs(true);
			} else {
				Features.setUseTopWordsVerbs(false);
			}
			// Use adjectives
			if (useTopWordsAdjectives.isSelected()) {
				c1++;
				Features.setUseTopWordsAdjectives(true);
			} else {
				Features.setUseTopWordsAdjectives(false);
			}
			// Use adverbs
			if (useTopWordsAdverbs.isSelected()) {
				c1++;
				Features.setUseTopWordsAdverbs(true);
			} else {
				Features.setUseTopWordsAdverbs(false);
			}
			
			if (together.isSelected()) {
				c1=1;
				c2 = Features.getNumberOfTopWordsPerClass();
				Features.setTopWordsTypeOfPos(Features.TypeOfPos.TOGETHER);
			} else if (separated.isSelected()) {
				c2 = Features.getNumberOfTopWordsPerPos();
				Features.setTopWordsTypeOfPos(Features.TypeOfPos.SEPARATED);
			} else {
				// TODO handle this
				c2 = Features.getNumberOfTopWordsPerPos();
				Features.setTopWordsTypeOfPos(null);
			}
			
			if (uniquefeature.isSelected()) {
				count = c1*c2*c3;
				Features.setTopWordsType(Features.TypeOfFeature.UNIQUE);
			} else if (summedfeature.isSelected()) {
				count = c1*c3;
				Features.setTopWordsType(Features.TypeOfFeature.SUMMED);
			} else {
				// TODO handle this
				count = c1*c2*c3;
				Features.setTopWordsType(null);
			}
			
			SelectBasicFeaturesWindow.setNumberOfTopWordsFeatures(count);
			SelectBasicFeaturesWindow.countOfTopWordsFeatures.set(count);
			FeaturesCustumization.stage.close();
		}
	}
	
	/**
	 * Handle the cancel button
	 */
	@FXML public void handleCancel() {
		System.out.println("Cancel clicked!");
		FeaturesCustumization.stage.close();
	}


	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Use Top Words Nouns
		if(Features.isUseTopWordsNouns()) {
			useTopWordsNouns.setSelected(true);
		}
		// Use Top Words Verbs
		if(Features.isUseTopWordsVerbs()) {
			useTopWordsVerbs.setSelected(true);
		}
		// Use TopWords Adjectives
		if(Features.isUseTopWordsAdjectives()) {
			useTopWordsAdjectives.setSelected(true);
		}
		// Use TopWords Adverbs
		if(Features.isUseTopWordsAdverbs()) {
			useTopWordsAdverbs.setSelected(true);
		}
		
		// Together or Separated
		if (Features.getTopWordsTypeOfPos()!=null) {
			if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
				together.setSelected(true);
				separated.setSelected(false);
				numberOfTopWordsPerClass.setDisable(false);
				numberOfTopWordsPerPos.setDisable(true);
			} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
				together.setSelected(false);
				separated.setSelected(true);
				numberOfTopWordsPerClass.setDisable(true);
				numberOfTopWordsPerPos.setDisable(false);
			} 
		} else {
			together.setSelected(false);
			separated.setSelected(false);
			numberOfTopWordsPerClass.setDisable(true);
			numberOfTopWordsPerPos.setDisable(true);
		}
		
		// Number of words per class
		numberOfTopWordsPerClass.setText(Features.getNumberOfTopWordsPerClass()+"");
		
		// Number of words per pos
		numberOfTopWordsPerPos.setText(Features.getNumberOfTopWordsPerPos() + "");
		
		// min Ratio
		topWordsMinRatio.setText(Features.getTopWordsMinRatio() + "");
		
		// min occurrence
		topWordsMinOccurrence.setText(Features.getTopWordsMinOccurrence() + "");
		
		// Unique or Summed
		if (Features.getTopWordsType()!=null) {
			if (Features.getTopWordsType().equals(Features.TypeOfFeature.UNIQUE)) {
				uniquefeature.setSelected(true);
				summedfeature.setSelected(false);
			} else if (Features.getTopWordsType().equals(Features.TypeOfFeature.SUMMED)) {
				uniquefeature.setSelected(false);
				summedfeature.setSelected(true);
			} 
		} else {
			uniquefeature.setSelected(false);
			summedfeature.setSelected(false);
		}
		
		
	}
	
	
	private boolean checkNumbers() {
		
		if (together.isSelected()) {
			if (!checkInteger(numberOfTopWordsPerClass.getText())) {
				AlertBox.display("Error", "The \"Number of Top Words per Class\" should be a number (integer)", "OK");
				return false;
			}
		} else if (separated.isSelected()) {
			if (!checkInteger(numberOfTopWordsPerPos.getText())) {
				AlertBox.display("Error", "The \"Number of Top Words per PoS\" should be a number (integer)", "OK");
				return false;
			}
		}
		
		if (!checkDouble(topWordsMinRatio.getText())) {
			AlertBox.display("Error", "The \"Min Ratio\" should be a number (decimal)", "OK");
			return false;
		}
		
		if (!checkInteger(topWordsMinOccurrence.getText())) {
			AlertBox.display("Error", "The \"Min Number of Occurrences\" should be a number (integer)", "OK");
			return false;
		}
		
		
		return true;
	}
	
	private boolean checkInteger(String text) {
		boolean test = false;
		
		try {
			Integer.parseInt(text);
			test = true;
		} catch (NumberFormatException e) {
			return false;
		}
		return test;
	}
	
	private boolean checkDouble(String text) {
		boolean test = false;
		
		try {
			Double.parseDouble(text);
			test = true;
		} catch (NumberFormatException e) {
			return false;
		}
		return test;
	}

}

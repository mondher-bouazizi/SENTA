package windows.features;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import main.items.Features;
import windows.main.SelectBasicFeaturesWindow;

public class SemanticFeaturesWindow implements Initializable {
	
	//=================================//
	//         FXML COMPONENTS         //
	//=================================//
	
	@FXML Button defaultFeatures;
	@FXML Button allFeatures;
	@FXML Button clearFeatures;
	
	@FXML Button select;
	@FXML Button cancel;
	
	@FXML CheckBox useOfOpinionWords;
	@FXML CheckBox useOfHighlySentimentalWords;
	@FXML CheckBox useOfUncertaintyWords;
	@FXML CheckBox useOfActiveForm;
	@FXML CheckBox useOfPassiveForm;
	
	
	//=================================//
	//     FXML COMPONENTS ACTIONS     //
	//=================================//
	
	/**
	 * Select the default features
	 */
	@FXML public void handleDefault() {
		System.out.println("Default clicked!");
		
		useOfOpinionWords.setSelected(true);
		useOfHighlySentimentalWords.setSelected(true);
		useOfUncertaintyWords.setSelected(true);
		useOfActiveForm.setSelected(false);
		useOfPassiveForm.setSelected(true);
	}
	
	/**
	 * Select all the features
	 */
	@FXML public void handleAll() {
		System.out.println("Default clicked!");
		
		useOfOpinionWords.setSelected(true);
		useOfHighlySentimentalWords.setSelected(true);
		useOfUncertaintyWords.setSelected(true);
		useOfActiveForm.setSelected(true);
		useOfPassiveForm.setSelected(true);
	}
	
	/**
	 * Clear all the features (parameters)
	 * TODO make it so if the parameters are cleared and the user clicks OK it works [for the different feature sets]
	 */
	@FXML public void handleClear() {
		System.out.println("Default clicked!");
		
		useOfOpinionWords.setSelected(false);
		useOfHighlySentimentalWords.setSelected(false);
		useOfUncertaintyWords.setSelected(false);
		useOfActiveForm.setSelected(false);
		useOfPassiveForm.setSelected(false);
	}
	
	/**
	 * Handles the [Select] button
	 */
	@FXML public void handleSelect() {
		System.out.println("Select clicked!");
		
		int count = 0;

		// Use of opinion words
		if (useOfOpinionWords.isSelected()) {
			count++;
			Features.setUseOfOpinionWords(true);
		} else {
			Features.setUseOfOpinionWords(false);
		}
		
		// Use of highly sentimental words
		if (useOfHighlySentimentalWords.isSelected()) {
			count++;
			Features.setUseOfHighlySentimentalWords(true);
		} else {
			Features.setUseOfHighlySentimentalWords(false);
		}
		
		// Use of uncertainty words
		if (useOfUncertaintyWords.isSelected()) {
			count++;
			Features.setUseOfUncertaintyWords(true);
		} else {
			Features.setUseOfUncertaintyWords(false);
		}
		
		// Use of active form
		if (useOfActiveForm.isSelected()) {
			count++;
			Features.setUseOfActiveForm(true);
		} else {
			Features.setUseOfActiveForm(false);
		}
		
		// Use of passive form
		if (useOfPassiveForm.isSelected()) {
			count++;
			Features.setUseOfPassiveForm(true);
		} else {
			Features.setUseOfPassiveForm(false);
		}
		
		SelectBasicFeaturesWindow.setNumberOfSemanticFeatures(count);
		SelectBasicFeaturesWindow.countOfSemanticFeatures.set(count);
		FeaturesCustumization.stage.close();
	}
	
	/**
	 * Handles the [Cancel] button
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
		useOfOpinionWords.setSelected(Features.isUseOfOpinionWords());
		useOfHighlySentimentalWords.setSelected(Features.isUseOfHighlySentimentalWords());
		useOfUncertaintyWords.setSelected(Features.isUseOfUncertaintyWords());
		useOfActiveForm.setSelected(Features.isUseOfActiveForm());
		useOfPassiveForm.setSelected(Features.isUseOfPassiveForm());
	}
}

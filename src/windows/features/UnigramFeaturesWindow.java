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
import windows.features.customunigrams.CustomizeUnigrams;
import windows.main.SelectBasicFeaturesWindow;
import windows.others.AlertBox;

public class UnigramFeaturesWindow implements Initializable {
	
	//======================================//
	//         NON-FXML COMPONENTS          //
	//======================================//
		
	public static final String SENTIMENTFEATURESHELP = "Unigram Features";
	
	
	//======================================//
	//           FXML COMPONENTS            //
	//======================================//
	
	@FXML Button defaultFeatures;
	@FXML Button allFeatures;
	@FXML Button clearFeatures;
	
	@FXML Button select;
	@FXML Button cancel;
	
	@FXML CheckBox nouns;
	@FXML CheckBox verbs;
	@FXML CheckBox adjectives;
	@FXML CheckBox adverbs;
	
	@FXML RadioButton together;
	@FXML RadioButton separated;
	
	@FXML TextField depth;
	
	@FXML CheckBox zeroScoreTaken;
	@FXML CheckBox oppositeTaken;
	
	@FXML Button manageSeedWords;
	
	
	//======================================//
	//       FXML COMPONENTS ACTIONS        //
	//======================================//
	
	/**
	 * Select the default features
	 */
	@FXML public void handleDefault() {
		System.out.println("Default clicked!");
		
		nouns.setSelected(true);
		verbs.setSelected(true);
		adjectives.setSelected(true);
		adverbs.setSelected(true);
		
		together.setSelected(true);
		separated.setSelected(false);
		
		depth.setText("2");
		
		zeroScoreTaken.setSelected(false);
		oppositeTaken.setSelected(true);
	}
	
	/**
	 * Select all
	 */
	@FXML public void handleAll() {
		System.out.println("Default clicked!");
		
		nouns.setSelected(true);
		verbs.setSelected(true);
		adjectives.setSelected(true);
		adverbs.setSelected(true);
		
		together.setSelected(true);
		separated.setSelected(false);
		
		depth.setText("2");
		
		zeroScoreTaken.setSelected(true);
		oppositeTaken.setSelected(true);
	}
	
	/**
	 * Clear all
	 */
	@FXML public void handleClear() {
		System.out.println("Default clicked!");
		
		nouns.setSelected(false);
		verbs.setSelected(false);
		adjectives.setSelected(false);
		adverbs.setSelected(false);
		
		together.setSelected(false);
		separated.setSelected(false);
		
		depth.setText("0");
		
		zeroScoreTaken.setSelected(false);
		oppositeTaken.setSelected(false);
	}
	
	
	@FXML public void handleManageSeedWords() {
		System.out.println("Manage seed words clicked!");
		CustomizeUnigrams.customize();
	}
	
	
	/**
	 * Handles the select button
	 */
	@FXML public void handleSelect() {
		System.out.println("Select clicked!");
		
		if (checkNumber()) {
			
			Features.setDepth(Integer.parseInt(depth.getText()));
			
			int c3 = Parameters.getClasses().size();
			int count = 0;
			// Use nouns
			if (nouns.isSelected()) {
				count++;
				Features.setUseUnigramNouns(true);
			} else {
				Features.setUseUnigramNouns(false);
			}
			// Use verbs
			if (verbs.isSelected()) {
				count++;
				Features.setUseUnigramVerbs(true);
			} else {
				Features.setUseUnigramVerbs(false);
			}
			// Use adjectives
			if (adjectives.isSelected()) {
				count++;
				Features.setUseUnigramAdjectives(true);
			} else {
				Features.setUseUnigramAdjectives(false);
			}
			// Use adverbs
			if (adverbs.isSelected()) {
				count++;
				Features.setUseUnigramAdverbs(true);
			} else {
				Features.setUseUnigramAdverbs(false);
			}
			// How the POS tags are taken (separated or together)
			if (together.isSelected()) {
				count = 1; // remember this will be multiplied by the number of classes
				Features.setUnigramTypeOfPos(Features.TypeOfPos.TOGETHER);
			} else if (separated.isSelected()) {
				Features.setUnigramTypeOfPos(Features.TypeOfPos.SEPARATED);
			} else {
				// TODO handle this
				Features.setUnigramTypeOfPos(Features.TypeOfPos.SEPARATED);
			}
			// Zero score words taken?
			if (zeroScoreTaken.isSelected()) {
				Features.setZeroTaken(true);
			} else {
				Features.setZeroTaken(false);
			}
			// opposite sentiment words taken?
			if (oppositeTaken.isSelected()) {
				Features.setOppositeTaken(true);
			} else {
				Features.setOppositeTaken(false);
			}
			SelectBasicFeaturesWindow.setNumberOfUnigramFeatures(count * c3);
			SelectBasicFeaturesWindow.countOfUnigramFeatures.set(count * c3);
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

	
	//======================================//
	//           INITIALIZATION             //
	//======================================//
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(Features.isUseUnigramNouns()) {
			nouns.setSelected(true);
		}
		if(Features.isUseUnigramVerbs()) {
			verbs.setSelected(true);
		}
		if(Features.isUseUnigramAdjectives()) {
			adjectives.setSelected(true);
		}
		if(Features.isUseUnigramAdverbs()) {
			adverbs.setSelected(true);
		}

		if (Features.getUnigramTypeOfPos()!=null) {
			if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
				separated.setSelected(true);
			}
			if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
				together.setSelected(true);
			} 
		}
		depth.setText(Features.getDepth()+"");
		
		
		
		if(Features.isZeroTaken()) {
			zeroScoreTaken.setSelected(true);
		}
		if(Features.isOppositeTaken()) {
			oppositeTaken.setSelected(true);
		}

		
	}

	
	//======================================//
	//               OTHERS                 //
	//======================================//
	
	private boolean checkNumber() {
		boolean test = false;
		if (depth.getText().equals("")) {
			test = true;
		}
		try {
			Integer.parseInt(depth.getText());
			test = true;
		} catch (NumberFormatException e) {
			AlertBox.display("Error", "The \"Depth\" should be a number (integer)", "OK");
		}
		return test;
	}
}

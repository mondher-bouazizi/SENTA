package windows.features;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import main.items.Features;
import windows.main.SelectBasicFeaturesWindow;

public class StylisticFeaturesWindow implements Initializable {	
	
	//=================================//
	//         FXML COMPONENTS         //
	//=================================//
	
	@FXML Button defaultFeatures;
	@FXML Button allFeatures;
	@FXML Button clearFeatures;
	
	@FXML Button select;
	@FXML Button cancel;
	
	@FXML CheckBox numberOfNouns;
	@FXML CheckBox ratioOfNouns;
	@FXML CheckBox numberOfVerbs;
	@FXML CheckBox ratioOfVerbs;
	@FXML CheckBox numberOfAdjectives;
	@FXML CheckBox ratioOfAdjectives;
	@FXML CheckBox numberOfAdverbs;
	@FXML CheckBox ratioOfAdverbs;

	@FXML CheckBox useOfSymbols;
	@FXML CheckBox useOfComparativeForm;
	@FXML CheckBox useOfSuperlativeForm;
	@FXML CheckBox useOfProperNouns;

	@FXML CheckBox totalNumberOfParticles;
	@FXML CheckBox averageNumberOfParticles;
	@FXML CheckBox totalNumberOfInterjections;
	@FXML CheckBox averageNumberOfInterjections;
	@FXML CheckBox totalNumberOfPronouns;
	@FXML CheckBox averageNumberOfPronouns;
	@FXML CheckBox totalNumberOfPronounsGroup1;
	@FXML CheckBox averageNumberOfPronounsGroup1;
	@FXML CheckBox totalNumberOfPronounsGroup2;
	@FXML CheckBox averageNumberOfPronounsGroup2;
	@FXML CheckBox useOfNegation;
	@FXML CheckBox useOfUncommonWords;
	@FXML CheckBox numberOfUncommonWords;
	
	
	//=================================//
	//     FXML COMPONENTS ACTIONS     //
	//=================================//
	
	/**
	 * Handle the action of "Default" Button: Select the default features
	 */
	@FXML public void handleDefault() {
		System.out.println("Default clicked!");
		
		numberOfNouns.setSelected(false);
		ratioOfNouns.setSelected(true);
		numberOfVerbs.setSelected(false);
		ratioOfVerbs.setSelected(true);
		numberOfAdjectives.setSelected(false);
		ratioOfAdjectives.setSelected(true);
		numberOfAdverbs.setSelected(false);
		ratioOfAdverbs.setSelected(true);

		useOfSymbols.setSelected(false);
		useOfComparativeForm.setSelected(true);
		useOfSuperlativeForm.setSelected(true);
		useOfProperNouns.setSelected(false);

		totalNumberOfParticles.setSelected(true);
		averageNumberOfParticles.setSelected(false);
		totalNumberOfInterjections.setSelected(true);
		averageNumberOfInterjections.setSelected(false);
		totalNumberOfPronouns.setSelected(true);
		averageNumberOfPronouns.setSelected(false);
		totalNumberOfPronounsGroup1.setSelected(true);
		averageNumberOfPronounsGroup1.setSelected(false);
		totalNumberOfPronounsGroup2.setSelected(true);
		averageNumberOfPronounsGroup2.setSelected(false);
		useOfNegation.setSelected(true);
		useOfUncommonWords.setSelected(true);
		numberOfUncommonWords.setSelected(true);
		
	}
	
	/**
	 * Handle the action of "Select all" Button: Select all the features
	 */
	@FXML public void handleAll() {
		System.out.println("All clicked!");

		numberOfNouns.setSelected(true);
		ratioOfNouns.setSelected(true);
		numberOfVerbs.setSelected(true);
		ratioOfVerbs.setSelected(true);
		numberOfAdjectives.setSelected(true);
		ratioOfAdjectives.setSelected(true);
		numberOfAdverbs.setSelected(true);
		ratioOfAdverbs.setSelected(true);

		useOfSymbols.setSelected(true);
		useOfComparativeForm.setSelected(true);
		useOfSuperlativeForm.setSelected(true);
		useOfProperNouns.setSelected(true);

		totalNumberOfParticles.setSelected(true);
		averageNumberOfParticles.setSelected(true);
		totalNumberOfInterjections.setSelected(true);
		averageNumberOfInterjections.setSelected(true);
		totalNumberOfPronouns.setSelected(true);
		averageNumberOfPronouns.setSelected(true);
		totalNumberOfPronounsGroup1.setSelected(true);
		averageNumberOfPronounsGroup1.setSelected(true);
		totalNumberOfPronounsGroup2.setSelected(true);
		averageNumberOfPronounsGroup2.setSelected(true);
		useOfNegation.setSelected(true);
		useOfUncommonWords.setSelected(true);
		numberOfUncommonWords.setSelected(true);
		
	}
	
	/**
	 * Handle the action of "Clear" Button: Clear all the features
	 * TODO make it so if the parameters are cleared and the user clicks OK it works [for the different feature sets]
	 */
	@FXML public void handleClear() {
		System.out.println("Clear clicked!");
		
		numberOfNouns.setSelected(false);
		ratioOfNouns.setSelected(false);
		numberOfVerbs.setSelected(false);
		ratioOfVerbs.setSelected(false);
		numberOfAdjectives.setSelected(false);
		ratioOfAdjectives.setSelected(false);
		numberOfAdverbs.setSelected(false);
		ratioOfAdverbs.setSelected(false);

		useOfSymbols.setSelected(false);
		useOfComparativeForm.setSelected(false);
		useOfSuperlativeForm.setSelected(false);
		useOfProperNouns.setSelected(false);

		totalNumberOfParticles.setSelected(false);
		averageNumberOfParticles.setSelected(false);
		totalNumberOfInterjections.setSelected(false);
		averageNumberOfInterjections.setSelected(false);
		totalNumberOfPronouns.setSelected(false);
		averageNumberOfPronouns.setSelected(false);
		totalNumberOfPronounsGroup1.setSelected(false);
		averageNumberOfPronounsGroup1.setSelected(false);
		totalNumberOfPronounsGroup2.setSelected(false);
		averageNumberOfPronounsGroup2.setSelected(false);
		useOfNegation.setSelected(false);
		useOfUncommonWords.setSelected(false);
		numberOfUncommonWords.setSelected(false);

	}
	
	/**
	 * Handle the action of "Select" button
	 */
	@FXML public void handleSelect() {
		System.out.println("Select clicked!");
		
		int count = 0;
		
		// Number of Nouns
		if (numberOfNouns.isSelected()) {
			count++;
			Features.setNumberOfNouns(true);
		} else {
			Features.setNumberOfNouns(false);
		}
		// Ratio of Nouns
		if (ratioOfNouns.isSelected()) {
			count++;
			Features.setRatioOfNouns(true);
		} else {
			Features.setRatioOfNouns(false);
		}
		// Number of Verbs
		if (numberOfVerbs.isSelected()) {
			count++;
			Features.setNumberOfVerbs(true);
		} else {
			Features.setNumberOfVerbs(false);
		}
		// Ratio of Verbs
		if (ratioOfVerbs.isSelected()) {
			count++;
			Features.setRatioOfVerbs(true);
		} else {
			Features.setRatioOfVerbs(false);
		}
		// Number of Adjectives
		if (numberOfAdjectives.isSelected()) {
			count++;
			Features.setNumberOfAdjectives(true);
		} else {
			Features.setNumberOfAdjectives(false);
		}
		// Ratio of Adjectives
		if (ratioOfAdjectives.isSelected()) {
			count++;
			Features.setRatioOfAdjectives(true);
		} else {
			Features.setRatioOfAdjectives(false);
		}
		// Number of Adverbs
		if (numberOfAdverbs.isSelected()) {
			count++;
			Features.setNumberOfAdverbs(true);
		} else {
			Features.setNumberOfAdverbs(false);
		}
		// Ratio of Adverbs
		if (ratioOfAdverbs.isSelected()) {
			count++;
			Features.setRatioOfAdverbs(true);
		} else {
			Features.setRatioOfAdverbs(false);
		}

		
		// Use of Symbols
		if (useOfSymbols.isSelected()) {
			count++;
			Features.setUseOfSymbols(true);
		} else {
			Features.setUseOfSymbols(false);
		}
		// Use of Comparative Form
		if (useOfComparativeForm.isSelected()) {
			count++;
			Features.setUseOfComparativeForm(true);
		} else {
			Features.setUseOfComparativeForm(false);
		}
		// Use of Superlative Form
		if (useOfSuperlativeForm.isSelected()) {
			count++;
			Features.setUseOfSuperlativeForm(true);
		} else {
			Features.setUseOfSuperlativeForm(false);
		}
		// Use of Proper Nouns
		if (useOfProperNouns.isSelected()) {
			count++;
			Features.setUseOfProperNouns(true);
		} else {
			Features.setUseOfProperNouns(false);
		}
		

		// Total Number of Particles
		if (totalNumberOfParticles.isSelected()) {
			count++;
			Features.setTotalNumberOfParticles(true);
		} else {
			Features.setTotalNumberOfParticles(false);
		}
		// Average Number of Particles
		if (averageNumberOfParticles.isSelected()) {
			count++;
			Features.setAverageNumberOfParticles(true);
		} else {
			Features.setAverageNumberOfParticles(false);
		}
		// Total Number of Interjections
		if (totalNumberOfInterjections.isSelected()) {
			count++;
			Features.setTotalNumberOfInterjections(true);
		} else {
			Features.setTotalNumberOfInterjections(false);
		}
		// Average Number of Interjections
		if (averageNumberOfInterjections.isSelected()) {
			count++;
			Features.setAverageNumberOfInterjections(true);
		} else {
			Features.setAverageNumberOfInterjections(false);
		}
		// Total Number of Pronouns
		if (totalNumberOfPronouns.isSelected()) {
			count++;
			Features.setTotalNumberOfPronouns(true);
		} else {
			Features.setTotalNumberOfPronouns(false);
		}
		// Average Number of Pronouns
		if (averageNumberOfPronouns.isSelected()) {
			count++;
			Features.setAverageNumberOfPronouns(true);
		} else {
			Features.setAverageNumberOfPronouns(false);
		}
		// Total Number of Pronouns Group 1
		if (totalNumberOfPronounsGroup1.isSelected()) {
			count++;
			Features.setTotalNumberOfPronounsGroup1(true);
		} else {
			Features.setTotalNumberOfPronounsGroup1(false);
		}
		// Average Number of Pronouns Group1
		if (averageNumberOfPronounsGroup1.isSelected()) {
			count++;
			Features.setAverageNumberOfPronounsGroup1(true);
		} else {
			Features.setAverageNumberOfPronounsGroup1(false);
		}
		// Total Number of Pronouns Group2
		if (totalNumberOfPronounsGroup2.isSelected()) {
			count++;
			Features.setTotalNumberOfPronounsGroup2(true);
		} else {
			Features.setTotalNumberOfPronounsGroup2(false);
		}
		// Average Number of Pronouns Group2
		if (averageNumberOfPronounsGroup2.isSelected()) {
			count++;
			Features.setAverageNumberOfPronounsGroup2(true);
		} else {
			Features.setAverageNumberOfPronounsGroup2(false);
		}
		// Use of Negation
		if (useOfNegation.isSelected()) {
			count++;
			Features.setUseOfNegation(true);
		} else {
			Features.setUseOfNegation(false);
		}
		// Use of Uncommon Words
		if (useOfUncommonWords.isSelected()) {
			count++;
			Features.setUseOfUncommonWords(true);
		} else {
			Features.setUseOfUncommonWords(false);
		}
		// Number of Uncommon Words
		if (numberOfUncommonWords.isSelected()) {
			count++;
			Features.setNumberOfUncommonWords(true);
		} else {
			Features.setNumberOfUncommonWords(false);
		}
		
		SelectBasicFeaturesWindow.setNumberOfStylisticFeatures(count);
		SelectBasicFeaturesWindow.countOfStylisticFeatures.set(count);
		FeaturesCustumization.stage.close();
	}
	
	/**
	 * Handle the action of "Cancel" Button
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
		numberOfNouns.setSelected(Features.isNumberOfNouns());
		ratioOfNouns.setSelected(Features.isRatioOfNouns());
		numberOfVerbs.setSelected(Features.isNumberOfVerbs());
		ratioOfVerbs.setSelected(Features.isRatioOfVerbs());
		numberOfAdjectives.setSelected(Features.isNumberOfAdjectives());
		ratioOfAdjectives.setSelected(Features.isRatioOfAdjectives());
		numberOfAdverbs.setSelected(Features.isNumberOfAdverbs());
		ratioOfAdverbs.setSelected(Features.isRatioOfAdverbs());
		
		useOfSymbols.setSelected(Features.isUseOfSymbols());
		useOfComparativeForm.setSelected(Features.isUseOfComparativeForm());
		useOfSuperlativeForm.setSelected(Features.isUseOfSuperlativeForm());
		useOfProperNouns.setSelected(Features.isUseOfProperNouns());
		
		totalNumberOfParticles.setSelected(Features.isTotalNumberOfParticles());
		averageNumberOfParticles.setSelected(Features.isAverageNumberOfParticles());
		totalNumberOfInterjections.setSelected(Features.isTotalNumberOfInterjections());
		averageNumberOfInterjections.setSelected(Features.isAverageNumberOfInterjections());
		totalNumberOfPronouns.setSelected(Features.isTotalNumberOfPronouns());
		averageNumberOfPronouns.setSelected(Features.isAverageNumberOfPronouns());
		totalNumberOfPronounsGroup1.setSelected(Features.isTotalNumberOfPronounsGroup1());
		averageNumberOfPronounsGroup1.setSelected(Features.isAverageNumberOfPronounsGroup1());
		totalNumberOfPronounsGroup2.setSelected(Features.isTotalNumberOfPronounsGroup2());
		averageNumberOfPronounsGroup2.setSelected(Features.isAverageNumberOfPronounsGroup2());
		useOfNegation.setSelected(Features.isUseOfNegation());
		useOfUncommonWords.setSelected(Features.isUseOfUncommonWords());
		numberOfUncommonWords.setSelected(Features.isNumberOfUncommonWords());
	}

}

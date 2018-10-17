package windows.features;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import main.items.Features;
import main.items.Parameters;
import windows.features.customunigrams.CustomizeUnigrams;
import windows.main.SelectAdvancedFeaturesWindow;
import windows.others.AlertBox;

public class AdvancedPatternFeaturesWindow implements Initializable {
	
	//=================================//
	//       NON-FXML COMPONENTS       //
	
	private enum Error {
		Type,
		Pattern_Length,
		Number_Of_Patterns,
		Pattern_Min_Length,
		Pattern_Max_Length,
		Min_Occ,
		Alpha,
		Gamma,
		Knn,
		Fine
	}
	
	//=================================//
	
	//=================================//
	//         FXML COMPONENTS         //
	//=================================//
	
	// Patterns type-related parameters
	@FXML ToggleGroup typeOfFeature;
	@FXML RadioButton uniqueFeatures;
	@FXML RadioButton summedFeatures;
	
	@FXML ComboBox<Integer> patternLength;
	@FXML TextField numberOfPatternsPerClass;
	
	@FXML ComboBox<Integer> minPatternLength;
	@FXML ComboBox<Integer> maxPatternLength;
	
	// Parameters
	@FXML TextField patternsMinOccurrence;
	@FXML TextField alpha;
	@FXML TextField gamma;
	@FXML TextField knn;
	
	// Buttons
	@FXML Button importBasicConfig;
	@FXML Button manageSeedWords;
	
	@FXML Button defaultFeatures;
	@FXML Button allFeatures;
	@FXML Button clearFeatures;
	
	@FXML Button select;
	@FXML Button cancel;
	
	
	//=================================//
	//     FXML COMPONENTS ACTIONS     //
	//=================================//
	
	/**
	 * Handles the case where the [Unique Features] Radio is selected
	 */
	@FXML public void handleUniqueFeaturesSelection() {
		patternLength.setDisable(false);
		numberOfPatternsPerClass.setDisable(false);
		minPatternLength.setDisable(true);
		maxPatternLength.setDisable(true);
		knn.setDisable(true);
	}
	
	/**
	 * Handles the case where the [Summed Features] Radio is selected
	 */
	@FXML public void handleSummedFeaturesSelection() {
		patternLength.setDisable(true);
		numberOfPatternsPerClass.setDisable(true);
		minPatternLength.setDisable(false);
		maxPatternLength.setDisable(false);
		knn.setDisable(false);
	}

	/**
	 * Select the default features
	 */
	@FXML public void handleDefault() {
		System.out.println("Default clicked!");
		
		uniqueFeatures.setSelected(false);
		patternLength.setValue(7);
		patternLength.setDisable(true);
		numberOfPatternsPerClass.setText("500");
		numberOfPatternsPerClass.setDisable(true);
		
		summedFeatures.setSelected(true);
		minPatternLength.setValue(3);
		minPatternLength.setDisable(false);
		maxPatternLength.setValue(10);
		maxPatternLength.setDisable(false);
		
		patternsMinOccurrence.setText("3");
		alpha.setText("0.1");
		gamma.setText("0.02");
		knn.setText("5");
		knn.setDisable(false);
	}
	
	/**
	 * Select all the features
	 */
	@FXML public void handleAll() {
		System.out.println("All clicked!");
		
		uniqueFeatures.setSelected(true);
		patternLength.setValue(7);
		patternLength.setDisable(false);
		numberOfPatternsPerClass.setText("500");
		numberOfPatternsPerClass.setDisable(false);
		
		summedFeatures.setSelected(false);
		minPatternLength.setValue(3);
		minPatternLength.setDisable(true);
		maxPatternLength.setValue(10);
		maxPatternLength.setDisable(true);
		
		patternsMinOccurrence.setText("3");
		alpha.setText("0.1");
		gamma.setText("0.02");
		knn.setText("5");
		knn.setDisable(false);
	}
	
	/**
	 * Clear all the features
	 */
	@FXML public void handleClear() {
		System.out.println("Clear clicked!");
		
		uniqueFeatures.setSelected(false);
		patternLength.setValue(null);
		patternLength.setValue(null);
		patternLength.setDisable(false);
		numberOfPatternsPerClass.setText("");
		numberOfPatternsPerClass.setDisable(false);
		
		summedFeatures.setSelected(false);
		minPatternLength.setValue(null);
		minPatternLength.setDisable(false);
		maxPatternLength.setValue(null);
		maxPatternLength.setDisable(false);
		
		patternsMinOccurrence.setText("");
		alpha.setText("");
		gamma.setText("");
		knn.setText("");
		knn.setDisable(false);
		
	}
	
	/**
	 * Handle the import of the same set of parameters as the [Basic Pattern Features]
	 */
	@FXML public void handleImportBasicConfig() {
		System.out.println("Import basic configuration clicked!");
		if (Features.getPatternFeaturesType()!=null) {
			if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
				uniqueFeatures.setSelected(false);
				patternLength.setValue(Features.getPatternLength());
				patternLength.setDisable(true);
				numberOfPatternsPerClass.setText(Features.getNumberOfPatternsPerClass() + "");
				numberOfPatternsPerClass.setDisable(true);
				summedFeatures.setSelected(true);
				minPatternLength.setValue(Features.getMinPatternLength());
				minPatternLength.setDisable(false);
				maxPatternLength.setValue(Features.getMaxPatternLength());
				maxPatternLength.setDisable(false);
				
				patternsMinOccurrence.setText(Features.getPatternsMinOccurrence() + "");
				alpha.setText(Features.getAlpha() + "");
				gamma.setText(Features.getGamma() + "");
				knn.setText(Features.getBasicPatternsKnn() + "");
			}
			
			if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
				uniqueFeatures.setSelected(true);
				patternLength.setValue(Features.getPatternLength());
				patternLength.setDisable(false);
				numberOfPatternsPerClass.setText(Features.getNumberOfPatternsPerClass() + "");
				numberOfPatternsPerClass.setDisable(false);
				summedFeatures.setSelected(false);
				minPatternLength.setValue(Features.getMinPatternLength());
				minPatternLength.setDisable(true);
				maxPatternLength.setValue(Features.getMaxPatternLength());
				maxPatternLength.setDisable(true);
				
				patternsMinOccurrence.setText(Features.getPatternsMinOccurrence() + "");
				alpha.setText(Features.getAlpha() + "");
				gamma.setText(Features.getGamma() + "");
			}
		}
		
	}
	
	/**
	 * Open a window to manage the seed words [If the unigram features have been set, these seed words should have been already added]
	 */
	@FXML public void handleManageSeedWords() {
		System.out.println("Manage seed words clicked!");
		CustomizeUnigrams.customize();
	}
	
	/**
	 * Handle the [Select] button
	 */
	@FXML public void handleSelect() {
		System.out.println("Select clicked!");
		
		int c3 = Parameters.getClasses().size();
		
		int count = 0;
		
		String title = "Error";
		String key = "OK";
		
		if (checkParameters().equals(Error.Type)) {
			AlertBox.display(title, "Please choose how your features are to be counted Pattern Type", key);
		} else if (checkParameters().equals(Error.Pattern_Length)) {
			AlertBox.display(title, "Please choose a pattern length", key);
		} else if (checkParameters().equals(Error.Pattern_Min_Length) || checkParameters().equals(Error.Pattern_Max_Length)) {
			AlertBox.display(title, "Please choose the min and max length of patterns", key);
		} else if (checkParameters().equals(Error.Knn)) {
			AlertBox.display(title, "Please choose a valid value for the parameter \"knn\"", key);
		} else if (checkParameters().equals(Error.Min_Occ)) {
			AlertBox.display(title, "Please choose a valid value for the parameter \"Min_Occ\"", key);
		} else if (checkParameters().equals(Error.Alpha)) {
			AlertBox.display(title, "Please choose a valid value for the parameter \"alpha\"", key);
		} else if (checkParameters().equals(Error.Gamma)) {
				AlertBox.display(title, "Please choose a valid value for the parameter \"gamma\"", key);
		} else if (checkParameters().equals(Error.Fine)) {
			
			// Set the values of the Features parameters
			if (uniqueFeatures.isSelected()) {
				Features.setAdvancedPatternFeaturesType(Features.TypeOfFeature.UNIQUE);
				Features.setAdvancedPatternLength(patternLength.getValue());
				Features.setAdvancedNumberOfPatternsPerClass(Integer.parseInt(numberOfPatternsPerClass.getText()));
			} else if (summedFeatures.isSelected()) {
				Features.setAdvancedPatternFeaturesType(Features.TypeOfFeature.SUMMED);
				Features.setAdvancedMinPatternLength(minPatternLength.getValue());
				Features.setAdvancedMaxPatternLength(maxPatternLength.getValue());

				int noPatterns = 0;
				if (checkInteger(numberOfPatternsPerClass.getText())) {
					noPatterns = Integer.parseInt(numberOfPatternsPerClass.getText());
				}
				Features.setAdvancedNumberOfPatternsPerClass(noPatterns);
				int knnVal = 0;
				if (checkInteger(knn.getText())) {
					knnVal = Integer.parseInt(knn.getText());
				}
				Features.setAdvancedPatternsKnn(knnVal);
			}
			
			Features.setAdvancedPatternsMinOccurrence(Integer.parseInt(patternsMinOccurrence.getText()));
			Features.setAdvancedPatternAlpha(Double.parseDouble(alpha.getText()));
			Features.setAdvancedPatternGamma(Double.parseDouble(gamma.getText()));

			
			if (summedFeatures.isSelected()) {
				count = c3 * (maxPatternLength.getValue() - minPatternLength.getValue() + 1);
			} else {
				count = c3 *Integer.parseInt(numberOfPatternsPerClass.getText());
			}
			SelectAdvancedFeaturesWindow.setNumberOfPatternFeatures(count);
			SelectAdvancedFeaturesWindow.countOfPatternFeatures.set(count);
			FeaturesCustumization.stage.close();
		}
	}
	
	/**
	 * Handle the [Cancel} button
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
		
		if(patternLength.getItems().isEmpty()) {
			patternLength.getItems().addAll(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
		}
		
		if(minPatternLength.getItems().isEmpty()) {
			minPatternLength.getItems().addAll(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
		}
		
		if(maxPatternLength.getItems().isEmpty()) {
			maxPatternLength.getItems().addAll(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
		}
		
		
		if (Features.getAdvancedPatternFeaturesType()!=null) {
			if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
				uniqueFeatures.setSelected(true);
			} else if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
				summedFeatures.setSelected(true);
			} 
		}
		
		if (Features.getAdvancedPatternLength()!=0) {
			patternLength.setValue(Features.getAdvancedPatternLength());
		}
		if (Features.getAdvancedNumberOfPatternsPerClass()!=0) {
			numberOfPatternsPerClass.setText(Features.getAdvancedNumberOfPatternsPerClass() + "");
		}
		if (Features.getAdvancedMinPatternLength()!=0) {
			minPatternLength.setValue(Features.getAdvancedMinPatternLength());
		}
		if (Features.getAdvancedMaxPatternLength()!=0) {
			maxPatternLength.setValue(Features.getAdvancedMaxPatternLength());
		}
		
		if (Features.getAdvancedPatternsKnn() !=0) {
			knn.setText(Features.getAdvancedPatternsKnn() + "");
		}
		
		if(uniqueFeatures.isSelected()) {
			patternLength.setDisable(false);
			numberOfPatternsPerClass.setDisable(false);
		} else {
			patternLength.setDisable(true);
			numberOfPatternsPerClass.setDisable(true);
		}
		
		if (summedFeatures.isSelected()) {
			minPatternLength.setDisable(false);
			maxPatternLength.setDisable(false);
		} else {
			minPatternLength.setDisable(true);
			maxPatternLength.setDisable(true);
		}
		
		if (Features.getAdvancedPatternsMinOccurrence()!=0) {
			patternsMinOccurrence.setText(Features.getAdvancedPatternsMinOccurrence()+"");
		}
		
		if (Features.getAdvancedPatternAlpha()!=0) {
			alpha.setText(Features.getAdvancedPatternAlpha()+"");
		}
		
		if (Features.getAdvancedPatternGamma()!=0) {
			gamma.setText(Features.getAdvancedPatternGamma()+"");
		}
		
	}

	
	//=================================//
	//         PRIVATE METHODS         //
	//=================================//
	
	/**
	 * Check if the different parameters entered are fine
	 * TODO add an enumeration so that the different error codes are in the enum
	 * @return
	 */
	private Error checkParameters() {
		
		if (!uniqueFeatures.isSelected() && !summedFeatures.isSelected()) {
			return Error.Type;
		}
		
		if (uniqueFeatures.isSelected()) {
			if (patternLength.getValue()==null || patternLength.getValue().equals("0")) {
				return Error.Pattern_Length;
			}
				
			if (!checkInteger(numberOfPatternsPerClass.getText()) || numberOfPatternsPerClass.getText()==null || numberOfPatternsPerClass.getText().equals("0")) {
				return Error.Number_Of_Patterns;
			}
			
		}
		
		
		if (summedFeatures.isSelected()) {
			if (minPatternLength.getValue()==null || minPatternLength.getValue().equals("0")) {
					return Error.Pattern_Min_Length;
			}
			if (maxPatternLength.getValue()==null || maxPatternLength.getValue().equals("0")) {
				return Error.Pattern_Max_Length;
			}
			if (!checkInteger(knn.getText()) || knn.getText()==null ||  knn.getText().equals("0")) {
				return Error.Knn;
			}
		}
		
		if (!checkInteger(patternsMinOccurrence.getText()) || patternsMinOccurrence.getText()==null ||  patternsMinOccurrence.getText().equals("0")) {
			return Error.Min_Occ;
		}
		
		if (!checkDouble(alpha.getText()) || alpha.getText()==null ||  alpha.getText().equals("0")) {
			return Error.Alpha;
		}
		
		if (!checkDouble(gamma.getText()) || gamma.getText()==null ||  gamma.getText().equals("0")) {
			return Error.Gamma;
		}
		
		return Error.Fine;
		
	}
	
	
	
	/**
	 * Check if a text entered in a TextFiled corresponds to a numbers [int]
	 * TODO change in the {initialize} method so that only numbers can be put
	 * @return
	 */
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
	
	/**
	 * Check if a text entered in a TextFiled corresponds to a numbers [double]
	 * TODO change in the {initialize} method so that only numbers and one dot [.] can be put
	 * @return
	 */
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

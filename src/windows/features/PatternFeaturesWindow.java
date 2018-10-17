package windows.features;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import main.items.Features;
import main.items.Parameters;
import windows.features.custompatterns.CustomizePatterns;
import windows.features.custompatterns.CustomizePos;
import windows.main.SelectBasicFeaturesWindow;
import windows.others.AlertBox;

public class PatternFeaturesWindow implements Initializable {
	
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
	
	// Customize rules
	@FXML ComboBox<Integer> numberOfPosCategories;
	@FXML Button cuztomizePosActions;
	@FXML Button definePosCategory;
	@FXML Label cuztomizationValidity;
	
	// Parameters
	@FXML TextField patternsMinOccurrence;
	@FXML TextField alpha;
	@FXML TextField gamma;
	@FXML TextField knn;
	
	// Buttons
	@FXML Button defaultFeatures;
	@FXML Button allFeatures;
	@FXML Button clearFeatures;
	
	@FXML Button select;
	@FXML Button cancel;
	
	
	//=================================//
	//     FXML COMPONENTS ACTIONS     //
	//=================================//
	
	/**
	 * Handle the selection of the [Unique Features] radio
	 */
	@FXML public void handleUniqueFeaturesSelection() {
		patternLength.setDisable(false);
		numberOfPatternsPerClass.setDisable(false);
		minPatternLength.setDisable(true);
		maxPatternLength.setDisable(true);
		knn.setDisable(true);
	}
	
	/**
	 * Handle the selection of the [Summed Features] radio
	 */
	@FXML public void handleSummedFeaturesSelection() {
		patternLength.setDisable(true);
		numberOfPatternsPerClass.setDisable(true);
		minPatternLength.setDisable(false);
		maxPatternLength.setDisable(false);
		knn.setDisable(false);
	}
	
	/**
	 * Handle the customization of PoS tags actions
	 */
	@FXML public void handleCuztomizePosActions() {
		if (numberOfPosCategories.getValue()==null || numberOfPosCategories.getValue()==0) {
			AlertBox.display("Error", "Choose the number of Categories first", "OK");
		} else {
			CustomizePatterns.customize(numberOfPosCategories.getValue());
		}
	}
	
	/**
	 * Handle the definition of PoS tags categories
	 */
	@FXML public void handleDefinePosCategory() {
		if (numberOfPosCategories.getValue()==null || numberOfPosCategories.getValue()==0) {
			AlertBox.display("Error", "Choose the number of Categories first", "OK");
		} else {
			CustomizePos.customize(numberOfPosCategories.getValue());
		}
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
		
		numberOfPosCategories.setValue(3);
		
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
		
		numberOfPosCategories.setValue(3);
		
		patternsMinOccurrence.setText("3");
		alpha.setText("0.1");
		gamma.setText("0.02");
		knn.setText("5");
		knn.setDisable(false);
		
	}
	
	/**
	 * Clear all the features (parameters)
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
		
		numberOfPosCategories.setValue(null);
		
		patternsMinOccurrence.setText("");
		alpha.setText("");
		gamma.setText("");
		knn.setText("");
		knn.setDisable(false);
	}
	
	/**
	 * Handle the [Select] button
	 */
	@FXML public void handleSelect() {
		System.out.println("Select clicked!");
		
		int c3 = Parameters.getClasses().size();
		int c1 = 0;
		
		int count = 0;
		
		String title = "Error";
		String key = "OK";
		
		if (checkParameters().equals("type")) {
			AlertBox.display(title, "Please choose how your features are to be counted Pattern Type", key);
		} else if (checkParameters().equals("patternLength")) {
			AlertBox.display(title, "Please choose a pattern length", key);
		} else if (checkParameters().equals("patternMinMaxLength")) {
			AlertBox.display(title, "Please choose the min and max length of patterns", key);
		} else if (checkParameters().equals("categoriesNumber")) {
			AlertBox.display(title, "Please choose the number of categories", key);
		} else if (checkParameters().equals("actions")) {
			AlertBox.display(title, "Please specify the action to perform for each category", key);
		} else if (checkParameters().equals("categories")) {
			AlertBox.display(title, "Please set for each PoS-Tag the corresponding category", key);
		} else if (checkParameters().equals("parameters")) {
			AlertBox.display(title, "Please choose valid parameters (min # of occurrences, alpha and gamma)", key);
		} else if (checkParameters().equals("numberOfPatterns")) {
			AlertBox.display(title, "Please choose valid number of patterns per class", key);
		} else if (checkParameters().equals("fine")) {
			
			// Set the values of the Features parameters
			if (uniqueFeatures.isSelected()) {
				Features.setPatternFeaturesType(Features.TypeOfFeature.UNIQUE);
				System.out.println("############## " + Features.TypeOfFeature.UNIQUE + " ##############" );
				Features.setPatternLength(patternLength.getValue());
				Features.setNumberOfPatternsPerClass(Integer.parseInt(numberOfPatternsPerClass.getText()));
				// TODO in case of import, check if this is really necessary
				if (minPatternLength.getValue()!=null && !minPatternLength.getValue().equals("")
					&& maxPatternLength.getValue()!=null && !maxPatternLength.getValue().equals("")) {
					Features.setMinPatternLength(minPatternLength.getValue());
					Features.setMaxPatternLength(maxPatternLength.getValue());
				}
			} else if (summedFeatures.isSelected()) {
				Features.setPatternFeaturesType(Features.TypeOfFeature.SUMMED);
				System.out.println("############## " + Features.TypeOfFeature.SUMMED + " ##############" );
				Features.setMinPatternLength(minPatternLength.getValue());
				Features.setMaxPatternLength(maxPatternLength.getValue());
				
				if (patternLength.getValue()!=null && !patternLength.getValue().equals("")) {
					Features.setPatternLength(patternLength.getValue());
				}
				
				int noPatterns = 0;
				if (checkInteger(numberOfPatternsPerClass.getText())) {
					noPatterns = Integer.parseInt(numberOfPatternsPerClass.getText());
				}
				Features.setNumberOfPatternsPerClass(noPatterns);
				
			}
			
			Features.setNumberOfPosCategories(numberOfPosCategories.getValue());
			Features.setPatternsMinOccurrence(Integer.parseInt(patternsMinOccurrence.getText()));
			Features.setAlpha(Double.parseDouble(alpha.getText()));
			Features.setGamma(Double.parseDouble(gamma.getText()));
			Features.setBasicPatternsKnn(Integer.parseInt(knn.getText()));
			
			if (summedFeatures.isSelected()) {
				count = c3 * (maxPatternLength.getValue() - minPatternLength.getValue() + 1);
			} else {
				c1 = Integer.parseInt(numberOfPatternsPerClass.getText());
				count = c3 *c1;
			}
			SelectBasicFeaturesWindow.setNumberOfPatternFeatures(count);
			SelectBasicFeaturesWindow.countOfPatternFeatures.set(count);
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
		
		if(numberOfPosCategories.getItems().isEmpty()) {
			numberOfPosCategories.getItems().addAll(1, 2, 3, 4);
		}
		
		
		
		if (Features.getPatternFeaturesType()!=null) {
			if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
				uniqueFeatures.setSelected(true);
			} else if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
				summedFeatures.setSelected(true);
			} 
		}
		
		if (Features.getPatternLength()!=0) {
			patternLength.setValue(Features.getPatternLength());
		}
		if (Features.getNumberOfPatternsPerClass()!=0) {
			numberOfPatternsPerClass.setText(Features.getNumberOfPatternsPerClass() + "");
		}
		
		if (Features.getMinPatternLength()!=0) {
			minPatternLength.setValue(Features.getMinPatternLength());
		}
		if (Features.getMaxPatternLength()!=0) {
			maxPatternLength.setValue(Features.getMaxPatternLength());
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
		
		if (Features.getNumberOfPosCategories()!=0) {
			numberOfPosCategories.setValue(Features.getNumberOfPosCategories());
		}
		
		if (Features.getPatternsMinOccurrence()!=0) {
			patternsMinOccurrence.setText(Features.getPatternsMinOccurrence()+"");
		}
		
		if (Features.getAlpha()!=0) {
			alpha.setText(Features.getAlpha()+"");
		}
		
		if (Features.getGamma()!=0) {
			gamma.setText(Features.getGamma()+"");
		}
		
		if (Features.getBasicPatternsKnn()!=0) {
			knn.setText(Features.getBasicPatternsKnn()+"");
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
	private String checkParameters() {

		String errorCode = "fine";
		
		if (!uniqueFeatures.isSelected() && !summedFeatures.isSelected()) {
			return "type";
		} else {
			if (uniqueFeatures.isSelected()) {
				if (patternLength.getValue()==null || patternLength.getValue().equals("0")) {
					return "patternLength";
				}
								
				if (!checkInteger(numberOfPatternsPerClass.getText()) || numberOfPatternsPerClass.getText()==null || numberOfPatternsPerClass.getText().equals("0")) {
					return "numberOfPatterns";
				}
			}
			
			if (summedFeatures.isSelected()) {
				if (minPatternLength.getValue()==null || minPatternLength.getValue().equals("0") ||
						maxPatternLength.getValue()==null || maxPatternLength.getValue().equals("0")) {
					return "patternMinMaxLength";
				}
			}
		}
		
		if (numberOfPosCategories.getValue()==null || numberOfPosCategories.getValue().equals("0")) {
			return "categoriesNumber";
		}
		
		if (numberOfPosCategories.getValue()==1) {
			if (Features.getAction1()==null) {
				return "actions";
			}
		}
		
		if (numberOfPosCategories.getValue()==2) {
			if (Features.getAction1()==null || Features.getAction2()==null) {
				return "actions";
			}
		}
		
		if (numberOfPosCategories.getValue()==3) {
			if (Features.getAction1()==null || Features.getAction2()==null || Features.getAction3()==null) {
				return "actions";
			}
		}
		
		if (numberOfPosCategories.getValue()==4) {
			if (Features.getAction1()==null || Features.getAction2()==null || Features.getAction3()==null || Features.getAction4()==null ) {
				return "actions";
			}
		}
		
		if (Features.getCategories()==null) {
			return "categories";
		} else {
			for (Features.Categories cat : Features.getCategories()) {
				if (cat==null) {
					return "categories";
				}
			}
		}
		
		if (!checkNumbers()) {
			return "parameters";
		}
		
		
		return errorCode;
		
	}
	
	/**
	 * Check whether the number are correctly specified
	 * @return
	 */
	private boolean checkNumbers() {
		
		if (!checkInteger(patternsMinOccurrence.getText())) {
			AlertBox.display("Error", "The \"Min # of Occurrences\" should be a number (integer)", "OK");
			return false;
		}

		
		if (!checkDouble(alpha.getText())) {
			AlertBox.display("Error", "The parameter \"alpha\" should be a number (decimal)", "OK");
			return false;
		}
		
		if (!checkDouble(gamma.getText())) {
			AlertBox.display("Error", "The parameter \"gamma\" should be a number (decimal)", "OK");
			return false;
		}
		
		if (!checkDouble(knn.getText())) {
			AlertBox.display("Error", "The parameter \"knn\" should be a number (integer)", "OK");
			return false;
		}
		
		return true;
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

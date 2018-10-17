package windows.features;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import commons.io.Reader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import main.items.Features;
import main.items.Parameters;
import main.start.Main;
import windows.main.SelectAdvancedFeaturesWindow;
import windows.others.AlertBox;

public class AdvancedUnigramFeaturesWindow implements Initializable {
	
	// =====================================//
	//         NON-FXML COMPONENTS          //
	// =====================================//
	
	private static enum Error {
		Radios,
		File_Not_Found,
		File_Field_Empty,
		Fine
	}
	

	// =====================================//
	//           FXML COMPONENTS            //
	// =====================================//
	
	@FXML Button select;
	@FXML Button cancel;
	
	@FXML Button SelectFileButton;
	
	@FXML RadioButton lemmasRadio;
	@FXML RadioButton wordsRadio;
	
	@FXML TextField fileLocationField;
	
	
	// =====================================//
	//        FXML COMPONENTS ACTIONS       //
	// =====================================//
	
	/**
	 * Handle the [Select] button
	 */
	@FXML public void handleSelectFile() {
		System.out.println("File location \"select\" clicked");
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text file", "*.txt"), new ExtensionFilter("CSV", "*.csv"), new ExtensionFilter("All files", "*.*"));
		fileChooser.setTitle("Please specify which file you want to open");
		try {
			String fileToOpen = fileChooser.showOpenDialog(Main.primaryStage).getPath();
			fileLocationField.setText(fileToOpen);
		} catch (NullPointerException exepction) {
			System.out.println(exepction.getMessage());
		}
	}
	
	
	/**
	 * Handle the [Select] button
	 */
	@FXML public void handleSelect() {
		System.out.println("Select clicked!");
		
		int count = 0;
		
		if (checkParameters().equals(Error.Radios)) {
			AlertBox.display("Error", "Please choose whether you want to use lemmas or words as they are.", "OK");
		} else if (checkParameters().equals(Error.File_Field_Empty)) {
			AlertBox.display("Error", "Please choose the file you want to use as a reference for you unigrams.", "OK");
		} else if (checkParameters().equals(Error.File_Not_Found)) {
			AlertBox.display("Error", "The file you have chosen does not exist, please make sure to use a valid file.", "OK");
		} else if (checkParameters().equals(Error.Fine)) {
			// File location
			Parameters.setImportedUnigramsLocation(fileLocationField.getText());
			if (lemmasRadio.isSelected()) {
				Features.setIsLemma(Features.IsLemma.Lemmas);
			} else if (wordsRadio.isSelected()) {
				Features.setIsLemma(Features.IsLemma.Words);
			}
			
			if (Parameters.getImportedUnigramsLocation() != null) {
				count = Reader.countLines(Parameters.getImportedUnigramsLocation());
			}
			
			SelectAdvancedFeaturesWindow.setNumberOfUnigramFeatures(count);
			SelectAdvancedFeaturesWindow.countOfUnigramFeatures.set(count);
			
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
	 * Initializes the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Type of sentiment features
		if (Features.getIsLemma()!=null) {
			if (Features.getIsLemma().equals(Features.IsLemma.Lemmas)) {
				lemmasRadio.setSelected(true);
				wordsRadio.setSelected(false);
			} else if (Features.getIsLemma().equals(Features.IsLemma.Words)) {
				lemmasRadio.setSelected(false);
				wordsRadio.setSelected(true);
			}
		}
		
		// File location
		if (Parameters.getImportedUnigramsLocation() != null) {
			fileLocationField.setText(Parameters.getImportedUnigramsLocation());
		}
		
	}
	
	
	//=================================//
	//         PRIVATE METHODS         //
	//=================================//
	
	/**
	 * Checks whether the parameters entered are correct
	 * TODO add an enumeration so that the different error codes are in the enum
	 * @return
	 */
	private Error checkParameters() {
		
		if (!lemmasRadio.isSelected() && !wordsRadio.isSelected()) {
			return Error.Radios;
		}
		
		if (fileLocationField.getText().isEmpty()) {
			return Error.File_Field_Empty;
		}
		
		File file = new File(fileLocationField.getText());
		
		if (!file.exists() || file.isDirectory()) {
			return Error.File_Not_Found;
		}
		
		return Error.Fine;
	}

}

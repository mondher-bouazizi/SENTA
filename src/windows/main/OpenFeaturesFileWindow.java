package windows.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import commons.io.Reader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import main.items.ImportedFeatures;
import main.items.Parameters;
import main.items.Parameters.TypeOfProject;
import main.start.Main;
import windows.others.AlertBox;
import windows.others.ConfirmBox;

public class OpenFeaturesFileWindow implements Initializable {
	
	// =====================================//
	//          Non-FXML Components         //
	// =====================================//
	
	private static enum ErrorCode {
		No_Type_Selected,
		No_Set_Selected,
		Training_File,
		Test_File,
		Training_Features_File,
		Test_Features_File,
		Incompatible,
		ID_Field,
		Fine
	}
	
		
	// =====================================//
	//            FXML Components           //
	// =====================================//
	
	@FXML ToggleGroup typeOfFile;
	
	@FXML RadioButton wekaFileType;
	@FXML RadioButton textFileType;
	@FXML RadioButton csvFileType;
	
	@FXML TextField trainingSetLocation;
	@FXML Button trainingSetSelect;
	@FXML Label trainingSetStatus;
	
	@FXML ToggleGroup typeOfSet;
	@FXML RadioButton testSet;
	@FXML RadioButton nonAnnotatedSet;
	@FXML TextField testSetLocation;
	@FXML Button testSetSelect;
	@FXML Label testSetStatus;
	
	@FXML Button loadTweets;
	
	@FXML TextField trainingSetFeaturesLocation;
	@FXML Button trainingSetFeaturesSelect;
	@FXML Label trainingSetFeaturesStatus;
	
	@FXML TextField testSetFeaturesLocation;
	@FXML Button testSetFeaturesSelect;
	@FXML Label testSetFeaturesStatus;
	
	@FXML Button collectFeatures;
	
	@FXML ComboBox<String> fields;
	
	@FXML Button backButton;
	@FXML Button nextButton;
	@FXML Button cancelButton;
	
	
	// =====================================//
	//        FXML Components actions       //
	// =====================================//
	
	@FXML public void handleLoadTweetsButton() {
		System.out.println("Load Tweets Button clicked");

		if (checkFirstStep().equals(ErrorCode.No_Type_Selected)) {
			AlertBox.display("Specify the type of file", "You need to specify the type of file you want to import first", "OK");
		} else if (checkFirstStep().equals(ErrorCode.Training_File)) {
			AlertBox.display("Choose the training set file", "You need to specify the location of the the file that contains your training tweets", "OK");
		} else if (checkFirstStep().equals(ErrorCode.Test_File)) {
			AlertBox.display("Choose the test/non-annotated set file", "You need to specify the location of the the file that contains your test/non-annotated tweets", "OK");
		} else if (checkFirstStep().equals(ErrorCode.No_Set_Selected)) {
			AlertBox.display("Specify the type of set", "You need to specify the of set (test/non-annotated) you want to import first", "OK");
		} else if (checkFirstStep().equals(ErrorCode.Fine)) {
			
			Reader.getAttributePositions(trainingSetLocation.getText());
			Parameters.setClasses(Reader.getClasses(trainingSetLocation.getText()));
			
			// Training set
			Parameters.setTrainingSetLocation(trainingSetLocation.getText());
			Parameters.setTrainingSet(Reader.readRawFile(trainingSetLocation.getText(), true));

			// Test set
			if (testSet.isSelected()) {
				Parameters.setTypeOfProject(TypeOfProject.TESTSET);
				Parameters.setTestSetLocation(testSetLocation.getText());
				Parameters.setTestSet(Reader.readRawFile(testSetLocation.getText(), true));
				
				Parameters.setNonAnnotatedDataLocation(null);
				Parameters.setUnknownSet(null);
			}
			
			// Non annotated set
			if (nonAnnotatedSet.isSelected()) {
				Parameters.setTypeOfProject(TypeOfProject.NONANNOTATEDSET);
				Parameters.setNonAnnotatedDataLocation(testSetLocation.getText());
				Parameters.setUnknownSet(Reader.readRawFile(testSetLocation.getText(), false));
				
				Parameters.setTestSetLocation(null);
				Parameters.setTestSet(null);
			}
		}
		
	}
	
	@FXML public void handleCollectFeaturesButton() {
		System.out.println("Collect Features Button clicked");
		
		if (checkSecondStep().equals(ErrorCode.Training_Features_File)) {
			AlertBox.display("Choose the training set features file", "You need to specify the location of the the file that contains the extra features to be added to the training tweets", "OK");
		} else if (checkSecondStep().equals(ErrorCode.Test_Features_File)) {
			if (testSet.isSelected()) {
				AlertBox.display("Choose the test set features file", "You need to specify the location of the the file that contains the extra features to be added to the test tweets", "OK");
			} else if (nonAnnotatedSet.isSelected()) {
				AlertBox.display("Choose the non-annotated set features file", "You need to specify the location of the the file that contains the extra features to be added to the non-annotated tweets", "OK");
			} else {
				AlertBox.display("Choose the test/non-annotated set features file", "You need to specify the location of the the file that contains the extra features to be added to the test/non-annotated tweets", "OK");
			}
		} else if (checkSecondStep().equals(ErrorCode.Fine))  {
			// Attributes
			for (String attribute : Reader.getAttributes(trainingSetFeaturesLocation.getText())) {
				fields.getItems().add(attribute);
			}
		}
		
	}
	
	
	@FXML public void handleBackButton() {
		System.out.println("Back Button clicked");
		Parameters.reinitialize();
		ImportedFeatures.reinitialize();
		try {
			Main.root = FXMLLoader.load(getClass().getResource("/windows/main/ConfigurationWindow.fxml"));
			Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
			Main.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void handleNextButton() {
		System.out.println("Next Button clicked");
		
		if (isGoodParameters().equals(ErrorCode.Fine)) {
			saveParameters();
			
			
			
			
//			Reader.collectExtraFeatures(Parameters.getTrainingSet(), trainingSetFeaturesLocation.getText());
//			if(testSet.isSelected()) {
//				Reader.collectExtraFeatures(Parameters.getTestSet(), testSetFeaturesLocation.getText());
//			} else if (nonAnnotatedSet.isSelected()) {
//				Reader.collectExtraFeatures(Parameters.getUnknownSet(), testSetFeaturesLocation.getText());
//			}
			
			
			
		}
		
	}
	
	@FXML public void handleCancelButton() {
		System.out.println("Cancel Button clicked");
		closeProgram();
	}
	

	// =====================================//
	//            INITIALIZATION            //
	// =====================================//
	
	/**
	 * Initialize the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Training set text field
		trainingSetStatus.setText("No file selected");
		trainingSetLocation.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("")) {
				trainingSetStatus.setText("No file selected");
				trainingSetStatus.setTextFill(Color.BLACK);
			} else {
				if(Reader.isValidRawFile(newValue, true)) {
					System.out.println("Valid file");
					trainingSetStatus.setText("Valid File!");
					trainingSetStatus.setTextFill(Color.GREEN);
				} else {
					System.out.println("Non Valid file");
					trainingSetStatus.setText("Non Valid File!");
					trainingSetStatus.setTextFill(Color.RED);
					
				}
			}
		});
		
		if (Parameters.getTrainingSetLocation()!=null) {
			trainingSetLocation.setText(Parameters.getTrainingSetLocation());
		}
		
		// Test set / Non-annotated text field
		testSetStatus.setText("No file selected");
		testSetLocation.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("")) {
				testSetStatus.setText("No file selected");
				testSetStatus.setTextFill(Color.BLACK);
			} else {
				if(Reader.isValidRawFile(newValue, true)) {
					System.out.println("Valid file");
					testSetStatus.setText("Valid File!");
					testSetStatus.setTextFill(Color.GREEN);
				} else {
					System.out.println("Non Valid file");
					testSetStatus.setText("Non Valid File!");
					testSetStatus.setTextFill(Color.RED);
					
				}
			}
		});
		
		if (Parameters.getTypeOfProject()!=null) {
			if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
				testSet.setSelected(true);
				if (Parameters.getTestSetLocation()!=null) {
					testSetLocation.setText(Parameters.getTestSetLocation());
				}
				nonAnnotatedSet.setSelected(false);
			}
			else if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.NONANNOTATEDSET)) {
				nonAnnotatedSet.setSelected(true);
				if (Parameters.getNonAnnotatedDataLocation()!=null) {
					testSetLocation.setText(Parameters.getTestSetLocation());
				}
				
				testSet.setSelected(false);
			}
		}
		
		// Training set extra Features text field
		trainingSetFeaturesStatus.setText("No file selected");
		trainingSetFeaturesLocation.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("")) {
				trainingSetFeaturesStatus.setText("No file selected");
				trainingSetFeaturesStatus.setTextFill(Color.BLACK);
			} else {
				if(Reader.isValidRawFile(newValue, true)) {
					System.out.println("Valid file");
					trainingSetFeaturesStatus.setText("Valid File!");
					trainingSetFeaturesStatus.setTextFill(Color.GREEN);
				} else {
					System.out.println("Non Valid file");
					trainingSetFeaturesStatus.setText("Non Valid File!");
					trainingSetFeaturesStatus.setTextFill(Color.RED);
					
				}
			}
		});
		if (ImportedFeatures.getImportTrainingFeaturesFileLocation()!=null) {
			trainingSetLocation.setText(ImportedFeatures.getImportTrainingFeaturesFileLocation());
		}
		
		// Test set / Non-Annotated set extra Features text field
		testSetFeaturesStatus.setText("No file selected");
		testSetFeaturesLocation.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("")) {
				testSetFeaturesStatus.setText("No file selected");
				testSetFeaturesStatus.setTextFill(Color.BLACK);
			} else {
				if(Reader.isValidRawFile(newValue, true)) {
					System.out.println("Valid file");
					testSetFeaturesStatus.setText("Valid File!");
					testSetFeaturesStatus.setTextFill(Color.GREEN);
				} else {
					System.out.println("Non Valid file");
					testSetFeaturesStatus.setText("Non Valid File!");
					testSetFeaturesStatus.setTextFill(Color.RED);
					
				}
			}
		});
		if (ImportedFeatures.getImportTestFeaturesFileLocation()!=null) {
			testSetLocation.setText(ImportedFeatures.getImportTestFeaturesFileLocation());
		}
		
		// Extra features fields
		fields = new ComboBox<>();
		if (ImportedFeatures.getImportedFeatures()!=null && !ImportedFeatures.getImportedFeatures().isEmpty()) {
			for (String attribute : ImportedFeatures.getImportedFeatures()) {
				fields.getItems().add(attribute);
			}
		}
		
		// Tweet ID field
		if (ImportedFeatures.getTweetIdField()!=null && !ImportedFeatures.getTweetIdField().equals("")) {
			fields.setValue(ImportedFeatures.getTweetIdField());
		}

	}
	
	
	// =====================================//
	//            OTHER METHODS             //
	// =====================================//
	

	/**
	 * Handles the exit situations
	 */
	private void closeProgram() {
		System.out.println("Cancel Button clicked");
		if (ConfirmBox.display( "Exit", "Are you sure you want to exis?")) {
			System.exit(0);
		}
	}
	
	/**
	 * Save the parameter before going to the next window
	 */
	private void saveParameters() {
		
		
		
		
		
		
		
		
		
		SelectBasicFeaturesWindow.setPreviousWindow(SelectBasicFeaturesWindow.Previous.importFeaturesWindow);
		
	}

	private ErrorCode checkFirstStep() {
		if (!wekaFileType.isSelected() && !textFileType.isSelected() && !csvFileType.isSelected()) {
			return ErrorCode.No_Type_Selected;
		}
		
		if (!trainingSetStatus.getText().equals("Valid File!")) {
			return ErrorCode.Training_File;
		}
		
		if (!testSet.isSelected() && !nonAnnotatedSet.isSelected()) {
			return ErrorCode.No_Set_Selected;
		}
		
		if (!testSetStatus.getText().equals("Valid File!")) {
			return ErrorCode.Test_File;
		}
		
		return ErrorCode.Fine;
	}
	
	private ErrorCode checkSecondStep() {
		if (!trainingSetFeaturesStatus.getText().equals("Valid File!")) {
			return ErrorCode.Training_Features_File;
		}
		
		if (!testSetFeaturesStatus.getText().equals("Valid File!")) {
			return ErrorCode.Test_Features_File;
		}
		
		return ErrorCode.Fine;
	}
	
	/**
	 * Check if all the parameters were put correctly
	 * @return
	 */
	private ErrorCode isGoodParameters() {
		
		return ErrorCode.Fine;
	}

}

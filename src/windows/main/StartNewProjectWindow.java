package windows.main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;

import commons.io.Reader;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import main.items.Parameters;
import main.items.Parameters.TypeOfProject;
import main.start.Main;
import windows.others.AlertBox;
import windows.others.ConfirmBox;

public class StartNewProjectWindow implements Initializable {
	
	// =====================================//
	//             Error codes              //
	// =====================================//
	
	private enum ErrorCode {
		TrainingSet,
		Type,
		TestSet,
		NonAnnotatedSet,
		NonDefinedClasses,
		Fine
	}
	
	// =====================================//
	//          Non-FXML Components         //
	// =====================================//

	private static TreeMap<String, HBox> classesLabels;
	private static IntegerProperty countOfClasses;
	private GridPane classesLayout;

	
	// =====================================//
	//            FXML Components           //
	// =====================================//
	
	// Radios
	@FXML RadioButton testSetRadio;
	@FXML RadioButton nonAnnotatedSetRadio;
	
	// Sets Files locations
	@FXML TextField trainingSetLocation;
	@FXML TextField testSetLocation;
	@FXML TextField nonAnnotatedSetLocation;
	
	// Sets select buttons
	@FXML Button trainingSetSelect;
	@FXML Button testSetSelect;
	@FXML Button nonAnnotatedSetSelect;
	
	// Sets file status
	@FXML Label trainingSetStatus;
	@FXML Label testSetStatus;
	@FXML Label nonAnnotatedSetStatus;
	
	@FXML Label testSetStatusText;
	@FXML Label nonAnnotatedSetStatusText;
	
	// Load Button
	@FXML Button load;
	
	// Classes related layouts
	@FXML VBox classesOuterBorder;
	
	// Add and Clear Buttons
	@FXML Button add;
	@FXML Button clear;
	
	@FXML TextField classField;
	
	// Back, Next and Cancel Buttons
	@FXML Button backButton;
	@FXML Button nextButton;
	@FXML Button cancelButton;
	
	
	// =====================================//
	//        FXML Components Actions       //
	// =====================================//
	
	@FXML public void handleTestSetRadio() {
		testSetLocation.setDisable(false);
		testSetSelect.setDisable(false);
		testSetRadio.setTextFill(Color.GREEN);
		testSetStatusText.setDisable(false);
		testSetStatus.setDisable(false);
		
		nonAnnotatedSetLocation.setDisable(true);
		nonAnnotatedSetSelect.setDisable(true);
		nonAnnotatedSetRadio.setTextFill(Color.BLACK);
		nonAnnotatedSetStatusText.setDisable(true);
		nonAnnotatedSetStatus.setDisable(true);
	}
	
	@FXML public void handleNonAnnotatedSetRadio() {
		testSetLocation.setDisable(true);
		testSetSelect.setDisable(true);
		testSetRadio.setTextFill(Color.BLACK);
		testSetStatusText.setDisable(true);
		testSetStatus.setDisable(true);
		
		nonAnnotatedSetLocation.setDisable(false);
		nonAnnotatedSetSelect.setDisable(false);
		nonAnnotatedSetRadio.setTextFill(Color.GREEN);
		nonAnnotatedSetStatusText.setDisable(false);
		nonAnnotatedSetStatus.setDisable(false);
	}
	
	@FXML public void handleTrainingSetSelect() {
		System.out.println("Training set \"select\" clicked");
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text file", "*.txt"), new ExtensionFilter("CSV (Comma delimited)", "*.csv"), new ExtensionFilter("All files", "*.*"));
		fileChooser.setTitle("Please specify which file you want to open");
		try {
			String fileToOpen = fileChooser.showOpenDialog(Main.primaryStage).getPath();
			trainingSetLocation.setText(fileToOpen);
		} catch (NullPointerException exepction) {
			System.out.println(exepction.getMessage());
		}
	}

	@FXML public void handleTestSetSelect() {
		System.out.println("Test set \"select\" clicked");
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text file", "*.txt"), new ExtensionFilter("CSV (Comma delimited)", "*.csv"), new ExtensionFilter("All files", "*.*"));
		fileChooser.setTitle("Please specify which file you want to open");
		try {
			String fileToOpen = fileChooser.showOpenDialog(Main.primaryStage).getPath();
			testSetLocation.setText(fileToOpen);
		} catch (NullPointerException exepction) {
			System.out.println(exepction.getMessage());
		}
	}
 	
	@FXML public void handlenonAnnotatedSetSelect() {
		System.out.println("Unknown data set \"select\" clicked");
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text file", "*.txt"), new ExtensionFilter("CSV (Comma delimited)", "*.csv"), new ExtensionFilter("All files", "*.*"));
		fileChooser.setTitle("Please specify which file you want to open");
		try {
			String fileToOpen = fileChooser.showOpenDialog(Main.primaryStage).getPath();
			nonAnnotatedSetLocation.setText(fileToOpen);
		} catch (NullPointerException exepction) {
			System.out.println(exepction.getMessage());
		}
	}
	
	@FXML public void handleLoad() {
		System.out.println("\"Load\" button clicked");
		if (trainingSetStatus.getText().equalsIgnoreCase("Valid File!")) {
			Reader.getAttributePositions(trainingSetLocation.getText());
			
			ArrayList<String> classes = Reader.getClasses(trainingSetLocation.getText());

			for (String cla : classes) {
				if(!cla.equals("") && !classesLabels.containsKey(cla.toUpperCase())) {
					HBox labelToAdd = createKeyWord(cla.toUpperCase());
					classesLabels.put(cla.toUpperCase(), labelToAdd);
					countOfClasses.set(countOfClasses.intValue() + 1);
					System.out.println("class added!");
				}
			}
		} else {
			AlertBox.display("Error", "Make sure you have chosen a valid training file", "OK");
		}
	}

	@FXML public void handleAdd() {
		if(!classField.getText().equals("") && !classesLabels.containsKey(classField.getText().toUpperCase())) {
			HBox labelToAdd = createKeyWord(classField.getText().toUpperCase());
			classesLabels.put(classField.getText().toUpperCase(), labelToAdd);
			countOfClasses.set(countOfClasses.intValue() + 1);
			classField.clear();
			System.out.println("class added!");
		}
	}
	
	@FXML public void handleClear() {
		classesLabels.clear();
		countOfClasses.set(0);
	}
	
	// Back, Next and Cancel Buttons
	@FXML public void handleBackButton() {
		System.out.println("Back Button clicked");
		Parameters.reinitialize();
		try {
			Main.root = FXMLLoader.load(getClass().getResource("/windows/main/ConfigurationWindow.fxml"));
			Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
			Main.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void handleNextButton() {
		
		if (isGoodParameters().equals(ErrorCode.TrainingSet)) {
			System.out.println("Error, training set not defined");
			AlertBox.display("Error", "Your training set has not been loaded. Make sure you choose a correct file and press \"Load\"", "OK");
		} else if (isGoodParameters().equals(ErrorCode.Type)) {
			System.out.println("Error, Type not set");
			AlertBox.display("Error", "Please choose whether you want to work with a test set or an unknown dataset", "OK");
		} else if (isGoodParameters().equals(ErrorCode.TestSet)) {
			System.out.println("Error, test set not defined");
			AlertBox.display("Error", "Your test set has not been loaded. Make sure you choose a correct file and press \"Load\"", "OK");
		} else if (isGoodParameters().equals(ErrorCode.NonAnnotatedSet)) {
			System.out.println("Error, non-annotated set not defined");
			AlertBox.display("Error", "Your unknown set has not been loaded. Make sure you choose a correct file and press \"Load\"", "OK");
		} else if (isGoodParameters().equals(ErrorCode.NonDefinedClasses)) {
			System.out.println("Error, no class defined");
			AlertBox.display("Error", "You Should set at least one one class. Make sure you define at least either by adding it or by simply pressing \"Load\"", "OK");
		} else {
			System.out.println("Next Button clicked");
			saveParameters();
			SelectBasicFeaturesWindow.setPreviousWindow(SelectBasicFeaturesWindow.Previous.startProjectWindow);
			try {
				Main.root = FXMLLoader.load(getClass().getResource("/windows/main/SelectBasicFeaturesWindow.fxml"));
				Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
				Main.primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		
		// Disable the location for both test and non-annotated sets until the user chooses one
		testSetLocation.setDisable(true);
		nonAnnotatedSetLocation.setDisable(true);
		testSetSelect.setDisable(true);
		nonAnnotatedSetSelect.setDisable(true);
		testSetStatus.setDisable(true);
		nonAnnotatedSetStatus.setDisable(true);
		testSetStatusText.setDisable(true);
		nonAnnotatedSetStatusText.setDisable(true);
		
		
		// Test set text field
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
		
		// Non-annotated set text field
		nonAnnotatedSetStatus.setText("No file selected");
		nonAnnotatedSetLocation.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("")) {
				nonAnnotatedSetStatus.setText("No file selected");
				nonAnnotatedSetStatus.setTextFill(Color.BLACK);
			} else {
				if(Reader.isValidRawFile(newValue, false)) {
					System.out.println("Valid file");
					nonAnnotatedSetStatus.setText("Valid File!");
					nonAnnotatedSetStatus.setTextFill(Color.GREEN);
				} else {
					System.out.println("Non Valid file");
					nonAnnotatedSetStatus.setText("Non Valid File!");
					nonAnnotatedSetStatus.setTextFill(Color.RED);
					
				}
			}
		});
		
		
		// Check if any already exists
		if (Parameters.getTypeOfProject()!=null) {
			if (Parameters.getNonAnnotatedDataLocation() != null && Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.NONANNOTATEDSET)) {
				nonAnnotatedSetLocation.setText(Parameters.getNonAnnotatedDataLocation());
				nonAnnotatedSetLocation.setDisable(false);
				nonAnnotatedSetSelect.setDisable(false);
				nonAnnotatedSetStatus.setDisable(false);
				nonAnnotatedSetStatusText.setDisable(false);
				testSetRadio.setSelected(false);
				nonAnnotatedSetRadio.setSelected(true);
				nonAnnotatedSetRadio.setTextFill(Color.GREEN);
				
			} else if (Parameters.getTestSetLocation()!=null && Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
				testSetLocation.setText(Parameters.getTrainingSetLocation());
				testSetLocation.setDisable(false);
				testSetSelect.setDisable(false);
				testSetStatus.setDisable(false);
				testSetStatusText.setDisable(false);
				testSetRadio.setSelected(true);
				testSetRadio.setTextFill(Color.GREEN);
				nonAnnotatedSetRadio.setSelected(false);
			}
		}
		
		
		// Related to the classes
		classesLabels = new TreeMap<>();
		countOfClasses = new SimpleIntegerProperty(0);
		
		classesLayout = new GridPane();
		classesLayout.setMinWidth(480);
		classesLayout.setMaxWidth(480);
		classesLayout.setMinHeight(100);
		classesLayout.setMaxWidth(100);
		classesLayout.setHgap(2);
		classesLayout.setVgap(2);
		final int numCols = 4 ;
        final int numRows = 4 ;
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            classesLayout.getColumnConstraints().add(colConst);
        }
        
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            classesLayout.getRowConstraints().add(rowConst);         
        }
        
        classesOuterBorder.getChildren().add(classesLayout);
		
		// Listener to che change of count of key words entered
        ReadOnlyIntegerProperty.readOnlyIntegerProperty(countOfClasses).addListener((v, oldValue, newValue) -> {
        	rearrangeKeywords(classesLayout, 4, 4, classesLabels);
			if (!newValue.equals(16)) {
				add.setDisable(false);
			} else {
				add.setDisable(true);
			}
			System.out.println("oldValue:" + oldValue + ", newValue = " + newValue);
		});
        
        // Initialize the classes lables
        if (Parameters.getClasses()!=null) {
        	for (String cla : Parameters.getClasses()) {
				if(!cla.equals("") && !classesLabels.containsKey(cla.toUpperCase())) {
					HBox labelToAdd = createKeyWord(cla.toUpperCase());
					classesLabels.put(cla.toUpperCase(), labelToAdd);
					countOfClasses.set(countOfClasses.intValue() + 1);
					System.out.println("class added!");
				}
			}
        }
        
        // Keys pressing
        classField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.ENTER) {
					keyEvent.consume();
					add.fire();
				}
			}
		});

	}
	
	
	// =====================================//
	//            OTHER METHODS             //
	// =====================================//
	
	/**
	 * Creates the label that will be added to the keywords
	 * @param keyword
	 * @return
	 */
	private static HBox createKeyWord(String keyword) {
		Button closeButton = new Button("X");
		closeButton.setStyle("-fx-font-size: 5pt; -fx-text-fill:red;");
		Label label = new Label(keyword);
		label.setGraphic(closeButton);
		label.setContentDisplay(ContentDisplay.RIGHT);

		HBox keywordRemoveLayout = new HBox(label);
		keywordRemoveLayout.setAlignment(Pos.CENTER);
		keywordRemoveLayout.setStyle("-fx-padding: 1;" + 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 1;" +
                "-fx-border-insets: 1;" + 
                "-fx-border-radius: 1;" + 
                "-fx-border-color: gray;");
		closeButton.setOnAction(event -> {
			keywordRemoveLayout.getChildren().remove(label);
			classesLabels.remove(keyword);
			countOfClasses.set(countOfClasses.get()-1);
		});
		return keywordRemoveLayout;
	}
	
	/**
	 * Rearrange the keywords in the grid (after adding one keyword)
	 * @param layout
	 * @param numberOfColumns
	 * @param NumberOfLines
	 * @param kwLabels
	 */
	private static void rearrangeKeywords(GridPane layout, int numberOfColumns, int NumberOfLines, TreeMap<String, HBox> kwLabels) {
		
		int counter = 0;
		Integer i = 0;
		Integer j = 0;
		
		layout.getChildren().clear();
		
		for (String key : kwLabels.keySet()) {
			
			layout.add(kwLabels.get(key), i, j);
			
			counter++;
			i++;
			if (i%(Integer)numberOfColumns==0) {
				i = 0;
				j++;
			}
			if (counter == numberOfColumns * NumberOfLines) {
				break;
			}
 		}
	}
	
	/**
	 * Save the parameter before going to the next window
	 */
	private void saveParameters() {
		
		// save the set of classes
		ArrayList<String> classes = new ArrayList<>();
		for (String cla : classesLabels.keySet()) {
			classes.add(cla);
		}
		Parameters.setClasses(classes);
		
		// save how to read the file
		Reader.getAttributePositions(trainingSetLocation.getText());
		
		// Training set
		Parameters.setTrainingSetLocation(trainingSetLocation.getText());
		Parameters.setTrainingSet(Reader.readRawFile(trainingSetLocation.getText(), true));

		// Test set
		if (testSetRadio.isSelected()) {
			Parameters.setTypeOfProject(TypeOfProject.TESTSET);
			Parameters.setTestSetLocation(testSetLocation.getText());
			Parameters.setTestSet(Reader.readRawFile(testSetLocation.getText(), true));
			
			Parameters.setNonAnnotatedDataLocation(null);
			Parameters.setUnknownSet(null);
		}
		
		// Non annotated set
		if (nonAnnotatedSetRadio.isSelected()) {
			Parameters.setTypeOfProject(TypeOfProject.NONANNOTATEDSET);
			Parameters.setNonAnnotatedDataLocation(nonAnnotatedSetLocation.getText());
			Parameters.setUnknownSet(Reader.readRawFile(nonAnnotatedSetLocation.getText(), false));
			
			Parameters.setTestSetLocation(null);
			Parameters.setTestSet(null);
		}
		
	}

	/**
	 * Check if all the parameters were put correctly
	 * @return
	 */
	private ErrorCode isGoodParameters() {
				
		if (!trainingSetStatus.getText().equalsIgnoreCase("Valid File!")) {
			System.out.println("Empty training set");
			return ErrorCode.TrainingSet;
		}
		
		if (!testSetRadio.isSelected() && !nonAnnotatedSetRadio.isSelected()) {
			System.out.println("Type of project not selected");
			return ErrorCode.Type;
		} else if (testSetRadio.isSelected()) {
			if (!testSetStatus.getText().equalsIgnoreCase("Valid File!")) {
				System.out.println("Empty test set");
				return ErrorCode.TestSet;
			}
		} else if (nonAnnotatedSetRadio.isSelected()) {
			if (!nonAnnotatedSetStatus.getText().equalsIgnoreCase("Valid File!")) {
				System.out.println("Empty non-annotated set");
				return ErrorCode.NonAnnotatedSet;
			}
		}
		
		if (classesLabels.isEmpty()) {
			System.out.println("classes non defined");
			return ErrorCode.NonDefinedClasses;
		}
		
		return ErrorCode.Fine;
	}

	/**
	 * Handles the exit situations
	 */
	private void closeProgram() {
		System.out.println("Cancel Button clicked");
		if (ConfirmBox.display( "Exit", "Are you sure you want to exit?")) {
			System.exit(0);
		}
	}
	
}

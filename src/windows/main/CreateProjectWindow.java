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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import main.items.Parameters;
import main.start.Main;
import windows.others.AlertBox;
import windows.others.ConfirmBox;

public class CreateProjectWindow implements Initializable {
	
	// =====================================//
	//           FXML Components            //
	// =====================================//
	
	// Project name and directory
	@FXML TextField projectDirectory;
	@FXML TextField projectName;
	
	@FXML Label projectDirectoryStatus;
	@FXML Label projectNameStatus;
	
	@FXML Button projectDirectorySelect;
	
	// Import Top words and patterns file
	@FXML CheckBox importTopWords;
	@FXML CheckBox importPatterns;
	@FXML CheckBox importAdvancedPatterns;
	
	@FXML TextField topWordsFile;
	@FXML TextField patternsFile;
	@FXML TextField advancedPatternsFile;
	
	@FXML Label topWordsFileStatus;
	@FXML Label topWordsFileStatusLabel;
	@FXML Label patternsFileStatus;
	@FXML Label patternsFileStatusLabel;
	@FXML Label advancedPatternsFileStatus;
	@FXML Label advancedPatternsFileStatusLabel;
	
	@FXML Button topWordsFileSelect;
	@FXML Button patternsFileSelect;
	@FXML Button advancedPatternsFileSelect;
	
	// Saving formats
	@FXML CheckBox csvFormat;
	@FXML CheckBox txtFormat;
	@FXML CheckBox arffFormat;
	
	// Saving formats
	@FXML CheckBox saveTopWords;
	@FXML CheckBox savePatterns;
	@FXML CheckBox saveAdvancedPatterns;
	
	// Back, Next and Cancel Buttons
	@FXML Button backButton;
	@FXML Button nextButton;
	@FXML Button cancelButton;
	

	// =====================================//
	//       FXML Components ACTIONS        //
	// =====================================//
	
	// Handle the select button for the Project directory
	@FXML public void handleProjectDirectorySelect() {
		System.out.println("Training set \"select\" clicked");
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle("Please specify where you want to save your project");
		try {
			String fileToOpen = directoryChooser.showDialog(Main.primaryStage).getPath();
			System.out.println(fileToOpen);
			projectDirectory.setText(fileToOpen);
		} catch (NullPointerException exepction) {
			System.out.println(exepction.getMessage());
		}
	}
	
	@FXML public void handleTopWordsFileSelect() {
		System.out.println("Top Words File \"select\" clicked");
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text file", "*.txt"), new ExtensionFilter("All files", "*.*"));
		fileChooser.setTitle("Please specify which file you want to open");
		try {
			String fileToOpen = fileChooser.showOpenDialog(Main.primaryStage).getPath();
			System.out.println(fileToOpen);
			// TODO add the function in Reader that checks whether the file really works fine
			topWordsFile.setText(fileToOpen);
		} catch (NullPointerException exepction) {
			System.out.println(exepction.getMessage());
		}
	}
	
	@FXML public void handlePatternsFileSelect() {
		System.out.println("Patterns File \"select\" clicked");
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text file", "*.txt"), new ExtensionFilter("All files", "*.*"));
		fileChooser.setTitle("Please specify which file you want to open");
		try {
			String fileToOpen = fileChooser.showOpenDialog(Main.primaryStage).getPath();
			System.out.println(fileToOpen);
			// TODO add the function in Reader that checks whether the file really works fine
			patternsFile.setText(fileToOpen);
		} catch (NullPointerException exepction) {
			System.out.println(exepction.getMessage());
		}
	}
	
	@FXML public void handleAdvancedPatternsFileSelect() {
		System.out.println("Advanced Patterns File \"select\" clicked");
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text file", "*.txt"), new ExtensionFilter("All files", "*.*"));
		fileChooser.setTitle("Please specify which file you want to open");
		try {
			String fileToOpen = fileChooser.showOpenDialog(Main.primaryStage).getPath();
			System.out.println(fileToOpen);
			// TODO add the function in Reader that checks whether the file really works fine
			advancedPatternsFile.setText(fileToOpen);
		} catch (NullPointerException exepction) {
			System.out.println(exepction.getMessage());
		}	
	}
	
	@FXML public void handleImportTopWords() {
		if (importTopWords.isSelected()) {
			topWordsFile.setDisable(false);
			topWordsFileSelect.setDisable(false);
			topWordsFileStatusLabel.setDisable(false);
			topWordsFileStatus.setDisable(false);
		} else {
			topWordsFile.setDisable(true);
			topWordsFileSelect.setDisable(true);
			topWordsFileStatusLabel.setDisable(true);
			topWordsFileStatus.setDisable(true);
		}
	}
	
	@FXML public void handleImportPatterns() {
		if (importPatterns.isSelected()) {
			patternsFile.setDisable(false);
			patternsFileSelect.setDisable(false);
			patternsFileStatusLabel.setDisable(false);
			patternsFileStatus.setDisable(false);
		} else {
			patternsFile.setDisable(true);
			patternsFileSelect.setDisable(true);
			patternsFileStatusLabel.setDisable(true);
			patternsFileStatus.setDisable(true);
		}
	}
	
	@FXML public void handleImportAdvancedPatterns() {
		if (importAdvancedPatterns.isSelected()) {
			advancedPatternsFile.setDisable(false);
			advancedPatternsFileSelect.setDisable(false);
			advancedPatternsFileStatusLabel.setDisable(false);
			advancedPatternsFileStatus.setDisable(false);
		} else {
			advancedPatternsFile.setDisable(true);
			advancedPatternsFileSelect.setDisable(true);
			advancedPatternsFileStatusLabel.setDisable(true);
			advancedPatternsFileStatus.setDisable(true);
		}
	}

	
	// Back, Next and Cancel Buttons
	@FXML public void handleBackButton() {
		System.out.println("Back Button clicked");
		if (isGoodParameters().equalsIgnoreCase("fine")) {
			saveParameters();
		}
		try {
			Main.root = FXMLLoader.load(getClass().getResource("/windows/main/SelectAdvancedFeaturesWindow.fxml"));
			Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
			Main.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void handleNextButton() {
		System.out.println("Next Button clicked");
		if (isGoodParameters().equals("fine")) {
			saveParameters();
			try {
				Main.root = FXMLLoader.load(getClass().getResource("/windows/main/ProjectProgress.fxml"));
				Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
				Main.primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (isGoodParameters().equals("projectDirectory")) {
			AlertBox.display("Invalid Directory", "The directory you have chosen is invalid. Please choose a valid directory.", "OK");
		} else if (isGoodParameters().equals("projectName")) {
			AlertBox.display("Invalid Project Name", "The name you have chosen for your project is invalid. Please choose a valid name.", "OK");
		} else if (isGoodParameters().equals("topWords")) {
			AlertBox.display("Error", "The file you have chosen to import Top Words is invalid or corrupted. Please choose a working file or uncheck the import of Top Words.", "OK");
		} else if (isGoodParameters().equals("basicPatterns")) {
			AlertBox.display("Error", "The file you have chosen to import the basic patterns is invalid or corrupted. Please choose a working file or uncheck the import of basic patterns.", "OK");
		} else if (isGoodParameters().equals("advancedPatterns")) {
			AlertBox.display("Error", "The file you have chosen to import the advanced patterns is invalid or corrupted. Please choose a working file or uncheck the import of advanced patterns.", "OK");
		} else if (isGoodParameters().equals("format")) {
			AlertBox.display("Choose an output file(s)", "You need to choose at least one format to export your output.", "OK");
		}
		
		
		// TODO try to make a test file before starting to check if it is possible to write on the directory in the first place
		// TODO add the current project to the list of recent projects
	}
	
	@FXML public void handleCancelButton() {
		System.out.println("Cancel Button clicked");
		closeProgram();
	}
	

	//======================================//
	//            INITIALIZATION            //
	//======================================//
	
	/**
	 * Initialize the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Project Directory-related
		projectDirectoryStatus.setText("No directory selected");
		projectDirectory.setPromptText("Select a directory");
		projectDirectory.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("")) {
				projectDirectoryStatus.setText("No directory selected");
				projectDirectoryStatus.setTextFill(Color.BLACK);
			} else {
				if(Reader.isValidDirectory(newValue)) {
					System.out.println("Valid directory");
					projectDirectoryStatus.setText("Valid directory!");
					projectDirectoryStatus.setTextFill(Color.GREEN);
				} else {
					System.out.println("Non Valid directory");
					projectDirectoryStatus.setText("Non valid directory!");
					projectDirectoryStatus.setTextFill(Color.RED);
					
				}
			}
		});
		if (Parameters.getProjectLocation()!=null) {
			projectDirectory.setText(Parameters.getProjectLocation());
		}
		
		// Project Name-related
		projectNameStatus.setText("No name!");
		projectName.setPromptText("Choose a name for your project");
		projectName.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("")) {
				projectNameStatus.setText("No name!");
				projectNameStatus.setTextFill(Color.BLACK);
			} else {
				System.out.println("Valid project name");
				projectName.setText(projectName.getText().replaceAll("[^a-zA-Z0-9 _-]", ""));
				if(!projectName.equals("")) {
					projectNameStatus.setText("Valid project name!");
					projectNameStatus.setTextFill(Color.GREEN);
				}
			}
		});
		if (Parameters.getProjectName()!=null) {
			projectName.setText(Parameters.getProjectName());
		}
		
		
		// Top words file-related
		if (Parameters.isImportTopWords()) {
			importTopWords.setSelected(true);
			topWordsFile.setDisable(false);
			if (Parameters.getTopWordsImportedFileLocation()!=null && !Parameters.getTopWordsImportedFileLocation().equals("")) {
				topWordsFile.setText(Parameters.getTopWordsImportedFileLocation());
				if (Reader.isValidTopWordsFile(topWordsFile.getText())){
					topWordsFileStatus.setText("Valid file!");
					topWordsFileStatus.setTextFill(Color.GREEN);
				} else {
					topWordsFileStatus.setText("Invalid file!");
					topWordsFileStatus.setTextFill(Color.RED);
				}
			}
			topWordsFileSelect.setDisable(false);
			topWordsFileStatusLabel.setDisable(false);
			topWordsFileStatus.setDisable(false);
		} else {
			importTopWords.setSelected(false);
			topWordsFile.setDisable(true);
			topWordsFileSelect.setDisable(true);
			topWordsFileStatusLabel.setDisable(true);
			topWordsFileStatus.setDisable(true);
		}
		
		topWordsFile.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("")) {
				topWordsFileStatus.setText("No file!");
				topWordsFileStatus.setTextFill(Color.BLACK);
			} else if (Reader.isValidTopWordsFile(topWordsFile.getText())){
				topWordsFileStatus.setText("Valid file!");
				topWordsFileStatus.setTextFill(Color.GREEN);
			} else {
				topWordsFileStatus.setText("Invalid file!");
				topWordsFileStatus.setTextFill(Color.RED);
			}
		});
		
		
		// Basic Patterns-related
		if (Parameters.isImportBasicPatterns()) {
			importPatterns.setSelected(true);
			patternsFile.setDisable(false);
			if (Parameters.getBasicPatternsImportedFileLocation()!=null && !Parameters.getBasicPatternsImportedFileLocation().equals("")) {
				patternsFile.setText(Parameters.getBasicPatternsImportedFileLocation());
				if (Reader.isValidPatternsFile(patternsFile.getText())){
					patternsFileStatus.setText("Valid file!");
					patternsFileStatus.setTextFill(Color.GREEN);
				} else {
					patternsFileStatus.setText("Invalid file!");
					patternsFileStatus.setTextFill(Color.RED);
				}
			}
			patternsFileSelect.setDisable(false);
			patternsFileStatusLabel.setDisable(false);
			patternsFileStatus.setDisable(false);
		} else {
			importPatterns.setSelected(false);
			patternsFile.setDisable(true);
			patternsFileSelect.setDisable(true);
			patternsFileStatusLabel.setDisable(true);
			patternsFileStatus.setDisable(true);
		}
		
		patternsFile.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("")) {
				patternsFileStatus.setText("No file!");
				patternsFileStatus.setTextFill(Color.BLACK);
			} else if (Reader.isValidPatternsFile(patternsFile.getText())){
				patternsFileStatus.setText("Valid file!");
				patternsFileStatus.setTextFill(Color.GREEN);
			} else {
				patternsFileStatus.setText("Invalid file!");
				patternsFileStatus.setTextFill(Color.RED);
			}
		});
		
		// Advanced Patterns-related
		if (Parameters.isImportAdvancedPatterns()) {
			importAdvancedPatterns.setSelected(true);
			advancedPatternsFile.setDisable(false);
			if (Parameters.getAdvancedPatternsImportedFileLocation()!=null && !Parameters.getAdvancedPatternsImportedFileLocation().equals("")) {
				advancedPatternsFile.setText(Parameters.getAdvancedPatternsImportedFileLocation());
				if (Reader.isValidPatternsFile(advancedPatternsFile.getText())){
					advancedPatternsFileStatus.setText("Valid file!");
					advancedPatternsFileStatus.setTextFill(Color.GREEN);
				} else {
					advancedPatternsFileStatus.setText("Invalid file!");
					advancedPatternsFileStatus.setTextFill(Color.RED);
				}
			}
			advancedPatternsFileSelect.setDisable(false);
			advancedPatternsFileStatusLabel.setDisable(false);
			advancedPatternsFileStatus.setDisable(false);
		} else {
			importAdvancedPatterns.setSelected(false);
			advancedPatternsFile.setDisable(true);
			advancedPatternsFileSelect.setDisable(true);
			advancedPatternsFileStatusLabel.setDisable(true);
			advancedPatternsFileStatus.setDisable(true);
		}
		
		advancedPatternsFile.textProperty().addListener((observable, oldValue, newValue) -> {
			if(newValue.equals("")) {
				advancedPatternsFileStatus.setText("No file!");
				advancedPatternsFileStatus.setTextFill(Color.BLACK);
			} else if (Reader.isValidPatternsFile(advancedPatternsFile.getText())){
				advancedPatternsFileStatus.setText("Valid file!");
				advancedPatternsFileStatus.setTextFill(Color.GREEN);
			} else {
				advancedPatternsFileStatus.setText("Invalid file!");
				advancedPatternsFileStatus.setTextFill(Color.RED);
			}
		});

		// Save format-related
		csvFormat.setSelected(Parameters.isSaveCsv());
		txtFormat.setSelected(Parameters.isSaveTxt());
		arffFormat.setSelected(Parameters.isSaveArff());

		// Save additional files-related
		saveTopWords.setSelected(Parameters.isSaveTopWords());
		savePatterns.setSelected(Parameters.isSaveBasicPatterns());
		saveAdvancedPatterns.setSelected(Parameters.isSaveAdvancedPatterns());
		
		
	}
	
	
	//======================================//
	//            PRIVATE METHODS           //
	//======================================//
	
	/**
	 * Private method that handles the exit situations
	 */
	private void closeProgram() {
		System.out.println("Cancel Button clicked");
		if (ConfirmBox.display( "Exit", "Are you sure you want to exis?")) {
			System.exit(0);
		}
	}

	/**
	 * Private methods that handles saving the parameters
	 */
	private void saveParameters() {
		
		// ProjectDirectory
		String projectDir = projectDirectory.getText();
		while(projectDir.endsWith("\\")) {
			projectDir = projectDir.substring(0, projectDir.length()-1);
		}
		Parameters.setProjectLocation(projectDir);
		
		// Project Name
		Parameters.setProjectName(projectName.getText());
		
		// Import Top Words-related
		Parameters.setImportTopWords(importTopWords.isSelected());
		if (importTopWords.isSelected()) {
			if (Reader.isValidTopWordsFile(topWordsFile.getText())) {
				Parameters.setTopWordsImportedFileLocation(topWordsFile.getText());
			}
		}
		
		// Import Basic Patterns-related
		Parameters.setImportBasicPatterns(importPatterns.isSelected());
		if (importPatterns.isSelected()) {
			if (Reader.isValidPatternsFile(patternsFile.getText())) {
				Parameters.setBasicPatternsImportedFileLocation(patternsFile.getText());
			}
		}
		
		// Import Advanced Patterns-related
		Parameters.setImportAdvancedPatterns(importAdvancedPatterns.isSelected());
		if (importAdvancedPatterns.isSelected()) {
			if (Reader.isValidPatternsFile(advancedPatternsFile.getText())) {
				Parameters.setAdvancedPatternsImportedFileLocation(advancedPatternsFile.getText());
			}
		}
		
		// Save format-related
		Parameters.setSaveCsv(csvFormat.isSelected());
		Parameters.setSaveTxt(txtFormat.isSelected());
		Parameters.setSaveArff(arffFormat.isSelected());

		// Export Top Words
		Parameters.setSaveTopWords(saveTopWords.isSelected());
		if (saveTopWords.isSelected()) {
			Parameters.setTopWordsSavedFileLocation(Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + Parameters.getProjectName() + "-top_words.txt");
		} else {
			Parameters.setTopWordsSavedFileLocation(null);
		}
		
		// Export Basic Patterns
		Parameters.setSaveBasicPatterns(savePatterns.isSelected());
		if (savePatterns.isSelected()) {
			Parameters.setBasicPatternsSavedFileLocation(Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + Parameters.getProjectName() + "-basic_patterns.txt");
		} else {
			Parameters.setBasicPatternsSavedFileLocation(null);
		}
		
		// Export Advanced Patterns
		Parameters.setSaveAdvancedPatterns(saveAdvancedPatterns.isSelected());
		if (saveAdvancedPatterns.isSelected()) {
			Parameters.setAdvancedPatternsSavedFileLocation(Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\advanced_patterns.txt");
		} else {
			Parameters.setAdvancedPatternsSavedFileLocation(null);
		}
	
		
	}

	/**
	 * Private methods that checks if all the parameters are good
	 * @return
	 */
	private String isGoodParameters() {
		
		if (!Reader.isValidDirectory(projectDirectory.getText())) {
			return "projectDirectory";
		} else if (projectName.getText()==null || projectName.getText().equals("")) {
			return "projectName";
		} else if (!csvFormat.isSelected() && !txtFormat.isSelected() && !arffFormat.isSelected()) {
			return "format";
		} else if (importTopWords.isSelected() && !Reader.isValidTopWordsFile(topWordsFile.getText())) {
			return "topWords";
		} else if (importPatterns.isSelected() && !Reader.isValidPatternsFile(patternsFile.getText())) {
			return "basicPatterns";
		} else if (importAdvancedPatterns.isSelected() && !Reader.isValidPatternsFile(advancedPatternsFile.getText())) {
			return "advancedPatterns";
		}
		return "fine";
	}

}

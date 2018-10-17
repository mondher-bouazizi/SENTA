package windows.main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

import commons.io.Reader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import main.items.Parameters;
import main.start.Loader;
import main.start.Main;
import windows.others.AlertBox;
import windows.others.ConfirmBox;

public class OpenProjectWindow implements Initializable {
	
	// =====================================//
	//         non-FXML Components          //
	// =====================================//
	
	private boolean isGotten;
	
	private ArrayList<String> tempClasses;
	private Parameters.TypeOfProject type;
	private String projectFile;
	//private String projectLocation;
	
	// =====================================//
	//           FXML Components            //
	// =====================================//
	
	// Select a file-related
	@FXML RadioButton fileSelection;
	
	@FXML TextField fileLocation;
	@FXML Button fileLocationSelect;
	
	@FXML Label fileLocationStatusLabel;
	@FXML Label fileLocationStatus;
	
	
	// Recent files-related
	@FXML RadioButton recentFile;
	
	@FXML ComboBox<String> recentFilesList;
	
	@FXML Label recentFilesStatusLabel;
	@FXML Label recentFilesStatus;
	
	// Get Button
	@FXML Button getButton;
	
	// project id and name
	@FXML Label projectType;
	@FXML Label projectName;
	
	// Training and test files
	@FXML Label trainingFile;
	@FXML Label trainingFileStatusLabel;
	@FXML Label trainingFileStatus;
	@FXML Button trainingFileUpdate;
	
	@FXML Label testFile;
	@FXML Label testFileStatusLabel;
	@FXML Label testFileStatus;
	@FXML Button testFileUpdate;
	
	// Classes
	@FXML Label classes;
	
	// Other files
	@FXML CheckBox isFeaturesFile;
	@FXML Label featuresFile;
	@FXML Label featuresFileStatusLabel;
	@FXML Label featuresFileStatus;
	
	@FXML CheckBox isTopWordsFile;
	@FXML Label topWordsFile;
	@FXML Label topWordsFileStatusLabel;
	@FXML Label topWordsFileStatus;
	
	@FXML CheckBox isBasicPatternsFile;
	@FXML Label basicPatternsFile;
	@FXML Label basicPatternsFileStatusLabel;
	@FXML Label basicPatternsFileStatus;
	
	@FXML CheckBox isAdvancedPatternsFile;
	@FXML Label advancedPatternsFile;
	@FXML Label advancedPatternsFileStatusLabel;
	@FXML Label advancedPatternsFileStatus;
	
	
	// Back, Next and Cancel Buttons
	@FXML Button backButton;
	@FXML Button nextButton;
	@FXML Button cancelButton;
	

	// =====================================//
	//       FXML Components ACTIONS        //
	// =====================================//
	
	// Radio Buttons Actions
	@FXML public void fileSelectionSelection() {
		System.out.println("File Selection is selected");
		if(fileSelection.isSelected()) {
			fileLocation.setDisable(false);
			fileLocationSelect.setDisable(false);
			fileLocationStatusLabel.setDisable(false);
			fileLocationStatus.setDisable(false);
			
			recentFilesList.setDisable(true);
			recentFilesStatusLabel.setDisable(true);
			recentFilesStatus.setDisable(true);
		}
	}
	
	@FXML public void recentFileSelection() {
		System.out.println("Recent files selection is selected");
		if(recentFile.isSelected()) {
			fileLocation.setDisable(true);
			fileLocationSelect.setDisable(true);
			fileLocationStatusLabel.setDisable(true);
			fileLocationStatus.setDisable(true);
			
			recentFilesList.setDisable(false);
			recentFilesStatusLabel.setDisable(false);
			recentFilesStatus.setDisable(false);
		}
	}
	
	// Selection of the file
	@FXML public void handleFileLocationSelect() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("SENTA project file", "*.senta"), new ExtensionFilter("Text file", "*.txt"), new ExtensionFilter("All files", "*.*"));
		fileChooser.setTitle("Please specify the project file you want to open");
		try {
			String fileToOpen = fileChooser.showOpenDialog(Main.primaryStage).getPath();
			fileLocation.setText(fileToOpen);
		} catch (NullPointerException exepction) {
			System.out.println("EXCEPTION ++++++++" + exepction.getMessage());
		}
	}
	
	// Get Button actions
	@FXML public void handleGetButton() {
		if (fileSelection.isSelected()) {
			if (Reader.isValidProjectFile(fileLocation.getText())) {
				getParameters(fileLocation.getText());
				isGotten = true;
			}
		} else if (recentFile.isSelected()) {
			if (Reader.isValidProjectFile(recentFilesList.getValue())) {
				getParameters(recentFilesList.getValue());
				isGotten = true;
			}
		}
		
		// TODO [VERY CRITICAL!!] When opening project, the seed words are not imported along with the rest of the parameters!!!!!
		// Fix urgently
	}
	
	// update buttons actions
	@FXML public void handleTrainingFileUpdate() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text file", "*.txt"), new ExtensionFilter("All files", "*.*"));
		fileChooser.setTitle("Please specify the file you want to open");
		try {
			String fileToOpen = fileChooser.showOpenDialog(Main.primaryStage).getPath();
			trainingFile.setText(fileToOpen);
		} catch (NullPointerException exepction) {
			System.out.println("EXCEPTION ++++++++" + exepction.getMessage());
		}
	}
	
	@FXML public void handleTestFileUpdate() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text file", "*.txt"), new ExtensionFilter("All files", "*.*"));
		fileChooser.setTitle("Please specify the file you want to open");
		try {
			String fileToOpen = fileChooser.showOpenDialog(Main.primaryStage).getPath();
			testFile.setText(fileToOpen);
		} catch (NullPointerException exepction) {
			System.out.println("EXCEPTION ++++++++" + exepction.getMessage());
		}
	}
	
	// Back, Next and Cancel Buttons
	@FXML public void handleBackButton() {
		System.out.println("Back Button clicked");
		if (isGoodParameters().equalsIgnoreCase("fine")) {
			saveParameters();
		}
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
		if (isGoodParameters().equals("fine")) {
			saveParameters();
			SelectBasicFeaturesWindow.setPreviousWindow(SelectBasicFeaturesWindow.Previous.importProjectWindow);
			try {
				Main.root = FXMLLoader.load(getClass().getResource("/windows/main/SelectBasicFeaturesWindow.fxml"));
				Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
				Main.primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (!isGotten) {
			AlertBox.display("Please Select a Project File", "Please choose a project file first before continuing!", "OK");
		} else if (isGoodParameters().equals("projectType")) {
			AlertBox.display("Invalid Project Type", "The project file might be corrupted, the type of project specified is undefined!", "OK");
		} else if (isGoodParameters().equals("projectName")) {
			AlertBox.display("Invalid Project Name", "The project file might be corrupted, the name of the project specified is invalid!", "OK");
		} else if (isGoodParameters().equals("trainingFileNonValid")) {
			AlertBox.display("Invalid Training Set File", "The file chosen to get the training set is invalid!", "OK");
		} else if (isGoodParameters().equals("trainingFileNonExisting")) {
			AlertBox.display("Invalid Training Set File", "The file chosen to get the training set does not exist!", "OK");
		} else if (isGoodParameters().equals("testFileNonValid")) {
			AlertBox.display("Invalid Test Set File", "The file chosen to get the test set is invalid!", "OK");
		} else if (isGoodParameters().equals("testFileNonExisting")) {
			AlertBox.display("Invalid Test Set File", "The file chosen to get the test set does not exist!", "OK");
		} else if (isGoodParameters().equals("classes")) {
			AlertBox.display("Invalid Classes", "The set of classes is empty or invalide!", "OK");
		} else if (isGoodParameters().equals("featuresFile")) {
			AlertBox.display("Invalid Features File", "The file chosen to import the features is invalid!", "OK");
		} else if (isGoodParameters().equals("topWords")) {
			AlertBox.display("Invalid Top Words File", "The file you have chosen to import Top Words is invalid or corrupted. Please choose a working file or uncheck the import of Top Words.", "OK");
		} else if (isGoodParameters().equals("basicPatterns")) {
			AlertBox.display("Invalid Basic Patterns File", "The file you have chosen to import the basic patterns is invalid or corrupted. Please choose a working file or uncheck the import of basic patterns.", "OK");
		} else if (isGoodParameters().equals("advancedPatterns")) {
			AlertBox.display("Invalid Advanced Patterns File", "The file you have chosen to import the advanced patterns is invalid or corrupted. Please choose a working file or uncheck the import of advanced patterns.", "OK");
		}
		
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
		// Add the list of recent project files to the ComboBox
		for (String recentFile : Loader.getRecentFiles()) {
			recentFilesList.getItems().add(recentFile);
		}
		
		// initialize the checkers of the different components
		initializeCheckers();
		
		// Checks whether we came from the start window or using the "Back" Button click on the "Select Basic features window"
		if (SelectBasicFeaturesWindow.getPreviousWindow()==null) {
			initializeNew();
		} else {
			if (SelectBasicFeaturesWindow.getPreviousWindow().equals(SelectBasicFeaturesWindow.Previous.startProjectWindow)) {
				initializeNew();
			} else {
				initializeFromBack();
			}
		}
		
	}
	
	private void initializeFromBack() {
		if (Parameters.getImportedProjectDirectory()!=null && !Parameters.getImportedProjectDirectory().equals("")) {
			try {
				if (Parameters.getImportedProjectName()!=null && !Parameters.getImportedProjectName().equals("")) {
					fileSelection.setSelected(true);
					fileSelection.setDisable(false);
					isGotten = true;
					projectFile = Parameters.getImportedProjectDirectory() + "\\" + Parameters.getImportedProjectName() + "\\" + Parameters.getImportedProjectName() + "-Config.senta";
					//projectLocation = (new File(projectFile)).getParent();
					//projectLocation = (new File((new File(projectFile)).getParent())).getParent();
					fileLocation.setText(projectFile);
					recentFile.setSelected(false);
					recentFile.setDisable(true);
					recentFilesList.setDisable(true);
					recentFilesStatusLabel.setDisable(true);
					recentFilesStatus.setDisable(true);
				}
					
				
				if (Parameters.getTypeOfProject()!=null) {
					type = Parameters.getTypeOfProject();
					projectType.setDisable(false);
					if (projectType.equals(Parameters.TypeOfProject.TESTSET)) {
						projectType.setText("Training set + Test set");
					} else {
						projectType.setText("Training set + Non-annotated set");
					}
				}
				
				if (Parameters.getImportedProjectName()!=null) {
					projectName.setDisable(false);
					projectName.setText(Parameters.getImportedProjectName());
				}
				
				
			} catch (NullPointerException e) {
				System.out.println("Problem here!");
			}
		}
		
		// Training set Location
		if (Parameters.getTrainingSetLocation()!=null) {
			trainingFile.setText(Parameters.getTrainingSetLocation());
			trainingFileStatusLabel.setDisable(false);
			trainingFileStatus.setDisable(false);
			trainingFile.setDisable(false);
		} else {
			trainingFile.setText("#N/A");
			trainingFileStatusLabel.setDisable(true);
			trainingFileStatus.setDisable(true);
			trainingFile.setDisable(true);
		}
		
		// Test set Location
		if (Parameters.getTestSetLocation()!=null) {
			testFile.setText(Parameters.getTestSetLocation());
			testFileStatusLabel.setDisable(false);
			testFileStatus.setDisable(false);
			testFile.setDisable(false);
		} else {
			testFile.setText("#N/A");
			testFileStatusLabel.setDisable(true);
			testFileStatus.setDisable(true);
			testFile.setDisable(true);
		}
		
		// Classes
		if (Parameters.getClasses()!=null && !Parameters.getClasses().isEmpty()) {
			classes.setText(Parameters.getClasses().toString());
			tempClasses = Parameters.getClasses();
		}
		
		// Features set
		if (Parameters.getFeaturesFileLocation()!=null && !Parameters.getFeaturesFileLocation().equals("")) {
			isFeaturesFile.setDisable(false);
			if (Parameters.isImportFeatures()) {
				isFeaturesFile.setSelected(true);
			}
			featuresFile.setText(Parameters.getFeaturesFileLocation());
			featuresFile.setDisable(false);
			featuresFileStatusLabel.setDisable(false);
			featuresFileStatus.setDisable(false);
		} else {
			featuresFile.setDisable(true);
			isFeaturesFile.setSelected(false);
			featuresFile.setText("#N/A");
			featuresFile.setDisable(true);
			featuresFileStatusLabel.setDisable(true);
			featuresFileStatus.setDisable(true);
		}
		
		// Top Words file
		if (Parameters.getTopWordsImportedFileLocation()!=null && !Parameters.getTopWordsImportedFileLocation().equals("")) {
			isTopWordsFile.setDisable(false);
			if (Parameters.isImportTopWords()) {
				isTopWordsFile.setSelected(true);
			}
			topWordsFile.setText(Parameters.getTopWordsImportedFileLocation());
			topWordsFile.setDisable(false);
			topWordsFileStatusLabel.setDisable(false);
			topWordsFileStatus.setDisable(false);
		} else {
			isTopWordsFile.setDisable(true);
			isTopWordsFile.setSelected(false);
			topWordsFile.setText("#N/A");
			topWordsFile.setDisable(true);
			topWordsFileStatusLabel.setDisable(true);
			topWordsFileStatus.setDisable(true);
		}
		
		// Basic Patterns file
		if (Parameters.getBasicPatternsImportedFileLocation()!=null && !Parameters.getBasicPatternsImportedFileLocation().equals("")) {
			isBasicPatternsFile.setDisable(false);
			if (Parameters.isImportBasicPatterns()) {
				isBasicPatternsFile.setSelected(true);
			}
			basicPatternsFile.setText(Parameters.getBasicPatternsImportedFileLocation());
			basicPatternsFile.setDisable(false);
			basicPatternsFileStatusLabel.setDisable(false);
			basicPatternsFileStatus.setDisable(false);
		} else {
			isBasicPatternsFile.setDisable(true);
			isBasicPatternsFile.setSelected(false);
			basicPatternsFile.setText("#N/A");
			basicPatternsFile.setDisable(true);
			basicPatternsFileStatusLabel.setDisable(true);
			basicPatternsFileStatus.setDisable(true);
		}
		
		// Advanced Patterns file
		if (Parameters.getAdvancedPatternsImportedFileLocation()!=null && !Parameters.getAdvancedPatternsImportedFileLocation().equals("")) {
			isAdvancedPatternsFile.setDisable(false);
			if (Parameters.isImportAdvancedPatterns()) {
				isAdvancedPatternsFile.setSelected(true);
			}
			advancedPatternsFile.setText(Parameters.getAdvancedPatternsImportedFileLocation());
			advancedPatternsFile.setDisable(false);
			advancedPatternsFileStatusLabel.setDisable(false);
			advancedPatternsFileStatus.setDisable(false);
		} else {
			isAdvancedPatternsFile.setDisable(true);
			isAdvancedPatternsFile.setSelected(false);
			advancedPatternsFile.setText("#N/A");
			advancedPatternsFile.setDisable(true);
			advancedPatternsFileStatusLabel.setDisable(true);
			advancedPatternsFileStatus.setDisable(true);
		}
	}
	
	private void initializeNew() {
		// Local Parameters
					isGotten = false;
					tempClasses = new ArrayList<>();
					
					// Disable until one Radio is selected
					fileLocation.setDisable(true);
					fileLocationSelect.setDisable(true);
					fileLocationStatusLabel.setDisable(true);
					fileLocationStatus.setDisable(true);
					fileLocationStatus.setText("No file selected");
					
					recentFilesList.setDisable(true);
					recentFilesStatusLabel.setDisable(true);
					recentFilesStatus.setDisable(true);
					recentFilesStatus.setText("No file selected");
					
					// project specifications
					projectType.setDisable(true);
					projectName.setDisable(true);
					
					// Training file
					trainingFile.setDisable(true);
					trainingFileStatusLabel.setDisable(true);
					trainingFileStatus.setDisable(true);
					trainingFileUpdate.setDisable(true);
					trainingFileStatus.setText("Non valid");
					
					// Test file
					testFile.setDisable(true);
					testFileStatusLabel.setDisable(true);
					testFileStatus.setDisable(true);
					testFileUpdate.setDisable(true);
					testFileStatus.setText("Non valid");
					
					// Classes
					classes.setText("No class");
					classes.setDisable(true);
					
					// Features file
					isFeaturesFile.setDisable(true);
					featuresFile.setDisable(true);
					featuresFileStatusLabel.setDisable(true);
					featuresFileStatus.setDisable(true);
					featuresFileStatus.setText("Non valid");
					
					// Top Words file
					isTopWordsFile.setDisable(true);
					topWordsFile.setDisable(true);
					topWordsFileStatusLabel.setDisable(true);
					topWordsFileStatus.setDisable(true);
					topWordsFileStatus.setText("Non valid");
					
					// Basic Patterns file
					isBasicPatternsFile.setDisable(true);
					basicPatternsFile.setDisable(true);
					basicPatternsFileStatusLabel.setDisable(true);
					basicPatternsFileStatus.setDisable(true);
					basicPatternsFileStatus.setText("Non valid");
					
					// Advanced Patterns file
					isAdvancedPatternsFile.setDisable(true);
					advancedPatternsFile.setDisable(true);
					advancedPatternsFileStatusLabel.setDisable(true);
					advancedPatternsFileStatus.setDisable(true);
					advancedPatternsFileStatus.setText("Non valid");
	}
	
	private void initializeCheckers() {
		// Open file status
				fileLocation.textProperty().addListener((observable, oldValue, newValue) -> {
					if(newValue.equals("")) {
						fileLocationStatus.setText("No file selected");
						fileLocationStatus.setTextFill(Color.BLACK);
					} else {
						if (Reader.isValidFile(newValue)) {
							if(Reader.isValidProjectFile(newValue)) {
								System.out.println("Valid file");
								fileLocationStatus.setText("Valid File!");
								fileLocationStatus.setTextFill(Color.GREEN);
							} else {
								System.out.println("Non Valid file");
								fileLocationStatus.setText("Non Valid File!");
								fileLocationStatus.setTextFill(Color.RED);
							}
						} else {
							System.out.println("Non existing file");
							fileLocationStatus.setText("Non existing File!");
							fileLocationStatus.setTextFill(Color.RED);
						}
					}
				});

				// Open recent file status
				recentFilesList.valueProperty().addListener((observable, oldValue, newValue) -> {
					if(newValue.equals("")) {
						recentFilesStatus.setText("No file selected");
						recentFilesStatus.setTextFill(Color.BLACK);
					} else {
						if (Reader.isValidFile(newValue)) {
							if(Reader.isValidProjectFile(newValue)) {
								System.out.println("Valid file");
								recentFilesStatus.setText("Valid File!");
								recentFilesStatus.setTextFill(Color.GREEN);
							} else {
								System.out.println("Non Valid file");
								recentFilesStatus.setText("Non Valid File!");
								recentFilesStatus.setTextFill(Color.RED);
							}
						} else {
							System.out.println("Non existing file");
							recentFilesStatus.setText("Non existing File!");
							recentFilesStatus.setTextFill(Color.RED);
						}
					}
				});
				
				// Training file
				trainingFile.textProperty().addListener((observable, oldValue, newValue) -> {
					if(newValue.equals("") || newValue.equals("#N/A")) {
						trainingFileStatus.setText("No file selected");
						trainingFileStatus.setTextFill(Color.BLACK);
					} else {
						if (Reader.isValidFile(newValue)) {
							if(Reader.isValidRawFile(newValue, true)) {
								System.out.println("Valid file");
								trainingFileStatus.setText("Valid File!");
								trainingFileStatus.setTextFill(Color.GREEN);
							} else {
								System.out.println("Non Valid");
								trainingFileStatus.setText("Non Valid File!");
								trainingFileStatus.setTextFill(Color.RED);
							}
						} else {
							System.out.println("Non existing file");
							trainingFileStatus.setText("Non existing File!");
							trainingFileStatus.setTextFill(Color.RED);
						}
					}
				});
				
				// Test file
				testFile.textProperty().addListener((observable, oldValue, newValue) -> {
					if(newValue.equals("") || newValue.equals("#N/A")) {
						testFileStatus.setText("No file selected");
						testFileStatus.setTextFill(Color.BLACK);
					} else {
						if (Reader.isValidFile(newValue)) {
							if(Reader.isValidRawFile(newValue, true)) {
								System.out.println("Valid file");
								testFileStatus.setText("Valid File!");
								testFileStatus.setTextFill(Color.GREEN);
							} else {
								System.out.println("Non Valid");
								testFileStatus.setText("Non Valid File!");
								testFileStatus.setTextFill(Color.RED);
							}
						} else {
							System.out.println("Non existing file");
							testFileStatus.setText("Non existing File!");
							testFileStatus.setTextFill(Color.RED);
						}
					}
				});

				// Features file
				featuresFile.textProperty().addListener((observable, oldValue, newValue) -> {
					if(newValue.equals("") || newValue.equals("#N/A")) {
						featuresFileStatus.setText("No file selected");
						featuresFileStatus.setTextFill(Color.BLACK);
					} else {
						if (Reader.isValidFile(newValue)) {
							System.out.println("Valid file");
							featuresFileStatus.setText("Valid File!");
							featuresFileStatus.setTextFill(Color.GREEN);
						} else {
							System.out.println("Non existing file");
							featuresFileStatus.setText("Non existing File!");
							featuresFileStatus.setTextFill(Color.RED);
						}
					}
				});
				
				// Top words file
				topWordsFile.textProperty().addListener((observable, oldValue, newValue) -> {
					if(newValue.equals("") || newValue.equals("#N/A")) {
						topWordsFileStatus.setText("No file selected");
						topWordsFileStatus.setTextFill(Color.BLACK);
					} else {
						if (Reader.isValidFile(newValue)) {
							if(Reader.isValidTopWordsFile(newValue)) {
								System.out.println("Valid file");
								topWordsFileStatus.setText("Valid File!");
								topWordsFileStatus.setTextFill(Color.GREEN);
							} else {
								System.out.println("Non Valid");
								topWordsFileStatus.setText("Non Valid File!");
								topWordsFileStatus.setTextFill(Color.RED);
							}
						} else {
							System.out.println("Non existing file");
							topWordsFileStatus.setText("Non existing File!");
							topWordsFileStatus.setTextFill(Color.RED);
						}
					}
				});
				
				// Basic Patterns File
				basicPatternsFile.textProperty().addListener((observable, oldValue, newValue) -> {
					if(newValue.equals("") || newValue.equals("#N/A")) {
						basicPatternsFileStatus.setText("No file selected");
						basicPatternsFileStatus.setTextFill(Color.BLACK);
					} else {
						if (Reader.isValidFile(newValue)) {
							if(Reader.isValidPatternsFile(newValue)) {
								System.out.println("Valid file");
								basicPatternsFileStatus.setText("Valid File!");
								basicPatternsFileStatus.setTextFill(Color.GREEN);
							} else {
								System.out.println("Non Valid");
								basicPatternsFileStatus.setText("Non Valid File!");
								basicPatternsFileStatus.setTextFill(Color.RED);
							}
						} else {
							System.out.println("Non existing file");
							basicPatternsFileStatus.setText("Non existing File!");
							basicPatternsFileStatus.setTextFill(Color.RED);
						}
					}
				});
				
				// Advanced Patterns File
				advancedPatternsFile.textProperty().addListener((observable, oldValue, newValue) -> {
					if(newValue.equals("") || newValue.equals("#N/A")) {
						advancedPatternsFileStatus.setText("No file selected");
						advancedPatternsFileStatus.setTextFill(Color.BLACK);
					} else {
						if (Reader.isValidFile(newValue)) {
							if(Reader.isValidPatternsFile(newValue)) {
								System.out.println("Valid file");
								advancedPatternsFileStatus.setText("Valid File!");
								advancedPatternsFileStatus.setTextFill(Color.GREEN);
							} else {
								System.out.println("Non Valid");
								advancedPatternsFileStatus.setText("Non Valid File!");
								advancedPatternsFileStatus.setTextFill(Color.RED);
							}
						} else {
							System.out.println("Non existing file");
							advancedPatternsFileStatus.setText("Non existing File!");
							advancedPatternsFileStatus.setTextFill(Color.RED);
						}
					}
				});
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
	
	private void getParameters(String file) {
		
		//projectLocation = (new File(file)).getParent();
		projectFile = new File(file).getPath();
		
		// TODO move all this to the reader class
		HashMap<String, String> projectConfig = Reader.openProject(file);
		
		// Goal of project
		if (projectConfig.containsKey("projectGoal")) {
			if (projectConfig.get("projectGoal").equals("CLASSIFICATION")) {
				Loader.setProjectGoal(Loader.ProjectGoal.CLASSIFICATION);
			} else if(projectConfig.get("projectGoal").equals("QUANTIFICATION")) {
				Loader.setProjectGoal(Loader.ProjectGoal.QUANTIFICATION);
			}
		}
		
		// Type of project
		if (projectConfig.containsKey("typeOfProject")) {
			if (projectConfig.get("typeOfProject").equals("TESTSET")) {
				projectType.setDisable(false);
				projectType.setText("Training set + Test set");
				type = Parameters.TypeOfProject.TESTSET;
			} else if (projectConfig.get("typeOfProject").equals("NONANNOTATEDSET")) {
				projectType.setDisable(false);
				projectType.setText("Training set + Non-annotated set");
				type = Parameters.TypeOfProject.NONANNOTATEDSET;
			}
		}
		
		// Project Name
		if (projectConfig.containsKey("projectName")) {
			projectName.setDisable(false);
			projectName.setText(projectConfig.get("projectName"));
		}
		
		// Training set file
		if (projectConfig.containsKey("trainingSetLocation")) {
			trainingFile.setDisable(false);
			trainingFile.setText(projectConfig.get("trainingSetLocation"));
			trainingFileStatusLabel.setDisable(false);
			trainingFileStatus.setDisable(false);
			trainingFileUpdate.setDisable(false);
		}
		
		// Test set file
		if (projectConfig.containsKey("testSetLocation")) {
			testFile.setDisable(false);
			testFile.setText(projectConfig.get("testSetLocation"));
			testFileStatusLabel.setDisable(false);
			testFileStatus.setDisable(false);
			testFileUpdate.setDisable(false);
		}
		
		// Classes
		if (projectConfig.containsKey("classes")) {
			classes.setDisable(false);
			ArrayList<String> cl = new ArrayList<String>(Arrays.asList(projectConfig.get("classes").split("#")));
			tempClasses = cl;
			classes.setText(cl.toString());
		}
		
//		// How to read the files
//		if (projectConfig.containsKey("textIdPosition")) {
//			Parameters.setTextIdPosition(Integer.parseInt(projectConfig.get("textIdPosition")));
//		}
//		if (projectConfig.containsKey("userNamePosition")) {
//			Parameters.setUserNamePosition(Integer.parseInt(projectConfig.get("userNamePosition")));
//		}
//		if (projectConfig.containsKey("textPosition")) {
//			Parameters.setTextPosition(Integer.parseInt(projectConfig.get("textPosition")));
//		}
//		if (projectConfig.containsKey("classPosition")) {
//			Parameters.setClassPosition(Integer.parseInt(projectConfig.get("classPosition")));
//		}
//		
//		if (projectConfig.containsKey("indexPosition")) {
//			Parameters.setIndexPosition(Integer.parseInt(projectConfig.get("indexPosition")));
//		}
		
		// Features file
		if (projectConfig.containsKey("featuresFileLocation")) {
			isFeaturesFile.setDisable(false);
			featuresFile.setDisable(false);
			featuresFile.setText(projectConfig.get("featuresFileLocation"));
			featuresFileStatusLabel.setDisable(false);
			featuresFileStatus.setDisable(false);
		}
		
		// Top Words file
		if (projectConfig.containsKey("topWordsFileExists")) {
			if (projectConfig.get("topWordsFileExists").equals("true")) {
				isTopWordsFile.setDisable(false);
				topWordsFile.setDisable(false);
				if (projectConfig.containsKey("topWordsFileLocation")) {
					topWordsFile.setText(projectConfig.get("topWordsFileLocation"));
					topWordsFileStatusLabel.setDisable(false);
					topWordsFileStatus.setDisable(false);
				} else {
					topWordsFile.setText("No Top Words file found");
				}
				
			} else {
				topWordsFile.setText("No Top Words file found");
			}
		}
		
		// Basic Patterns file
		if (projectConfig.containsKey("basicPatternsFileExists")) {
			if (projectConfig.get("basicPatternsFileExists").equals("true")) {
				isBasicPatternsFile.setDisable(false);
				basicPatternsFile.setDisable(false);
				if (projectConfig.containsKey("basicPatternsFileLocation")) {
					basicPatternsFile.setText(projectConfig.get("basicPatternsFileLocation"));
					basicPatternsFileStatusLabel.setDisable(false);
					basicPatternsFileStatus.setDisable(false);
				} else {
					basicPatternsFile.setText("No basic patterns file found");
				}
				
			} else {
				basicPatternsFile.setText("No basic patterns file found");
			}
		}
		
		// Advanced Patterns file
		if (projectConfig.containsKey("advancedPatternsFileExists")) {
			if (projectConfig.get("advancedPatternsFileExists").equals("true")) {
				isAdvancedPatternsFile.setDisable(false);
				advancedPatternsFile.setDisable(false);
				if (projectConfig.containsKey("advancedPatternsFileLocation")) {
					advancedPatternsFile.setText(projectConfig.get("advancedPatternsFileLocation"));
					advancedPatternsFileStatusLabel.setDisable(false);
					advancedPatternsFileStatus.setDisable(false);
				} else {
					advancedPatternsFile.setText("No advanced patterns file found");
				}
				
			} else {
				advancedPatternsFile.setText("No advanced patterns file found");
			}
		}
		
	}
	
	/**
	 * Private methods that handles saving the parameters
	 */
	private void saveParameters() {
		
		Parameters.setImportedProjectDirectory(new File(new File(projectFile).getParent()).getParent());
		Parameters.setImportedProjectName(projectName.getText());
		
		Parameters.setTrainingSetLocation(trainingFile.getText());
		Parameters.setTestSetLocation(testFile.getText());
		
		// Learn how to read a file
		Reader.getAttributePositions(trainingFile.getText());
		
		Parameters.setTypeOfProject(type);
		
		Parameters.setClasses(tempClasses);
		
		if (isFeaturesFile.isSelected()) {
			Reader.importFeatures(featuresFile.getText());
			Parameters.setImportFeatures(true);
			Parameters.setFeaturesFileLocation(featuresFile.getText());
		} else {
			Parameters.setImportFeatures(false);
			Parameters.setFeaturesFileLocation(null);
		}
		
		if (isTopWordsFile.isSelected()) {
			Parameters.setImportTopWords(true);
			Parameters.setTopWordsImportedFileLocation(topWordsFile.getText());
		} else {
			Parameters.setImportTopWords(false);
			Parameters.setTopWordsImportedFileLocation(null);
		}
		
		if (isBasicPatternsFile.isSelected()) {
			Parameters.setImportBasicPatterns(true);
			Parameters.setBasicPatternsImportedFileLocation(basicPatternsFile.getText());
		} else {
			Parameters.setImportBasicPatterns(false);
			Parameters.setBasicPatternsImportedFileLocation(null);
		}
		
		if (isAdvancedPatternsFile.isSelected()) {
			Parameters.setImportAdvancedPatterns(true);
			Parameters.setAdvancedPatternsImportedFileLocation(advancedPatternsFile.getText());
		} else {
			Parameters.setImportAdvancedPatterns(false);
			Parameters.setAdvancedPatternsImportedFileLocation(null);
		}
		
		
		
	}

	/**
	 * Private methods that checks if all the parameters are good
	 * @return
	 */
	private String isGoodParameters() {
		// File selection
		if (!isGotten) {
			return "noFileSelected";
		}
		
		// Project Type
		if (type==null) {
			return "projectType";
		} else {
			if (!type.equals(Parameters.TypeOfProject.TESTSET)
					&& !type.equals(Parameters.TypeOfProject.NONANNOTATEDSET)) {
				return "projectType";
			} 
		}
		// Project Name
		if (projectName.getText()==null || projectName.getText().equals("")) {
			return "projectName";
		}
		// Training set file
		if (Reader.isValidFile(trainingFile.getText())) {
			if (!Reader.isValidRawFile(trainingFile.getText(), true)) {
				return "trainingFileNonValid";
			}
		} else {
			return "trainingFileNonExisting";
		}
		// Test set File
		if (Reader.isValidFile(testFile.getText())) {
			if (projectType.equals("Training set + Test set")) {
				if (!Reader.isValidRawFile(testFile.getText(), true)) {
					return "testFileNonValid";
				}
			} else if(projectType.equals("Training set + Non-annotated set")) {
				if (!Reader.isValidRawFile(testFile.getText(), false)) {
					return "testFileNonValid";
				}
			}
		} else {
			return "testFileNonExisting";
		}
		
		if (tempClasses==null || tempClasses.isEmpty()) {
			return "classes";
		}
		
		if (isFeaturesFile.isSelected() && !Reader.isValidFile(featuresFile.getText())) {
			return "featuresFile";
		}
		if (isTopWordsFile.isSelected() && !Reader.isValidTopWordsFile(topWordsFile.getText())) {
			return "topWords";
		}
		if (isBasicPatternsFile.isSelected() && !Reader.isValidPatternsFile(basicPatternsFile.getText())) {
			return "basicPatterns";
		}
		if (isAdvancedPatternsFile.isSelected() && !Reader.isValidPatternsFile(advancedPatternsFile.getText())) {
			return "advancedPatterns";
		}
		
		return "fine";
	}
	
	
	
}

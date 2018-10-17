package windows.main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;

import backend.classifiers.HoeffdingTreeClassifier;
import backend.classifiers.J48Classifier;
import backend.classifiers.KStarClassifier;
import backend.classifiers.NaiveBayesClassifier;
import backend.classifiers.NaiveBayesUpdateableClassifier;
import backend.classifiers.RandomForestClassifier;
import commons.constants.Commons;
import commons.constants.Constants;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import main.items.ClassifierParameters;
import main.items.Parameters;
import main.start.Main;
import main.start.ClassificationProblem;
import main.start.Loader;
import windows.classifiers.ClassifiersWindowsManager;
import windows.classifiers.selectfeatures.FeatureSelectorWindowsManager;
import windows.others.AlertBox;
import windows.others.ConfirmBox;

public class ClassifiersWindow implements Initializable {
	
	// =====================================//
	//          Non-FXML Components         //
	// =====================================//
	
	private static enum Task {
		CROSSVALIDATION,
		TRAININGSPLIT,
		TESTSET,
		ANNOTATION
	}
	
	private static enum ErrorCode {
		No_Classifier_Selected,
		No_Task_Selected,
		Percentage_Not_Valid,
		Training_File_Missing,
		Test_File_Missing,
		Non_Annotated_File_Missing,
		Fine
	}
	
	private static Task task;
	
	private static int splitPercentage;
	
	private static HashMap<String, String> previousResults;
	private static ArrayList<String> previousResultsTitles;
	protected static IntegerProperty countOfResults;
	
	protected static Thread background;
	
	protected static boolean isFeaturesSelected;
	
	
	// =====================================//
	//            FXML Components           //
	// =====================================//
	
	@FXML ComboBox<String> classifiersList;
	@FXML Button parametersButton;
	
	@FXML ToggleGroup actions;
	
	@FXML RadioButton trainingXValidation;
	@FXML RadioButton traininsSplit;
	@FXML RadioButton testEvaluation;
	@FXML RadioButton annotateUnknown;
	
	@FXML TextField percentageField;
	@FXML Label percentageLabel;
	
	@FXML Button chooseFeaturesButton;
	@FXML Button startButton;
	
	@FXML TextArea output;
	
	@FXML ListView<String> previousResultsList;
	@FXML Button displayButton;
	
	@FXML ProgressBar progressBar;
	
	@FXML ProgressIndicator indicator;
	
	@FXML Button backButton;
	@FXML Button nextButton;
	@FXML Button cancelButton;
	
	
	// =====================================//
	//        FXML Components Actions       //
	// =====================================//
	
	@FXML public void handleParametersButton() {
		System.out.println("Parameters Button clicked");
		if (classifiersList.getValue()==null) {
			AlertBox.display("Please choose a classifier", "You need to choose a classifier first, before proceeding", "OK");
		} else {
			if (classifiersList.getValue().equals("Random Forest")) {
				ClassifiersWindowsManager.displayParametersWindow(ClassifierParameters.Classifier.RANDOM_FOREST);
			}
			
			if (classifiersList.getValue().equals("Naive Bayes")) {
				ClassifiersWindowsManager.displayParametersWindow(ClassifierParameters.Classifier.NAIVE_BAYES);
			}
			
			if (classifiersList.getValue().equals("Naive Bayes Updateable")) {
				ClassifiersWindowsManager.displayParametersWindow(ClassifierParameters.Classifier.NAIVE_BAYES_UPDATEABLE);
			}
			
			if (classifiersList.getValue().equals("Iterative Dichotomiser 3 (J48)")) {
				ClassifiersWindowsManager.displayParametersWindow(ClassifierParameters.Classifier.J48);
			}
			
			if (classifiersList.getValue().equals("K-Star (K*)")) {
				ClassifiersWindowsManager.displayParametersWindow(ClassifierParameters.Classifier.K_STAR);
			}
			
			if (classifiersList.getValue().equals("Hoeffding Tree")) {
				ClassifiersWindowsManager.displayParametersWindow(ClassifierParameters.Classifier.HOEFFDING_TREE);
			}
			
			
			if (classifiersList.getValue().equals("Support Vector Machine (SVM)")) {
				// TODO remove this and replace it with the actual parameters window
				AlertBox.display("Coming soon", "The Support Vector Machine (SVM) classifier has not been implemented yet. It will be added soon.", "OK");
			}
		}
		
	}
	
	@FXML public void handleRadios() {
		if (trainingXValidation.isSelected()) {
			task = Task.CROSSVALIDATION;
			percentageField.setDisable(true);
			percentageLabel.setDisable(true);
		} else if (traininsSplit.isSelected()) {
			task = Task.TRAININGSPLIT;
			percentageField.setDisable(false);
			percentageLabel.setDisable(false);
		} else if (testEvaluation.isSelected()) {
			task = Task.TESTSET;
			percentageField.setDisable(true);
			percentageLabel.setDisable(true);
		} else if (annotateUnknown.isSelected()) {
			task = Task.ANNOTATION;
			percentageField.setDisable(true);
			percentageLabel.setDisable(true);
		}
	}
	
	@FXML public void handleStartButton() {
		
		startAction();
//		System.out.println("Start Button clicked");
//		
//		if (task == null) {
//			Commons.print("Oh no, the task is null!");
//		}
//		
//		if (classifiersList.getValue()== null || classifiersList.getValue().isEmpty()) {
//			AlertBox.display("Choose a classifier", "Please choose a classifier from the list of classifiers first", "OK");
//		} else {
//			// Set the parameter {@ClassifierParameters.classifer}
//			if (classifiersList.getValue().equals("Random Forest")) {
//				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.RANDOM_FOREST);
//			} else if (classifiersList.getValue().equals("Naive Bayes")) {
//				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.NAIVE_BAYES);
//			} else if (classifiersList.getValue().equals("Naive Bayes Updateable")) {
//				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.NAIVE_BAYES_UPDATEABLE);
//			} else if (classifiersList.getValue().equals("Iterative Dichotomiser 3 (J48)")) {
//				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.J48);
//			} else if (classifiersList.getValue().equals("K-Star (K*)")) {
//				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.K_STAR);
//			} else if (classifiersList.getValue().equals("Hoeffding Tree")) {
//				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.HOEFFDING_TREE);
//			} else if (classifiersList.getValue().equals("Support Vector Machine (SVM)")) {
//				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.SVM);
//			} else {
//				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.NONE);
//			}
//			
//			if (task==null) {
//				AlertBox.display("Choose a task", "Please choose a valid task to perform from the list of actions provided first", "OK");
//			} else {
//				// Perform the classification using the classifier chosen
//				if (ClassifierParameters.getClassifier().equals(ClassifierParameters.Classifier.RANDOM_FOREST)) {
//					classifyWithRandowForest();
//				} else if (ClassifierParameters.getClassifier().equals(ClassifierParameters.Classifier.NAIVE_BAYES)) {
//					classifyWithNaiveBayes();
//				} else if (ClassifierParameters.getClassifier().equals(ClassifierParameters.Classifier.NAIVE_BAYES_UPDATEABLE)) {
//					classifyWithNaiveBayesUpdateable();
//				} else if (ClassifierParameters.getClassifier().equals(ClassifierParameters.Classifier.J48)) {
//					classifyWithJ48();
//				} else if (ClassifierParameters.getClassifier().equals(ClassifierParameters.Classifier.K_STAR)) {
//					classifyWithKStar();
//				} else if (ClassifierParameters.getClassifier().equals(ClassifierParameters.Classifier.HOEFFDING_TREE)) {
//					classifyWithKHoeffdingTree();
//				} else if (ClassifierParameters.getClassifier().equals(ClassifierParameters.Classifier.SVM)) {
//					classifyWithSVM();
//				}
//				if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
//					nextButton.setDisable(false);
//				}
//			}
//		}
	}
	
	@FXML public void handleChooseFeaturesButton() {
		System.out.println("Choose Features Button clicked");
		
		FeatureSelectorWindowsManager.displayParametersWindow();
	}
	
	@FXML public void handleDisplayButton() {
		
		String chosen = previousResultsList.getSelectionModel().getSelectedItem();
		
		if (previousResults.containsKey(chosen)) {
			output.setText(previousResults.get(chosen));
		}
	}
	
	@FXML public void handleBackButton() {
		System.out.println("Back Button clicked");
		try {
			Main.root = FXMLLoader.load(getClass().getResource("/windows/main/EndWindow.fxml"));
			Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
			Main.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void handleNextButton() {
		System.out.println("Next Button clicked");
		if (ConfirmBox.display("Proceed?", "The quantification will use the last classification results you have run (not the one displayed). "
				+ "Are you sure you want to continues?")) {
			
			try {
				Main.root = FXMLLoader.load(getClass().getResource("/windows/main/QuantifierWindow.fxml"));
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
		
		isFeaturesSelected = false;
		splitPercentage = 0;
		
		indicator.setProgress(1);
		
		// Make sure no task that has been previously stored is selected
		task = null;
		
		percentageField.setDisable(true);
		percentageLabel.setDisable(true);
		
		// Previous results-related
		previousResults = new HashMap<>();
		previousResultsTitles = new ArrayList<>();
		countOfResults = new SimpleIntegerProperty(0);
				
		countOfResults.addListener((observable, oldValue, newValue) -> {
			if (newValue!=null) {
				if (!newValue.equals(oldValue)) {
					int index = newValue.intValue() - 1;
					javafx.application.Platform.runLater( () -> {
						previousResultsList.getItems().add(previousResultsTitles.get(index));
						previousResultsList.refresh();
					});
				}
			}
			
		});
		
		if (classifiersList.getItems().isEmpty()) {
			classifiersList.getItems().addAll("Random Forest", "Naive Bayes", "Naive Bayes Updateable", "Iterative Dichotomiser 3 (J48)", "K-Star (K*)", "Hoeffding Tree", "Support Vector Machine (SVM)");
		}
		
		nextButton.setDisable(true);
		
		if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
			cancelButton.setText("Exit");
		}
		
		output.setText("");
		
		output.setEditable(false);
				
		RandomForestClassifier.getOutputText().addListener((observable, oldValue, newValue) -> {
			if (newValue!=null) {
				if(!newValue.equals(oldValue)) {

					javafx.application.Platform.runLater( () -> {
						int start = 0;
						int end = 0;
						if (output.getSelectedText()!=null) {
							start = output.getSelection().getStart();
							end = output.getSelection().getEnd();
						}
						output.setText(RandomForestClassifier.getOutputText().get());
						if (output.getText()!=null) {
							if (output.getText().length()>end && start>0 && end>start) {
								output.selectRange(start, end);
							}
						}
					});
				}
			}
			
		});
		
		NaiveBayesClassifier.getOutputText().addListener((observable, oldValue, newValue) -> {
			if (newValue!=null) {
				if(!newValue.equals(oldValue)) {

					javafx.application.Platform.runLater( () -> {
						int start = 0;
						int end = 0;
						if (output.getSelectedText()!=null) {
							start = output.getSelection().getStart();
							end = output.getSelection().getEnd();
						}
						output.setText(NaiveBayesClassifier.getOutputText().get());
						if (output.getText()!=null) {
							if (output.getText().length()>end && start>0 && end>start) {
								output.selectRange(start, end);
							}
						}
					});
				}
			}
			
		});
		
		NaiveBayesUpdateableClassifier.getOutputText().addListener((observable, oldValue, newValue) -> {
			if (newValue!=null) {
				if(!newValue.equals(oldValue)) {

					javafx.application.Platform.runLater( () -> {
						int start = 0;
						int end = 0;
						if (output.getSelectedText()!=null) {
							start = output.getSelection().getStart();
							end = output.getSelection().getEnd();
						}
						output.setText(NaiveBayesUpdateableClassifier.getOutputText().get());
						if (output.getText()!=null) {
							if (output.getText().length()>end && start>0 && end>start) {
								output.selectRange(start, end);
							}
						}
					});
				}
			}
			
		});
		
		J48Classifier.getOutputText().addListener((observable, oldValue, newValue) -> {
			if (newValue!=null) {
				if(!newValue.equals(oldValue)) {

					javafx.application.Platform.runLater( () -> {
						int start = 0;
						int end = 0;
						if (output.getSelectedText()!=null) {
							start = output.getSelection().getStart();
							end = output.getSelection().getEnd();
						}
						output.setText(J48Classifier.getOutputText().get());
						if (output.getText()!=null) {
							if (output.getText().length()>end && start>0 && end>start) {
								output.selectRange(start, end);
							}
						}
					});
				}
			}
			
		});
		
		KStarClassifier.getOutputText().addListener((observable, oldValue, newValue) -> {
			if (newValue!=null) {
				if(!newValue.equals(oldValue)) {

					javafx.application.Platform.runLater( () -> {
						int start = 0;
						int end = 0;
						if (output.getSelectedText()!=null) {
							start = output.getSelection().getStart();
							end = output.getSelection().getEnd();
						}
						output.setText(KStarClassifier.getOutputText().get());
						if (output.getText()!=null) {
							if (output.getText().length()>end && start>0 && end>start) {
								output.selectRange(start, end);
							}
						}
					});
				}
			}
			
		});
		
		HoeffdingTreeClassifier.getOutputText().addListener((observable, oldValue, newValue) -> {
			if (newValue!=null) {
				if(!newValue.equals(oldValue)) {

					javafx.application.Platform.runLater( () -> {
						int start = 0;
						int end = 0;
						if (output.getSelectedText()!=null) {
							start = output.getSelection().getStart();
							end = output.getSelection().getEnd();
						}
						output.setText(HoeffdingTreeClassifier.getOutputText().get());
						if (output.getText()!=null) {
							if (output.getText().length()>end && start>0 && end>start) {
								output.selectRange(start, end);
							}
						}
					});
				}
			}
			
		});
		
	}
	
	
	
	// =====================================//
	//             START METHODS            //
	// =====================================//
	
	public void startAction() {
		
		ErrorCode code = checkParameters();
				
		if (code.equals(ErrorCode.No_Classifier_Selected)) {
			AlertBox.display("Choose a classifier", "Please choose a classifier from the list of classifiers first", "OK");
			ClassifierParameters.setClassifier(ClassifierParameters.Classifier.NONE);
		} else if (code.equals(ErrorCode.No_Task_Selected)) {
			AlertBox.display("Choose a task", "Please choose a valid task to perform from the list of actions provided first", "OK");
		} else if (code.equals(ErrorCode.Training_File_Missing)) {
			AlertBox.display("No Training File", "The Training file was not create. Could not run the classification without it", "OK");
		} else if (code.equals(ErrorCode.Test_File_Missing)) {
			AlertBox.display("No Test File", "The test file was not create. Could not run the classification without it", "OK");
		} else if (code.equals(ErrorCode.Non_Annotated_File_Missing)) {
			AlertBox.display("No Test File", "The file containing the non-annotated data was not create. Could not run the classification without it", "OK");
		} else if (code.equals(ErrorCode.Percentage_Not_Valid)) {
			AlertBox.display("Split percentage", "Please choose a valid percentage for the training set split (integer between 1 and 99)", "OK");
		}
		
		else if (code.equals(ErrorCode.Fine)){
			// Set the parameter {@ClassifierParameters.classifer}
			if (classifiersList.getValue().equals("Random Forest")) {
				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.RANDOM_FOREST);
			} else if (classifiersList.getValue().equals("Naive Bayes")) {
				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.NAIVE_BAYES);
			} else if (classifiersList.getValue().equals("Naive Bayes Updateable")) {
				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.NAIVE_BAYES_UPDATEABLE);
			} else if (classifiersList.getValue().equals("Iterative Dichotomiser 3 (J48)")) {
				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.J48);
			} else if (classifiersList.getValue().equals("K-Star (K*)")) {
				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.K_STAR);
			} else if (classifiersList.getValue().equals("Hoeffding Tree")) {
				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.HOEFFDING_TREE);
			} else if (classifiersList.getValue().equals("Support Vector Machine (SVM)")) {
				ClassifierParameters.setClassifier(ClassifierParameters.Classifier.SVM);
			} else {
				
			}
			
			if (task==null) {
				
			} else {
				// Perform the classification using the classifier chosen
				if (ClassifierParameters.getClassifier().equals(ClassifierParameters.Classifier.RANDOM_FOREST)) {
					classifyWithRandowForest();
				} else if (ClassifierParameters.getClassifier().equals(ClassifierParameters.Classifier.NAIVE_BAYES)) {
					classifyWithNaiveBayes();
				} else if (ClassifierParameters.getClassifier().equals(ClassifierParameters.Classifier.NAIVE_BAYES_UPDATEABLE)) {
					classifyWithNaiveBayesUpdateable();
				} else if (ClassifierParameters.getClassifier().equals(ClassifierParameters.Classifier.J48)) {
					classifyWithJ48();
				} else if (ClassifierParameters.getClassifier().equals(ClassifierParameters.Classifier.K_STAR)) {
					classifyWithKStar();
				} else if (ClassifierParameters.getClassifier().equals(ClassifierParameters.Classifier.HOEFFDING_TREE)) {
					classifyWithKHoeffdingTree();
				} else if (ClassifierParameters.getClassifier().equals(ClassifierParameters.Classifier.SVM)) {
					classifyWithSVM();
				}
				if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
					nextButton.setDisable(false);
				}
			}
		}
	}
	
	/**
	 * check whether the ARFF files exist and create them if they don't
	 * @return
	 */
	private ErrorCode checkParameters() {
		String trainingArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\Training.arff";
		String testArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + "Test.arff";
		String nonAnnotatedArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + "NonAnnotated.arff";
		
		Commons.print(trainingArff);
		Commons.print(testArff);
		Commons.print(nonAnnotatedArff);
				
		File trainingFile = new File(trainingArff);
		
		if (!trainingFile.exists()) {
			String question = "The following file could not be found:\n"+ trainingArff + "\n"
					+ "Either you have not chosen to create it or it has been removed.\n"
					+ "Do you want to (re)create it?";

			boolean createTraining = ConfirmBox.display("File inexistant", question);
			
			if (createTraining) {
				ClassificationProblem.saveArffFile("Training", Parameters.getTrainingSet());
			} else {
				return ErrorCode.Training_File_Missing;
			}
		}
		
		if (task == null) {
			return ErrorCode.No_Task_Selected;
		}
		else {
			if (task.equals(Task.TRAININGSPLIT)) {
				if (checkInteger(percentageField.getText())) {
					int percentage = Integer.parseInt(percentageField.getText());
					if (percentage > 99 || percentage < 1) {
						return ErrorCode.Percentage_Not_Valid;
					}
				} else {
					return ErrorCode.Percentage_Not_Valid;
				}
			}
			if (task.equals(Task.TESTSET)) {
				File testFile = new File(testArff);

				if (!testFile.exists()) {
					String question = "The following file could not be found:\n" + testArff + "\n"
							+ "Either you have not chosen to create it or it has been removed.\n"
							+ "Do you want to (re)create it?";

					boolean createTest = ConfirmBox.display("File inexistant", question);

					if (createTest) {
						ClassificationProblem.saveArffFile("Test", Parameters.getTestSet());
					} else {
						return ErrorCode.Test_File_Missing;
					}
				}
			}
			if (task.equals(Task.ANNOTATION)) {
				File nonAnnotatedFile = new File(nonAnnotatedArff);

				if (!nonAnnotatedFile.exists()) {
					String question = "The following file could not be found:\n" + nonAnnotatedArff + "\n"
							+ "Either you have not chosen to create it or it has been removed.\n"
							+ "Do you want to (re)create it?";

					boolean createNonAnnotated = ConfirmBox.display("File inexistant", question);

					if (createNonAnnotated) {
						ClassificationProblem.saveArffFile("Non-Annotated", Parameters.getTestSet());
					} else {
						return ErrorCode.Non_Annotated_File_Missing;
					}
				}
			} 
		}
		
		return ErrorCode.Fine;
	}

	
	// =====================================//
	//        CLASSIFICATION METHODS        //
	// =====================================//
	
	/**
	 * Run the classification using the classifier [Random Forest].
	 */
	private void classifyWithRandowForest() {
		
		String trainingArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\Training.arff";
		String testArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + "Test.arff";
		String nonAnnotatedArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + "NonAnnotated.arff";
		
		
		if(checkParameters().equals(ErrorCode.Fine)) {
			Runnable runnable = new Runnable() {
				public void run() {
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	indicator.setProgress(-1);
					    	startButton.setDisable(true);
					    	chooseFeaturesButton.setDisable(true);
					    }
					});
					
					String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
					
					String tsk = "[" + timeStamp + "] - Random Forest - ";
					
					RandomForestClassifier.setParameters();
					
					if (task.equals(Task.CROSSVALIDATION)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							RandomForestClassifier.crossValidate(trainingArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							RandomForestClassifier.crossValidate(trainingArff, Constants.mainClasses);
						}
						tsk += "X-Validation";
						
					} else if (task.equals(Task.TRAININGSPLIT)) {
						splitPercentage = Integer.parseInt(percentageField.getText());
						
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							RandomForestClassifier.trainingSplit(trainingArff, splitPercentage, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							RandomForestClassifier.trainingSplit(trainingArff, splitPercentage, Constants.mainClasses);
						}
						tsk += "Training set split";
						
					} else if (task.equals(Task.TESTSET)) {
						
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							RandomForestClassifier.test(trainingArff, testArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							RandomForestClassifier.test(trainingArff, testArff, Constants.mainClasses);
						}
						tsk += "Test";
						
					} else if (task.equals(Task.ANNOTATION)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							RandomForestClassifier.test(trainingArff, nonAnnotatedArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							RandomForestClassifier.test(trainingArff, nonAnnotatedArff, Constants.mainClasses);
						}
						tsk += "Annotation";
						
					}
					
					previousResults.put(tsk, RandomForestClassifier.getOutputText().get());
					previousResultsTitles.add(tsk);
					countOfResults.set(countOfResults.getValue() + 1);
					
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	indicator.setProgress(1);
					    	startButton.setDisable(false);
					    	chooseFeaturesButton.setDisable(false);
					    }
					});
				}
			};

			background = new Thread(runnable);

			background.setDaemon(true);

			background.start();

		}
		
	}
	
	/**
	 * Run the classification using the classifier [Naive Bayes Adaptable].
	 */
	private void classifyWithNaiveBayes() {
		String trainingArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\Training.arff";
		String testArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + "Test.arff";
		String nonAnnotatedArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + "NonAnnotated.arff";
		
		if (checkParameters().equals(ErrorCode.Fine)) {
			Runnable runnable = new Runnable() {
				public void run() {
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	indicator.setProgress(-1);
					    	startButton.setDisable(true);
					    	chooseFeaturesButton.setDisable(true);
					    }
					});
					String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
					
					String tsk = "[" + timeStamp + "] - Naive Bayes - ";
					
					NaiveBayesClassifier.setParameters();
					
					if (task.equals(Task.CROSSVALIDATION)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							NaiveBayesClassifier.crossValidate(trainingArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							NaiveBayesClassifier.crossValidate(trainingArff, Constants.mainClasses);
						}
						tsk += "X-Validation";
						
					} else if (task.equals(Task.TRAININGSPLIT)) {
						splitPercentage = Integer.parseInt(percentageField.getText());
						
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							NaiveBayesClassifier.trainingSplit(trainingArff, splitPercentage, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							NaiveBayesClassifier.trainingSplit(trainingArff, splitPercentage, Constants.mainClasses);
						}
						tsk += "Training set split";
						
					} else if (task.equals(Task.TESTSET)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							NaiveBayesClassifier.test(trainingArff, testArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							NaiveBayesClassifier.test(trainingArff, testArff, Constants.mainClasses);
						}
						tsk += "Test";
						
					} else if (task.equals(Task.ANNOTATION)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							NaiveBayesClassifier.test(trainingArff, nonAnnotatedArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							NaiveBayesClassifier.test(trainingArff, nonAnnotatedArff, Constants.mainClasses);
						}
						tsk += "Annotation";
						
					}
					
					previousResults.put(tsk, NaiveBayesClassifier.getOutputText().get());
					previousResultsTitles.add(tsk);
					countOfResults.set(countOfResults.getValue() + 1);

					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	indicator.setProgress(1);
					    	startButton.setDisable(false);
					    	chooseFeaturesButton.setDisable(false);
					    }
					});
				}
			};

			background = new Thread(runnable);

			background.setDaemon(true);

			background.start();
		}
	}
	
	/**
	 * Run the classification using the classifier [Naive Bayes Adaptable].
	 */
	private void classifyWithNaiveBayesUpdateable() {
		String trainingArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\Training.arff";
		String testArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + "Test.arff";
		String nonAnnotatedArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + "NonAnnotated.arff";
		
		if (checkParameters().equals(ErrorCode.Fine)) {
			Runnable runnable = new Runnable() {
				public void run() {
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	indicator.setProgress(-1);
					    	startButton.setDisable(true);
					    	chooseFeaturesButton.setDisable(true);
					    }
					});
					String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
					
					String tsk = "[" + timeStamp + "] - Naive Bayes Updateable - ";
					
					NaiveBayesUpdateableClassifier.setParameters();
					
					if (task.equals(Task.CROSSVALIDATION)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							NaiveBayesUpdateableClassifier.crossValidate(trainingArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							NaiveBayesUpdateableClassifier.crossValidate(trainingArff ,Constants.mainClasses);
						}
						
						tsk += "X-Validation";
					} else if (task.equals(Task.TRAININGSPLIT)) {
						splitPercentage = Integer.parseInt(percentageField.getText());
						
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							NaiveBayesUpdateableClassifier.trainingSplit(trainingArff, splitPercentage, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							NaiveBayesUpdateableClassifier.trainingSplit(trainingArff, splitPercentage, Constants.mainClasses);
						}
						tsk += "Training set split";
						
					} else if (task.equals(Task.TESTSET)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							NaiveBayesUpdateableClassifier.test(trainingArff, testArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							NaiveBayesUpdateableClassifier.test(trainingArff, testArff, Constants.mainClasses);
						}
						tsk += "Test";
						
					} else if (task.equals(Task.ANNOTATION)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							NaiveBayesUpdateableClassifier.test(trainingArff, nonAnnotatedArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							NaiveBayesUpdateableClassifier.test(trainingArff, nonAnnotatedArff, Constants.mainClasses);
						}
						
						tsk += "Annotation";
					}
					
					previousResults.put(tsk, NaiveBayesUpdateableClassifier.getOutputText().get());
					previousResultsTitles.add(tsk);
					countOfResults.set(countOfResults.getValue() + 1);
					
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	indicator.setProgress(1);
					    	startButton.setDisable(false);
					    	chooseFeaturesButton.setDisable(false);
					    }
					});
				}
			};

			background = new Thread(runnable);

			background.setDaemon(true);

			background.start();
		}
	}
	
	/**
	 * Run the classification using the classifier [J48].
	 */
	private void classifyWithJ48() {
		String trainingArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\Training.arff";
		String testArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + "Test.arff";
		String nonAnnotatedArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + "NonAnnotated.arff";
		
		if (checkParameters().equals(ErrorCode.Fine)) {
			Runnable runnable = new Runnable() {
				public void run() {
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	indicator.setProgress(-1);
					    	startButton.setDisable(true);
					    	chooseFeaturesButton.setDisable(true);
					    }
					});
					String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
					
					String tsk = "[" + timeStamp + "] - J48 - ";
					
					J48Classifier.setParameters();
					if (task.equals(Task.CROSSVALIDATION)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							J48Classifier.crossValidate(trainingArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							J48Classifier.crossValidate(trainingArff, Constants.mainClasses);
						}
						tsk += "X-Validation";
						
					} else if (task.equals(Task.TRAININGSPLIT)) {
						splitPercentage = Integer.parseInt(percentageField.getText());
						
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							J48Classifier.trainingSplit(trainingArff, splitPercentage, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							J48Classifier.trainingSplit(trainingArff, splitPercentage, Constants.mainClasses);
						}
						tsk += "Training set split";
						
					} else if (task.equals(Task.TESTSET)) {
						
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							J48Classifier.test(trainingArff, testArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							J48Classifier.test(trainingArff, testArff, Constants.mainClasses);
						}
						tsk += "Test";
						
					} else if (task.equals(Task.ANNOTATION)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							J48Classifier.test(trainingArff, nonAnnotatedArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							J48Classifier.test(trainingArff, nonAnnotatedArff, Constants.mainClasses);
						}
						tsk += "Annotation";
						
					}
					
					previousResults.put(tsk, J48Classifier.getOutputText().get());
					previousResultsTitles.add(tsk);
					countOfResults.set(countOfResults.getValue() + 1);
					
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	indicator.setProgress(1);
					    	startButton.setDisable(false);
					    	chooseFeaturesButton.setDisable(false);
					    }
					});
				}
			};

			background = new Thread(runnable);

			background.setDaemon(true);

			background.start();
		}
	}
	
	/**
	 * Run the classification using the classifier [K*].
	 */
	private void classifyWithKStar() {
		String trainingArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\Training.arff";
		String testArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + "Test.arff";
		String nonAnnotatedArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + "NonAnnotated.arff";
		
		if (checkParameters().equals(ErrorCode.Fine)) {
			Runnable runnable = new Runnable() {
				public void run() {
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	indicator.setProgress(-1);
					    	startButton.setDisable(true);
					    	chooseFeaturesButton.setDisable(true);
					    }
					});
					String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
					
					String tsk = "[" + timeStamp + "] - K* - ";
					
					KStarClassifier.setParameters();
					if (task.equals(Task.CROSSVALIDATION)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							KStarClassifier.crossValidate(trainingArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							KStarClassifier.crossValidate(trainingArff, Constants.mainClasses);
						}
						tsk += "X-Validation";
						
					} else if (task.equals(Task.TRAININGSPLIT)) {
						splitPercentage = Integer.parseInt(percentageField.getText());
						
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							KStarClassifier.trainingSplit(trainingArff, splitPercentage, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							KStarClassifier.trainingSplit(trainingArff, splitPercentage, Constants.mainClasses);
						}
						tsk += "Training set split";
						
					} else if (task.equals(Task.TESTSET)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							KStarClassifier.test(trainingArff, testArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							KStarClassifier.test(trainingArff, testArff, Constants.mainClasses);
						}
						tsk += "Test";
						
					} else if (task.equals(Task.ANNOTATION)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							KStarClassifier.test(trainingArff, nonAnnotatedArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							KStarClassifier.test(trainingArff, nonAnnotatedArff, Constants.mainClasses);
						}
						tsk += "Annotation";
					}
					
					previousResults.put(tsk, KStarClassifier.getOutputText().get());
					previousResultsTitles.add(tsk);
					countOfResults.set(countOfResults.getValue() + 1);
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	indicator.setProgress(1);
					    	startButton.setDisable(false);
					    	chooseFeaturesButton.setDisable(false);
					    }
					});
				}
			};

			background = new Thread(runnable);

			background.setDaemon(true);

			background.start();
		}
	}
	
	/**
	 * Run the classification using the classifier [Hoeffding Tree].
	 */
	private void classifyWithKHoeffdingTree(){
		String trainingArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\Training.arff";
		String testArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + "Test.arff";
		String nonAnnotatedArff = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + "NonAnnotated.arff";
		
		if (checkParameters().equals(ErrorCode.Fine)) {
			Runnable runnable = new Runnable() {
				public void run() {
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	indicator.setProgress(-1);
					    	startButton.setDisable(true);
					    	chooseFeaturesButton.setDisable(true);
					    }
					});
					String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
					
					String tsk = "[" + timeStamp + "] - Hoeffding Trees - ";
					
					HoeffdingTreeClassifier.setParameters();
					if (task.equals(Task.CROSSVALIDATION)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							HoeffdingTreeClassifier.crossValidate(trainingArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							HoeffdingTreeClassifier.crossValidate(trainingArff, Constants.mainClasses);
						}
						tsk += "X-Validation";
						
					} else if (task.equals(Task.TRAININGSPLIT)) {
						splitPercentage = Integer.parseInt(percentageField.getText());
						
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							HoeffdingTreeClassifier.trainingSplit(trainingArff, splitPercentage, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							HoeffdingTreeClassifier.trainingSplit(trainingArff, splitPercentage, Constants.mainClasses);
						}
						tsk += "Training set split";
						
					} else if (task.equals(Task.TESTSET)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							HoeffdingTreeClassifier.test(trainingArff, testArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							HoeffdingTreeClassifier.test(trainingArff, testArff, Constants.mainClasses);
						}
						tsk += "Test";
						
					} else if (task.equals(Task.ANNOTATION)) {
						if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
							HoeffdingTreeClassifier.test(trainingArff, nonAnnotatedArff, Parameters.getClasses());
						} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
							HoeffdingTreeClassifier.test(trainingArff, nonAnnotatedArff, Constants.mainClasses);
						}
						tsk += "Annotation";
						
					}
					
					previousResults.put(tsk, HoeffdingTreeClassifier.getOutputText().get());
					previousResultsTitles.add(tsk);
					countOfResults.set(countOfResults.getValue() + 1);
					Platform.runLater(new Runnable() {
					    @Override
					    public void run() {
					    	indicator.setProgress(1);
					    	startButton.setDisable(false);
					    	chooseFeaturesButton.setDisable(false);
					    }
					});
				}
			};

			background = new Thread(runnable);

			background.setDaemon(true);

			background.start();
		}
	}
	
	/**
	 * Run the classification using the classifier [Hoeffding Tree].
	 * TODO add support for this classifier (fix the issue of importing LibSVM
	 */
	private void classifyWithSVM() {
		//TODO make sure the .arff files are created!
		AlertBox.display("Coming soon...", "This classifier has not been added yet, therefore it cannot be used. It will be added soon.", "OK");
	}
	
	
	// =====================================//
	//             OTHER METHODS            //
	// =====================================//
	
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
	 * Handle the exit situations
	 */
	private void closeProgram() {
		System.out.println("Cancel Button clicked");
		if (ConfirmBox.display( "Exit", "Are you sure you want to exit?")) {
			System.exit(0);
		}
	}

}

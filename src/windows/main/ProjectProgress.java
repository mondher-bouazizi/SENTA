package windows.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import main.items.Features;
import main.start.Main;
import main.start.ClassificationProblem;
import windows.others.AlertBox;
import windows.others.ConfirmBox;

public class ProjectProgress implements Initializable {
	
	// =====================================//
	//         Non-FXML Components          //
	// =====================================//
	
	private static Button endButton;
	
	private static boolean isStarted;
	private static boolean isPaused;
	private static boolean isDone;
	
	protected static int totalNumberOfTasks;
	
	protected static Thread background;
	
	// =====================================//
	//           FXML Components            //
	// =====================================//
	
	@FXML AnchorPane layout;
	
	@FXML TextArea progressText;
	
	@FXML ProgressBar progressBar;
	
	@FXML Button startPauseButton;
	@FXML Button backButton;
	@FXML Button nextButton;
	@FXML Button cancelButton;
	
	
	
	// =====================================//
	//       FXML Components ACTIONS        //
	// =====================================//

	@FXML public void handleStartPauseButton() {
		if (!isStarted) {
			isStarted = true;
			startPauseButton.setText("Pause");
			backButton.setDisable(true);
			startAction();
		} else {
			if (!isDone) {
				if (isPaused) {
					isPaused = false;
					startPauseButton.setText("Pause");
					continueAction();
				} else {
					isPaused = true;
					startPauseButton.setText("Continue");
					pauseAction();
				}
			} else {
				startPauseButton.setText("Done!");
				backButton.setDisable(false);
				nextButton.setDisable(false);
				endAction();
			}
		}
		
		
		
	}
	
	@FXML public void handleBackButton() {
		System.out.println("Back Button clicked");
		try {
			Main.root = FXMLLoader.load(getClass().getResource("/windows/main/CreateProjectWindow.fxml"));
			Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
			Main.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void handleNextButton() {
		System.out.println("Next Button clicked");
		try {
			Main.root = FXMLLoader.load(getClass().getResource("/windows/main/EndWindow.fxml"));
			Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
			Main.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void handleCancelButton() {
		closeProgram();
	}

	
	// =====================================//
	//            INITIALIZATION            //
	// =====================================//
	
	/**
	 * Initializes the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// I am creating another button to replace the "start/pause button"
		// TODO find another way to make the same button change the text without the exception popping up
		endButton = new Button("Done!");
		layout.getChildren().add(endButton);
		endButton.setLayoutX(359.0);
		endButton.setLayoutY(514.0);
		endButton.setPrefHeight(25.0);
		endButton.setPrefWidth(82.0);
		endButton.setVisible(false);
		
		endButton.setOnAction(event -> {
			endAction();
		});
		
		initializeNumberOfTasks();
		
		isStarted = false;
		isPaused = false;
		isDone = false;

		nextButton.setDisable(true);
		progressText.setEditable(false);
		
		progressText.setEditable(false);
		//progressText.setMouseTransparent(true);
		//progressText.setFocusTraversable(false);
				
		ClassificationProblem.getTextProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue!=null) {
				if(!newValue.equals(oldValue)) {
//					int start = 0;
//					int end = 0;
//					if (progressText.getSelectedText()!=null) {
//						start = progressText.getSelection().getStart();
//						end = progressText.getSelection().getEnd();
//					}
//					progressText.setText(Problem.getTextProperty().get());
//					if (progressText.getText()!=null) {
//						if (progressText.getText().length()>end && start>0 && end>start) {
//							progressText.selectRange(start, end);
//						}
//					}
					javafx.application.Platform.runLater( () -> {
						int start = 0;
						int end = 0;
						if (progressText.getSelectedText()!=null) {
							start = progressText.getSelection().getStart();
							end = progressText.getSelection().getEnd();
						}
						progressText.setText(ClassificationProblem.getTextProperty().get());
						if (progressText.getText()!=null) {
							if (progressText.getText().length()>end && start>0 && end>start) {
								progressText.selectRange(start, end);
							}
						}
					});
				}
			}
			
		});
		
		ClassificationProblem.getCurrentTask().addListener((observable, oldValue, newValue) -> {
			
			double progress = newValue.doubleValue() / (double) totalNumberOfTasks;
			
			if(!newValue.equals(oldValue)) {
				progressBar.progressProperty().set(Math.min(progress, 1.0));
			}
		});
		
		ClassificationProblem.getDone().addListener((observable, oldValue, newValue) -> {
			if (newValue.booleanValue()==true) {
				isDone = true;
				
				//progressText.setMouseTransparent(false);
				//progressText.setFocusTraversable(true);

				endButton.setVisible(true);
				startPauseButton.setVisible(false);
				backButton.setDisable(false);
				nextButton.setDisable(false);
			}
		});
		
	}
	
	private void initializeNumberOfTasks() {
		
		// Problem.setCurrentTask(0);
		// Problem.setDone(false);
		
		// Initially, there are two tasks: collect the training set and test set tweets (even if these have been already done)
		int count = 4;
		
		// Basic Features
		if (Features.isUseSentimentFeatures()) count +=2;
		if (Features.isUsePunctuationFeatures()) count +=2;
		if (Features.isUseStylisticFeatures()) count +=2;
		if (Features.isUseSemanticFeatures()) count +=2;
		if (Features.isUseUnigramFeatures()) count +=2;
		if (Features.isUseTopWords()) count +=3;
		if (Features.isUsePatternFeatures()) count +=3;
		
		// Advanced Features
		if (Features.isUseAdvancedSentimentFeatures()) count +=2;
		if (Features.isUseAdvancedSemanticFeatures()) count +=2;
		if (Features.isUseAdvancedPatternFeatures()) count +=3;
		
		count +=1;
		
		System.out.println("The total Number of tasks is " + count);
		totalNumberOfTasks = count;
	}
	
	
	// =====================================//
	//           PRIVATE METHODS            //
	// =====================================//
	
	/**
	 * Private method that handles the exit situations
	 */
	private static void closeProgram() {
		System.out.println("Cancel Button clicked");
		if (ConfirmBox.display( "Exit", "Are you sure you want to exis?")) {
			System.exit(0);
		}
	}

	/**
	 * Start the collection of features
	 */
	private static void startAction() {

		Runnable task = new Runnable() {
			public void run() {
				ClassificationProblem.run();
				ClassificationProblem.saveProject();
				ClassificationProblem.end();
			}
		};

		background = new Thread(task);

		background.setDaemon(true);

		background.start();

	}
	
	/**
	 * Pause the collection of features
	 */
	private static void pauseAction() {
		// TODO The methods suspend and resume are deprecated (for synchronization issues, check if they are really needed)
		background.suspend();
	}
	
	/**
	 * Resume the collection of features
	 */
	private static void continueAction() {
		// TODO The methods suspend and resume are deprecated (for synchronization issues, check if they are really needed)
		background.resume();
	}

	/**
	 * Run if the program finished executing
	 */
	private static void endAction() {
		AlertBox.display("Finished", "Your project has finished executing!", "OK");
	}

	// =====================================//
	//         GETTERS AND SETTERS          //
	// =====================================//
	
	public static int getTotalNumberOfTasks() {
		return totalNumberOfTasks;
	}
	public static void setTotalNumberOfTasks(int totalNumberOfTasks) {
		ProjectProgress.totalNumberOfTasks = totalNumberOfTasks;
	}
	
	
}

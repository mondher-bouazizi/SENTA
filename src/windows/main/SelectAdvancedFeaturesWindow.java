package windows.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import commons.constants.Constants;
import commons.help.HelpConstants;
import commons.io.Reader;
import commons.io.Writer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import main.items.Features;
import main.items.Parameters;
import main.start.Main;
import windows.features.FeaturesCustumization;
import windows.others.ConfirmBox;

public class SelectAdvancedFeaturesWindow implements Initializable {
	
	// =====================================//
	//          NON-FXML Components         //
	// =====================================//
	
	// The count of features
	protected static int numberOfSentimentRelatedFeatures;
	public static IntegerProperty countOfSentimentRelatedFeatures;
	
	protected static int numberOfSemanticFeatures;
	public static IntegerProperty countOfSemanticFeatures;
	
	protected static int numberOfPatternFeatures;
	public static IntegerProperty countOfPatternFeatures;
	
	protected static int numberOfUnigramFeatures;
	public static IntegerProperty countOfUnigramFeatures;
	
	
	// =====================================//
	//            FXML Components           //
	// =====================================//
	
	// Check boxes for the selection of features
	@FXML CheckBox sentimentRelatedFeatures;
	@FXML CheckBox semanticFeatures;
	@FXML CheckBox patternFeatures;
	@FXML CheckBox unigramFeatures;
	
	// Help Buttons
	@FXML Button sentimentFeaturesHelp;
	@FXML Button semanticFeaturesHelp;
	@FXML Button patternFeaturesHelp;
	@FXML Button unigramFeaturesHelp;
	
	// Customize Buttons
	@FXML Button sentimentFeaturesCustomize;
	@FXML Button semanticFeaturesCustomize;
	@FXML Button patternFeaturesCustomize;
	@FXML Button unigramFeaturesCustomize;
	
	// Labels indicating the number of features
	@FXML Label numberOfSentimentFeaturesLabel;
	@FXML Label numberOfSemanticFeaturesLabel;
	@FXML Label numberOfPatternFeaturesLabel;
	@FXML Label numberOfUnigramFeaturesLabel;
	
	@FXML Label nbSentF;
	@FXML Label nbSemF;
	@FXML Label nbPattF;
	@FXML Label nbUniF;

	// Main Buttons
	@FXML Button backButton;
	@FXML Button nextButton;
	@FXML Button cancelButton;
	
	// Import/Export Buttons
	@FXML Button importButton;
	@FXML Button exportButton;
	
	
	// =====================================//
	//        FXML Components ACTIONS       //
	// =====================================//
	
	// Help Buttons
	@FXML public void handleSentimentFeaturesHelp() {
		// Help.help(HelpConstants.FeaturesSets.ADVANCED_SENTIMENT_BASED_FEATURES);
		try {
			Runtime.getRuntime().exec("hh.exe " + Constants.helpFile + "::8- Advanced Sentiment Features.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML public void handleSemanticFeaturesHelp() {
		// Help.help(HelpConstants.FeaturesSets.ADVANCED_SEMANTIC_FEATURES);
		try {
			Runtime.getRuntime().exec("hh.exe " + Constants.helpFile + "::9- Advanced Semantic Features.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML public void handlePatternFeaturesHelp() {
		// Help.help(HelpConstants.FeaturesSets.ADVANCED_PATTERN_BASED_FEATURES);
		try {
			Runtime.getRuntime().exec("hh.exe " + Constants.helpFile + "::10- Advanced Pattern Features.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML public void handleUnigramFeaturesHelp() {
		// Help.help(HelpConstants.FeaturesSets.ADVANCED_UNIGRAM_FEATURES);
		try {
			Runtime.getRuntime().exec("hh.exe " + Constants.helpFile + "::11- Advanced Unigram Features.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Customize Buttons
	@FXML public void handleSentimentFeaturesCustomize() {
		System.out.println("Customize Advanced sentiment features button clicked");
		FeaturesCustumization.customize(HelpConstants.FeaturesSets.ADVANCED_SENTIMENT_BASED_FEATURES);
		
	}
	
	@FXML public void handleSemanticFeaturesCustomize() {
		System.out.println("Advanced Semantic features Customize button clicked");
		FeaturesCustumization.customize(HelpConstants.FeaturesSets.ADVANCED_SEMANTIC_FEATURES);
	}
	
	@FXML public void handlePatternFeaturesCustomize() {
		System.out.println("Advanced Pattern features Customize button clicked");
		FeaturesCustumization.customize(HelpConstants.FeaturesSets.ADVANCED_PATTERN_BASED_FEATURES);
	}
	
	@FXML public void handleUnigramFeaturesCustomize() {
		System.out.println("Advanced Unigram features Customize button clicked");
		FeaturesCustumization.customize(HelpConstants.FeaturesSets.ADVANCED_UNIGRAM_FEATURES);
	}
	
	// Back, Next and Cancel Buttons
	@FXML public void handleBackButton() {
		System.out.println("Back Button clicked");
		
		if (sentimentRelatedFeatures.isSelected()) {
			Features.setUseAdvancedSentimentFeatures(true);
		} else {
			Features.setUseAdvancedSentimentFeatures(false);
		}
		
		
		if (semanticFeatures.isSelected()) {
			Features.setUseAdvancedSemanticFeatures(true);
		} else {
			Features.setUseAdvancedSemanticFeatures(false);
		}
		
		if (patternFeatures.isSelected()) {
			Features.setUseAdvancedPatternFeatures(true);
		} else {
			Features.setUseAdvancedPatternFeatures(false);
		}
		
		if (unigramFeatures.isSelected()) {
			Features.setUseAdvancedUnigramFeatures(true);
		} else {
			Features.setUseAdvancedUnigramFeatures(false);
		}
		
		try {
			Main.root = FXMLLoader.load(getClass().getResource("/windows/main/SelectBasicFeaturesWindow.fxml"));
			Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
			Main.primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void handleNextButton() {
		System.out.println("Next Button clicked");
		
		if (checkSetOfFeatures()) {
			Features.setUseAdvancedSentimentFeatures(sentimentRelatedFeatures.isSelected());
			Features.setUseAdvancedSemanticFeatures(semanticFeatures.isSelected());
			Features.setUseAdvancedPatternFeatures(patternFeatures.isSelected());
			Features.setUseAdvancedUnigramFeatures(unigramFeatures.isSelected());
			try {
				Main.root = FXMLLoader.load(getClass().getResource("/windows/main/CreateProjectWindow.fxml"));
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
		
	
	// Import and Export Buttons
	@FXML public void handleImportButton() {
		System.out.println("Import Button Clicked!");
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("SENTA features selection", "*.sfs"), new ExtensionFilter("All files", "*.*"));
		fileChooser.setTitle("Please specify which file you want to import");
		try {
				String fileToOpen = fileChooser.showOpenDialog(Main.primaryStage).getPath();
				if (ConfirmBox.display("Import features configuration?", "Are you sure you want to import the selected set of features?")) {
					Reader.importFeatures(fileToOpen);
				}
				
				Main.root = FXMLLoader.load(getClass().getResource("/classifier/application/SelectAdvancedFeaturesWindow.fxml"));
				Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
				Main.primaryStage.show();
				
				
		} catch (NullPointerException exepction) {
			System.out.println(exepction.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@FXML public void handleExportButton() {
		System.out.println("Export Button Clicked!");
		
		if (sentimentRelatedFeatures.isSelected()) {
			Features.setUseAdvancedSentimentFeatures(true);
		} else {
			Features.setUseAdvancedSentimentFeatures(false);
		}


		if (semanticFeatures.isSelected()) {
			Features.setUseAdvancedSemanticFeatures(true);
		} else {
			Features.setUseAdvancedSemanticFeatures(false);
		}


		if (patternFeatures.isSelected()) {
			Features.setUseAdvancedPatternFeatures(true);
		} else {
			Features.setUseAdvancedPatternFeatures(false);
		}
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("SENTA features selection", "*.sfs"), new ExtensionFilter("All files", "*.*"));
		fileChooser.setTitle("Please specify where you want to save your feature selection");
		try {
			String file = fileChooser.showSaveDialog(Main.primaryStage).getPath();
			Writer.exportFeatures(file);
			
		} catch (NullPointerException exepction) {
			System.out.println(exepction.getMessage());
		}
	}
	
	
	// =====================================//
	//            INITIALIZATION            //
	// =====================================//
	
	/**
	 * Initialization method
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if (!Parameters.isUseAdavancedFeaturesAllowed()) {
			sentimentRelatedFeatures.setDisable(true);
			sentimentFeaturesHelp.setDisable(true);
			sentimentFeaturesCustomize.setDisable(true);
			
			nbSentF.setDisable(true);
			numberOfSentimentFeaturesLabel.setDisable(true);
			
			semanticFeatures.setDisable(true);
			semanticFeaturesHelp.setDisable(true);
			semanticFeaturesCustomize.setDisable(true);
			
			nbSemF.setDisable(true);
			numberOfSemanticFeaturesLabel.setDisable(true);
		} else {
			sentimentRelatedFeatures.setDisable(false);
			sentimentFeaturesHelp.setDisable(false);
			sentimentFeaturesCustomize.setDisable(false);
			
			nbSentF.setDisable(false);
			numberOfSentimentFeaturesLabel.setDisable(false);
			
			semanticFeatures.setDisable(false);
			semanticFeaturesHelp.setDisable(false);
			semanticFeaturesCustomize.setDisable(false);
			
			nbSemF.setDisable(false);
			numberOfSemanticFeaturesLabel.setDisable(false);
		}
		
		// Selection of sets of features
		if (Features.isUseAdvancedSentimentFeatures()) {
			sentimentRelatedFeatures.setSelected(true);
		}
		if (Features.isUseAdvancedSemanticFeatures()) {
			semanticFeatures.setSelected(true);
		}
		if (Features.isUseAdvancedPatternFeatures()) {
			patternFeatures.setSelected(true);
		}
		if (Features.isUseAdvancedUnigramFeatures()) {
			unigramFeatures.setSelected(true);
		}
		
		
		// Count of sentiment-related features
		numberOfSentimentFeaturesLabel.setText("" + numberOfSentimentRelatedFeatures);
		if(numberOfSentimentRelatedFeatures==0) {
			numberOfSentimentFeaturesLabel.setTextFill(Color.RED);
		} else {
			numberOfSentimentFeaturesLabel.setTextFill(Color.GREEN);
		}
		countOfSentimentRelatedFeatures = new SimpleIntegerProperty(0);
		ReadOnlyIntegerProperty.readOnlyIntegerProperty(countOfSentimentRelatedFeatures).addListener((v, oldValue, newValue) -> {
			if (!newValue.equals(oldValue)) {
				numberOfSentimentFeaturesLabel.setText("" + numberOfSentimentRelatedFeatures);
			}
			if (newValue.intValue()==0) {
				numberOfSentimentFeaturesLabel.setTextFill(Color.RED);
			} else {
				numberOfSentimentFeaturesLabel.setTextFill(Color.GREEN);
			}
		});
		
		// Count of Semantic Features
		numberOfSemanticFeaturesLabel.setText("" + numberOfSemanticFeatures);
		if (numberOfSemanticFeatures==0) {
			numberOfSemanticFeaturesLabel.setTextFill(Color.RED);
		} else {
			numberOfSemanticFeaturesLabel.setTextFill(Color.GREEN);
		}
		
		countOfSemanticFeatures = new SimpleIntegerProperty(0);
		ReadOnlyIntegerProperty.readOnlyIntegerProperty(countOfSemanticFeatures).addListener((v, oldValue, newValue) -> {
			if (!newValue.equals(oldValue)) {
				numberOfSemanticFeaturesLabel.setText("" + numberOfSemanticFeatures);
			}
			if (newValue.intValue()==0) {
				numberOfSemanticFeaturesLabel.setTextFill(Color.RED);
			} else {
				numberOfSemanticFeaturesLabel.setTextFill(Color.GREEN);
			}
		});
		
		// Count of Pattern Features
		numberOfPatternFeaturesLabel.setText("" + numberOfPatternFeatures);
		if (numberOfPatternFeatures == 0) {
			numberOfPatternFeaturesLabel.setTextFill(Color.RED);
		} else {
			numberOfPatternFeaturesLabel.setTextFill(Color.GREEN);
		}
		
		countOfPatternFeatures = new SimpleIntegerProperty(0);
		ReadOnlyIntegerProperty.readOnlyIntegerProperty(countOfPatternFeatures).addListener((v, oldValue, newValue) -> {
			if (!newValue.equals(oldValue)) {
				numberOfPatternFeaturesLabel.setText("" + numberOfPatternFeatures);
			}
			if (newValue.intValue()==0) {
				numberOfPatternFeaturesLabel.setTextFill(Color.RED);
			} else {
				numberOfPatternFeaturesLabel.setTextFill(Color.GREEN);
			}
		});
		
		// Count of Unigram Features
		numberOfUnigramFeaturesLabel.setText("" + numberOfUnigramFeatures);
		if (numberOfUnigramFeatures == 0) {
			numberOfUnigramFeaturesLabel.setTextFill(Color.RED);
		} else {
			numberOfUnigramFeaturesLabel.setTextFill(Color.GREEN);
		}
		
		countOfUnigramFeatures = new SimpleIntegerProperty(0);
		ReadOnlyIntegerProperty.readOnlyIntegerProperty(countOfUnigramFeatures).addListener((v, oldValue, newValue) -> {
			if (!newValue.equals(oldValue)) {
				numberOfUnigramFeaturesLabel.setText("" + numberOfUnigramFeatures);
			}
			if (newValue.intValue()==0) {
				numberOfUnigramFeaturesLabel.setTextFill(Color.RED);
			} else {
				numberOfUnigramFeaturesLabel.setTextFill(Color.GREEN);
			}
		});
		
		initializeSentimentFeaturesCount();		
		initializeSemanticFeaturesCount();
		initializePatternFeaturesCount();
		initializeUnigramFeaturesCount();
		
	}
	
	
	private void initializeSentimentFeaturesCount() {
		int count = 0;
		
			// Words before and after
			int numberOfWordsBefore = Features.getNumberOfWordsBefore();
			count = count + numberOfWordsBefore;
			
			int numberOfWordsAfter = Features.getNumberOfWordsAfter();
			count = count + numberOfWordsAfter;
			
			// Add Sentiment Information
			if (Features.isAddSentimentClassInformation()) {
				int extraFeatures = Parameters.getClasses().size();
				count = count + count*extraFeatures;
			}
			
			SelectAdvancedFeaturesWindow.setNumberOfSentimentRelatedFeatures(count);
			SelectAdvancedFeaturesWindow.countOfSentimentRelatedFeatures.set(count);
	}
	
	private void initializeSemanticFeaturesCount() {
		int count = 1;
		
		int 	c1 = 1,
				c2 = 1,
				c3 = 0;
			
			if (Features.isCountOtherWords()) {
				c1++;
			}
			
			if (Features.getAdvancedSemanticPos()==null) {
				// Do nothing, the count is equal to 0
				c2=0;
			} else if (Features.getAdvancedSemanticPos().equals(Features.TypeOfPos.SEPARATED)) {
				c2 = 4;
			} else if (Features.getAdvancedSemanticPos().equals(Features.TypeOfPos.TOGETHER)) {
				c2 = 1;
			}
			
			
			if(Features.isAdvUseOfPositiveWords()) {
				c3++;
			}
			if(Features.isAdvUseOfNegativeWords()) {
				
				c3++;
			}
			if(Features.isAdvUseOfHighlyPositiveWords()) {
				
				c3++;
			}
			if(Features.isAdvUseOfHighlyNegativeWords()) {
				c3++;
			}
			
			if(Features.isAdvUseOfOpinionWords()) {
				c3++;
			}
			
			if(Features.isAdvUseOfUncertaintyWords()) {
				c3++;
			}
			
			if(Features.isAdvUseOfActiveForm()) {
				c3++;
			}
			
			if(Features.isAdvUseOfPassiveForm()) {
				c3++;
			}
			
			count = c3 * c2 * c1;
			
			SelectAdvancedFeaturesWindow.setNumberOfSemanticFeatures(count);
			SelectAdvancedFeaturesWindow.countOfSemanticFeatures.set(count);
	}
	
	private void initializePatternFeaturesCount() {
		int c3 = Parameters.getClasses().size();
		
		int count = 0;
			
			if (Features.getAdvancedPatternFeaturesType()==null) {
				// Do nothing, the count is equal to 0
			} else if (Features.getAdvancedPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
				count = c3 * (Features.getAdvancedMaxPatternLength() - Features.getAdvancedMinPatternLength() + 1);
			} else {
				count = c3 *Features.getAdvancedNumberOfPatternsPerClass();
			}
			SelectAdvancedFeaturesWindow.setNumberOfPatternFeatures(count);
			SelectAdvancedFeaturesWindow.countOfPatternFeatures.set(count);
	}
	
	private void initializeUnigramFeaturesCount() {
		int count = 0;
		if (Parameters.getImportedUnigramsLocation() != null) {
			count = Reader.countLines(Parameters.getImportedUnigramsLocation());
		}

		SelectAdvancedFeaturesWindow.setNumberOfUnigramFeatures(count);
		SelectAdvancedFeaturesWindow.countOfUnigramFeatures.set(count);
	}
	
	
	// =====================================//
	//             Other methods            //
	// =====================================//
	
	/**
	 * Private method that handles the exit situations
	 */
	private void closeProgram() {
		System.out.println("Cancel Button clicked");
		if (ConfirmBox.display( "Exit", "Are you sure you want to exit?")) {
			System.exit(0);
		}
	}
	
	/**
	 * Private method to check whether Any set of features is select while no feature is actually being used or the opposite
	 * @return whether such inconsistency exists or not (+Displays an alert box)
	 */
	private boolean checkSetOfFeatures() {
		
		if ((sentimentRelatedFeatures.isSelected() && numberOfSentimentRelatedFeatures==0) ||
			(semanticFeatures.isSelected() && numberOfSemanticFeatures==0) ||
			(patternFeatures.isSelected() && numberOfPatternFeatures==0) ||
			(unigramFeatures.isSelected() && numberOfUnigramFeatures==0))
			
			{
			boolean test = ConfirmBox.display("Attention", "You have chosen to use one or more set(s) of features, however you have not set the corresponding parameters yet. Are you sure you want to continue?");
			if (test==false) {
				return false;
			}
		}
		
		if ((!sentimentRelatedFeatures.isSelected() && numberOfSentimentRelatedFeatures!=0) ||
			(!semanticFeatures.isSelected() && numberOfSemanticFeatures!=0) ||
			(!patternFeatures.isSelected() && numberOfPatternFeatures!=0) ||
			(!unigramFeatures.isSelected() && numberOfUnigramFeatures!=0))
				
				{
				boolean test = ConfirmBox.display("Attention", "You have customized one or more set(s) of features, however you have not selected this (these) set(s) to be extracted. Are you sure you want to continue?");
				if (test==false) {
					return false;
				}
			}
		
		return true;
		
	}
	
	// Getters and Setters of the counters

	public static int getNumberOfSentimentRelatedFeatures() {
		return numberOfSentimentRelatedFeatures;
	}
	public static void setNumberOfSentimentRelatedFeatures(int numberOfSentimentRelatedFeatures) {
		SelectAdvancedFeaturesWindow.numberOfSentimentRelatedFeatures = numberOfSentimentRelatedFeatures;
	}

	public static int getNumberOfSemanticFeatures() {
		return numberOfSemanticFeatures;
	}
	public static void setNumberOfSemanticFeatures(int numberOfSemanticFeatures) {
		SelectAdvancedFeaturesWindow.numberOfSemanticFeatures = numberOfSemanticFeatures;
	}

	public static int getNumberOfPatternFeatures() {
		return numberOfPatternFeatures;
	}
	public static void setNumberOfPatternFeatures(int numberOfPatternFeatures) {
		SelectAdvancedFeaturesWindow.numberOfPatternFeatures = numberOfPatternFeatures;
	}

	
	public static int getNumberOfUnigramFeatures() {
		return numberOfUnigramFeatures;
	}
	public static void setNumberOfUnigramFeatures(int numberOfUnigramFeatures) {
		SelectAdvancedFeaturesWindow.numberOfUnigramFeatures = numberOfUnigramFeatures;
	}
	
	

}

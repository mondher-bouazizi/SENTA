package windows.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import commons.constants.Constants;
import commons.help.Help;
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

public class SelectBasicFeaturesWindow implements Initializable {
	
	// =====================================//
	//         non-FXML Components          //
	// =====================================//
	
	// Related to the knowledge of where we came from
	public static enum Previous {
			importProjectWindow,
			importFeaturesWindow,
			startProjectWindow
	}
	
	private static Previous previousWindow;
	
	// The count of features
	protected static int numberOfSentimentRelatedFeatures;
	public static IntegerProperty countOfSentimentRelatedFeatures;
	
	protected static int numberOfPunctuationFeatures;
	public static IntegerProperty countOfPunctuationFeatures;
	
	protected static int numberOfStylisticFeatures;
	public static IntegerProperty countOfStylisticFeatures;
	
	protected static int numberOfSemanticFeatures;
	public static IntegerProperty countOfSemanticFeatures;
	
	protected static int numberOfUnigramFeatures;
	public static IntegerProperty countOfUnigramFeatures;
	
	protected static int numberOfTopWordsFeatures;
	public static IntegerProperty countOfTopWordsFeatures;
	
	protected static int numberOfPatternFeatures;
	public static IntegerProperty countOfPatternFeatures;
	
	
	// =====================================//
	//           FXML Components            //
	// =====================================//
	
	// The Check Boxes that are used to tell which sets of features are to be used
	@FXML CheckBox sentimentRelatedFeatures;
	@FXML CheckBox punctuationFeatures;
	@FXML CheckBox stylisticFeatures;
	
	@FXML CheckBox semanticFeatures;
	@FXML CheckBox unigramFeatures;
	@FXML CheckBox topWordsFeatures;
	@FXML CheckBox patternFeatures;
	
	// The buttons that are used to help explaining the meaning of the sets of features
	@FXML Button sentimentRelatedFeaturesHelp;
	@FXML Button punctuationFeaturesHelp;
	@FXML Button stylisticFeaturesHelp;
	
	@FXML Button semanticFeaturesHelp;
	@FXML Button unigramFeaturesHelp;
	@FXML Button topWordsFeaturesHelp;
	@FXML Button patternFeaturesHelp;
	
	// The buttons that are used to customize the sets of features
	@FXML Button sentimentRelatedFeaturesCustomize;
	@FXML Button punctuationFeaturesCustomize;
	@FXML Button stylisticFeaturesCustomize;
	
	@FXML Button semanticFeaturesCustomize;
	@FXML Button unigramFeaturesCustomize;
	@FXML Button topWordsFeaturesCustomize;
	@FXML Button patternFeaturesCustomize;
	
	// Labels of telling the count of features
	@FXML Label numberOfSentimentFeaturesLabel;
	@FXML Label numberOfPunctuationFeaturesLabel;
	@FXML Label numberOfStylisticFeaturesLabel;
	
	@FXML Label numberOfSemanticFeaturesLabel;
	@FXML Label numberOfUnigramFeaturesLabel;
	@FXML Label numberOfTopWordsFeaturesLabel;
	@FXML Label numberOfPatternFeaturesLabel;
	
	// Back, Next and Cancel + Import and Export Buttons
	@FXML Button backButton;
	@FXML Button nextButton;
	@FXML Button cancelButton;
	
	@FXML Button importButton;
	@FXML Button exportButton;
	
	
	// =====================================//
	//        FXML Components Actions       //
	// =====================================//
	
	// Help Buttons
	@FXML public void handleSentimentRelatedFeaturesHelp() {
		// Help.help(HelpConstants.FeaturesSets.BASIC_SENTIMENT_BASED_FEATURES);
		try {
			Runtime.getRuntime().exec("hh.exe " + Constants.helpFile + "::1- Sentiment Features.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML public void handlePunctuationFeaturesHelp() {
		// Help.help(HelpConstants.FeaturesSets.PUNCTUATION_FEATURES);
		try {
			Runtime.getRuntime().exec("hh.exe " + Constants.helpFile + "::2- Punctuation Features.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML public void handleStylisticFeaturesHelp() {
		// Help.help(HelpConstants.FeaturesSets.STYLISTIC_FEATURES);
		try {
			Runtime.getRuntime().exec("hh.exe " + Constants.helpFile + "::3- Syntactic and Stylistic Features.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML public void handleSemanticFeaturesHelp() {
		// Help.help(HelpConstants.FeaturesSets.BASIC_SEMANTIC_FEATURES);
		try {
			Runtime.getRuntime().exec("hh.exe " + Constants.helpFile + "::4- Semantic Features.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML public void handleUnigramFeaturesHelp() {
		// Help.help(HelpConstants.FeaturesSets.UNIGRAM_FEATURES);
		try {
			Runtime.getRuntime().exec("hh.exe " + Constants.helpFile + "::5- Unigram Features.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML public void handleTopWordsFeaturesHelp() {
		// Help.help(HelpConstants.FeaturesSets.TOP_WORDS_FEATURES);
		try {
			Runtime.getRuntime().exec("hh.exe " + Constants.helpFile + "::6- Top Words.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML public void handlePatternFeaturesHelp() {
		// Help.help(HelpConstants.FeaturesSets.BASIC_PATTERN_BASED_FEATURES);
		try {
			Runtime.getRuntime().exec("hh.exe " + Constants.helpFile + "::7- Pattern Features.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// Customize Features Buttons
	
	@FXML public void handleSentimentRelatedFeaturesCustomize() {
		System.out.println("Customize Sentiment-Related Features");
		FeaturesCustumization.customize(HelpConstants.FeaturesSets.BASIC_SENTIMENT_BASED_FEATURES);
	}
	
	@FXML public void handlePunctuationFeaturesCustomize() {
		System.out.println("Customize Punctuation Features");
		FeaturesCustumization.customize(HelpConstants.FeaturesSets.PUNCTUATION_FEATURES);
		
	}
	
	@FXML public void handleStylisticFeaturesCustomize() {
		System.out.println("Customize Stylistic and Syntactic Features");
		FeaturesCustumization.customize(HelpConstants.FeaturesSets.STYLISTIC_FEATURES);
		
	}
	
	@FXML public void handleSemanticFeaturesCustomize() {
		System.out.println("Customize Semantic Features");
		FeaturesCustumization.customize(HelpConstants.FeaturesSets.BASIC_SEMANTIC_FEATURES);
		
	}

	@FXML public void handleUnigramFeaturesCustomize() {
		System.out.println("Customize Sentiment-Related Features");
		FeaturesCustumization.customize(HelpConstants.FeaturesSets.UNIGRAM_FEATURES);
	}
	
	@FXML public void handleTopWordsFeaturesCustomize() {
		System.out.println("Customize Top Words Features");
		FeaturesCustumization.customize(HelpConstants.FeaturesSets.TOP_WORDS_FEATURES);
	}
	
	@FXML public void handlePatternFeaturesCustomize() {
		System.out.println("Customize Pattern-Related Features");
		FeaturesCustumization.customize(HelpConstants.FeaturesSets.BASIC_PATTERN_BASED_FEATURES);
	}
	
	
	// Back, Next and Cancel Buttons
	
	@FXML public void handleBackButton() {
		System.out.println("Back Button clicked");
		
		if (sentimentRelatedFeatures.isSelected()) {
			Features.setUseSentimentFeatures(true);
		}
		
		if (punctuationFeatures.isSelected()) {
			Features.setUsePunctuationFeatures(true);
		}
		
		if (stylisticFeatures.isSelected()) {
			Features.setUseStylisticFeatures(true);
		}
		
		if (semanticFeatures.isSelected()) {
			Features.setUseSemanticFeatures(true);
		}
		
		if (unigramFeatures.isSelected()) {
			Features.setUseUnigramFeatures(true);
		}
		
		if (topWordsFeatures.isSelected()) {
			Features.setUseTopWords(true);
		}
		
		if (patternFeatures.isSelected()) {
			Features.setUsePatternFeatures(true);
		}
		
		if (previousWindow==null) {
			try {
				Main.root = FXMLLoader.load(getClass().getResource("/windows/main/StartNewProjectWindow.fxml"));
				Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
				Main.primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (previousWindow.equals(Previous.startProjectWindow)) {
				try {
					Main.root = FXMLLoader.load(getClass().getResource("/windows/main/StartNewProjectWindow.fxml"));
					Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
					Main.primaryStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (previousWindow.equals(Previous.importProjectWindow)) {
				try {
					Main.root = FXMLLoader.load(getClass().getResource("/windows/main/OpenProjectWindow.fxml"));
					Main.primaryStage.setScene(new Scene(Main.root, 800, 600));
					Main.primaryStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	@FXML public void handleNextButton() {
		System.out.println("Next Button clicked");
		
		if (checkSetOfFeatures()) {
			
			Features.setUseSentimentFeatures(sentimentRelatedFeatures.isSelected());
			Features.setUsePunctuationFeatures(punctuationFeatures.isSelected());
			Features.setUseStylisticFeatures(stylisticFeatures.isSelected());
			Features.setUseSemanticFeatures(semanticFeatures.isSelected());
			Features.setUseUnigramFeatures(unigramFeatures.isSelected());
			Features.setUseTopWords(topWordsFeatures.isSelected());
			Features.setUsePatternFeatures(patternFeatures.isSelected());

			try {
				Main.root = FXMLLoader.load(getClass().getResource("/windows/main/SelectAdvancedFeaturesWindow.fxml"));
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
				
				Main.root = FXMLLoader.load(getClass().getResource("/windows/main/SelectBasicFeaturesWindow.fxml"));
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
		
		Features.setUseSentimentFeatures(sentimentRelatedFeatures.isSelected());
		Features.setUsePunctuationFeatures(punctuationFeatures.isSelected());
		Features.setUseStylisticFeatures(stylisticFeatures.isSelected());
		Features.setUseSemanticFeatures(semanticFeatures.isSelected());
		Features.setUseUnigramFeatures(unigramFeatures.isSelected());
		Features.setUseTopWords(topWordsFeatures.isSelected());
		Features.setUsePatternFeatures(patternFeatures.isSelected());
		
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
	//             Initialization           //
	// =====================================//
	
	/**
	 * Initialization method
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		numberOfSentimentRelatedFeatures = 0;
		numberOfPunctuationFeatures = 0;
		numberOfStylisticFeatures = 0;
		numberOfSemanticFeatures = 0;
		numberOfUnigramFeatures = 0;
		numberOfTopWordsFeatures = 0;
		numberOfPatternFeatures = 0;
		
		// Sets of features used
		if (Features.isUseSentimentFeatures()) {
			sentimentRelatedFeatures.setSelected(true);
		}		
		
		if (Features.isUsePunctuationFeatures()) {
			punctuationFeatures.setSelected(true);
		}
		
		if (Features.isUseStylisticFeatures()) {
			stylisticFeatures.setSelected(true);
		}
		
		if (Features.isUseSemanticFeatures()) {
			semanticFeatures.setSelected(true);
		}
		
		if (Features.isUseUnigramFeatures()) {
			unigramFeatures.setSelected(true);
		}
		
		if (Features.isUseTopWords()) {
			topWordsFeatures.setSelected(true);
		}
		
		if (Features.isUsePatternFeatures()) {
			patternFeatures.setSelected(true);
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
		
		
		// Count of Punctuation Features
		numberOfPunctuationFeaturesLabel.setText("" + numberOfPunctuationFeatures);
		if (numberOfPunctuationFeatures==0) {
			numberOfPunctuationFeaturesLabel.setTextFill(Color.RED);
		} else {
			numberOfPunctuationFeaturesLabel.setTextFill(Color.GREEN);
		}
		
		countOfPunctuationFeatures = new SimpleIntegerProperty(0);
		ReadOnlyIntegerProperty.readOnlyIntegerProperty(countOfPunctuationFeatures).addListener((v, oldValue, newValue) -> {
			if (!newValue.equals(oldValue)) {
				numberOfPunctuationFeaturesLabel.setText("" + numberOfPunctuationFeatures);
			}
			if (newValue.intValue()==0) {
				numberOfPunctuationFeaturesLabel.setTextFill(Color.RED);
			} else {
				numberOfPunctuationFeaturesLabel.setTextFill(Color.GREEN);
			}
		});
		
		
		// Count of Stylistic Features
		numberOfStylisticFeaturesLabel.setText("" + numberOfStylisticFeatures);
		if (numberOfStylisticFeatures==0) {
			numberOfStylisticFeaturesLabel.setTextFill(Color.RED);
		} else {
			numberOfStylisticFeaturesLabel.setTextFill(Color.GREEN);
		}
		
		countOfStylisticFeatures = new SimpleIntegerProperty(0);
		ReadOnlyIntegerProperty.readOnlyIntegerProperty(countOfStylisticFeatures).addListener((v, oldValue, newValue) -> {
			if (!newValue.equals(oldValue)) {
				numberOfStylisticFeaturesLabel.setText("" + numberOfStylisticFeatures);
			}
			if (newValue.intValue()==0) {
				numberOfStylisticFeaturesLabel.setTextFill(Color.RED);
			} else {
				numberOfStylisticFeaturesLabel.setTextFill(Color.GREEN);
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
		
		
		// Count of Unigram Features
		numberOfUnigramFeaturesLabel.setText("" + numberOfUnigramFeatures);
		if (numberOfUnigramFeatures==0) {
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
		
		
		// Count of Top Words Features
		numberOfTopWordsFeaturesLabel.setText("" + numberOfTopWordsFeatures);
		if (numberOfTopWordsFeatures == 0) {
			numberOfTopWordsFeaturesLabel.setTextFill(Color.RED);
		} else {
			numberOfTopWordsFeaturesLabel.setTextFill(Color.GREEN);
		}
		
		countOfTopWordsFeatures = new SimpleIntegerProperty(0);
		ReadOnlyIntegerProperty.readOnlyIntegerProperty(countOfTopWordsFeatures).addListener((v, oldValue, newValue) -> {
			if (!newValue.equals(oldValue)) {
				numberOfTopWordsFeaturesLabel.setText("" + numberOfTopWordsFeatures);
			}
			if (newValue.intValue()==0) {
				numberOfTopWordsFeaturesLabel.setTextFill(Color.RED);
			} else {
				numberOfTopWordsFeaturesLabel.setTextFill(Color.GREEN);
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
		
		// Initialize the count of features in case of the user chooses to import the feature set
		initializeSentimentFeaturesCount();
		initializePunctuationFeaturesCount();
		initializeStylisticFeaturesCount();
		initializeSemanticFeaturesCount();
		initializeUnigramFeaturesCount();
		initializeTopWordsFeaturesCount();
		initializePatternFeaturesCount();
		
		
	}
	
	// Initialize the count of features!
	private void initializeSentimentFeaturesCount() {
		int count = 0;

		// Number of positive words
		if (Features.isNumberOfPositiveWords()) {
			count++;
		}
		
		// Number of negative words
		if (Features.isNumberOfNegativeWords()) {
			count++;
		}
		
		// Number of highly emotional positive words
		if (Features.isNumberOfHighlyEmoPositiveWords()) {
			count++;
		}
		
		// Number of highly emotional negative words
		if (Features.isNumberOfHighlyEmoNegativeWords()) {
			count++;
		}
		
		// Number of Capitalized positive words
		if (Features.isNumberOfCapitalizedPositiveWords()) {
			count++;
		}
		
		// Number of Capitalized Negative Words
		if (Features.isNumberOfCapitalizedNegativeWords()) {
			count++;
		}
		
		// Ratio of Emotional Words
		if (Features.isRatioOfEmotionalWords()) {
			count++;
		}
		
		// Number of Positive Emoticons
		if (Features.isNumberOfPositiveEmoticons()) {
			count++;
		}
		
		// Number of Negative Emoticons
		if (Features.isNumberOfNegativeEmoticons()) {
			count++;
		}
		
		// Number of "Neutral" Emoticons
		if (Features.isNumberOfNeutralEmoticons()) {
			count++;
		}
		
		// Number of "Joking" Emoticons
		if (Features.isNumberOfJokingEmoticons()) {
			count++;
		}
		
		// Number Of Positive Slangs
		if (Features.isNumberOfPositiveSlangs()) {
			count++;
		}
		
		// Number of Negative Slangs
		if (Features.isNumberOfNegativeSlangs()) {
			count++;
		}
		
		// Number Of Positive Hashtags
		if (Features.isNumberOfPositiveHashtags()) {
			count++;
		}
		
		// Number Of Negative Hashtags
		if (Features.isNumberOfNegativeHashtags()) {
			count++;
		}
		
		// Contrast Words Vs Words
		if (Features.isContrastWordsVsWords()) {
			count++;
		}
		
		// Contrast Words Vs Hashtags
		if (Features.isContrastWordsVsHashtags()) {
			count++;
		}
		
		// Contrast Words Vs Emoticons
		if (Features.isContrastWordsVsEmoticons()) {
			count++;
		}
		
		// Contrast Hashtags Vs Hashtags
		if (Features.isContrastHashtagsVsHashtags()) {
			count++;
		}
		
		// Contrast Hashtags Vs Emoticons
		if (Features.isContrastHashtagsVsEmoticons()) {
			count++;
		}
		
		SelectBasicFeaturesWindow.setNumberOfSentimentRelatedFeatures(count);
		SelectBasicFeaturesWindow.countOfSentimentRelatedFeatures.set(count);
	}
	
	private void initializePunctuationFeaturesCount() {
		int count = 0;

		// Number of dots
		if (Features.isNumberOfDots()) {
			count++;
		}
		
		// Number of commas
		if (Features.isNumberOfCommas()) {
			count++;
		}
		
		// Number of semicolons
		if (Features.isNumberOfSemicolons()) {
			count++;
		}
		
		// Number of Exclamation Marks
		if (Features.isNumberOfExclamationMarks()) {
			count++;
		}
		
		// Number of Question Marks
		if (Features.isNumberOfQuestionMarks()) {
			count++;
		}
		
		// Number of Parenthesis
		if (Features.isNumberOfParentheses()) {
			count++;
		}
		
		// Number of Brackets
		if (Features.isNumberOfBrackets()) {
			count++;
		}
		
		// Number of Braces
		if (Features.isNumberOfBraces()) {
			count++;
		}

		// Total Number Of Characters
		if (Features.isTotalNumberOfCharacters()) {
			count++;
		}
		
		// Total Number Of Words
		if (Features.isTotalNumberOfWords()) {
			count++;
		}
		
		// Total Number Of Sentences
		if (Features.isTotalNumberOfSentences()) {
			count++;
		}
		
		// Average Number Of Characters
		if (Features.isAverageNumberOfCharactersPerSentence()) {
			count++;
		}
		
		// Average Number Of Words
		if (Features.isAverageNumberOfWordsPerSentence()) {
			count++;
		}

		if (Features.isNumberOfApostrophes()) {
			count++;
		}
		
		if (Features.isNumberOfQuotationMarks()) {
			count++;
		}
		
		SelectBasicFeaturesWindow.setNumberOfPunctuationFeatures(count);
		SelectBasicFeaturesWindow.countOfPunctuationFeatures.set(count);
	}
	
	private void initializeStylisticFeaturesCount() {
		int count = 0;

		// Number of Nouns
		if (Features.isNumberOfNouns()) {
			count++;
		}

		// Ratio of Nouns
		if (Features.isRatioOfNouns()) {
			count++;
		}

		// Number of Verbs
		if (Features.isNumberOfVerbs()) {
			count++;
		}

		// Ratio of Verbs
		if (Features.isRatioOfVerbs()) {
			count++;
		}

		// Number of Adjectives
		if (Features.isNumberOfAdjectives()) {
			count++;
		}

		// Ratio of Adjectives
		if (Features.isRatioOfAdjectives()) {
			count++;
		}

		// Number of Adverbs
		if (Features.isNumberOfAdverbs()) {
			count++;
		}

		// Ratio of Adverbs
		if (Features.isRatioOfAdverbs()) {
			count++;
		}

		// Use of Symbols
		if (Features.isUseOfSymbols()) {
			count++;
		}
		// Use of Comparative Form
		if (Features.isUseOfComparativeForm()) {
			count++;
		}

		// Use of Superlative Form
		if (Features.isUseOfSuperlativeForm()) {
			count++;
		}

		// Use of Proper Nouns
		if (Features.isUseOfProperNouns()) {
			count++;
		}

		// Total Number of Particles
		if (Features.isTotalNumberOfParticles()) {
			count++;
		}

		// Average Number of Particles
		if (Features.isAverageNumberOfParticles()) {
			count++;
		}

		// Total Number of Interjections
		if (Features.isTotalNumberOfInterjections()) {
			count++;
		}

		// Average Number of Interjections
		if (Features.isAverageNumberOfInterjections()) {
			count++;
		}

		// Total Number of Pronouns
		if (Features.isTotalNumberOfPronouns()) {
			count++;
		}

		// Average Number of Pronouns
		if (Features.isAverageNumberOfPronouns()) {
			count++;
		}

		// Total Number of Pronouns Group 1
		if (Features.isTotalNumberOfPronounsGroup1()) {
			count++;

		}

		// Average Number of Pronouns Group1
		if (Features.isAverageNumberOfPronounsGroup1()) {
			count++;
		}

		// Total Number of Pronouns Group2
		if (Features.isTotalNumberOfPronounsGroup2()) {
			count++;
		}

		// Average Number of Pronouns Group2
		if (Features.isAverageNumberOfPronounsGroup2()) {
			count++;
		}

		// Use of Negation
		if (Features.isUseOfNegation()) {
			count++;
		}

		// Use of Uncommon Words
		if (Features.isUseOfUncommonWords()) {
			count++;
		}

		// Number of Uncommon Words
		if (Features.isNumberOfUncommonWords()) {
			count++;
		}

		SelectBasicFeaturesWindow.setNumberOfStylisticFeatures(count);
		SelectBasicFeaturesWindow.countOfStylisticFeatures.set(count);
	}

	private void initializeSemanticFeaturesCount() {
		int count = 0;

		// Use of opinion words
		if (Features.isUseOfOpinionWords()) {
			count++;
		}
		
		// Use of highly sentimental words
		if (Features.isUseOfHighlySentimentalWords()) {
			count++;
		}
		
		// Use of uncertainty words
		if (Features.isUseOfUncertaintyWords()) {
			count++;
		}
		
		// Use of active form
		if (Features.isUseOfActiveForm()) {
			count++;
		}
		
		// Use of passive form
		if (Features.isUseOfPassiveForm()) {
			count++;
		}
		
		
		SelectBasicFeaturesWindow.setNumberOfSemanticFeatures(count);
		SelectBasicFeaturesWindow.countOfSemanticFeatures.set(count);
	}
	
	private void initializeUnigramFeaturesCount() {
				
		int c3 = Parameters.getClasses().size();
		int count = 0;
		// Use nouns
		if (Features.isUseUnigramNouns()) {
			count++;
		}
		
		// Use verbs
		if (Features.isUseUnigramVerbs()) {
			count++;
		}
		
		// Use adjectives
		if (Features.isUseUnigramAdjectives()) {
			count++;
		}
		
		// Use adverbs
		if (Features.isUseUnigramAdverbs()) {
			count++;
		}

		// How the POS tags are taken (separated or together)
		if (Features.getUnigramTypeOfPos()==null) {
			// This basically suppose that the set of features has not been customized in the first place, but who knows?
			count = 0;
		} else if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
			count = 1; // This will be multiplied by the number of classes
			
		} else if (Features.getUnigramTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
			// I guess nothing!
		}
		
		SelectBasicFeaturesWindow.setNumberOfUnigramFeatures(count * c3);
		SelectBasicFeaturesWindow.countOfUnigramFeatures.set(count * c3);
	}
	
	private void initializeTopWordsFeaturesCount() {
		int 	c1 = 0,
				c2=0,
				c3=Parameters.getClasses().size();
		
		int count = 0;
		
		// Use nouns
		if (Features.isUseTopWordsNouns()) {
			c1++;
		}
		// Use verbs
		if (Features.isUseTopWordsVerbs()) {
			c1++;
		}
		// Use adjectives
		if (Features.isUseTopWordsAdjectives()) {
			c1++;
		}
		// Use adverbs
		if (Features.isUseTopWordsAdverbs()) {
			c1++;
		}
		
		if(Features.getTopWordsTypeOfPos()==null) {
			// This means that the user have not selected this set of features in the first place. Everything should be equal to 0 here!
			c1=0;
		} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.TOGETHER)) {
			c1=1;
			c2 = Features.getNumberOfTopWordsPerClass();
		} else if (Features.getTopWordsTypeOfPos().equals(Features.TypeOfPos.SEPARATED)) {
			c2 = Features.getNumberOfTopWordsPerPos();
		}
		
		if(Features.getTopWordsType()==null) {
			// This means that the user have not selected this set of features in the first place. Everything should be equal to 0 here!
			count = 0;
		} else if (Features.getTopWordsType().equals(Features.TypeOfFeature.UNIQUE)) {
			count = c1*c2*c3;
		} else if (Features.getTopWordsType().equals(Features.TypeOfFeature.SUMMED)) {
			count = c1*c3;
		}
		
		SelectBasicFeaturesWindow.setNumberOfTopWordsFeatures(count);
		SelectBasicFeaturesWindow.countOfTopWordsFeatures.set(count);
	}
	
	private void initializePatternFeaturesCount() {
		int c3 = Parameters.getClasses().size();
		
		int count = 0;

		if(Features.getPatternFeaturesType()==null) {
			// The count should be equal to 0 in this case
		} else if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.SUMMED)) {
			count = c3 * (Features.getMaxPatternLength() - Features.getMinPatternLength() + 1);
		} else if (Features.getPatternFeaturesType().equals(Features.TypeOfFeature.UNIQUE)) {
			count = c3 *Features.getNumberOfPatternsPerClass();
		}
		SelectBasicFeaturesWindow.setNumberOfPatternFeatures(count);
		SelectBasicFeaturesWindow.countOfPatternFeatures.set(count);
	}
	
		
	// =====================================//
	//   Getters and Setters of Components  //
	// =====================================//
	
	// Previous window
	
	public static Previous getPreviousWindow() {
		return previousWindow;
	}

	public static void setPreviousWindow(Previous previousWindow) {
		SelectBasicFeaturesWindow.previousWindow = previousWindow;
	}
	
	//  the counters
	public static int getNumberOfSentimentRelatedFeatures() {
		return numberOfSentimentRelatedFeatures;
	}
	public static void setNumberOfSentimentRelatedFeatures(int numberOfSentimentRelatedFeatures) {
		SelectBasicFeaturesWindow.numberOfSentimentRelatedFeatures = numberOfSentimentRelatedFeatures;
	}
	
	public static int getNumberOfPunctuationFeatures() {
		return numberOfPunctuationFeatures;
	}
	public static void setNumberOfPunctuationFeatures(int numberOfPunctuationFeatures) {
		SelectBasicFeaturesWindow.numberOfPunctuationFeatures = numberOfPunctuationFeatures;
	}

	public static int getNumberOfStylisticFeatures() {
		return numberOfStylisticFeatures;
	}
	public static void setNumberOfStylisticFeatures(int numberOfStylisticFeatures) {
		SelectBasicFeaturesWindow.numberOfStylisticFeatures = numberOfStylisticFeatures;
	}

	public static int getNumberOfSemanticFeatures() {
		return numberOfSemanticFeatures;
	}
	public static void setNumberOfSemanticFeatures(int numberOfSemanticFeatures) {
		SelectBasicFeaturesWindow.numberOfSemanticFeatures = numberOfSemanticFeatures;
	}

	public static int getNumberOfUnigramFeatures() {
		return numberOfUnigramFeatures;
	}
	public static void setNumberOfUnigramFeatures(int numberOfUnigramFeatures) {
		SelectBasicFeaturesWindow.numberOfUnigramFeatures = numberOfUnigramFeatures;
	}

	public static int getNumberOfTopWordsFeatures() {
		return numberOfTopWordsFeatures;
	}
	public static void setNumberOfTopWordsFeatures(int numberOfTopWordsFeatures) {
		SelectBasicFeaturesWindow.numberOfTopWordsFeatures = numberOfTopWordsFeatures;
	}

	public static int getNumberOfPatternFeatures() {
		return numberOfPatternFeatures;
	}
	public static void setNumberOfPatternFeatures(int numberOfPatternFeatures) {
		SelectBasicFeaturesWindow.numberOfPatternFeatures = numberOfPatternFeatures;
	}



	// =====================================//
	//             Other methods            //
	// =====================================//
	
	/**
	 * Private method to check whether Any set of features is select while no feature is actually being used or the opposite
	 * @return whether such inconsistency exists or not (+Displays an alert box)
	 */
	private boolean checkSetOfFeatures() {
		
		if ((sentimentRelatedFeatures.isSelected() && numberOfSentimentRelatedFeatures==0) ||
			(punctuationFeatures.isSelected() && numberOfPunctuationFeatures==0) ||
			(stylisticFeatures.isSelected() && numberOfStylisticFeatures==0) ||
			(semanticFeatures.isSelected() && numberOfSemanticFeatures==0) ||
			(unigramFeatures.isSelected() && numberOfUnigramFeatures==0) ||
			(topWordsFeatures.isSelected() && numberOfTopWordsFeatures==0) ||
			(patternFeatures.isSelected() && numberOfPatternFeatures==0))
			
			{
			boolean test = ConfirmBox.display("Attention", "You have chosen to use one or more set(s) of features, however you have not set the corresponding parameters yet. Are you sure you want to continue?");
			if (test==false) {
				return false;
			}
		}
		
		if ((!sentimentRelatedFeatures.isSelected() && numberOfSentimentRelatedFeatures!=0) ||
			(!punctuationFeatures.isSelected() && numberOfPunctuationFeatures!=0) ||
			(!stylisticFeatures.isSelected() && numberOfStylisticFeatures!=0) ||
			(!semanticFeatures.isSelected() && numberOfSemanticFeatures!=0) ||
			(!unigramFeatures.isSelected() && numberOfUnigramFeatures!=0) ||
			(!topWordsFeatures.isSelected() && numberOfTopWordsFeatures!=0) ||
			(!patternFeatures.isSelected() && numberOfPatternFeatures!=0))
				
				{
				boolean test = ConfirmBox.display("Attention", "You have customized one or more set(s) of features, however you have not selected this (these) set(s) to be extracted. Are you sure you want to continue?");
				if (test==false) {
					return false;
				}
			}
		
		return true;
		
	}
	
	/**
	 * Private method that handles the exit situations
	 */
	private void closeProgram() {
		System.out.println("Cancel Button clicked");
		if (ConfirmBox.display( "Exit", "Are you sure you want to exit?")) {
			System.exit(0);
		}
	}

	

}

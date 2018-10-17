package main.start;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import backend.processor.TweetProcessing;
import commons.io.Reader;
import commons.io.Writer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.items.Features;
import main.items.Parameters;
import main.items.Tweet;
import windows.others.AlertBox;

public class ClassificationProblem {
	
	protected static String text;
	
	protected static double totalTime;
	
	protected static StringProperty textProperty = new SimpleStringProperty("");
	protected static IntegerProperty currentTask = new SimpleIntegerProperty(0);
	protected static BooleanProperty done = new SimpleBooleanProperty(false);
	
	
	//============================================//
	//              RUN THE PROGRAM               //
	//============================================//
	
	public static void run() {

		currentTask.set(0);
		done.set(false);
		
		text =  "Start time                Task								Time\n";
		text = text + "====================================================================================================\n";
		textProperty.set(text);
		
		//============================================//
		//          EXTRACTION OF THE TWEETS          //
		//============================================//
		
		totalTime = 0;
		
		// Collect the training tweets
		String task = "Collect Training tweets";
		double startTime = markStart(task);
		collectTrainingTweets();
		markEnd(startTime);
		
		// Add Basic Components to the training set
		task = "Training tweets: add Basic Components";
		startTime = markStart(task);
		addBaicComponents(Parameters.getTrainingSet());
		markEnd(startTime);
		
		// Collect the test / non-annotated tweets
		task = "Collect test tweets";
		startTime = markStart(task);
		if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
			collectTestTweets();
		} else if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.NONANNOTATEDSET)) {
			collectUnknownTweets();
		}
		markEnd(startTime);
		
		// Add Basic Components to the test / non-annotated set
		if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
			task = "Test tweets: add Basic Components";
			startTime = markStart(task);
			addBaicComponents(Parameters.getTestSet());
		} else if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.NONANNOTATEDSET)) {
			task = "Non-annotated tweets: add Basic Components";
			startTime = markStart(task);
			addBaicComponents(Parameters.getUnknownSet());
		}
		markEnd(startTime);
		
		text = text + "----------------------------------------------------------------------------------------------------\n";
		textProperty.set(text);
		
		//============================================//
		//    COLLECTION OF TOP WORDS AND PATTERNS    //
		//============================================//
		
		// Collect/Import the Top Words
		task = "Collection of Top Words";
		startTime = markStart(task);
		if (Features.isUseTopWords()) {
			collectTopWords(Parameters.getTrainingSet());
		}
		markEnd(startTime);
		
		// Collect/Import the Patterns
		task = "Collection of Basic Patterns";
		startTime = markStart(task);
		if (Features.isUsePatternFeatures()) {
			
//			String training_location = "output\\patterns\\training_patterns.txt";
//			Writer.exportPatterns(Parameters.getTrainingSet(), 21, training_location);
//			
//			String test_location = "output\\patterns\\test_patterns.txt";
//			Writer.exportPatterns(Parameters.getTestSet(), 21, test_location);
			
 			collectBasicPatterns(Parameters.getTrainingSet());
		}
		markEnd(startTime);
		
		// Collect/Import the Advanced Patterns
		task = "Collection of Advanced Patterns";
		startTime = markStart(task);
		if (Features.isUseAdvancedPatternFeatures()) {
			collectAdvancedPatterns(Parameters.getTrainingSet());
		}
		markEnd(startTime);
		
		// Collect the Advanced Unigrams
		task = "Collection of Advanced Unigrams";
		startTime = markStart(task);
		if (Features.isUseAdvancedUnigramFeatures()) {
			Parameters.setAdvancedUnigrams(Reader.getWords(Parameters.getImportedUnigramsLocation()));
		}
		markEnd(startTime);
		
		text = text + "----------------------------------------------------------------------------------------------------\n";
		textProperty.set(text);
		
		//============================================//
		//           TRAINING SET PROCESSING          //
		//============================================//
		
		// Add Basic Sentiment Features
		if (Features.isUseSentimentFeatures()) {
			task = "Training tweets: extract sentiment features";
			startTime = markStart(task);
			addBasicSentimentFeatures(Parameters.getTrainingSet());
			markEnd(startTime);
			
		}
		// Add Punctuation Features
		if (Features.isUsePunctuationFeatures()) {
			task = "Training tweets: extract punctuation features";
			startTime = markStart(task);
			addPunctuationFeatures(Parameters.getTrainingSet());
			markEnd(startTime);
		}
		// Add Stylistic Features
		if (Features.isUseStylisticFeatures()) {
			task = "Training tweets: extract stylistic features";
			startTime = markStart(task);
			addStylisticFeatures(Parameters.getTrainingSet());
			markEnd(startTime);
		}
		// Add Semantic features
		if (Features.isUseSemanticFeatures()) {
			task = "Training tweets: extract semantic features";
			startTime = markStart(task);
			addBasicSemanticFeatures(Parameters.getTrainingSet());
			markEnd(startTime);
		}
		// Add unigram features
		if (Features.isUseUnigramFeatures()) {
			task = "Training tweets: extract Unigram features";
			startTime = markStart(task);
			addUnigramFeatures(Parameters.getTrainingSet());
			markEnd(startTime);
		}
		// add Top Words features
		if (Features.isUseTopWords()) {
			task = "Training tweets: extract top words features";
			startTime = markStart(task);
			addTopWordsFeatures(Parameters.getTrainingSet());
			markEnd(startTime);
		}
		// add Patterns features
		if (Features.isUsePatternFeatures()) {
			task = "Training tweets: extract pattern features";
			startTime = markStart(task);
			addPatternsFeatures(Parameters.getTrainingSet());
			markEnd(startTime);
		}
		// add Advanced Sentiment Features
		if (Features.isUseAdvancedSentimentFeatures()) {
			task = "Training tweets: extract advanced sentiment features";
			startTime = markStart(task);
			addAdvancedSentimentFeatures(Parameters.getTrainingSet());
			markEnd(startTime);
		}
		// add Advanced Semantic Features
		if (Features.isUseAdvancedSemanticFeatures()) {
			task = "Training tweets: extract advanced semantic features";
			startTime = markStart(task);
			addAdvancedSemanticFeatures(Parameters.getTrainingSet());
			markEnd(startTime);
		}
		// add Advanced Pattern Features
		if (Features.isUseAdvancedPatternFeatures()) {
			task = "Training tweets: extract advanced pattern features";
			startTime = markStart(task);
			addAdvancedPatternsFeatures(Parameters.getTrainingSet());
			markEnd(startTime);
		}
		// add Advanced Unigram Features
		if (Features.isUseAdvancedUnigramFeatures()) {
			task = "Training tweets: extract advanced unigram features";
			startTime = markStart(task);
			addAdvancedUnigramFeatures(Parameters.getTrainingSet());
			markEnd(startTime);
		}
		
		text = text + "----------------------------------------------------------------------------------------------------\n";
		textProperty.set(text);
		
		//============================================//
		//       TEST / UNKNOWN SET PROCESSING        //
		//============================================//
		
		if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
			// Add Basic Sentiment Features
			if (Features.isUseSentimentFeatures()) {
				task = "Test tweets: extract sentiment features";
				startTime = markStart(task);
				addBasicSentimentFeatures(Parameters.getTestSet());
				markEnd(startTime);
			}
			// Add Punctuation Features
			if (Features.isUsePunctuationFeatures()) {
				task = "Test tweets: extract punctuation features";
				startTime = markStart(task);
				addPunctuationFeatures(Parameters.getTestSet());
				markEnd(startTime);
			}
			// Add Stylistic Features
			if (Features.isUseStylisticFeatures()) {
				task = "Test tweets: extract stylistic features";
				startTime = markStart(task);
				addStylisticFeatures(Parameters.getTestSet());
				markEnd(startTime);
			}
			// Add Semantic features
			if (Features.isUseSemanticFeatures()) {
				task = "Test tweets: extract semantic features";
				startTime = markStart(task);
				addBasicSemanticFeatures(Parameters.getTestSet());
				markEnd(startTime);
			}
			// Add unigram features
			if (Features.isUseUnigramFeatures()) {
				task = "Test tweets: extract unigram features";
				startTime = markStart(task);
				addUnigramFeatures(Parameters.getTestSet());
				markEnd(startTime);
			}
			// add Top Words features
			if (Features.isUseTopWords()) {
				task = "Test tweets: extract top words features";
				startTime = markStart(task);
				addTopWordsFeatures(Parameters.getTestSet());
				markEnd(startTime);
			}
			// add Patterns features
			if (Features.isUsePatternFeatures()) {
				task = "Test tweets: extract pattern features";
				startTime = markStart(task);
				addPatternsFeatures(Parameters.getTestSet());
				markEnd(startTime);
			}
			// add Advanced Sentiment Features
			if (Features.isUseAdvancedSentimentFeatures()) {
				task = "Test tweets: extract advanced sentiment features";
				startTime = markStart(task);
				addAdvancedSentimentFeatures(Parameters.getTestSet());
				markEnd(startTime);
			}
			// add Advanced Semantic Features
			if (Features.isUseAdvancedSemanticFeatures()) {
				task = "Test tweets: extract advanced semantic features";
				startTime = markStart(task);
				addAdvancedSemanticFeatures(Parameters.getTestSet());
				markEnd(startTime);
			}
			// add Advanced Pattern Features
			if (Features.isUseAdvancedPatternFeatures()) {
				task = "Test tweets: extract advanced pattern features";
				startTime = markStart(task);
				addAdvancedPatternsFeatures(Parameters.getTestSet());
				markEnd(startTime);
			}
			
			// add Advanced Unigram Features
			if (Features.isUseAdvancedUnigramFeatures()) {
				task = "Training tweets: extract advanced unigram features";
				startTime = markStart(task);
				addAdvancedUnigramFeatures(Parameters.getTestSet());
				markEnd(startTime);
			}
			
		} else if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.NONANNOTATEDSET)) {
			// Add Basic Sentiment Features
			if (Features.isUseSentimentFeatures()) {
				task = "Non-annotated tweets: extract sentiment features";
				startTime = markStart(task);
				addBasicSentimentFeatures(Parameters.getUnknownSet());
				markEnd(startTime);
			}
			// Add Punctuation Features
			if (Features.isUsePunctuationFeatures()) {
				task = "Non-annotated tweets: extract punctuation features";
				startTime = markStart(task);
				addPunctuationFeatures(Parameters.getUnknownSet());
				markEnd(startTime);
			}
			// Add Stylistic Features
			if (Features.isUseStylisticFeatures()) {
				task = "Non-annotated tweets: extract stylistic features";
				startTime = markStart(task);
				addStylisticFeatures(Parameters.getUnknownSet());
				markEnd(startTime);
			}
			// Add Semantic features
			if (Features.isUseSemanticFeatures()) {
				task = "Non-annotated tweets: extract semantic features";
				startTime = markStart(task);
				addBasicSemanticFeatures(Parameters.getUnknownSet());
				markEnd(startTime);
			}
			// Add unigram features
			if (Features.isUseUnigramFeatures()) {
				task = "Non-annotated tweets: extract unigram features";
				startTime = markStart(task);
				addUnigramFeatures(Parameters.getUnknownSet());
				markEnd(startTime);
			}
			// add Top Words features
			if (Features.isUseTopWords()) {
				task = "Non-annotated tweets: extract top words features";
				startTime = markStart(task);
				addTopWordsFeatures(Parameters.getUnknownSet());
				markEnd(startTime);
			}
			// add Patterns features
			if (Features.isUsePatternFeatures()) {
				task = "Non-annotated tweets: extract pattern features";
				startTime = markStart(task);
				addPatternsFeatures(Parameters.getUnknownSet());
				markEnd(startTime);
			}
			// add Advanced Sentiment Features
			if (Features.isUseAdvancedSentimentFeatures()) {
				task = "Non-annotated tweets: extract advanced sentiment features";
				startTime = markStart(task);
				addAdvancedSentimentFeatures(Parameters.getUnknownSet());
				markEnd(startTime);
			}
			// add Advanced Semantic Features
			if (Features.isUseAdvancedSemanticFeatures()) {
				task = "Non-annotated tweets: extract advanced semantic features";
				startTime = markStart(task);
				addAdvancedSemanticFeatures(Parameters.getUnknownSet());
				markEnd(startTime);
			}
			// add Advanced Pattern Features
			if (Features.isUseAdvancedPatternFeatures()) {
				task = "Non-annotated tweets: extract advanced pattern features";
				startTime = markStart(task);
				addAdvancedPatternsFeatures(Parameters.getUnknownSet());
				markEnd(startTime);
			}
			// add Advanced Unigram Features
			if (Features.isUseAdvancedUnigramFeatures()) {
				task = "Training tweets: extract advanced unigram features";
				startTime = markStart(task);
				addAdvancedUnigramFeatures(Parameters.getUnknownSet());
				markEnd(startTime);
			}
		}
	}
	
	public static void saveProject() {
		
		text = text + "----------------------------------------------------------------------------------------------------\n";
		textProperty.set(text);
		
		String task = "Saving data";
		double startTime = markStart(task);
		
		// Create the project Folder
		createFolder();
		
		// Save project 
		saveProjectConfiguration();
		saveFeatures();
		
		// Save Top Words and patterns
		if (Parameters.isSaveTopWords()) {
			saveTopWords();
		}
		
		if (Parameters.isSaveBasicPatterns()) {
			saveBasicPatterns();
		}
		
		if (Parameters.isSaveAdvancedPatterns()) {
			saveAdvancedPatterns();
		}
		
		
		// Save in CSV FORMAT
		if (Parameters.isSaveCsv()) {
			saveCsvFile("Training", Parameters.getTrainingSet());
			if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
				saveCsvFile("Test", Parameters.getTestSet());
			} else if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.NONANNOTATEDSET)) {
				saveCsvFile("Non-Annotated", Parameters.getUnknownSet());
			}
		}
		
		// Save in TXT FORMAT
		if (Parameters.isSaveTxt()) {
			saveTxtFile("Training", Parameters.getTrainingSet());
			if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
				saveTxtFile("Test", Parameters.getTestSet());
			} else if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.NONANNOTATEDSET)) {
				saveTxtFile("Non-Annotated", Parameters.getUnknownSet());
			}
		}
		
		// Save in ARFF FORMAT
		if (Parameters.isSaveArff()) {
			saveArffFile("Training", Parameters.getTrainingSet());
			if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
				saveArffFile("Test", Parameters.getTestSet());
			} else if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.NONANNOTATEDSET)) {
				saveArffFile("Non-Annotated", Parameters.getUnknownSet());
			}
		}
		markEnd(startTime);
	}
	
	public static void end() {
		text = text + "====================================================================================================\n";
		String task = "TOTAL";
		double startTime = System.currentTimeMillis();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		DecimalFormat df2 = new DecimalFormat(".###");
		
		text = text + format.format(startTime) + "       " + task + getTab(task) + df2.format(totalTime);
		textProperty.set(text);
		done.set(true);
	}
	
	
	//============================================//
	//             ADD TWEET FEATURES             //
	//============================================//
	
	private static void collectTrainingTweets() {
		if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
			Parameters.setTrainingSet(Reader.readRawFile(Parameters.getTrainingSetLocation(), true));
		} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
			Parameters.setTrainingSet(Reader.readRawFileQuantification(Parameters.getTrainingSetLocation(), true));
		}
		
	}
	
	private static void collectTestTweets() {
		if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
			Parameters.setTestSet(Reader.readRawFile(Parameters.getTestSetLocation(), true));
		} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
			Parameters.setTestSet(Reader.readRawFileQuantification(Parameters.getTestSetLocation(), true));
		}
	}
	
	private static void collectUnknownTweets() {
		if (Loader.getProjectGoal().equals(Loader.ProjectGoal.CLASSIFICATION)) {
			Parameters.setUnknownSet(Reader.readRawFile(Parameters.getNonAnnotatedDataLocation(), false));
		} else if (Loader.getProjectGoal().equals(Loader.ProjectGoal.QUANTIFICATION)) {
			Parameters.setUnknownSet(Reader.readRawFileQuantification(Parameters.getNonAnnotatedDataLocation(), false));
		}
	}

	private static void addBaicComponents(ArrayList<Tweet> tweets) {
		for (Tweet tweet : tweets) {
			TweetProcessing.preprocessTweets(tweet);
			TweetProcessing.addBasicComponents(tweet);
		}
	}
	
	private static void collectTopWords(ArrayList<Tweet> tweets) {
		if (Parameters.isImportTopWords()) {
			TweetProcessing.importTopWords();
		} else {
			TweetProcessing.extractTopWords(tweets);
		}
		
	}
 	
	private static void collectBasicPatterns(ArrayList<Tweet> tweets) {
		if (Parameters.isImportBasicPatterns()) {
			// TweetProcessing.importPatterns();
			TweetProcessing.importBasicPatternsNumeric();
		} else {
			// TweetProcessing.extractPatterns(tweets, Loader.getProjectGoal());
			TweetProcessing.extractNumericPatterns(tweets, Loader.getProjectGoal());
			//saveBasicPatterns(); // TODO remove later
		}
	}
	
	private static void collectAdvancedPatterns(ArrayList<Tweet> tweets) {
		if (Parameters.isImportAdvancedPatterns()) {
			// TweetProcessing.importAdvancedPatterns();
			TweetProcessing.importAdvancedPatternsNumeric();
		} else {
			// TweetProcessing.extractAdvancedPatterns(tweets, Loader.getProjectGoal());
			TweetProcessing.extractAdvancedNumericPatterns(tweets, Loader.getProjectGoal());
			//saveAdvancedPatterns(); // TODO remove later
		}
	}
	
	private static void addBasicSentimentFeatures(ArrayList<Tweet> tweets) {
		for (Tweet tweet : tweets) {
			TweetProcessing.addBasicSentimentFeatures(tweet);
		}
	}

	private static void addPunctuationFeatures(ArrayList<Tweet> tweets) {
		for (Tweet tweet : tweets) {
			TweetProcessing.addPunctuationFeatures(tweet);
		}
	}
	
	private static void addStylisticFeatures(ArrayList<Tweet> tweets) {
		for (Tweet tweet : tweets) {
			TweetProcessing.addStylisticFeatures(tweet);
		}
	}
	
	private static void addBasicSemanticFeatures(ArrayList<Tweet> tweets) {
		for (Tweet tweet : tweets) {
			TweetProcessing.addBasicSemanticFeatures(tweet);
		}
	}
	
	private static void addUnigramFeatures(ArrayList<Tweet> tweets) {
		for (Tweet tweet : tweets) {
			TweetProcessing.addUnigramFeatures(tweet);
		}
	}
	
	private static void addTopWordsFeatures(ArrayList<Tweet> tweets) {
		for (Tweet tweet : tweets) {
			TweetProcessing.addTopWordsFeatures(tweet);
		}
	}
	
	private static void addPatternsFeatures(ArrayList<Tweet> tweets) {
		for (Tweet tweet : tweets) {
			// TweetProcessing.addBasicPatternsFeatures(tweet);
			TweetProcessing.addBasicPatternsFeaturesNumeric(tweet);
		}
	}
	
	private static void addAdvancedSentimentFeatures(ArrayList<Tweet> tweets) {
		// TODO add this
	}
	
	private static void addAdvancedSemanticFeatures(ArrayList<Tweet> tweets) {
		// TODO add this
	}
	
	private static void addAdvancedPatternsFeatures(ArrayList<Tweet> tweets) {
		for (Tweet tweet : tweets) {
			// TweetProcessing.addAdvancedPatternsFeatures(tweet);
			TweetProcessing.addAdvancedPatternsFeaturesNumeric(tweet);
		}
	}
	
	private static void addAdvancedUnigramFeatures(ArrayList<Tweet> tweets) {
		for (Tweet tweet : tweets) {
			TweetProcessing.addAdvancedUnigramFeatures(tweet);
		}
	}
	
	
	//============================================//
	//                 SAVE FILES                 //
	//============================================//
	
	private static void createFolder() {
		File exportedFileDirectory = new File(Parameters.getProjectLocation());

		if (!exportedFileDirectory.exists()) {
			try {
				exportedFileDirectory.mkdir();
			} catch (SecurityException se) {
				AlertBox.display("Error", "The folder you want to save in cannot be accessed!", "OK");
			}
		}
	}
	
	private static void saveTopWords() {
		String file = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + Parameters.getProjectName() + "-top_words.txt";
		Writer.saveTopWords(file);
	}
	
	private static void saveBasicPatterns() {
		String file = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + Parameters.getProjectName() + "-basic_patterns.txt";
		//Writer.saveBasicPatterns(file);
		Writer.saveBasicPatternsNumeric(file);
	}
	
	private static void saveAdvancedPatterns() {
		String file = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + Parameters.getProjectName() + "-advanced_patterns.txt";
		// Writer.saveAdvancedPatterns(file);
		Writer.saveAdvancedPatternsNumeric(file);
	}
	
	public static void saveCsvFile(String set, ArrayList<Tweet> tweets) {
		String file = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + set + ".csv";
		// Writer.saveCsvFile(file, tweets);
		Writer.saveCsvFileNumeric(file, tweets);
	}
	
	public static void saveTxtFile(String set, ArrayList<Tweet> tweets) {
		// TODO this method as well as the next two ones were changed to public because I needed to call them from the winodw "ClassifierWindow"
		// See if this makes sense, otherwise, change things and create (maybe) a similar method in the other class (Even though this might not
		// make sense!!!)
		String file = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + set + ".txt";
		// Writer.saveTxtFile(file, tweets);
		Writer.saveTxtFileNumeric(file, tweets);
	}
	
	public static void saveArffFile(String set, ArrayList<Tweet> tweets) {
		String file = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + set + ".arff";
		// Writer.saveArffFile(file, tweets);
		Writer.saveArffFileNumeric(file, tweets);
	}
	
	private static void saveProjectConfiguration() {
		String file = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + Parameters.getProjectName() + "-Config.senta";
		Writer.saveProject(file);
		
		// Save the project into The list of recent project files
		if (Loader.getRecentFiles().size()>10) {
			Loader.getRecentFiles().removeLast();
		}
		Loader.getRecentFiles().addFirst(file);
		Writer.saveRecentFiles();
		
	}
	
	private static void saveFeatures() {
		String file = Parameters.getProjectLocation() + "\\" + Parameters.getProjectName() + "\\" + Parameters.getProjectName() + "-Features.sfs";
		Writer.saveFeatures(file);
	}
	
	
	//============================================//
	//            GETTERS AND SETTERS             //
	//============================================//
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		ClassificationProblem.text = text;
	}
	
	public static StringProperty getTextProperty() {
		return textProperty;
	}
	public static void setTextProperty(StringProperty textProperty) {
		ClassificationProblem.textProperty = textProperty;
	}
	
	public static IntegerProperty getCurrentTask() {
		return currentTask;
	}
	public static void setCurrentTask(IntegerProperty currentTask) {
		ClassificationProblem.currentTask = currentTask;
	}
	public static void setCurrentTask(int currentTask) {
		if (ClassificationProblem.currentTask==null) {
			ClassificationProblem.currentTask = new SimpleIntegerProperty(0);
		}
		ClassificationProblem.setCurrentTask(currentTask);
	}
	
	public static BooleanProperty getDone() {
		return done;
	}
	public static void setDone(BooleanProperty done) {
		ClassificationProblem.done = done;
	}
	public static void setDone(boolean done) {
		if (ClassificationProblem.done==null) {
			ClassificationProblem.done = new SimpleBooleanProperty(false);
		}
		ClassificationProblem.done.set(done);
	}
	
	
	//============================================//
	//              PRIVATE METHODS               //
	//============================================//
	
	private static double markStart(String task) {		
		double startTime = System.currentTimeMillis();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		text = text + format.format(startTime) + "       " + task + getTab(task);
		textProperty.set(text);
		return startTime;
	}
	
	private static void markEnd(double startTime) {
		double time = (double) (System.currentTimeMillis() - startTime) / 1000;
		totalTime += time;
		text = text + time + "\n";
		textProperty.set(text);
		currentTask.set(currentTask.get() + 1);
	}
	
	private static String getTab (String task) {
		
		String t = "\t";
		String tab = "";
		
		if (task.length()<=5) {
			tab = t + t + t + t + t + t + t + t;
		} else if (task.length()>5 && task.length()<=13) {
			tab = t + t + t + t + t + t + t;
		} else if (task.length()>13 && task.length()<=21) {
			tab = t + t + t + t + t + t;
		} else if (task.length()>21 && task.length()<=29) {
			tab = t + t + t + t + t;
		} else if (task.length()>29 && task.length()<=37) {
			tab = t + t + t + t;
		} else if (task.length()>37 && task.length()<=45) {
			tab = t + t + t;
		} else if (task.length()>45 && task.length()<=53) {
			tab = t + t;
		} else if (task.length()>53 && task.length()<=61) {
			tab = t;
		}
		
		return tab;
	}

	
	// =====================================//
	//            REINITIALIZER             //
	// =====================================//
	
	/**
	 * Re-initialize all the features to the [null / 0 / false] values
	 */
	public static void reinitialize() {
		text = null;
		totalTime = 0;
		
		textProperty = new SimpleStringProperty("");
		currentTask = new SimpleIntegerProperty(0);
		done = new SimpleBooleanProperty(false);
	}

	
	
	
	
}

package main.start;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import backend.processor.QuantifierProcessing;
import backend.processor.TweetProcessing;
import commons.constants.Constants;
import commons.io.Reader;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import main.items.Features;
import main.items.Parameters;
import main.items.Tweet;

public class QuantificationProblem {
	
	protected static String text;
	
	protected static double totalTime;
	
	protected static StringProperty textProperty = new SimpleStringProperty("");
	protected static IntegerProperty currentTask = new SimpleIntegerProperty(0);
	protected static IntegerProperty totalNumberOfTasks = new SimpleIntegerProperty(0);
	protected static BooleanProperty done = new SimpleBooleanProperty(false);
	
	private static String n ="\n";
	private static String t ="\t";
	private static String sep = "-------------------------------------------------------------------------------------------\n";
	
	private static DecimalFormat df2 = new DecimalFormat("0.###");
	
	
	// These will be used to store the optimal values so that tweets, at the end, are again attributed the correct classes
	private static double optimal_tau = 0.0;
	private static double optimal_mu = 0.0;
	private static double optimal_nu = 0.0;
	private static double optimal_treshold = 0.0;
	
	
	//============================================//
	//              RUN THE PROGRAM               //
	//============================================//
	
	public static void run() {
		
		currentTask = new SimpleIntegerProperty(0);
		totalNumberOfTasks = new SimpleIntegerProperty(0);
		
		done.set(false);
		
		// Main title
		text =  "Quantification" + n + "==============" + n + n;
		textProperty.set(text);
		
		// Preparing the data
		text = text + sep + "1. Preparing the data" + n + sep;
		textProperty.set(text);
		prepareData();
		
		// Optimization of parameters
		text = text + sep +  "2. Optimizing the parameters\n" + sep;
		textProperty.set(text);
		optimize();
		textProperty.set(text);
		
//		// Saving quantification results
//		text = text + sep +  "3. Saving results\n" + sep;
//		textProperty.set(text);
//		save();
		
		// Outputting predictions
		if (Features.isOutputPredictions()) {
			text = text + sep +  "4. Predictions\n" + sep;
			text = text + "[NOTE: Tweets whose polarity was wrongly detected might have empty prediction list]" + n + n;
			
			textProperty.set(text);
			outputPredictions();
		}
		
		// Save the output
//		text = text + sep +  "5. Saving outputs\n" + sep;
//		save();
		
	}
	
	
	//============================================//
	//             ADD TWEET FEATURES             //
	//============================================//
	
	private static void prepareData() {
		if (Features.isUseManualParameters()) {
			prepareDataManual();
		} else {
			prepareDataAutomatic();
		}		
	}
	
	private static void prepareDataManual() {
		
		// Count of families of features to use

		text = text + "Use unigram features:       [" + Features.isUseUnigramFeatures() + "] - Used in Quantification: [" + Features.isUseQuantifUnigrams() + "]" + n;
		textProperty.set(text);

		text = text + "Use basic pattern features: [" + Features.isUsePatternFeatures() + "] - Used in Quantification: [" + Features.isUseQuantifBasicPatterns() + "]" + n;
		textProperty.set(text);
		
		text = text + "Use adv. pattern features:  [" + Features.isUseAdvancedPatternFeatures() + "] - Used in Quantification: [" + Features.isUseQuantifAdvancedPatterns() + "]" + n;
		textProperty.set(text);
				
		Features.setCoefficients(Features.getManualCoefficients());
		totalNumberOfTasks.set(1);

		text = text + "Coefficients extracted" + n;
		textProperty.set(text);
		
		// Select the data set to use
		ArrayList<Tweet> tweets;
		
		if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
			tweets = Parameters.getTestSet();
			text = text + "Data set to use: " + "[Test Set]" + n;
			textProperty.set(text);
		} else {
			tweets = Parameters.getUnknownSet();
			text = text + "Data set to use: " + "[Non-annotated Set]" + n;
			textProperty.set(text);
		}
		
		// Add Unigram final scores
		if (Features.isUseQuantifUnigrams()) {
			for (Tweet tweet : tweets) {
				TweetProcessing.addUnigramScores(tweet, Parameters.getClasses());
			}
			text = text + "Unigram scores extracted" + n;
			textProperty.set(text);
		}
		
		// Add the basic pattern final scores
		if (Features.isUseQuantifBasicPatterns()) {
			
			int m = Features.getMaxPatternLength() - Features.getMinPatternLength() + 1;
			int min = Features.getMinPatternLength();
			
			double[] factors = new double[m];
			
			for (int i=0; i<m; i++) {
				factors[i] = (double) (min + i-1) / (double) (min + i+1); 
			}
			
			for (Tweet tweet : tweets) {
				TweetProcessing.addBasicPatternScores(tweet, Parameters.getClasses(), factors);
			}
			text = text + "Basic pattern scores extracted" + n;
			textProperty.set(text);
		}
		
		// Add the Advanced pattern final scores
		if (Features.isUseQuantifAdvancedPatterns()) {
			
			int m = Features.getAdvancedMaxPatternLength() - Features.getAdvancedMinPatternLength() + 1;
			int min = Features.getAdvancedMinPatternLength();
			
			double[] factors = new double[m];
			
			for (int i=0; i<m; i++) {
				factors[i] = (double) (min + i-1) / (double) (min + i+1); 
			}
			
			for (Tweet tweet : tweets) {
				TweetProcessing.addAdvancedPatternScores(tweet, Parameters.getClasses(), factors);
			}
			text = text + "Advanced pattern scores extracted" + n;
			textProperty.set(text);
		}
		
	}
	
	private static void prepareDataAutomatic() {
		
		// Count of families of features to use
		int numberOfSets = 0;
		if (Features.isUseUnigramFeatures()) {
			numberOfSets++;
		}
		text = text + "Use unigram features:       [" + Features.isUseUnigramFeatures() + "] - Used in Quantification: [" + Features.isUseUnigramFeatures() + "]" + n;
		textProperty.set(text);
		
		if (Features.isUsePatternFeatures()) {
			numberOfSets++;
		}
		text = text + "Use basic pattern features: [" + Features.isUsePatternFeatures() + "] - Used in Quantification: [" + Features.isUsePatternFeatures() + "]" + n;
		textProperty.set(text);
		
		if (Features.isUseAdvancedPatternFeatures()) {
			numberOfSets++;
		}
		text = text + "Use adv. pattern features:  [" + Features.isUseAdvancedPatternFeatures() + "] - Used in Quantification: [" + Features.isUseAdvancedPatternFeatures() + "]" + n;
		textProperty.set(text);
		
		// set the corresponding scoring coefficients
		if (numberOfSets == 0) {
			double[][] coefficients = new double[0][0];
			Features.setCoefficients(coefficients);
			totalNumberOfTasks.set(1);
		} else if (numberOfSets == 1) {
			double[][] coefficients = new double[1][1];
			coefficients[0][0] = 1;
			Features.setCoefficients(coefficients);
			totalNumberOfTasks.set(1);
		} else if (numberOfSets == 2) {
			double[][] coefficients = new double[2][11];
			for (int i = 0; i < 11; i++) {
				coefficients[0][i] = (double) i / 10;
				coefficients[1][i] = 1 - coefficients[0][i];
				totalNumberOfTasks.set(11);
			}
			Features.setCoefficients(coefficients);
		} else if (numberOfSets == 3) {
			Features.setCoefficients(Reader.getCoefficients(Constants.COEFFICIENTS));
			totalNumberOfTasks.set(66);
		} 
		
		text = text + "Coefficients extracted" + n;
		textProperty.set(text);
		
		// Select the data set to use
		ArrayList<Tweet> tweets;
		
		if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
			tweets = Parameters.getTestSet();
			text = text + "Data set to use: " + "[Test Set]" + n;
			textProperty.set(text);
		} else {
			tweets = Parameters.getUnknownSet();
			text = text + "Data set to use: " + "[Non-annotated Set]" + n;
			textProperty.set(text);
		}
		
		// Add Unigram final scores
		if (Features.isUseUnigramFeatures()) {
			for (Tweet tweet : tweets) {
				TweetProcessing.addUnigramScores(tweet, Parameters.getClasses());
			}
			text = text + "Unigram scores extracted" + n;
			textProperty.set(text);
		}
		
		// Add the basic pattern final scores
		if (Features.isUsePatternFeatures()) {
			
			int m = Features.getMaxPatternLength() - Features.getMinPatternLength() + 1;
			int min = Features.getMinPatternLength();
			
			double[] factors = new double[m];
			
			for (int i=0; i<m; i++) {
				factors[i] = (double) (min + i-1) / (double) (min + i+1); 
			}
			
			for (Tweet tweet : tweets) {
				TweetProcessing.addBasicPatternScores(tweet, Parameters.getClasses(), factors);
			}
			text = text + "Basic pattern scores extracted" + n;
			textProperty.set(text);
		}
		
		// Add the Advanced pattern final scores
		if (Features.isUseAdvancedPatternFeatures()) {
			
			int m = Features.getAdvancedMaxPatternLength() - Features.getAdvancedMinPatternLength() + 1;
			int min = Features.getAdvancedMinPatternLength();
			
			double[] factors = new double[m];
			
			for (int i=0; i<m; i++) {
				factors[i] = (double) (min + i-1) / (double) (min + i+1); 
			}
			
			for (Tweet tweet : tweets) {
				TweetProcessing.addAdvancedPatternScores(tweet, Parameters.getClasses(), factors);
			}
			text = text + "Advanced pattern scores extracted" + n;
			textProperty.set(text);
		}
		
	}
	
	
	private static void optimize() {
		if (Features.isUseManualParameters()) {
			optimizeManual();
		} else {
			optimizeAutomatic();
		}	
	}
	
	private static void optimizeManual() {
		double[][] coefficients = Features.getCoefficients();
		double threshold = Features.getSentimentsThreshold();
		
		// Select the data set to use
		ArrayList<Tweet> tweets;
		
		if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
			tweets = Parameters.getTestSet();
		} else {
			tweets = Parameters.getUnknownSet();
		}
		
		text = text + "Measuring the scores the specified coefficients:" + n;
		textProperty.set(text);

		currentTask.set(1);
		
		double tau = 0,
				mu = 0,
				nu = 0;
		

		boolean isUnigrams = Features.isUseQuantifUnigrams(), 
				isBasicPatt = Features.isUseQuantifBasicPatterns(),
				isAdvPatt = Features.isUseQuantifAdvancedPatterns();
		
		
		if (isUnigrams && isBasicPatt && isAdvPatt) {
			tau = coefficients[0][0];
			mu = coefficients[0][1];
			nu = coefficients[0][2];
		} else
		if (isUnigrams && isBasicPatt && !isAdvPatt) {
			tau = coefficients[0][0];
			mu = coefficients[0][1];
		} else
		if (isUnigrams && !isBasicPatt && isAdvPatt) {
			tau = coefficients[0][0];
			nu = coefficients[0][1];
		} else 
		if (isUnigrams && !isBasicPatt && !isAdvPatt) {
			tau = coefficients[0][0];
		} else 
		if (!isUnigrams && isBasicPatt && isAdvPatt) {
			mu = coefficients[0][0];
			nu = coefficients[0][1];
		} else
		if (!isUnigrams && isBasicPatt && !isAdvPatt) {
			mu = coefficients[0][0];
		} else
		if (!isUnigrams && !isBasicPatt && isAdvPatt) {
			nu = coefficients[0][0];
		}
		
		// generating the final scores for the given coefficients
		for (Tweet tweet : tweets) {
			TweetProcessing.generateFinalScore(tweet, isUnigrams, isBasicPatt, isAdvPatt, tau, mu, nu);
		}
		
		double numberOfTweets = tweets.size();
		
		double bestF1 = 0;
		double bestPrec = 0; // This is NOT the best precision, but the precision of the same prediction that gave the highest F1
		double bestRec = 0; // This is NOT the best precision, but the precision of the same prediction that gave the highest F1
		
		double tempPrec = 0;
		double tempRec = 0;
		double tempF1 = 0;
		
		for (Tweet tweet : tweets) {
			TweetProcessing.predictClasses(tweet, threshold);
			tempPrec += QuantifierProcessing.measurePrecision(tweet.getTweetClasses(), tweet.getPredictedClasses());
			tempRec += QuantifierProcessing.measureRecall(tweet.getTweetClasses(), tweet.getPredictedClasses());
			tempF1 += QuantifierProcessing.measureF1(tweet.getTweetClasses(), tweet.getPredictedClasses());
		}
		
		bestF1 = tempPrec / numberOfTweets;
		bestPrec = tempRec / numberOfTweets;
		bestRec = tempF1 / numberOfTweets;
		
		
		String coef = Arrays.toString(coefficients[0]);
		text = text + "Threshold= " + df2.format(threshold) + "   ";
		text = text + "Coefficients= " + coef + "   ";
		
		
		text = text + "Prec = " + df2.format(bestPrec) + "  "
					+ "Rec = " + df2.format(bestRec) + "  "
					+ "F1 = " + df2.format(bestF1) + n;
		textProperty.set(text);
		
		text = text + sep;
		text = text + "3. Optimal Results\n";
		text = text + sep;
		
		text = text + "The best results were obtained for the following set of parameters:" + n;
		text = text + "   Threshold:    " + df2.format(threshold) + n;
		text = text + "   Coefficients: " + coef + n;
		
		text = text + "Prec = " + df2.format(bestPrec) + "  "
				+ "Rec = " + df2.format(bestRec) + "  "
				+ "F1 = " + df2.format(bestF1) + n;
		textProperty.set(text);
		
		
	}
	
	private static void optimizeAutomatic() {
		double[][] coefficients = Features.getCoefficients();
		
		// Select the data set to use
		ArrayList<Tweet> tweets;
		
		if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
			tweets = Parameters.getTestSet();
		} else {
			tweets = Parameters.getUnknownSet();
		}
		
		text = text + "Measuring the scores for different coefficients:" + n;
		textProperty.set(text);
		
		double overallBestF1 = 0;
		double overallBestPrec = 0; // This is NOT the best precision, but the precision of the same prediction that gave the highest F1
		double overallBestRec = 0; // This is NOT the best precision, but the precision of the same prediction that gave the highest F1
		double overallBestThreshold = 0;
		double[] overallBestParameters = coefficients[0] ;
		
		for (int i=0; i<coefficients.length; i++) {
			currentTask.set(i);
			// System.out.println("The current task is " + currentTask.get());
			
			double tau = 0,
					mu = 0,
					nu = 0;
			
			boolean isUnigrams = Features.isUseUnigramFeatures(),
					isBasicPatt = Features.isUsePatternFeatures(),
					isAdvPatt = Features.isUseAdvancedPatternFeatures();
			
			if (isUnigrams && isBasicPatt && isAdvPatt) {
				tau = coefficients[i][0];
				mu = coefficients[i][1];
				nu = coefficients[i][2];
			} else
			if (isUnigrams && isBasicPatt && !isAdvPatt) {
				tau = coefficients[i][0];
				mu = coefficients[i][1];
			} else
			if (isUnigrams && !isBasicPatt && isAdvPatt) {
				tau = coefficients[i][0];
				nu = coefficients[i][1];
			} else 
			if (isUnigrams && !isBasicPatt && !isAdvPatt) {
				tau = coefficients[i][0];
			} else 
			if (!isUnigrams && isBasicPatt && isAdvPatt) {
				mu = coefficients[i][0];
				nu = coefficients[i][1];
			} else
			if (!isUnigrams && isBasicPatt && !isAdvPatt) {
				mu = coefficients[i][0];
			} else
			if (!isUnigrams && !isBasicPatt && isAdvPatt) {
				nu = coefficients[i][0];
			}
			
			// generating the final scores for the given coefficients
			for (Tweet tweet : tweets) {
				TweetProcessing.generateFinalScore(tweet, isUnigrams, isBasicPatt, isAdvPatt, tau, mu, nu);
			}
			
			double numberOfTweets = tweets.size();
			
			double bestF1 = 0;
			double bestPrec = 0; // This is NOT the best precision, but the precision of the same prediction that gave the highest F1
			double bestRec = 0; // This is NOT the best precision, but the precision of the same prediction that gave the highest F1
			double bestThreshold = 0;
			
			for (int j=0; j<101; j++) {
				double threshold = (double) j / 100;
				
				double tempPrec = 0;
				double tempRec = 0;
				double tempF1 = 0;
				
				for (Tweet tweet : tweets) {
					TweetProcessing.predictClasses(tweet, threshold);
					tempPrec += QuantifierProcessing.measurePrecision(tweet.getTweetClasses(), tweet.getPredictedClasses());
					tempRec += QuantifierProcessing.measureRecall(tweet.getTweetClasses(), tweet.getPredictedClasses());
					tempF1 += QuantifierProcessing.measureF1(tweet.getTweetClasses(), tweet.getPredictedClasses());
				}
				
				tempPrec = tempPrec / numberOfTweets;
				tempRec = tempRec / numberOfTweets;
				tempF1 = tempF1 / numberOfTweets;
				
				if (tempF1 > bestF1) {
					bestF1 = tempF1;
					bestPrec = tempPrec;
					bestRec = tempRec;
					bestThreshold = threshold;
				}
				
				if (bestF1 > overallBestF1) {
					overallBestF1 = bestF1;
					overallBestPrec = bestPrec;
					overallBestRec = bestRec;
					overallBestThreshold = bestThreshold;
					overallBestParameters = coefficients[i] ;
					
					optimal_tau = coefficients[i][0];
					optimal_mu = coefficients[i][1];
					optimal_nu = coefficients[i][2];
					optimal_treshold = threshold;
				}
				
			}
			
			String coef = Arrays.toString(coefficients[i]);
			
			text = text + "Threshold= " + df2.format(bestThreshold) + "   ";
			text = text + "Coefficients= " + coef + "   ";
			
			
			text = text + "Prec = " + df2.format(bestPrec) + "  "
						+ "Rec = " + df2.format(bestRec) + "  "
						+ "F1 = " + df2.format(bestF1) + n;
			textProperty.set(text);
		}
		
		currentTask.set(currentTask.get() + 1);
		
		text = text + sep;
		text = text + "3. Optimal Results\n";
		text = text + sep;
		
		String coef = Arrays.toString(overallBestParameters);
		
		text = text + "The best results were obtained for the following set of parameters:" + n;
		text = text + "   Threshold:    " + df2.format(overallBestThreshold) + n;
		text = text + "   Coefficients: " + coef + n;
		
		text = text + "Prec = " + df2.format(overallBestPrec) + "  "
				+ "Rec = " + df2.format(overallBestRec) + "  "
				+ "F1 = " + df2.format(overallBestF1) + n;
		textProperty.set(text);
		
		
	}
	
	
	private static void outputPredictions() {
		if (Features.isUseManualParameters()) {
			outputPredictionsManual();
		} else {
			outputPredictionsAutomatic();
		}	
	}
	
	
	private static void outputPredictionsManual() {
		ArrayList<Tweet> tweets;
		
		if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
			tweets = Parameters.getTestSet();
		} else {
			tweets = Parameters.getUnknownSet();
		}
		
		// Get the values
		double[][] coefficients = Features.getCoefficients();
		double threshold = Features.getSentimentsThreshold();
		
		double tau = 0,
				mu = 0,
				nu = 0;
		
		boolean isUnigrams = Features.isUseQuantifUnigrams(), 
				isBasicPatt = Features.isUseQuantifBasicPatterns(),
				isAdvPatt = Features.isUseQuantifAdvancedPatterns();
		
		if (isUnigrams && isBasicPatt && isAdvPatt) {
			tau = coefficients[0][0];
			mu = coefficients[0][1];
			nu = coefficients[0][2];
		} else
		if (isUnigrams && isBasicPatt && !isAdvPatt) {
			tau = coefficients[0][0];
			mu = coefficients[0][1];
		} else
		if (isUnigrams && !isBasicPatt && isAdvPatt) {
			tau = coefficients[0][0];
			nu = coefficients[0][1];
		} else 
		if (isUnigrams && !isBasicPatt && !isAdvPatt) {
			tau = coefficients[0][0];
		} else 
		if (!isUnigrams && isBasicPatt && isAdvPatt) {
			mu = coefficients[0][0];
			nu = coefficients[0][1];
		} else
		if (!isUnigrams && isBasicPatt && !isAdvPatt) {
			mu = coefficients[0][0];
		} else
		if (!isUnigrams && !isBasicPatt && isAdvPatt) {
			nu = coefficients[0][0];
		}
		
		
		for (int i=0; i< tweets.size(); i++ ) {
			String act = tweets.get(i).getTweetClasses().toString();
			
			TweetProcessing.generateFinalScore(tweets.get(i), isUnigrams, isBasicPatt, isAdvPatt, tau, mu, nu);
			TweetProcessing.predictClasses(tweets.get(i), threshold);
			
			String pred = tweets.get(i).getPredictedClasses().toString();
			
			text = text + "Tweet [" + i + "]" + t + "Actual Classes=" + act + t + "predictions=" + pred + n;
			textProperty.set(text);
		}
		
		
	}
	
	private static void outputPredictionsAutomatic() {
		ArrayList<Tweet> tweets;
		
		if (Parameters.getTypeOfProject().equals(Parameters.TypeOfProject.TESTSET)) {
			tweets = Parameters.getTestSet();
		} else {
			tweets = Parameters.getUnknownSet();
		}
		
		boolean isUnigrams = Features.isUseUnigramFeatures(),
				isBasicPatt = Features.isUsePatternFeatures(),
				isAdvPatt = Features.isUseAdvancedPatternFeatures();
		
		for (int i=0; i< tweets.size(); i++ ) {
			String act = tweets.get(i).getTweetClasses().toString();
			
			
			TweetProcessing.generateFinalScore(tweets.get(i), isUnigrams, isBasicPatt, isAdvPatt, optimal_tau, optimal_mu, optimal_nu);
			TweetProcessing.predictClasses(tweets.get(i), optimal_treshold);
			
			String pred = tweets.get(i).getPredictedClasses().toString();
			
			text = text + "Tweet [" + i + "]" + t + "Actual Classes=" + act + t + "predictions=" + pred + n;
			textProperty.set(text);
		}
		
		
	}

	
	//============================================//
	//            GETTERS AND SETTERS             //
	//============================================//
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		QuantificationProblem.text = text;
	}
	
	public static StringProperty getTextProperty() {
		return textProperty;
	}
	public static void setTextProperty(StringProperty textProperty) {
		QuantificationProblem.textProperty = textProperty;
	}
	
	public static IntegerProperty getCurrentTask() {
		return currentTask;
	}
	public static void setCurrentTask(IntegerProperty currentTask) {
		QuantificationProblem.currentTask = currentTask;
	}
	public static void setCurrentTask(int currentTask) {
		if (QuantificationProblem.currentTask==null) {
			QuantificationProblem.currentTask = new SimpleIntegerProperty();
		}
		QuantificationProblem.setCurrentTask(currentTask);
	}
	
	public static IntegerProperty getTotalNumberOfTasks() {
		return totalNumberOfTasks;
	}
	public static void setTotalNumberOfTasks(IntegerProperty totalNumberOfTasks) {
		QuantificationProblem.totalNumberOfTasks = totalNumberOfTasks;
	}
	public static void setTotalNumberOfTasks(int totalNumberOfTasks) {
		if (QuantificationProblem.totalNumberOfTasks == null) {
			QuantificationProblem.totalNumberOfTasks = new SimpleIntegerProperty();
		}
		QuantificationProblem.totalNumberOfTasks.set(totalNumberOfTasks);
	}


	public static BooleanProperty getDone() {
		return done;
	}
	public static void setDone(BooleanProperty done) {
		QuantificationProblem.done = done;
	}
	public static void setDone(boolean done) {
		if (QuantificationProblem.done==null) {
			QuantificationProblem.done = new SimpleBooleanProperty(false);
		}
		QuantificationProblem.done.set(done);
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

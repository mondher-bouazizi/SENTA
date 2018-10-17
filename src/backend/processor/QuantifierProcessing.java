package backend.processor;

import java.util.ArrayList;
import java.util.HashMap;

import commons.constants.Commons;

public class QuantifierProcessing {
	
	// ========================================//
	//     Attribute Unigram/pattern scores    //
	// ========================================//
	
	/**
	 * This takes a HashMap of the sentiments along with the scores, and select the sentiments that have scores higher then the threshold
	 * @param scoredClasses
	 * @param threshold
	 * @return
	 */
	public static ArrayList<String> predictClasses(HashMap<String, Double> scoredClasses, double threshold) {
		
		ArrayList<String> predictions = new ArrayList<>();
		
		for (String cl : scoredClasses.keySet()) {
			if (scoredClasses.get(cl) >= threshold) {
				predictions.add(cl);
			}
		}
		
		return predictions;
	}
	
	/**
	 * Generate the final pattern scores for different sentiments using the given factors
	 * @param sentiments : the sentiments that will be scored
	 * @param initialScores : Pattern scores. This MUST be a matrix m x n where m = # sentiments, and n = # number of lengths, it can't be the opposite
	 * @param factors : Factors for different lengths. This MUST be of dimensions n = # number of lengths
	 * @return the scores of the different sentiments
	 */
	public static HashMap<String, Double> generatePatternScores(ArrayList<String> sentiments, double[][] initialScores, double[] factors, String predictedClass) {
		
		HashMap<String, Double> scoredClasses = new HashMap<>();
		
		if (factors.length != initialScores[0].length) {
			Commons.print("ERROR! There is a discrepancy in the data [scores] x [factors]");
			
			for (String sentiment : sentiments) {
				scoredClasses.put(sentiment, new Double(0));
			}
			
			return scoredClasses;
		}
		
		if (sentiments.size() != initialScores.length) {
			Commons.print("ERROR! There is a discrepancy in the data [sentiments] x [scores]");
			
			for (String sentiment : sentiments) {
				scoredClasses.put(sentiment, new Double(0));
			}
			
			return scoredClasses;
		}
		
		double[][] tempScores = new double[initialScores.length][initialScores[0].length];
		
		if (predictedClass.equalsIgnoreCase("POSITIVE")) {
			for (int i = 0; i< initialScores.length; i++) {
				if (Commons.isPositiveSentiment(sentiments.get(i))) {
					tempScores[i] = initialScores[i];
				} else {
					for (int j = 0; j<initialScores[i].length; j++)
					tempScores[i][j] = 0;
				}
			}
			
			
		} else if (predictedClass.equalsIgnoreCase("NEGATIVE")) {
			for (int i = 0; i< initialScores.length; i++) {
				if (Commons.isNegativeSentiment(sentiments.get(i))) {
					tempScores[i] = initialScores[i];
				} else {
					for (int j = 0; j<initialScores[i].length; j++)
						tempScores[i][j] = 0;
				}
			}
			
		} else if (predictedClass.equalsIgnoreCase("NEUTRAL")) {
			for (int i = 0; i< initialScores.length; i++) {
				if (sentiments.get(i).equalsIgnoreCase("NEUTRAL")) {
					tempScores[i] = initialScores[i];
				} else {
					for (int j = 0; j<initialScores[i].length; j++)
						tempScores[i][j] = 0;
				}
			}
		}
		
		
		double[] newScores = normalize(attributeScores(tempScores, factors));
		
		for (int i = 0; i< newScores.length; i++) {
			scoredClasses.put(sentiments.get(i), newScores[i]);
		}
		
		return scoredClasses;
	}
	
	/**
	 * Generate the final unigram scores for different sentiments using the given factors
	 * @param sentiments : the sentiments that will be scored
	 * @param initialScores  : Unigram scores. This MUST be of dimensions m = # sentiments
	 * @return the scores for the different sentiments
	 */
	public static HashMap<String, Double> generateUnigramScores(ArrayList<String> sentiments, int[] initialScores, String predictedClass) {
		HashMap<String, Double> scoredClasses = new HashMap<>();
		
		if (sentiments.size() != initialScores.length) {
			Commons.print("ERROR! There is a discrepancy in the data [sentiments] x [scores]");
			
			for (String sentiment : sentiments) {
				scoredClasses.put(sentiment, new Double(0));
			}
			
			return scoredClasses;
		}
		
		double[] tempScores = new double[initialScores.length];
		
		if (predictedClass.equalsIgnoreCase("POSITIVE")) {
			
			for (int i = 0; i< initialScores.length; i++) {
				if (Commons.isPositiveSentiment(sentiments.get(i))) {
					tempScores[i] = initialScores[i];
				} else {
					tempScores[i] = 0;
				}
			}
			
			
		} else if (predictedClass.equalsIgnoreCase("NEGATIVE")) {
			for (int i = 0; i< initialScores.length; i++) {
				if (Commons.isNegativeSentiment(sentiments.get(i))) {
					tempScores[i] = initialScores[i];
				} else {
					tempScores[i] = 0;
				}
			}
			
		} else if (predictedClass.equalsIgnoreCase("NEUTRAL")) {
			for (int i = 0; i< initialScores.length; i++) {
				if (sentiments.get(i).equalsIgnoreCase("NEUTRAL")) {
					tempScores[i] = initialScores[i];
				} else {
					tempScores[i] = 0;
				}
			}
		}
		
		double[] newScores = normalize(tempScores);
		
		for (int i = 0; i< newScores.length; i++) {
			scoredClasses.put(sentiments.get(i), newScores[i]);
		}
		
		return scoredClasses;
		
	}
	
	// Private methods
	
	/**
	 * Normalize positive Integer scores to [0..1] interval
	 * @param initialScores : positive scores (integers)
	 * @return an array of the normalized scores [0..1]
	 */
	private static double[] normalize(int[] initialScores) {
		
		double scores[] = new double[initialScores.length];
		
		if (initialScores.length != 0) {
			int max = initialScores[0];
			for (int score : initialScores) {
				if (score > max) {
					max = score;
				}
			}
			
			if (max == 0) {
				for (int i = 0; i<initialScores.length; i++) {
					scores[i] = 0;
				}
			} else {
				for (int i = 0; i<initialScores.length; i++) {
					scores[i] = initialScores[i] / max;
				}
			}
			
		}
		
		return scores;
		
	}
	
	/**
	 * Normalize positive Integer scores to [0..1] interval
	 * @param initialScores : positive scores (doubles)
	 * @return an array of the normalized scores [0..1]
	 */
	private static double[] normalize(double[] initialScores) {
		
		double scores[] = new double[initialScores.length];
		
		if (initialScores.length != 0) {
			double max = initialScores[0];
			for (double score : initialScores) {
				if (score > max) {
					max = score;
				}
			}
			
			if (max == 0) {
				for (int i = 0; i<initialScores.length; i++) {
					scores[i] = 0;
				}
			} else {
				for (int i = 0; i<initialScores.length; i++) {
					scores[i] = initialScores[i] / max;
				}
			}
			
			
			
		}
		
		return scores;
		
	}
	
	/**
	 * From a matrix of scores and factors, return the product of multiplication of each line by the factors
	 * @param initialScores : a matrix of scores (m x n)
	 * @param factors : the factors to be used in the multiplication (dimension = n)
	 * @return the product of multiplication of each line by the factors
	 */
	private static double[] attributeScores(double[][] initialScores, double[] factors) {
		double[] scores = new double[initialScores.length];
		
		for (int i = 0; i<initialScores.length; i++) {
			double score = 0;
			for (int j = 0; j<factors.length; j++) {
				score = score + factors[j] * initialScores[i][j];
			}
			scores[i] = score;
		}
		return scores;
	}
	
	
	
	// ========================================//
	//       Quantification measurements       //
	// ========================================//
	
	/**
	 * Measures the M1 of a classification. M1 is defined as follows: if any of the predictions is correct, the score is 1, otherwise it is 0.
	 * @param trueConditions : true conditions of the classification
	 * @param predictedConditions : predictions of the classification
	 * @return
	 */
	public static double measureM1(ArrayList<String> trueConditions, ArrayList<String> predictedConditions) {
		if (trueConditions == null || predictedConditions == null) {
			return 0;
		}
		
		for (String cond : predictedConditions) {
			if (trueConditions.contains(cond)) {
				return 1;
			}
		}
		
		return 0;
		
	}
	
	/**
	 * Measures the M2 score of a classification. M2 is defined as follows: if m out of the n predictions are correct, M2 = m / n
	 * This measure is applicable only when there are n true conditions and only n predictions are guessed
	 * @param trueConditions : true conditions of the classification
	 * @param predictedConditions : predictions of the classification
	 * @return
	 */
	public static double measureM2(ArrayList<String> trueConditions, ArrayList<String> predictedConditions) {
		if (trueConditions == null || predictedConditions == null || trueConditions.size() == 0 || predictedConditions.size() == 0 ) {
			return 0;
		}
		
		double num = 0;
		double denom = trueConditions.size();
		
		for (String cond : predictedConditions) {
			if (trueConditions.contains(cond)) {
				num += 1;
			}
		}
		return num/denom;
	}
	
	/**
	 * Measures the Precision score of a classification
	 * @param trueConditions : true conditions of the classification
	 * @param predictedConditions : predictions of the classification
	 * @return the precision of classification
	 */
	public static double measurePrecision(ArrayList<String> trueConditions, ArrayList<String> predictedConditions) {
		if (trueConditions == null || predictedConditions == null || trueConditions.size() == 0 || predictedConditions.size() == 0 ) {
			return 0;
		}
		
		int tp = 0;
		int fp = 0;
		int fn = 0;
		
		for (String cond : trueConditions) {
			if (predictedConditions.contains(cond)) {
				tp += 1;
			} else {
				fn += 1;
			}
		}
		
		for (String cond : predictedConditions) {
			if (! trueConditions.contains(cond)) {
				fp +=1;
			}
		}
		
		return measurePrecision(tp, fp, fn);
	}
	
	/**
	 * Measures the Recall of a classification
	 * @param trueConditions : true conditions of the classification
	 * @param predictedConditions : predictions of the classification
	 * @return the Recall of classification
	 */
	public static double measureRecall(ArrayList<String> trueConditions, ArrayList<String> predictedConditions) {
		if (trueConditions == null || predictedConditions == null || trueConditions.size() == 0 || predictedConditions.size() == 0 ) {
			return 0;
		}
		
		int tp = 0;
		int fp = 0;
		int fn = 0;
		
		for (String cond : trueConditions) {
			if (predictedConditions.contains(cond)) {
				tp += 1;
			} else {
				fn += 1;
			}
		}
		
		for (String cond : predictedConditions) {
			if (! trueConditions.contains(cond)) {
				fp +=1;
			}
		}
		
		return measureRecall(tp, fp, fn);
	}
	
	/**
	 * Measures the F1 score of a classification. The F1 score is defined as [2 x (precision x recall) / (precision + recall)]
	 * @param trueConditions : true conditions of the classification
	 * @param predictedConditions : predictions of the classification
	 * @return the F1 score of classification
	 */
	public static double measureF1(ArrayList<String> trueConditions, ArrayList<String> predictedConditions) {
		if (trueConditions == null || predictedConditions == null || trueConditions.size() == 0 || predictedConditions.size() == 0 ) {
			return 0;
		}
		
		int tp = 0;
		int fp = 0;
		int fn = 0;
		
		for (String cond : trueConditions) {
			if (predictedConditions.contains(cond)) {
				tp += 1;
			} else {
				fn += 1;
			}
		}
		
		for (String cond : predictedConditions) {
			if (! trueConditions.contains(cond)) {
				fp +=1;
			}
		}
		return measureF1(tp, fp, fn);
	}

	// Private methods
	
	/**
	 * Measures the precision of a classification
	 * @param tp : Number of True Positives
	 * @param fp : Number of False Positives
	 * @param fn : Number of False Negative
	 * @return the precision of classification
	 */
	private static double measurePrecision(int tp, int fp, int fn) {
		if (tp + fp != 0) {
			return (double) tp / (double)(tp+ fp);
		}
		return 0;
	}
	
	/**
	 * Measures the recall of a classification
	 * @param tp : Number of True Positives
	 * @param fp : Number of False Positives
	 * @param fn : Number of False Negative
	 * @return the recall of classification
	 */
	private static double measureRecall(int tp, int fp, int fn) {
		if (tp + fn != 0) {
			return (double) tp / (double)(tp+ fn);
		}
		return 0;
	}

	/**
	 * Measures the F1 score of a classification. The F1 score is defined as [2 x (precision x recall) / (precision + recall)]
	 * @param tp : Number of True Positives
	 * @param fp : Number of False Positives
	 * @param fn : Number of False Negative
	 * @return the F1 score of classification
	 */
	private static double measureF1(int tp, int fp, int fn) {
		if (tp + fn + fp != 0) {
			return (double)(2 * tp) / (double)(2 * tp + fp + fn);
		}
		return 0;
	}
	
}

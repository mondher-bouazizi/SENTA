package backend.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.lang3.ArrayUtils;

public class PatternProcessing {

	public PatternProcessing() {
		super();
	}
	
	//============================================//
	//              Compare patterns              //
	//============================================//
	
	/**
	 * Compare a pattern and a sub-pattern and attribute a score for the comparison
	 * @param sentenceToCheck : The full vector/pattern of a tweet [String format]
	 * @param referenceVector : The pattern to check [String format]
	 * @param alpha : the score to return in case of parse match
	 * @param gamma : to factor by which the result of incomplete match is multiplied
	 * @return the score of comparison of the full pattern and the sub-pattern
	 */
	public static double comparePatterns(String[] sentenceToCheck, String[] referenceVector, double alpha, double gamma) {

		if (checkPerfectMatch(sentenceToCheck, referenceVector)) {
			return 1.0;
		} else if (checkSparseMatch(sentenceToCheck, referenceVector)) {
			return alpha;
		} else {
			return (checkIncompleteMatch(sentenceToCheck, referenceVector) / (double) referenceVector.length * gamma);
		}
	}
	
	/**
	 * Compare a pattern and a sub-pattern and attribute a score for the comparison
	 * @param sentenceToCheck : The full vector/pattern of a tweet [integer format]
	 * @param referenceVector : The pattern to check [integer format]
	 * @param alpha : the score to return in case of parse match
	 * @param gamma : to factor by which the result of incomplete match is multiplied
	 * @return the score of comparison of the full pattern and the sub-pattern
	 */
	public static double comparePatterns(int[] sentenceToCheck, int[] referenceVector, double alpha, double gamma) {

		if (checkPerfectMatch(sentenceToCheck, referenceVector)) {
			return 1.0;
		} else if (checkSparseMatch(sentenceToCheck, referenceVector)) {
			return alpha;
		} else {
			return (checkIncompleteMatch(sentenceToCheck, referenceVector) / (double) referenceVector.length * gamma);
		}
	}

	
	
	//============================================//
	//             Check perfect match            //
	//============================================//
	
	/**
	 * Check perfect match between a pattern and a sub-pattern (Check whether the sub-pattern exists as it is in the correct order in the long pattern)
	 * @param array : The full vector/pattern of a tweet [String format]
	 * @param subArray : The pattern to check [String format]
	 * @return {@value True} if the sub-pattern exists as it is in the right order in the full pattern, {@value false} otherwise
	 */
	public static boolean checkPerfectMatch(String[] array, String[] subArray) {

		if (array == null || subArray==null) {
			return false;
		}

		if (subArray.length == 0 || array.length == 0 || subArray.length > array.length) {
			return false;
		}

		int limit = array.length - subArray.length;

		for (int i = 0; i <= limit; i++) {
			
				boolean perfectMatch = true;
				
				for (int j = 0; j < subArray.length; j++) {
					if (!subArray[j].equalsIgnoreCase(array[i + j])) {
						perfectMatch = false;
						break;
					}
				}

				if (perfectMatch) {
					return true;
				}

		}
		return false;
	}

	/**
	 * Check perfect match between a pattern and a sub-pattern (Check whether the sub-pattern exists as it is in the correct order in the long pattern)
	 * @param array : The full vector/pattern of a tweet [int format]
	 * @param subArray : The pattern to check [int format]
	 * @return {@value True} if the sub-pattern exists as it is in the right order in the full pattern, {@value false} otherwise
	 */
	public static boolean checkPerfectMatch(int[] array, int[] subArray) {

		if (array == null || subArray==null) {
			return false;
		}

		if (subArray.length == 0 || array.length == 0 || subArray.length > array.length) {
			return false;
		}

		int limit = array.length - subArray.length;

		for (int i = 0; i <= limit; i++) {

				boolean perfectMatch = true;

				for (int j = 0; j < subArray.length; j++) {
					if (subArray[j] != array[i + j]) {
						perfectMatch = false;
						break;
					}
				}

				if (perfectMatch) {
					return true;
				}
				
		}
		return false;
	}
	
	
	//============================================//
	//             Check perfect match            //
	//============================================//
	
	/**
	 * Check sparse match between a pattern and a sub-pattern
	 * @param array : The full vector/pattern of a tweet [String format]
	 * @param subArray : The pattern to check [String format]
	 * @return {@value True} if the sub-pattern exists even with some words in-between in the full pattern, {@value false} otherwise
	 */
	public static boolean checkSparseMatch(String[] array, String[] subArray) {
		// TODO this is not the optimal way, and may contain some error in finding the perfect sequence, re-vist and re-check
		int subArrayLength = subArray.length;
		int arrayLength = array.length;

		if (subArrayLength == 0 || arrayLength == 0 || subArrayLength > arrayLength) {
			return false;
		}

		int limit = arrayLength - subArrayLength;

		for (int i = 0; i <= limit; i++) {
			if (subArray[0].equalsIgnoreCase(array[i])) {

				boolean sparseMatch = false;
				int j = i;
				int pos = 0;

				while (sparseMatch == false && j < arrayLength) {
					if (subArray[pos].equalsIgnoreCase(array[j])) {
						pos++;
					}
					j++;
					if (pos == subArrayLength && subArray[pos - 1].equalsIgnoreCase(array[j - 1])) {
						sparseMatch = true;
						break;
					}
				}

				return sparseMatch;

			}

		}
		return false;
	}
	
	/**
	 * Check sparse match between a pattern and a sub-pattern
	 * @param array : The full vector/pattern of a tweet [int format]
	 * @param subArray : The pattern to check [int format]
	 * @return {@value True} if the sub-pattern exists even with some words in-between in the full pattern, {@value false} otherwise
	 */
	public static boolean checkSparseMatch(int[] array, int[] subArray) {
		// TODO this is not the optimal way, and may contain some error in finding the perfect sequence, re-vist and re-check
		int subArrayLength = subArray.length;
		int arrayLength = array.length;

		if (subArrayLength == 0 || arrayLength == 0 || subArrayLength > arrayLength) {
			return false;
		}

		int limit = arrayLength - subArrayLength;

		for (int i = 0; i <= limit; i++) {
			if (subArray[0] == (array[i])) {

				boolean sparseMatch = false;
				int j = i;
				int pos = 0;

				while (sparseMatch == false && j < arrayLength) {
					if (subArray[pos] == (array[j])) {
						pos++;
					}
					j++;
					if (pos == subArrayLength && subArray[pos - 1] == (array[j - 1])) {
						sparseMatch = true;
						break;
					}
				}
				return sparseMatch;
			}

		}
		return false;
	}

	
	//============================================//
	//           Check incomplete match           //
	//============================================//

	/**
	 * Check incomplete match between a pattern and a sub-pattern
	 * @param array : The full vector/pattern of a tweet [String format]
	 * @param subArray : The pattern to check [String format]
	 * @return {@value True} if the part of the sub-pattern exists in the full pattern, {@value false} otherwise
	 */
	public static int checkIncompleteMatch(String[] array, String[] subArray) {
		// The returned value is supposed to be divided by the length of subArray, then multiplied gamma
		// TODO Re-check the incomplete match [This seems to take some time to run]

		int subArrayLength = subArray.length;
		int arrayLength = array.length;

		if (subArrayLength == 0 || arrayLength == 0) return 0;
		
		int[] maxOverlap = new int[subArrayLength];
		
		for (int i = 0; i < subArrayLength; i++) {
			for (int j = 0; j<arrayLength; j++) {
				if (array[j].equalsIgnoreCase(subArray[i])) {
					maxOverlap[i] = 1 + checkIncompleteMatch(Arrays.copyOfRange(array, j+1,  arrayLength), Arrays.copyOfRange(subArray, i+1,  subArrayLength));
				}
			}
		}
		return Collections.max(Arrays.asList (ArrayUtils.toObject((maxOverlap))));
	}
	
	/**
	 * Check incomplete match between a pattern and a sub-pattern
	 * @param array : The full vector/pattern of a tweet [int format]
	 * @param subArray : The pattern to check [int format]
	 * @return {@value True} if the part of the sub-pattern exists in the full pattern, {@value false} otherwise
	 */
	public static int checkIncompleteMatch(int[] array, int[] subArray) {
		// TODO This is not the optimal way, please re-use the optimal one!

		int subArrayLength = subArray.length;
		int arrayLength = array.length;


		int[] maxOverlap = new int[subArrayLength];
		
		for (int i = 0; i < arrayLength; i++) {
			for (int j = 0; j<subArrayLength; j++) {
				if (array[i]==subArray[j]) {
					for (int k=0; k<j; k++) {
						maxOverlap[k]++;
					}
					break;
				}
			}
		}
		return Collections.max(Arrays.asList (ArrayUtils.toObject((maxOverlap))));
	}
	
	/**
	 * Check incomplete match between a pattern and a sub-pattern
	 * @param array : The full vector/pattern of a tweet [int format]
	 * @param subArray : The pattern to check [int format]
	 * @return {@value True} if the part of the sub-pattern exists in the full pattern, {@value false} otherwise
	 */
	public static int checkIncompleteMatchBackup(int[] array, int[] subArray) {
		// The returned value is supposed to be divided by the length of subArray, then multiplied gamma
		// TODO Re-check the incomplete match [This seems to take some time to run]

		int subArrayLength = subArray.length;
		int arrayLength = array.length;

		if (subArrayLength == 0 || arrayLength == 0) return 0;
		
		int[] maxOverlap = new int[subArrayLength];
		for (int i = 0; i<maxOverlap.length; i++) {
			maxOverlap[i] = 0;
		}
		
		for (int i = 0; i < subArrayLength; i++) {
			for (int j = 0; j<arrayLength; j++) {
				if (array[j] == (subArray[i])) {
					maxOverlap[i] = 1 + checkIncompleteMatch(Arrays.copyOfRange(array, j+1,  arrayLength), Arrays.copyOfRange(subArray, i+1,  subArrayLength));
				}
			}
		}
		return Collections.max(Arrays.asList (ArrayUtils.toObject((maxOverlap))));
	}

	
	
	//============================================//
	//            BACUKP (TO REMOVE?)             //
	//============================================//
	
	// Compare patterns for the extended method
	public static double[] compareSetOfPatterns(String[] sentenceToCheck, ArrayList<String[]> referencePatterns, double alpha, double gamma) {

		int size = referencePatterns.size();

		double[] score = new double[size];

		for (int j = 0; j < size; j++) score[j] = 0.0;


		for (int i = 0; i < size; i++) {

			if (checkPerfectMatch(sentenceToCheck, referencePatterns.get(i))) {
				score[i] = score[i] + 1.0;
			} else if (checkSparseMatch(sentenceToCheck, referencePatterns.get(i))) {
				score[i] = score[i] + alpha;
			} else {
				score[i] = score[i] + checkIncompleteMatch(sentenceToCheck, referencePatterns.get(i)) / (double) referencePatterns.get(i).length * gamma;
			}

		}
		//Constants.print(score);
		return score;
	}
	
}

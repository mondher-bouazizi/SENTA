package commons.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.sf.extjwnl.data.POS;

public class Commons {
	
	//=================================//
	//   PRINT A TEXT IN THE CONSOLE   //
	//=================================//
	
	public static void print(String text) {
		System.out.println(text);
	}

	public static void print(String[] array) {
		System.out.println(Arrays.asList(array));
	}
	
	public static void print(boolean[] array) {
		String output;
		if (array == null) {
			output = "null";
		} else {
			if (array.length == 0) {
				output = "[]";
			} else if (array.length == 1) {
				output = "[" + array[0] + "]";
			} else {
				output = "[" + array[0];
				for (int i = 1; i<array.length; i++) {
					output = output + "," + array[i];
				}
				output = output + "]";
			}
		}
		print(output);
	}

	public static void print(int[] array) {
		String output;
		if (array == null) {
			output = "null";
		} else {
			if (array.length == 0) {
				output = "[]";
			} else if (array.length == 1) {
				output = "[" + array[0] + "]";
			} else {
				output = "[" + array[0];
				for (int i = 1; i<array.length; i++) {
					output = output + ", " + array[i];
				}
				output = output + "]";
			}
		}
		print(output);
	}

	public static void print(double[] array) {
		String output;
		if (array == null) {
			output = "null";
		} else {
			if (array.length == 0) {
				output = "[]";
			} else if (array.length == 1) {
				output = "[" + array[0] + "]";
			} else {
				output = "[" + array[0];
				for (int i = 1; i<array.length; i++) {
					output = output + ", " + array[i];
				}
				output = output + "]";
			}
		}
		print(output);
	}

	public static void print(int[][] array) {
		if (array.length == 0)
			System.out.println("[]");

		else if (array.length == 1) {
			System.out.println("[");
			print(array[0]);
			System.out.println("]");
		} else {
			System.out.print("[");
			for (int i = 0; i < array.length - 1; i++) {
				print(array[i]);
				print(" , ");
			}
			print(array[array.length - 1]);
			System.out.println("]");
		}
	}

	public static void print(double[][] array) {
		if (array.length == 0)
			System.out.println("[]");

		else if (array.length == 1) {
			System.out.println("[");
			print(array[0]);
			System.out.println("]");
		} else {
			System.out.print("[");
			for (int i = 0; i < array.length - 1; i++) {
				print(array[i]);
				print(" , ");
			}
			print(array[array.length - 1]);
			System.out.println("]");
		}
	}
	
	
	//=================================//
	//      ARRAY-RELATED METHODS      //
	//=================================//
	
	/**
	 * Check whether an array is included in a list of arrays
	 * @param array
	 * @param fullArrayList
	 * @return
	 */
	public static boolean isIncluded(String[] array, ArrayList<String[]> fullArrayList) {
		for (String[] refArray : fullArrayList) {
			if (equals(array, refArray)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check whether two arrays of strings are equal
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static boolean equals(String[] array1, String[] array2) {

		if (array1.equals(null) || array2.equals(null))
			return false;

		if (array1.length != array2.length) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			if (!array1[i].equalsIgnoreCase(array2[i])) {
				return false;
			}
		}

		return true;

	}
	
	/**
	 * Check whether two arrays of integers are equal
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static boolean equals(int[] array1, int[] array2) {

		if (array1.equals(null) || array2.equals(null))
			return false;

		if (array1.length != array2.length) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i]) {
				return false;
			}
		}

		return true;

	}
	
	/**
	 * Sort an array of doubles
	 *
	 * @param array : the array to sort
	 * @return the array sorted
	 */
	public static double[] sort(double[] array) {

		int length = array.length;

		for (int j = 1; j < length; j++) {
			double temp = array[j];
			int i = j - 1;
			while ((i > -1) && (array[i] > temp)) {
				array[i + 1] = array[i];
				i--;
			}
			array[i + 1] = temp;
		}

		return array;
	}
	
	/**
	 * Sort an array of integers
	 *
	 * @param array : the array to sort
	 * @return the array sorted
	 */
	public static int[] sort(int[] array) {

		int length = array.length;

		for (int j = 1; j < length; j++) {
			int temp = array[j];
			int i = j - 1;
			while ((i > -1) && (array[i] > temp)) {
				array[i + 1] = array[i];
				i--;
			}
			array[i + 1] = temp;
		}

		return array;
	}
	
	public static double[] findTopNInsersion(double[] values, int n) {

	    int length = values.length;
	    for (int i=1; i<length; i++) {
	        int curPos = i;
	        while ((curPos > 0) && (values[i] > values[curPos-1])) {
	            curPos--;
	        }

	        if (curPos != i) {
	        	double element = values[i];
	            System.arraycopy(values, curPos, values, curPos+1, (i-curPos));
	            values[curPos] = element;
	        }
	    }       

	    return Arrays.copyOf(values, n);        
	}
	
	public static double[] findTopNSelection(double[] values, int n) {
	    int length = values.length;

	    for (int i=0; i<=n; i++) {
	        int maxPos = i;
	        for (int j=i+1; j<length; j++) {
	            if (values[j] > values[maxPos]) {
	                maxPos = j;
	            }
	        }

	        if (maxPos != i) {
	        	double maxValue = values[maxPos];
	            values[maxPos] = values[i];
	            values[i] = maxValue;
	        }           
	    }
	    return Arrays.copyOf(values, n);        
	}
	
	/**
	 * Sum the elements of an array
	 * @param array
	 * @return
	 */
	public static double sumArray(double[] array) {
		double result = 0;
		for (double d : array) {
			result += d;
		}
		return result;
	}
	
	//=================================//
	//   GENERATE PROB. OUT OF DIST.   //
	//=================================//
	
	/**
	 * Generate the probability Vector out of the distribution Vector
	 * @param distributionVector
	 * @return
	 */
	public static double[] generateProbabilityVector(int[] distributionVector) {

		int length = distributionVector.length;

		double[] probabilityVector = new double[length];

		for (int i = 0; i < length; i++) {
			probabilityVector[i] = 0;
		}

		double Sum = 0;

		for (int i = 0; i < length; i++) {
			Sum = Sum + distributionVector[i];
		}
		for (int i = 0; i < length; i++) {
			if (Sum != 0) {
				probabilityVector[i] = (double) distributionVector[i]
						/ (double) Sum;
			}
		}
		return probabilityVector;
	}
	
	/**
	 * Generate the probability Matrix out of the distribution Matrix
	 * @param distributionMatrix
	 * @return
	 */
	public static double[][] generateProbabilityMatrix(
			int[][] distributionMatrix) {

		int length = distributionMatrix.length;

		double[][] probabilityMatrix = new double[length][length];

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				probabilityMatrix[i][j] = 0;
			}
		}

		double[] Sum = new double[length];

		for (int i = 0; i < length; i++) {
			Sum[i] = 0;
			for (int j = 0; j < length; j++) {
				Sum[i] = Sum[i] + distributionMatrix[i][j];
			}
		}

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (Sum[i] != 0) {
					probabilityMatrix[i][j] = (double) distributionMatrix[i][j]
							/ (double) Sum[i];
				}
			}
		}

		return probabilityMatrix;

	}
	

	//=================================//
	//     POS-RELATED OPERATIONS      //
	//=================================//
	
	/**
	 * Return the PoS (Class POS) out of the PoS-tag
	 *
	 * @param posTag PoS tag (e.g., "JJS", "NNP", etc.)
	 * @return the Part of Speech (type {@link POS})
	 */
	public static POS getPOS(String posTag) {

		ArrayList<String> adjective = new ArrayList<String>(Arrays.asList("JJ", "JJR", "JJS", "ADJECTIVE"));
		ArrayList<String> adverb = new ArrayList<String>(Arrays.asList("RB", "RBR", "RBS", "ADVERB"));
		ArrayList<String> noun = new ArrayList<String>(Arrays.asList("NN", "NNS", "NNP", "NNPS", "NOUN"));
		ArrayList<String> verb = new ArrayList<String>(Arrays.asList("VB", "VBD", "VBG", "VBN", "VBP", "VBZ", "VERB"));

		if (adjective.contains(posTag))
			return POS.ADJECTIVE;
		else if (adverb.contains(posTag))
			return POS.ADVERB;
		else if (noun.contains(posTag))
			return POS.NOUN;
		else if (verb.contains(posTag))
			return POS.VERB;
		else
			return null;
	}

	/**
	 * Check whether a PoS Tag is relevant of not. A PoS Tag is considered
	 * relevant when it corresponds to:
	 * <ul>
	 * <li>VERB</li>
	 * <li>ADJECTIVE</li>
	 * <li>ADVERB</li>
	 * <li>NOUN</li>
	 * </ul>
	 *
	 * @param posTag the PoS Tag to verify the relevance.
	 * @return whether a PoS Tag corresponds to a relevant Part of Speech (type
	 * {@link POS}) or not ( true} if it is, false} otherwise)
	 */
	public static boolean isRelevant(String posTag) {
		return getPOS(posTag) != null;
	}

	/**
	 * Check whether a PoS Tag is relevant of not. A PoS Tag is considered
	 * relevant when it is:
	 * <ul>
	 * <li>VERB</li>
	 * <li>ADJECTIVE</li>
	 * <li>ADVERB</li>
	 * <li>NOUN</li>
	 * </ul>
	 *
	 * @param pos The Part of Speech of Type {@link POS}
	 * @return whether a Part of Speech is relevant (true) or not (false)
	 */
	public static boolean isRelevant(POS pos) {
		return pos.equals(POS.ADJECTIVE) || pos.equals(POS.ADVERB)
				|| pos.equals(POS.NOUN) || pos.equals(POS.VERB);
	}
	
	
	//=================================//
	//  SENTIMENT-RELATED OPERATIONS   //
	//=================================//
	
	/**
	 * Checks whether a sentiment class is positive or not
	 * @param sentiment
	 * @return
	 */
	public static boolean isPositiveSentiment(String sentiment) {
		if (Constants.positiveClasses.contains(sentiment.toUpperCase())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks whether a sentiment class is negative or not
	 * @param sentiment
	 * @return
	 */
	public static boolean isNegativeSentiment(String sentiment) {
		if (Constants.negativeClasses.contains(sentiment.toUpperCase())) {
			return true;
		}
		return false;
	}
	
	
	
	
	public static ArrayList<String> getTopWords(HashMap<String, Integer> hashmap, int limit) {
		
		ArrayList<String> result = new ArrayList<>();
		
		if (hashmap.size()<limit) {
			for (String key : hashmap.keySet()) {
				if (hashmap.get(key) > 0) {
					result.add(key);
				}
			}
			return result;
		} else {
			List<Integer> list = new ArrayList<Integer>(hashmap.values());
			Collections.sort(list);
			
			int lowestValueAccepted = list.get(limit);
			
			for (String word : hashmap.keySet()) {
				if (hashmap.get(word) >= Math.max(1, lowestValueAccepted)) {
					result.add(word);
					if (result.size()==limit) {
						break;
					}
				}
			}
			return result;
		}
	}
	
}

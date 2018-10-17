package backend.spellingcorrectors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.start.Loader;


public class LevenshteinCorrector {
	
	/**
	 * Correct misspelled words [This method is the one used and it gives high importance to vowels]
	 *
	 * @param wordToCheck
	 * @return
	 */
	public static String correct(String wordToCheck) {
		// TODO Fix this method to make use of the scores as in the Norvig corrector
		String returnedWord = wordToCheck;

		ArrayList<String> candidateList = proposeCandidateWords(wordToCheck);
		int[] resemblance = new int[candidateList.size()];
		int i = 0;

		for (String word : candidateList) {

			List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u', 'y');
			char[] misspelledWordLetters = wordToCheck.toCharArray();
			char[] candidateWordLetters = word.toCharArray();

			int inf = misspelledWordLetters.length;
			if (candidateWordLetters.length < inf) inf = candidateWordLetters.length;

			for (int j = 0; j < inf; j++) {
				if (misspelledWordLetters[j] == candidateWordLetters[j]) {
					if (vowels.contains(misspelledWordLetters[j])) resemblance[i] += 2;
					else resemblance[i] += 5;
				} else {
					if (j != 0 && misspelledWordLetters[j] == candidateWordLetters[j - 1]) {
						if (vowels.contains(misspelledWordLetters[j])) resemblance[i] += 1;
						else resemblance[i] += 4;
					}
					if ((j != inf - 1 || misspelledWordLetters.length < candidateWordLetters.length) && misspelledWordLetters[j] == candidateWordLetters[j + 1]) {
						if (vowels.contains(misspelledWordLetters[j])) resemblance[i] += 1;
						else resemblance[i] += 3;
					}
				}

			}
			i++;

		}

		returnedWord = candidateList.get(0);
		int higherResemblance = resemblance[0];

		for (i = 0; i < candidateList.size(); i++) {
			if (resemblance[i] > higherResemblance) {
				returnedWord = candidateList.get(i);
				higherResemblance = resemblance[i];
			}
		}


		return returnedWord;
	}

	
	/**
	 * Calculate the Levenshtein distance between two words
	 * @param wordToCheck
	 * @param wordToCompare
	 * @return
	 */
	private static int measureLevenshteinDistance(String wordToCheck, String wordToCompare) {
		wordToCheck = wordToCheck.toLowerCase();
		wordToCompare = wordToCompare.toLowerCase();
		int[] costs = new int[wordToCompare.length() + 1];
		for (int j = 0; j < costs.length; j++)
			costs[j] = j;
		for (int i = 1; i <= wordToCheck.length(); i++) {
			costs[0] = i;
			int nw = i - 1;
			for (int j = 1; j <= wordToCompare.length(); j++) {
				int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), wordToCheck.charAt(i - 1) == wordToCompare.charAt(j - 1) ? nw : nw + 1);
				nw = costs[j];
				costs[j] = cj;
			}
		}
		return costs[wordToCompare.length()];
	}

	/**
	 * Return a list of the words that have the least Levenshtein distance
	 * @param wordToCheck
	 * @return
	 */
	private static ArrayList<String> proposeCandidateWords(String wordToCheck) {

		ArrayList<String> candidateList = new ArrayList<String>();
		int minimumDistance = 1000;
		for (String key : Loader.getEnglishWords().keySet()) {
			if (measureLevenshteinDistance(wordToCheck, key) < minimumDistance)
				minimumDistance = measureLevenshteinDistance(wordToCheck, key);
		}

		for (String key : Loader.getEnglishWords().keySet()) {
			if (measureLevenshteinDistance(wordToCheck, key) == minimumDistance) candidateList.add(key);
			;
		}

		System.out.print(candidateList);

		return candidateList;
	}



}

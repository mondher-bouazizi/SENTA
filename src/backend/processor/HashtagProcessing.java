package backend.processor;

import java.util.ArrayList;
import java.util.Arrays;

import main.start.Loader;


public class HashtagProcessing {

	public static final int HASHTAG_MAX = 160;

	private int[] temp;
	private int[] scores;
	private int[] numWords;

	
	//=================================//
	//           CONSTRUCTOR           //
	//=================================//
	
	/**
	 * Default Constructor
	 */
	public HashtagProcessing() {
		temp = new int[HASHTAG_MAX];
		scores = new int[HASHTAG_MAX];
		numWords = new int[HASHTAG_MAX];
	}
	
	
	//=================================//
	//         PUBLIC METHODS          //
	//=================================//
	
	/**
	 * Return whether a Hashtag can be decomposed into words or not
	 * @param hashtagToCheck
	 * @return
	 */
	public boolean isHashtagCorrect(String hashtagToCheck) {
		hashtagToCheck = hashtagToCheck.toLowerCase();
		/*if(hashtagToCheck.startsWith("#"))*/
		hashtagToCheck = this.removeHashtagSymbol(hashtagToCheck);
		Arrays.fill(temp, 0, hashtagToCheck.length() + 1, -1);
		Arrays.fill(scores, 0, hashtagToCheck.length() + 1, -1);
		Arrays.fill(numWords, 0, hashtagToCheck.length() + 1, -1);

		temp[hashtagToCheck.length()] = 0;
		scores[hashtagToCheck.length()] = 0;
		numWords[hashtagToCheck.length()] = 0;
		for (int i = hashtagToCheck.length() - 1; i >= 0; --i) {
			for (int j = hashtagToCheck.length() - 1; j >= i; --j) {
				String curSubStr = hashtagToCheck.substring(i, j + 1);
				if (temp[j + 1] != -1) {
					int count = getCount(curSubStr);
					int curScore = (count + scores[j + 1]) / (numWords[j + 1] + 1);
					if (count > 2 && scores[i] < curScore) {
						temp[i] = j;
						scores[i] = curScore;
						numWords[i] = numWords[j + 1] + 1;
					}
				}
			}
		}

		int test = temp[0];

		return test != -1;
	}
	
	/**
	 * Print the list of words composing the Hashtag
	 * @param hashtagToCheck
	 */
	public void printWords(String hashtagToCheck) {

		hashtagToCheck = hashtagToCheck.toLowerCase();
		/*if(hashtagToCheck.startsWith("#"))*/
		hashtagToCheck = this.removeHashtagSymbol(hashtagToCheck);

		if (isHashtagCorrect(hashtagToCheck)) {

			int currentPos = 0;
			System.out.println(hashtagToCheck);
			while (currentPos < hashtagToCheck.length() && temp[currentPos] != -1) {
				String str = hashtagToCheck.substring(currentPos, temp[currentPos] + 1);
				System.out.print("\t" + str);
				currentPos = temp[currentPos] + 1;
			}
			System.out.println();
		}
	}

	/**
	 * Return the list of words composing the Hashtag
	 * @param hashtagToCheck
	 * @return
	 */
	public ArrayList<String> returnHashtagWords(String hashtagToCheck) {
		ArrayList<String> listOfWords = new ArrayList<String>();

		hashtagToCheck = hashtagToCheck.toLowerCase();
		hashtagToCheck = this.removeHashtagSymbol(hashtagToCheck);

		if (isHashtagCorrect(hashtagToCheck)) {
			if (Loader.getEnglishWords().keySet().contains(hashtagToCheck))
				listOfWords.add(hashtagToCheck);
			else {
				int currentPos = 0;
				while (currentPos < hashtagToCheck.length() && temp[currentPos] != -1) {
					String str = hashtagToCheck.substring(currentPos, temp[currentPos] + 1);
					listOfWords.add(str);
					currentPos = temp[currentPos] + 1;
				}
			}
		}
		return listOfWords;
	}

	/**
	 * Converts a hashtag into a sequence of words (1 String)
	 * @param hashtagToCheck
	 * @return
	 */
	public String convertHashtagToString(String hashtagToCheck) {

		hashtagToCheck = hashtagToCheck.toLowerCase();
		/* if(hashtagToCheck.startsWith("#")) */
		hashtagToCheck = this.removeHashtagSymbol(hashtagToCheck);

		String outputString = "";

		if (hashtagToCheck.length() < 1)
			return outputString;
		else if (isHashtagCorrect(hashtagToCheck)) {
			ArrayList<String> listOfWords = new ArrayList<String>();
			if (Loader.getEnglishWords().keySet().contains(hashtagToCheck))
				listOfWords.add(hashtagToCheck);
			else {
				int currentPos = 0;

				while (currentPos < hashtagToCheck.length()
						&& temp[currentPos] != -1) {
					String str = hashtagToCheck.substring(currentPos, temp[currentPos] + 1);
					listOfWords.add(str);
					currentPos = temp[currentPos] + 1;
				}
			}

			for (String s : listOfWords) {
				outputString += s + " ";
			}
			outputString = outputString.substring(0, outputString.length() - 1);

		}
		return outputString;
	}

	/**
	 * Determine the polarity of a Hashtag
	 * @param hashtagToCheck
	 * @return {@value "positive"}, {@value "negative"} or {@value "neutral"}
	 */
	public String determinePolartiyOfHashtag(String hashtagToCheck) {
		hashtagToCheck = hashtagToCheck.toLowerCase();
		String polarity = "neutral";
		hashtagToCheck = this.removeHashtagSymbol(hashtagToCheck);

		if (isHashtagCorrect(hashtagToCheck)) {

			ArrayList<String> wordList = this.returnHashtagWords(hashtagToCheck);

			if (wordList.size() != 0) {
				if (wordList.get(0).toLowerCase().equals("no") || wordList.get(0).toLowerCase().equals("not")
						|| wordList.get(0).toLowerCase().equals("never") || wordList.get(0).toLowerCase().equals("non")) {
					polarity = "negative";
				} else {

					boolean[] prefixes = new boolean[wordList.size()];

					for (int i = 1; i < wordList.size(); i++) {
						if (wordList.get(i).toLowerCase().equals("no") || wordList.get(i).toLowerCase().equals("not") || wordList.get(i).toLowerCase().equals("never") || wordList.get(i).toLowerCase().equals("never")) {
							for (int j = i + 1; j < wordList.size(); j++) {
								prefixes[j] = true;
							}
						}
					}

					int positiveCount = 0;
					int negativeCount = 0;
					for (int i = 0; i < wordList.size(); i++) {
						if (WordProcessing.isPositive(wordList.get(i), prefixes[i])) {
							positiveCount++;
						}
						if (WordProcessing.isNegative(wordList.get(i), prefixes[i])) {
							negativeCount++;
						}
					}

					if (positiveCount > negativeCount) polarity = "positive";
					if (negativeCount > positiveCount) polarity = "negative";

				}
			}
		}
		return polarity;
	}
	
	
	//=================================//
	//         PRIVATE METHODS         //
	//=================================//
	
	/**
	 * Remove Hashtag symbol, and all the charcters other than letters
	 * @param wordToCheck
	 * @return
	 */
	private String removeHashtagSymbol(String wordToCheck) {
		return wordToCheck.toLowerCase().replaceAll("[^a-z]", "").trim();
	}
	
	/**
	 * Get the probability of a word
	 * @param word
	 * @return
	 */
	private int getCount(String word) {
		int count = 0;
		if (Loader.getWordsProbablities().containsKey(word.toLowerCase())) {
			// I Gave "a" (and other 1-letter words) a relatively low score to avoid cutting words
			if (Loader.getStopCache().containsKey(word.toLowerCase())) return 10;
			else if (word.equalsIgnoreCase("a") || word.equalsIgnoreCase("s") || word.equalsIgnoreCase("o") || word.equalsIgnoreCase("d") || word.equalsIgnoreCase("m"))
				return 5;
			else count = Loader.getWordsProbablities().get(word.toLowerCase());
		}
		return count;
	}




}

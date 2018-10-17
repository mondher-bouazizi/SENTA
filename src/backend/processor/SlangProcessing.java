package backend.processor;

import java.util.HashMap;

public class SlangProcessing {

	protected HashMap<String, Integer> slangsPolarity;
	protected HashMap<String, String> slangsMeaning;


	// Constructors

	public SlangProcessing(HashMap<String, Integer> slangsPolarity, HashMap<String, String> slangsMeaning) {
		super();
		this.slangsPolarity = slangsPolarity;
		this.slangsMeaning = slangsMeaning;
	}

	public SlangProcessing() {
		super();
	}


	// Setters

	public void setSlangsPolarity(HashMap<String, Integer> slangsPolarity) {
		this.slangsPolarity = slangsPolarity;
	}

	public void setSlangsMeaning(HashMap<String, String> slangsMeaning) {
		this.slangsMeaning = slangsMeaning;
	}


	// Search a slang word and return its emotional polarity
	public int wordSentimentSearch(String wordToCheck) {

		int returnedSentiment = 0;

		if (wordToCheck.length() > 0) {
			if (slangsMeaning.get(wordToCheck.toLowerCase()) != null)
				returnedSentiment = slangsPolarity.get(wordToCheck.toLowerCase());
		}

		return returnedSentiment;
	}

	// Look for a slang word and replace it with the expression it means
	public String replaceSlangWord(String wordToCheck) {

		String correction = wordToCheck;

		if (wordToCheck.length() > 0) {
			if (slangsMeaning.get(wordToCheck.toLowerCase()) != null)
				correction = slangsMeaning.get(wordToCheck.toLowerCase());
		}
		return correction;
	}

}

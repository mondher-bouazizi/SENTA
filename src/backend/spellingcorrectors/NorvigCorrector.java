package backend.spellingcorrectors;

import java.util.*;

import main.start.Loader;

public class NorvigCorrector {
	
	/**
	 * Corrector
	 * @param wordToCheck
	 * @return
	 */
	public static String correct(String wordToCheck) {

		if (Loader.getWordsProbablities().containsKey(wordToCheck)) return wordToCheck;
		ArrayList<String> list = edits(wordToCheck);
		HashMap<Integer, String> candidates = new HashMap<Integer, String>();
		for (String s : list) if (Loader.getWordsProbablities().containsKey(s)) candidates.put(Loader.getWordsProbablities().get(s), s);
		if (candidates.size() > 0) return candidates.get(Collections.max(candidates.keySet()));
		for (String s : list) for (String w : edits(s)) if (Loader.getWordsProbablities().containsKey(w)) candidates.put(Loader.getWordsProbablities().get(w), w);
		return candidates.size() > 0 ? candidates.get(Collections.max(candidates.keySet())) : wordToCheck;
	}
	
	
	/**
	 * Determine the candidate words
	 * @param word
	 * @return
	 */
	private static ArrayList<String> edits(String word) {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < word.length(); ++i) result.add(word.substring(0, i) + word.substring(i + 1));
		for (int i = 0; i < word.length() - 1; ++i)
			result.add(word.substring(0, i) + word.substring(i + 1, i + 2) + word.substring(i, i + 1) + word.substring(i + 2));
		for (int i = 0; i < word.length(); ++i)
			for (char c = 'a'; c <= 'z'; ++c)
				result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i + 1));
		for (int i = 0; i <= word.length(); ++i)
			for (char c = 'a'; c <= 'z'; ++c) result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));
		return result;
	}
	
	
}

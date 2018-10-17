package backend.processor;

import java.lang.reflect.Array;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import commons.constants.Commons;
import commons.constants.Constants;
import main.items.Features;
import main.start.Loader;


public class TextProcessing {

	//======================================//
	//           NLP BASIC TASKS            //
	//======================================//
	
	/**
	 * Split a paragraph into sentences
	 * @param paragraph : the paragraph to split into sentences
	 * @return an Array of sentences
	 */
	public static String[] splitParagraph(String paragraph) {
		return Loader.getSDetector().sentDetect(paragraph);
	}

	/**
	 * Tokenize a sentence into its basic components [words, puncutation marks, numbers, etc.]
	 * @param sentence : the sentence to split
	 * @return an Array of basic components [words, puncutation marks, numbers, etc.]
	 */
	public static String[] tokenize(String sentence) {
		return Loader.getTokenizer().tokenize(sentence);
	}
	
	/**
	 * PoS-tag s non tokenized sentence
	 * TODO check if this is really useful (I think in OpenNLP the method itself is deprecated)
	 * @param sentence
	 * @return an Array of PoS-tags
	 */
	public static String[] posTag(String sentence) {
		String[] tokens = Loader.getTokenizer().tokenize(sentence);
		return Loader.getTagger().tag(tokens);
	}

	/**
	 * PoS-tag an already tokenized sentence
	 * @param sentence
	 * @return an Array of PoS-tags
	 */
	public static String[] posTag(String[] sentence) {
		return Loader.getTagger().tag(sentence);
	}

	/**
	 * Generate the simplified PoS-Tags vector out of the original PoS-Tags
	 * @param posTags : a list of Part of Speech tags
	 * @return an {@link ArrayList} of simplified PoS-tags
	 */
	public static String[] getSimplifiedPosTags(String[] posTags) {
		String[] simplifiedPosTags = new String[posTags.length];
		for (int i = 0; i < posTags.length; i++) {
			simplifiedPosTags[i] = WordProcessing.getSiplifiedPosTag(posTags[i]);
		}
		return simplifiedPosTags;
	}

	/**
	 * Lemmatize the words of a sentence.
	 * @param sentence : the sentence tokenized
	 * @param posTags  : PoS-Tags of the tokens of the sentence
	 * @return an ArrayList of the lemmas of the sentence.
	 */
	public static String[] lemmatize(String[] sentence, String[] posTags) {
		if (sentence.length != posTags.length) {
			Commons.print("The two table don't have the same length");
			return sentence;
		}
		String[] lemmas = new String[sentence.length];

		for (int i = 0; i < sentence.length; i++) {
			lemmas[i] = WordProcessing.lemmatize(sentence[i], posTags[i]);
		}
		return lemmas;
	}

	
	//======================================//
	//          NLP ADVANCED TASKS          //
	//======================================//
	
	/**
	 * Create the prefix "NOT" array of the sentence
	 * [The prefix "NOT" is given to all the words after a negation word 'til the next punctuation / contrast word]
	 * [NOTE: This step has to be done after the replacement of all the words containing apostrophe with their expression]
	 *
	 * @param sentence : the sentence tokenized
	 * @return an {@link Array} of boolean which show whether the word is under the coverage of a negation word
	 */
	public static boolean[] generateNegationIndex(String[] sentence) {

		int size = sentence.length;
		boolean[] indexes = new boolean[size];

		if (sentence.length != 0) {
			int startPoint = 0;
			for (int i = 0; i < sentence.length; i++) {
				if (WordProcessing.isPunctuation(sentence[i]) || sentence[i].toLowerCase().equals("but") || sentence[i].toLowerCase().equals("however") || sentence[i].toLowerCase().equals("believe")) {
					for (int j = startPoint; j < i; j++) {
						if (WordProcessing.isNegationWord(sentence[j])) {
							// TODO Check which makes more sense
							// for (int k = j; k < i; k++)
							//    or
							// for (int k = j; k < Math.min(j+4, i); k++)
							for (int k = j; k < i; k++) {
								//if (Main.wordPro.isNegationWord(sentence[k]) == false) {
								indexes[k] = true;
								//}
							}
							break;
						}
					}
					startPoint = i + 1;
				} else if (i == sentence.length - 1) {
					for (int j = startPoint; j < i; j++) {
						if (WordProcessing.isNegationWord(sentence[j])) {
							// TODO Check which makes more sense
							// for (int k = j; k < i; k++)
							//    or
							// for (int k = j; k < Math.min(j+4, i); k++)
							for (int k = j; k <= i; k++) {
								//if (Main.wordPro.isNegationWord(sentence[k]) == false) {
								indexes[k] = true;
								//}
							}
							break;
						}
					}
				}
			}
		}

		return indexes;
	}

	/**
	 * Create the boosting coefficient array of the sentence
	 * [The boosting coefficients are given to all the words after a negation word until the next punctuation / contrast word]
	 * [NOTE: This step has to be done after the replacement of all the words containing apostrophe with their expression]
	 * @param sentence : the sentence tokenized
	 * @return an {@link Array} of Strings which take the values {"NOT", "." and ""}
	 */
	public static double[] generateBoostingCoefficients(String[] sentence) {
		int size = sentence.length;
		double[] coefficients = new double[size];

		for (int i = 0; i < size; i++) {
			coefficients[i] = 1;
		}
		if (sentence.length != 0) {

			int startPoint = 1;

			for (int i = 0; i < sentence.length; i++) {
				if (WordProcessing.isPunctuation(sentence[i])
						|| sentence[i].toLowerCase().equals("but") || sentence[i].toLowerCase().equals("however") ||
						sentence[i].toLowerCase().equals("believe") || (i == sentence.length - 1)) {
					
					for (int j = startPoint; j < i; j++) {
						if (WordProcessing.isBooster(sentence[j])) {
							for (int k = j; k < i; k++) {
								int s = Loader.getBoosters().get(sentence[j].toLowerCase());
								if (s == -1) {
									coefficients[k] = 0.75;
								}
								if (s == 1) {
									coefficients[k] = 1.5;
								}
								if (s == 2) {
									coefficients[k] = 2;
								}
							}
						}
					}
					startPoint = i + 1;
				}
			}
		}

		return coefficients;
	}

	/**
	 * Attribute polarity orientation to the words of a sentence
	 *
	 * @param tokens   : the words of the sentence
	 * @param negationIndexes : the indexes which can be generated using the method {@link "generateNegationIndex"}
	 * @return an Array of the polarities of the words of the sentence [negative, neutral, positive]
	 */
	public static String[] getWordPolarity(String[] tokens, boolean[] negationIndexes) {

		int length = tokens.length;

		String[] wordsPolarity = new String[length];

		for (int i = 0; i < length; i++) {
			if (WordProcessing.isPositive(tokens[i].toLowerCase(), negationIndexes[i])) {
				wordsPolarity[i] = "positive";
			} else if (WordProcessing.isNegative(tokens[i].toLowerCase(), negationIndexes[i])) {
				wordsPolarity[i] = "negative";
			} else {
				wordsPolarity[i] = "neutral";
			}
		}

		return wordsPolarity;
	}

	/**
	 * Attribute sentiment scores to the words of a sentence
	 * @param tokens   : the words of the sentence
	 * @param negationIndexes : the indexes which can be generated using the method {@link "generateNegationIndex"}
	 * @param boosters : the vector of boost coefficients
	 * @return an Array of the polarities of the words of the sentence [-1, 0, 1]
	 */
	public static double[] getWordsSentimentScores(String[] tokens, boolean[] negationIndexes, double[] boosters) {

		int length = tokens.length;

		double[] wordsPolarity = new double[length];
		
		for (int i = 0; i < length; i++) {
			wordsPolarity[i] = WordProcessing.getSentimentScore(tokens[i], negationIndexes[i], boosters[i]);
		}
		
		return wordsPolarity;
	}
	
	/**
	 * Convert a text into lower case
	 * @param sentence : the sentence to convert
	 * @return the sentence converted to lower case
	 */
	public static String sentenceToLowerCase(String sentence) {
		return sentence.toLowerCase();
	}

	/**
	 * Reduce every punctuation mark that exists more than once in row to one mark (e.g. "!!!" -> "!")
	 *
	 * @param sentence : the sentence to be modified
	 * @return the sentence after simplification of the punctuation
	 */
	public static String simplifyPunctuation(String sentence) {
		sentence = sentence.replaceAll("�", ".");

		sentence = sentence.replaceAll("\\{", "(");
		sentence = sentence.replaceAll("\\}", ")");
		sentence = sentence.replaceAll("\\[", "(");
		sentence = sentence.replaceAll("\\[", ")");
		sentence = sentence.replaceAll("\\\\\\\\", "/");
		sentence = sentence.replaceAll("/", "/");
		sentence = sentence.replaceAll("^", "");
		sentence = sentence.replaceAll("~", "");
		sentence = sentence.replaceAll("|", "");
		sentence = sentence.replaceAll("<", "");
		sentence = sentence.replaceAll(">", "");

		while (sentence.contains("..")) sentence = sentence.replace("..", ".");
		while (sentence.contains(",,")) sentence = sentence.replace(",,", ",");
		while (sentence.contains("??")) sentence = sentence.replace("??", "?");
		while (sentence.contains("!!")) sentence = sentence.replace("!!", "!");
		while (sentence.contains("//")) sentence = sentence.replace("//", "/");
		while (sentence.contains(";;")) sentence = sentence.replace(";;", ";");
		while (sentence.contains("::")) sentence = sentence.replace("::", ":");
		while (sentence.contains("((")) sentence = sentence.replace("((", "(");
		while (sentence.contains("))")) sentence = sentence.replace("))", ")");
		while (sentence.contains("{{")) sentence = sentence.replace("{{", "{");
		while (sentence.contains("}}")) sentence = sentence.replace("}}", "}");
		while (sentence.contains("\"\"")) sentence = sentence.replace("\"\"", "\"");
		while (sentence.contains("$$")) sentence = sentence.replace("$$", "$");
		while (sentence.contains("  ")) sentence = sentence.replace("  ", " ");

		return sentence;
	}
	
	
	//======================================//
	//     COUNT OF WORDS, SLANGS, ETC.     //
	//======================================//
	
	/**
	 * Collects and returns all the words of a sentence
	 *
	 * @param sentence : the sentence to get the words from
	 * @return the full list of words of the sentence (the other tokens are removed
	 */
	public static ArrayList<String> getAllWords(String[] sentence) {
		//TODO Fix this and use lemmas + PoS-Tags to decide on which tokens are words and which are not
		ArrayList<String> words = new ArrayList<String>();

		for (String word : sentence) {
			if (word.toLowerCase().replaceAll("[^a-z]", "").trim().length() != 0) {
				words.add(word);
			}
		}

		return words;
	}

	/**
	 * Collects and returns the important words in a paragraph
	 *
	 * @param word      : the word
	 * @param paragraph : the paragraph to check
	 * @param posTags   : the PoS-tags of the words of the paragraph
	 * @return the list of the important words
	 */
	public static String[] getImportantWords(String[] paragraph, String[] posTags) {

		ArrayList<String> importantWords = new ArrayList<String>();

		for (int i = 0; i < paragraph.length; i++) {
			if (Loader.getRelvCache().containsKey(posTags[i])) {
				if (!Loader.getStopCache().containsKey(paragraph[i].toLowerCase())) {
					importantWords.add(paragraph[i].toLowerCase());
				}
			}

		}
		return importantWords.toArray(new String[importantWords.size()]);
	}

	/**
	 * Collects and returns the important words in a paragraph
	 *
	 * @param word      : the word
	 * @param paragraph : the paragraph to check
	 * @param posTags   : the PoS-tags of the words of the paragraph
	 * @return the list of the important words
	 */
	public static ArrayList<String> getImportantWordsAsList(String[] paragraph, String[] posTags) {

		ArrayList<String> importantWords = new ArrayList<String>();

		for (int i = 0; i < paragraph.length; i++) {
			if (Loader.getRelvCache().containsKey(posTags[i])) {
				if (!Loader.getStopCache().containsKey(paragraph[i].toLowerCase())) {
					importantWords.add(paragraph[i].toLowerCase());
				}
			}

		}
		return importantWords;
	}
	
	/**
	 * Count the number of words in a text
	 *
	 * @param sentence : the sentence to count the number of words
	 * @return the number of words of the sentence
	 */
	public static int countNumberOfWords(String[] sentence) {
		ArrayList<String> words = getAllWords(sentence);
		return words.size();
	}
	
	/**
	 * Count the number of words in a text
	 * @param aAllWords
	 * @return
	 */
	public static int countNumberOfWords(ArrayList<String> aAllWords) {
		return aAllWords.size();
	}

	/**
	 * Count the number of words per sentence in a paragraph
	 *
	 * @param paragraph : the paragraph to check
	 * @return the ration defined by the number of words of the paragraph divided by the number of sentences of the paragraph
	 */
	public static double countWordsPerSentence(String paragraph) {

		int numberOfSentences = 0;
		int numberOfWords = 0;

		String[] words = tokenize(paragraph);

		for (int i = 0; i < words.length; i++) {

			if (!WordProcessing.isPunctuation(words[i])) {
				numberOfWords++;
			}

		}

		numberOfSentences = splitParagraph(paragraph).length;

		if (numberOfSentences == 0) {
			numberOfSentences = 1;
		}

		double wordsPerSentence = (double) numberOfWords / (double) numberOfSentences;

		return wordsPerSentence;


	}

	/**
	 * Count the number of words with a repeated character (the character is a  letter,and the repetition should
	 * be consecutive + equal to or more than 3 times)
	 *
	 * @param sentence
	 * @return
	 */
	public static int countRepeatition(String sentence) {

		int count = 0;

		sentence = sentence.toLowerCase().replaceAll("_", " ").replaceAll("-", " ").replaceAll("[^a-zA-Z ]", "").trim();
		String[] words = sentence.split("\\s");
		for (String word : words) {
			if (WordProcessing.containsRepitition(word)) count++;
		}

		return count;
	}

	/**
	 * Check whether a sentence contains a laughing expression
	 *
	 * @param sentence : the sentence which we check if it contains the laughter
	 * @return {@value "true"} if the sentence contains a laughter, {@value "false"} otherwise
	 */
	public static boolean containsLaughter(String[] sentence) {
		boolean laughter = false;

		for (String word : sentence) {
			if (WordProcessing.isLaughter(word)) {
				laughter = true;
				break;
			}
		}

		return laughter;

	}

	/**
	 * Check whether a text contains an expression
	 *
	 * @param textToCheck : the text in which we look for the expression
	 * @param expression  : the expression to look for
	 * @return {@value "true"} if the expression exists in the text, {@value "false"} otherwise
	 */
	public static boolean containsExpression(String textToCheck, String expression) {

		boolean found = false;

		if (textToCheck.toLowerCase().contains(expression.toLowerCase())) found = true;

		return found;
	}
	
	/**
	 * Count the number of uncommon English words used in a sentence
	 *
	 * @param sentence : the sentence to check
	 * @return the number of uncommon English words used in the sentence
	 */
	public static int countUncommonWords(String[] sentence) {
		int uncommonWords = 0;
		for (int i = 0; i < sentence.length; i++) {
			if (WordProcessing.isCommon(sentence[i]) == false) {
				uncommonWords++;
			}
		}
		return uncommonWords;
	}
	
	/**
	 * Count the number of stop words in a sentence
	 * @param sentence
	 * @return
	 */
	public static int countStopWords(String[] sentence) {
		int count = 0;
		for(String word : sentence) {
			if(Loader.getStopCache().containsKey(word.toLowerCase())) {
				count ++;
			}
		}
		return count;
	}
	
	/**
	 * Count the number of Hashtags in a sentence
	 * @param sentence : the sentence to check
	 * @return the number of hashtags in the sentence
	 */
	public static int countHashtags(String[] sentence) {
		// TODO fix this when the class {@link HashtagProcessing} is modified
		int count = 0;

		for (int i = 0; i < sentence.length; i++) {
			
			if (i > 0) {
				if (sentence[i - 1].equals("#")) {
						count++;
				}
			} else {
				if (WordProcessing.isHashtag(sentence[i])) {
						count++;
				}
			}
		}
		return count;
	}
	
	/**
	 * Count the total number of Emoticons in a sentence
	 *
	 * @param sentence : the sentence to check
	 * @return the total number of Emoticons in the sentence
	 */
	public static int countEmoticons(String sentence) {
		// TODO VERY IMPORTANT TO FIX: We are currently checking if the big string(SENTENCE) contains a small string (EMOTICON)
		// this is due to the problem of the tokenizer as mentioned above for hashtags
		int numberOfEmoticons = 0;

		for (String emoticon : Loader.getEmoticonPolarities().keySet()) {
			if (sentence.contains(emoticon)) {
				numberOfEmoticons+= StringUtils.countMatches(sentence, emoticon);
			}
		}
		return numberOfEmoticons;
	}
	
	/**
	 * Count the total number of slang words in a sentence
	 *
	 * @param sentence : the sentence to check
	 * @return the total number of slang words in the sentence
	 */
	public static int countSlangs(String[] sentence) {

		int count = 0;

		for (int i = 0; i < sentence.length; i++) {
			if (WordProcessing.isSlang(sentence[i]) == true)
				count++;
		}

		return count;
	}
	
	/**
	 * Count the number of apostrophes marks in a sentence
	 * [The number of quote marks is divided by 2 at the end]
	 * @param sentence : the sentence to check
	 * @return the number of quotes in the sentence
	 */
	public static int countCharacters(String sentence) {
		return sentence.toCharArray().length;
	}
	
	/**
	 * Count the number of sentences in a paragraph
	 * @param paragraph
	 * @return
	 */
	public static int countSentences(String paragraph) {
		return splitParagraph(paragraph).length;
	}
	
	/**
	 * Count the number of nouns in a sentence
	 * @param tags
	 * @return
	 */
	public static int countNouns(String[] tags) {
		int count = 0;
		for (String tag : tags) {
			if (tag.startsWith("NN")) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Count the number of verbs in a sentence
	 * @param tags
	 * @return
	 */
	public static int countVerbs(String[] tags) {
		int count = 0;
		for (String tag : tags) {
			if (tag.startsWith("VB")) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Count the number of adjectives in a sentence
	 * @param tags
	 * @return
	 */
	public static int countAdjectives(String[] tags) {
		int count = 0;
		for (String tag : tags) {
			if (tag.startsWith("JJ")) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Count the number of adverbs in a sentence
	 * @param tags
	 * @return
	 */
	public static int countAdverbs(String[] tags) {
		int count = 0;
		for (String tag : tags) {
			if (tag.startsWith("RB")) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Count the ratio of nouns in a sentence
	 * @param tags
	 * @return
	 */
	public static double countNounsRatio(String[] tags) {
		if (tags.length>0) {
			return (double) countNouns(tags)/ (double) tags.length;
		} else {
			return 0;
		}
	}
	
	/**
	 * Count the ratio of verbs in a sentence
	 * @param tags
	 * @return
	 */
	public static double countVerbsRatio(String[] tags) {
		if (tags.length>0) {
			return (double) countVerbs(tags)/ (double) tags.length;
		} else {
			return 0;
		}
	}
	
	/**
	 * Count the ratio of adjectives in a sentence
	 * @param tags
	 * @return
	 */
	public static double countAdjectivesRatio(String[] tags) {
		if (tags.length>0) {
			return (double) countAdjectives(tags)/ (double) tags.length;
		} else {
			return 0;
		}
	}
	
	/**
	 * Count the ratio of adverbs in a sentence
	 * @param tags
	 * @return
	 */
	public static double countAdverbsRatio(String[] tags) {
		if (tags.length>0) {
			return (double) countAdverbs(tags)/ (double) tags.length;
		} else {
			return 0;
		}
	}
	
	/**
	 * Check the existence of opinion words or expressions
	 * @param text
	 * @param lemmas
	 * @return
	 */
	public static boolean useOpinionWords(String text, String[] lemmas) {
		for (String lemma : lemmas) {
			if (Constants.opinionWords.contains(lemma.toLowerCase())) {
				return true;
			}
		}
		for (String expression : Constants.opinionExpressions) {
			if (text.toLowerCase().contains(expression)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check the existence of uncertainty words or expressions
	 * @param text
	 * @param lemmas
	 * @return
	 */
	public static boolean useUncertaintyWords(String text, String[] lemmas) {
		for (String lemma : lemmas) {
			if (Constants.uncertaintyWords.contains(lemma.toLowerCase())) {
				return true;
			}
		}
		for (String expression : Constants.uncertaintyExpressions) {
			if (text.toLowerCase().contains(expression)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if any of the sentence of a text are in the active form
	 * @param tags
	 * @return
	 */
	public static boolean useActiveForm(String[] tags) {
		for (int i=0; i<tags.length-1; i++) {
			if (tags[i+1].equals("VB") || tags[i+1].equals("VBD") || tags[i+1].equals("VBP") || tags[i+1].equals("VBZ") ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if any of the sentence of a text are in the passive form
	 * @param tags
	 * @return
	 */
	public static boolean usePassiveForm(String[] tokens, String[] tags) {
		for (int i=0; i<tokens.length-1; i++) {
			if (tokens[i].equals("be")) {
				if (tags[i+1].equals("VBN") || tags[i+1].equals("VBD")) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	//======================================//
	//      COUNT OF PUNCTUATION MARKS      //
	//======================================//

	/**
	 * Count the number of exclamation marks in a sentence
	 * @param sentence : the sentence to check
	 * @return the number of exclamation marks in the sentence
	 */
	public static int countExclamationMarks(String sentence) {

		int count = 0;

		char[] charArray = sentence.toCharArray();

		for (char test : charArray) {
			if (test == '!') count++;
		}

		return count;
	}

	/**
	 * Count the number of question marks in a sentence
	 * @param sentence : the sentence to check
	 * @return the number of question marks in the sentence
	 */
	public static int countQuestionMarks(String sentence) {

		int count = 0;

		char[] charArray = sentence.toCharArray();

		for (char test : charArray) {
			if (test == '?') count++;
		}

		return count;
	}

	/**
	 * Count the number of dots in a sentence
	 * @param sentence : the sentence to check
	 * @return the number of dots in the sentence
	 */
	public static int countDots(String sentence) {

		int count = 0;

		char[] charArray = sentence.toCharArray();

		for (char test : charArray) {
			if (test == '.') count += 1;
		}

		return count;
	}
	
	/**
	 * Count the number of comma, in a sentence
	 * @param sentence : the sentence to check
	 * @return the number of dots in the sentence
	 */
	public static int countCommas(String sentence) {

		int count = 0;

		char[] charArray = sentence.toCharArray();

		for (char test : charArray) {
			if (test == ',') count += 1;
		}

		return count;
	}
	
	/**
	 * Count the number of semicolons, in a sentence
	 * @param sentence : the sentence to check
	 * @return the number of dots in the sentence
	 */
	public static int countSemiColons(String sentence) {

		int count = 0;

		char[] charArray = sentence.toCharArray();

		for (char test : charArray) {
			if (test == ';') count += 1;
		}

		return count;
	}

	/**
	 * Count the number of all capitalized words in a sentence
	 * @param sentence : the sentence to check
	 * @return the number of all capitalized words in the sentence
	 */
	public static int countCapitalizedWords(String[] sentence) {

		int count = 0;

		for (String token : sentence) {
			if (WordProcessing.isCapitalized(token)) {
				count++;
			}
		}

		return count;
	}

	/**
	 * Count the number of interjections in a sentence
	 * @param posTags : the sentence to check
	 * @return the number of exclamation marks in the sentence
	 */
	public static int countInterjections(String[] posTags) {
		int count = 0;
		for (String tag : posTags) {
			if (tag.equalsIgnoreCase("UH")) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Count the number of Quotes marks in a sentence
	 * [The number of quote marks is divided by 2 at the end]
	 * @param sentence : the sentence to check
	 * @return the number of quotes in the sentence
	 */
	public static int countQuotes(String sentence) {
		int count = 0;
		char[] charArray = sentence.toCharArray();
		for (char test : charArray) {
			if (test == '"') count++;
		}
		return count / 2;
	}

	/**
	 * Count the number of apostrophes marks in a sentence
	 * [The number of quote marks is divided by 2 at the end]
	 * @param sentence : the sentence to check
	 * @return the number of quotes in the sentence
	 */
	public static int countApostrophes(String sentence) {
		int count = 0;
		char[] charArray = sentence.toCharArray();
		for (char test : charArray) {
			if (test == '\'') count++;
		}
		return count;
	}
	
	/**
	 * Count the number of Laughters in a sentence
	 *
	 * @param sentence : the sentence to check
	 * @return the number of Laughters in the sentence
	 */
	public static int countLaughters(String[] sentence) {
		int count = 0;

		for (String token : sentence) {
			if (WordProcessing.isLaughter(token)) count++;
		}

		return count;
	}
	
	/**
	 * Count the number of pairs of parentheses in a sentence
	 * [The number of quote marks is divided by 2 at the end]
	 * @param sentence : the sentence to check
	 * @return the number of parentheses in the sentence
	 */
	public static int countParentheses(String sentence) {
		int count = 0;
		char[] charArray = sentence.toCharArray();
		for (char test : charArray) {
			if (test == '(' || test == ')') count++;
		}
		return count / 2;
	}
	
	/**
	 * Count the number of pairs of brackets in a sentence
	 * [The number of quote marks is divided by 2 at the end]
	 * @param sentence : the sentence to check
	 * @return the number of brackets in the sentence
	 */
	public static int countBrackets(String sentence) {
		int count = 0;
		char[] charArray = sentence.toCharArray();
		for (char test : charArray) {
			if (test == '[' || test == ']') count++;
		}
		return count / 2;
	}
	
	/**
	 * Count the number of pairs of braces in a sentence
	 * [The number of quote marks is divided by 2 at the end]
	 * @param sentence : the sentence to check
	 * @return the number of braces in the sentence
	 */
	public static int countBraces(String sentence) {
		int count = 0;
		char[] charArray = sentence.toCharArray();
		for (char test : charArray) {
			if (test == '{' || test == '}') count++;
		}
		return count / 2;
	}
	
	/**
	 * Count the number of symbols in a sentence. Only symbols in this list are counted:
	 * ["#", "$", "@", "%", "*", "+", "/", "&"]
	 * @param sentence
	 * @return
	 */
	public static int countSymbols(String sentence) {
		int count = 0;
		char[] charArray = sentence.toCharArray();
		for (char test : charArray) {
			if (Constants.symbols.contains(test)) count++;
		}
		return count;
	}
	
	/**
	 * Count the number of symbols in a sentence. This counts all the symbols according to OpenNLP tagger
	 * @param sentence
	 * @return
	 */
	public static int countSymbols(String[] tags) {
		int count = 0;
		
		for (String tag : tags) {
			if (tag.equals("SYM")) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * This checks if a the comparative form of an adjective/adverb is used in a sentence
	 * @param tags
	 * @return
	 */
	public static boolean useComparativeForm(String[] tags) {
		for (String tag : tags) {
			if (tag.equalsIgnoreCase("JJR") || tag.equalsIgnoreCase("RBR")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This checks if a the superlative form of an adjective/adverb is used in a sentence
	 * @param tags
	 * @return
	 */
	public static boolean useSuperlativeForm(String[] tags) {
		for (String tag : tags) {
			if (tag.equalsIgnoreCase("JJS") || tag.equalsIgnoreCase("RBS")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This checks the existence of proper nouns in the sentence
	 * @param tags
	 * @return
	 */
	public static boolean useProperNouns(String[] tags) {
		for (String tag : tags) {
			if (tag.equalsIgnoreCase("NNP") || tag.equalsIgnoreCase("NNPS")) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Count the number of particles
	 * @param sentence : the sentence to check
	 * @return the number of braces in the sentence
	 */
	public static int countParticles(String[] tags) {
		int count = 0;
		for (String tag : tags) {
			if (tag.equals("CC") || tag.equals("DT") || tag.equals("IN") || tag.equals("PDT") || tag.equals("RP") || tag.equals("TO")) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Count the number of pronouns
	 * @param sentence : the sentence to check
	 * @return the number of braces in the sentence
	 */
	public static int countPronouns(String[] tags) {
		int count = 0;
		for (String tag : tags) {
			if (tag.startsWith("PRP")) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Count the number of pronouns of group I
	 * [I, me, my, mine, you, your, yours, we, us, our, ours]
	 * @param sentence : the sentence to check
	 * @return the number of braces in the sentence
	 */
	public static int countPronounsGroupI(String[] tags, String[] tokens) {
		int count = 0;
		for (int i = 0; i<tags.length; i++) {
			if (tags[i].startsWith("PRP")) {
				if (Constants.pronounsGroupI.contains(tokens[i].toLowerCase())) {
					count++;
				}
			}
		}
		return count;
	}
	
	/**
	 * Count the number of pronouns of group II
	 * [he, him, his, she, her, hers, it, its]
	 * @param sentence : the sentence to check
	 * @return the number of braces in the sentence
	 */
	public static int countPronounsGroupII(String[] tags, String[] tokens) {
		int count = 0;
		for (int i = 0; i<tags.length; i++) {
			if (tags[i].startsWith("PRP")) {
				if (!Constants.pronounsGroupI.contains(tokens[i].toLowerCase())) {
					count++;
				}
			}
		}
		return count;
	}
	
	
	//======================================//
	//      SENTIMENT POLARITY-RELATED      //
	//======================================//

	/**
	 * Recuperate the list of positive words
	 * @param tokens   : the sentence tokenized
	 * @param negationIndexes : the negation index list (eg. ["NOT", "NOT", "", "", ""])
	 * @param posTags  : the PoS-tags of the tokenized sentence
	 * @return an {@link ArrayList} of positive words
	 */
	public static ArrayList<String> getPositiveWords(String[] tokens, boolean[] negationIndexes, String[] posTags) {

		ArrayList<String> positiveWords = new ArrayList<String>();

		for (int i = 0; i < tokens.length; i++) {
			if (WordProcessing.isPositive(tokens[i], negationIndexes[i])) {
				if (tokens[i].equalsIgnoreCase("like")) {
					if (posTags[i].equalsIgnoreCase("VB") || posTags[i].equalsIgnoreCase("VBP")) {
						positiveWords.add(tokens[i]);
					}
				} else {
					if (Loader.getNegativeCache().containsKey(tokens[i])) {
						positiveWords.add("NOT_" + tokens[i]);
					} else {
						positiveWords.add(tokens[i]);
					}
				}
			}
		}


		return positiveWords;
	}
	
	/**
	 * Recuperate the list of negative words
	 *
	 * @param tokens   : the sentence tokenized
	 * @param negationIndexes : the negation index list (eg. ["NOT", "NOT", "", "", ""])
	 * @param posTags  : the PoS-tags of the tokenized sentence
	 * @return an {@link ArrayList} of negative words
	 */
	public static ArrayList<String> getNegativeWords(String[] tokens, boolean[] negationIndexes, String[] posTags) {

		ArrayList<String> negativeWords = new ArrayList<String>();

		for (int i = 0; i < tokens.length; i++) {
			if (WordProcessing.isNegative(tokens[i], negationIndexes[i])) {
				if (tokens[i].equalsIgnoreCase("like")) {
					if (posTags[i].equalsIgnoreCase("VB") || posTags[i].equalsIgnoreCase("VBP")) {
						negativeWords.add("NOT_" + tokens[i]);
					}
				} else {
					if (Loader.getPositiveCache().containsKey(tokens[i])) {
						negativeWords.add("NOT_" + tokens[i]);
					} else {
						negativeWords.add(tokens[i]);
					}
				}
			}
		}


		return negativeWords;
	}
	
	/**
	 * Recuperate the list of unique positive words (a word repeated more than once should appear once)
	 * @param tokens   : the sentence tokenized
	 * @param negationIndexes : the negation index list (eg. ["NOT", "NOT", "", "", ""])
	 * @param posTags  : the PoS-tags of the tokenized sentence
	 * @return an {@link ArrayList} of the unique positive words
	 */
	public static ArrayList<String> getUniquePositiveWords(String[] tokens, boolean[] negationIndexes, String[] posTags) {

		ArrayList<String> positiveUniqueWords = new ArrayList<String>();

		for (String word : getPositiveWords(tokens, negationIndexes, posTags)) {
			if (!positiveUniqueWords.contains(word)) positiveUniqueWords.add(word);
		}
		return positiveUniqueWords;
	}
	
	/**
	 * Recuperate the list of unique negative words (a word repeated more than once should appear once)
	 *
	 * @param tokens   : the sentence tokenized
	 * @param negationIndexes : the negation index list (eg. ["NOT", "NOT", "", "", ""])
	 * @param posTags  : the PoS-tags of the tokenized sentence
	 * @return an {@link ArrayList} of the unique negative words
	 */
	public static ArrayList<String> getUniqueNegativeWords(String[] tokens, boolean[] negationIndexes, String[] posTags) {

		ArrayList<String> negativeUniqueWords = new ArrayList<String>();

		for (String word : getNegativeWords(tokens, negationIndexes, posTags)) {
			if (!negativeUniqueWords.contains(word)) negativeUniqueWords.add(word);
		}
		return negativeUniqueWords;
	}
	
	/**
	 * Recuperate the list of highly emotional positive words
	 *
	 * @param tokens   : the sentence tokenized
	 * @param negationIndexes : the negation index list (eg. ["NOT", "NOT", "", "", ""])
	 * @param posTags  : the PoS-tags of the tokenized sentence
	 * @return an {@link ArrayList} of the relevant positive words
	 */
	public static ArrayList<String> getHighlyEmotionalPositiveWords(String[] tokens, boolean[] negationIndexes, double[] boosters) {
		
		double[] sentimentScores = getWordsSentimentScores(tokens, negationIndexes, boosters);
		
		ArrayList<String> relevantPositiveWords = new ArrayList<String>();

		for (int i = 0; i < tokens.length; i++) {
			if (sentimentScores[i] >=3) {
				relevantPositiveWords.add(tokens[i]);
			}
		}

		return relevantPositiveWords;
	}
	
	/**
	 * Recuperate the list of relevant negative words
	 *
	 * @param tokens   : the sentence tokenized
	 * @param negationIndexes : the negation index list (eg. ["NOT", "NOT", "", "", ""])
	 * @param posTags  : the PoS-tags of the tokenized sentence
	 * @return an {@link ArrayList} of the relevant negative words
	 */
	public static ArrayList<String> getHighlyEmotionalNegativeWords(String[] tokens, boolean[] negationIndexes, double[] boosters) {
		
		double[] sentimentScores = getWordsSentimentScores(tokens, negationIndexes, boosters);
		
		ArrayList<String> relevantNegativeWords = new ArrayList<String>();

		for (int i = 0; i < tokens.length; i++) {
			if (sentimentScores[i] <=-3) {
				relevantNegativeWords.add(tokens[i]);
			}
		}

		return relevantNegativeWords;
	}
	
	/**
	 * Count the number of positive words of a sentence
	 *
	 * @param tokens   : the sentence tokenized
	 * @param negationIndexes : the negation index list (eg. ["NOT", "NOT", "", "", ""])
	 * @param posTags  : the PoS-tags of the tokenized sentence
	 * @return the number of positive words
	 */
	public static int countPositiveWords(String[] tokens, boolean[] negationIndexes, String[] posTags) {
		return getPositiveWords(tokens, negationIndexes, posTags).size();
	}
	
	/**
	 * Count the number of positive words of a sentence
	 * @param postiveWords
	 * @return
	 */
	public static int countPositiveWords(ArrayList<String> postiveWords) {
		return postiveWords.size();
	}
	
	/**
	 * Count the number of negative words of a sentence
	 *
	 * @param tokens   : the sentence tokenized
	 * @param negationIndexes : the negation index list (eg. ["NOT", "NOT", "", "", ""])
	 * @param posTags  : the PoS-tags of the tokenized sentence
	 * @return the number of negative words
	 */
	public static int countNegativeWords(String[] tokens, boolean[] negationIndexes, String[] posTags) {
		return getNegativeWords(tokens, negationIndexes, posTags).size();
	}

	/**
	 * Count the number of negative words of a sentence
	 * @param postiveWords
	 * @return
	 */
	public static int countNegativeWords(ArrayList<String> negativeWords) {
		return negativeWords.size();
	}
	
	/**
	 * Count the number of highly emotional positive words of a sentence
	 * @param tokens   : the sentence tokenized
	 * @param negationIndexes : the negation index list (eg. ["NOT", "NOT", "", "", ""])
	 * @param posTags  : the PoS-tags of the tokenized sentence
	 * @return the number of relevant positive words
	 */
	public static int countHighlyEmotionalPositiveWords(String[] tokens, boolean[] negationIndexes, double[] boosters) {
		return getHighlyEmotionalPositiveWords(tokens, negationIndexes, boosters).size();
	}

	/**
	 * Count the number of highly emotional positive words of a sentence
	 * @param postiveWords
	 * @return
	 */
	public static int countHighlyEmotionalPositiveWords(ArrayList<String> highlyEmotionalPostiveWords) {
		return highlyEmotionalPostiveWords.size();
	}
	
	/**
	 * Count the number of highly emotional positive words of a sentence
	 * @param postiveWords
	 * @return
	 */
	public static int countHighlyEmotionalPositiveWords(double[] wordScores, double threshold) {
		int count = 0;
		for (double score : wordScores) {
			if (score >=threshold) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Count the number of highly emotional negative words of a sentence
	 * @param tokens   : the sentence tokenized
	 * @param negationIndexes : the negation index list (eg. ["NOT", "NOT", "", "", ""])
	 * @param posTags  : the PoS-tags of the tokenized sentence
	 * @return the number of relevant negative words
	 */
	public static int countHighlyEmotionalNegativeWords(String[] tokens, boolean[] negationIndexes, double[] boosters) {
		return getHighlyEmotionalNegativeWords(tokens, negationIndexes, boosters).size();
	}
	
	/**
	 * Count the number of highly emotional negative words of a sentence
	 * @param postiveWords
	 * @return
	 */
	public static int countHighlyEmotionalNegativeWords(ArrayList<String> highlyEmotionalNegativeWords) {
		return highlyEmotionalNegativeWords.size();
	}
	
	/**
	 * Count the number of highly emotional negative words of a sentence
	 * @param postiveWords
	 * @return
	 */
	public static int countHighlyEmotionalNegativeWords(double[] wordScores, double threshold) {
		int count = 0;
		for (double score : wordScores) {
			if (score <= -threshold) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Calculate the ratio of sentimental words in a sentence
	 * @param wordsSentimentScores
	 * @return
	 */
	public static double getRatioOfEmotionalWords(double[] wordsSentimentScores) {
		double ratio = 0.0;
		double num = 0.0;
		double denom = 0.0;
		
		for (double wordScore : wordsSentimentScores) {
			num = num + wordScore;
			denom = denom + Math.abs(wordScore);
		}
		
		if (denom != 0) {
			ratio = num/denom;
		}
		
		return ratio;
	}
	
	/**
	 * Count the number of ALL CAPITAL positive words of a sentence
	 *
	 * @param tokens   : the sentence tokenized
	 * @param negationIndexes : the negation index list (eg. ["NOT", "NOT", "", "", ""])
	 * @param posTags  : the PoS-tags of the tokenized sentence
	 * @return the number of positive words
	 */
	public static int countCapitalizedPositiveWords(String[] tokens, boolean[] negationIndexes, String[] posTags) {
		ArrayList<String> posWords = getPositiveWords(tokens, negationIndexes, posTags);
		int count = 0;

		// TODO check the contains method!!!
		for (String token : tokens) {
			if (posWords.contains(token.toUpperCase()) || posWords.contains("NOT_" + token.toUpperCase())) {
				count++;
			}
		}

		return count;
	}

	/**
	 * Count the number of negative words of a sentence
	 *
	 * @param tokens   : the sentence tokenized
	 * @param negationIndexes : the negation index list (eg. ["NOT", "NOT", "", "", ""])
	 * @param posTags  : the PoS-tags of the tokenized sentence
	 * @return the number of negative words
	 */
	public static int countCapitalizedNegativeWords(String[] tokens, boolean[] negationIndexes, String[] posTags) {
		ArrayList<String> negWords = getNegativeWords(tokens, negationIndexes, posTags);
		int count = 0;

		for (String token : tokens) {
			if (negWords.contains(token.toUpperCase()) || negWords.contains("NOT_" + token.toUpperCase())) {
				count++;
			}
		}

		return count;
	}
	
	/**
	 * Count the number of positive Hashtags in a sentence
	 * @param sentence : the sentence to check
	 * @return the number of positive hashtags in the sentence
	 */
	public static int countPositiveHashtags(String[] sentence) {
		// TODO fix this. The current Tokenizer of OpenNLP in most of the cases divides the hashtag into two tokens
		// e.g. #IloveCalifornia = [#, IloveCalifornia]
		int count = 0;

		for (int i = 0; i < sentence.length; i++) {
			if (i > 0) {
				if (sentence[i - 1].equals("#") || WordProcessing.isHashtag(sentence[i])) {
					sentence[i] = WordProcessing.removeHashtagSymbol(sentence[i]);
					
					HashtagProcessing hashtagPro = new HashtagProcessing();
					
					if (hashtagPro.determinePolartiyOfHashtag(sentence[i]).equals("positive"))
						count++;
				}
				
			} else {
				if (WordProcessing.isHashtag(sentence[i])) {
					sentence[i] = WordProcessing.removeHashtagSymbol(sentence[i]);
					
					HashtagProcessing hashtagPro = new HashtagProcessing();
					
					if (hashtagPro.determinePolartiyOfHashtag(sentence[i])
							.equals("positive"))
						count++;
				}

			}
		}
		return count;
	}

	/**
	 * Count the number of negative Hashtags in a sentence
	 *
	 * @param sentence : the sentence to check
	 * @return the number of negative hashtags in the sentence
	 */
	public static int countNegativeHashtags(String[] sentence) {
		// TODO fix this. The current Tokenizer of OpenNLP in most of the cases divides the hashtag into two tokens
		// e.g. #IloveCalifornia = [#, IloveCalifornia]
		int count = 0;

		for (int i = 0; i < sentence.length; i++) {
			if (i > 0) {
				if (sentence[i - 1].equals("#") || WordProcessing.isHashtag(sentence[i])) {
					sentence[i] = WordProcessing.removeHashtagSymbol(sentence[i]);
					HashtagProcessing hashtagPro = new HashtagProcessing();
					if (hashtagPro.determinePolartiyOfHashtag(sentence[i])
							.equals("negative"))
						count++;
				}
			} else {
				if (WordProcessing.isHashtag(sentence[i])) {
					sentence[i] = WordProcessing.removeHashtagSymbol(sentence[i]);
					HashtagProcessing hashtagPro = new HashtagProcessing();
					if (hashtagPro.determinePolartiyOfHashtag(sentence[i])
							.equals("negative"))
						count++;
				}

			}
		}
		return count;
	}
	
	/**
	 * Count the number of positive Emoticons in a sentence
	 *
	 * @param sentence : the sentence to check
	 * @return the number of positive Emoticons in the sentence
	 */
	public static int countPositiveEmoticons(String sentence) {
		// TODO VERY IMPORTANT TO FIX: We are currently checking if the big string(SENTENCE) contains a small string (EMOTICON)
		// this is due to the problem of the tokenizer as mentioned above for hashtags
		int numberOfPositiveEmoticons = 0;

		for (String emoticon : Loader.getEmoticonPolarities().keySet()) {
			if (sentence.contains(emoticon)) {
				int polarity = Loader.getEmoticonPolarities().get(emoticon);
				if (polarity == 1) numberOfPositiveEmoticons+= StringUtils.countMatches(sentence, emoticon);
			}
		}
		return numberOfPositiveEmoticons;
	}

	/**
	 * Count the number of negative Emoticons in a sentence
	 *
	 * @param sentence : the sentence to check
	 * @return the number of negative Emoticons in the sentence
	 */
	public static int countNegativeEmoticons(String sentence) {
		// TODO VERY IMPORTANT TO FIX: We are currently checking if the big string(SENTENCE) contains a small string (EMOTICON)
		// this is due to the problem of the tokenizer as mentioned above for hashtags
		int numberOfNegativeEmoticons = 0;

		for (String emoticon : Loader.getEmoticonPolarities().keySet()) {
			if (sentence.contains(emoticon)) {
				int polarity = Loader.getEmoticonPolarities().get(emoticon);
				if (polarity == -1) numberOfNegativeEmoticons+= StringUtils.countMatches(sentence, emoticon);
			}
		}

		return numberOfNegativeEmoticons;
	}

	/**
	 * Count the number of neutral Emoticons in a sentence
	 *
	 * @param sentence : the sentence to check
	 * @return the number of neutral Emoticons in the sentence
	 */
	public static int countNeutralEmoticons(String sentence) {
		// TODO VERY IMPORTANT TO FIX: We are currently checking if the big string(SENTENCE) contains a small string (EMOTICON)
		// this is due to the problem of the tokenizer as mentioned above for hashtags
		int numberOfNeutralEmoticons = 0;

		for (String emoticon : Loader.getEmoticonPolarities().keySet()) {
			if (sentence.contains(emoticon)) {
				int polarity = Loader.getEmoticonPolarities().get(emoticon);
				if (polarity == 0) numberOfNeutralEmoticons+= StringUtils.countMatches(sentence, emoticon);
			}
		}

		return numberOfNeutralEmoticons;
	}

	/**
	 * Count the number of Joking Emoticons in a sentence
	 *
	 * @param sentence : the sentence to check
	 * @return the number of neutral Emoticons in the sentence
	 */
	public static int countJokingEmoticons(String sentence) {
		// TODO VERY IMPORTANT TO FIX: We are currently checking if the big string(SENTENCE) contains a small string (EMOTICON)
		// this is due to the problem of the tokenizer as mentioned above for hashtags
		int numberOfJokingEmoticons = 0;

		for (String emoticon : Loader.getEmoticonPolarities().keySet()) {
			if (sentence.contains(emoticon)) {
				int polarity = Loader.getEmoticonPolarities().get(emoticon);
				if (polarity == 2) numberOfJokingEmoticons+= StringUtils.countMatches(sentence, emoticon);
			}
		}

		return numberOfJokingEmoticons;
	}
	
	/**
	 * Count the number of positive slang words in a sentence
	 *
	 * @param sentence : the sentence to check
	 * @return the number of positive slang words in the sentence
	 */
	public static int countPositiveSlangs(String[] sentence) {

		int positiveCount = 0;

		for (int i = 0; i < sentence.length; i++) {
			if (WordProcessing.isSlang(sentence[i]) == true) {
				int polarity = Loader.getSlangPolarities().get(sentence[i].toLowerCase());
				if (polarity == 1) positiveCount++;
			}
		}

		return positiveCount;
	}

	/**
	 * Count the number of negative slang words in a sentence
	 *
	 * @param sentence : the sentence to check
	 * @return the number of negative slang words in the sentence
	 */
	public static int countNegativeSlangs(String[] sentence) {

		int negativeCount = 0;

		for (int i = 0; i < sentence.length; i++) {
			if (WordProcessing.isSlang(sentence[i]) == true) {
				int polarity = Loader.getSlangPolarities().get(sentence[i].toLowerCase());
				if (polarity == -1) negativeCount++;
			}
		}

		return negativeCount;
	}
	
	
	//======================================//
	//         TEXT PRE-PROCESSING          //
	//======================================//
	
	/**
	 * Replace HTML codes by the corresponding characters (Example: "&amp;" by "&" and "&lt;" by "<")
	 * @param text
	 * @return
	 */
	public static String replaceHTMLCharacters(String text) {
		text = text.replaceAll("&quot;", "\"");
		text = text.replaceAll("&amp;", "&");
		text = text.replaceAll("&lt;", "<");
		text = text.replaceAll("&gt;", ">");
		return text;
	}

	/**
	 * Replace Saucy Words
	 * @param text
	 * @return
	 */
	public static String replaceSaucyWords(String text) {
		text = text.replaceAll("a\\*\\*", "ass");
		text = text.replaceAll("f__k", "fuck");
		text = text.replaceAll("f\\*\\*k", "fuck");
		text = text.replaceAll("f\\*ck", "fuck");
		text = text.replaceAll("fu\\*k", "fuck");
		text = text.replaceAll("s\\*\\*t", "shit");
		text = text.replaceAll("sh\\*t", "shit");
		text = text.replaceAll("s\\*it", "shit");
		text = text.replaceAll("s\\*\\*t", "shit");
		text = text.replaceAll("d\\*\\*n", "damn");
		text = text.replaceAll("d\\*mn", "damn");
		text = text.replaceAll("da\\*n", "damn");
		text = text.replaceAll("b\\*\\*\\*\\*\\*d", "bastard");
		text = text.replaceAll("b\\*\\*\\*h", "bitch");
		text = text.replaceAll("bi\\*\\*h", "bitch");
		text = text.replaceAll("bit\\*h", "bitch");
		text = text.replaceAll("b\\*\\*ch", "bitch");
		text = text.replaceAll("bi\\*ch", "bitch");
		text = text.replaceAll("b\\*tch", "bitch");
		text = text.replaceAll("c\\*m", "cum");
		text = text.replaceAll("c\\*\\*", "cum");
		return text;
	}
	
	// URL related
	
	/**
	 * Replace the URLs in a sentence by an empty String
	 *
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing any existing URL by an empty String ""
	 */
	public static String removeURLs(String sentence) {

		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.removeURL(tokens[i]);
			output = output + " " + tokens[i];
		}
		
		output.replaceAll("  ", " ");

		return output.substring(1, output.length());
	}

	/**
	 * Replace the URLs in a sentence by the expression "AT_URL"
	 *
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing any existing URL by the expression "AT_URL"
	 */
	public static String replaceURLs(String sentence) {

		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.replaceURL(tokens[i]);
			output = output + " " + tokens[i];
		}

		return output.substring(1, output.length());
	}

	/**
	 * Replace the URLs in a sentence by an expression defined by the user
	 *
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing any existing URL by the expression given by the user
	 */
	public static String replaceURLs(String sentence, String userDefined) {

		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.replaceURL(tokens[i], userDefined);
			output = output + " " + tokens[i];
		}

		return output.substring(1, output.length());
	}

	// Tag-related
	
	/**
	 * Remove the tag symbol from the words that are Tags in a sentence
	 *
	 * @param sentence : the sentence to modify
	 * @return the same sentence while removing the tag symbols
	 */
	public static String removeTagSymbol(String sentence) {
		
		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.removeTagSymbol(tokens[i]);
			output = output + " " + tokens[i];
		}

		return output.substring(1, output.length());
	}

	/**
	 * Replace the Tags in a sentence by an empty String
	 *
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing any existing Tag by an empty String ""
	 */
	public static String removeTags(String sentence) {
		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.removeTag(tokens[i]);
			output = output + " " + tokens[i];
		}
		
		output.replaceAll("  ", " ");

		return output.substring(1, output.length());
	}

	/**
	 * Replace the Tags in a sentence by the expression "TO_USER"
	 *
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing any existing Tag by the expression "AT_URL"
	 */
	public static String replaceTags(String sentence) {

		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.replaceTag(tokens[i]);
			output = output + " " + tokens[i];
		}

		return output.substring(1, output.length());

	}

	/**
	 * Replace the Tags in a sentence by an expression defined by the user
	 *
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing any existing Tag by the expression given by the user
	 */
	public static String replaceTags(String sentence, String userDefined) {
		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.replaceTag(tokens[i], userDefined);
			output = output + " " + tokens[i];
		}

		return output.substring(1, output.length());
	}

	// Email-related

	/**
	 * Replace the email addresses in a sentence by an empty String
	 *
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing any existing email address by an empty String ""
	 */
	public static String removeEmails(String sentence) {
		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.removeEmail(tokens[i]);
			output = output + " " + tokens[i];
		}
		
		output.replaceAll("  ", " ");

		return output.substring(1, output.length());
	}

	/**
	 * Replace the email addresses in a sentence by the expression "TO_EMAIL"
	 *
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing any existing email address by the expression "TO_EMAIL"
	 */
	public static String replaceEmails(String sentence) {
		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.replaceEmail(tokens[i]);
			output = output + " " + tokens[i];
		}

		return output.substring(1, output.length());
	}

	/**
	 * Replace the email addresses in a sentence by an expression given by the user
	 *
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing any existing email address by the expression given by the user
	 */
	public static String replaceEmails(String sentence, String userDefined) {
		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.replaceEmail(tokens[i], userDefined);
			output = output + " " + tokens[i];
		}

		return output.substring(1, output.length());
	}

	// Hashtag-related
	
	/**
	 * Replace the hashtags in a sentence by an empty String
	 *
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing any existing hashtag by an empty String ""
	 */
	public static String removeHashtags(String sentence) {
		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.removeHashtag(tokens[i]);
			output = output + " " + tokens[i];
		}
		
		output.replaceAll("  ", " ");

		return output.substring(1, output.length());
	}

	/**
	 * Decompose the hashtag into the words composing it, and replace the hashtag with the resulting expression
	 * [IMPORTANT : The words are stored together in the same token]
	 *
	 * @param sentence : the sentence to modify
	 * @return
	 */
	public static String decomposeHashtags(String sentence) {
		//TODO check if it makes sense
		
		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.decomposeHashtag(tokens[i]);
			output = output + " " + tokens[i];
		}
		
		output.replaceAll("  ", " ");

		return output.substring(1, output.length());
	}

	/**
	 * Replace the hashtags in a sentence by the expression "AT_HASHTAG"
	 *
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing any existing hashtag by the expression "AT_HASHTAG"
	 */
	public static String replaceHashtags(String sentence) {
		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.replaceHashtag(tokens[i]);
			output = output + " " + tokens[i];
		}

		return output.substring(1, output.length());
	}

	/**
	 * Replace the hashtags in a sentence by an expression given by the user
	 *
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing any existing hashtag by the expression given by the user
	 */
	public static String replaceHashtags(String sentence, String userDefined) {
		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.replaceHashtag(tokens[i], userDefined);
			output = output + " " + tokens[i];
		}

		return output.substring(1, output.length());
	}
	
	// Emoticons-related

	/**
	 * Remove Emoticons (Replace the emoticons in a sentence by an empty String
	 *
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing any existing emoticon by a white space
	 */
	public static String removeEmoticons(String sentence) {
		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.removeEmoticon(tokens[i]);
			output = output + " " + tokens[i];
		}

		return output.substring(1, output.length());
	}

	// Slang-related
	
	/**
	 * Replace the slang word by the expression it means
	 * [Unlike the other components, slang words require the sentence to be tokenized at first]
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing every slang word by the expression it means
	 */
	public static String[] correctSlangs(String[] sentence) {
		for (int i = 0; i < sentence.length; i++) {
			sentence[i] = WordProcessing.replaceSlang(sentence[i]);
		}
		return sentence;
	}

	/**
	 * Replace the slang words in a sentence by an empty String
	 * [Unlike the other components, slang words require the sentence to be tokenized at first]
	 * @param sentence : the sentence to modify
	 * @return the same sentence while replacing any existing slang word by an empty String ""
	 */
	public static String[] removeSlangs(String[] sentence) {
		for (int i = 0; i < sentence.length; i++) {
			sentence[i] = WordProcessing.replaceSlang(sentence[i]);
		}
		return sentence;
	}
	
	// Other pre-processing steps

	/**
	 * Replace EVERY word containing an apostrophe with its long form
	 *
	 * @param sentence : the sentence to modify
	 * @return
	 */
	public static String replaceApostropheWords(String sentence) {
		for (String key : Loader.getApastropheWords().keySet()) {
			sentence = sentence.replaceAll(key, Loader.getApastropheWords().get(key));
		}
		return sentence;
	}

	/**
	 * Replace the "_" character by a " " character except for tokens which are email addresses, URLs, and Tags
	 *
	 * @param sentence : the sentence to modify
	 * @return : the same sentence while replacing the "_" character with a white space
	 */
	public static String replaceUnderscores(String sentence) {
		
		String[] tokens = sentence.split("\\s");
		String output = "";
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = WordProcessing.replaceUnderscore(tokens[i]);
			output = output + " " + tokens[i];
		}

		return output.substring(1, output.length());
	}
	
	// Correct Misspelled words
	
	/**
	 * Correct misspelled words with their correct form using the Norvig Corrector
	 * [Of course, emoticons, tags, hashtags, etc. are not considered as misspelled words]
	 *
	 * @param sentence : the sentence to check and correct
	 * @return
	 */
	public static String[] norvigCorrectText(String[] sentence, WordProcessing.Corrector corrector) {
		for (int i = 0; i < sentence.length; i++) {
			sentence[i] = WordProcessing.correctWord(sentence[i], corrector);
		}
		return sentence;
	}
	
	// Global pre-processor
	/**
	 * Replace words with apostrophe with their long form and simplify punctuation
	 *
	 * @param sentence : the sentence to modify
	 * @return the sentence after replacing the words with apostrophe by their longer form, and simplifying the punctuation
	 */
	public static String preprocess(String sentence) {

		String outputSentence = sentence;
		
		outputSentence = replaceHTMLCharacters(outputSentence);
		outputSentence = replaceSaucyWords(outputSentence);
		// outputSentence = replaceApostropheWords(outputSentence);
		// outputSentence = simplifyPunctuation(outputSentence);
		outputSentence = replaceURLs(outputSentence);
		outputSentence = replaceTags(outputSentence);
		outputSentence = replaceEmails(outputSentence);
		
		return outputSentence;
	}
	
	
	//======================================//
	//      BASIC PATTERNS EXTRACTION       //
	//======================================//

	/**
	 * Extract the Vector of Patterns (Type String[])
	 *
	 * @param lemmas  : the sentence to extract the patterns from
	 * @param posTags : the PoS-tags of the tokens of the sentence
	 * @return the full Sarcasm pattern vector
	 */
	public static String[] extractFullPatternVecor(String[] tokens, String[] lemmas, String[] posTags, double[] sentimentPolarity) {
		// TODO handle the null pointer, array out of bounds, etc. cases
		ArrayList<String> pattern = new ArrayList<String>();

		for (int i = 0; i < tokens.length; i++) {
			if (Loader.getPosList1().containsKey(posTags[i])) {
				if (Features.getAction1().equals(Features.PatternActions.KEEP)) {
					pattern.add(tokens[i]);
				} else if (Features.getAction1().equals(Features.PatternActions.LEMMATIZE)) {
					pattern.add(lemmas[i]);
				} else if (Features.getAction1().equals(Features.PatternActions.POS)) {
					pattern.add(posTags[i]);
				} else if (Features.getAction1().equals(Features.PatternActions.SIMPLIFIEDPOS)) {
					pattern.add(Loader.getSimplifiedPosTagsConverter().get(posTags[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT)) {
					String component = "";
					if (sentimentPolarity[i]==0) {
						component = "NEUTRAL_";
					} else if (sentimentPolarity[i]>0) {
						component = "POSITIVE_";
					} else {
						component = "NEGATIVE_";
					}
					component = component + Loader.getSimplifiedPosTagsConverter().get(posTags[i]);
					pattern.add(component);
				} else if (Features.getAction1().equals(Features.PatternActions.REPLACEWITH)) {
					pattern.add(Features.getReplacement1());
				} else {
					// handle this case
					pattern.add("UNDEFINED");
				}
			} else if (Loader.getPosList2().containsKey(posTags[i])) {
				if (Features.getAction2().equals(Features.PatternActions.KEEP)) {
					pattern.add(tokens[i]);
				} else if (Features.getAction2().equals(Features.PatternActions.LEMMATIZE)) {
					pattern.add(lemmas[i]);
				} else if (Features.getAction2().equals(Features.PatternActions.POS)) {
					pattern.add(posTags[i]);
				} else if (Features.getAction2().equals(Features.PatternActions.SIMPLIFIEDPOS)) {
					pattern.add(Loader.getSimplifiedPosTagsConverter().get(posTags[i]));
				} else if (Features.getAction2().equals(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT)) {
					String component = "";
					if (sentimentPolarity[i]==0) {
						component = "NEUTRAL_";
					} else if (sentimentPolarity[i]>0) {
						component = "POSITIVE_";
					} else {
						component = "NEGATIVE_";
					}
					component = component + Loader.getSimplifiedPosTagsConverter().get(posTags[i]);
					pattern.add(component);
				} else if (Features.getAction2().equals(Features.PatternActions.REPLACEWITH)) {
					pattern.add(Features.getReplacement2());
				} else {
					// handle this case
					pattern.add("UNDEFINED");
				}
			} else if (Loader.getPosList3().containsKey(posTags[i])) {
				if (Features.getAction3().equals(Features.PatternActions.KEEP)) {
					pattern.add(tokens[i]);
				} else if (Features.getAction3().equals(Features.PatternActions.LEMMATIZE)) {
					pattern.add(lemmas[i]);
				} else if (Features.getAction3().equals(Features.PatternActions.POS)) {
					pattern.add(posTags[i]);
				} else if (Features.getAction3().equals(Features.PatternActions.SIMPLIFIEDPOS)) {
					pattern.add(Loader.getSimplifiedPosTagsConverter().get(posTags[i]));
				} else if (Features.getAction3().equals(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT)) {
					String component = "";
					if (sentimentPolarity[i]==0) {
						component = "NEUTRAL_";
					} else if (sentimentPolarity[i]>0) {
						component = "POSITIVE_";
					} else {
						component = "NEGATIVE_";
					}
					component = component + Loader.getSimplifiedPosTagsConverter().get(posTags[i]);
					pattern.add(component);
				} else if (Features.getAction3().equals(Features.PatternActions.REPLACEWITH)) {
						pattern.add(Features.getReplacement3());
				} else {
					// handle this case
					pattern.add("UNDEFINED");
				}
			} else if (Loader.getPosList4().containsKey(posTags[i])) {
				if (Features.getAction4().equals(Features.PatternActions.KEEP)) {
					pattern.add(tokens[i]);
				} else if (Features.getAction4().equals(Features.PatternActions.LEMMATIZE)) {
					pattern.add(lemmas[i]);
				} else if (Features.getAction4().equals(Features.PatternActions.POS)) {
					pattern.add(posTags[i]);
				} else if (Features.getAction4().equals(Features.PatternActions.SIMPLIFIEDPOS)) {
					pattern.add(Loader.getSimplifiedPosTagsConverter().get(posTags[i]));
				} else if (Features.getAction4().equals(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT)) {
					String component = "";
					if (sentimentPolarity[i]==0) {
						component = "NEUTRAL_";
					} else if (sentimentPolarity[i]>0) {
						component = "POSITIVE_";
					} else {
						component = "NEGATIVE_";
					}
					component = component + Loader.getSimplifiedPosTagsConverter().get(posTags[i]);
					pattern.add(component);
				} else if (Features.getAction4().equals(Features.PatternActions.REPLACEWITH)) {
						pattern.add(Features.getReplacement4());
				} else {
					// handle this case
					pattern.add("UNDEFINED");
				}
			} else {
				pattern.add(".");
			}

		}
		
		String[] posTagVector = pattern.toArray(new String[pattern.size()]);
		return posTagVector;
	}

	/**
	 * Extract the different patterns from a text such as their length satisfies
	 * 		min_length < Length(pattern) < max_length (Type ArrayList<String[]>)
	 * @param sentence             : the sentence to extract the patterns from
	 * @param posTags              : the PoS-tags of the tokens of the sentence
	 * @param minLengthOftheVector : the minimal length of a pattern
	 * @param MaxLengthOfVector    : the maximal length of a pattern
	 * @return
	 */
	public static ArrayList<String[]> createPatternVectors(String[] tokens, String[] lemmas, String[] posTags, double[] sentimentPolarity, int minLengthOftheVector, int MaxLengthOfVector) {

		String[] fullVector = extractFullPatternVecor(tokens, lemmas, posTags, sentimentPolarity);

		ArrayList<String[]> vectors = new ArrayList<String[]>();

		if (!(fullVector.length < minLengthOftheVector)) {
			for (int i = 0; i < fullVector.length - minLengthOftheVector; i++) {
				ArrayList<String> tempVector = new ArrayList<String>();
				for (int j = i; j < Math.min(i + MaxLengthOfVector, fullVector.length); j++) {
					tempVector.add(fullVector[j]);
					if (tempVector.size() >= minLengthOftheVector)
						vectors.add(tempVector.toArray(new String[tempVector.size()]));
				}
			}
		}

		return vectors;
	}
	
	/**
	 * Extract the different patterns from the pattern full vector  (Type ArrayList<String[]>)
	 * @param fullPatternVector the full pattern [already extracted]
	 * @param minLengthOftheVector
	 * @param MaxLengthOfVector
	 * @return
	 */
	public static ArrayList<String[]> createPatternVectors(String[] fullPatternVector, int minLengthOftheVector, int MaxLengthOfVector) {

		ArrayList<String[]> vectors = new ArrayList<String[]>();

		if (!(fullPatternVector.length < minLengthOftheVector)) {
			for (int i = 0; i < fullPatternVector.length - minLengthOftheVector; i++) {
				ArrayList<String> tempVector = new ArrayList<String>();
				for (int j = i; j < Math.min(i + MaxLengthOfVector, fullPatternVector.length); j++) {
					tempVector.add(fullPatternVector[j]);
					if (tempVector.size() >= minLengthOftheVector)
						vectors.add(tempVector.toArray(new String[tempVector.size()]));
				}
			}
		}

		return vectors;
	}
	
	//-------------------------------------------
	
	/**
	 * Extract the Vector of Patterns  (Type int[])
	 *
	 * @param lemmas  : the sentence to extract the patterns from
	 * @param posTags : the PoS-tags of the tokens of the sentence
	 * @return the full Sarcasm pattern vector
	 */
	public static int[] extractFullNumericPatternVecor(String[] tokens, String[] lemmas, String[] posTags, double[] sentimentPolarity) {
		// TODO handle the null pointer, array out of bounds, etc. cases
		ArrayList<Integer> pattern = new ArrayList<>();

		for (int i = 0; i < tokens.length; i++) {
			if (Loader.getPosList1().containsKey(posTags[i])) {
				if (Features.getAction1().equals(Features.PatternActions.KEEP)) {
					pattern.add(Loader.getWordsSerialized().get(tokens[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.LEMMATIZE)) {
					pattern.add(Loader.getWordsSerialized().get(lemmas[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.POS)) {
					pattern.add(Loader.getPosSerialized().get(posTags[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.SIMPLIFIEDPOS)) {
					pattern.add(Loader.getPosSerialized().get(posTags[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT)) {
					int component = 0;
					if (sentimentPolarity[i]==0) {
						component += Loader.getPosSerialized().get("POSITIVE");
					} else if (sentimentPolarity[i]>0) {
						component = Loader.getPosSerialized().get("NEGATIVE");
					} else {
						component = Loader.getPosSerialized().get("NEUTRAL");
					}
					component = component + Loader.getPosSerialized().get(posTags[i]);
					pattern.add(component);
				} else if (Features.getAction1().equals(Features.PatternActions.REPLACEWITH)) {
					pattern.add(Loader.getPosSerialized().get(Features.getReplacement1()));
				} else {
					// handle this case
					pattern.add(-1000000);
				}
			} else if (Loader.getPosList2().containsKey(posTags[i])) {
				if (Features.getAction1().equals(Features.PatternActions.KEEP)) {
					pattern.add(Loader.getWordsSerialized().get(tokens[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.LEMMATIZE)) {
					pattern.add(Loader.getWordsSerialized().get(lemmas[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.POS)) {
					pattern.add(Loader.getPosSerialized().get(posTags[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.SIMPLIFIEDPOS)) {
					pattern.add(Loader.getPosSerialized().get(posTags[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT)) {
					int component = 0;
					if (sentimentPolarity[i]==0) {
						component += Loader.getPosSerialized().get("POSITIVE");
					} else if (sentimentPolarity[i]>0) {
						component = Loader.getPosSerialized().get("NEGATIVE");
					} else {
						component = Loader.getPosSerialized().get("NEUTRAL");
					}
					component = component + Loader.getPosSerialized().get(posTags[i]);
					pattern.add(component);
				} else if (Features.getAction1().equals(Features.PatternActions.REPLACEWITH)) {
					pattern.add(Loader.getPosSerialized().get(Features.getReplacement1()));
				} else {
					// handle this case
					pattern.add(-1000000);
				}
			} else if (Loader.getPosList3().containsKey(posTags[i])) {
				if (Features.getAction1().equals(Features.PatternActions.KEEP)) {
					pattern.add(Loader.getWordsSerialized().get(tokens[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.LEMMATIZE)) {
					pattern.add(Loader.getWordsSerialized().get(lemmas[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.POS)) {
					pattern.add(Loader.getPosSerialized().get(posTags[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.SIMPLIFIEDPOS)) {
					pattern.add(Loader.getPosSerialized().get(posTags[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT)) {
					int component = 0;
					if (sentimentPolarity[i]==0) {
						component += Loader.getPosSerialized().get("POSITIVE");
					} else if (sentimentPolarity[i]>0) {
						component = Loader.getPosSerialized().get("NEGATIVE");
					} else {
						component = Loader.getPosSerialized().get("NEUTRAL");
					}
					component = component + Loader.getPosSerialized().get(posTags[i]);
					pattern.add(component);
				} else if (Features.getAction1().equals(Features.PatternActions.REPLACEWITH)) {
					pattern.add(Loader.getPosSerialized().get(Features.getReplacement1()));
				} else {
					// handle this case
					pattern.add(-1000000);
				}
			} else if (Loader.getPosList4().containsKey(posTags[i])) {
				if (Features.getAction1().equals(Features.PatternActions.KEEP)) {
					pattern.add(Loader.getWordsSerialized().get(tokens[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.LEMMATIZE)) {
					pattern.add(Loader.getWordsSerialized().get(lemmas[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.POS)) {
					pattern.add(Loader.getPosSerialized().get(posTags[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.SIMPLIFIEDPOS)) {
					pattern.add(Loader.getPosSerialized().get(posTags[i]));
				} else if (Features.getAction1().equals(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT)) {
					int component = 0;
					if (sentimentPolarity[i]==0) {
						component += Loader.getPosSerialized().get("POSITIVE");
					} else if (sentimentPolarity[i]>0) {
						component = Loader.getPosSerialized().get("NEGATIVE");
					} else {
						component = Loader.getPosSerialized().get("NEUTRAL");
					}
					component = component + Loader.getPosSerialized().get(posTags[i]);
					pattern.add(component);
				} else if (Features.getAction1().equals(Features.PatternActions.REPLACEWITH)) {
					pattern.add(Loader.getPosSerialized().get(Features.getReplacement1()));
				} else {
					// handle this case
					pattern.add(-1000000);
				}
			} else {
				pattern.add(0);
			}

		}
		
		int[] posTagVector = new int[pattern.size()];
		
		for (int i=0; i<pattern.size(); i++) {
			posTagVector[i] = pattern.get(i);
		}
		
		return posTagVector;
	}

	/**
	 * Extract the different patterns from a text such as their length satisfies
	 * 		min_length < Length(pattern) < max_length (Type ArrayList<int[]>)
	 * @param sentence             : the sentence to extract the patterns from
	 * @param posTags              : the PoS-tags of the tokens of the sentence
	 * @param minLengthOftheVector : the minimal length of a pattern
	 * @param MaxLengthOfVector    : the maximal length of a pattern
	 * @return
	 */
	public static ArrayList<int[]> createNumericPatternVectors(String[] tokens, String[] lemmas, String[] posTags, double[] sentimentPolarity, int minLengthOftheVector, int MaxLengthOfVector) {

		int[] fullVector = extractFullNumericPatternVecor(tokens, lemmas, posTags, sentimentPolarity);

		ArrayList<int[]> vectors = new ArrayList<>();

		if (!(fullVector.length < minLengthOftheVector)) {
			for (int i = 0; i < fullVector.length - minLengthOftheVector; i++) {
				ArrayList<Integer> tempVector = new ArrayList<>();
				for (int j = i; j < Math.min(i + MaxLengthOfVector, fullVector.length); j++) {
					tempVector.add(fullVector[j]);
					if (tempVector.size() >= minLengthOftheVector) {
						int[] v = new int[tempVector.size()];
						for (int k=0; k<tempVector.size(); k++) {
							v[k] = tempVector.get(k);
						}
						vectors.add(v);
					}
						
				}
			}
		}

		return vectors;
	}
	
	/**
	 * Extract the different patterns from the pattern full vector (Type ArrayList<int[]>)
	 * @param fullPatternVector the full pattern [already extracted]
	 * @param minLengthOftheVector
	 * @param MaxLengthOfVector
	 * @return
	 */
	public static ArrayList<int[]> createNumericPatternVectors(int[] fullPatternVector, int minLengthOftheVector, int MaxLengthOfVector) {

		ArrayList<int[]> vectors = new ArrayList<>();

		if (!(fullPatternVector.length < minLengthOftheVector)) {
			for (int i = 0; i < fullPatternVector.length - minLengthOftheVector; i++) {
				ArrayList<Integer> tempVector = new ArrayList<>();
				for (int j = i; j < Math.min(i + MaxLengthOfVector, fullPatternVector.length); j++) {
					tempVector.add(fullPatternVector[j]);
					if (tempVector.size() >= minLengthOftheVector) {
						int[] v = new int[tempVector.size()];
						for (int k=0; k<tempVector.size(); k++) {
							v[k] = tempVector.get(k);
						}
						vectors.add(v);
					}
				}
			}
		}

		return vectors;
	}
	
	
	
	//======================================//
	//     ADVANCED PATTERNS EXTRACTION     //
	//======================================//
	
	/**
	 * Extract the Vector of advanced Patterns
	 *
	 * @param lemmas  : the sentence to extract the patterns from
	 * @param posTags : the PoS-tags of the tokens of the sentence
	 * @return the full Sarcasm pattern vector
	 */
	public static String[] extractFullAdvancedPatternVector(String[] tokens, String[] lemmas, String[] posTags, String sentiment, ArrayList<String> sentimentalWords) {
		// TODO handle the null pointer, array out of bounds, etc. cases
		ArrayList<String> pattern = new ArrayList<String>();

		for (int i = 0; i < tokens.length; i++) {
			
			if (Commons.isRelevant(posTags[i])) {
				String component = "";
				if (sentimentalWords.contains(lemmas[i])) {
					component = sentiment + "_";
				} else {
					component = "";
				}
				component = component + Loader.getSimplifiedPosTagsConverter().get(posTags[i]);
				pattern.add(component);
			} else {
				pattern.add(posTags[i]);
			}
		}

		String[] posTagVector = pattern.toArray(new String[pattern.size()]);

		return posTagVector;
	}

	/**
	 * Extract the different patterns from a text such as their length satisfies
	 * 		min_length < Length(pattern) < max_length
	 * @param sentence             : the sentence to extract the patterns from
	 * @param posTags              : the PoS-tags of the tokens of the sentence
	 * @param minLengthOftheVector : the minimal length of a pattern
	 * @param MaxLengthOfVector    : the maximal length of a pattern
	 * @return
	 */
	public static ArrayList<String[]> createAdvancedPatternVectors(String[] tokens, String[] lemmas, String[] posTags, String sentiment, ArrayList<String> sentimentalWords, int minLengthOftheVector, int MaxLengthOfVector) {

		String[] fullVector = extractFullAdvancedPatternVector(tokens, lemmas, posTags, sentiment, sentimentalWords);

		ArrayList<String[]> vectors = new ArrayList<String[]>();

		if (!(fullVector.length < minLengthOftheVector)) {
			for (int i = 0; i < fullVector.length - minLengthOftheVector; i++) {
				ArrayList<String> tempVector = new ArrayList<String>();
				for (int j = i; j < Math.min(i + MaxLengthOfVector, fullVector.length); j++) {
					tempVector.add(fullVector[j]);
					if (tempVector.size() >= minLengthOftheVector)
						vectors.add(tempVector.toArray(new String[tempVector.size()]));
				}
			}
		}

		return vectors;
	}
	
	/**
	 * Extract the different patterns from the pattern full vector
	 * @param fullPatternVector the full pattern [already extracted]
	 * @param minLengthOftheVector
	 * @param MaxLengthOfVector
	 * @return
	 */
	public static ArrayList<String[]> createAdvancedPatternVectors(String[] fullPatternVector, int minLengthOftheVector, int MaxLengthOfVector) {

		ArrayList<String[]> vectors = new ArrayList<String[]>();

		if (!(fullPatternVector.length < minLengthOftheVector)) {
			for (int i = 0; i < fullPatternVector.length - minLengthOftheVector; i++) {
				ArrayList<String> tempVector = new ArrayList<String>();
				for (int j = i; j < Math.min(i + MaxLengthOfVector, fullPatternVector.length); j++) {
					tempVector.add(fullPatternVector[j]);
					if (tempVector.size() >= minLengthOftheVector)
						vectors.add(tempVector.toArray(new String[tempVector.size()]));
				}
			}
		}

		return vectors;
	}
	
	//-------------------------------------------
	
	/**
	 * Extract the Vector of advanced Patterns (Type : int[])
	 *
	 * @param lemmas  : the sentence to extract the patterns from
	 * @param posTags : the PoS-tags of the tokens of the sentence
	 * @return the full Sarcasm pattern vector
	 */
	public static int[] extractFullNumericAdvancedPatternVector(String[] tokens, String[] lemmas, String[] posTags, String sentiment, ArrayList<String> sentimentalWords) {
		// TODO handle the null pointer, array out of bounds, etc. cases
		ArrayList<Integer> pattern = new ArrayList<>();

		for (int i = 0; i < tokens.length; i++) {
			
			if (Commons.isRelevant(posTags[i])) {
				int component = 0;
				if (sentimentalWords.contains(lemmas[i])) {
					component = Loader.getPosSerialized().get(sentiment) + Loader.getPosSerialized().get(Loader.getSimplifiedPosTagsConverter().get(posTags[i]));
				} else {
					component = Loader.getPosSerialized().get(Loader.getSimplifiedPosTagsConverter().get(posTags[i]));
				}
				pattern.add(component);
			} else {
				pattern.add(Loader.getPosSerialized().get(posTags[i]));
			}
		}
		
		int[] posTagVector = new int[pattern.size()];
		
		for (int i=0; i<pattern.size(); i++) {
			posTagVector[i] = pattern.get(i);
		}

		return posTagVector;
	}

	/**
	 * Extract the different advanced patterns from a text such as their length satisfies min_length < Length(pattern) < max_length  (Type : int[])
	 * @param sentence             : the sentence to extract the patterns from
	 * @param posTags              : the PoS-tags of the tokens of the sentence
	 * @param minLengthOftheVector : the minimal length of a pattern
	 * @param MaxLengthOfVector    : the maximal length of a pattern
	 * @return
	 */
	public static ArrayList<int[]> createNumericAdvancedPatternVectors(String[] tokens, String[] lemmas, String[] posTags, String sentiment, ArrayList<String> sentimentalWords, int minLengthOftheVector, int MaxLengthOfVector) {

		int[] fullVector = extractFullNumericAdvancedPatternVector(tokens, lemmas, posTags, sentiment, sentimentalWords);

		ArrayList<int[]> vectors = new ArrayList<>();

		if (!(fullVector.length < minLengthOftheVector)) {
			for (int i = 0; i < fullVector.length - minLengthOftheVector; i++) {
				ArrayList<Integer> tempVector = new ArrayList<>();
				for (int j = i; j < Math.min(i + MaxLengthOfVector, fullVector.length); j++) {
					tempVector.add(fullVector[j]);
					if (tempVector.size() >= minLengthOftheVector) {
						int[] posTagVector = new int[tempVector.size()];
						
						for (int k=0; k<tempVector.size(); k++) {
							posTagVector[k] = tempVector.get(k);
						}
						
						vectors.add(posTagVector);
					}
						
						
				}
			}
		}

		return vectors;
	}
	
	/**
	 * Extract the different patterns from the pattern full vector
	 * @param fullPatternVector the full pattern [already extracted]
	 * @param minLengthOftheVector
	 * @param MaxLengthOfVector
	 * @return
	 */
	public static ArrayList<int[]> createNumericAdvancedPatternVectors(int[] fullPatternVector, int minLengthOftheVector, int MaxLengthOfVector) {

		ArrayList<int[]> vectors = new ArrayList<>();

		if (!(fullPatternVector.length < minLengthOftheVector)) {
			for (int i = 0; i < fullPatternVector.length - minLengthOftheVector; i++) {
				ArrayList<Integer> tempVector = new ArrayList<>();
				for (int j = i; j < Math.min(i + MaxLengthOfVector, fullPatternVector.length); j++) {
					tempVector.add(fullPatternVector[j]);
					if (tempVector.size() >= minLengthOftheVector) {
						int[] posTagVector = new int[tempVector.size()];
						
						for (int k=0; k<tempVector.size(); k++) {
							posTagVector[k] = tempVector.get(k);
						}
						
						vectors.add(posTagVector);
					}
				}
			}
		}

		return vectors;
	}
	
	//======================================//
	//   POS DISTRIBUTION AND TRANSITION    //
	//======================================//
	
	// Distribution
	/**
	 * Generate the distribution of PoS-tags of a text
	 *
	 * @param posFullVector : the PoS-tags vector of the text
	 * @return an {@link Array} describing the number of occurrences of each PoS-Tag in the text
	 */
	public static double[] generatePosDistribution(String[] posFullVector) {

		int size = Constants.allPOS.size();
		int textLength = posFullVector.length;

		int[] distributionNumbers = new int[size + 1];

		for (int i = 0; i < size + 1; i++) {
			distributionNumbers[i] = 0;
		}

		if (textLength != 0) {
			for (int i = 0; i < textLength; i++) {
				boolean test = false;
				for (int j = 0; j < size; j++) {
					if (posFullVector[i].equals(Constants.allPOS.get(j))) {
						distributionNumbers[j] = distributionNumbers[j] + 1;
						test = true;
						break;
					}
				}
				if (!test) {
					distributionNumbers[size] = distributionNumbers[size] + 1;
				}
			}
		}
		
		int total = 0;
		
		for(int i = 0; i<distributionNumbers.length; i++) {
			total = total + distributionNumbers[i];
		}
		
		double[] result = new double[distributionNumbers.length];
		

		for (int i = 0; i < distributionNumbers.length; i++) {
			result[i] = (double) distributionNumbers[i] / (double) total;
		}

		return result;
	}
	
	/**
	 * Generate the distribution of PoS-tags of a set of texts
	 * @param posFullVectors : the PoS vectors of the different texts
	 * @return
	 */
	public static double[] generatePosDistribution(ArrayList<String[]> posFullVectors) {

		int size = Constants.allPOS.size();
		
		int[] distributionNumbers = new int[size + 1];
		
		for (int i = 0; i < size + 1; i++) {
			distributionNumbers[i] = 0;
		}
		
		for (String[] vector : posFullVectors) {
			int textLength = vector.length;
			for (int i = 0; i < textLength; i++) {
				boolean test = false;
				for (int j = 0; j < size; j++) {
					if (vector[i].equals(Constants.allPOS.get(j))) {
						distributionNumbers[j] = distributionNumbers[j] + 1;
						test = true;
						break;
					}
				}
				if (!test) {
					distributionNumbers[size] = distributionNumbers[size] + 1;
				}
			}
		}
		
		int total = 0;
		
		for(int i = 0; i<distributionNumbers.length; i++) {
			total = total + distributionNumbers[i];
		}
		
		double[] result = new double[distributionNumbers.length];
		
		for (int i = 0; i<distributionNumbers.length; i++) {
			result[i] = (double) distributionNumbers[i] / (double) total;
		}

		return result;
	}

	/**
	 * Generate the distribution of PoS-tags of a text
	 *
	 * @param posFullVector : the PoS-tags vector of the text
	 * @return an {@link Array} describing the number of occurrences of each PoS-Tag in the text
	 */
	public static double[] generateSimplifiedPosDistribution(String[] posFullVector) {
		
		int size = Constants.simplifiedPOS.size();
		int textLength = posFullVector.length;

		int[] distributionNumbers = new int[size + 1];

		for (int i = 0; i < size + 1; i++) {
			distributionNumbers[i] = 0;
		}

		String[] fullSimplifiedVector = new String[posFullVector.length];

		for (int i = 0; i < posFullVector.length; i++) {
			if(Loader.getSimplifiedPosTagsConverter().get(posFullVector[i]) != null) {
				fullSimplifiedVector[i] = Loader.getSimplifiedPosTagsConverter().get(posFullVector[i]);
			} else {
				fullSimplifiedVector[i] = ".";
			}
			
		}
		
		if (textLength != 0) {
			for (int i = 0; i < textLength; i++) {
				boolean test = false;
				for (int j = 0; j < size; j++) {
					if (posFullVector[i].equals(Constants.simplifiedPOS.get(j))) {
						distributionNumbers[j] = distributionNumbers[j] + 1;
						test = true;
						break;
					}
				}
				if (!test) {
					distributionNumbers[size] = distributionNumbers[size] + 1;
				}
			}
		}
		
		int total = 0;
		
		for(int i = 0; i<distributionNumbers.length; i++) {
			total = total + distributionNumbers[i];
		}
		
		double[] result = new double[distributionNumbers.length];
		

		if (total!=0) {
			for (int i = 0; i < distributionNumbers.length; i++) {
				result[i] = (double) distributionNumbers[i] / (double) total;
			}
		} else {
			for (int i = 0; i < distributionNumbers.length; i++) {
				result[i] = 0;
			} 
		}
		return result;
	}
	
	/**
	 * Generate the distribution of PoS-tags of a set of texts
	 * @param posFullVectors : the PoS vectors of the different texts
	 * @return
	 */
	public static double[] generateSimplifiedPosDistribution(ArrayList<String[]> posFullVectors) {

		int[] distribution = new int[Constants.simplifiedPOS.size() + 1];

		for (int i = 0; i < Constants.simplifiedPOS.size() + 1; i++) {
			distribution[i] = 0;
		}

		int[] distributionNumbers;

		for (String[] posFullVector : posFullVectors) {
			String[] fullSimplifiedVector = new String[posFullVector.length];
			for (int i = 0; i < posFullVector.length; i++) {
				if (Loader.getSimplifiedPosTagsConverter().get(posFullVector[i]) != null) {
					fullSimplifiedVector[i] = Loader.getSimplifiedPosTagsConverter().get(posFullVector[i]);
				} else {
					fullSimplifiedVector[i] = ".";
				}

			}
			int size = Constants.simplifiedPOS.size();
			int textLength = posFullVector.length;
			distributionNumbers = new int[size + 1];
			for (int i = 0; i < size + 1; i++) {
				distributionNumbers[i] = 0;
			}
			if (textLength != 0) {
				for (int i = 0; i < textLength; i++) {
					boolean test = false;
					for (int j = 0; j < size; j++) {
						if (posFullVector[i].equals(Constants.simplifiedPOS.get(j))) {
							distributionNumbers[j] = distributionNumbers[j] + 1;
							test = true;
							break;
						}
					}
					if (!test) {
						distributionNumbers[size] = distributionNumbers[size] + 1;
					}
				}
			} 
			
			for (int i = 0; i < Constants.simplifiedPOS.size() + 1; i++) {
				distribution[i] = distribution[i] + distributionNumbers[i];
			}
			
		}
		
		int total = 0;
		
		for(int i = 0; i<distribution.length; i++) {
			total = total + distribution[i];
		}
		
		double[] result = new double[distribution.length];
		

		if (total!=0) {
			for (int i = 0; i < distribution.length; i++) {
				result[i] = (double) distribution[i] / (double) total;
			}
		} else {
			for (int i = 0; i < distribution.length; i++) {
				result[i] = 0;
			} 
		}
		return result;
	}
	
	// Transitions
	/**
	 * Generate the PoS-Tag transition matrix
	 * [Each cell [i,j] presents the number of times the PoS-Tag j came after the PoS-Tag i]
	 *
	 * @param posFullVector
	 * @return the transition matrix of PoS-Tags
	 */
	public static double[][] generatePosTransitionMatrix(String[] posFullVector) {

		int[][] markovMatrix = new int[Constants.allPOS.size() + 1][Constants.allPOS.size() + 1];

		for (int i = 0; i < Constants.allPOS.size() + 1; i++) {
			for (int j = 0; j < Constants.allPOS.size() + 1; j++) {
				markovMatrix[i][j] = 0;
			}
		}
		
		if (posFullVector.length > 2) {

			for (int k = 0; k < posFullVector.length-1; k++) {
				boolean bigTest = false;
				for (int i = 0; i < Constants.allPOS.size(); i++) {
					if (posFullVector[k].equals(Constants.allPOS.get(i))) {
						bigTest = true;
						boolean smallTest = false;
						for (int j = 0; j < Constants.allPOS.size(); j++) {
								if (Constants.allPOS.get(j).equals(posFullVector[k + 1])) {
									smallTest = true;
									markovMatrix[i][j] = markovMatrix[i][j] + 1;
									break;
								}
								if (smallTest) {
									markovMatrix[i][Constants.allPOS.size()] = markovMatrix[i][Constants.allPOS.size()] + 1;
								}
							}
						}
				}
				if(bigTest) {
					boolean smallTest = false;
					for (int j = 0; j < Constants.allPOS.size(); j++) {
							if (Constants.allPOS.get(j).equals(posFullVector[k + 1])) {
								smallTest = true;
								markovMatrix[Constants.allPOS.size()][j] = markovMatrix[Constants.allPOS.size()][j] + 1;
								break;
							}
							if (smallTest) {
								markovMatrix[Constants.allPOS.size()][Constants.allPOS.size()] = markovMatrix[Constants.allPOS.size()][Constants.allPOS.size()] + 1;
							}
						}
				}
			}
		}
		
		double[][] result = new double[Constants.allPOS.size() + 1][Constants.allPOS.size() + 1];
		
		for(int i = 0; i< markovMatrix.length; i++) {
			int total = 0;
			for (int j=0; j< markovMatrix.length; j++) {
				total = total + markovMatrix[i][j];
			}
			
			if (total!=0) {
				for (int j = 0; j < markovMatrix.length; j++) {
					result[i][j] = (double) markovMatrix[i][j] / (double) total;
				} 
			} else {
				for (int j=0; j< markovMatrix.length; j++) {
					result[i][j] = 0;
				}
			}
			
			
		}

		return result;

	}
	
	/**
	 * Generate the PoS-Tag transition matrix of multiple texts (typically of the same author)
	 * [Each cell [i,j] presents the number of times the PoS-Tag j came after the PoS-Tag i]
	 *
	 * @param posFullVectors
	 * @return the transition matrix of PoS-Tags
	 */
	public static double[][] generatePosTransitionMatrix(ArrayList<String[]> posFullVectors) {

		int[][] fullMarkovMatrix = new int[Constants.allPOS.size() + 1][Constants.allPOS.size() + 1];

		for (int i = 0; i < Constants.allPOS.size() + 1; i++) {
			for (int j = 0; j < Constants.allPOS.size() + 1; j++) {
				fullMarkovMatrix[i][j] = 0;
			}
		}
		
		for (String[] posFullVector : posFullVectors) {
			int[][] markovMatrix = new int[Constants.allPOS.size() + 1][Constants.allPOS.size() + 1];

			for (int i = 0; i < Constants.allPOS.size() + 1; i++) {
				for (int j = 0; j < Constants.allPOS.size() + 1; j++) {
					markovMatrix[i][j] = 0;
				}
			}
			
			if (posFullVector.length > 2) {

				for (int k = 0; k < posFullVector.length-1; k++) {
					boolean bigTest = false;
					for (int i = 0; i < Constants.allPOS.size(); i++) {
						if (posFullVector[k].equals(Constants.allPOS.get(i))) {
							bigTest = true;
							boolean smallTest = false;
							for (int j = 0; j < Constants.allPOS.size(); j++) {
									if (Constants.allPOS.get(j).equals(posFullVector[k + 1])) {
										smallTest = true;
										markovMatrix[i][j] = markovMatrix[i][j] + 1;
										break;
									}
									if (smallTest) {
										markovMatrix[i][Constants.allPOS.size()] = markovMatrix[i][Constants.allPOS.size()] + 1;
									}
								}
							}
					}
					if(bigTest) {
						boolean smallTest = false;
						for (int j = 0; j < Constants.allPOS.size(); j++) {
								if (Constants.allPOS.get(j).equals(posFullVector[k + 1])) {
									smallTest = true;
									markovMatrix[Constants.allPOS.size()][j] = markovMatrix[Constants.allPOS.size()][j] + 1;
									break;
								}
								if (smallTest) {
									markovMatrix[Constants.allPOS.size()][Constants.allPOS.size()] = markovMatrix[Constants.allPOS.size()][Constants.allPOS.size()] + 1;
								}
							}
					}
				}
			}
			
			for (int i = 0; i < Constants.allPOS.size() + 1; i++) {
				for (int j = 0; j < Constants.allPOS.size() + 1; j++) {
					fullMarkovMatrix[i][j] = fullMarkovMatrix[i][j] + markovMatrix[i][j];
				}
			}
			
		}
		
		
		double[][] result = new double[Constants.allPOS.size() + 1][Constants.allPOS.size() + 1];
		
		for(int i = 0; i< fullMarkovMatrix.length; i++) {
			int total = 0;
			for (int j=0; j< fullMarkovMatrix.length; j++) {
				total = total + fullMarkovMatrix[i][j];
			}
			
			if (total != 0) {
				for (int j = 0; j < fullMarkovMatrix.length; j++) {
					result[i][j] = (double) fullMarkovMatrix[i][j] / (double) total;
				} 
			} else {
				for (int j = 0; j < fullMarkovMatrix.length; j++) {
					result[i][j] = 0;
				} 
			}
			
			
		}

		return result;

	}
	
	/**
	 * Generate the simplified Markov Chain of PoS-Transitions
	 * Please refer to {@link Constants} [Constants.simplifiedPOS] to check The list of Simplified PoS-Tags
	 *
	 * @param fullVector : the full vector of original (not Simplified) PoS-Tags
	 * @return
	 */
	public static double[][] generateSimplifiedMarkovChain(String[] fullVector) {

		int[][] markovMatrix = new int[Constants.simplifiedPOS.size() + 1][Constants.simplifiedPOS.size() + 1];

		for (int i = 0; i < Constants.simplifiedPOS.size() + 1; i++) {
			for (int j = 0; j < Constants.simplifiedPOS.size() + 1; j++) {
				markovMatrix[i][j] = 0;
			}
		}

		String[] fullSimplifiedVector = new String[fullVector.length];

		for (int i = 0; i < fullVector.length; i++) {
			if(Loader.getSimplifiedPosTagsConverter().get(fullVector[i]) != null) {
				fullSimplifiedVector[i] = Loader.getSimplifiedPosTagsConverter().get(fullVector[i]);
			} else {
				fullSimplifiedVector[i] = ".";
			}
			
		}

		if (fullSimplifiedVector.length > 2) {

			for (int k = 0; k < fullSimplifiedVector.length-1; k++) {
				boolean bigTest = false;
				for (int i = 0; i < Constants.simplifiedPOS.size(); i++) {
					if (fullSimplifiedVector[k].equals(Constants.simplifiedPOS.get(i))) {
						bigTest = true;
						boolean smallTest = false;
						for (int j = 0; j < Constants.simplifiedPOS.size(); j++) {
								if (Constants.simplifiedPOS.get(j).equals(fullSimplifiedVector[k + 1])) {
									smallTest = true;
									markovMatrix[i][j] = markovMatrix[i][j] + 1;
									break;
								}
								if (smallTest) {
									markovMatrix[i][Constants.simplifiedPOS.size()] = markovMatrix[i][Constants.simplifiedPOS.size()] + 1;
								}
							}
						}
				}
				if(bigTest) {
					boolean smallTest = false;
					for (int j = 0; j < Constants.simplifiedPOS.size(); j++) {
							if (Constants.simplifiedPOS.get(j).equals(fullSimplifiedVector[k + 1])) {
								smallTest = true;
								markovMatrix[Constants.simplifiedPOS.size()][j] = markovMatrix[Constants.simplifiedPOS.size()][j] + 1;
								break;
							}
							if (smallTest) {
								markovMatrix[Constants.simplifiedPOS.size()][Constants.simplifiedPOS.size()] = markovMatrix[Constants.simplifiedPOS.size()][Constants.simplifiedPOS.size()] + 1;
							}
						}
				}
			}

		}
		
		double[][] result = new double[Constants.simplifiedPOS.size() + 1][Constants.simplifiedPOS.size() + 1];
		
		for(int i = 0; i< markovMatrix.length; i++) {
			int total = 0;
			for (int j=0; j< markovMatrix.length; j++) {
				total = total + markovMatrix[i][j];
			}
			
			if (total!=0) {
				for (int j = 0; j < markovMatrix.length; j++) {
					result[i][j] = (double) markovMatrix[i][j] / (double) total;
				} 
			} else {
				for (int j = 0; j < markovMatrix.length; j++) {
					result[i][j] = 0;
				} 
			}
			
			
		}

		return result;

	}
	
	/**
	 * Generate the simplified Markov Chain of PoS-Transitions
	 * Please refer to {@link Constants} [Constants.simplifiedPOS] to check The list of Simplified PoS-Tags
	 *
	 * @param fullVectors : the full vector of original (not Simplified) PoS-Tags
	 * @return
	 */
	public static double[][] generateSimplifiedMarkovChain(ArrayList<String[]> fullVectors) {
		
		int[][] fullMarkovMatrix = new int[Constants.simplifiedPOS.size() + 1][Constants.simplifiedPOS.size() + 1];

		for (int i = 0; i < Constants.simplifiedPOS.size() + 1; i++) {
			for (int j = 0; j < Constants.simplifiedPOS.size() + 1; j++) {
				fullMarkovMatrix[i][j] = 0;
			}
		}

		for (String[] fullVector : fullVectors) {
			
			int[][] markovMatrix = new int[Constants.simplifiedPOS.size() + 1][Constants.simplifiedPOS.size() + 1];
			
			for (int i = 0; i < Constants.simplifiedPOS.size() + 1; i++) {
				for (int j = 0; j < Constants.simplifiedPOS.size() + 1; j++) {
					markovMatrix[i][j] = 0;
				}
			}
			
			
			String[] fullSimplifiedVector = new String[fullVector.length];
			for (int i = 0; i < fullVector.length; i++) {
				if (Loader.getSimplifiedPosTagsConverter().get(fullVector[i]) != null) {
					fullSimplifiedVector[i] = Loader.getSimplifiedPosTagsConverter().get(fullVector[i]);
				} else {
					fullSimplifiedVector[i] = ".";
				}

			}
			
			if (fullSimplifiedVector.length > 2) {

				for (int k = 0; k < fullSimplifiedVector.length - 1; k++) {
					boolean bigTest = false;
					for (int i = 0; i < Constants.simplifiedPOS.size(); i++) {
						if (fullSimplifiedVector[k].equals(Constants.simplifiedPOS.get(i))) {
							bigTest = true;
							boolean smallTest = false;
							for (int j = 0; j < Constants.simplifiedPOS.size(); j++) {
								if (Constants.simplifiedPOS.get(j).equals(fullSimplifiedVector[k + 1])) {
									smallTest = true;
									markovMatrix[i][j] = markovMatrix[i][j] + 1;
									break;
								}
								if (smallTest) {
									markovMatrix[i][Constants.simplifiedPOS.size()] = markovMatrix[i][Constants.simplifiedPOS.size()]
											+ 1;
								}
							}
						}
					}
					if (bigTest) {
						boolean smallTest = false;
						for (int j = 0; j < Constants.simplifiedPOS.size(); j++) {
							if (Constants.simplifiedPOS.get(j).equals(fullSimplifiedVector[k + 1])) {
								smallTest = true;
								markovMatrix[Constants.simplifiedPOS.size()][j] = markovMatrix[Constants.simplifiedPOS.size()][j]
										+ 1;
								break;
							}
							if (smallTest) {
								markovMatrix[Constants.simplifiedPOS.size()][Constants.simplifiedPOS.size()] = markovMatrix[Constants.simplifiedPOS.size()][Constants.simplifiedPOS.size()]
										+ 1;
							}
						}
					}
				}

			}
			
			for (int i = 0; i < Constants.simplifiedPOS.size() + 1; i++) {
				for (int j = 0; j < Constants.simplifiedPOS.size() + 1; j++) {
					fullMarkovMatrix[i][j] = fullMarkovMatrix[i][j] + markovMatrix[i][j];
				}
			}
		}
		
		double[][] result = new double[Constants.simplifiedPOS.size() + 1][Constants.simplifiedPOS.size() + 1];
		
		for(int i = 0; i< fullMarkovMatrix.length; i++) {
			int total = 0;
			for (int j=0; j< fullMarkovMatrix.length; j++) {
				total = total + fullMarkovMatrix[i][j];
			}
			
			if (total!=0) {
				for (int j = 0; j < fullMarkovMatrix.length; j++) {
					result[i][j] = (double) fullMarkovMatrix[i][j] / (double) total;
				} 
			} else {
				for (int j = 0; j < fullMarkovMatrix.length; j++) {
					result[i][j] = 0;
				} 
			}
		}

		return result;
	}
	

}

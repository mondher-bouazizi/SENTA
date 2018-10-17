package backend.processor;

import java.text.NumberFormat;
import java.text.ParsePosition;

import backend.spellingcorrectors.LevenshteinCorrector;
import backend.spellingcorrectors.NorvigCorrector;
import commons.constants.Constants;
import main.start.Loader;
import opennlp.tools.util.Span;


public class WordProcessing {

	
	//=================================//
	//            CHECKERS             //
	//=================================//

	/**
	 * Check whether a token is a punctuation and/or numerical
	 *
	 * @param token : the token to check
	 * @return {@value true} if it is a punctuation, {@value false} otherwise
	 */
	public static boolean isPunctuation(String token) {

		if (isHashtag(token) || isEmail(token) || isEmoticon(token) || isNumeric(token) || isTag(token)) {
			return false;
		}

		for (int i = 0; i < Constants.punctuationMarks.size(); i++) {
			if (token.contains(Constants.punctuationMarks.get(i))) {
				return true;
			}
		}

		return false;

	}

	/**
	 * Check whether a token is a Hashtag
	 *
	 * @param token : the token to check
	 * @return {@value true} if it is a Hashtag, {@value false} otherwise
	 */
	public static boolean isHashtag(String token) {

		boolean verif = false;
		if (token.length() > 1) {
			if (token.charAt(0) == '#') verif = true;
		}

		return verif;

	}

	/**
	 * Check whether a token is an email address
	 *
	 * @param token : the token to check
	 * @return {@value true} if it is an email address, {@value false} otherwise
	 */
	public static boolean isEmail(String token) {

		if (token.length() != 0) {
			if (token.charAt(0) != '@' && token.contains("@")) {
				for (String key : Loader.getDomainNames().keySet()) {
					String s = "." + key;
					if (token.endsWith(s)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Check whether a token is a Tag
	 *
	 * @param token : the token to check
	 * @return {@value true} if it is a Tag, {@value false} otherwise
	 */
	public static boolean isTag(String token) {


		if (token.length() != 0) {
			if (token.charAt(0) == '@' && token.length() > 1) {
				return true;
			}
		}

		return false;

	}

	/**
	 * Check whether a token is an Emoticon
	 *
	 * @param token : the token to check
	 * @return {@value true} if it is a Tag, {@value false} otherwise
	 */
	public static boolean isEmoticon(String token) {

		if (token.length() != 0) {
			if (Loader.getEmoticonMeanings().containsKey(token)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Check whether a token is a URL
	 *
	 * @param token : the token to check
	 * @return {@value true} if it is a URL, {@value false} otherwise
	 */
	public static boolean isURL(String token) {

		boolean verif = false;
		if (token.length() != 0) {
			if (token.toLowerCase().startsWith("http://") || token.toLowerCase().startsWith("https://"))
				verif = true;
		}

		return verif;

	}

	/**
	 * Check whether a token is a slang word
	 *
	 * @param token : the token to check
	 * @return {@value true} if it is a slang word, {@value false} otherwise
	 */
	public static boolean isSlang(String token) {

		if (token.length() != 0) {
			// if (!Loader.getEnglishWords().containsKey(token.toLowerCase())) {
			if (Loader.getSlangMeanings().containsKey(token.toLowerCase())) {
				return true;
				}
			// }
		}

		return false;
	}

	/**
	 * Check whether a token is all capitalized
	 * [ATTENTION: to apply after checking that the token is actually a word]
	 *
	 * @param token : the token to check
	 * @return {@value true} if it is all capitalized, {@value false} otherwise
	 */
	public static boolean isCapitalized(String token) {

		if (isPunctuation(token) || isEmoticon(token) || isNumeric(token)) {
			return false;
		}

		if (token.equals(token.toUpperCase())) {
			if (token.length() > 1) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Check whether a token is a negation word
	 * [Negation words are : "not", "no", "never", "none", "nor", "non", "n't"]
	 *
	 * @param token : the token to check
	 * @return {@value true} if it is a negation word, {@value false} otherwise
	 */
	public static boolean isNegationWord(String token) {

		boolean found = false;

		if (Constants.negationWords.contains(token.toLowerCase())) found = true;

		return found;
	}

	/**
	 * Check whether a token is a boosting word
	 * [Boosing words are words like : "really", "extremely"]
	 *
	 * @param token : the token to check
	 * @return {@value true} if it is a boosing word, {@value false} otherwise
	 */
	public static boolean isBooster(String token) {

		boolean found = false;

		if (Loader.getBoosters().containsKey(token.toLowerCase())) found = true;

		return found;
	}

	/**
	 * Check whether a token is misspelled
	 *
	 * @param token : the token to check
	 * @return {@value true} if the word is misspelled, {@value false} otherwise
	 */
	public static boolean isMisspelled(String token) {

		if (isPunctuation(token) || isEmail(token) || isNumeric(token) || isHashtag(token) || isTag(token) || isEmoticon(token)
				|| isURL(token) || isLaughter(token) || isName(token)) {
			return false;
		} else {
			if (Loader.getEnglishWords().containsKey(token.toLowerCase())) {
				return false;
			}
		}

		return true;

	}

	/**
	 * Check whether a word is a relevant in a sentence
	 *
	 * @param token : the token to check
	 * @return {@value true} if it is relevant, {@value false} otherwise
	 */
	public static boolean isRelevant(String token, String posTag) {

		if (Loader.getStopCache().containsKey(token.toLowerCase())) {
			return false;
		}

		if (Loader.getRelvCache().containsKey(posTag)) {
			return true;
		}

		return false;
	}

	/**
	 * Check whether a word is a stop word
	 *
	 * @param token : the token to check
	 * @return {@value true} if it is a stop word, {@value false} otherwise
	 */
	public static boolean isStopWord(String wordToCheck) {
		if (Loader.getStopCache().containsKey(wordToCheck.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * Check whether a token is numeric
	 *
	 * @param token : the token to check
	 * @return {@value true} if the token is numeric, {@value false} otherwise
	 */
	public static boolean isNumeric(String token) {
		NumberFormat formatter = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(token, pos);
		return token.length() == pos.getIndex();
	}

	/**
	 * Check whether a word contains a repeated character [more than 3 times consecutively]
	 *
	 * @param token
	 * @return {@value true} if the word contains a character repeated more than 3 times consecutively
	 */
	public static boolean containsRepitition(String token) {

		token = token.toLowerCase().replaceAll("[^a-zA-Z]", "").trim();

		char[] charArray = token.toCharArray();

		if (charArray.length < 3) {
			return false;
		} else {
			for (int i = 0; i < charArray.length - 2; i++) {
				if (charArray[i] == charArray[i + 1] && charArray[i] == charArray[i + 2]) return true;
			}
		}

		return false;
	}

	/**
	 * Check whether a word is a laughter
	 *
	 * @param token : the token to check
	 * @return {@value true} if it is a laughter, {@value false} otherwise
	 */
	public static boolean isLaughter(String wordToCheck) {

		if (wordToCheck.toLowerCase().contains("haha") || wordToCheck.toLowerCase().contains("hihi") ||
			wordToCheck.toLowerCase().contains("huhu") || wordToCheck.toLowerCase().contains("hhh"))
			return true;
		if (Loader.getLaughExpressions().containsKey(wordToCheck.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * Check whether a word is common
	 * [Here we base our decision on the file "bigText.txt" we have. In this file, all the words have at least 5 occ.]
	 *
	 * @param wordToCheck : the word to check
	 * @return {@value true} if the word is common, {@value false} otherwise
	 */
	public static boolean isCommon(String wordToCheck) {
		if (Loader.getWordsProbablities().get(wordToCheck.toLowerCase()) == null) return true;
		if (Loader.getWordsProbablities().get(wordToCheck.toLowerCase()) <= 5) return false;
		return true;
	}

	/**
	 * Check whether a word is a Proper Noun
	 *
	 * @param token : the word to check
	 * @return {@value true} if the word is a proper noun, {@value false} otherwise
	 */
	public static boolean isName(String token) {

		String[] sentence = new String[]{token};

		Span nameSpans[] = Loader.getNameFinder().find(sentence);
		if (nameSpans.length != 0) {
			return true;
		} else
			return false;
	}

	
	//=================================//
	//         HASHTAG-RELATED         //
	//=================================//

	/**
	 * Remove the Hashtag symbol "#"
	 * [a verification is done before, if the word is not a hashtag, it is returned as it is]
	 *
	 * @param hashtag : the hashtag of which the symbol is to be removed
	 * @return the hashtag without the symbol "#"
	 */
	public static String removeHashtagSymbol(String hashtag) {
		if (isHashtag(hashtag) == true)
			hashtag = hashtag.substring(1, hashtag.length());
		return hashtag;
	}

	/**
	 * Remove the Hashtag and returns an empty String
	 * [a verification is done before, if the word is not a hashtag, it is returned as it is]
	 *
	 * @param hashtag : the hashtag to be removed
	 * @return an empty string if the expression is actually a hashtag, the expression itself otherwise
	 */
	public static String removeHashtag(String hashtag) {
		if (isHashtag(hashtag) == true)
			hashtag = "";
		return hashtag;

	}

	/**
	 * Replace a Hashtag by the expression {@value "AT_HASHTAG"} if the expression given is really a hashtag
	 * [a verification is done before, if the word is not a hashtag, it is returned as it is]
	 *
	 * @param hashtag : the hashtag to replace
	 * @return {@value "AT_HASHTAG"} if the expression is actually a hashtag, the expression itself otherwise
	 */
	public static String replaceHashtag(String hashtag) {
		if (isHashtag(hashtag) == true)
			hashtag = "AT_HASHTAG";
		return hashtag;
	}

	/**
	 * Decompose the Hashtag into the expression it means, and return the full expression
	 * [e.g. "#nomorewar" -> "no more war"]
	 *
	 * @param hashtag : the hashtag to decompose
	 * @return the expression meant by the hashtag if it makes sense, the hashtag as it is otherwise
	 */
	public static String decomposeHashtag(String hashtag) {
		HashtagProcessing hashTagPro = new HashtagProcessing();
		if (isHashtag(hashtag) == true) {
			hashtag = removeHashtagSymbol(hashtag);
			if (hashTagPro.isHashtagCorrect(hashtag)) {
				hashtag = hashTagPro.convertHashtagToString(hashtag);
			}
		}
		return hashtag;
	}

	/**
	 * Replace a Hashtag by an expression specified by the user if the expression given is really a hashtag
	 * [a verification is done before, if the word is not a hashtag, it is returned as it is]
	 *
	 * @param hashtag     : the hashtag to replace
	 * @param userDefined : the expression to replace the Hashtag
	 * @return the expression specified by the user if the expression is actually a hashtag, the expression itself otherwise
	 */
	public static String replaceHashtag(String hashtag, String userDefined) {
		if (isHashtag(hashtag) == true)
			hashtag = userDefined;
		return hashtag;
	}

	
	//=================================//
	//           TAG-RELATED           //
	//=================================//
	
	/**
	 * Remove the Tag symbol "@"
	 * [a verification is done before, if the word is not a Tag, it is returned as it is]
	 *
	 * @param tag : the Tag of which the symbol is to be removed
	 * @return the tag without the symbol "@"
	 */
	public static String removeTagSymbol(String tag) {
		if (isTag(tag) == true)
			tag = tag.substring(1, tag.length());
		return tag;
	}

	/**
	 * Remove the Tag and returns an empty String
	 * [a verification is done before, if the word is not a tag, it is returned as it is]
	 *
	 * @param tag : the tag to be removed
	 * @return an empty String if the expression is actually a tag, the expression itself otherwise
	 */
	public static String removeTag(String tag) {
		if (isTag(tag) == true)
			tag = "";
		return tag;
	}

	/**
	 * Replace a Tag by the expression {@value "TO_USER"} if the expression given is really a tag
	 * [a verification is done before, if the word is not a tag, it is returned as it is]
	 *
	 * @param tag : the tag to replace
	 * @return {@value "AT_USER"} if the expression is actually a tag, the expression itself otherwise
	 */
	public static String replaceTag(String tag) {
		if (isTag(tag) == true)
			tag = "TO_USER";
		return tag;
	}

	/**
	 * Replace a Tag by an expression specified by the user if the expression given is really a tag
	 * [a verification is done before, if the word is not a tag, it is returned as it is]
	 *
	 * @param tag : the tag to replace
	 * @return the expression specified by the user if the expression is actually a tag, the expression itself otherwise
	 */
	public static String replaceTag(String tag, String userDefined) {
		if (isTag(tag) == true)
			tag = userDefined;
		return tag;
	}
	
	
	//=================================//
	//        SENTIMENT-RELATED        //
	//=================================//
	
	/**
	 * Check whether a word is positive
	 *
	 * @param token  : the word to verify
	 * @param index : whether the word is preceded by a negation word
	 * @return {@value true} if the word is Positive, {@value false} if it is not
	 */
	public static boolean isPositive(String token, boolean index) {

		if (token.length() == -1 || token.length() == 0) {
			return false;
		}

		if (!index) {
			if (Loader.getPositiveCache().containsKey(token.toLowerCase())) {
				return true;
			}
		} else {
			if (Loader.getNegativeCache().containsKey(token.toLowerCase())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Check whether a word is negative
	 *
	 * @param token  : the word to verify
	 * @param index : whether the word is preceded by a negation word
	 * @return {@value true} if the word is negative, {@value false} if it is not
	 */
	public static boolean isNegative(String token, boolean index) {

		if (token.length() == -1 || token.length() == 0) {
			return false;
		}

		if (!index) {
			if (Loader.getNegativeCache().containsKey(token.toLowerCase())) {
				return true;
			}
		} else {
			if (Loader.getPositiveCache().containsKey(token.toLowerCase())) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Returns the sentiment score of a word, taking into account the negation index, and the booster
	 * @param token
	 * @param index
	 * @return
	 */
	public static double getSentimentScore(String token, boolean index, double booster) {
		if (Loader.getSentimentalWords().containsKey(token)) {
			if (index) {
				return (double) -Loader.getSentimentalWords().get(token) * booster;
			}
			return (double) Loader.getSentimentalWords().get(token) * booster;
		}
		else {
			return 0;
		}
	}
	
	//=================================//
	//           URL-RELATED           //
	//=================================//
	
	/**
	 * Remove the URL and return an empty String
	 * [The expression is checked first if it is a URL, if not, it is returned as it is]
	 *
	 * @param url : the URL to remove
	 * @return {@value ""} if the expression is actually a URL, the expression itself otherwise
	 */
	public static String removeURL(String url) {
		if (isURL(url) == true)
			url = "";
		return url;
	}

	/**
	 * Replace the URL by the expression "AT_URL"
	 * [The expression is checked first if it is a URL, if not, it is returned as it is]
	 *
	 * @param url : the URL to replace
	 * @return {@value "AT_URL"} if the expression is actually a URL, the expression itself otherwise
	 */
	public static String replaceURL(String url) {
		if (isURL(url) == true)
			url = "AT_URL";
		return url;
	}

	/**
	 * Replace a URL by an expression specified by the user
	 * [A verification is done before, if the word is not a URL, it is returned as it is]
	 *
	 * @param url         : the URL to replace
	 * @param userDefined : the expression to replace the URL
	 * @return the expression specified by the user if the expression is actually a URL, the expression itself otherwise
	 */
	public static String replaceURL(String url, String userDefined) {
		if (isURL(url) == true)
			url = userDefined;
		return url;
	}

	
	//=================================//
	//      EMAIL ADDRESS-RELATED      //
	//=================================//

	/**
	 * Remove the email address and returns an empty String if the token is really an email address, the token itself if the token is not a slang
	 * [The expression is checked first. If it is an email address, if not, it is returned as it is]
	 * @param email : the email address to be removed
	 * @return an empty string if the expression is actually an email address, the expression itself otherwise
	 */
	public static String removeEmail(String email) {
		if (isEmail(email) == true)
			email = "";
		return email;
	}

	/**
	 * Replace the email address by an expression defined by the user
	 * [The expression is checked first. If it is an email address, if not, it is returned as it is]
	 * @param email : the email address
	 * @return {@value "TO_EMAIL"} if the expression is actually an email address, the expression itself otherwise
	 */
	public static String replaceEmail(String email) {
		if (isEmail(email) == true)
			email = "TO_EMAIL";
		return email;
	}

	/**
	 * Replace the email address by an expression defined by the user
	 * [The expression is checked first. If it is an email address, if not, it is returned as it is]
	 * @param email       : the email address
	 * @param userDefined : the expression to replace the email address
	 * @return the expression specified by the user if the expression is actually an email address, the expression itself otherwise
	 */
	public static String replaceEmail(String email, String userDefined) {
		if (isEmail(email) == true)
			email = userDefined;
		return email;
	}
	
	
	//=================================//
	//       SLANG WORDS-RELATED       //
	//=================================//

	/**
	 * Remove the slang and return an empty String if the token is really a slang
	 * [The expression is checked first. If it is a slang, if not, it is returned as it is]
	 * @param slang : the slang to remove
	 * @return an empty string if the token is really a slang, the token itself if the token is not a slang
	 */
	public static String removeSlang(String slang) {
		if (isSlang(slang)) {
			slang = "";
		}
		return slang;
	}

	/**
	 * Replace the slang word by the expression it means, and return the full expression [e.g. "wth" -> "what the hell"]
	 * [The expression is checked first. If it is a slang, if not, it is returned as it is]
	 * @param slang : the hashtag to decompose
	 * @return the expression meant by the slang word if it makes sense, the slang word as it is otherwise
	 */
	public static String replaceSlang(String slang) {
		if (isSlang(slang)) {
			slang = Loader.getSlangMeanings().get(slang.toLowerCase());
		}
		return slang;
	}


	//=================================//
	//        EMOTICONS-RELATED        //
	//=================================//
	
	/**
	 * Remove the emoticon and returns an empty string
	 * [The expression is checked first. If it is an emoticon, if not, it is returned as it is]
	 * @param emoticon : the emoticon to be removed
	 * @return an empty String
	 */
	public static String removeEmoticon(String emoticon) {
		if (isEmoticon(emoticon) == true) {
			emoticon = "";
		}
		return emoticon;
	}

	
	//=================================//
	//              OTHERS             //
	//=================================//
	
	/**
	 * Replace the underscores of an expression by a white space [e.g. "who_are_you" -> "who are you"]
	 * @param token
	 * @return
	 */
	public static String replaceUnderscore(String token) {
		if (!isURL(token) && !isEmail(token) && !isTag(token))
			token = token.replace('_', ' ');
		return token;
	}

	/**
	 * Get the lemma of a word
	 * @param token  : the original word
	 * @param posTag : the PoS-Tag of the word
	 * @return the lemma of the word
	 */
	public static String lemmatize(String token, String posTag) {
		return Loader.getLemmatizer().lemmatize(token, posTag);
	}

	/**
	 * Get the simplified PoS-Tag of a word out of its PoS-Tag
	 * @param posTag : the PoS-Tag of the word
	 * @return the simplified PoS-Tag
	 */
	public static String getSiplifiedPosTag(String posTag) {
		if (Loader.getSimplifiedPosTagsConverter().containsKey(posTag)) {
			return Loader.getSimplifiedPosTagsConverter().get(posTag);
		} else {
			return ".";
		}
		
	}
	
	public enum Corrector {
		NORVIG, LEVENSHTEIN
	}
	
	/**
	 * Correct a misspelled word
	 * @param word : the word to correct
	 * @return the word after correcting it using the Norvig Corrector
	 */
	public static String correctWord(String word, Corrector corrector) {
		
		if (!isMisspelled(word)) {
			return word;
		}
		String returnedWord = "";
		
		char[] lettersOfWord = word.toCharArray();

		for (int i = 0; i < lettersOfWord.length; i++) {
			if (i < lettersOfWord.length - 2) {
				if (lettersOfWord[i] != lettersOfWord[i + 1] && lettersOfWord[i] != lettersOfWord[i + 2]) {
					returnedWord = returnedWord + lettersOfWord[i];
				}
			} else
				returnedWord = returnedWord + lettersOfWord[i];
		}

		if (corrector.equals(Corrector.NORVIG)) {
			return NorvigCorrector.correct(returnedWord);
		} else if (corrector.equals(Corrector.LEVENSHTEIN)) {
			return LevenshteinCorrector.correct(returnedWord);
		} else {
			//Default corrector = norvig
			return NorvigCorrector.correct(returnedWord);
		}
	}


}
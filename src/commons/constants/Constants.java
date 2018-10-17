package commons.constants;

import java.util.ArrayList;
import java.util.Arrays;

public class Constants {
	
	//=================================//
	//             FOLDERS             //
	//=================================//
	
	// Text files directories
	public static final String TEXTDIRECTORY = "src\\resources\\texts\\";
	public static final String DICTIONARIESDIRECTORY = TEXTDIRECTORY + "\\dictionaries\\";
	
	// Image files directories
	public static final String IMAGEDIRECTORY = "src\\resources\\images\\";
	
	// Help files directories
	public static final String HELPDIRECTORY = "src\\resources\\help\\";
	
	
	//=================================//
	//              FILES              //
	//=================================//
	
	// Text files
	public static final String RECENTFILESFILE = TEXTDIRECTORY + "recent_files.txt";
	
	// Dictionaries
	public static final String emoticonsFile = DICTIONARIESDIRECTORY + "Emoticon_Dictionary.txt";
	public static final String slangsFile = DICTIONARIESDIRECTORY + "SlangDictionary.txt";

	public static final String englishDictionaryFile = DICTIONARIESDIRECTORY + "English_Words.txt";
	public static final String bigTextFile = DICTIONARIESDIRECTORY + "Big_Text.txt";

	public static final String stopWordsFile = DICTIONARIESDIRECTORY + "Stop_Words.txt";
	public static final String wordsWithApastropheeFile = DICTIONARIESDIRECTORY + "Words_With_Apostrophe.txt";
	public static final String laughExpressionsFile = DICTIONARIESDIRECTORY + "LaughExpressions.txt";

	public static final String posSimplifiedFile = DICTIONARIESDIRECTORY + "Simplified_PoS.txt";

	public static final String boostersFile = DICTIONARIESDIRECTORY + "Boosters.txt";
	public static final String seedWordsFile = DICTIONARIESDIRECTORY + "Seed_Words.txt";
	public static final String emotionalWordsFile = DICTIONARIESDIRECTORY + "Emotional_Words.txt"; // This file contains the words + sent. class + scores
	public static final String sentimentalWordsFile = DICTIONARIESDIRECTORY + "Sentimental_Words.txt"; // This file contains the words + scores
	
	public static final String domainNamesFile = DICTIONARIESDIRECTORY + "WorldDomainNames.txt";
	
	public static final String COEFFICIENTS = DICTIONARIESDIRECTORY + "Coefficients.txt";
	
	
	//Model files (for OpenNLP)
	public static final String MODELSDIRECTORY = "src\\resources\\models\\";
	
	public static final String sentenceDetectorModel = MODELSDIRECTORY + "en-sent.bin";
	public static final String tokenizerModel = MODELSDIRECTORY + "en-token.bin";
	public static final String taggerModel = MODELSDIRECTORY + "en-pos-maxent.bin";
	public static final String nameFinderModel = MODELSDIRECTORY + "en-ner-person.bin";
	public static final String lemmatizerModel = DICTIONARIESDIRECTORY + "en-lemmatizer.dict"; // This is not a model file but rather a dictionary
	
	
	// Image files
	public static final String TwitterImage = IMAGEDIRECTORY + "twitter.png";
	public static final String aboutImage = IMAGEDIRECTORY + "about.png";
	public static final String checkboxImage = IMAGEDIRECTORY + "checkbox.png";
	public static final String emoticonsImage = IMAGEDIRECTORY + "emoticons.png";
	
	public static final String TwitterIcon = IMAGEDIRECTORY + "twitter-icon.png";
	public static final String saveIcon = IMAGEDIRECTORY + "save-icon.png";
	public static final String settingsIcon = IMAGEDIRECTORY + "settings-icon.png";
	
	// help files
	public static final String helpFile = HELPDIRECTORY + "helpFile.chm";

	
	//=================================//
	//          ENUMERATIONS           //
	//=================================//
	
	// Type of project
	public enum TypeOfProject {
		OPEN_PROJECT, OPEN_FEATURES_FILE, START_NEW_PROJECT
		}
	
	
	//=================================//
	//          STATIC LISTS           //
	//=================================//
	
	// List of families of classes:
	
	public static ArrayList<String> mainClasses = new ArrayList<String>(Arrays.asList("POSITIVE", "NEGATIVE", "NEUTRAL"));

	// List of all the PoS tags
	public static ArrayList<String> allPOS = new ArrayList<String>(Arrays.asList("CC", "CD", "DT", "EX", "FW", "IN", "JJ", "JJR",
			"JJS", "LS", "MD", "NN", "NNS", "NNP", "NNPS", "PDT", "POS", "PRP", "PRP$", "RB", "RBR", "RBS", "RP", "SYM", "TO", "UH",
			"VB", "VBD", "VBG", "VBN", "VBP", "VBZ", "WDT", "WP", "WP$", "WRB"));

	// List of the relevant PoS tags (Nouns, verbs, adjectives and adverbs)
	public static ArrayList<String> relevantPOS = new ArrayList<String>(Arrays.asList("NN", "NNS", "RBS", "VB", "VBD", "VBG", "VBN",
			"VBP", "VBZ", "JJ", "JJR", "JJS", "RB", "RBR", "RBS"));

	// List of simplified PoS
	public static ArrayList<String> simplifiedPOS = new ArrayList<String>(Arrays.asList("COORDCONJUNCTION", "CARDINAL", "DETERMINER",
			"EXISTTHERE", "FOREIGNWORD", "PREPOSITION", "ADJECTIVE", "LISTMARKER", "MODAL", "NOUN", "PREDETERMINER", "POSSESSIVEEND",
			"PRONOUN", "ADVERB", "PARTICLE", "SYMBOL", "TO", "INTERJECTION", "VERB", "WHDETERMINER"));

	// List of punctuation marks
	public static ArrayList<String> punctuationMarks = new ArrayList<String>(Arrays.asList(".", ",", "?", ";", ":", "/", "[", "]",
			"(", ")", "{", "}", "<", ">", "!", "$", "\\", "^", "%", "+", "-", "~", "|", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
	
	public static ArrayList<String> symbols = new ArrayList<String>(Arrays.asList("#", "$", "@", "%", "*", "+", "/", "&"));

	public static ArrayList<String> restrictedPunctuationMarks  = new ArrayList<String>(Arrays.asList(".", ",", "?", "!", ";", ":",
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

	// List of Negation Words
	public static ArrayList<String> negationWords = new ArrayList<String>(Arrays.asList("not", "no", "never", "none", "nor", "non", "n\'t", "nt", "dont"));

	// List of prounouns by group (set to lower case on purpose)
	public static ArrayList<String> pronounsGroupI = new ArrayList<String>(Arrays.asList("i", "me", "my", "mine", "you", "your", "yours", "we", "us", "our", "ours"));
	
	// List of opinion words
	public static ArrayList<String> opinionWords = new ArrayList<String>(Arrays.asList("think", "believe", "feel", "opinion", "favorite", 
			"belief", "convinced", "besides", "consequently", "clearly", "obviously", "terrible", "unfair", "worthwhile", "enjoyable", 
			"disgusting", "definitely", "horrible", "good", "bad", "better", "worse", "best", "worst", "inferior", "superior", "always", 
			"never", "awful", "wonderful", "beautiful", "ugly", "oppose", "support", "against"));
	
	// List of opinion expressions
	public static ArrayList<String> opinionExpressions = new ArrayList<String>(Arrays.asList("from my point of view", "based on what i know", 
			"i am convinced", "speaking for myself", "as far as i am concerned", "i know you will have to ", "agree that", 
			"i am confident that", "first of all", "after that", "additionally", "equally important ", "in conclusion", "for all", 
			"furthermore", "in addition", "for these reasons", "finally"));
	
	// List of uncertainty words
	public static ArrayList<String> uncertaintyWords = new ArrayList<String>(Arrays.asList("perhaps", "maybe", "probably", "probable", 
			"possibly", "possible", "apparently", "imagine", "suppose", "guess", "suggest", "think", "likely", "unlikely"));
	
	// List of uncertainty expressions
	public static ArrayList<String> uncertaintyExpressions = new ArrayList<String>(Arrays.asList("as far as i am aware", 
			"as far as i know", "to the best of my knowledge", "not to my knowledge"));
	
	// List of the most common sarcastic patterns
	public static String[][] sarcasticPatterns = new String[][]{
			{"love", "be"}, {"like", "be"}, {"like", "be"},
			{"love", "how"}, {"like", "how"}, {"enjoy", "how"},
			{"love", "when"}, {"like", "when"}, {"enjoy", "when"},
			{"enjoy", "PRONOUN", "when"}, {"love", "PRONOUN", "when"},
			{"like", "PRONOUN", "when"}, {"love", "PRONOUN", "how"},
			{"like", "PRONOUN", "how"}, {"enjoy", "PRONOUN", "how"},
			{"PRONOUN", "be", "amazing"},
			{"with", "the", "greatest", "NOUN"},
			{"thank", "PRONOUN", "for", "the", "NOUN"},
			// { "that", "MODAL", "be", "NOUN" },
			{"that", "be", "ADVERB", "funny"}, {"PRONOUN", "be", "great"},
			{"good", "NOUN", "with", "that"},
			// { "do", "not", "worry", "about", "PRONOUN" },
			{"PRONOUN", "MODAL", "make", "a", "NOUN"},
			// { "PRONOUN", "think", "that", "be", "PRONOUN", "NOUN" },
			// { "PRONOUN", "NOUN", "MODAL", "be", "ADVERB", "proud" },
			// { "PRONOUN", "be", "okay", "to", "feel", "what", "PRONOUN" },
			{"PRONOUN", "remind", "PRONOUN", "of", "PRONOUN"},
			// { "have", "a", "weird", "NOUN", "of", "NOUN" }
	};

	// List of positive sentiment classes
	public static ArrayList<String> positiveClasses = new ArrayList<String>(Arrays.asList("POSITIVE", "HAPPINESS", "LOVE", "ENTHUSIASM", "FUN", "RELIEF", "RESPECT", "CALM", "CLEAN"));
	
	// List of negative sentiment classes
	public static ArrayList<String> negativeClasses = new ArrayList<String>(Arrays.asList("NEGATIVE", "SADNESS", "HATE", "INDIFFERENCE", "BOREDOM", "WORRY", "DISRESPECT", "ANGER", "JEALOUSY", "OFFENSIVE"));
	
	

}

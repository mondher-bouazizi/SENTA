package main.items;

import java.util.ArrayList;
import java.util.HashMap;

import commons.constants.Commons;

public class Tweet implements Comparable<Tweet> {
	
	//======================================//
	//            STATIC FIELDS             //
	//======================================//
	
	protected static String additionalField1Title;
	protected static String additionalField2Title;
	protected static String additionalField3Title;
	protected static String additionalField4Title;
	
	
	//======================================//
	//           BASIC COMPONENTS           //
	//======================================//
	
	// Basic components Part 1
	protected String id;
	protected String userName;
	protected String text;
	protected String preprocessedText;
	protected String tweetClass;				// For classification
	protected ArrayList<String> tweetClasses;	// For quantification
	
	protected String additionalField1;
	protected String additionalField2;
	protected String additionalField3;
	protected String additionalField4;
	
	// Basic components Part 2
	protected String[] tokens;
	protected String[] lemmas;
	protected String[] tags;
	protected String[] simplifiedTags;
	protected boolean[] polaritySwitcher;
	protected double[] coefficients;
	
	protected String[] tokensPolarity;
	protected double[] tokensScore;
	
	
	//======================================//
	//         SENTIMENT FEATURES           //
	//======================================//
	
	protected ArrayList<String> positiveWords;
	protected ArrayList<String> negativeWords;
	
	protected int numberOfPositiveWords;
	protected int numberOfNegativeWords;
	protected int numberOfHighlyEmoPositiveWords;
	protected int numberOfHighlyEmoNegativeWords;
	protected int numberOfCapitalizedPositiveWords;
	protected int numberOfCapitalizedNegativeWords;
	protected double ratioOfEmotionalWords;
	
	protected int numberOfPositiveEmoticons;
	protected int numberOfNegativeEmoticons;
	protected int numberOfNeutralEmoticons;
	protected int numberOfJokingEmoticons;
	
	protected int numberOfPositiveSlangs;
	protected int numberOfNegativeSlangs;
	
	protected int numberOfPositiveHashtags;
	protected int numberOfNegativeHashtags;
	
	protected boolean contrastWordsVsWords;
	protected boolean contrastWordsVsHashtags;
	protected boolean contrastWordsVsEmoticons;
	protected boolean contrastHashtagsVsHashtags;
	protected boolean contrastHashtagsVsEmoticons;
	
	
	//======================================//
	//        PUNCTUATION FEATURES          //
	//======================================//
	
	protected int numberOfDots;
	protected int numberOfCommas;
	protected int numberOfSemicolons;
	protected int numberOfExclamationMarks;
	protected int numberOfQuestionMarks;

	protected int numberOfParentheses;
	protected int numberOfBrackets;
	protected int numberOfBraces;
	
	protected int numberOfApostrophes;
	protected int numberOfQuotationMarks;
	
	protected int totalNumberOfCharacters;
	protected int totalNumberOfWords;
	protected int totalNumberOfSentences;
	protected double averageNumberOfCharactersPerSentence;
	protected double averageNumberOfWordsPerSentence;
	
	
	//======================================//
	//         STYLISTIC FEATURES           //
	//======================================//
	
	protected int numberOfNouns;
	protected double ratioOfNouns;
	protected int numberOfVerbs;
	protected double ratioOfVerbs;
	protected int numberOfAdjectives;
	protected double ratioOfAdjectives;
	protected int numberOfAdverbs;
	protected double ratioOfAdverbs;

	protected boolean useOfSymbols;
	protected boolean useOfComparativeForm;
	protected boolean useOfSuperlativeForm;
	protected boolean useOfProperNouns;

	protected int totalNumberOfParticles;
	protected double averageNumberOfParticles;
	protected int totalNumberOfInterjections;
	protected double averageNumberOfInterjections;
	protected int totalNumberOfPronouns;
	protected double averageNumberOfPronouns;
	protected int totalNumberOfPronounsGroup1;
	protected double averageNumberOfPronounsGroup1;
	protected int totalNumberOfPronounsGroup2;
	protected double averageNumberOfPronounsGroup2;
	protected boolean useOfNegation;
	protected boolean useOfUncommonWords;
	protected int numberOfUncommonWords;
	
	
	//======================================//
	//          SEMANTIC FEATURES           //
	//======================================//
	
	protected boolean useOfOpinionWords;
	protected boolean useOfHighlySentimentalWords;
	protected boolean useOfUncertaintyWords;
	protected boolean useOfActiveForm;
	protected boolean useOfPassiveForm;
	
	
	//======================================//
	//           UNIGRAM FEATURES           //
	//======================================//
	
	protected HashMap<String, Integer> unigramCountPerClass;
	
	protected HashMap<String, Integer> nounCountPerClass;
	protected HashMap<String, Integer> verbCountPerClass;
	protected HashMap<String, Integer> adjectiveCountPerClass;
	protected HashMap<String, Integer> adverbCountPerClass;
	
	//======================================//
	//          TOP WORDS FEATURES          //
	//======================================//
	
	protected int[] topWordSummed;
	protected boolean[] topWordsSeparatelyBoolean;
	
	
	//======================================//
	//           PATTERN FEATURES           //
	//======================================//
	
	protected double[] patternScores;
	
	
	//======================================//
	//     ADVANCED SENTIMENT FEATURES      //
	//======================================//
	
	protected int[] advSentimentScoreVector;
	protected ArrayList<String[]> advSentimentClassVectors;
	
	
	//======================================//
	//     ADVANCED SEMANTIC FEATURES       //
	//======================================//
	
	protected int advSentimentalPositiveWords;
	protected int advSentimentalNegativeWords;
	protected boolean advUseHighlyEmotionalPositiveWords;
	protected boolean advUseHighlyEmotionalNegativeWords;
	protected boolean advUseOfOpinionWords;
	protected boolean advUseOfUncertaintyWords;
	protected boolean advUseOfActiveForm;
	protected boolean advUseOfPassiveForm;
	
	
	//======================================//
	//      ADVANCED UNIGRAM FEATURES       //
	//======================================//
	
	protected boolean[] advUnigramFeatures;
	
	
	//======================================//
	//       ADVANCED PATTERN FEATURES      //
	//======================================//
	
	protected double[] advancedPatternScores;
	
	
	//======================================//
	//           IMPORTED FEATURES          //
	//======================================//
	
	protected ArrayList<String> extraFeatures;
	
	
	//======================================//
	//        QUANTIFICATION-RELATED        //
	//======================================//
	
	protected int[] unigramScores;
	protected double[][] basicPatternQuantificationScores;
	protected double[][] advancedPatternQuantificationScores;
	
	protected HashMap<String, Double> unigramFinalScores;
	protected HashMap<String, Double> basicPatternsFinalScores;
	protected HashMap<String, Double> advancedPatternsFinalScores;
	
	protected HashMap<String, Double> finalScores;
	
	
	//======================================//
	//  AFTER-CLASSIFICATION/QUANTIFICATION //
	//======================================//
	
	protected String predictedClass;
	protected ArrayList<String> predictedClasses;
	
	protected double m1;
	protected double m2;
	protected double precision;
	protected double recall;
	protected double f1;
	
	
	//--------------------------------------------------------------------------//
	
	
	
	//======================================//
	//             CONSTRUCTORS             //
	//======================================//
	
	public Tweet() {
		super();
	}
	
	public Tweet(String id, String userName, String text) {
		super();
		this.id = id;
		this.userName = userName;
		this.text = text;
	}
	
	public Tweet(String id, String userName, String text, String textClass) {
		super();
		this.id = id;
		this.userName = userName;
		this.text = text;
		this.tweetClass = textClass;
	}
	
	public Tweet(String id, String userName, String text, ArrayList<String> classes) {
		super();
		this.id = id;
		this.userName = userName;
		this.text = text;
		this.tweetClasses = classes;
		if (Commons.isPositiveSentiment(classes.get(0))) {
			this.tweetClass = "POSITIVE";
		} else if (Commons.isNegativeSentiment(classes.get(0))) {
			this.tweetClass = "NEGATIVE";
		} else {
			this.tweetClass = "NEUTRAL";
		}
	}
	
	
	//======================================//
	//            STATIC FIELDS             //
	//======================================//

	public static String getAdditionalField1Title() {
		return additionalField1Title;
	}
	public static void setAdditionalField1Title(String additionalField1Title) {
		Tweet.additionalField1Title = additionalField1Title;
	}

	public static String getAdditionalField2Title() {
		return additionalField2Title;
	}
	public static void setAdditionalField2Title(String additionalField2Title) {
		Tweet.additionalField2Title = additionalField2Title;
	}

	public static String getAdditionalField3Title() {
		return additionalField3Title;
	}
	public static void setAdditionalField3Title(String additionalField3Title) {
		Tweet.additionalField3Title = additionalField3Title;
	}

	public static String getAdditionalField4Title() {
		return additionalField4Title;
	}
	public static void setAdditionalField4Title(String additionalField4Title) {
		Tweet.additionalField4Title = additionalField4Title;
	}

	
	//======================================//
	//           BASIC COMPONENTS           //
	//======================================//
	
	// Basic components Part 1
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getPreprocessedText() {
		return preprocessedText;
	}
	public void setPreprocessedText(String preprocessedText) {
		this.preprocessedText = preprocessedText;
	}

	public String getTweetClass() {
		return tweetClass;
	}
	public void setTweetClass(String tweetClass) {
		this.tweetClass = tweetClass;
	}
	
	public ArrayList<String> getTweetClasses() {
		return tweetClasses;
	}
	public void setTweetClasses(ArrayList<String> tweetClasses) {
		this.tweetClasses = tweetClasses;
	}
	
	public String getAdditionalField1() {
		return additionalField1;
	}
	public void setAdditionalField1(String additionalField1) {
		this.additionalField1 = additionalField1;
	}

	public String getAdditionalField2() {
		return additionalField2;
	}
	public void setAdditionalField2(String additionalField2) {
		this.additionalField2 = additionalField2;
	}

	public String getAdditionalField3() {
		return additionalField3;
	}
	public void setAdditionalField3(String additionalField3) {
		this.additionalField3 = additionalField3;
	}

	public String getAdditionalField4() {
		return additionalField4;
	}
	public void setAdditionalField4(String additionalField4) {
		this.additionalField4 = additionalField4;
	}

	
	// Basic components Part 2
	public String[] getTokens() {
		return tokens;
	}
	public void setTokens(String[] tokens) {
		this.tokens = tokens;
	}

	public String[] getLemmas() {
		return lemmas;
	}
	public void setLemmas(String[] lemmas) {
		this.lemmas = lemmas;
	}

	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String[] getSimplifiedTags() {
		return simplifiedTags;
	}
	public void setSimplifiedTags(String[] simplifiedTags) {
		this.simplifiedTags = simplifiedTags;
	}

	public boolean[] getPolaritySwitcher() {
		return polaritySwitcher;
	}
	public void setPolaritySwitcher(boolean[] polaritySwitcher) {
		this.polaritySwitcher = polaritySwitcher;
	}

	public double[] getCoefficients() {
		return coefficients;
	}
	public void setCoefficients(double[] coefficients) {
		this.coefficients = coefficients;
	}

	public String[] getTokensPolarity() {
		return tokensPolarity;
	}
	public void setTokensPolarity(String[] tokensPolarity) {
		this.tokensPolarity = tokensPolarity;
	}
	
	public double[] getTokensScore() {
		return tokensScore;
	}
	public void setTokensScore(double[] tokensScore) {
		this.tokensScore = tokensScore;
	}
	
	
	//======================================//
	//         SENTIMENT FEATURES           //
	//======================================//

	public ArrayList<String> getPositiveWords() {
		return positiveWords;
	}
	public void setPositiveWords(ArrayList<String> positiveWords) {
		this.positiveWords = positiveWords;
	}

	public ArrayList<String> getNegativeWords() {
		return negativeWords;
	}
	public void setNegativeWords(ArrayList<String> negativeWords) {
		this.negativeWords = negativeWords;
	}
	
	public int getNumberOfPositiveWords() {
		return numberOfPositiveWords;
	}
	public void setNumberOfPositiveWords(int numberOfPositiveWords) {
		this.numberOfPositiveWords = numberOfPositiveWords;
	}

	public int getNumberOfNegativeWords() {
		return numberOfNegativeWords;
	}
	public void setNumberOfNegativeWords(int numberOfNegativeWords) {
		this.numberOfNegativeWords = numberOfNegativeWords;
	}

	public int getNumberOfHighlyEmoPositiveWords() {
		return numberOfHighlyEmoPositiveWords;
	}
	public void setNumberOfHighlyEmoPositiveWords(int numberOfHighlyEmoPositiveWords) {
		this.numberOfHighlyEmoPositiveWords = numberOfHighlyEmoPositiveWords;
	}

	public int getNumberOfHighlyEmoNegativeWords() {
		return numberOfHighlyEmoNegativeWords;
	}
	public void setNumberOfHighlyEmoNegativeWords(int numberOfHighlyEmoNegativeWords) {
		this.numberOfHighlyEmoNegativeWords = numberOfHighlyEmoNegativeWords;
	}

	public int getNumberOfCapitalizedPositiveWords() {
		return numberOfCapitalizedPositiveWords;
	}
	public void setNumberOfCapitalizedPositiveWords(int numberOfCapitalizedPositiveWords) {
		this.numberOfCapitalizedPositiveWords = numberOfCapitalizedPositiveWords;
	}

	public int getNumberOfCapitalizedNegativeWords() {
		return numberOfCapitalizedNegativeWords;
	}
	public void setNumberOfCapitalizedNegativeWords(int numberOfCapitalizedNegativeWords) {
		this.numberOfCapitalizedNegativeWords = numberOfCapitalizedNegativeWords;
	}

	public double getRatioOfEmotionalWords() {
		return ratioOfEmotionalWords;
	}
	public void setRatioOfEmotionalWords(double ratioOfEmotionalWords) {
		this.ratioOfEmotionalWords = ratioOfEmotionalWords;
	}

	public int getNumberOfPositiveEmoticons() {
		return numberOfPositiveEmoticons;
	}
	public void setNumberOfPositiveEmoticons(int numberOfPositiveEmoticons) {
		this.numberOfPositiveEmoticons = numberOfPositiveEmoticons;
	}

	public int getNumberOfNegativeEmoticons() {
		return numberOfNegativeEmoticons;
	}
	public void setNumberOfNegativeEmoticons(int numberOfNegativeEmoticons) {
		this.numberOfNegativeEmoticons = numberOfNegativeEmoticons;
	}

	public int getNumberOfNeutralEmoticons() {
		return numberOfNeutralEmoticons;
	}
	public void setNumberOfNeutralEmoticons(int numberOfNeutralEmoticons) {
		this.numberOfNeutralEmoticons = numberOfNeutralEmoticons;
	}

	public int getNumberOfJokingEmoticons() {
		return numberOfJokingEmoticons;
	}
	public void setNumberOfJokingEmoticons(int numberOfJokingEmoticons) {
		this.numberOfJokingEmoticons = numberOfJokingEmoticons;
	}

	public int getNumberOfPositiveSlangs() {
		return numberOfPositiveSlangs;
	}
	public void setNumberOfPositiveSlangs(int numberOfPositiveSlangs) {
		this.numberOfPositiveSlangs = numberOfPositiveSlangs;
	}

	public int getNumberOfNegativeSlangs() {
		return numberOfNegativeSlangs;
	}
	public void setNumberOfNegativeSlangs(int numberOfNegativeSlangs) {
		this.numberOfNegativeSlangs = numberOfNegativeSlangs;
	}

	public int getNumberOfPositiveHashtags() {
		return numberOfPositiveHashtags;
	}
	public void setNumberOfPositiveHashtags(int numberOfPositiveHashtags) {
		this.numberOfPositiveHashtags = numberOfPositiveHashtags;
	}

	public int getNumberOfNegativeHashtags() {
		return numberOfNegativeHashtags;
	}
	public void setNumberOfNegativeHashtags(int numberOfNegativeHashtags) {
		this.numberOfNegativeHashtags = numberOfNegativeHashtags;
	}

	public boolean getContrastWordsVsWords() {
		return contrastWordsVsWords;
	}
	public void setContrastWordsVsWords(boolean contrastWordsVsWords) {
		this.contrastWordsVsWords = contrastWordsVsWords;
	}

	public boolean getContrastWordsVsHashtags() {
		return contrastWordsVsHashtags;
	}
	public void setContrastWordsVsHashtags(boolean contrastWordsVsHashtags) {
		this.contrastWordsVsHashtags = contrastWordsVsHashtags;
	}

	public boolean getContrastWordsVsEmoticons() {
		return contrastWordsVsEmoticons;
	}
	public void setContrastWordsVsEmoticons(boolean contrastWordsVsEmoticons) {
		this.contrastWordsVsEmoticons = contrastWordsVsEmoticons;
	}

	public boolean getContrastHashtagsVsHashtags() {
		return contrastHashtagsVsHashtags;
	}
	public void setContrastHashtagsVsHashtags(boolean contrastHashtagsVsHashtags) {
		this.contrastHashtagsVsHashtags = contrastHashtagsVsHashtags;
	}

	public boolean getContrastHashtagsVsEmoticons() {
		return contrastHashtagsVsEmoticons;
	}
	public void setContrastHashtagsVsEmoticons(boolean contrastHashtagsVsEmoticons) {
		this.contrastHashtagsVsEmoticons = contrastHashtagsVsEmoticons;
	}

	
	//======================================//
	//        PUNCTUATION FEATURES          //
	//======================================//
	
	public int getNumberOfDots() {
		return numberOfDots;
	}
	public void setNumberOfDots(int numberOfDots) {
		this.numberOfDots = numberOfDots;
	}

	public int getNumberOfCommas() {
		return numberOfCommas;
	}
	public void setNumberOfCommas(int numberOfCommas) {
		this.numberOfCommas = numberOfCommas;
	}

	public int getNumberOfSemicolons() {
		return numberOfSemicolons;
	}
	public void setNumberOfSemicolons(int numberOfSemicolons) {
		this.numberOfSemicolons = numberOfSemicolons;
	}

	public int getNumberOfExclamationMarks() {
		return numberOfExclamationMarks;
	}
	public void setNumberOfExclamationMarks(int numberOfExclamationMarks) {
		this.numberOfExclamationMarks = numberOfExclamationMarks;
	}

	public int getNumberOfQuestionMarks() {
		return numberOfQuestionMarks;
	}
	public void setNumberOfQuestionMarks(int numberOfQuestionMarks) {
		this.numberOfQuestionMarks = numberOfQuestionMarks;
	}

	public int getNumberOfParentheses() {
		return numberOfParentheses;
	}
	public void setNumberOfParentheses(int numberOfParentheses) {
		this.numberOfParentheses = numberOfParentheses;
	}

	public int getNumberOfBrackets() {
		return numberOfBrackets;
	}
	public void setNumberOfBrackets(int numberOfBrackets) {
		this.numberOfBrackets = numberOfBrackets;
	}

	public int getNumberOfBraces() {
		return numberOfBraces;
	}
	public void setNumberOfBraces(int numberOfBraces) {
		this.numberOfBraces = numberOfBraces;
	}

	public int getNumberOfApostrophes() {
		return numberOfApostrophes;
	}
	public void setNumberOfApostrophes(int numberOfApostrophes) {
		this.numberOfApostrophes = numberOfApostrophes;
	}

	public int getNumberOfQuotationMarks() {
		return numberOfQuotationMarks;
	}
	public void setNumberOfQuotationMarks(int numberOfQuotationMarks) {
		this.numberOfQuotationMarks = numberOfQuotationMarks;
	}

	public int getTotalNumberOfCharacters() {
		return totalNumberOfCharacters;
	}
	public void setTotalNumberOfCharacters(int totalNumberOfCharacters) {
		this.totalNumberOfCharacters = totalNumberOfCharacters;
	}

	public int getTotalNumberOfWords() {
		return totalNumberOfWords;
	}
	public void setTotalNumberOfWords(int totalNumberOfWords) {
		this.totalNumberOfWords = totalNumberOfWords;
	}

	public int getTotalNumberOfSentences() {
		return totalNumberOfSentences;
	}
	public void setTotalNumberOfSentences(int totalNumberOfSentences) {
		this.totalNumberOfSentences = totalNumberOfSentences;
	}

	public double getAverageNumberOfCharactersPerSentence() {
		return averageNumberOfCharactersPerSentence;
	}
	public void setAverageNumberOfCharactersPerSentence(double averageNumberOfCharactersPerSentence) {
		this.averageNumberOfCharactersPerSentence = averageNumberOfCharactersPerSentence;
	}

	public double getAverageNumberOfWordsPerSentence() {
		return averageNumberOfWordsPerSentence;
	}
	public void setAverageNumberOfWordsPerSentence(double averageNumberOfWordsPerSentence) {
		this.averageNumberOfWordsPerSentence = averageNumberOfWordsPerSentence;
	}

	
	//======================================//
	//         STYLISTIC FEATURES           //
	//======================================//
	
	public int getNumberOfNouns() {
		return numberOfNouns;
	}
	public void setNumberOfNouns(int numberOfNouns) {
		this.numberOfNouns = numberOfNouns;
	}

	public double getRatioOfNouns() {
		return ratioOfNouns;
	}
	public void setRatioOfNouns(double ratioOfNouns) {
		this.ratioOfNouns = ratioOfNouns;
	}

	public int getNumberOfVerbs() {
		return numberOfVerbs;
	}
	public void setNumberOfVerbs(int numberOfVerbs) {
		this.numberOfVerbs = numberOfVerbs;
	}

	public double getRatioOfVerbs() {
		return ratioOfVerbs;
	}
	public void setRatioOfVerbs(double ratioOfVerbs) {
		this.ratioOfVerbs = ratioOfVerbs;
	}

	public int getNumberOfAdjectives() {
		return numberOfAdjectives;
	}
	public void setNumberOfAdjectives(int numberOfAdjectives) {
		this.numberOfAdjectives = numberOfAdjectives;
	}

	public double getRatioOfAdjectives() {
		return ratioOfAdjectives;
	}
	public void setRatioOfAdjectives(double ratioOfAdjectives) {
		this.ratioOfAdjectives = ratioOfAdjectives;
	}

	public int getNumberOfAdverbs() {
		return numberOfAdverbs;
	}
	public void setNumberOfAdverbs(int numberOfAdverbs) {
		this.numberOfAdverbs = numberOfAdverbs;
	}

	public double getRatioOfAdverbs() {
		return ratioOfAdverbs;
	}
	public void setRatioOfAdverbs(double ratioOfAdverbs) {
		this.ratioOfAdverbs = ratioOfAdverbs;
	}

	public boolean isUseOfSymbols() {
		return useOfSymbols;
	}
	public void setUseOfSymbols(boolean useOfSymbols) {
		this.useOfSymbols = useOfSymbols;
	}

	public boolean isUseOfComparativeForm() {
		return useOfComparativeForm;
	}
	public void setUseOfComparativeForm(boolean useOfComparativeForm) {
		this.useOfComparativeForm = useOfComparativeForm;
	}

	public boolean isUseOfSuperlativeForm() {
		return useOfSuperlativeForm;
	}
	public void setUseOfSuperlativeForm(boolean useOfSuperlativeForm) {
		this.useOfSuperlativeForm = useOfSuperlativeForm;
	}

	public boolean isUseOfProperNouns() {
		return useOfProperNouns;
	}
	public void setUseOfProperNouns(boolean useOfProperNouns) {
		this.useOfProperNouns = useOfProperNouns;
	}

	public int getTotalNumberOfParticles() {
		return totalNumberOfParticles;
	}
	public void setTotalNumberOfParticles(int totalNumberOfParticles) {
		this.totalNumberOfParticles = totalNumberOfParticles;
	}

	public double getAverageNumberOfParticles() {
		return averageNumberOfParticles;
	}
	public void setAverageNumberOfParticles(double averageNumberOfParticles) {
		this.averageNumberOfParticles = averageNumberOfParticles;
	}

	public int getTotalNumberOfInterjections() {
		return totalNumberOfInterjections;
	}
	public void setTotalNumberOfInterjections(int totalNumberOfInterjections) {
		this.totalNumberOfInterjections = totalNumberOfInterjections;
	}

	public double getAverageNumberOfInterjections() {
		return averageNumberOfInterjections;
	}
	public void setAverageNumberOfInterjections(double averageNumberOfInterjections) {
		this.averageNumberOfInterjections = averageNumberOfInterjections;
	}

	public int getTotalNumberOfPronouns() {
		return totalNumberOfPronouns;
	}
	public void setTotalNumberOfPronouns(int totalNumberOfPronouns) {
		this.totalNumberOfPronouns = totalNumberOfPronouns;
	}

	public double getAverageNumberOfPronouns() {
		return averageNumberOfPronouns;
	}
	public void setAverageNumberOfPronouns(double averageNumberOfPronouns) {
		this.averageNumberOfPronouns = averageNumberOfPronouns;
	}

	public int getTotalNumberOfPronounsGroup1() {
		return totalNumberOfPronounsGroup1;
	}
	public void setTotalNumberOfPronounsGroup1(int totalNumberOfPronounsGroup1) {
		this.totalNumberOfPronounsGroup1 = totalNumberOfPronounsGroup1;
	}

	public double getAverageNumberOfPronounsGroup1() {
		return averageNumberOfPronounsGroup1;
	}
	public void setAverageNumberOfPronounsGroup1(double averageNumberOfPronounsGroup1) {
		this.averageNumberOfPronounsGroup1 = averageNumberOfPronounsGroup1;
	}

	public int getTotalNumberOfPronounsGroup2() {
		return totalNumberOfPronounsGroup2;
	}
	public void setTotalNumberOfPronounsGroup2(int totalNumberOfPronounsGroup2) {
		this.totalNumberOfPronounsGroup2 = totalNumberOfPronounsGroup2;
	}

	public double getAverageNumberOfPronounsGroup2() {
		return averageNumberOfPronounsGroup2;
	}
	public void setAverageNumberOfPronounsGroup2(double averageNumberOfPronounsGroup2) {
		this.averageNumberOfPronounsGroup2 = averageNumberOfPronounsGroup2;
	}

	public boolean isUseOfNegation() {
		return useOfNegation;
	}
	public void setUseOfNegation(boolean useOfNegation) {
		this.useOfNegation = useOfNegation;
	}

	public boolean isUseOfUncommonWords() {
		return useOfUncommonWords;
	}
	public void setUseOfUncommonWords(boolean useOfUncommonWords) {
		this.useOfUncommonWords = useOfUncommonWords;
	}

	public int getNumberOfUncommonWords() {
		return numberOfUncommonWords;
	}
	public void setNumberOfUncommonWords(int numberOfUncommonWords) {
		this.numberOfUncommonWords = numberOfUncommonWords;
	}

	
	
	//======================================//
	//         SEMANTIC FEATURES           //
	//======================================//
	
	public boolean isUseOfOpinionWords() {
		return useOfOpinionWords;
	}
	public void setUseOfOpinionWords(boolean useOfOpinionWords) {
		this.useOfOpinionWords = useOfOpinionWords;
	}

	public boolean isUseOfHighlySentimentalWords() {
		return useOfHighlySentimentalWords;
	}

	public void setUseOfHighlySentimentalWords(boolean useOfHighlySentimentalWords) {
		this.useOfHighlySentimentalWords = useOfHighlySentimentalWords;
	}

	public boolean isUseOfUncertaintyWords() {
		return useOfUncertaintyWords;
	}

	public void setUseOfUncertaintyWords(boolean useOfUncertaintyWords) {
		this.useOfUncertaintyWords = useOfUncertaintyWords;
	}

	public boolean isUseOfActiveForm() {
		return useOfActiveForm;
	}

	public void setUseOfActiveForm(boolean useOfActiveForm) {
		this.useOfActiveForm = useOfActiveForm;
	}

	public boolean isUseOfPassiveForm() {
		return useOfPassiveForm;
	}

	public void setUseOfPassiveForm(boolean useOfPassiveForm) {
		this.useOfPassiveForm = useOfPassiveForm;
	}

	public HashMap<String, Integer> getUnigramCountPerClass() {
		return unigramCountPerClass;
	}

	public void setUnigramCountPerClass(HashMap<String, Integer> unigramCountPerClass) {
		this.unigramCountPerClass = unigramCountPerClass;
	}

	public HashMap<String, Integer> getNounCountPerClass() {
		return nounCountPerClass;
	}

	public void setNounCountPerClass(HashMap<String, Integer> nounCountPerClass) {
		this.nounCountPerClass = nounCountPerClass;
	}

	public HashMap<String, Integer> getVerbCountPerClass() {
		return verbCountPerClass;
	}

	public void setVerbCountPerClass(HashMap<String, Integer> verbCountPerClass) {
		this.verbCountPerClass = verbCountPerClass;
	}

	public HashMap<String, Integer> getAdjectiveCountPerClass() {
		return adjectiveCountPerClass;
	}

	public void setAdjectiveCountPerClass(HashMap<String, Integer> adjectiveCountPerClass) {
		this.adjectiveCountPerClass = adjectiveCountPerClass;
	}

	public HashMap<String, Integer> getAdverbCountPerClass() {
		return adverbCountPerClass;
	}

	public void setAdverbCountPerClass(HashMap<String, Integer> adverbCountPerClass) {
		this.adverbCountPerClass = adverbCountPerClass;
	}

	public int[] getTopWordSummed() {
		return topWordSummed;
	}

	public void setTopWordSummed(int[] topWordSummed) {
		this.topWordSummed = topWordSummed;
	}

	public boolean[] getTopWordsSeparatelyBoolean() {
		return topWordsSeparatelyBoolean;
	}

	public void setTopWordsSeparatelyBoolean(boolean[] topWordsSeparatelyBoolean) {
		this.topWordsSeparatelyBoolean = topWordsSeparatelyBoolean;
	}

	public double[] getPatternScores() {
		return patternScores;
	}

	public void setPatternScores(double[] patternScores) {
		this.patternScores = patternScores;
	}

	
	//======================================//
	//     ADVANCED SENTIMENT FEATURES      //
	//======================================//
	
	public int[] getAdvSentimentScoreVector() {
		return advSentimentScoreVector;
	}

	public void setAdvSentimentScoreVector(int[] advSentimentScoreVector) {
		this.advSentimentScoreVector = advSentimentScoreVector;
	}

	public ArrayList<String[]> getAdvSentimentClassVectors() {
		return advSentimentClassVectors;
	}

	public void setAdvSentimentClassVectors(ArrayList<String[]> advSentimentClassVectors) {
		this.advSentimentClassVectors = advSentimentClassVectors;
	}

	
	//======================================//
	//     ADVANCED SEMANTIC FEATURES       //
	//======================================//
	
	public int getAdvSentimentalPositiveWords() {
		return advSentimentalPositiveWords;
	}

	public void setAdvSentimentalPositiveWords(int advSentimentalPositiveWords) {
		this.advSentimentalPositiveWords = advSentimentalPositiveWords;
	}

	public int getAdvSentimentalNegativeWords() {
		return advSentimentalNegativeWords;
	}

	public void setAdvSentimentalNegativeWords(int advSentimentalNegativeWords) {
		this.advSentimentalNegativeWords = advSentimentalNegativeWords;
	}

	public boolean isAdvUseHighlyEmotionalPositiveWords() {
		return advUseHighlyEmotionalPositiveWords;
	}

	public void setAdvUseHighlyEmotionalPositiveWords(boolean advUseHighlyEmotionalPositiveWords) {
		this.advUseHighlyEmotionalPositiveWords = advUseHighlyEmotionalPositiveWords;
	}

	public boolean isAdvUseHighlyEmotionalNegativeWords() {
		return advUseHighlyEmotionalNegativeWords;
	}

	public void setAdvUseHighlyEmotionalNegativeWords(boolean advUseHighlyEmotionalNegativeWords) {
		this.advUseHighlyEmotionalNegativeWords = advUseHighlyEmotionalNegativeWords;
	}

	public boolean isAdvUseOfOpinionWords() {
		return advUseOfOpinionWords;
	}

	public void setAdvUseOfOpinionWords(boolean advUseOfOpinionWords) {
		this.advUseOfOpinionWords = advUseOfOpinionWords;
	}

	public boolean isAdvUseOfUncertaintyWords() {
		return advUseOfUncertaintyWords;
	}

	public void setAdvUseOfUncertaintyWords(boolean advUseOfUncertaintyWords) {
		this.advUseOfUncertaintyWords = advUseOfUncertaintyWords;
	}

	public boolean isAdvUseOfActiveForm() {
		return advUseOfActiveForm;
	}

	public void setAdvUseOfActiveForm(boolean advUseOfActiveForm) {
		this.advUseOfActiveForm = advUseOfActiveForm;
	}

	public boolean isAdvUseOfPassiveForm() {
		return advUseOfPassiveForm;
	}

	public void setAdvUseOfPassiveForm(boolean advUseOfPassiveForm) {
		this.advUseOfPassiveForm = advUseOfPassiveForm;
	}

	
	//======================================//
	//      ADVANCED PATTERN FEATURES       //
	//======================================//
	
	public double[] getAdvancedPatternScores() {
		return advancedPatternScores;
	}

	public void setAdvancedPatternScores(double[] advancedPatternScores) {
		this.advancedPatternScores = advancedPatternScores;
	}
	
	
	//======================================//
	//      ADVANCED UNIGRAM FEATURES       //
	//======================================//
	
	public boolean[] getAdvUnigramFeatures() {
		return advUnigramFeatures;
	}
	public void setAdvUnigramFeatures(boolean[] advUnigramFeatures) {
		this.advUnigramFeatures = advUnigramFeatures;
	}
	
	
	//======================================//
	//            EXTRA FEATURES            //
	//======================================//
	
	public ArrayList<String> getExtraFeatures() {
		return extraFeatures;
	}
	public void setExtraFeatures(ArrayList<String> extraFeatures) {
		this.extraFeatures = extraFeatures;
	}
	
	
	//======================================//
	//        QUANTIFICATION-RELATED        //
	//======================================//

	public int[] getUnigramScores() {
		return unigramScores;
	}
	public void setUnigramScores(int[] unigramScores) {
		this.unigramScores = unigramScores;
	}
	
	public double[][] getBasicPatternQuantificationScores() {
		return basicPatternQuantificationScores;
	}
	public void setBasicPatternQuantificationScores(double[][] basicPatternQuantificationScores) {
		this.basicPatternQuantificationScores = basicPatternQuantificationScores;
	}

	public double[][] getAdvancedPatternQuantificationScores() {
		return advancedPatternQuantificationScores;
	}
	public void setAdvancedPatternQuantificationScores(double[][] advancedPatternQuantificationScores) {
		this.advancedPatternQuantificationScores = advancedPatternQuantificationScores;
	}
	
	public HashMap<String, Double> getUnigramFinalScores() {
		return unigramFinalScores;
	}
	public void setUnigramFinalScores(HashMap<String, Double> unigramFinalScores) {
		this.unigramFinalScores = unigramFinalScores;
	}

	public HashMap<String, Double> getBasicPatternsFinalScores() {
		return basicPatternsFinalScores;
	}
	public void setBasicPatternsFinalScores(HashMap<String, Double> basicPatternsFinalScores) {
		this.basicPatternsFinalScores = basicPatternsFinalScores;
	}

	public HashMap<String, Double> getAdvancedPatternsFinalScores() {
		return advancedPatternsFinalScores;
	}
	public void setAdvancedPatternsFinalScores(HashMap<String, Double> advancedPatternsFinalScores) {
		this.advancedPatternsFinalScores = advancedPatternsFinalScores;
	}
	
	public HashMap<String, Double> getFinalScores() {
		return finalScores;
	}
	public void setFinalScores(HashMap<String, Double> finalScores) {
		this.finalScores = finalScores;
	}
	
	
	//======================================//
	//  AFTER-CLASSIFICATION/QUANTIFICATION //
	//======================================//
	
	public String getPredictedClass() {
		return predictedClass;
	}
	public void setPredictedClass(String predictedClass) {
		this.predictedClass = predictedClass;
	}
	
	public ArrayList<String> getPredictedClasses() {
		return predictedClasses;
	}
	public void setPredictedClasses(ArrayList<String> predictedClasses) {
		this.predictedClasses = predictedClasses;
	}
	
	

	public double getM1() {
		return m1;
	}
	public void setM1(double m1) {
		this.m1 = m1;
	}

	public double getM2() {
		return m2;
	}
	public void setM2(double m2) {
		this.m2 = m2;
	}

	public double getPrecision() {
		return precision;
	}
	public void setPrecision(double precision) {
		this.precision = precision;
	}

	public double getRecall() {
		return recall;
	}
	public void setRecall(double recall) {
		this.recall = recall;
	}

	public double getF1() {
		return f1;
	}
	public void setF1(double f1) {
		this.f1 = f1;
	}
	
	
	//======================================//
	//         COMOPARISON METHODS          //
	//======================================//


	/**
	 * Compare the tweet IDs
	 * @param otherTweet
	 * @return
	 */
	@Override
	public int compareTo(Tweet otherTweet) {
		
		if (this.id.equals(otherTweet.getId())) {
			return 0;
		} else {
			return -1;
		}
	}




	
	
	
	

}

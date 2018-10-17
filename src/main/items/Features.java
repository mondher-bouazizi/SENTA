package main.items;

import java.util.HashMap;

public class Features {
	
	// =====================================//
	//             ENUMERATIONS             //
	// =====================================//
	
	public enum TypeOfFeature {
		UNIQUE,
		SUMMED
		}
	
	public enum AdvancedTypeOfFeatures {
		POLARITY, SCORE
	}
	
	public enum TypeOfPos {
		TOGETHER,
		SEPARATED
	}
	
	public enum PatternActions{
		KEEP,
		LEMMATIZE,
		POS,
		SIMPLIFIEDPOS,
		SIMPLIFIEDPOSANDSENTIMENT,
		REPLACEWITH
	}
	
	public enum Categories{
		cat1, cat2, cat3, cat4
	}
	
	public enum TopicRelated {
		ONLYTOPICRELATED,
		ALL
	}
	
	public enum IsLemma {
		Lemmas,
		Words
	}
	
	
	
	// =====================================//
	//         ATTRIBUTES (FEATURES)        //
	// =====================================//
	
	// Sentiment-related features
	protected static boolean useSentimentFeatures;
	
	protected static boolean numberOfPositiveWords;
	protected static boolean numberOfNegativeWords;
	protected static boolean numberOfHighlyEmoPositiveWords;
	protected static boolean numberOfHighlyEmoNegativeWords;
	protected static boolean numberOfCapitalizedPositiveWords;
	protected static boolean numberOfCapitalizedNegativeWords;
	protected static boolean ratioOfEmotionalWords;
	
	protected static boolean numberOfPositiveEmoticons;
	protected static boolean numberOfNegativeEmoticons;
	protected static boolean numberOfNeutralEmoticons;
	protected static boolean numberOfJokingEmoticons;
	
	protected static boolean numberOfPositiveSlangs;
	protected static boolean numberOfNegativeSlangs;
	
	protected static boolean numberOfPositiveHashtags;
	protected static boolean numberOfNegativeHashtags;
	
	protected static boolean contrastWordsVsWords;
	protected static boolean contrastWordsVsHashtags;
	protected static boolean contrastWordsVsEmoticons;
	protected static boolean contrastHashtagsVsHashtags;
	protected static boolean contrastHashtagsVsEmoticons;
	
	
	// Punctuation features
	protected static boolean usePunctuationFeatures;
	
	protected static boolean numberOfDots;
	protected static boolean numberOfCommas;
	protected static boolean numberOfSemicolons;
	protected static boolean numberOfExclamationMarks;
	protected static boolean numberOfQuestionMarks;

	protected static boolean numberOfParentheses;
	protected static boolean numberOfBrackets;
	protected static boolean numberOfBraces;
	
	protected static boolean numberOfApostrophes;
	protected static boolean numberOfQuotationMarks;
	
	protected static boolean totalNumberOfCharacters;
	protected static boolean totalNumberOfWords;
	protected static boolean totalNumberOfSentences;
	protected static boolean averageNumberOfCharactersPerSentence;
	protected static boolean averageNumberOfWordsPerSentence;
	
	
	// Sytlistic features
	protected static boolean useStylisticFeatures;
	
	protected static boolean numberOfNouns;
	protected static boolean ratioOfNouns;
	protected static boolean numberOfVerbs;
	protected static boolean ratioOfVerbs;
	protected static boolean numberOfAdjectives;
	protected static boolean ratioOfAdjectives;
	protected static boolean numberOfAdverbs;
	protected static boolean ratioOfAdverbs;

	protected static boolean useOfSymbols;
	protected static boolean useOfComparativeForm;
	protected static boolean useOfSuperlativeForm;
	protected static boolean useOfProperNouns;

	protected static boolean totalNumberOfParticles;
	protected static boolean averageNumberOfParticles;
	protected static boolean totalNumberOfInterjections;
	protected static boolean averageNumberOfInterjections;
	protected static boolean totalNumberOfPronouns;
	protected static boolean averageNumberOfPronouns;
	protected static boolean totalNumberOfPronounsGroup1;
	protected static boolean averageNumberOfPronounsGroup1;
	protected static boolean totalNumberOfPronounsGroup2;
	protected static boolean averageNumberOfPronounsGroup2;
	protected static boolean useOfNegation;
	protected static boolean useOfUncommonWords;
	protected static boolean numberOfUncommonWords;
	
	
	// Semantic Feature
	protected static boolean useSemanticFeatures;
	
	protected static boolean useOfOpinionWords;
	protected static boolean useOfHighlySentimentalWords;
	protected static boolean useOfUncertaintyWords;
	protected static boolean useOfActiveForm;
	protected static boolean useOfPassiveForm;
	
	
	// Unigram Features
	protected static boolean useUnigramFeatures;
	
	protected static int depth;
	
	protected static boolean useUnigramNouns;
	protected static boolean useUnigramVerbs;
	protected static boolean useUnigramAdjectives;
	protected static boolean useUnigramAdverbs;
	
	protected static TypeOfPos unigramTypeOfPos;
	
	protected static boolean zeroTaken;
	protected static boolean oppositeTaken;
	
	
	// Top Words Features
	protected static boolean useTopWords;
	
	protected static boolean useTopWordsNouns;
	protected static boolean useTopWordsVerbs;
	protected static boolean useTopWordsAdjectives;
	protected static boolean useTopWordsAdverbs;
	
	protected static TypeOfPos topWordsTypeOfPos;
	
	protected static int numberOfTopWordsPerClass;
	protected static int numberOfTopWordsPerPos;
	
	protected static TypeOfFeature topWordsType;
	
	protected static double topWordsMinRatio;
	protected static int topWordsMinOccurrence;
	
	
	// Pattern Features
	protected static boolean usePatternFeatures;

	protected static TypeOfFeature patternFeaturesType;

	protected static int patternLength;
	
	protected static int numberOfPatternsPerClass;

	protected static int minPatternLength;
	protected static int maxPatternLength;

	protected static int numberOfPosCategories;
	
	protected static boolean category1;
	protected static boolean category2;
	protected static boolean category3;
	protected static boolean category4;

	protected static PatternActions action1;
	protected static PatternActions action2;
	protected static PatternActions action3;
	protected static PatternActions action4;
	
	protected static String replacement1;
	protected static String replacement2;
	protected static String replacement3;
	protected static String replacement4;
		
	protected static int patternsMinOccurrence;
	protected static double alpha;
	protected static double gamma;
	protected static int basicPatternsKnn;
	
	protected static Categories[] categories;
	protected static HashMap<String, Categories> categoriesMap;
	
	
	// Advanced Sentiment Features
	protected static boolean useAdvancedSentimentFeatures;
	
	protected static AdvancedTypeOfFeatures typeOfAdvancedSentimentFeatures;
	
	protected static int numberOfWordsBefore;
	protected static int numberOfWordsAfter;
	
	protected static boolean addSentimentClassInformation;
	
	
	// Advanced Semantic Features
	protected static boolean useAdvancedSemanticFeatures;
	
	protected static TopicRelated wordsIntoAccount;
	protected static boolean countOtherWords;
	protected static TypeOfPos advancedSemanticPos;
	
	protected static boolean advUseOfPositiveWords;
	protected static boolean advUseOfNegativeWords;
	protected static boolean advUseOfHighlyPositiveWords;
	protected static boolean advUseOfHighlyNegativeWords;
	
	protected static boolean advUseOfOpinionWords;
	protected static boolean advUseOfUncertaintyWords;
	protected static boolean advUseOfActiveForm;
	protected static boolean advUseOfPassiveForm;
	
	
	// Advanced Pattern Features
	protected static boolean useAdvancedPatternFeatures;
	
	protected static TypeOfFeature advancedPatternFeaturesType;

	protected static int advancedPatternLength;
	protected static int advancedNumberOfPatternsPerClass;

	protected static int advancedMinPatternLength;
	protected static int advancedMaxPatternLength;
	
	protected static int advancedPatternsMinOccurrence;
	protected static double advancedPatternAlpha;
	protected static double advancedPatternGamma;
	protected static int advancedPatternsKnn;
	
	
	// Advanced Unigram Features
	protected static boolean useAdvancedUnigramFeatures;
	protected static IsLemma isLemma;
	
	
	// Quantification-related
	protected static double[][] coefficients;
	protected static double[][] manualCoefficients;

	protected static boolean useManualParameters;
	
	protected static boolean useQuantifUnigrams;
	protected static boolean useQuantifBasicPatterns;
	protected static boolean useQuantifAdvancedPatterns;
	
	protected static double sentimentsThreshold;
	
	protected static boolean outputPredictions;
	
	
	// =====================================//
	//          GETTERS AND SETTERS         //
	// =====================================//
	
	// Setters and getters of Sentiment-Related Words
	
	public static boolean isUseSentimentFeatures() {
		return useSentimentFeatures;
	}
	public static void setUseSentimentFeatures(boolean useSentimentFeatures) {
		Features.useSentimentFeatures = useSentimentFeatures;
	}

	public static boolean isNumberOfPositiveWords() {
		return numberOfPositiveWords;
	}
	public static void setNumberOfPositiveWords(boolean numberOfPositiveWords) {
		Features.numberOfPositiveWords = numberOfPositiveWords;
	}

	public static boolean isNumberOfNegativeWords() {
		return numberOfNegativeWords;
	}
	public static void setNumberOfNegativeWords(boolean numberOfNegativeWords) {
		Features.numberOfNegativeWords = numberOfNegativeWords;
	}

	public static boolean isNumberOfHighlyEmoPositiveWords() {
		return numberOfHighlyEmoPositiveWords;
	}
	public static void setNumberOfHighlyEmoPositiveWords(boolean numberOfHighlyEmoPositiveWords) {
		Features.numberOfHighlyEmoPositiveWords = numberOfHighlyEmoPositiveWords;
	}

	public static boolean isNumberOfHighlyEmoNegativeWords() {
		return numberOfHighlyEmoNegativeWords;
	}
	public static void setNumberOfHighlyEmoNegativeWords(boolean numberOfHighlyEmoNegativeWords) {
		Features.numberOfHighlyEmoNegativeWords = numberOfHighlyEmoNegativeWords;
	}

	public static boolean isNumberOfCapitalizedPositiveWords() {
		return numberOfCapitalizedPositiveWords;
	}
	public static void setNumberOfCapitalizedPositiveWords(boolean numberOfCapitalizedPositiveWords) {
		Features.numberOfCapitalizedPositiveWords = numberOfCapitalizedPositiveWords;
	}

	public static boolean isNumberOfCapitalizedNegativeWords() {
		return numberOfCapitalizedNegativeWords;
	}
	public static void setNumberOfCapitalizedNegativeWords(boolean numberOfCapitalizedNegativeWords) {
		Features.numberOfCapitalizedNegativeWords = numberOfCapitalizedNegativeWords;
	}

	public static boolean isRatioOfEmotionalWords() {
		return ratioOfEmotionalWords;
	}
	public static void setRatioOfEmotionalWords(boolean ratioOfEmotionalWords) {
		Features.ratioOfEmotionalWords = ratioOfEmotionalWords;
	}

	public static boolean isNumberOfPositiveEmoticons() {
		return numberOfPositiveEmoticons;
	}
	public static void setNumberOfPositiveEmoticons(boolean numberOfPositiveEmoticons) {
		Features.numberOfPositiveEmoticons = numberOfPositiveEmoticons;
	}

	public static boolean isNumberOfNegativeEmoticons() {
		return numberOfNegativeEmoticons;
	}
	public static void setNumberOfNegativeEmoticons(boolean numberOfNegativeEmoticons) {
		Features.numberOfNegativeEmoticons = numberOfNegativeEmoticons;
	}

	public static boolean isNumberOfNeutralEmoticons() {
		return numberOfNeutralEmoticons;
	}
	public static void setNumberOfNeutralEmoticons(boolean numberOfNeutralEmoticons) {
		Features.numberOfNeutralEmoticons = numberOfNeutralEmoticons;
	}

	public static boolean isNumberOfJokingEmoticons() {
		return numberOfJokingEmoticons;
	}
	public static void setNumberOfJokingEmoticons(boolean numberOfJokingEmoticons) {
		Features.numberOfJokingEmoticons = numberOfJokingEmoticons;
	}

	public static boolean isNumberOfPositiveSlangs() {
		return numberOfPositiveSlangs;
	}
	public static void setNumberOfPositiveSlangs(boolean numberOfPositiveSlangs) {
		Features.numberOfPositiveSlangs = numberOfPositiveSlangs;
	}

	public static boolean isNumberOfNegativeSlangs() {
		return numberOfNegativeSlangs;
	}
	public static void setNumberOfNegativeSlangs(boolean numberOfNegativeSlangs) {
		Features.numberOfNegativeSlangs = numberOfNegativeSlangs;
	}

	public static boolean isNumberOfPositiveHashtags() {
		return numberOfPositiveHashtags;
	}
	public static void setNumberOfPositiveHashtags(boolean numberOfPositiveHashtags) {
		Features.numberOfPositiveHashtags = numberOfPositiveHashtags;
	}

	public static boolean isNumberOfNegativeHashtags() {
		return numberOfNegativeHashtags;
	}
	public static void setNumberOfNegativeHashtags(boolean numberOfNegativeHashtags) {
		Features.numberOfNegativeHashtags = numberOfNegativeHashtags;
	}

	public static boolean isContrastWordsVsWords() {
		return contrastWordsVsWords;
	}
	public static void setContrastWordsVsWords(boolean contrastWordsVsWords) {
		Features.contrastWordsVsWords = contrastWordsVsWords;
	}

	public static boolean isContrastWordsVsHashtags() {
		return contrastWordsVsHashtags;
	}
	public static void setContrastWordsVsHashtags(boolean contrastWordsVsHashtags) {
		Features.contrastWordsVsHashtags = contrastWordsVsHashtags;
	}

	public static boolean isContrastWordsVsEmoticons() {
		return contrastWordsVsEmoticons;
	}
	public static void setContrastWordsVsEmoticons(boolean contrastWordsVsEmoticons) {
		Features.contrastWordsVsEmoticons = contrastWordsVsEmoticons;
	}

	public static boolean isContrastHashtagsVsHashtags() {
		return contrastHashtagsVsHashtags;
	}
	public static void setContrastHashtagsVsHashtags(boolean contrastHashtagsVsHashtags) {
		Features.contrastHashtagsVsHashtags = contrastHashtagsVsHashtags;
	}

	public static boolean isContrastHashtagsVsEmoticons() {
		return contrastHashtagsVsEmoticons;
	}
	public static void setContrastHashtagsVsEmoticons(boolean contrastHashtagsVsEmoticons) {
		Features.contrastHashtagsVsEmoticons = contrastHashtagsVsEmoticons;
	}
	

	// Puncutation Features Getters and Setters
	
	public static boolean isUsePunctuationFeatures() {
		return usePunctuationFeatures;
	}
	public static void setUsePunctuationFeatures(boolean usePunctuationFeatures) {
		Features.usePunctuationFeatures = usePunctuationFeatures;
	}
	
	public static boolean isNumberOfDots() {
		return numberOfDots;
	}
	public static void setNumberOfDots(boolean numberOfDots) {
		Features.numberOfDots = numberOfDots;
	}
	
	public static boolean isNumberOfCommas() {
		return numberOfCommas;
	}
	public static void setNumberOfCommas(boolean numberOfCommas) {
		Features.numberOfCommas = numberOfCommas;
	}
	
	public static boolean isNumberOfSemicolons() {
		return numberOfSemicolons;
	}
	public static void setNumberOfSemicolons(boolean numberOfSemicolons) {
		Features.numberOfSemicolons = numberOfSemicolons;
	}
	
	public static boolean isNumberOfExclamationMarks() {
		return numberOfExclamationMarks;
	}
	public static void setNumberOfExclamationMarks(boolean numberOfExclamationMarks) {
		Features.numberOfExclamationMarks = numberOfExclamationMarks;
	}
	

	public static boolean isNumberOfQuestionMarks() {
		return numberOfQuestionMarks;
	}
	public static void setNumberOfQuestionMarks(boolean numberOfQuestionMarks) {
		Features.numberOfQuestionMarks = numberOfQuestionMarks;
	}
	
	public static boolean isNumberOfParentheses() {
		return numberOfParentheses;
	}
	public static void setNumberOfParentheses(boolean numberOfParentheses) {
		Features.numberOfParentheses = numberOfParentheses;
	}
	
	public static boolean isNumberOfBrackets() {
		return numberOfBrackets;
	}
	public static void setNumberOfBrackets(boolean numberOfBrackets) {
		Features.numberOfBrackets = numberOfBrackets;
	}
	
	public static boolean isNumberOfBraces() {
		return numberOfBraces;
	}
	public static void setNumberOfBraces(boolean numberOfBraces) {
		Features.numberOfBraces = numberOfBraces;
	}
	
	public static boolean isNumberOfApostrophes() {
		return numberOfApostrophes;
	}
	public static void setNumberOfApostrophes(boolean numberOfApostrophes) {
		Features.numberOfApostrophes = numberOfApostrophes;
	}
	
	public static boolean isNumberOfQuotationMarks() {
		return numberOfQuotationMarks;
	}
	public static void setNumberOfQuotationMarks(boolean numberOfQuotationMarks) {
		Features.numberOfQuotationMarks = numberOfQuotationMarks;
	}
	
	public static boolean isTotalNumberOfCharacters() {
		return totalNumberOfCharacters;
	}
	public static void setTotalNumberOfCharacters(boolean totalNumberOfCharacters) {
		Features.totalNumberOfCharacters = totalNumberOfCharacters;
	}
	
	public static boolean isTotalNumberOfWords() {
		return totalNumberOfWords;
	}
	public static void setTotalNumberOfWords(boolean totalNumberOfWords) {
		Features.totalNumberOfWords = totalNumberOfWords;
	}
	
	public static boolean isTotalNumberOfSentences() {
		return totalNumberOfSentences;
	}
	public static void setTotalNumberOfSentences(boolean totalNumberOfSentences) {
		Features.totalNumberOfSentences = totalNumberOfSentences;
	}
	
	public static boolean isAverageNumberOfCharactersPerSentence() {
		return averageNumberOfCharactersPerSentence;
	}
	public static void setAverageNumberOfCharactersPerSentence(boolean averageNumberOfCharactersPerSentence) {
		Features.averageNumberOfCharactersPerSentence = averageNumberOfCharactersPerSentence;
	}
	
	public static boolean isAverageNumberOfWordsPerSentence() {
		return averageNumberOfWordsPerSentence;
	}
	public static void setAverageNumberOfWordsPerSentence(boolean averageNumberOfWordsPerSentence) {
		Features.averageNumberOfWordsPerSentence = averageNumberOfWordsPerSentence;
	}
	
	
	// Unigram-based features
	
	public static boolean isUseUnigramFeatures() {
		return useUnigramFeatures;
	}
	public static void setUseUnigramFeatures(boolean useUnigramFeatures) {
		Features.useUnigramFeatures = useUnigramFeatures;
	}
	
	public static int getDepth() {
		return depth;
	}
	public static void setDepth(int depth) {
		Features.depth = depth;
	}

	public static boolean isUseUnigramNouns() {
		return useUnigramNouns;
	}
	public static void setUseUnigramNouns(boolean useUnigramNouns) {
		Features.useUnigramNouns = useUnigramNouns;
	}

	public static boolean isUseUnigramVerbs() {
		return useUnigramVerbs;
	}
	public static void setUseUnigramVerbs(boolean useUnigramVerbs) {
		Features.useUnigramVerbs = useUnigramVerbs;
	}

	public static boolean isUseUnigramAdjectives() {
		return useUnigramAdjectives;
	}
	public static void setUseUnigramAdjectives(boolean useUnigramAdjectives) {
		Features.useUnigramAdjectives = useUnigramAdjectives;
	}

	public static boolean isUseUnigramAdverbs() {
		return useUnigramAdverbs;
	}
	public static void setUseUnigramAdverbs(boolean useUnigramAdverbs) {
		Features.useUnigramAdverbs = useUnigramAdverbs;
	}

	public static TypeOfPos getUnigramTypeOfPos() {
		return unigramTypeOfPos;
	}
	public static void setUnigramTypeOfPos(TypeOfPos unigramTypeOfPos) {
		Features.unigramTypeOfPos = unigramTypeOfPos;
	}

	public static boolean isZeroTaken() {
		return zeroTaken;
	}
	public static void setZeroTaken(boolean zeroTaken) {
		Features.zeroTaken = zeroTaken;
	}

	public static boolean isOppositeTaken() {
		return oppositeTaken;
	}
	public static void setOppositeTaken(boolean oppositeTaken) {
		Features.oppositeTaken = oppositeTaken;
	}
	
	
	// Top Words based Features
	public static boolean isUseTopWords() {
		return useTopWords;
	}
	public static void setUseTopWords(boolean useTopWords) {
		Features.useTopWords = useTopWords;
	}

	public static boolean isUseTopWordsNouns() {
		return useTopWordsNouns;
	}
	public static void setUseTopWordsNouns(boolean useTopWordsNouns) {
		Features.useTopWordsNouns = useTopWordsNouns;
	}

	public static boolean isUseTopWordsVerbs() {
		return useTopWordsVerbs;
	}
	public static void setUseTopWordsVerbs(boolean useTopWordsVerbs) {
		Features.useTopWordsVerbs = useTopWordsVerbs;
	}

	public static boolean isUseTopWordsAdjectives() {
		return useTopWordsAdjectives;
	}
	public static void setUseTopWordsAdjectives(boolean useTopWordsAdjectives) {
		Features.useTopWordsAdjectives = useTopWordsAdjectives;
	}

	public static boolean isUseTopWordsAdverbs() {
		return useTopWordsAdverbs;
	}
	public static void setUseTopWordsAdverbs(boolean useTopWordsAdverbs) {
		Features.useTopWordsAdverbs = useTopWordsAdverbs;
	}

	public static TypeOfPos getTopWordsTypeOfPos() {
		return topWordsTypeOfPos;
	}
	public static void setTopWordsTypeOfPos(TypeOfPos topWordsTypeOfPos) {
		Features.topWordsTypeOfPos = topWordsTypeOfPos;
	}

	public static int getNumberOfTopWordsPerClass() {
		return numberOfTopWordsPerClass;
	}
	public static void setNumberOfTopWordsPerClass(int numberOfTopWordsPerClass) {
		Features.numberOfTopWordsPerClass = numberOfTopWordsPerClass;
	}

	public static int getNumberOfTopWordsPerPos() {
		return numberOfTopWordsPerPos;
	}
	public static void setNumberOfTopWordsPerPos(int numberOfTopWordsPerPos) {
		Features.numberOfTopWordsPerPos = numberOfTopWordsPerPos;
	}

	public static TypeOfFeature getTopWordsType() {
		return topWordsType;
	}
	public static void setTopWordsType(TypeOfFeature topWordsType) {
		Features.topWordsType = topWordsType;
	}

	public static double getTopWordsMinRatio() {
		return topWordsMinRatio;
	}
	public static void setTopWordsMinRatio(double topWordsMinRatio) {
		Features.topWordsMinRatio = topWordsMinRatio;
	}

	public static int getTopWordsMinOccurrence() {
		return topWordsMinOccurrence;
	}
	public static void setTopWordsMinOccurrence(int topWordsMinOccurrence) {
		Features.topWordsMinOccurrence = topWordsMinOccurrence;
	}
	
	
	// Stylistic features
	public static boolean isUseStylisticFeatures() {
		return useStylisticFeatures;
	}
	public static void setUseStylisticFeatures(boolean useStylisticFeatures) {
		Features.useStylisticFeatures = useStylisticFeatures;
	}

	public static boolean isNumberOfNouns() {
		return numberOfNouns;
	}
	public static void setNumberOfNouns(boolean numberOfNouns) {
		Features.numberOfNouns = numberOfNouns;
	}

	public static boolean isRatioOfNouns() {
		return ratioOfNouns;
	}
	public static void setRatioOfNouns(boolean ratioOfNouns) {
		Features.ratioOfNouns = ratioOfNouns;
	}

	public static boolean isNumberOfVerbs() {
		return numberOfVerbs;
	}
	public static void setNumberOfVerbs(boolean numberOfVerbs) {
		Features.numberOfVerbs = numberOfVerbs;
	}

	public static boolean isRatioOfVerbs() {
		return ratioOfVerbs;
	}
	public static void setRatioOfVerbs(boolean ratioOfVerbs) {
		Features.ratioOfVerbs = ratioOfVerbs;
	}

	public static boolean isNumberOfAdjectives() {
		return numberOfAdjectives;
	}
	public static void setNumberOfAdjectives(boolean numberOfAdjectives) {
		Features.numberOfAdjectives = numberOfAdjectives;
	}

	public static boolean isRatioOfAdjectives() {
		return ratioOfAdjectives;
	}
	public static void setRatioOfAdjectives(boolean ratioOfAdjectives) {
		Features.ratioOfAdjectives = ratioOfAdjectives;
	}

	public static boolean isNumberOfAdverbs() {
		return numberOfAdverbs;
	}
	public static void setNumberOfAdverbs(boolean numberOfAdverbs) {
		Features.numberOfAdverbs = numberOfAdverbs;
	}

	public static boolean isRatioOfAdverbs() {
		return ratioOfAdverbs;
	}
	public static void setRatioOfAdverbs(boolean ratioOfAdverbs) {
		Features.ratioOfAdverbs = ratioOfAdverbs;
	}

	public static boolean isUseOfSymbols() {
		return useOfSymbols;
	}
	public static void setUseOfSymbols(boolean useOfSymbols) {
		Features.useOfSymbols = useOfSymbols;
	}

	public static boolean isUseOfComparativeForm() {
		return useOfComparativeForm;
	}
	public static void setUseOfComparativeForm(boolean useOfComparativeForm) {
		Features.useOfComparativeForm = useOfComparativeForm;
	}

	public static boolean isUseOfSuperlativeForm() {
		return useOfSuperlativeForm;
	}
	public static void setUseOfSuperlativeForm(boolean useOfSuperlativeForm) {
		Features.useOfSuperlativeForm = useOfSuperlativeForm;
	}

	public static boolean isUseOfProperNouns() {
		return useOfProperNouns;
	}
	public static void setUseOfProperNouns(boolean useOfProperNouns) {
		Features.useOfProperNouns = useOfProperNouns;
	}

	public static boolean isTotalNumberOfParticles() {
		return totalNumberOfParticles;
	}
	public static void setTotalNumberOfParticles(boolean totalNumberOfParticles) {
		Features.totalNumberOfParticles = totalNumberOfParticles;
	}

	public static boolean isAverageNumberOfParticles() {
		return averageNumberOfParticles;
	}
	public static void setAverageNumberOfParticles(boolean averageNumberOfParticles) {
		Features.averageNumberOfParticles = averageNumberOfParticles;
	}

	public static boolean isTotalNumberOfInterjections() {
		return totalNumberOfInterjections;
	}
	public static void setTotalNumberOfInterjections(boolean totalNumberOfInterjections) {
		Features.totalNumberOfInterjections = totalNumberOfInterjections;
	}

	public static boolean isAverageNumberOfInterjections() {
		return averageNumberOfInterjections;
	}
	public static void setAverageNumberOfInterjections(boolean averageNumberOfInterjections) {
		Features.averageNumberOfInterjections = averageNumberOfInterjections;
	}

	public static boolean isTotalNumberOfPronouns() {
		return totalNumberOfPronouns;
	}
	public static void setTotalNumberOfPronouns(boolean totalNumberOfPronouns) {
		Features.totalNumberOfPronouns = totalNumberOfPronouns;
	}

	public static boolean isAverageNumberOfPronouns() {
		return averageNumberOfPronouns;
	}
	public static void setAverageNumberOfPronouns(boolean averageNumberOfPronouns) {
		Features.averageNumberOfPronouns = averageNumberOfPronouns;
	}

	public static boolean isTotalNumberOfPronounsGroup1() {
		return totalNumberOfPronounsGroup1;
	}
	public static void setTotalNumberOfPronounsGroup1(boolean totalNumberOfPronounsGroup1) {
		Features.totalNumberOfPronounsGroup1 = totalNumberOfPronounsGroup1;
	}

	public static boolean isAverageNumberOfPronounsGroup1() {
		return averageNumberOfPronounsGroup1;
	}
	public static void setAverageNumberOfPronounsGroup1(boolean averageNumberOfPronounsGroup1) {
		Features.averageNumberOfPronounsGroup1 = averageNumberOfPronounsGroup1;
	}

	public static boolean isTotalNumberOfPronounsGroup2() {
		return totalNumberOfPronounsGroup2;
	}
	public static void setTotalNumberOfPronounsGroup2(boolean totalNumberOfPronounsGroup2) {
		Features.totalNumberOfPronounsGroup2 = totalNumberOfPronounsGroup2;
	}

	public static boolean isAverageNumberOfPronounsGroup2() {
		return averageNumberOfPronounsGroup2;
	}
	public static void setAverageNumberOfPronounsGroup2(boolean averageNumberOfPronounsGroup2) {
		Features.averageNumberOfPronounsGroup2 = averageNumberOfPronounsGroup2;
	}

	public static boolean isUseOfNegation() {
		return useOfNegation;
	}
	public static void setUseOfNegation(boolean useOfNegation) {
		Features.useOfNegation = useOfNegation;
	}
	
	public static boolean isUseOfUncommonWords() {
		return useOfUncommonWords;
	}
	public static void setUseOfUncommonWords(boolean useOfUncommonWords) {
		Features.useOfUncommonWords = useOfUncommonWords;
	}

	public static boolean isNumberOfUncommonWords() {
		return numberOfUncommonWords;
	}
	public static void setNumberOfUncommonWords(boolean numberOfUncommonWords) {
		Features.numberOfUncommonWords = numberOfUncommonWords;
	}
	
	
	// Pattern Features
	public static boolean isUsePatternFeatures() {
		return usePatternFeatures;
	}
	public static void setUsePatternFeatures(boolean usePatternFeatures) {
		Features.usePatternFeatures = usePatternFeatures;
	}
	
	public static TypeOfFeature getPatternFeaturesType() {
		return patternFeaturesType;
	}
	public static void setPatternFeaturesType(TypeOfFeature patternFeaturesType) {
		Features.patternFeaturesType = patternFeaturesType;
	}

	public static int getPatternLength() {
		return patternLength;
	}
	public static void setPatternLength(int patternLength) {
		Features.patternLength = patternLength;
	}

	public static int getNumberOfPatternsPerClass() {
		return numberOfPatternsPerClass;
	}
	public static void setNumberOfPatternsPerClass(int numberOfPatternsPerClass) {
		Features.numberOfPatternsPerClass = numberOfPatternsPerClass;
	}
	
	public static int getMinPatternLength() {
		return minPatternLength;
	}
	public static void setMinPatternLength(int minPatternLength) {
		Features.minPatternLength = minPatternLength;
	}

	public static int getMaxPatternLength() {
		return maxPatternLength;
	}
	public static void setMaxPatternLength(int maxPatternLength) {
		Features.maxPatternLength = maxPatternLength;
	}

	public static int getNumberOfPosCategories() {
		return numberOfPosCategories;
	}
	public static void setNumberOfPosCategories(int numberOfPosCategories) {
		Features.numberOfPosCategories = numberOfPosCategories;
	}

	public static boolean isCategory1() {
		return category1;
	}
	public static void setCategory1(boolean category1) {
		Features.category1 = category1;
	}

	public static boolean isCategory2() {
		return category2;
	}
	public static void setCategory2(boolean category2) {
		Features.category2 = category2;
	}

	public static boolean isCategory3() {
		return category3;
	}
	public static void setCategory3(boolean category3) {
		Features.category3 = category3;
	}

	public static boolean isCategory4() {
		return category4;
	}
	public static void setCategory4(boolean category4) {
		Features.category4 = category4;
	}

	public static PatternActions getAction1() {
		return action1;
	}
	public static void setAction1(PatternActions action1) {
		Features.action1 = action1;
	}

	public static PatternActions getAction2() {
		return action2;
	}
	public static void setAction2(PatternActions action2) {
		Features.action2 = action2;
	}

	public static PatternActions getAction3() {
		return action3;
	}
	public static void setAction3(PatternActions action3) {
		Features.action3 = action3;
	}

	public static PatternActions getAction4() {
		return action4;
	}
	public static void setAction4(PatternActions action4) {
		Features.action4 = action4;
	}

	public static String getReplacement1() {
		return replacement1;
	}
	public static void setReplacement1(String replacement1) {
		Features.replacement1 = replacement1;
	}

	public static String getReplacement2() {
		return replacement2;
	}
	public static void setReplacement2(String replacement2) {
		Features.replacement2 = replacement2;
	}

	public static String getReplacement3() {
		return replacement3;
	}
	public static void setReplacement3(String replacement3) {
		Features.replacement3 = replacement3;
	}

	public static String getReplacement4() {
		return replacement4;
	}
	public static void setReplacement4(String replacement4) {
		Features.replacement4 = replacement4;
	}

	public static int getPatternsMinOccurrence() {
		return patternsMinOccurrence;
	}
	public static void setPatternsMinOccurrence(int patternsMinOccurrence) {
		Features.patternsMinOccurrence = patternsMinOccurrence;
	}

	public static double getAlpha() {
		return alpha;
	}
	public static void setAlpha(double alpha) {
		Features.alpha = alpha;
	}

	public static double getGamma() {
		return gamma;
	}
	public static void setGamma(double gamma) {
		Features.gamma = gamma;
	}

	public static int getBasicPatternsKnn() {
		return basicPatternsKnn;
	}
	public static void setBasicPatternsKnn(int basicPatternsKnn) {
		Features.basicPatternsKnn = basicPatternsKnn;
	}
	
	public static Categories[] getCategories() {
		return categories;
	}
	public static void setCategories(Categories[] categories) {
		Features.categories = categories;
	}
	
	public static HashMap<String, Categories> getCategoriesMap() {
		return categoriesMap;
	}
	public static void setCategoriesMap(HashMap<String, Categories> categoriesMap) {
		Features.categoriesMap = categoriesMap;
	}


	// Semantic Features
	public static boolean isUseSemanticFeatures() {
		return useSemanticFeatures;
	}
	public static void setUseSemanticFeatures(boolean useSemanticFeatures) {
		Features.useSemanticFeatures = useSemanticFeatures;
	}

	public static boolean isUseOfOpinionWords() {
		return useOfOpinionWords;
	}
	public static void setUseOfOpinionWords(boolean useOfOpinionWords) {
		Features.useOfOpinionWords = useOfOpinionWords;
	}
	
	public static boolean isUseOfHighlySentimentalWords() {
		return useOfHighlySentimentalWords;
	}
	public static void setUseOfHighlySentimentalWords(boolean useOfHighlySentimentalWords) {
		Features.useOfHighlySentimentalWords = useOfHighlySentimentalWords;
	}
	
	
	public static boolean isUseOfUncertaintyWords() {
		return useOfUncertaintyWords;
	}
	public static void setUseOfUncertaintyWords(boolean useOfUncertaintyWords) {
		Features.useOfUncertaintyWords = useOfUncertaintyWords;
	}
	

	public static boolean isUseOfActiveForm() {
		return useOfActiveForm;
	}
	public static void setUseOfActiveForm(boolean useOfActiveForm) {
		Features.useOfActiveForm = useOfActiveForm;
	}
	
	public static boolean isUseOfPassiveForm() {
		return useOfPassiveForm;
	}
	public static void setUseOfPassiveForm(boolean useOfPassiveForm) {
		Features.useOfPassiveForm = useOfPassiveForm;
	}
	
	
	// Advanced Sentiment Features
	public static boolean isUseAdvancedSentimentFeatures() {
		return useAdvancedSentimentFeatures;
	}
	public static void setUseAdvancedSentimentFeatures(boolean useAdvancedSentimentFeatures) {
		Features.useAdvancedSentimentFeatures = useAdvancedSentimentFeatures;
	}

	public static AdvancedTypeOfFeatures getTypeOfAdvancedSentimentFeatures() {
		return typeOfAdvancedSentimentFeatures;
	}
	public static void setTypeOfAdvancedSentimentFeatures(AdvancedTypeOfFeatures typeOfAdvancedSentimentFeatures) {
		Features.typeOfAdvancedSentimentFeatures = typeOfAdvancedSentimentFeatures;
	}
	
	public static int getNumberOfWordsBefore() {
		return numberOfWordsBefore;
	}
	public static void setNumberOfWordsBefore(int numberOfWordsBefore) {
		Features.numberOfWordsBefore = numberOfWordsBefore;
	}
	
	public static int getNumberOfWordsAfter() {
		return numberOfWordsAfter;
	}
	public static void setNumberOfWordsAfter(int numberOfWordsAfter) {
		Features.numberOfWordsAfter = numberOfWordsAfter;
	}
	
	public static boolean isAddSentimentClassInformation() {
		return addSentimentClassInformation;
	}
	public static void setAddSentimentClassInformation(boolean addSentimentClassInformation) {
		Features.addSentimentClassInformation = addSentimentClassInformation;
	}
	
	
	// Advanced Semantic Features
	public static boolean isUseAdvancedSemanticFeatures() {
		return useAdvancedSemanticFeatures;
	}
	public static void setUseAdvancedSemanticFeatures(boolean useAdvancedSemanticFeatures) {
		Features.useAdvancedSemanticFeatures = useAdvancedSemanticFeatures;
	}
	
	public static TopicRelated getWordsIntoAccount() {
		return wordsIntoAccount;
	}
	public static void setWordsIntoAccount(TopicRelated wordsIntoAccount) {
		Features.wordsIntoAccount = wordsIntoAccount;
	}
	
	public static boolean isCountOtherWords() {
		return countOtherWords;
	}
	public static void setCountOtherWords(boolean countOtherWords) {
		Features.countOtherWords = countOtherWords;
	}
	
	public static TypeOfPos getAdvancedSemanticPos() {
		return advancedSemanticPos;
	}
	public static void setAdvancedSemanticPos(TypeOfPos advancedSemanticPos) {
		Features.advancedSemanticPos = advancedSemanticPos;
	}
	
	public static boolean isAdvUseOfPositiveWords() {
		return advUseOfPositiveWords;
	}
	public static void setAdvUseOfPositiveWords(boolean advUseOfPositiveWords) {
		Features.advUseOfPositiveWords = advUseOfPositiveWords;
	}
	
	public static boolean isAdvUseOfNegativeWords() {
		return advUseOfNegativeWords;
	}
	public static void setAdvUseOfNegativeWords(boolean advUseOfNegativeWords) {
		Features.advUseOfNegativeWords = advUseOfNegativeWords;
	}
	
	public static boolean isAdvUseOfHighlyPositiveWords() {
		return advUseOfHighlyPositiveWords;
	}
	public static void setAdvUseOfHighlyPositiveWords(boolean advUseOfHighlyPositiveWords) {
		Features.advUseOfHighlyPositiveWords = advUseOfHighlyPositiveWords;
	}
	
	public static boolean isAdvUseOfHighlyNegativeWords() {
		return advUseOfHighlyNegativeWords;
	}
	public static void setAdvUseOfHighlyNegativeWords(boolean advUseOfHighlyNegativeWords) {
		Features.advUseOfHighlyNegativeWords = advUseOfHighlyNegativeWords;
	}
	
	public static boolean isAdvUseOfOpinionWords() {
		return advUseOfOpinionWords;
	}
	public static void setAdvUseOfOpinionWords(boolean advUseOfOpinionWords) {
		Features.advUseOfOpinionWords = advUseOfOpinionWords;
	}
	
	public static boolean isAdvUseOfUncertaintyWords() {
		return advUseOfUncertaintyWords;
	}
	public static void setAdvUseOfUncertaintyWords(boolean advUseOfUncertaintyWords) {
		Features.advUseOfUncertaintyWords = advUseOfUncertaintyWords;
	}
	
	public static boolean isAdvUseOfActiveForm() {
		return advUseOfActiveForm;
	}
	public static void setAdvUseOfActiveForm(boolean advUseOfActiveForm) {
		Features.advUseOfActiveForm = advUseOfActiveForm;
	}
	
	public static boolean isAdvUseOfPassiveForm() {
		return advUseOfPassiveForm;
	}
	public static void setAdvUseOfPassiveForm(boolean advUseOfPassiveForm) {
		Features.advUseOfPassiveForm = advUseOfPassiveForm;
	}
	
	
	// Advanced Pattern Features
	public static boolean isUseAdvancedPatternFeatures() {
		return useAdvancedPatternFeatures;
	}
	public static void setUseAdvancedPatternFeatures(boolean useAdvancedPatternFeatures) {
		Features.useAdvancedPatternFeatures = useAdvancedPatternFeatures;
	}

	public static TypeOfFeature getAdvancedPatternFeaturesType() {
		return advancedPatternFeaturesType;
	}
	public static void setAdvancedPatternFeaturesType(TypeOfFeature advancedPatternFeaturesType) {
		Features.advancedPatternFeaturesType = advancedPatternFeaturesType;
	}
	
	public static int getAdvancedPatternLength() {
		return advancedPatternLength;
	}
	public static void setAdvancedPatternLength(int advancedPatternLength) {
		Features.advancedPatternLength = advancedPatternLength;
	}
	
	public static int getAdvancedNumberOfPatternsPerClass() {
		return advancedNumberOfPatternsPerClass;
	}
	public static void setAdvancedNumberOfPatternsPerClass(int advancedNumberOfPatternsPerClass) {
		Features.advancedNumberOfPatternsPerClass = advancedNumberOfPatternsPerClass;
	}
	
	public static int getAdvancedMinPatternLength() {
		return advancedMinPatternLength;
	}
	public static void setAdvancedMinPatternLength(int advancedMinPatternLength) {
		Features.advancedMinPatternLength = advancedMinPatternLength;
	}
	
	public static int getAdvancedMaxPatternLength() {
		return advancedMaxPatternLength;
	}
	public static void setAdvancedMaxPatternLength(int advancedMaxPatternLength) {
		Features.advancedMaxPatternLength = advancedMaxPatternLength;
	}
	
	public static int getAdvancedPatternsMinOccurrence() {
		return advancedPatternsMinOccurrence;
	}
	public static void setAdvancedPatternsMinOccurrence(int advancedPatternsMinOccurrence) {
		Features.advancedPatternsMinOccurrence = advancedPatternsMinOccurrence;
	}
	
	public static double getAdvancedPatternAlpha() {
		return advancedPatternAlpha;
	}
	public static void setAdvancedPatternAlpha(double advancedPatternAlpha) {
		Features.advancedPatternAlpha = advancedPatternAlpha;
	}
	
	public static double getAdvancedPatternGamma() {
		return advancedPatternGamma;
	}
	public static void setAdvancedPatternGamma(double advancedPatternGamma) {
		Features.advancedPatternGamma = advancedPatternGamma;
	}
	
	public static int getAdvancedPatternsKnn() {
		return advancedPatternsKnn;
	}
	public static void setAdvancedPatternsKnn(int advancedPatternsKnn) {
		Features.advancedPatternsKnn = advancedPatternsKnn;
	}
	
	
	// Advanced Unigram Features
	public static boolean isUseAdvancedUnigramFeatures() {
		return useAdvancedUnigramFeatures;
	}
	public static void setUseAdvancedUnigramFeatures(boolean isUseAdvancedUnigramFeatures) {
		Features.useAdvancedUnigramFeatures = isUseAdvancedUnigramFeatures;
	}
	
	public static IsLemma getIsLemma() {
		return isLemma;
	}
	public static void setIsLemma(IsLemma isLemma) {
		Features.isLemma = isLemma;
	}
	
	
	// Quantification related
	public static double[][] getCoefficients() {
		return coefficients;
	}
	public static void setCoefficients(double[][] coefficients) {
		Features.coefficients = coefficients;
	}

	public static boolean isUseManualParameters() {
		return useManualParameters;
	}
	public static void setUseManualParameters(boolean useManualParameters) {
		Features.useManualParameters = useManualParameters;
	}
	
	public static double[][] getManualCoefficients() {
		return manualCoefficients;
	}
	public static void setManualCoefficients(double[][] manualCoefficients) {
		Features.manualCoefficients = manualCoefficients;
	}

	public static boolean isUseQuantifUnigrams() {
		return useQuantifUnigrams;
	}
	public static void setUseQuantifUnigrams(boolean useQuantifUnigrams) {
		Features.useQuantifUnigrams = useQuantifUnigrams;
	}

	public static boolean isUseQuantifBasicPatterns() {
		return useQuantifBasicPatterns;
	}
	public static void setUseQuantifBasicPatterns(boolean useQuantifBasicPatterns) {
		Features.useQuantifBasicPatterns = useQuantifBasicPatterns;
	}

	public static boolean isUseQuantifAdvancedPatterns() {
		return useQuantifAdvancedPatterns;
	}
	public static void setUseQuantifAdvancedPatterns(boolean useQuantifAdvancedPatterns) {
		Features.useQuantifAdvancedPatterns = useQuantifAdvancedPatterns;
	}

	public static double getSentimentsThreshold() {
		return sentimentsThreshold;
	}
	public static void setSentimentsThreshold(double sentimentsThreshold) {
		Features.sentimentsThreshold = sentimentsThreshold;
	}
	
	public static boolean isOutputPredictions() {
		return outputPredictions;
	}
	public static void setOutputPredictions(boolean outputPredictions) {
		Features.outputPredictions = outputPredictions;
	}
	
	// =====================================//
	//            REINITIALIZER             //
	// =====================================//
	
	/**
	 * Re-initialize all the features to the [null / 0 / false] values
	 */
	public static void reinitialize() {
		
		// Sentiment Features
		useSentimentFeatures = false;
		numberOfPositiveWords = false;
		numberOfNegativeWords = false;
		numberOfHighlyEmoPositiveWords = false;
		numberOfHighlyEmoNegativeWords = false;
		numberOfCapitalizedPositiveWords = false;
		numberOfCapitalizedNegativeWords = false;
		ratioOfEmotionalWords = false;
		numberOfPositiveEmoticons = false;
		numberOfNegativeEmoticons = false;
		numberOfNeutralEmoticons = false;
		numberOfJokingEmoticons = false;
		numberOfPositiveSlangs = false;
		numberOfNegativeSlangs = false;
		numberOfPositiveHashtags = false;
		numberOfNegativeHashtags = false;
		contrastWordsVsWords = false;
		contrastWordsVsHashtags = false;
		contrastWordsVsEmoticons = false;
		contrastHashtagsVsHashtags = false;
		contrastHashtagsVsEmoticons = false;
		
		// Punctuation features
		usePunctuationFeatures = false;
		numberOfDots = false;
		numberOfCommas = false;
		numberOfSemicolons = false;
		numberOfExclamationMarks = false;
		numberOfQuestionMarks = false;
		numberOfParentheses = false;
		numberOfBrackets = false;
		numberOfBraces = false;
		numberOfApostrophes = false;
		numberOfQuotationMarks = false;
		totalNumberOfCharacters = false;
		totalNumberOfWords = false;
		totalNumberOfSentences = false;
		averageNumberOfCharactersPerSentence = false;
		averageNumberOfWordsPerSentence = false;
		
		// Sytlistic features
		useStylisticFeatures = false;
		numberOfNouns = false;
		ratioOfNouns = false;
		numberOfVerbs = false;
		ratioOfVerbs = false;
		numberOfAdjectives = false;
		ratioOfAdjectives = false;
		numberOfAdverbs = false;
		ratioOfAdverbs = false;
		useOfSymbols = false;
		useOfComparativeForm = false;
		useOfSuperlativeForm = false;
		useOfProperNouns = false;
		totalNumberOfParticles = false;
		averageNumberOfParticles = false;
		totalNumberOfInterjections = false;
		averageNumberOfInterjections = false;
		totalNumberOfPronouns = false;
		averageNumberOfPronouns = false;
		totalNumberOfPronounsGroup1 = false;
		averageNumberOfPronounsGroup1 = false;
		totalNumberOfPronounsGroup2 = false;
		averageNumberOfPronounsGroup2 = false;
		useOfNegation = false;
		useOfUncommonWords = false;
		numberOfUncommonWords = false;
		
		// Semantic Feature
		useSemanticFeatures = false;
		useOfOpinionWords = false;
		useOfHighlySentimentalWords = false;
		useOfUncertaintyWords = false;
		useOfActiveForm = false;
		useOfPassiveForm = false;
		
		// Unigram Features
		useUnigramFeatures = false;
		depth = 0;
		useUnigramNouns = false;
		useUnigramVerbs = false;
		useUnigramAdjectives = false;
		useUnigramAdverbs = false;
		unigramTypeOfPos = null;
		zeroTaken = false;
		oppositeTaken = false;
		
		// Top Words Features
		useTopWords = false;
		useTopWordsNouns = false;
		useTopWordsVerbs = false;
		useTopWordsAdjectives = false;
		useTopWordsAdverbs = false;
		topWordsTypeOfPos = null;
		numberOfTopWordsPerClass = 0;
		numberOfTopWordsPerPos = 0;
		topWordsType = null;
		topWordsMinRatio = 0;
		topWordsMinOccurrence = 0;
		
		// Pattern Features
		usePatternFeatures = false;
		patternFeaturesType = null;
		patternLength = 0;
		numberOfPatternsPerClass = 0;
		minPatternLength = 0;
		maxPatternLength = 0;
		numberOfPosCategories = 0;
		category1 = false;
		category2 = false;
		category3 = false;
		category4 = false;
		action1 = null;
		action2 = null;
		action3 = null;
		action4 = null;
		replacement1 = null;
		replacement2 = null;
		replacement3 = null;
		replacement4 = null;
		patternsMinOccurrence = 0;
		alpha = 0;
		gamma = 0;
		categories = null;
		categoriesMap = null;
		
		// Advanced Sentiment Features
		useAdvancedSentimentFeatures = false;
		typeOfAdvancedSentimentFeatures = null;
		numberOfWordsBefore = 0;
		numberOfWordsAfter = 0;
		addSentimentClassInformation = false;
		
		// Advanced Semantic Features
		useAdvancedSemanticFeatures = false;
		wordsIntoAccount = null;
		countOtherWords = false;
		advancedSemanticPos = null;
		advUseOfPositiveWords = false;
		advUseOfNegativeWords = false;
		advUseOfHighlyPositiveWords = false;
		advUseOfHighlyNegativeWords = false;
		advUseOfOpinionWords = false;
		advUseOfUncertaintyWords = false;
		advUseOfActiveForm = false;
		advUseOfPassiveForm = false;
		
		// Advanced Pattern Features
		useAdvancedPatternFeatures = false;
		advancedPatternFeaturesType = null;
		advancedPatternLength = 0;
		advancedNumberOfPatternsPerClass = 0;
		advancedMinPatternLength = 0;
		advancedMaxPatternLength = 0;
		advancedPatternsMinOccurrence = 0;
		advancedPatternAlpha = 0;
		advancedPatternGamma = 0;
	}

	
}

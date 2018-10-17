package main.items;

public class ClassifierFeatures {
	
	//============================================//
	//              sets of features              //
	//============================================//
	
	protected static boolean useCustomizedFeatures;
	
	protected static boolean sentimentFeatures;
	protected static boolean punctuationFeatures;
	protected static boolean syntacticFeatures;
	protected static boolean semanticFeatures;
	protected static boolean unigramFeatures;
	protected static boolean topWordsFeatures;
	protected static boolean patternFeatures;
	protected static boolean advancedSentimentFeatures;
	protected static boolean advancedSemanticFeatures;
	protected static boolean advancedPatternFeatures;
	protected static boolean advancedUnigramFeatures;
	
	//============================================//
	//               File to process              //
	//============================================//
	
	protected static String currentArffFile;

	//============================================//
	//               File to process              //
	//============================================//
	
	public static void initialize() {
		
		sentimentFeatures = Features.isUseSentimentFeatures();
		punctuationFeatures = Features.isUsePunctuationFeatures();
		syntacticFeatures = Features.isUseStylisticFeatures();
		semanticFeatures = Features.isUseSemanticFeatures();
		unigramFeatures = Features.isUseUnigramFeatures();
		topWordsFeatures = Features.isUseTopWords();
		patternFeatures = Features.isUsePatternFeatures();
		
		advancedSentimentFeatures = Features.isUseAdvancedSentimentFeatures();
		advancedSemanticFeatures = Features.isUseAdvancedSemanticFeatures();
		advancedPatternFeatures = Features.isUseAdvancedPatternFeatures();
		advancedUnigramFeatures = Features.isUseAdvancedUnigramFeatures();
		
	}
	
	public static void rest() {
		initialize();
		useCustomizedFeatures = false;
	}
	
	
	//============================================//
	//            Getters and setters             //
	//============================================//
	
	// Is first time
	public static boolean isUseCustomizedFeatures() {
		return useCustomizedFeatures;
	}
	public static void setUseCustomizedFeatures(boolean useCustomizedFeatures) {
		ClassifierFeatures.useCustomizedFeatures = useCustomizedFeatures;
	}

	// Feature families
	public static boolean isSentimentFeatures() {
		return sentimentFeatures;
	}
	public static void setSentimentFeatures(boolean sentimentFeatures) {
		ClassifierFeatures.sentimentFeatures = sentimentFeatures;
	}

	public static boolean isPunctuationFeatures() {
		return punctuationFeatures;
	}
	public static void setPunctuationFeatures(boolean punctuationFeatures) {
		ClassifierFeatures.punctuationFeatures = punctuationFeatures;
	}

	public static boolean isSyntacticFeatures() {
		return syntacticFeatures;
	}
	public static void setSyntacticFeatures(boolean syntacticFeatures) {
		ClassifierFeatures.syntacticFeatures = syntacticFeatures;
	}

	public static boolean isSemanticFeatures() {
		return semanticFeatures;
	}
	public static void setSemanticFeatures(boolean semanticFeatures) {
		ClassifierFeatures.semanticFeatures = semanticFeatures;
	}

	public static boolean isUnigramFeatures() {
		return unigramFeatures;
	}
	public static void setUnigramFeatures(boolean unigramFeatures) {
		ClassifierFeatures.unigramFeatures = unigramFeatures;
	}

	public static boolean isTopWordsFeatures() {
		return topWordsFeatures;
	}
	public static void setTopWordsFeatures(boolean topWordsFeatures) {
		ClassifierFeatures.topWordsFeatures = topWordsFeatures;
	}

	public static boolean isPatternFeatures() {
		return patternFeatures;
	}
	public static void setPatternFeatures(boolean patternFeatures) {
		ClassifierFeatures.patternFeatures = patternFeatures;
	}

	public static boolean isAdvancedSentimentFeatures() {
		return advancedSentimentFeatures;
	}
	public static void setAdvancedSentimentFeatures(boolean advancedSentimentFeatures) {
		ClassifierFeatures.advancedSentimentFeatures = advancedSentimentFeatures;
	}

	public static boolean isAdvancedSemanticFeatures() {
		return advancedSemanticFeatures;
	}
	public static void setAdvancedSemanticFeatures(boolean advancedSemanticFeatures) {
		ClassifierFeatures.advancedSemanticFeatures = advancedSemanticFeatures;
	}

	public static boolean isAdvancedPatternFeatures() {
		return advancedPatternFeatures;
	}
	public static void setAdvancedPatternFeatures(boolean advancedPatternFeatures) {
		ClassifierFeatures.advancedPatternFeatures = advancedPatternFeatures;
	}

	public static boolean isAdvancedUnigramFeatures() {
		return advancedUnigramFeatures;
	}
	public static void setAdvancedUnigramFeatures(boolean advancedUnigramFeatures) {
		ClassifierFeatures.advancedUnigramFeatures = advancedUnigramFeatures;
	}

	public static String getCurrentArffFile() {
		return currentArffFile;
	}
	public static void setCurrentArffFile(String currentArffFile) {
		ClassifierFeatures.currentArffFile = currentArffFile;
	}
	

}

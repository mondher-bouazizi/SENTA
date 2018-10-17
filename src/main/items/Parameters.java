package main.items;

import java.util.ArrayList;
import java.util.HashMap;

import commons.constants.Constants;
import commons.io.Reader;
import main.start.Loader;

public class Parameters {
	
	//======================================//
	//             ENUMERATIONS             //
	//======================================//
	
	public enum TypeOfProject{
		TESTSET,
		NONANNOTATEDSET
	}
	
	
	//======================================//
	//              ATTRIBUTES              //
	//======================================//

	// Import Project-related parameters
	protected static String importedProjectDirectory;
	protected static String importedProjectName;
	
	protected static boolean isImportFeatures;
	protected static String featuresFileLocation;
	
	protected static boolean isImportTopWords;
	protected static String topWordsImportedFileLocation;
	
	protected static boolean isImportBasicPatterns;
	protected static String basicPatternsImportedFileLocation;
	
	protected static boolean isImportAdvancedPatterns;
	protected static String advancedPatternsImportedFileLocation;
	
	// Current-Project Related parameters
	protected static String projectLocation;
	protected static String projectName;
	
	protected static TypeOfProject typeOfProject;
	
	protected static ArrayList<String> classes;
	
	protected static String trainingSetLocation;
	protected static String testSetLocation;
	protected static String nonAnnotatedDataLocation;
	
	protected static boolean isSaveCsv;
	protected static boolean isSaveTxt;
	protected static boolean isSaveArff;
	
	protected static boolean isSaveTopWords;
	protected static String topWordsSavedFileLocation;
	
	protected static boolean isSaveBasicPatterns;
	protected static String basicPatternsSavedFileLocation;
	
	protected static boolean isSaveAdvancedPatterns;
	protected static String advancedPatternsSavedFileLocation;
	
	
	// Related to the collection of seed words
	protected static ArrayList<Word> seeds;
	protected static ArrayList<Word> unigrams;
	protected static HashMap<String, ArrayList<String>> rawUnigrams;
	
	protected static String seedsFileLocation;
	protected static String unigramsFileLocation;
	
	// Related to the Imported Unigrams
	protected static boolean isImportUnigramList;
	protected static String importedUnigramsLocation;
	protected static HashMap<String, Object> advancedUnigrams;
	
	
	// Related to how to read the files
	protected static int textIdPosition;
	protected static int userNamePosition;
	protected static int textPosition;
	protected static int classPosition;
	protected static int indexPosition;
	
	protected static boolean useAdavancedFeaturesAllowed;
	
	
	// Data sets
	protected static ArrayList<Tweet> trainingSet;
	protected static ArrayList<Tweet> testSet;
	protected static ArrayList<Tweet> unknownSet;
	
	
	// Top Words lists
	protected static HashMap<String, ArrayList<String>> topWords;
	
	protected static HashMap<String, ArrayList<String>> topNouns;
	protected static HashMap<String, ArrayList<String>> topVerbs;
	protected static HashMap<String, ArrayList<String>> topAdjectives;
	protected static HashMap<String, ArrayList<String>> topAdverbs;
	
	
	// Basic Patterns lists
	public static HashMap<String, ArrayList<Pattern>> singleLengthPatterns;
	public static HashMap<String, HashMap<Integer, ArrayList<Pattern>>> multiLengthPatterns;
	
	public static HashMap<String, ArrayList<PatternNumeric>> singleLengthPatternsNumeric;
	public static HashMap<String, HashMap<Integer, ArrayList<PatternNumeric>>> multiLengthPatternsNumeric;
	
	
	// Advanced Patterns lists
	public static HashMap<String, ArrayList<Pattern>> singleLengthAdvancedPatterns;
	public static HashMap<String, HashMap<Integer, ArrayList<Pattern>>> multiLengthAdvancedPatterns;
	
	public static HashMap<String, ArrayList<PatternNumeric>> singleLengthAdvancedPatternsNumeric;
	public static HashMap<String, HashMap<Integer, ArrayList<PatternNumeric>>> multiLengthAdvancedPatternsNumeric;
	
	//======================================//
	//         GETTERS AND SETTERS          //
	//======================================//
	
	// Import Project-related parameters
	public static String getImportedProjectDirectory() {
		return importedProjectDirectory;
	}
	public static void setImportedProjectDirectory(String importedProjectDirectory) {
		Parameters.importedProjectDirectory = importedProjectDirectory;
	}
	
	public static String getImportedProjectName() {
		return importedProjectName;
	}
	public static void setImportedProjectName(String importedProjectName) {
		Parameters.importedProjectName = importedProjectName;
	}
	
	public static boolean isImportFeatures() {
		return isImportFeatures;
	}
	public static void setImportFeatures(boolean isImportFeatures) {
		Parameters.isImportFeatures = isImportFeatures;
	}
	
	public static String getFeaturesFileLocation() {
		return featuresFileLocation;
	}
	public static void setFeaturesFileLocation(String featuresFileLocation) {
		Parameters.featuresFileLocation = featuresFileLocation;
	}

	public static boolean isImportTopWords() {
		return isImportTopWords;
	}
	public static void setImportTopWords(boolean isImportTopWords) {
		Parameters.isImportTopWords = isImportTopWords;
	}

	public static String getTopWordsImportedFileLocation() {
		return topWordsImportedFileLocation;
	}
	public static void setTopWordsImportedFileLocation(String topWordsImportedFileLocation) {
		Parameters.topWordsImportedFileLocation = topWordsImportedFileLocation;
	}

	public static boolean isImportBasicPatterns() {
		return isImportBasicPatterns;
	}
	public static void setImportBasicPatterns(boolean isImportBasicPatterns) {
		Parameters.isImportBasicPatterns = isImportBasicPatterns;
	}

	public static String getBasicPatternsImportedFileLocation() {
		return basicPatternsImportedFileLocation;
	}
	public static void setBasicPatternsImportedFileLocation(String basicPatternsImportedFileLocation) {
		Parameters.basicPatternsImportedFileLocation = basicPatternsImportedFileLocation;
	}
	
	public static boolean isImportAdvancedPatterns() {
		return isImportAdvancedPatterns;
	}
	public static void setImportAdvancedPatterns(boolean isImportAdvancedPatterns) {
		Parameters.isImportAdvancedPatterns = isImportAdvancedPatterns;
	}
	
	public static String getAdvancedPatternsImportedFileLocation() {
		return advancedPatternsImportedFileLocation;
	}
	public static void setAdvancedPatternsImportedFileLocation(String advancedPatternsImportedFileLocation) {
		Parameters.advancedPatternsImportedFileLocation = advancedPatternsImportedFileLocation;
	}
	
	// Current-Project Related parameters
	public static String getProjectLocation() {
		return projectLocation;
	}
	public static void setProjectLocation(String projectLocation) {
		Parameters.projectLocation = projectLocation;
	}

	public static String getProjectName() {
		return projectName;
	}
	public static void setProjectName(String projectName) {
		Parameters.projectName = projectName;
	}
	
	public static TypeOfProject getTypeOfProject() {
		return typeOfProject;
	}
	public static void setTypeOfProject(TypeOfProject typeOfProject) {
		Parameters.typeOfProject = typeOfProject;
	}
	
	public static ArrayList<String> getClasses() {
		return classes;
	}
	public static void setClasses(ArrayList<String> classes) {
		Parameters.classes = classes;
	}
	
	public static String getTrainingSetLocation() {
		return trainingSetLocation;
	}
	public static void setTrainingSetLocation(String trainingSetLocation) {
		Parameters.trainingSetLocation = trainingSetLocation;
	}
	
	public static String getTestSetLocation() {
		return testSetLocation;
	}
	public static void setTestSetLocation(String testSetLocation) {
		Parameters.testSetLocation = testSetLocation;
	}
	
	public static String getNonAnnotatedDataLocation() {
		return nonAnnotatedDataLocation;
	}
	public static void setNonAnnotatedDataLocation(String nonAnnotatedDataLocation) {
		Parameters.nonAnnotatedDataLocation = nonAnnotatedDataLocation;
	}
	
	public static boolean isSaveCsv() {
		return isSaveCsv;
	}
	public static void setSaveCsv(boolean isSaveCsv) {
		Parameters.isSaveCsv = isSaveCsv;
	}
	
	public static boolean isSaveTxt() {
		return isSaveTxt;
	}
	public static void setSaveTxt(boolean isSaveTxt) {
		Parameters.isSaveTxt = isSaveTxt;
	}
	
	public static boolean isSaveArff() {
		return isSaveArff;
	}
	public static void setSaveArff(boolean isSaveArff) {
		Parameters.isSaveArff = isSaveArff;
	}
	
	public static boolean isSaveTopWords() {
		return isSaveTopWords;
	}
	public static void setSaveTopWords(boolean isSaveTopWords) {
		Parameters.isSaveTopWords = isSaveTopWords;
	}
	
	public static String getTopWordsSavedFileLocation() {
		return topWordsSavedFileLocation;
	}
	public static void setTopWordsSavedFileLocation(String topWordsSavedFileLocation) {
		Parameters.topWordsSavedFileLocation = topWordsSavedFileLocation;
	}
	
	public static boolean isSaveBasicPatterns() {
		return isSaveBasicPatterns;
	}
	public static void setSaveBasicPatterns(boolean isSaveBasicPatterns) {
		Parameters.isSaveBasicPatterns = isSaveBasicPatterns;
	}

	public static String getBasicPatternsSavedFileLocation() {
		return basicPatternsSavedFileLocation;
	}
	public static void setBasicPatternsSavedFileLocation(String basicPatternsSavedFileLocation) {
		Parameters.basicPatternsSavedFileLocation = basicPatternsSavedFileLocation;
	}
	
	public static boolean isSaveAdvancedPatterns() {
		return isSaveAdvancedPatterns;
	}
	public static void setSaveAdvancedPatterns(boolean isSaveAdvancedPatterns) {
		Parameters.isSaveAdvancedPatterns = isSaveAdvancedPatterns;
	}
	
	public static String getAdvancedPatternsSavedFileLocation() {
		return advancedPatternsSavedFileLocation;
	}
	public static void setAdvancedPatternsSavedFileLocation(String advancedPatternsSavedFileLocation) {
		Parameters.advancedPatternsSavedFileLocation = advancedPatternsSavedFileLocation;
	}
	
	
	// Related to the collection of seed words
	public static ArrayList<Word> getSeeds() {
		if (seeds == null || seeds.isEmpty()) {
			seeds = new ArrayList<>();
		}
		return seeds;
	}
	public static void setSeeds(ArrayList<Word> seeds) {
		Parameters.seeds = seeds;
	}
	
	public static ArrayList<Word> getUnigrams() {
		// TODO fix this with priority!!!!!!!!
		if (unigrams == null) {
			if (seeds == null || seeds.isEmpty()) {
				unigrams = Reader.importEmotionalWords(Constants.emotionalWordsFile);
			} else {
				Loader.enrich();
			}
		}
		return unigrams;
	}
	public static void setUnigrams(ArrayList<Word> unigrams) {
		Parameters.unigrams = unigrams;
	}
	
	public static HashMap<String, ArrayList<String>> getRawUnigrams() {
		if (rawUnigrams == null) {
			if (seeds == null || seeds.isEmpty()) {
				unigrams = Reader.importEmotionalWords(Constants.emotionalWordsFile);
			} else {
				Loader.enrich();
			}
		}
		return rawUnigrams;
	}
	public static void setRawUnigrams(HashMap<String, ArrayList<String>> rawUnigrams) {
		Parameters.rawUnigrams = rawUnigrams;
	}
	
	public static String getSeedsFileLocation() {
		return seedsFileLocation;
	}
	public static void setSeedsFileLocation(String seedsFileLocation) {
		Parameters.seedsFileLocation = seedsFileLocation;
	}
	
	public static String getUnigramsFileLocation() {
		return unigramsFileLocation;
	}
	public static void setUnigramsFileLocation(String unigramsFileLocation) {
		Parameters.unigramsFileLocation = unigramsFileLocation;
	}
	
	
	// Related to the Imported Unigrams
	public static boolean isImportUnigramList() {
		return isImportUnigramList;
	}
	public static void setImportUnigramList(boolean isImportUnigramList) {
		Parameters.isImportUnigramList = isImportUnigramList;
	}
	
	public static String getImportedUnigramsLocation() {
		return importedUnigramsLocation;
	}
	public static void setImportedUnigramsLocation(String importedUnigramsLocation) {
		Parameters.importedUnigramsLocation = importedUnigramsLocation;
	}
	
	public static HashMap<String, Object> getAdvancedUnigrams() {
		if (advancedUnigrams == null || advancedUnigrams.isEmpty()) {
			advancedUnigrams = new HashMap<String, Object>();
		}
		return advancedUnigrams;
	}
	public static void setAdvancedUnigrams(HashMap<String, Object> advancedUnigrams) {
		Parameters.advancedUnigrams = advancedUnigrams;
	}
	

	// Related to how to read the files
	public static int getTextIdPosition() {
		return textIdPosition;
	}
	public static void setTextIdPosition(int textIdPosition) {
		Parameters.textIdPosition = textIdPosition;
	}
	
	public static int getUserNamePosition() {
		return userNamePosition;
	}
	public static void setUserNamePosition(int userNamePosition) {
		Parameters.userNamePosition = userNamePosition;
	}
	
	public static int getTextPosition() {
		return textPosition;
	}
	public static void setTextPosition(int textPosition) {
		Parameters.textPosition = textPosition;
	}
	
	public static int getClassPosition() {
		return classPosition;
	}
	public static void setClassPosition(int classPosition) {
		Parameters.classPosition = classPosition;
	}
	
	public static int getIndexPosition() {
		return indexPosition;
	}
	public static void setIndexPosition(int indexPosition) {
		Parameters.indexPosition = indexPosition;
	}
	
	public static boolean isUseAdavancedFeaturesAllowed() {
		return useAdavancedFeaturesAllowed;
	}
	public static void setUseAdavancedFeaturesAllowed(boolean useAdavancedFeaturesAllowed) {
		Parameters.useAdavancedFeaturesAllowed = useAdavancedFeaturesAllowed;
	}
	
	
	// Data sets
	public static ArrayList<Tweet> getTrainingSet() {
		return trainingSet;
	}
	public static void setTrainingSet(ArrayList<Tweet> trainingSet) {
		Parameters.trainingSet = trainingSet;
	}
	
	public static ArrayList<Tweet> getTestSet() {
		return testSet;
	}
	public static void setTestSet(ArrayList<Tweet> testSet) {
		Parameters.testSet = testSet;
	}
	
	public static ArrayList<Tweet> getUnknownSet() {
		return unknownSet;
	}
	public static void setUnknownSet(ArrayList<Tweet> unknownSet) {
		Parameters.unknownSet = unknownSet;
	}
	
	
	// Top Words lists
	public static HashMap<String, ArrayList<String>> getTopWords() {
		if (topWords==null) {
			topWords = new HashMap<>();
			for (String sentiment : Parameters.getClasses()) {
				topWords.put(sentiment, new ArrayList<String>());
			}
		}
		return topWords;
	}
	public static void setTopWords(HashMap<String, ArrayList<String>> topWords) {
		Parameters.topWords = topWords;
	}

	public static HashMap<String, ArrayList<String>> getTopNouns() {
		if (topNouns==null) {
			topNouns = new HashMap<>();
			for (String sentiment : Parameters.getClasses()) {
				topNouns.put(sentiment, new ArrayList<String>());
			}
		}
		return topNouns;
	}
	public static void setTopNouns(HashMap<String, ArrayList<String>> topNouns) {
		Parameters.topNouns = topNouns;
	}

	public static HashMap<String, ArrayList<String>> getTopVerbs() {
		if (topVerbs==null) {
			topVerbs = new HashMap<>();
			for (String sentiment : Parameters.getClasses()) {
				topVerbs.put(sentiment, new ArrayList<String>());
			}
		}
		return topVerbs;
	}
	public static void setTopVerbs(HashMap<String, ArrayList<String>> topVerbs) {
		Parameters.topVerbs = topVerbs;
	}

	public static HashMap<String, ArrayList<String>> getTopAdjectives() {
		if (topAdjectives==null) {
			topAdjectives = new HashMap<>();
			for (String sentiment : Parameters.getClasses()) {
				topAdjectives.put(sentiment, new ArrayList<String>());
			}
		}
		return topAdjectives;
	}
	public static void setTopAdjectives(HashMap<String, ArrayList<String>> topAdjectives) {
		Parameters.topAdjectives = topAdjectives;
	}

	public static HashMap<String, ArrayList<String>> getTopAdverbs() {
		if (topAdverbs==null) {
			topAdverbs = new HashMap<>();
			for (String sentiment : Parameters.getClasses()) {
				topAdverbs.put(sentiment, new ArrayList<String>());
			}
		}
		return topAdverbs;
	}
	public static void setTopAdverbs(HashMap<String, ArrayList<String>> topAdverbs) {
		Parameters.topAdverbs = topAdverbs;
	}
	
	
	// Basic Pattern lists
	public static HashMap<String, ArrayList<Pattern>> getSingleLengthPatterns() {
		return singleLengthPatterns;
	}
	public static void setSingleLengthPatterns(HashMap<String, ArrayList<Pattern>> singleLengthPatterns) {
		Parameters.singleLengthPatterns = singleLengthPatterns;
	}
	
	public static HashMap<String, HashMap<Integer, ArrayList<Pattern>>> getMultiLengthPatterns() {
		return multiLengthPatterns;
	}
	public static void setMultiLengthPatterns(HashMap<String, HashMap<Integer, ArrayList<Pattern>>> multiLengthPatterns) {
		Parameters.multiLengthPatterns = multiLengthPatterns;
	}
	
	public static HashMap<String, ArrayList<PatternNumeric>> getSingleLengthPatternsNumeric() {
		return singleLengthPatternsNumeric;
	}
	public static void setSingleLengthPatternsNumeric(HashMap<String, ArrayList<PatternNumeric>> singleLengthPatternsNumeric) {
		Parameters.singleLengthPatternsNumeric = singleLengthPatternsNumeric;
	}
	
	public static HashMap<String, HashMap<Integer, ArrayList<PatternNumeric>>> getMultiLengthPatternsNumeric() {
		return multiLengthPatternsNumeric;
	}
	public static void setMultiLengthPatternsNumeric(
			HashMap<String, HashMap<Integer, ArrayList<PatternNumeric>>> multiLengthPatternsNumeric) {
		Parameters.multiLengthPatternsNumeric = multiLengthPatternsNumeric;
	}
	
	// Advanced Patterns lists
	public static HashMap<String, ArrayList<Pattern>> getSingleLengthAdvancedPatterns() {
		return singleLengthAdvancedPatterns;
	}
	public static void setSingleLengthAdvancedPatterns(HashMap<String, ArrayList<Pattern>> singleLengthAdvancedPatterns) {
		Parameters.singleLengthAdvancedPatterns = singleLengthAdvancedPatterns;
	}
	
	public static HashMap<String, HashMap<Integer, ArrayList<Pattern>>> getMultiLengthAdvancedPatterns() {
		return multiLengthAdvancedPatterns;
	}
	public static void setMultiLengthAdvancedPatterns(
			HashMap<String, HashMap<Integer, ArrayList<Pattern>>> multiLengthAdvancedPatterns) {
		Parameters.multiLengthAdvancedPatterns = multiLengthAdvancedPatterns;
	}
	
	public static HashMap<String, ArrayList<PatternNumeric>> getSingleLengthAdvancedPatternsNumeric() {
		return singleLengthAdvancedPatternsNumeric;
	}
	public static void setSingleLengthAdvancedPatternsNumeric(HashMap<String, ArrayList<PatternNumeric>> singleLengthAdvancedPatternsNumeric) {
		Parameters.singleLengthAdvancedPatternsNumeric = singleLengthAdvancedPatternsNumeric;
	}
	
	
	public static HashMap<String, HashMap<Integer, ArrayList<PatternNumeric>>> getMultiLengthAdvancedPatternsNumeric() {
		return multiLengthAdvancedPatternsNumeric;
	}
	public static void setMultiLengthAdvancedPatternsNumeric(HashMap<String, HashMap<Integer, ArrayList<PatternNumeric>>> multiLengthAdvancedPatternsNumeric) {
		Parameters.multiLengthAdvancedPatternsNumeric = multiLengthAdvancedPatternsNumeric;
	}
	
	
	// =====================================//
	//            REINITIALIZER             //
	// =====================================//
	
	/**
	 * Re-initialize all the parameters to the [null / 0 / false] values
	 */
	public static void reinitialize() {
		
		importedProjectDirectory = null;
		importedProjectName = null;
		isImportFeatures = false;
		featuresFileLocation = null;
		isImportTopWords = false;
		topWordsImportedFileLocation = null;
		isImportBasicPatterns = false;
		basicPatternsImportedFileLocation = null;
		isImportAdvancedPatterns = false;
		advancedPatternsImportedFileLocation = null;
		
		// Current-Project Related parameters
		projectLocation = null;
		projectName = null;
		typeOfProject = null;
		classes = null;
		trainingSetLocation = null;
		testSetLocation = null;
		nonAnnotatedDataLocation = null;
		isSaveCsv = false;
		isSaveTxt = false;
		isSaveArff = false;
		isSaveTopWords = false;
		topWordsSavedFileLocation = null;
		isSaveBasicPatterns = false;
		basicPatternsSavedFileLocation = null;
		isSaveAdvancedPatterns = false;
		advancedPatternsSavedFileLocation = null;
		
		// Related to the collection of seed words
		seeds = null;
		unigrams = null;
		seedsFileLocation = null;
		unigramsFileLocation = null;
		
		// Related to how to read the files
		textIdPosition = 0;
		userNamePosition = 0;
		textPosition = 0;
		classPosition = 0;
		indexPosition = 0;
		useAdavancedFeaturesAllowed = false;
		
		// Data sets
		trainingSet = null;
		testSet = null;
		unknownSet = null;
		
		// Top Words lists
		topWords = null;
		topNouns = null;
		topVerbs = null;
		topAdjectives = null;
		topAdverbs = null;
		
		// Basic Patterns lists
		singleLengthPatterns = null;
		multiLengthPatterns = null;
		
		// Advanced Patterns lists
		singleLengthAdvancedPatterns = null;
		multiLengthAdvancedPatterns = null;
	}

}

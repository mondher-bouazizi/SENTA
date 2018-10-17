package main.items;

import java.util.ArrayList;
import java.util.HashMap;

public class ImportedFeatures {
	
	// =====================================//
	//    ATTRIBUTES (IMPORTED FEATURES)    //
	// =====================================//

	protected static String importTrainingFeaturesFileLocation;
	protected static String importTestFeaturesFileLocation; // Used for both test and non-annotated
	
	protected static ArrayList<String> importedFeatures;
	protected static HashMap<String, String> featuresValues;
	
	protected static boolean isUseTweetId;
	protected static String tweetIdField;
	
	
	// =====================================//
	//          GETTERS AND SETTERS         //
	// =====================================//

	public static String getImportTrainingFeaturesFileLocation() {
		return importTrainingFeaturesFileLocation;
	}
	public static void setImportTrainingFeaturesFileLocation(String importTrainingFeaturesFileLocation) {
		ImportedFeatures.importTrainingFeaturesFileLocation = importTrainingFeaturesFileLocation;
	}
	
	public static String getImportTestFeaturesFileLocation() {
		return importTestFeaturesFileLocation;
	}
	public static void setImportTestFeaturesFileLocation(String importTestFeaturesFileLocation) {
		ImportedFeatures.importTestFeaturesFileLocation = importTestFeaturesFileLocation;
	}
	
	public static ArrayList<String> getImportedFeatures() {
		return importedFeatures;
	}
	public static void setImportedFeatures(ArrayList<String> importedFeatures) {
		ImportedFeatures.importedFeatures = importedFeatures;
	}
	
	public static HashMap<String, String> getFeaturesValues() {
		return featuresValues;
	}
	public static void setFeaturesValues(HashMap<String, String> featuresValues) {
		ImportedFeatures.featuresValues = featuresValues;
	}
	
	public static boolean isUseTweetId() {
		return isUseTweetId;
	}
	public static void setUseTweetId(boolean isUseTweetId) {
		ImportedFeatures.isUseTweetId = isUseTweetId;
	}
	
	public static String getTweetIdField() {
		return tweetIdField;
	}
	public static void setTweetIdField(String tweetIdField) {
		ImportedFeatures.tweetIdField = tweetIdField;
	}
	
	
	// =====================================//
	//            REINITIALIZER             //
	// =====================================//
	
	/**
	 * Re-initialize all the features to the [null / 0 / false] values
	 */
	public static void reinitialize() {
		importTrainingFeaturesFileLocation = null;
		importTestFeaturesFileLocation = null;
		importedFeatures = null;
		isUseTweetId = false;
		tweetIdField = null;
	}

}

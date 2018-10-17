package windows.features;

import java.io.IOException;

import commons.constants.Constants;
import commons.help.HelpConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

// TODO change this to be handled by the [windows manager] class

public class FeaturesCustumization {
	
	public static Parent root;
	public static Stage stage;
	
	public static void customize(HelpConstants.FeaturesSets featureSet) {
		
		if (featureSet.equals(HelpConstants.FeaturesSets.BASIC_SENTIMENT_BASED_FEATURES)) {
			
			FeaturesCustumization.stage = new Stage();
			FeaturesCustumization.stage.setResizable(false);
			FeaturesCustumization.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
			FeaturesCustumization.stage.setTitle("Twitter Classifier - Sentiment features customization");
			try {
				FeaturesCustumization.root = FXMLLoader.load(FeaturesCustumization.class.getResource("/windows/features/SentimentFeaturesWindow.fxml"));
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			FeaturesCustumization.stage.setScene(new Scene(root, 640, 480));
			FeaturesCustumization.stage.initModality(Modality.APPLICATION_MODAL);
			FeaturesCustumization.stage.show();
		}
		
		if (featureSet.equals(HelpConstants.FeaturesSets.PUNCTUATION_FEATURES)) {
			
			FeaturesCustumization.stage = new Stage();
			FeaturesCustumization.stage.setResizable(false);
			FeaturesCustumization.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
			FeaturesCustumization.stage.setTitle("Twitter Classifier - Punctuation features customization");
			try {
				FeaturesCustumization.root = FXMLLoader.load(FeaturesCustumization.class.getResource("/windows/features/PunctuationFeaturesWindow.fxml"));
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			FeaturesCustumization.stage.setScene(new Scene(root, 640, 480));
			FeaturesCustumization.stage.initModality(Modality.APPLICATION_MODAL);
			FeaturesCustumization.stage.show();
		}
		
		if (featureSet.equals(HelpConstants.FeaturesSets.STYLISTIC_FEATURES)) {
			
			FeaturesCustumization.stage = new Stage();
			FeaturesCustumization.stage.setResizable(false);
			FeaturesCustumization.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
			FeaturesCustumization.stage.setTitle("Twitter Classifier - Sytlistic and Syntactic features customization");
			try {
				FeaturesCustumization.root = FXMLLoader.load(FeaturesCustumization.class.getResource("/windows/features/StylisticFeaturesWindow.fxml"));
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			FeaturesCustumization.stage.setScene(new Scene(root, 640, 480));
			FeaturesCustumization.stage.initModality(Modality.APPLICATION_MODAL);
			FeaturesCustumization.stage.show();
		}
		
		if (featureSet.equals(HelpConstants.FeaturesSets.BASIC_SEMANTIC_FEATURES)) {
			
			FeaturesCustumization.stage = new Stage();
			FeaturesCustumization.stage.setResizable(false);
			FeaturesCustumization.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
			FeaturesCustumization.stage.setTitle("Twitter Classifier - Semantic features customization");
			try {
				FeaturesCustumization.root = FXMLLoader.load(FeaturesCustumization.class.getResource("/windows/features/SemanticFeaturesWindow.fxml"));
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			FeaturesCustumization.stage.setScene(new Scene(root, 640, 480));
			FeaturesCustumization.stage.initModality(Modality.APPLICATION_MODAL);
			FeaturesCustumization.stage.show();
		}
		
		if (featureSet.equals(HelpConstants.FeaturesSets.UNIGRAM_FEATURES)) {
			
			FeaturesCustumization.stage = new Stage();
			FeaturesCustumization.stage.setResizable(false);
			FeaturesCustumization.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
			FeaturesCustumization.stage.setTitle("Twitter Classifier - Unigram features customization");
			try {
				FeaturesCustumization.root = FXMLLoader.load(FeaturesCustumization.class.getResource("/windows/features/UnigramFeaturesWindow.fxml"));
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			FeaturesCustumization.stage.setScene(new Scene(root, 640, 480));
			FeaturesCustumization.stage.initModality(Modality.APPLICATION_MODAL);
			FeaturesCustumization.stage.show();
		}
		
		if (featureSet.equals(HelpConstants.FeaturesSets.TOP_WORDS_FEATURES)) {
			
			FeaturesCustumization.stage = new Stage();
			FeaturesCustumization.stage.setResizable(false);
			FeaturesCustumization.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
			FeaturesCustumization.stage.setTitle("Twitter Classifier - Top words features customization");
			try {
				FeaturesCustumization.root = FXMLLoader.load(FeaturesCustumization.class.getResource("/windows/features/TopWordsFeaturesWindow.fxml"));
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			FeaturesCustumization.stage.setScene(new Scene(root, 640, 480));
			FeaturesCustumization.stage.initModality(Modality.APPLICATION_MODAL);
			FeaturesCustumization.stage.show();
		}
		
		if (featureSet.equals(HelpConstants.FeaturesSets.BASIC_PATTERN_BASED_FEATURES)) {
			
			FeaturesCustumization.stage = new Stage();
			FeaturesCustumization.stage.setResizable(false);
			FeaturesCustumization.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
			FeaturesCustumization.stage.setTitle("Twitter Classifier - Pattern features customization");
			try {
				FeaturesCustumization.root = FXMLLoader.load(FeaturesCustumization.class.getResource("/windows/features/PatternFeaturesWindow.fxml"));
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			FeaturesCustumization.stage.setScene(new Scene(root, 640, 480));
			FeaturesCustumization.stage.initModality(Modality.APPLICATION_MODAL);
			FeaturesCustumization.stage.show();
		}
		
		if (featureSet.equals(HelpConstants.FeaturesSets.ADVANCED_SENTIMENT_BASED_FEATURES)) {
			FeaturesCustumization.stage = new Stage();
			FeaturesCustumization.stage.setResizable(false);
			FeaturesCustumization.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
			FeaturesCustumization.stage.setTitle("Twitter Classifier - Advanced sentiment-based features customization");
			try {
				FeaturesCustumization.root = FXMLLoader.load(FeaturesCustumization.class.getResource("/windows/features/AdvancedSentimentFeaturesWindow.fxml"));
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			FeaturesCustumization.stage.setScene(new Scene(root, 640, 480));
			FeaturesCustumization.stage.initModality(Modality.APPLICATION_MODAL);
			FeaturesCustumization.stage.show();
		}
		
		if (featureSet.equals(HelpConstants.FeaturesSets.ADVANCED_SEMANTIC_FEATURES)) {
			FeaturesCustumization.stage = new Stage();
			FeaturesCustumization.stage.setResizable(false);
			FeaturesCustumization.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
			FeaturesCustumization.stage.setTitle("Twitter Classifier - Advanced semantic features customization");
			try {
				FeaturesCustumization.root = FXMLLoader.load(FeaturesCustumization.class.getResource("/windows/features/AdvancedSemanticFeaturesWindow.fxml"));
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			FeaturesCustumization.stage.setScene(new Scene(root, 640, 480));
			FeaturesCustumization.stage.initModality(Modality.APPLICATION_MODAL);
			FeaturesCustumization.stage.show();
		}
		
		if (featureSet.equals(HelpConstants.FeaturesSets.ADVANCED_PATTERN_BASED_FEATURES)) {
			FeaturesCustumization.stage = new Stage();
			FeaturesCustumization.stage.setResizable(false);
			FeaturesCustumization.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
			FeaturesCustumization.stage.setTitle("Twitter Classifier - Advanced pattern-based features customization");
			try {
				FeaturesCustumization.root = FXMLLoader.load(FeaturesCustumization.class.getResource("/windows/features/AdvancedPatternFeaturesWindow.fxml"));
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			FeaturesCustumization.stage.setScene(new Scene(root, 640, 480));
			FeaturesCustumization.stage.initModality(Modality.APPLICATION_MODAL);
			FeaturesCustumization.stage.show();
		}
		
		if (featureSet.equals(HelpConstants.FeaturesSets.ADVANCED_UNIGRAM_FEATURES)) {
			FeaturesCustumization.stage = new Stage();
			FeaturesCustumization.stage.setResizable(false);
			FeaturesCustumization.stage.getIcons().add(new Image("File:" + Constants.TwitterIcon));
			FeaturesCustumization.stage.setTitle("Twitter Classifier - Advanced unigram features customization");
			try {
				FeaturesCustumization.root = FXMLLoader.load(FeaturesCustumization.class.getResource("/windows/features/AdvancedUnigramFeaturesWindow.fxml"));
			} catch (IOException e) {
				System.out.println("Could not load the FXML file!!");
				e.printStackTrace();
			}
			FeaturesCustumization.stage.setScene(new Scene(root, 640, 480));
			FeaturesCustumization.stage.initModality(Modality.APPLICATION_MODAL);
			FeaturesCustumization.stage.show();
		}

	}

}

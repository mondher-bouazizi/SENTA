package commons.help;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class HelpConstants {
	
	public enum FeaturesSets {
		BASIC_SENTIMENT_BASED_FEATURES,
		PUNCTUATION_FEATURES,
		STYLISTIC_FEATURES,
		BASIC_SEMANTIC_FEATURES,
		UNIGRAM_FEATURES,
		TOP_WORDS_FEATURES,
		BASIC_PATTERN_BASED_FEATURES, 
		ADVANCED_SENTIMENT_BASED_FEATURES,
		ADVANCED_SEMANTIC_FEATURES,
		ADVANCED_PATTERN_BASED_FEATURES,
		ADVANCED_UNIGRAM_FEATURES
	}
	
	public static VBox sentimentFeaturesHelp;
	
	public static VBox semanticFeaturesHelp;
	
	public static VBox unigramFeaturesHelp1;
	public static VBox unigramFeaturesHelp2;
	
	public static VBox topWordsFeatures1;
	public static VBox topWordsFeatures2;
	public static VBox topWordsFeatures3;
	
	
	public static VBox patternFeaturesHelp1;
	public static VBox patternFeaturesHelp2;
	public static VBox patternFeaturesHelp3;
	
	
	
	public static VBox getSentimentFeaturesHelp() {
		if (sentimentFeaturesHelp==null) {
			sentimentFeaturesHelp = new VBox(10);
			
			String description1 = "Sentiment features are features which rely on the sentiment polarities of the different "
					+ "components of the text such as the words themselve, emoticons, hashtags, etc. These features are extracted "
					+ "using already-built dictionaries.";
			Label p1 = new Label();
			p1.setTextAlignment(TextAlignment.JUSTIFY);
			p1.setWrapText(true);
			p1.setText(description1);
			
			String description2 = "These features can be divided into 3 main sub-sets:\n"
					+ "   - Textual components sentiments: These include the number/score of positive and negative words, highly "
					+ "emotional words, and the overall score of the text.\n"
					+ "   - Non-textual components sentiments: These include the number of positive and negative hashtags, "
					+ "positive, negative, \"joking\" emoticons, etc.\n"
					+ "   - Contrast-based features: These are boolean features which detect the existance of contradictory "
					+ "sentiments between words (i.e., positive and negative words both exist in the text), between hashtags, "
					+ " between words and hashtags, between words and emoticons.\n";
			Label p2 = new Label();
			p2.setTextAlignment(TextAlignment.JUSTIFY);
			p2.setWrapText(true);
			p2.setText(description2);
			
			
			sentimentFeaturesHelp.getChildren().addAll(p1, p2);
		}
		
		return sentimentFeaturesHelp;
	}

	
	public static VBox getSemanticFeaturesHelp() {
		
		if (semanticFeaturesHelp==null) {
			semanticFeaturesHelp = new VBox(10);
			
			String description1 = "Punctuation features are features related to the use of punctuation as well as capitalization "
					+ "in the text. While these features might not be very good indicators when it comes to classification of texts, "
					+ "they help enhance the accuracy of classification when combined with other families of features";
			Label p1 = new Label();
			p1.setTextAlignment(TextAlignment.JUSTIFY);
			p1.setWrapText(true);
			p1.setText(description1);
			
			String description2 = "These features can be divided into 4 main sub-sets:\n"
					+ "   - Punctuation marks: These include the number of question marks, exclamation marks, etc.\n"
					+ "   - Words and characters: These include the total/average number of words and characters, etc.\n"
					+ "   - Parentheses and others: These include the use of parentheses, braces, etc.\n"
					+ "   - Apostrophe and others: These include the use of quotation marks, etc.\n";
			Label p2 = new Label();
			p2.setTextAlignment(TextAlignment.JUSTIFY);
			p2.setWrapText(true);
			p2.setText(description2);
			
			
			semanticFeaturesHelp.getChildren().addAll(p1, p2);
		}
		
		return semanticFeaturesHelp;
	}

	
	public static VBox getUnigramFeaturesHelp1() {
		return unigramFeaturesHelp1;
	}

	public static VBox getUnigramFeaturesHelp2() {
		return unigramFeaturesHelp2;
	}
	

	public static VBox getTopWordsFeatures1() {
		return topWordsFeatures1;
	}

	public static VBox getTopWordsFeatures2() {
		return topWordsFeatures2;
	}

	public static VBox getTopWordsFeatures3() {
		return topWordsFeatures3;
	}

	
	public static VBox getPatternFeaturesHelp1() {
		return patternFeaturesHelp1;
	}

	public static VBox getPatternFeaturesHelp2() {
		return patternFeaturesHelp2;
	}

	public static VBox getPatternFeaturesHelp3() {
		return patternFeaturesHelp3;
	}
	
	
	

}

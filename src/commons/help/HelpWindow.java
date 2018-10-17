package commons.help;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HelpWindow implements Initializable {
	
	public static final String SENTIMENTFEATURESHELP = "Sentiment-Related Features";
	public static final String PUNCTUATIONFEATURESHELP = "Punctuation Features";
	public static final String STYLISTICFEATURESHELP = "Stylistic and Syntactic Features";
	public static final String SEMANTICFEATURESHELP = "Semantic Features";
	public static final String UNIGRAMFEATURESHELP = "Unigram-Based Features";
	public static final String TOPWRODSFEATURES = "Top Words Features";
	public static final String PATTERNFEATURESHEP = "Pattern-Based Features";
	
	public static final String ADVANCEDSENTIMENTBASEDFEATURES = "Advanced Sentiment-Based Features";
	public static final String ADVANCEDSEMANTICFEATURES = "Advanced Semantic Features";
	public static final String ADVANCEDPATTERNBASEDFEATURES = "Advanced Pattern-Based Features";
		
	@FXML Label featureSetTitle;
	@FXML Label pageReference;
	
	@FXML VBox helpLabel;
	
	@FXML Button back;
	@FXML Button next;
	@FXML Button ok;
	
	protected static HelpConstants.FeaturesSets featureSet;
	
	protected static int pageNumber;
	protected static int numberOfPages;
	
	@FXML public void handleOk() {
		System.out.println("OK clicked!");
		Help.stage.close();
	}
	
	@FXML public void handleNext() {
		System.out.println("Next clicked!");
		if (pageNumber < numberOfPages) {
			pageNumber++;
		}
		
		pageReference.setText(pageNumber + "/" + numberOfPages);
		back.setDisable(false);
		
		if (pageNumber == numberOfPages) {
			next.setDisable(true);
		}
		
	}
	
	@FXML public void handleBack() {
		System.out.println("Back clicked!");
		if (pageNumber > 1) {
			pageNumber--;
		}
		
		pageReference.setText(pageNumber + "/" + numberOfPages);
		next.setDisable(false);
		
		if (pageNumber == 1) {
			back.setDisable(true);
		}
	}


	public static void setParameters (HelpConstants.FeaturesSets featureSet) {
		HelpWindow.featureSet = featureSet;
		System.out.println(HelpWindow.featureSet);
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if(featureSet.equals(HelpConstants.FeaturesSets.BASIC_SENTIMENT_BASED_FEATURES)) {
			featureSetTitle.setText(SENTIMENTFEATURESHELP);
			helpLabel.getChildren().addAll(HelpConstants.getSentimentFeaturesHelp());
			numberOfPages =1;
			next.setDisable(true);
			back.setDisable(true);
		} else if (featureSet.equals(HelpConstants.FeaturesSets.PUNCTUATION_FEATURES)) {
			featureSetTitle.setText(PUNCTUATIONFEATURESHELP);
			helpLabel.getChildren().addAll(HelpConstants.getSemanticFeaturesHelp());
			numberOfPages =1;
			next.setDisable(true);
			back.setDisable(true);
		} else if (featureSet.equals(HelpConstants.FeaturesSets.STYLISTIC_FEATURES)) {
			featureSetTitle.setText(STYLISTICFEATURESHELP);
			numberOfPages =1;
			next.setDisable(true);
			back.setDisable(true);
		} else if (featureSet.equals(HelpConstants.FeaturesSets.BASIC_SEMANTIC_FEATURES)) {
			featureSetTitle.setText(SEMANTICFEATURESHELP);
			numberOfPages =1;
			next.setDisable(true);
			back.setDisable(true);
		} else if (featureSet.equals(HelpConstants.FeaturesSets.UNIGRAM_FEATURES)) {
			featureSetTitle.setText(UNIGRAMFEATURESHELP);
			numberOfPages =2;
			next.setDisable(false);
			back.setDisable(true);
		} else if (featureSet.equals(HelpConstants.FeaturesSets.TOP_WORDS_FEATURES)) {
			featureSetTitle.setText(TOPWRODSFEATURES);
			numberOfPages =3;
			next.setDisable(false);
			back.setDisable(true);
		} else if (featureSet.equals(HelpConstants.FeaturesSets.BASIC_PATTERN_BASED_FEATURES)) {
			featureSetTitle.setText(PATTERNFEATURESHEP);
			numberOfPages =3;
			next.setDisable(false);
			back.setDisable(true);
		} else if (featureSet.equals(HelpConstants.FeaturesSets.ADVANCED_SENTIMENT_BASED_FEATURES)) {
			featureSetTitle.setText(ADVANCEDSENTIMENTBASEDFEATURES);
			numberOfPages =3;
			next.setDisable(false);
			back.setDisable(true);
		} else if (featureSet.equals(HelpConstants.FeaturesSets.ADVANCED_SEMANTIC_FEATURES)) {
			featureSetTitle.setText(ADVANCEDSEMANTICFEATURES);
			numberOfPages =3;
			next.setDisable(false);
			back.setDisable(true);
		} else if (featureSet.equals(HelpConstants.FeaturesSets.ADVANCED_PATTERN_BASED_FEATURES)) {
			featureSetTitle.setText(ADVANCEDPATTERNBASEDFEATURES);
			numberOfPages =3;
			next.setDisable(false);
			back.setDisable(true);
		} else {
			System.out.println(SENTIMENTFEATURESHELP);
			featureSetTitle = new Label("How did you get here?");
			numberOfPages =1;
			next.setDisable(false);
			back.setDisable(true);
		}
		
		pageNumber=1;
		pageReference.setText(pageNumber + "/" + numberOfPages);
	}

}

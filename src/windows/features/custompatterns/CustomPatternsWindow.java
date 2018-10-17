package windows.features.custompatterns;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.items.Features;
import windows.others.AlertBox;

public class CustomPatternsWindow implements Initializable {
	
	protected static int numberOfCategories;
	
	@FXML Button ok;
	@FXML Button cancel;
	
	@FXML Button def;
	@FXML Button clear;
	
	@FXML Label category1;
	@FXML Label category2;
	@FXML Label category3;
	@FXML Label category4;
	
	@FXML ComboBox<String> action1;
	@FXML ComboBox<String> action2;
	@FXML ComboBox<String> action3;
	@FXML ComboBox<String> action4;
	
	@FXML TextField replacement1;
	@FXML TextField replacement2;
	@FXML TextField replacement3;
	@FXML TextField replacement4;
	
	@FXML public void handleAction1() {
		if(action1.getValue()==null) {
			replacement1.setDisable(true);
		} else {
			if(action1.getValue().equals("Replace by")) {
				replacement1.setDisable(false);
			} else {
				replacement1.setDisable(true);
			}
		}
	}
	
	@FXML public void handleAction2() {
		if(action2.getValue()==null) {
			replacement2.setDisable(true);
		} else {
			if(action2.getValue().equals("Replace by")) {
				replacement2.setDisable(false);
			} else {
				replacement2.setDisable(true);
			}
		}
	}
	
	@FXML public void handleAction3() {
		if(action3.getValue()==null) {
			replacement3.setDisable(true);
		} else {
			if(action3.getValue().equals("Replace by")) {
				replacement3.setDisable(false);
			} else {
				replacement3.setDisable(true);
			}
		}
	}
	
	@FXML public void handleAction4() {
		if(action4.getValue()==null) {
			replacement4.setDisable(true);
		} else {
			if(action4.getValue().equals("Replace by")) {
				replacement4.setDisable(false);
			} else {
				replacement4.setDisable(true);
			}
		}
	}
	
	@FXML public void handleOk() {
		System.out.println("OK clicked!");
		
		boolean fine = true;
		
		if (numberOfCategories>=1) {
			if (action1.getValue()==null || (action1.getValue().equals("Replace by") && (replacement1.getText()==null || replacement1.getText().equals("")))) {
				AlertBox.display("Error", "Please choose a vlid action for the Category 1", "OK");
				fine = false;
			} else {
				if (action1.getValue().equals("Keep the token")) {
					Features.setAction1(Features.PatternActions.KEEP);
				}
				if (action1.getValue().equals("Lemmatize")) {
					Features.setAction1(Features.PatternActions.LEMMATIZE);
				}
				if (action1.getValue().equals("Replace by PoS")) {
					Features.setAction1(Features.PatternActions.POS);
				}
				if (action1.getValue().equals("Replace by simplified PoS")) {
					Features.setAction1(Features.PatternActions.SIMPLIFIEDPOS);
				}
				if (action1.getValue().equals("Replace by simp. PoS + Sentiment")) {
					Features.setAction1(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT);
				}
				if (action1.getValue().equals("Replace by")) {
					Features.setAction1(Features.PatternActions.REPLACEWITH);
					Features.setReplacement1(replacement1.getText());
				}
			}
		}
		
		if (fine && numberOfCategories>=2) {
			if (action2.getValue()==null || (action2.getValue().equals("Replace by") && (replacement2.getText()==null || replacement2.getText().equals("")))) {
				AlertBox.display("Error", "Please choose a vlid action for the Category 2", "OK");
				fine = false;
			} else {
				if (action2.getValue().equals("Keep the token")) {
					Features.setAction2(Features.PatternActions.KEEP);
				}
				if (action2.getValue().equals("Lemmatize")) {
					Features.setAction2(Features.PatternActions.LEMMATIZE);
				}
				if (action2.getValue().equals("Replace by PoS")) {
					Features.setAction2(Features.PatternActions.POS);
				}
				if (action2.getValue().equals("Replace by simplified PoS")) {
					Features.setAction2(Features.PatternActions.SIMPLIFIEDPOS);
				}
				if (action2.getValue().equals("Replace by simp. PoS + Sentiment")) {
					Features.setAction2(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT);
				}
				if (action2.getValue().equals("Replace by")) {
					Features.setAction2(Features.PatternActions.REPLACEWITH);
					Features.setReplacement2(replacement2.getText());
				}
			}
		}
		
		if (fine && numberOfCategories>=3) {
			if (action3.getValue()==null || (action3.getValue().equals("Replace by") && (replacement3.getText()==null || replacement3.getText().equals("")))) {
				AlertBox.display("Error", "Please choose a vlid action for the Category 3", "OK");
				fine = false;
			} else {
				if (action3.getValue().equals("Keep the token")) {
					Features.setAction3(Features.PatternActions.KEEP);
				}
				if (action3.getValue().equals("Lemmatize")) {
					Features.setAction3(Features.PatternActions.LEMMATIZE);
				}
				if (action3.getValue().equals("Replace by PoS")) {
					Features.setAction3(Features.PatternActions.POS);
				}
				if (action3.getValue().equals("Replace by simplified PoS")) {
					Features.setAction3(Features.PatternActions.SIMPLIFIEDPOS);
				}
				if (action3.getValue().equals("Replace by simp. PoS + Sentiment")) {
					Features.setAction3(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT);
				}
				if (action3.getValue().equals("Replace by")) {
					Features.setAction3(Features.PatternActions.REPLACEWITH);
					Features.setReplacement3(replacement3.getText());
				}
			}
		}
		
		if (fine && numberOfCategories>=4) {
			if (action4.getValue()==null || (action4.getValue().equals("Replace by") && (replacement4.getText()==null || replacement4.getText().equals("")))) {
				AlertBox.display("Error", "Please choose a vlid action for the Category 4", "OK");
				fine = false;
			} else {
				if (action4.getValue().equals("Keep the token")) {
					Features.setAction4(Features.PatternActions.KEEP);
				}
				if (action4.getValue().equals("Lemmatize")) {
					Features.setAction4(Features.PatternActions.LEMMATIZE);
				}
				if (action4.getValue().equals("Replace by PoS")) {
					Features.setAction4(Features.PatternActions.POS);
				}
				if (action4.getValue().equals("Replace by simplified PoS")) {
					Features.setAction4(Features.PatternActions.SIMPLIFIEDPOS);
				}
				if (action4.getValue().equals("Replace by simp. PoS + Sentiment")) {
					Features.setAction4(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT);
				}
				if (action4.getValue().equals("Replace by")) {
					Features.setAction4(Features.PatternActions.REPLACEWITH);
					Features.setReplacement4(replacement4.getText());
				}
			}
		}
		if (fine) {
			CustomizePatterns.stage.close();
		}
	}
	
	@FXML public void handleCancel() {
		System.out.println("Cancel clicked!");
		CustomizePatterns.stage.close();
	}
	
	@FXML public void handleDefault() {
		if (numberOfCategories==1) {
			action1.setValue("Lemmatize");
		}
		if (numberOfCategories==2) {
			action1.setValue("Lemmatize");
			action2.setValue("Replace by simplified PoS");
		}
		if (numberOfCategories==3) {
			action1.setValue("Lemmatize");
			action2.setValue("Replace by simplified PoS");
			action3.setValue("Replace by PoS");
		}
		if (numberOfCategories==4) {
			action1.setValue("Lemmatize");
			action2.setValue("Replace by simplified PoS");
			action3.setValue("Replace by PoS");
			action4.setValue("Replace by PoS");
		}
	}
	
	@FXML public void handleClear() {
		if (numberOfCategories==1) {
			action1.setValue(null);
		}
		if (numberOfCategories==2) {
			action1.setValue(null);
			action2.setValue(null);
		}
		if (numberOfCategories==3) {
			action1.setValue(null);
			action2.setValue(null);
			action3.setValue(null);
		}
		if (numberOfCategories==4) {
			action1.setValue(null);
			action2.setValue(null);
			action3.setValue(null);
			action4.setValue(null);
		}
	}
	
	
	
	
	
	public static void setParameters (int numberOfCategories) {
		CustomPatternsWindow.numberOfCategories = numberOfCategories;
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if (action1.getItems().isEmpty()) {
			action1.getItems().addAll("Keep the token", "Lemmatize", "Replace by PoS", "Replace by simplified PoS", "Replace by simp. PoS + Sentiment", "Replace by");
		}
		
		if (action2.getItems().isEmpty()) {
			action2.getItems().addAll("Keep the token", "Lemmatize", "Replace by PoS", "Replace by simplified PoS", "Replace by simp. PoS + Sentiment", "Replace by");
		}
		
		if (action3.getItems().isEmpty()) {
			action3.getItems().addAll("Keep the token", "Lemmatize", "Replace by PoS", "Replace by simplified PoS", "Replace by simp. PoS + Sentiment", "Replace by");
		}
		
		if (action4.getItems().isEmpty()) {
			action4.getItems().addAll("Keep the token", "Lemmatize", "Replace by PoS", "Replace by simplified PoS", "Replace by simp. PoS + Sentiment", "Replace by");
		}
		
		if (numberOfCategories==1) {
			category1.setDisable(false);
			category2.setDisable(true);
			category3.setDisable(true);
			category4.setDisable(true);
			
			action1.setDisable(false);
			action2.setDisable(true);
			action3.setDisable(true);
			action4.setDisable(true);
			
			replacement1.setDisable(true);
			replacement2.setDisable(true);
			replacement3.setDisable(true);
			replacement4.setDisable(true);
			
			initializeAction1();
			
		}
		
		if (numberOfCategories==2) {
			category1.setDisable(false);
			category2.setDisable(false);
			category3.setDisable(true);
			category4.setDisable(true);
			
			action1.setDisable(false);
			action2.setDisable(false);
			action3.setDisable(true);
			action4.setDisable(true);
			
			replacement1.setDisable(true);
			replacement2.setDisable(true);
			replacement3.setDisable(true);
			replacement4.setDisable(true);
			
			initializeAction1();
			initializeAction2();
		}
		
		if (numberOfCategories==3) {
			category1.setDisable(false);
			category2.setDisable(false);
			category3.setDisable(false);
			category4.setDisable(true);
			
			action1.setDisable(false);
			action2.setDisable(false);
			action3.setDisable(false);
			action4.setDisable(true);
			
			replacement1.setDisable(true);
			replacement2.setDisable(true);
			replacement3.setDisable(true);
			replacement4.setDisable(true);
			
			initializeAction1();
			initializeAction2();
			initializeAction3();
		}
		
		if (numberOfCategories==4) {
			category1.setDisable(false);
			category2.setDisable(false);
			category3.setDisable(false);
			category4.setDisable(false);
			
			action1.setDisable(false);
			action2.setDisable(false);
			action3.setDisable(false);
			action4.setDisable(false);
			
			replacement1.setDisable(true);
			replacement2.setDisable(true);
			replacement3.setDisable(true);
			replacement4.setDisable(true);
			
			initializeAction1();
			initializeAction2();
			initializeAction3();
			initializeAction4();
		}
		
		

	}

	private void initializeAction1() {
		if (Features.getAction1()!=null) {
			if (Features.getAction1().equals(Features.PatternActions.KEEP)) {
				action1.setValue("Keep the token");
			} else if (Features.getAction1().equals(Features.PatternActions.LEMMATIZE)) {
				action1.setValue("Lemmatize");
			} else if (Features.getAction1().equals(Features.PatternActions.POS)) {
				action1.setValue("Replace by PoS");
			} else if (Features.getAction1().equals(Features.PatternActions.SIMPLIFIEDPOS)) {
				action1.setValue("Replace by simplified PoS");
			} else if (Features.getAction1().equals(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT)) {
				action1.setValue("Replace by simp. PoS + Sentiment");
			} else if (Features.getAction1().equals(Features.PatternActions.REPLACEWITH)) {
				action1.setValue("Replace by");
				replacement1.setText(Features.getReplacement1());
				replacement1.setDisable(false);
			}
		}
	}
	
	private void initializeAction2() {
		if (Features.getAction2()!=null) {
			if (Features.getAction2().equals(Features.PatternActions.KEEP)) {
				action2.setValue("Keep the token");
			} else if (Features.getAction2().equals(Features.PatternActions.LEMMATIZE)) {
				action2.setValue("Lemmatize");
			} else if (Features.getAction2().equals(Features.PatternActions.POS)) {
				action2.setValue("Replace by PoS");
			} else if (Features.getAction2().equals(Features.PatternActions.SIMPLIFIEDPOS)) {
				action2.setValue("Replace by simplified PoS");
			} else if (Features.getAction2().equals(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT)) {
				action2.setValue("Replace by simp. PoS + Sentiment");
			} else if (Features.getAction2().equals(Features.PatternActions.REPLACEWITH)) {
				action2.setValue("Replace by");
				replacement2.setText(Features.getReplacement2());
				replacement2.setDisable(false);
			}
		}
	}
	
	private void initializeAction3() {
		if (Features.getAction3()!=null) {
			if (Features.getAction3().equals(Features.PatternActions.KEEP)) {
				action3.setValue("Keep the token");
			} else if (Features.getAction3().equals(Features.PatternActions.LEMMATIZE)) {
				action3.setValue("Lemmatize");
			} else if (Features.getAction3().equals(Features.PatternActions.POS)) {
				action3.setValue("Replace by PoS");
			} else if (Features.getAction3().equals(Features.PatternActions.SIMPLIFIEDPOS)) {
				action3.setValue("Replace by simplified PoS");
			} else if (Features.getAction3().equals(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT)) {
				action3.setValue("Replace by simp. PoS + Sentiment");
			} else if (Features.getAction3().equals(Features.PatternActions.REPLACEWITH)) {
				action3.setValue("Replace by");
				replacement3.setText(Features.getReplacement3());
				replacement3.setDisable(false);
			}
		}
	}
	
	private void initializeAction4() {
		if (Features.getAction4()!=null) {
			if (Features.getAction4().equals(Features.PatternActions.KEEP)) {
				action4.setValue("Keep the token");
			} else if (Features.getAction4().equals(Features.PatternActions.LEMMATIZE)) {
				action4.setValue("Lemmatize");
			} else if (Features.getAction4().equals(Features.PatternActions.POS)) {
				action4.setValue("Replace by PoS");
			} else if (Features.getAction4().equals(Features.PatternActions.SIMPLIFIEDPOS)) {
				action4.setValue("Replace by simplified PoS");
			} else if (Features.getAction4().equals(Features.PatternActions.SIMPLIFIEDPOSANDSENTIMENT)) {
				action4.setValue("Replace by simp. PoS + Sentiment");
			} else if (Features.getAction4().equals(Features.PatternActions.REPLACEWITH)) {
				action4.setValue("Replace by");
				replacement4.setText(Features.getReplacement4());
				replacement3.setDisable(false);
			}
		}
	}
	
}

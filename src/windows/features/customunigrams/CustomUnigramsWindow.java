package windows.features.customunigrams;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import commons.io.Reader;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import main.items.Parameters;
import main.items.Word;
import main.start.Loader;
import main.start.Main;
import windows.others.AlertBox;
import windows.others.ConfirmBox;

public class CustomUnigramsWindow implements Initializable {
	
	//======================================//
	//           FXML COMPONENTS            //
	//======================================//
	
	@FXML Button ok;
	@FXML Button cancel;

	@FXML TextField wordField;
	
	@FXML ComboBox<String> posChoice;
	@FXML ComboBox<String> classChoice;
	
	@FXML Button addButton;
	@FXML Button removeButton;
	
	@FXML VBox tableLayout;
	
	@FXML ComboBox<String> filterClassChoice;
	@FXML Button filterButton;
	
	@FXML Button importDefaultButton;
	@FXML Button importButton;
	@FXML Button exportButton;
	
	
	//======================================//
	//          NON-FXML COMPONENTS         //
	//======================================//
	
	protected static int numberOfCategories;
	
	private TableView<Word> wordsTable;
	
	private TableColumn<Word, String> word;
	private TableColumn<Word, String> posTag;
	private TableColumn<Word, String> emotion;
	
	private ArrayList<Word> tempSeeds;
	
	
	//======================================//
	//       FXML COMPONENTS ACTIONS        //
	//======================================//
	
	@FXML public void handleAdd() {
		System.out.println("Add button clicked");
		if (checkNewWord().equals("word")) {
			AlertBox.display("Enter a valid word", "Please enter a valid word", "OK");
		} else if (checkNewWord().equals("pos")) {
			AlertBox.display("Choose a Part of Speech", "Please Choose the part of speech of your word", "OK");
		} else if (checkNewWord().equals("class")) {
			AlertBox.display("Choose a class", "Please Choose the class to which belong your word", "OK");
		} else {
			// Add a condition to check whether the word exists in the English dictionary and whether or not it has a sentiment
			Word wd = new Word (wordField.getText(), posChoice.getValue(), classChoice.getValue());
			
			boolean test = true;
			
			for (Word refWord : Parameters.getSeeds()) {
				if (wd.equals(refWord)) {
					test = false;
					break;
				}
			}
			
			if (test) {
				Parameters.getSeeds().add(wd);
				// TODO fix this once the seeds are extracted with no issue
				wordsTable.getItems().add(wd);
				wordField.setText("");
				posChoice.setValue(null);
				classChoice.setValue(null);
			} else {
				AlertBox.display("Word already exists", "The chosen word already exists", "OK");
			}
		}
		
		
	}
	
	@FXML public void handleRemove() {
		System.out.println("Remove button clicked");
		
		if(!wordsTable.getSelectionModel().isEmpty()) {
			// TODO add the multiple selection removal of words!!!!
			Word wd = wordsTable.getSelectionModel().getSelectedItem();
			
			int position = Parameters.getSeeds().indexOf(wd);
			Parameters.getSeeds().remove(position);
			wordsTable.getItems().clear();
			if (filterClassChoice!=null && filterClassChoice.getValue()!=null) {
				if(filterClassChoice.getValue().equalsIgnoreCase("ALL")) {
					wordsTable.getItems().addAll(Parameters.getSeeds());
				} else {
					for (Word word : Parameters.getSeeds()) {
						if (word.getEmotion().equals(filterClassChoice.getValue())) {
							wordsTable.getItems().add(word);
						}
					}
				}
			} else {
				wordsTable.getItems().addAll(Parameters.getSeeds());
			}
		}
		
	}
		
	@FXML public void handleFilterButton() {
		System.out.println("Filter button clicked!");
		if (filterClassChoice.getValue()!=null) {
			wordsTable.getItems().clear();
			
			if(filterClassChoice.getValue().equalsIgnoreCase("ALL")) {
				wordsTable.getItems().addAll(Parameters.getSeeds());
			} else {
				for (Word wd : Parameters.getSeeds()) {
					if (wd.getEmotion().equals(filterClassChoice.getValue())) {
						wordsTable.getItems().add(wd);
					}
				}
			}
		}
		
		
		
		
	}
		
	@FXML public void handleImportDefault() {
		System.out.println("Import Default Clicked!");
		if (ConfirmBox.display("Import default seed words?", "Are you sure you want to import the default seed words?")) {
			Loader.loadDefaultSeedWords();
			wordsTable.getItems().clear();
			wordsTable.getItems().addAll(Parameters.getSeeds());
			//wordsTable.refresh();
		}
		
	}
	
	@FXML public void handleImport() {
		System.out.println("Import Clicked!");
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text documents", "*.txt"), new ExtensionFilter("All files", "*.*"));
		fileChooser.setTitle("Please specify which file you want to import");
		try {
				String fileToOpen = fileChooser.showOpenDialog(Main.primaryStage).getPath();
				if (ConfirmBox.display("Import seed words?", "Are you sure you want to import the selected set of seed words?")) {
					Parameters.getSeeds().addAll(Reader.importEmotionalWords(fileToOpen));
					wordsTable.getItems().clear();
					wordsTable.getItems().addAll(Parameters.getSeeds());
					//wordsTable.refresh();
				}
				
		} catch (NullPointerException exepction) {
			System.out.println(exepction.getMessage());
		}
	}
	
	@FXML public void handleExport() {
		System.out.println("Export  Clicked!");
		
	}
		
	@FXML public void handleOk() {
		System.out.println("OK clicked!");
		CustomizeUnigrams.stage.close();
	}
	
	@FXML public void handleCancel() {
		System.out.println("Cancel clicked!");
		Parameters.setSeeds(tempSeeds);
		CustomizeUnigrams.stage.close();
	}
	
	
	//======================================//
	//            INITIALIZATION            //
	//======================================//
	
	/**
	 * Initialize the scene
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		if (posChoice.getItems().isEmpty()) {
			posChoice.getItems().addAll("NOUN", "VERB", "ADJECTIVE", "ADVERB");
		}
		
		if (classChoice.getItems().isEmpty()) {
			for (String cl : Parameters.getClasses()) {
				classChoice.getItems().add(cl);
			}
		}
		
		if (filterClassChoice.getItems().isEmpty()) {
			filterClassChoice.getItems().add("ALL");
			for (String cl : Parameters.getClasses()) {
				filterClassChoice.getItems().add(cl);
			}
		}
		
		
		wordsTable = new TableView<>();
		wordsTable.setEditable(false);
//		wordsTable.getSelectionModel().setSelectionMode(
//			    SelectionMode.MULTIPLE
//				);
		
		word = new TableColumn<>("Word");
		word.setMinWidth(150);
		word.setStyle( "-fx-alignment: CENTER;");
		word.setEditable(false);
		word.setCellValueFactory(new PropertyValueFactory<Word, String>("word"));
		
		posTag = new TableColumn<>("Part of Speech");
		posTag.setMinWidth(150);
		posTag.setStyle( "-fx-alignment: CENTER;");
		posTag.setEditable(false);
		posTag.setCellValueFactory(new PropertyValueFactory<Word, String>("posTag"));
		
		emotion = new TableColumn<>("Class");
		emotion.setMinWidth(100);
		emotion.setStyle( "-fx-alignment: CENTER;");
		emotion.setEditable(false);
		emotion.setCellValueFactory(new PropertyValueFactory<Word, String>("emotion"));
		
		wordsTable.getColumns().addAll(word, posTag, emotion);
		
		wordsTable.getItems().clear();
		
		tempSeeds = new ArrayList<>();
		tempSeeds.addAll(Parameters.getSeeds());
		
		// TODO add the default words for the existing classes
		wordsTable.getItems().addAll(Parameters.getSeeds());
		
		wordsTable.refresh();
		
		tableLayout.getChildren().addAll(wordsTable);
		
		wordsTable.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.DELETE) {
					keyEvent.consume();
					removeButton.fire();
				}
			}
		});

	}

	
	//======================================//
	//            PRIVATE METHODS           //
	//======================================//
	
	/**
	 * Checks whether a new word that is about to be added is fine (word non empty, PoS and class selected)
	 * @return
	 */
	private String checkNewWord() {
		if (wordField.getText()==null || wordField.getText().equals("")) {
			return "word";
		}
		
		if (posChoice.getValue().isEmpty()) {
			return "pos";
		}
		
		if (classChoice.getValue().isEmpty()) {
			return "class";
		}
		
		return "fine";
	}
	
}

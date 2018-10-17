package windows.others;

import commons.constants.Constants;
import commons.io.Reader;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.items.RecentFile;
import main.start.Loader;

public class OpenRecentFiles {
	
	private static TableView<RecentFile> table;
	private static boolean test;
	
	@SuppressWarnings("unchecked")
	public static boolean display() {
		
		test = false;
//		
//		Stage window = new Stage();
//		
//		window.initModality(Modality.APPLICATION_MODAL);
//		window.setTitle("Recent Files");
//		window.getIcons().add(new Image("File:" + Constants.TwitterIcon));
//		window.setResizable(false);
//		
//		// Project Name
//		TableColumn<RecentFile, String> projectName = new TableColumn<>("Project Name");
//		projectName.setPrefWidth(110);
//		projectName.setCellValueFactory(new PropertyValueFactory<RecentFile, String>("projectName"));
//		// Date
//		TableColumn<RecentFile, String> date = new TableColumn<>("Date");
//		date.setPrefWidth(110);
//		date.setCellValueFactory(new PropertyValueFactory<RecentFile, String>("date"));
//		// Number of Texts
//		TableColumn<RecentFile, Integer> numberOfTexts = new TableColumn<>("Number of Texts");
//		numberOfTexts.setPrefWidth(110);
//		numberOfTexts.setCellValueFactory(new PropertyValueFactory<RecentFile, Integer>("numberOfTexts"));
//		// Location
//		TableColumn<RecentFile, String> file = new TableColumn<>("File Location");
//		file.setPrefWidth(250);
//		file.setCellValueFactory(new PropertyValueFactory<RecentFile, String>("file"));
//
//
//		// Table view
//		table = new TableView<>();
//		table.setEditable(false);
//		table.setItems(Loader.getRecentFiles());
//		table.getColumns().addAll(projectName, date, numberOfTexts, file);
//		table.setItems(Loader.getRecentFiles());
//		
//		// Separator
//		Separator separator0 = new Separator();
//		
//		// Button to Open the file
//		Button open = new Button("Open");
//		open.setOnAction(e -> {
//			if (!table.getSelectionModel().isEmpty()){
//			String fileToLoad = table.getSelectionModel().getSelectedItem().getFile();
//			if (Reader.isValidProjectFile(fileToLoad)) {
//				Loader.setProjectFileLocation(fileToLoad);
//				Loader.setProjectName(table.getSelectionModel().getSelectedItem().getProjectName());
//				//Reader.openProject(Loader.getProjectFileLocation());
//				test = true;
//				window.close();
//			} else {
//				AlertBox.display("Error", "Could not open the file", "OK");
//			}
//		}
//		});
//		
//		// Button to cancel
//		Button cancel = new Button("Cancel");
//		cancel.setOnAction(e -> {
//			System.out.println("Cancel Button clicked");
//			window.close();
//		});
//		
//		HBox buttonsLayout = new HBox(20);
//		buttonsLayout.setAlignment(Pos.CENTER);
//		buttonsLayout.getChildren().addAll(open, cancel);
//
//
//		VBox layout = new VBox(20);
//		layout.setPadding(new Insets(20, 20, 20, 20));
//		
//		layout.getChildren().addAll(table, separator0, buttonsLayout);
//		layout.setAlignment(Pos.CENTER);
//		
//		Scene scene = new Scene(layout, 600, 400);
//		
//		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//			 public void handle(final KeyEvent keyEvent) {
//			   if (keyEvent.getCode() == KeyCode.ENTER) {
//			    keyEvent.consume();
//			    open.fire();
//			   }
//			 }
//		});
//		
//		// Display the window and wait for it to close
//		window.setScene(scene);
//		window.showAndWait();
//		
		return test;
		
	}


}

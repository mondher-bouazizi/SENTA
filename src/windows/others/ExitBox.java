package windows.others;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExitBox {

static boolean exit;
static boolean save;
	
	public static boolean[] display(String title, String message) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setResizable(false);
		
		Label label = new Label();
		label.setText(message);
		
		
		// Save and Exit button
		Button saveButton = new Button("Save & Exit");
		saveButton.setMinWidth(80);
		saveButton.setOnAction(e -> {
			exit = true;
			save = true;
			window.close();
		});
		
		// Exit without saving button
		Button exitButton = new Button("Exit");
		exitButton.setMinWidth(80);
		exitButton.setOnAction(e -> {
			exit = true;
			save = false;
			window.close();
		});
		
		// Non-exit button
		Button noButton = new Button("No");
		noButton.setMinWidth(80);
		noButton.setOnAction(e -> {
			exit = false;
			save = false;
			window.close();
		});
		
		HBox innerLayout = new HBox(20);
		innerLayout.setAlignment(Pos.CENTER);
		
		innerLayout.getChildren().addAll(saveButton, exitButton, noButton);
		
		VBox layout = new VBox(20);
		layout.setMinSize(320, 100);
		
		layout.getChildren().addAll(label, innerLayout);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		
		// Display the window and wait for it to close
		window.setScene(scene);
		window.showAndWait();
		System.out.println("Save " + save);
		System.out.println("Exit " + exit);
		boolean[] result = {exit, save};
		
		return result;
	}
	
}

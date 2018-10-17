package windows.others;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	
	public static void display(String title, String message, String key) {
		Stage window = new Stage();
		
		// Block events to other windows
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setResizable(false);
		
		Label label = new Label();
		label.setText(message);
		label.setWrapText(true);
		
		Button closeButton = new Button(key);
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(20);
		layout.setPadding(new Insets(10, 20, 10, 20));
		layout.setMinSize(320, 120);
		layout.setMaxSize(480, 160);
		
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		
		// Display the window and wait for it to close
		window.setScene(scene);
		window.showAndWait();
		
	}

}

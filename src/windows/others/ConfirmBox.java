package windows.others;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
	
	static boolean answer;
	
	public static boolean display(String title, String message) {
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setResizable(false);
		
		Label label = new Label();
		label.setText(message);
		label.setWrapText(true);
		//label.setTextAlignment(TextAlignment.CENTER);
		
		
		// yes/no buttons
		Button yesButton = new Button("Yes");
		yesButton.setOnAction(e -> {
			answer = true;
			window.close();
		});
		
		Button noButton = new Button("No");
		noButton.setOnAction(e -> {
			answer = false;
			window.close();
		});
		
		HBox innerLayout = new HBox(20);
		innerLayout.setAlignment(Pos.CENTER);
		
		innerLayout.getChildren().addAll(yesButton, noButton);
		
		VBox layout = new VBox(20);
		layout.setPadding(new Insets(10, 20, 10, 20));
		layout.setMinSize(320, 120);
		layout.setMaxSize(480, 160);
		
		layout.getChildren().addAll(label, innerLayout);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		
		// Display the window and wait for it to close
		window.setScene(scene);
		window.showAndWait();
		
		return answer;
	}

}

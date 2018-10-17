package windows.others;

import commons.constants.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class About {
	
	private static final String ABOUTTEXT = "This program was created on October 27th, 2016.\n\n"
			+ "It is made to help you manually annotate texts into different classes. "
			+ "The creator of this program went through the same pain you're going through"
			+ " when you were manually annotating texts using programs like MS Excel (No offense Microsoft)"
			+ " so he decided to make this small program to help you all.\n\n"
			+ "If you have any question or you want just to tell me my program sucks, feel free to contact me at "
			+ "mondher.bouazizi@gmail.com\n\n"
			+ "And yeah, this program is free to use or modify as you want. Don't bother trying to sell it: I'll "
			+ "be the one getting all the money after I sue you :P";
	
	public static void display() {
		
		Stage window = new Stage();
		
		// Block events to other windows
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("About…");
		window.setResizable(false);
		
		Label label0 = new Label();
		label0.setText("Twitter Annotator V1.0");
		label0.setFont(Font.font(12));
		label0.setOpacity(10);
		
		ImageView image = new ImageView();
		image.setImage(new Image("File:" + Constants.aboutImage));
		image.setFitWidth(100);
		image.setPreserveRatio(true);
		
		Label label1 = new Label();
		label1.setText("© 2016 Ohtsuki Lab.");
		
		
		Label label = new Label();
		label.setText(ABOUTTEXT);
		label.setWrapText(true);
		label.setFont(Font.font(10));
		label.setTextAlignment(TextAlignment.JUSTIFY);
		
		Button closeButton = new Button("OK");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(20);
		layout.setPadding(new Insets(20, 20, 20, 20));
		layout.setMinSize(350, 460);
		layout.setMaxSize(350, 460);
		
		layout.getChildren().addAll(label0, image, label1, label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		
		// Display the window and wait for it to close
		window.setScene(scene);
		window.showAndWait();
		
	}

}

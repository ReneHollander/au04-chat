package at.hollanderpoecher.chat.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUI extends Application {

	private static final int SCENE_WIDTH = 800;
	private static final int SCENE_HEIGHT = 600;

	private TextArea contentArea;
	private TextField inputField;
	private Button sendButton;

	public GUI() {

	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane pane = new BorderPane();

		this.contentArea = new TextArea();
		this.contentArea.setEditable(false);

		this.inputField = new TextField();

		this.sendButton = new Button("Send");
		this.sendButton.setOnAction(event -> {
			if (this.contentArea.getText().length() == 0) {
				this.contentArea.appendText(this.inputField.getText());
			} else {
				this.contentArea.appendText("\n" + this.inputField.getText());
			}
			this.inputField.setText("");
		});

		pane.setCenter(contentArea);
		pane.setBottom(new BorderPane(inputField, null, sendButton, null, null));
		Scene scene = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);

		stage.setScene(scene);
		stage.show();
	}

	public void appendText(String string) {
		if (this.contentArea.getText().length() == 0) {
			this.contentArea.appendText(string);
		} else {
			this.contentArea.appendText("\n" + string);
		}
	}

	public String getInputFieldText() {
		return this.inputField.getText();
	}

	public void setInputFieldText(String text) {
		this.inputField.setText(text);
	}
}

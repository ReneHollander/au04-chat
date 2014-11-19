package at.hollanderpoecher.chat.gui;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ChatWindow implements Runnable {

	private static final int SCENE_WIDTH = 800;
	private static final int SCENE_HEIGHT = 600;

	private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder().parseCaseInsensitive().append(DateTimeFormatter.ISO_LOCAL_DATE).appendLiteral(' ').append(DateTimeFormatter.ISO_LOCAL_TIME).toFormatter();

	private TextArea contentArea;
	private TextField inputField;
	private Button sendButton;

	public ChatWindow() {
	}

	@Override
	public void run() {
		BorderPane pane = new BorderPane();

		this.contentArea = new TextArea();
		this.contentArea.setFocusTraversable(false);
		this.contentArea.setEditable(false);

		this.inputField = new TextField();
		this.inputField.setFocusTraversable(true);
		Platform.runLater(() -> this.inputField.requestFocus());
		// this.inputField.setOnKeyPressed(event -> {
		// if (event.getCode() == KeyCode.ENTER) {
		// this.append();
		// }
		// });

		this.sendButton = new Button("Send");
		this.sendButton.setFocusTraversable(true);
		// this.sendButton.setOnAction(event -> this.append());

		pane.setCenter(contentArea);
		pane.setBottom(new BorderPane(inputField, null, sendButton, null, null));
		Scene scene = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);

		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Chat");
		stage.show();
	}

	private void append() {
		this.appendMyText(LocalDateTime.now(), this.inputField.getText());
		this.inputField.setText("");
	}

	public void appendText(String text) {
		if (this.contentArea.getText().length() == 0) {
			this.contentArea.appendText(text);
		} else {
			this.contentArea.appendText("\n" + text);
		}
	}

	public void appendMyText(LocalDateTime timestamp, String text) {
		this.appendText("[" + timestamp.format(DATE_TIME_FORMATTER) + "] Ich: " + text);
	}

	public void appendPartnerText(LocalDateTime timestamp, InetAddress ip, String text) {
		this.appendText("[" + timestamp.format(DATE_TIME_FORMATTER) + "] " + ip.toString() + ": " + text);
	}

	public TextArea getContentArea() {
		return contentArea;
	}

	public TextField getInputField() {
		return inputField;
	}

	public Button getSendButton() {
		return sendButton;
	}
}

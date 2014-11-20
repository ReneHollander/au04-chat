package at.hollanderpoecher.chat.gui;

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
import at.hollanderpoecher.chat.interfaces.Message;

/**
 * Chat Window to display chat messages and write messages
 * 
 * @author Rene Hollander
 */
public class ChatWindow implements Runnable {

	private static final int SCENE_WIDTH = 800;
	private static final int SCENE_HEIGHT = 600;

	private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder().parseCaseInsensitive().append(DateTimeFormatter.ISO_LOCAL_DATE).appendLiteral(' ').append(DateTimeFormatter.ISO_LOCAL_TIME).toFormatter();

	private TextArea contentArea;
	private TextField inputField;
	private Button sendButton;

	@Override
	public void run() {
		BorderPane pane = new BorderPane();

		this.contentArea = new TextArea();
		this.contentArea.setFocusTraversable(false);
		this.contentArea.setEditable(false);

		this.inputField = new TextField();
		this.inputField.setFocusTraversable(true);
		Platform.runLater(() -> this.inputField.requestFocus());

		this.sendButton = new Button("Send");
		this.sendButton.setFocusTraversable(true);

		pane.setCenter(contentArea);
		pane.setBottom(new BorderPane(inputField, null, sendButton, null, null));
		Scene scene = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);

		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Chat");
		stage.show();
	}

	/**
	 * Appends the text to the content text area
	 * 
	 * @param text
	 *            Text to append
	 */
	public void appendText(String text) {
		if (this.contentArea.getText().length() == 0) {
			this.contentArea.appendText(text);
		} else {
			this.contentArea.appendText("\n" + text);
		}
	}

	/**
	 * Display the Message with timestamp to content
	 * 
	 * @param timestamp
	 *            Timestamp to display
	 * @param msg
	 *            Message object to display
	 */
	public void appendText(LocalDateTime timestamp, Message msg) {
		this.appendText("[" + timestamp.format(DATE_TIME_FORMATTER) + "] " + msg.getNick() + " (" + msg.getSenderAddress().getHostAddress() + "): " + msg.getMsg());
	}

	/**
	 * Get ContentArea Node
	 * 
	 * @return TextArea
	 */
	public TextArea getContentArea() {
		return contentArea;
	}

	/**
	 * Get InputField Node
	 * 
	 * @return InputField
	 */
	public TextField getInputField() {
		return inputField;
	}

	/**
	 * Get SendButton Node
	 * 
	 * @return SendButton
	 */
	public Button getSendButton() {
		return sendButton;
	}
}

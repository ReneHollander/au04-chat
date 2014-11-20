package at.hollanderpoecher.chat.gui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
	private CheckBox badwordFilterCheckBox;
	private Stage stage;

	@Override
	public void run() {
		BorderPane pane = new BorderPane();

		this.contentArea = new TextArea();
		this.contentArea.setFocusTraversable(false);
		this.contentArea.setEditable(false);
		this.contentArea.setWrapText(true);

		this.inputField = new TextField();
		this.inputField.setMinWidth(75);
		this.inputField.setFocusTraversable(true);
		Platform.runLater(() -> this.inputField.requestFocus());

		this.sendButton = new Button("Send");
		this.sendButton.setFocusTraversable(true);

		this.badwordFilterCheckBox = new CheckBox("Badword Filter");
		this.badwordFilterCheckBox.setSelected(true);

		pane.setCenter(contentArea);
		pane.setBottom(new BorderPane(inputField, null, new HBox(sendButton, badwordFilterCheckBox), null, null));
		Scene scene = new Scene(pane, SCENE_WIDTH, SCENE_HEIGHT);

		this.stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Chat");
		stage.show();
	}

	/**
	 * Appends the text to the content text area
	 * 
	 * @param timestamp
	 *            Timestamp of the message
	 * @param nick
	 *            Nickname of the sender
	 * @param text
	 *            Text to append
	 */
	public void appendText(LocalDateTime timestamp, String nick, String text) {
		String textToAppend = "[" + timestamp.format(DATE_TIME_FORMATTER) + "] " + nick + ": " + text;
		if (this.contentArea.getText().length() == 0) {
			this.contentArea.appendText(textToAppend);
		} else {
			this.contentArea.appendText("\n" + textToAppend);
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
	public void appendMessage(LocalDateTime timestamp, Message msg) {
		this.appendText(timestamp, msg.getNick() + " (" + msg.getSenderAddress().getHostAddress() + ")", msg.getMsg());
	}

	/**
	 * Get Stage
	 * 
	 * @return Stage
	 */
	public Stage getStage() {
		return stage;
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

	/**
	 * Get BadwordFilterCheckBox Node
	 * 
	 * @return BadwordFilterCheckBox
	 */
	public CheckBox getBadwordFilterCheckBox() {
		return badwordFilterCheckBox;
	}
}

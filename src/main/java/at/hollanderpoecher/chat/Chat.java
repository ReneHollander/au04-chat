package at.hollanderpoecher.chat;

import java.net.InetAddress;
import java.time.LocalDateTime;

import at.hollanderpoecher.chat.decorator.*;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import at.hollanderpoecher.chat.gui.ChatWindow;
import at.hollanderpoecher.chat.interfaces.Message;
import at.hollanderpoecher.chat.network.ChatClient;
import at.hollanderpoecher.chat.network.ChatMessage;
import at.hollanderpoecher.chat.util.FXUtils;

/**
 * A simple Multicast Chat
 * 
 * @author Rene Hollander
 */
public class Chat {

	/**
	 * Main Method of the chat. Connects the ChatClient to the ChatWindow
	 * 
	 * @param args
	 *            CLI Args
	 */
	public static void main(String[] args) {
		try {
			ChatWindow chatWindow = new ChatWindow();

			ChatClient chatClient = new ChatClient(InetAddress.getByName("239.255.255.250"), 8888, (message1) -> {
				Message message = new StringBufferSize(new ReneIsKing(new SmileyToSmileyface(new SmileyToLOL(new FilterBadWords(new ToUpperCase(message1))))));
				Platform.runLater(() -> {
					chatWindow.appendText(LocalDateTime.now(), message);
				});
			});

			FXUtils.startFX(chatWindow);

			chatWindow.getSendButton().setOnAction((event) -> {
				send(chatClient, chatWindow);
			});

			chatWindow.getInputField().setOnKeyPressed(event -> {
				if (event.getCode() == KeyCode.ENTER) {
					send(chatClient, chatWindow);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void send(ChatClient chatClient, ChatWindow chatWindow) {
		try {
			chatClient.send(new ChatMessage("Rene", chatWindow.getInputField().getText()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		chatWindow.getInputField().setText("");
	}
}

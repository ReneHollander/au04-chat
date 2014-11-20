package at.hollanderpoecher.chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import at.hollanderpoecher.chat.decorator.FilterBadWords;
import at.hollanderpoecher.chat.decorator.ReneIsKing;
import at.hollanderpoecher.chat.decorator.SmileyToLOL;
import at.hollanderpoecher.chat.decorator.SmileyToSmileyface;
import at.hollanderpoecher.chat.decorator.ToUpperCase;
import at.hollanderpoecher.chat.gui.ChatWindow;
import at.hollanderpoecher.chat.interfaces.Message;
import at.hollanderpoecher.chat.network.ChatClient;
import at.hollanderpoecher.chat.network.ChatMessage;
import at.hollanderpoecher.chat.util.FXUtils;

public class Chat {

	public static void main(String[] args) throws UnknownHostException, IOException {
		ChatWindow chatWindow = new ChatWindow();

		ChatClient chatClient = new ChatClient(InetAddress.getByName("239.255.255.250"), 8888, (message1) -> {
			Message message = new ReneIsKing(new SmileyToSmileyface(new SmileyToLOL(new FilterBadWords(new ToUpperCase(message1)))));
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

package at.hollanderpoecher.chat;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import at.hollanderpoecher.chat.gui.ChatWindow;
import at.hollanderpoecher.chat.network.ChatClient;
import at.hollanderpoecher.chat.util.FXUtils;
import at.hollanderpoecher.chat.util.Util;

public class Chat {

	public static void main(String[] args) throws UnknownHostException, IOException {
		String myIp = Util.getIp();
		ChatWindow chatWindow = new ChatWindow();
		FXUtils.startFX(chatWindow);

		@SuppressWarnings("resource")
		ChatClient chatClient = new ChatClient(InetAddress.getByName("239.255.255.250"), 8888, (data, inetaddress) -> {
			Platform.runLater(() -> {
				try {
					if (inetaddress.getHostAddress().equals(myIp)) {
						chatWindow.appendMyText(LocalDateTime.now(), data);
					} else {
						chatWindow.appendPartnerText(LocalDateTime.now(), inetaddress, data);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});

		chatWindow.getSendButton().setOnAction((event) -> {
			try {
				chatClient.send(chatWindow.getInputField().getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
			chatWindow.getInputField().setText("");
		});

		chatWindow.getInputField().setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				try {
					chatClient.send(chatWindow.getInputField().getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
				chatWindow.getInputField().setText("");
			}
		});
	}
}

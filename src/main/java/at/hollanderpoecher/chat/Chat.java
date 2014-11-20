package at.hollanderpoecher.chat;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.hollanderpoecher.chat.decorator.FilterBadWords;
import at.hollanderpoecher.chat.decorator.ReneIsKing;
import at.hollanderpoecher.chat.decorator.SmileyToLOL;
import at.hollanderpoecher.chat.decorator.SmileyToSmileyface;
import at.hollanderpoecher.chat.decorator.StringBufferSize;
import at.hollanderpoecher.chat.decorator.ToUpperCase;
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

	private static final Logger LOGGER = LogManager.getLogger(Chat.class);
	private static final String USAGE_STRING = "\nUsage:\n chat.jar <ip> <port> <nick>";

	/**
	 * Constructs a new Chat. It opens a ChatWindow and starts a ChatClient
	 * 
	 * @param ip
	 *            IP of the multicast group
	 * @param port
	 *            Port of the multicast group
	 * @param nick
	 *            Nickname of the user
	 * @throws IOException
	 *             If there was an error conencting to the server
	 */
	public Chat(InetAddress ip, int port, String nick) throws IOException {
		ChatWindow chatWindow = new ChatWindow();

		ChatClient chatClient = new ChatClient(ip, port, (message1) -> {
			Message message = new StringBufferSize(new ReneIsKing(new SmileyToSmileyface(new SmileyToLOL(new FilterBadWords(new ToUpperCase(message1))))));
			Platform.runLater(() -> {
				chatWindow.appendText(LocalDateTime.now(), message);
			});
		});

		FXUtils.startFX(chatWindow);

		chatWindow.getSendButton().setOnAction((event) -> {
			send(nick, chatClient, chatWindow);
		});

		chatWindow.getInputField().setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				send(nick, chatClient, chatWindow);
			}
		});
	}

	private void send(String nick, ChatClient chatClient, ChatWindow chatWindow) {
		try {
			chatClient.send(new ChatMessage(nick, chatWindow.getInputField().getText()));
		} catch (Exception e) {
			LOGGER.throwing(e);
		}
		chatWindow.getInputField().setText("");
	}

	/**
	 * Main Method of the chat. Connects the ChatClient to the ChatWindow
	 * 
	 * @param args
	 *            CLI Args
	 */
	public static void main(String[] args) {
		try {

			// parse cli args
			if (args.length != 3) {
				LOGGER.error("Invalid Argument Count!" + USAGE_STRING);
				System.exit(1);
			}

			InetAddress ip = null;
			try {
				ip = InetAddress.getByName(args[0]);
			} catch (Exception e) {
				LOGGER.error("Invalid IP address!" + USAGE_STRING);
				System.exit(1);
			}

			int port = -1;
			try {
				port = Integer.parseInt(args[1]);
				if (port <= 0 || port > 65535) {
					LOGGER.error("Invalid Port!" + USAGE_STRING);
					System.exit(1);
				}
			} catch (Exception e) {
				LOGGER.error("Invalid Port!" + USAGE_STRING);
				System.exit(1);
			}

			String nick = args[2];

			new Chat(ip, port, nick);
		} catch (Exception e) {
			LOGGER.throwing(e);
		}
	}
}

package at.hollanderpoecher.chat;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDateTime;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.hollanderpoecher.chat.decorator.FilterBadWords;
import at.hollanderpoecher.chat.decorator.ReneIsKing;
import at.hollanderpoecher.chat.decorator.SmileyToLOL;
import at.hollanderpoecher.chat.decorator.SmileyToSmileyface;
import at.hollanderpoecher.chat.decorator.ToUpperCase;
import at.hollanderpoecher.chat.gui.ChatWindow;
import at.hollanderpoecher.chat.interfaces.Message;
import at.hollanderpoecher.chat.network.ChatClient;
import at.hollanderpoecher.chat.network.ChatMessage;
import at.hollanderpoecher.chat.util.FXApplication;
import at.hollanderpoecher.chat.util.Handler;

/**
 * A simple Multicast Chat
 * 
 * @author Rene Hollander
 */
@SuppressWarnings("rawtypes")
public class Chat implements EventHandler, Handler<Message> {

	private static final Logger LOGGER = LogManager.getLogger(Chat.class);

	private static final String USAGE_STRING = "\nUsage:\n chat.jar <ip> <port> <nick>";
	private static final int MAX_MESSAGE_LENGTH = 1024;

	private InetAddress ip;
	private int port;
	private String nick;

	private ChatClient chatClient;

	private ChatWindow chatWindow;

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
		this.ip = ip;
		this.port = port;
		this.nick = nick;

		this.chatClient = new ChatClient(this.ip, this.port, this);
	}

	/**
	 * Starts and opens the GUI
	 */
	@SuppressWarnings("unchecked")
	public void launch() {
		this.chatWindow = new ChatWindow();
		FXApplication.launch();
		FXApplication.launchStage(this.chatWindow);

		this.chatWindow.getSendButton().setOnAction(this);
		this.chatWindow.getInputField().setOnKeyPressed(this);
	}

	private void doSend() {
		try {
			if (this.chatWindow.getInputField().getText().length() > MAX_MESSAGE_LENGTH) {
				this.chatWindow.appendText(LocalDateTime.now(), "System", "The message was too long! Maximum length: " + MAX_MESSAGE_LENGTH + ", Length of your message: " + this.chatWindow.getInputField().getText().length());
			} else {
				this.chatClient.send(new ChatMessage(this.nick, this.chatWindow.getInputField().getText()));
			}
		} catch (Exception e) {
			LOGGER.throwing(e);
		}
		this.chatWindow.getInputField().setText("");
	}

	@Override
	public void handle(Message message) {
		message = new ToUpperCase(message);
		message = new SmileyToLOL(message);
		message = new SmileyToSmileyface(message);
		message = new ReneIsKing(message);
		if (this.chatWindow != null && this.chatWindow.getBadwordFilterCheckBox() != null && this.chatWindow.getBadwordFilterCheckBox().isSelected()) {
			message = new FilterBadWords(message);
		}

		// Dirty hack to fix final issue
		final Message msg = message;
		Platform.runLater(() -> {
			this.chatWindow.appendMessage(LocalDateTime.now(), msg);
		});
	}

	@Override
	public void handle(Event e) {
		if (e.getEventType() == KeyEvent.KEY_PRESSED) {
			KeyEvent event = (KeyEvent) e;
			if (event.getSource() == this.chatWindow.getInputField()) {
				if (event.getCode() == KeyCode.ENTER) {
					this.doSend();
				}
			}
		} else if (e.getEventType() == ActionEvent.ACTION) {
			ActionEvent event = (ActionEvent) e;
			if (event.getSource() == this.chatWindow.getSendButton()) {
				this.doSend();
			}
		}
	}

	/**
	 * Gets the IP Address of the current Chat
	 * 
	 * @return IP Address
	 */
	public InetAddress getIp() {
		return ip;
	}

	/**
	 * Gets the Port of the current Chat
	 * 
	 * @return Port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Gets the Nickname of the user of the current Chat
	 * 
	 * @return Nickname
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Gets the ChatClient used by this Chat
	 * 
	 * @return ChatClient
	 */
	public ChatClient getChatClient() {
		return chatClient;
	}

	/**
	 * Gets the ChatWindow used by this Chat
	 * 
	 * @return ChatWindow
	 */
	public ChatWindow getChatWindow() {
		return chatWindow;
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

			Chat c = new Chat(ip, port, nick);
			c.launch();
		} catch (Exception e) {
			LOGGER.throwing(e);
		}
	}
}

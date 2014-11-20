package at.hollanderpoecher.chat.test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import org.junit.Before;
import org.junit.Test;

import at.hollanderpoecher.chat.Chat;
import at.hollanderpoecher.chat.network.ChatClient;
import at.hollanderpoecher.chat.util.Util;

/**
 * Test Cases for Chat Class
 * 
 * @author Rene Hollander
 */
public class TestChat {

	private static final InetAddress IP;
	private static int PORT = 5000;

	static {
		InetAddress ip = null;
		try {
			ip = InetAddress.getByName("239.255.255.250");
		} catch (UnknownHostException e) {
			ip = null;
			e.printStackTrace();
		}
		IP = ip;
	}

	/**
	 * Randomize the port before every test case
	 */
	@Before
	public void randomizePort() {
		PORT = (int) (Math.random() * 60000);
	}

	/**
	 * Test sending a message over the GUI
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	@Test
	public void testSendMessage() throws UnknownHostException, IOException {
		ChatClient chatClient = new ChatClient(IP, PORT);
		chatClient.setHandler((message) -> {
			assertEquals("Test message!", message.getMsg());
			assertEquals("testuser1", message.getNick());
			Util.closeSilently(chatClient);
		});

		Chat chat = new Chat(IP, PORT, "testuser1");
		chat.launch();
		chat.getChatWindow().getInputField().setText("Test message!");
		chat.getChatWindow().getSendButton().fire();
	}

	/**
	 * Test sending a message with the Enter Key from the GUI
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	@Test
	public void testSendMessageWithEnter() throws UnknownHostException, IOException {
		ChatClient chatClient = new ChatClient(IP, PORT);
		chatClient.setHandler((message) -> {
			assertEquals("Test message!", message.getMsg());
			assertEquals("testuser1", message.getNick());
			Util.closeSilently(chatClient);
		});

		Chat chat = new Chat(IP, PORT, "testuser1");
		chat.launch();
		chat.getChatWindow().getInputField().setText("Test message!");
		chat.getChatWindow().getInputField().fireEvent(new KeyEvent(chat.getChatWindow().getInputField(), chat.getChatWindow().getInputField(), KeyEvent.KEY_PRESSED, "\n", "\n", KeyCode.ENTER, false, false, false, false));
	}

	/**
	 * Test if a bad word is getting masked
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	@Test
	public void testSendBadWord() throws UnknownHostException, IOException {
		ChatClient chatClient = new ChatClient(IP, PORT);
		chatClient.setHandler((message) -> {
			assertEquals("$%&*", message.getMsg());
			assertEquals("testuser1", message.getNick());
			Util.closeSilently(chatClient);
		});

		Chat chat = new Chat(IP, PORT, "testuser1");
		chat.launch();
		chat.getChatWindow().getInputField().setText("idiot");
		chat.getChatWindow().getSendButton().fire();
	}
}

package at.hollanderpoecher.chat.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.junit.Test;

import at.hollanderpoecher.chat.network.ChatClient;
import at.hollanderpoecher.chat.network.ChatMessage;

public class TestChatClient {

	@Test
	public void testContstructChatClient() throws UnknownHostException, IOException {
		ChatClient cc = new ChatClient(InetAddress.getByName("239.255.255.250"), 5000, (msg) -> {
			System.out.println(msg);
		});
		cc.close();
	}

	@Test(expected = SocketException.class)
	public void testWrongIp() throws UnknownHostException, IOException {
		@SuppressWarnings({ "unused", "resource" })
		ChatClient cc = new ChatClient(InetAddress.getByName("127.0.0.1"), 5000, (msg) -> {
			System.out.println(msg);
		});
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWrongPort() throws UnknownHostException, IOException {
		@SuppressWarnings({ "unused", "resource" })
		ChatClient cc = new ChatClient(InetAddress.getByName("239.255.255.250"), -1, (msg) -> {
			System.out.println(msg);
		});
	}

	@Test
	public void testSendMessage() throws UnknownHostException, IOException {
		ChatClient cc2 = new ChatClient(InetAddress.getByName("239.255.255.250"), 5000);
		cc2.setHandler(msg -> {
			assertEquals("Hello!", msg.getMsg());
			try {
				cc2.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		ChatClient cc1 = new ChatClient(InetAddress.getByName("239.255.255.250"), 5000, (msg) -> {
		});

		cc1.send(new ChatMessage("testuser1", "Hello!"));
		cc1.close();
	}

	@Test
	public void testSendNick() throws UnknownHostException, IOException {
		ChatClient cc1 = new ChatClient(InetAddress.getByName("239.255.255.250"), 5000, (msg) -> {
		});

		cc1.send(new ChatMessage("testuser1", "Hello!"));

		ChatClient cc2 = new ChatClient(InetAddress.getByName("239.255.255.250"), 5000, (msg) -> {
			assertEquals("testuser1", msg.getNick());
		});
	}
}

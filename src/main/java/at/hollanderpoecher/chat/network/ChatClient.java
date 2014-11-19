package at.hollanderpoecher.chat.network;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import at.hollanderpoecher.chat.util.Handler;

public class ChatClient implements Handler<byte[], InetAddress>, Closeable {

	private static final Charset CHARSET = StandardCharsets.UTF_8;

	private Handler<String, InetAddress> handler;
	private Client client;

	public ChatClient(InetAddress groupAdress, int port, Handler<String, InetAddress> handler) throws IOException {
		this.handler = handler;
		this.client = new Client(groupAdress, port, this);
	}

	public void send(String data) throws IOException {
		this.client.send(data.getBytes(CHARSET));
	}

	@Override
	public void handle(byte[] param1, InetAddress param2) {
		this.handler.handle(new String(param1, CHARSET), param2);
	}

	@Override
	public void close() throws IOException {
		this.client.close();
	}
}

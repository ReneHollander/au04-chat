package at.hollanderpoecher.chat.network;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class Message {

	private String msg;

	private InetAddress senderAddress;

	public Message(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public InetAddress getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(InetAddress senderAddress) {
		this.senderAddress = senderAddress;
	}

	public static byte[] toByteArray(Message message) {
		byte[] data = message.getMsg().getBytes(StandardCharsets.UTF_8);
		return data;
	}

	public static Message fromByteArray(byte[] input) {
		String msg = new String(input, StandardCharsets.UTF_8);
		Message message = new Message(msg);
		return message;
	}
}

package at.hollanderpoecher.chat.network;

import at.hollanderpoecher.chat.interfaces.Message;

import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class ChatMessage implements Message {
	private String msg;

	private InetAddress senderAddress;

	public ChatMessage(String msg) {
		this.setMsg(msg);
	}

    public void setMsg(String msg) {
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
        Message message = new ChatMessage(msg);
		return message;
	}
}

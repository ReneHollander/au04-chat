package at.hollanderpoecher.chat.network;

import java.net.InetAddress;

import at.hollanderpoecher.chat.interfaces.Message;

public class ChatMessage implements Message {

	private String nick;
	private String msg;

	private InetAddress senderAddress;

	public ChatMessage(String nick, String msg) {
		this.setNick(nick);
		this.setMsg(msg);
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return this.nick;
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

}

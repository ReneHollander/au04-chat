package at.hollanderpoecher.chat.network;

import java.net.InetAddress;

import at.hollanderpoecher.chat.interfaces.Message;

/**
 * Implementation of the Message. Holds a Nickname, Content and the IP of the
 * Sender of the Message
 * 
 * @author Rene Hollander
 */
public class ChatMessage implements Message {

	private String nick;
	private String msg;

	private InetAddress senderAddress;

	/**
	 * Constructs a new ChatMessage with the given nick and msg
	 * 
	 * @param nick
	 *            Nickname of the sender
	 * @param msg
	 *            Message to send over the network
	 */
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

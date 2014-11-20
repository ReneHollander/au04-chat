package at.hollanderpoecher.chat.decorator;

import java.net.InetAddress;

import at.hollanderpoecher.chat.interfaces.Message;

/**
 *  The Message Decorator
 *
 * @author Rene Poecher
 */
public abstract class MessageDecorator implements Message {

	private Message message;

	public MessageDecorator(Message message) {
		this.message = message;
	}

	public void setNick(String nick) {
		this.message.setNick(nick);
	}

	public String getNick() {
		return this.message.getNick();
	}

	public void setMsg(String msg) {
		this.message.setMsg(msg);
	}

	public String getMsg() {
		return this.message.getMsg();
	}

	public void setSenderAddress(InetAddress senderAddress) {
		this.message.setSenderAddress(senderAddress);
	}

	public InetAddress getSenderAddress() {
		return this.message.getSenderAddress();
	}

}

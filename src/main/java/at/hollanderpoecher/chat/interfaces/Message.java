package at.hollanderpoecher.chat.interfaces;

import java.net.InetAddress;

public interface Message {

	public void setNick(String nick);

	public String getNick();

	public void setMsg(String msg);

	public String getMsg();

	public void setSenderAddress(InetAddress senderAddress);

	public InetAddress getSenderAddress();

}

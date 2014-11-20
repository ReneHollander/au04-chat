package at.hollanderpoecher.chat.interfaces;

import java.net.InetAddress;

/**
 * A Message that gets sent over the network
 * 
 * @author Rene Hollander
 */
public interface Message {

	/**
	 * Set the nickname of the sender of the message
	 * 
	 * @param nick
	 *            Nickname
	 */
	public void setNick(String nick);

	/**
	 * Get the nickname of the sender of the message
	 * 
	 * @return Nickname
	 */
	public String getNick();

	/**
	 * Set the actual message of the message
	 * 
	 * @param msg
	 *            Message
	 */
	public void setMsg(String msg);

	/**
	 * Get the message of the actual message
	 * 
	 * @return Message
	 */
	public String getMsg();

	/**
	 * Set the IP Adress of the sender (is not getting sent over the network)
	 * 
	 * @param senderAddress
	 *            SenderAdress
	 */
	public void setSenderAddress(InetAddress senderAddress);

	/**
	 * Get the IP Adress of the sender (is not getting sent over the network)
	 * 
	 * @return SenderAdress
	 */
	public InetAddress getSenderAddress();

}

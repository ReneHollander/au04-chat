package at.hollanderpoecher.chat.network;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.hollanderpoecher.chat.interfaces.Message;
import at.hollanderpoecher.chat.util.Handler;
import at.hollanderpoecher.chat.util.Util;

/**
 * A Chat Client that connects to a Multicast Group to send and recieve messages
 * 
 * @author Rene Hollander
 */
public class ChatClient implements Closeable {

	private static final int BUFFER_SIZE = 4 * 1024;

	private InetAddress groupAdress;
	private int port;

	private MulticastSocket socket;
	private Handler<Message> handler;
	private SocketReader socketReader;

	/**
	 * Construct a new ChatClient and start listening for messages
	 * 
	 * @param groupAdress
	 *            IP Adress of the group
	 * @param port
	 *            Port of the Multicast Socket
	 * @param handler
	 *            Handler to handle recieved messages
	 * @throws IOException
	 *             Throws an Exception of there are issues connecting to the
	 *             Multicast Socket
	 */
	public ChatClient(InetAddress groupAdress, int port, Handler<Message> handler) throws IOException {
		this.groupAdress = groupAdress;
		this.port = port;
		this.setHandler(handler);

		this.socket = new MulticastSocket(this.port);
		this.socket.setReuseAddress(true);
		this.socket.joinGroup(this.groupAdress);
		this.socketReader = new SocketReader(this.socket, this.handler);
	}

	/**
	 * Construct a new ChatClient and start listening for messages
	 * 
	 * @param groupAdress
	 *            IP Adress of the group
	 * @param port
	 *            Port of the Multicast Socket
	 * @throws IOException
	 *             Throws an Exception of there are issues connecting to the
	 *             Multicast Socket
	 */
	public ChatClient(InetAddress groupAdress, int port) throws IOException {
		this(groupAdress, port, null);
	}

	/**
	 * Set the handler to handle Message Events
	 * 
	 * @param handler
	 *            Handler to handle Message Events
	 */
	public void setHandler(Handler<Message> handler) {
		this.handler = handler;
	}

	/**
	 * Send a Message to the connected Multicast Group
	 * 
	 * @param message
	 *            Message to send to the group
	 * @throws IOException
	 *             Throws IOException on error sending packet
	 */
	public void send(Message message) throws IOException {
		byte[] data = MessageUtils.toByteArray(message);
		DatagramPacket packet = new DatagramPacket(data, data.length, this.groupAdress, this.port);
		this.socket.send(packet);
	}

	private class SocketReader implements Runnable, Closeable {

		final Logger LOGGER = LogManager.getLogger(SocketReader.class);

		private MulticastSocket socket;
		private Handler<Message> handler;

		private boolean running;
		private Thread thread;

		public SocketReader(MulticastSocket socket, Handler<Message> handler) {
			this.socket = socket;
			this.handler = handler;

			this.running = true;
			this.thread = new Thread(this);
			this.thread.start();
		}

		@Override
		public void run() {
			while (this.running) {
				DatagramPacket packet = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				try {
					this.socket.receive(packet);
				} catch (IOException e) {
					if (this.running == true) {
						LOGGER.throwing(e);
					}
				}
				if (handler != null) {
					Message message = MessageUtils.fromByteArray(packet.getData());
					message.setSenderAddress(packet.getAddress());
					this.handler.handle(message);
				}
			}
		}

		@Override
		public void close() throws IOException {
			this.running = false;
		}
	}

	@Override
	public void close() throws IOException {
		Util.closeSilently(this.socketReader);
		this.socket.leaveGroup(this.groupAdress);
		Util.closeSilently(this.socket);
	}

}

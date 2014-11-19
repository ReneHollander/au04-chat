package at.hollanderpoecher.chat.network;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import at.hollanderpoecher.chat.util.Handler;
import at.hollanderpoecher.chat.util.Util;

public class Client implements Closeable {

	private static final int BUFFER_SIZE = 4 * 1024;

	private InetAddress groupAdress;
	private int port;

	private MulticastSocket socket;
	private Handler<byte[], InetAddress> handler;
	private SocketReader socketReader;

	public Client(InetAddress groupAdress, int port, Handler<byte[], InetAddress> handler) throws IOException {
		this.groupAdress = groupAdress;
		this.port = port;
		this.handler = handler;

		this.socket = new MulticastSocket(this.port);
		this.socket.setReuseAddress(true);
		this.socket.joinGroup(this.groupAdress);
		this.socketReader = new SocketReader(this.socket, this.handler);
	}

	public void send(byte[] data) throws IOException {
		DatagramPacket packet = new DatagramPacket(data, data.length, this.groupAdress, this.port);
		this.socket.send(packet);
	}

	private class SocketReader implements Runnable, Closeable {

		private MulticastSocket socket;
		private Handler<byte[], InetAddress> handler;

		private boolean running;
		private Thread thread;

		public SocketReader(MulticastSocket socket, Handler<byte[], InetAddress> handler) {
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
						e.printStackTrace();
					}
				}
				this.handler.handle(packet.getData(), packet.getAddress());
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

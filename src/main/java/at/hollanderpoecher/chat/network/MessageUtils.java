package at.hollanderpoecher.chat.network;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import at.hollanderpoecher.chat.interfaces.Message;

public class MessageUtils {

	private static final Charset charset = Charset.forName("UTF-8");
	private static final CharsetEncoder encoder = charset.newEncoder();
	private static final CharsetDecoder decoder = charset.newDecoder();

	public static byte[] toByteArray(Message message) {
		try {
			byte[] nickBytes = encoder.encode(CharBuffer.wrap(message.getNick())).array();
			byte[] msgBytes = encoder.encode(CharBuffer.wrap(message.getMsg())).array();

			ByteBuffer byteBuffer = ByteBuffer.allocate(4 + nickBytes.length + 4 + msgBytes.length);

			byteBuffer.putInt(nickBytes.length);
			byteBuffer.put(nickBytes);

			byteBuffer.putInt(msgBytes.length);
			byteBuffer.put(msgBytes);

			return byteBuffer.array();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Message fromByteArray(byte[] input) {
		try {
			ByteBuffer byteBuffer = ByteBuffer.wrap(input);

			int nickBytesLength = byteBuffer.getInt();
			byte[] nickBytes = new byte[nickBytesLength];
			byteBuffer.get(nickBytes);
			String nick = new String(decoder.decode(ByteBuffer.wrap(nickBytes)).array());

			int msgBytesLength = byteBuffer.getInt();
			byte[] msgBytes = new byte[msgBytesLength];
			byteBuffer.get(msgBytes);
			String msg = new String(decoder.decode(ByteBuffer.wrap(msgBytes)).array());

			return new ChatMessage(nick, msg);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

package at.hollanderpoecher.chat.decorator;

import at.hollanderpoecher.chat.interfaces.Message;
import at.hollanderpoecher.chat.util.Util;

/**
 * A Message Decorator which split the Message
 *
 * @author Rene Poecher
 */

public class StringBufferSize extends MessageDecorator {
	private static final int splitsize = 50;

	public StringBufferSize(Message message) {
		super(message);
	}

	@Override
	public String getMsg() {
		String ret = super.getMsg();

		ret = Util.splitString(ret, splitsize);

		return ret;
	}

}

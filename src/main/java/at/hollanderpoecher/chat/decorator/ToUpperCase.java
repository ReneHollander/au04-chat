package at.hollanderpoecher.chat.decorator;

import at.hollanderpoecher.chat.interfaces.Message;

/**
 *  A Message Decorator which change all letters into uppercase
 *
 * @author Rene Poecher
 */
public class ToUpperCase extends MessageDecorator {

	public ToUpperCase(Message message) {
		super(message);
	}

	@Override
	public String getMsg() {
		return super.getMsg().toUpperCase();
	}
}

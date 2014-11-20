package at.hollanderpoecher.chat.decorator;

import at.hollanderpoecher.chat.interfaces.Message;
import at.hollanderpoecher.chat.util.Util;

/**
 * A Message Decorator which filter bad words and change them int some symbol's.
 *
 * @author Rene Poecher
 */
public class FilterBadWords extends MessageDecorator {

	private static final String[] BAD_WORDS = { "arschloch", "faggot", "idiot" };
	private static final String REPLACE_STRING = "$%&*";


    /**
     * Use the Message Constructor
     *
     * @param message the message object
     */
	public FilterBadWords(Message message) {
		super(message);
	}



	@Override
	public String getMsg() {
		String ret = super.getMsg();
		for (String badWord : BAD_WORDS) {
			ret = Util.replaceAll(badWord, REPLACE_STRING, ret, true);
		}
		return ret;
	}

}

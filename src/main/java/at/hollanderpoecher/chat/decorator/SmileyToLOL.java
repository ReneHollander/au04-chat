package at.hollanderpoecher.chat.decorator;

import at.hollanderpoecher.chat.interfaces.Message;
import at.hollanderpoecher.chat.util.Util;

/**
 * A Message Decorator which change Smileys into *lol*
 *
 * @author Rene Poecher
 */

public class SmileyToLOL extends MessageDecorator {

	private static final String[] SMILEYS = { ":)", "=)", ":D" };
	private static final String REPLACE_STRING = "*lol*";

    /**
     * Use the Message Constructor
     *
     * @param message the message object
     */
	public SmileyToLOL(Message message) {
		super(message);
	}


	@Override
	public String getMsg() {
		String ret = super.getMsg();
		for (String badWord : SMILEYS) {
			ret = Util.replaceAll(badWord, REPLACE_STRING, ret, true);
		}
		return ret;
	}

}
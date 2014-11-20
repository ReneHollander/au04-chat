package at.hollanderpoecher.chat.decorator;

import at.hollanderpoecher.chat.interfaces.Message;
import at.hollanderpoecher.chat.util.Util;

/**
 * Created by rene on 19.11.14.
 */
public class SmileyToSmileyface extends MessageDecorator {

    private static final String[] SMILEYS = {";)", ";-)", ";D"};
    private static final String REPLACE_STRING = "☻";

    public SmileyToSmileyface(Message message) {
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
package at.hollanderpoecher.chat.decorator;


import at.hollanderpoecher.chat.interfaces.Message;
import at.hollanderpoecher.chat.util.Util;


/**
 *  A Message Decorator which add to the word Rene a symbol and change the e into é
 *
 * @author Rene Poecher
 */
public class ReneIsKing extends MessageDecorator {

    private static final String[] SMILEYS = {"rene","Rene","René"};
    private static final String REPLACE_STRING = "René♕";

    public ReneIsKing(Message message) {
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

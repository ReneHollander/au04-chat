package at.hollanderpoecher.chat.decorator;

import at.hollanderpoecher.chat.interfaces.Message;

/**
 * Created by rene on 19.11.14.
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

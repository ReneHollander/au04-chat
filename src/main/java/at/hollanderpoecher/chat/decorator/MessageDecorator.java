package at.hollanderpoecher.chat.decorator;

import at.hollanderpoecher.chat.interfaces.Message;

import java.net.InetAddress;

/**
 * Created by rene on 19.11.14.
 */
public abstract class MessageDecorator implements Message {

    private Message message;

    public MessageDecorator(Message message) {
        this.message = message;
    }

    public String getMsg() {
        return this.message.getMsg();
    }

    public void setSenderAddress(InetAddress senderAddress) {
       this.message.setSenderAddress(senderAddress);
    }

    public InetAddress getSenderAddress() {
        return this.message.getSenderAddress();
    }

}

package at.hollanderpoecher.chat.interfaces;


import java.net.InetAddress;

public interface Message {

    public String getMsg();
    public void setSenderAddress(InetAddress senderAddress);
    public InetAddress getSenderAddress();

}

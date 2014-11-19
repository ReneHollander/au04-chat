package at.hollanderpoecher.chat.decorator;

import at.hollanderpoecher.chat.interfaces.ChatClientInterface;

import java.io.IOException;
import java.net.InetAddress;

public abstract class ChatClientDecorator implements ChatClientInterface {

    private ChatClientInterface chatClient;

    public ChatClientDecorator(ChatClientInterface chatClient) {
        this.chatClient = chatClient;
    }

    public void send(String data) throws IOException {
        this.chatClient.send(data);
    }

    public void handle(byte[] param1, InetAddress param2) {
        this.chatClient.handle(param1, param2);
    }

    public void close() throws IOException {
        this.chatClient.close();
    }

}

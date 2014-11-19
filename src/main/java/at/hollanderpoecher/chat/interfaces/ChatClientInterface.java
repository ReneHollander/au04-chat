package at.hollanderpoecher.chat.interfaces;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface ChatClientInterface {

    public static final Charset CHARSET = StandardCharsets.UTF_8;

    public void send(String data) throws IOException;

    public void handle(byte[] param1, InetAddress param2);

    public void close() throws IOException;

}

package at.hollanderpoecher.chat.util;

@FunctionalInterface
public interface Handler<P, Q> {

	public void handle(P param1, Q param2);

}

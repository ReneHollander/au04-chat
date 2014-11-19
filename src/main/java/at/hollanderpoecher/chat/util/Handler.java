package at.hollanderpoecher.chat.util;

@FunctionalInterface
public interface Handler<P> {

	public void handle(P param);

}

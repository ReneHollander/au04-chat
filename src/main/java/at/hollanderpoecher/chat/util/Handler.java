package at.hollanderpoecher.chat.util;

/**
 * 
 * Simple Interface to Implement Handling a result.
 * 
 * @author Hollander
 *
 * @param <P>
 *            Type of the Parameter
 */
@FunctionalInterface
public interface Handler<P> {

	/**
	 * Handle a Result without a return value
	 * 
	 * @param param
	 *            Parameter
	 */
	public void handle(P param);

}

package at.hollanderpoecher.chat.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Util class
 * 
 * @author Rene Hollander
 */
public class Util {

	/**
	 * Closes a Closeable silently (Without throwing an exception)
	 * 
	 * @param closeable
	 *            Closeable that shoudl be closed
	 */
	public static void closeSilently(Closeable closeable) {
		try {
			closeable.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Gets the local IP of the computer
	 * 
	 * @return Local IP of the computer
	 */
	public static String getIp() {
		String ipAddress = null;
		Enumeration<NetworkInterface> net = null;
		try {
			net = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			throw new RuntimeException(e);
		}
		while (net.hasMoreElements()) {
			NetworkInterface element = net.nextElement();
			Enumeration<InetAddress> addresses = element.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress ip = addresses.nextElement();
				if (ip instanceof Inet4Address) {
					if (ip.isSiteLocalAddress()) {
						ipAddress = ip.getHostAddress();
					}
				}
			}
		}
		return ipAddress;
	}

	/**
	 * Replace all occurences of a text with the replace text in a string. You
	 * can specifiy to ingore case sensitivity.
	 * 
	 * Source: Quelle: http://stackoverflow.com/a/16574312
	 * 
	 * @param findtxt
	 *            Text that should be replaces
	 * @param replacetxt
	 *            Text to replace the found text
	 * @param str
	 *            String where to replace the text
	 * @param isCaseInsensitive
	 *            Ignore case
	 * @return String with replaced text
	 */
	public static String replaceAll(String findtxt, String replacetxt, String str, boolean isCaseInsensitive) {
		if (str == null) {
			return null;
		}
		if (findtxt == null || findtxt.length() == 0) {
			return str;
		}
		if (findtxt.length() > str.length()) {
			return str;
		}
		int counter = 0;
		String thesubstr = "";
		while ((counter < str.length()) && (str.substring(counter).length() >= findtxt.length())) {
			thesubstr = str.substring(counter, counter + findtxt.length());
			if (isCaseInsensitive) {
				if (thesubstr.equalsIgnoreCase(findtxt)) {
					str = str.substring(0, counter) + replacetxt + str.substring(counter + findtxt.length());
					// Failing to increment counter by replacetxt.length()
					// leaves you open
					// to an infinite-replacement loop scenario: Go to replace
					// "a" with "aa" but
					// increment counter by only 1 and you'll be replacing 'a's
					// forever.
					counter += replacetxt.length();
				} else {
					counter++; // No match so move on to the next character from
					// which to check for a findtxt string match.
				}
			} else {
				if (thesubstr.equals(findtxt)) {
					str = str.substring(0, counter) + replacetxt + str.substring(counter + findtxt.length());
					counter += replacetxt.length();
				} else {
					counter++;
				}
			}
		}
		return str;
	}

    //http://www.java-forum.org/allgemeine-java-themen/27218-zeilenumbruch-string-jeweils-x-zeichen-einfuegen.html
    public static String splitString( String s, int col )
    {
        StringBuffer res = new StringBuffer( s );

        col++;

        for( int i = 0; i <= ( s.length() / ( col - 1 ) ); ++ i )
            res.insert( i  * col, '\n' );
        return res.toString();
    }

}

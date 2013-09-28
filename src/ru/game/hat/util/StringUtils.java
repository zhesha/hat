package ru.game.hat.util;

public class StringUtils {
	
	/**
	 * Used String.length() because of compatibility with API 8.
	 * @return true if <b>val</b> = <code>null</code> or empty.
	 */
	public static boolean isEmpty(String val) {
		return val == null || val.length() == 0;
	}
}

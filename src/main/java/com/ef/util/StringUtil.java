package com.ef.util;

/**
 * Utility methods to string manipulation.
 * @author patriciaespada
 *
 */
public class StringUtil {
	
	private StringUtil() { }
	
	/**
	 * Unquote a given string.
	 * @param str
	 * @return string without the start and end quotation
	 */
	public static String unquote(String str) {
		int length = str == null ? -1 : str.length();
		if (str == null || str.isEmpty()) {
			return str;
		}

		if (str.charAt(0) == '\"' && str.charAt(length - 1) == '\"' ) {
			str = str.substring(1, length - 1);
		}

		return str;
	}

}

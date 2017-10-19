package be.vdab.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class StringUtils {
	public static boolean isLong(String string) {
		try {
			Long.parseLong(string);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public static boolean isBigDecimal(String string) {
		try {
			new BigDecimal(string);
			return true;
		} catch (NullPointerException|NumberFormatException ex) {
			return false;
		}
	}
	
	/**
	 * @param regex - regex-patroon gebaseerd op patronen voor strings zoals die 
	 * voorkomen in de database retrovideo
	 */
	public static boolean isWellFormed(String string, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		return string != null && matcher.matches();
	}
}

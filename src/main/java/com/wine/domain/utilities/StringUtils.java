package com.wine.domain.utilities;

public class StringUtils {

	public static boolean checkIfIsBlankOrNull(String s) {
		return s == null || s.isEmpty();
	}
	
	public static boolean checkSizeField(String s, int length) {
		return !checkIfIsBlankOrNull(s) && s.length() == length;
	}


}

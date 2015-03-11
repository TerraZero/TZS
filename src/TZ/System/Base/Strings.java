package TZ.System.Base;


/**
 * 
 * @author terrazero
 * @created Dec 17, 2014
 * 
 * @file Strings.java
 * @project G7C
 * @identifier TZ.Strings
 *
 */
public class Strings {

	public static String clearString(String string, String clear) {
		return string.replaceAll(clear, "");
	}
	
	public static int countChar(String s, char c) {
		return s.replaceAll("[^" + c + "]","").length();
	}

	public static boolean isIntern(String search, String... array) {
		for (int i = 0; i < array.length; i++) {
			if (Base.is(array[i], search)) return true;
		}
		return false;
	}
	
	public static String[] merge(String s1, String... s2) {
		String[] array = new String[s2.length + 1];
		array[0] = s1;
		for (int i = 0; i < s2.length; i++) {
			array[i + 1] = s2[i];  
		}
		return array;
	}
	
}

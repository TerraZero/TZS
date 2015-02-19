package TZ.System.Base;

import TZ.System.Base.Functions.Searcher;

/**
 * 
 * @author terrazero
 * @created Dec 11, 2014
 * 
 * @file Base.java
 * @project G7C
 * @identifier TZ.Array
 *
 */
public class Base {

	public static boolean isIntern(Object search, Object[] array) {
		for (int i = 0; i < array.length; i++) {
			if (Base.is(array[i], search)) return true;
		}
		return false;
	}
	
	public static<type> boolean isIntern(type search, type[] array, Searcher<type> function) {
		for (int i = 0; i < array.length; i++) {
			if (function.is(search, array[i])) return true;
		}
		return false;
	}
	
	public static boolean is(Object one, Object two) {
		if (one != null && two != null) {
			return one.equals(two);
		}
		return one == null && two == null;
	}
	
}

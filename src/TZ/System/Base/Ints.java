package TZ.System.Base;

/**
 * 
 * @author terrazero
 * @created Dec 15, 2014
 * 
 * @file Ints.java
 * @project G7C
 * @identifier TZ.Ints
 *
 */
public class Ints {

	public static boolean isBit(int integer, int pos) {
		return ((integer >> pos) & 1) == 1;
	}
	
	public static int setBit(int integer, int pos, boolean bit) {
		if (bit) {
			integer = integer |= 1 << pos;
		} else {
			integer = integer &= ~(1 << pos);
		}
		return integer;
	}
	
}

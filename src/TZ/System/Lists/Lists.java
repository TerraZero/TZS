package TZ.System.Lists;

import java.util.Collections;
import java.util.List;

/**
 * 
 * @author terrazero
 * @created Feb 23, 2015
 * 
 * @file Lists.java
 * @project TZS
 * @identifier TZ.System.Lists
 *
 */
public class Lists {

	public static<type extends Weighted> void sortASC(List<type> sort) {
		Collections.sort(sort, (i1, i2) -> i1.weight() - i2.weight());
	}
	
	public static<type extends Weighted> void sortDESC(List<type> sort) {
		Collections.sort(sort, (i1, i2) -> i2.weight() - i1.weight());
	}
	
}

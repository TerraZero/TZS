package TZ.System.Mechnic.Mechnics;

import java.util.ArrayList;

import TZ.System.Mechnic.Mech;

/**
 * 
 * @author terrazero
 * @created Feb 13, 2015
 * 
 * @file GArrayList.java
 * @project G7C
 * @identifier TZ.Mechnic.Mechnics
 *
 */
@Mech(name = "arraylist", mechnic = "list")
public class GArrayList {

	public static ArrayList<?> mechnic(Object... args) {
		ArrayList<Object> create = new ArrayList<Object>();
		if (args.length != 0) {
			for (Object o : args) {
				create.add(o);
			}
		}
		return create;
	}

}

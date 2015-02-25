package TZ.System.Mechnic.Mechnics;

import java.util.ArrayList;

import TZ.System.Mechnic.Mech;
import TZ.System.Mechnic.MechnicCreator;

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
@Mech(option = "arraylist", mechnic = "list")
public class GArrayList implements MechnicCreator<ArrayList<?>> {
	
	@Override
	public ArrayList<?> mechnic(Object[] args) {
		ArrayList<Object> create = null;
		
		if (args.length > 0 && args[0].getClass() == Integer.class) {
			System.out.println("length: " + args[0]);
			create = new ArrayList<Object>((int)args[0]);
		} else {
			create = new ArrayList<Object>();
		}
		if (args.length != 0) {
			for (Object o : args) {
				create.add(o);
			}
		}
		return create;
	}

}

package TZ.System.Mechnic.Mechnics;

import java.util.ArrayList;

import TZ.System.Mechnic.MCreator;
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
@MCreator(option = "arraylist", mechnic = "list", base = true)
public class MechnicList implements MechnicCreator<ArrayList<?>> {
	
	@Override
	public ArrayList<?> mechnic(Object[] args) {
		ArrayList<Object> create = null;
		int i = 0;
		
		if (args.length > 0 && args[0].getClass() == Integer.class) {
			create = new ArrayList<Object>((int)args[0]);
			i = 1;
		} else {
			create = new ArrayList<Object>();
		}
		for (; i < args.length; i++) {
			create.add(args[i]);
		}
		return create;
	}

}

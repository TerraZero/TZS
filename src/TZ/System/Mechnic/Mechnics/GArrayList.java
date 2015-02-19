package TZ.System.Mechnic.Mechnics;

import java.util.ArrayList;

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
public class GArrayList implements MechnicCreator<ArrayList<?>> {

	/* 
	 * @see TZ.Mechnic.MechnicClass#mechnicName()
	 */
	@Override
	public String mechnicName() {
		return "list";
	}

	/* 
	 * @see TZ.Mechnic.MechnicClass#mechnic(java.lang.Object[])
	 */
	@Override
	public ArrayList<?> mechnic(Object... args) {
		ArrayList<Object> create = new ArrayList<Object>();
		if (args.length != 0) {
			for (Object o : args) {
				create.add(o);
			}
		}
		return create;
	}

}

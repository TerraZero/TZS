package TZ.System.Mechnic.Mechnics;

import java.util.HashMap;

import TZ.System.Mechnic.MCreator;
import TZ.System.Mechnic.MechnicCreator;

/**
 * 
 * @author Terra
 * @created 28.02.2015
 * 
 * @file MechnicMap.java
 * @project TZS
 * @identifier TZ.System.Mechnic.Mechnics
 *
 */
@MCreator(option = "hashmap", mechnic = "map", base = true)
public class MechnicMap implements MechnicCreator<HashMap<?, ?>> {

	@Override
	public HashMap<?, ?> mechnic(Object[] args) {
		return new HashMap<Object, Object>();
	}

}

package TZ.System.Mechnic;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Terra
 * @created 11.02.2015
 * 
 * @file Mechnik.java
 * @project G7C
 * @identifier TZ.Mechnic
 *
 */
public class Mechnic {
	
	private static Mechnic singleton;
	
	static {
		Mechnic.singleton = new Mechnic();
		// TODO
	}
	
	public static void register(MechnicCreator<?> mechnic) {
		Mechnic.singleton.mechnicRegister(mechnic);
	}
	
	public static<type> type get(String name, Object... args) {
		return Mechnic.singleton.mechnicGet(name, args);
	}

	protected Map<String, MechnicCreator<?>> mechnics;
	
	public Mechnic() {
		this.mechnics = new HashMap<String, MechnicCreator<?>>();
	}
	
	protected void mechnicRegister(MechnicCreator<?> mechnic) {
		this.mechnics.put(mechnic.mechnicName(), mechnic);
	}
	
	@SuppressWarnings("unchecked")
	protected<type> type mechnicGet(String name, Object... args) {
		MechnicCreator<?> mc = this.mechnics.get(name);
		return (type)mc.mechnic(args);
	}
	
}

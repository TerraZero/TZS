package TZ.System.Mechnic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TZ.System.Annotations.Info;
import TZ.System.Annotations.Functions.BootFunction;
import TZ.System.Reflect.Boot.Module;

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
@Info(weight = -1000)
public class Mechnic {
	
	private static Mechnic mechnic;
	
	@BootFunction
	public static void bootMechnic(String id, Module module, List<Module> classes) {
		Mechnic.mechnic = new Mechnic();
	}
	
	public static void register(MechnicCreator<?> mechnic) {
		Mechnic.mechnic.mechnicRegister(mechnic);
	}
	
	public static<type> type get(String name, Object... args) {
		return Mechnic.mechnic.mechnicGet(name, args);
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

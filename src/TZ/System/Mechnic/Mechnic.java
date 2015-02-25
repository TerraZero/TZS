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
		
		Module.forAnnotation(classes, Mech.class, (m, a) -> {
			m.reflect().instantiate();
			Mechnic.mechnic.mechnicRegister(a.mechnic(), m.reflect().getReflect());
		});
	}
	
	public static<type> type get(String name, Object... args) {
		return Mechnic.mechnic.mechnicGet(name, args);
	}

	protected Map<String, MechnicCreator<?>> mechnics;
	
	public Mechnic() {
		this.mechnics = new HashMap<String, MechnicCreator<?>>();
	}
	
	protected void mechnicRegister(String name, MechnicCreator<?> creator) {
		this.mechnics.put(name, creator);
	}
	
	@SuppressWarnings("unchecked")
	protected<type> type mechnicGet(String name, Object... args) {
		MechnicCreator<?> mechnic = this.mechnics.get(name);
		return (type)mechnic.mechnic(args);
	}
	
}

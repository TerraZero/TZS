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
		
		List<Module> mechnics = Module.getAnnotation(classes, Mech.class);
		
		for (Module mechnic : mechnics) {
			Mech mech = mechnic.reflect().annotation(Mech.class);
			Mechnic.mechnic.mechnicRegister(mech.mechnic(), mechnic);
		}
	}
	
	public static<type> type get(String name, Object... args) {
		return Mechnic.mechnic.mechnicGet(name, args);
	}

	protected Map<String, Module> mechnics;
	
	public Mechnic() {
		this.mechnics = new HashMap<String, Module>();
	}
	
	protected void mechnicRegister(String name, Module module) {
		this.mechnics.put(name, module);
	}
	
	protected<type> type mechnicGet(String name, Object... args) {
		Module module = this.mechnics.get(name);
		return module.reflect().call("mechnic", args);
	}
	
}

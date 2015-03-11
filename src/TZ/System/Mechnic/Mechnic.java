package TZ.System.Mechnic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TZ.System.Boot;
import TZ.System.Module;
import TZ.System.Annotations.Info;
import TZ.System.Annotations.Functions.BootFunction;

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
	public static void bootMechnic(String id, Module module, List<Boot> boots) {
		Mechnic.mechnic = new Mechnic();
		
		Boot.forAnnotations(boots, Mech.class, (wrapper) -> {
			wrapper.value().reflect().instantiate();
			Mechnic.mechnic.mechnicRegister(wrapper.info().mechnic(), wrapper.value().reflect().getReflect());
		});
	}
	
	public static<type> type get(String name, Object... args) {
		return Mechnic.mechnic.mechnicGet(name, args);
	}
	
	public static<type> type getContext(String context, String name, Object... args) {
		return Mechnic.mechnic.mechnicGetContext(context, name, args);
	}
	
	public static<type> MechnicCreator<type> getCreator(String context, String name) {
		return Mechnic.mechnic.mechnicCreator(context, name);
	}

	protected Map<String, MechnicCreator<?>> mechnics;
	
	public Mechnic() {
		this.mechnics = new HashMap<String, MechnicCreator<?>>();
	}
	
	protected void mechnicRegister(String name, MechnicCreator<?> creator) {
		this.mechnics.put(name, creator);
	}
	
	@SuppressWarnings("unchecked")
	public<type> MechnicCreator<type> mechnicCreator(String context, String name) {
		MechnicCreator<?> mechnic = this.mechnics.get(context + ":" + name);
		if (mechnic == null) mechnic = this.mechnics.get(name);
		return (MechnicCreator<type>)mechnic;
	}
	
	@SuppressWarnings("unchecked")
	protected<type> type mechnicGet(String name, Object... args) {
		MechnicCreator<?> mechnic = this.mechnics.get(name);
		return (type)mechnic.mechnic(args);
	}
	
	@SuppressWarnings("unchecked")
	protected<type> type mechnicGetContext(String context, String name, Object... args) {
		MechnicCreator<?> mechnic = this.mechnics.get(context + ":" + name);
		if (mechnic == null) mechnic = this.mechnics.get(name); 
		return (type)mechnic.mechnic(args);
	}
	
}

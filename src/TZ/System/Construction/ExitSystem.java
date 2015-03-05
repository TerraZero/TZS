package TZ.System.Construction;

import java.util.List;

import TZ.System.Module;
import TZ.System.TZSystem;
import TZ.System.Annotations.Construction;
import TZ.System.Annotations.Functions.ExitFunction;

/**
 * 
 * @author Terra
 * @created 04.03.2015
 * 
 * @file ExitSystem.java
 * @project TZS
 * @identifier TZ.System.Construction.Exit
 *
 */
@Construction(name = "exitsystem", system = true)
public class ExitSystem implements ExitSystemConstruction {
	
	private static ExitSystemConstruction construction;
	
	public static ExitSystemConstruction construction() {
		if (ExitSystem.construction == null) {
			ExitSystem.construction = TZSystem.construction("exitsystem");
		}
		return ExitSystem.construction;
	}
	
	public static void exiting(int code, List<Module> modules, List<Module> classes) {
		ExitSystem.construction().esExiting(code, modules, classes);
	}
	
	public static void exit(int code) {
		ExitSystem.construction().esExit(code);
	}
	
	

	/* 
	 * @see TZ.System.Construction.Exit.ExitSystemConstruction#esExiting(int)
	 */
	@Override
	public void esExiting(int code, List<Module> modules, List<Module> classes) {
		MessageSystem.out("Exiting ...");
		if (modules != null) {
			for (Module module : modules) {
				module.reflect().call(ExitFunction.class, TZSystem.EXIT_ID, module, classes);
			}
		}
	}

	/* 
	 * @see TZ.System.Construction.Exit.ExitSystemConstruction#esExit(int)
	 */
	@Override
	public void esExit(int code) {
		MessageSystem.out("Exit");
		System.exit(code);
	}

}

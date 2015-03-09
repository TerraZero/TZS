package TZ.System.Construction;

import java.util.List;

import TZ.System.Boot;
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
	
	public static void exiting(int code, List<Module> modules, List<Boot> boots) {
		ExitSystem.construction().esExiting(code, modules, boots);
	}
	
	public static void exit(int code) {
		ExitSystem.construction().esExit(code);
	}
	
	

	/* 
	 * @see TZ.System.Construction.Exit.ExitSystemConstruction#esExiting(int)
	 */
	@Override
	public void esExiting(int code, List<Module> modules, List<Boot> boots) {
		MessageSystem.out("Exiting ...");
		if (modules != null) {
			for (Module module : modules) {
				module.boot().reflect().call(ExitFunction.class, TZSystem.EXIT_ID, module, boots);
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

package TZ.System.Construction.Init;

import java.util.List;

import TZ.System.Module;
import TZ.System.TZSystem;
import TZ.System.Annotations.Construction;
import TZ.System.Annotations.Functions.InitFunction;

/**
 * 
 * @author Terra
 * @created 04.03.2015
 * 
 * @file InitSystem.java
 * @project TZS
 * @identifier TZ.System.Init
 *
 */
@Construction(name = "initsystem", system = true)
public class InitSystem implements InitSystemConstruction {
	
	private static InitSystemConstruction construction;
	
	public static InitSystemConstruction construction() {
		if (InitSystem.construction == null) {
			InitSystem.construction = TZSystem.construction("initsystem");
		}
		return InitSystem.construction;
	}
	
	public static void initing(List<Module> modules, List<Module> classes) {
		InitSystem.construction().isIniting(modules, classes);
	}
	
	public static Module initModule() {
		return InitSystem.construction().isInitModule();
	}
	
	

	protected Module module;
	
	/* 
	 * @see TZ.System.Init.InitSystemConstruction#isIniting(java.util.List, java.util.List)
	 */
	@Override
	public void isIniting(List<Module> modules, List<Module> classes) {
		for (Module module : modules) {
			this.module = module;
			module.reflect().call(InitFunction.class, TZSystem.INIT_ID, module, classes);
		}
	}

	/* 
	 * @see TZ.System.Init.InitSystemConstruction#isInitModule()
	 */
	@Override
	public Module isInitModule() {
		return this.module;
	}

}

package TZ.System.Construction;

import java.util.List;

import TZ.System.LoadState;
import TZ.System.Sys;
import TZ.System.Annotations.Construction;
import TZ.System.Module.Boot;
import TZ.System.Module.Module;

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
@Construction(name = "sysinit", type = "initsystem", system = true)
public class InitSystem implements InitSystemConstruction {
	
	private static InitSystemConstruction construction;
	
	public static InitSystemConstruction construction() {
		if (InitSystem.construction == null) {
			InitSystem.construction = Sys.construction("initsystem");
		}
		return InitSystem.construction;
	}
	
	public static void initing(LoadState state, List<Module> modules, List<Boot> boots) {
		InitSystem.construction().isIniting(state, modules, boots);
	}
	
	public static Module initModule() {
		return InitSystem.construction().isInitModule();
	}
	
	

	protected Module module;
	
	/* 
	 * @see TZ.System.Init.InitSystemConstruction#isIniting(java.util.List, java.util.List)
	 */
	@Override
	public void isIniting(LoadState state, List<Module> modules, List<Boot> boots) {
		MessageSystem.quest("Init modules ...");
		for (Module module : modules) {
			if (module.isActive()) {
				this.module = module;
				if (module.info().init().length() != 0) {
					module.boot().reflect().call(module.info().init(), state, module, boots);
				}
			}
		}
		MessageSystem.out("Complete");
	}

	/* 
	 * @see TZ.System.Init.InitSystemConstruction#isInitModule()
	 */
	@Override
	public Module isInitModule() {
		return this.module;
	}

}

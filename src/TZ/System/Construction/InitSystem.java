package TZ.System.Construction;

import java.util.List;

import TZ.System.Boot;
import TZ.System.Module;
import TZ.System.TZSystem;
import TZ.System.Annotations.Construction;

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
	
	public static void initing(List<Module> modules, List<Boot> boots) {
		InitSystem.construction().isIniting(modules, boots);
	}
	
	public static Module initModule() {
		return InitSystem.construction().isInitModule();
	}
	
	

	protected Module module;
	
	/* 
	 * @see TZ.System.Init.InitSystemConstruction#isIniting(java.util.List, java.util.List)
	 */
	@Override
	public void isIniting(List<Module> modules, List<Boot> boots) {
		for (Module module : modules) {
			if (module.isActive()) {
				this.module = module;
				if (module.info().init().length() != 0) {
					module.boot().reflect().call(module.info().init(), TZSystem.INIT_ID, module, boots);
				}
			}
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

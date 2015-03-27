package TZ.System.Construction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import TZ.System.LoadState;
import TZ.System.Sys;
import TZ.System.Annotations.Construction;
import TZ.System.Lists.Lists;
import TZ.System.Module.Boot;
import TZ.System.Module.Module;
import TZ.System.Module.Version;

/**
 * 
 * @author terrazero
 * @created Mar 4, 2015
 * 
 * @file BootSystem.java
 * @project TZS
 * @identifier TZ.System.Boot
 *
 */
@Construction(name = "sysboot", type = "bootsystem", system = true)
public class BootSystem implements BootSystemConstruction {

	private static BootSystemConstruction construction;
	
	public static BootSystemConstruction construction() {
		if (BootSystem.construction == null) {
			BootSystem.construction = Sys.construction("bootsystem");
		}
		return BootSystem.construction;
	}
	
	public static void bootModulesSort(LoadState state, List<Module> modules) {
		BootSystem.construction().bsBootModulesSort(state, modules);
	}
	
	public static List<Module> bootModulesDependencies(LoadState state, List<Module> modules) {
		return BootSystem.construction().bsBootModulesDependencies(state, modules);
	}
	
	public static void booting(LoadState state, List<Module> modules, List<Boot> boots) {
		BootSystem.construction().bsBooting(state, modules, boots);
	}
	
	public static Module bootModule() {
		return BootSystem.construction().bsBootModule();
	}
	
	public static void activeModule(LoadState state, List<Module> modules) {
		BootSystem.construction().bsActiveModule(state, modules);
	}
	
	
	
	protected Module module;

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBootModulesSort(java.util.List)
	 */
	@Override
	public void bsBootModulesSort(LoadState state, List<Module> modules) {
		MessageSystem.out("Sort modules ...");
		Lists.sortASC(modules);
		MessageSystem.out("Complete");
	}

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBootModulesDependencies(java.util.List)
	 */
	@Override
	public List<Module> bsBootModulesDependencies(LoadState state, List<Module> modules) {
		MessageSystem.out("Build dependencies ...");
		List<Module> dependencyTree = new ArrayList<Module>(modules.size());
		
		for (Module module : modules) {
			if (module.info().module()) {
				this.bsBootBuildModuleDependencies(state, dependencyTree, module);
			}
		}
		MessageSystem.out("Complete");
		return dependencyTree;
	}

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBootBuildModuleDependencies(java.util.List, TZ.System.Module)
	 */
	@Override
	public void bsBootBuildModuleDependencies(LoadState state, List<Module> dependencyTree, Module module) {
		MessageSystem.out("Check '" + module.name() + "' [" + module.version() + "] ...");
		if (!module.isActive()) {
			MessageSystem.respond("inactive", MessageType.NOTICE);
		} else if (module.isChecked()) {
			MessageSystem.respond("already checked");
		} else {
			boolean check = true;
			// WHEN module have dependencies THAN add dependencies first
			if (module.info().dependencies().length != 0) {
				for (String dependency :  module.info().dependencies()) {
					String[] depend = dependency.split("!");
					Version dependencyVersion = null;
					dependency = depend[0];
					if (depend.length == 2) {
						dependencyVersion = new Version(depend[1]);
					}
					MessageSystem.quest("\t'" + module.name() + "' dependence on '" + dependency + "' [" + dependencyVersion + "]");
					Module dm = Sys.getModule(dependency);
					
					// WHEN module is NOT available
					if (dm == null || !dm.isActive()) {
						MessageSystem.respond("not found or inactive", MessageType.WARNING);
						check = false;
						break;
					}
					
					if (dependencyVersion == null || dm.version().isCompatible(dependencyVersion)) {
						// WHEN module have already been checked THAN ignore module
						if (!dm.isAvailable()) {
							MessageSystem.respond("found", MessageType.SUCCESS);
							this.bsBootBuildModuleDependencies(state, dependencyTree, dm);
						} else {
							MessageSystem.respond("already load");
						}
					} else {
						MessageSystem.respond("Incorrect version", MessageType.WARNING);
						check = false;
						break;
					}
				}
			} else {
				MessageSystem.respond("no dependencies");
			}
			module.available(check);
			module.checked();
			dependencyTree.add(module);
		}
	}

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBooting(java.util.List)
	 */
	@Override
	public void bsBooting(LoadState state, List<Module> modules, List<Boot> boots) {
		MessageSystem.out("Booting modules ...");
		for (Module module : modules) {
			if (module.isActive()) {
				this.module = module;
				if (module.info().boot().length() != 0) {
					module.boot().reflect().call(module.info().boot(), state, module, boots);
				}
			}
		}
		MessageSystem.out("Complete");
	}

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBootModule()
	 */
	@Override
	public Module bsBootModule() {
		return this.module;
	}

	/* 
	 * @see TZ.System.Construction.BootSystemConstruction#bsActiveModule(java.util.List, TZ.System.File.InfoFile)
	 */
	@Override
	public void bsActiveModule(LoadState state, List<Module> modules) {
		MessageSystem.out("Check compatibility of modules ...");
		Version system = Sys.version;
		Map<String, String> info = state.infos("module");
		
		for (Module module : modules) {
			String active = info.get(module.id());
			
			if (active != null && active.equals("active")) {
				if (system.isCompatible(module.version())) {
					module.active(true);
				} else {
					state.data("boot:" + module.id(), "incompatible");
					MessageSystem.out("Version not compatible [" + module.id() + "]", MessageType.ERROR);
				}
			}
		}
		MessageSystem.out("Check complete");
	}
	
}

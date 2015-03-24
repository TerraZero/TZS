package TZ.System.Construction;

import java.util.ArrayList;
import java.util.List;

import TZ.System.TZSystem;
import TZ.System.Annotations.Construction;
import TZ.System.Annotations.Info;
import TZ.System.File.InfoFile;
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
@Construction(name = "bootsystem", system = true)
public class BootSystem implements BootSystemConstruction {

	private static BootSystemConstruction construction;
	
	public static BootSystemConstruction construction() {
		if (BootSystem.construction == null) {
			BootSystem.construction = TZSystem.construction("bootsystem");
		}
		return BootSystem.construction;
	}
	
	public static List<Module> bootModules(List<Boot> boots) {
		return BootSystem.construction().bsBootModules(boots);
	}
	
	public static void bootModulesSort(List<Module> modules) {
		BootSystem.construction().bsBootModulesSort(modules);
	}
	
	public static List<Module> bootModulesDependencies(List<Module> modules) {
		return BootSystem.construction().bsBootModulesDependencies(modules);
	}
	
	public static void booting(List<Module> modules, List<Boot> boots) {
		BootSystem.construction().bsBooting(modules, boots);
	}
	
	public static Module bootModule() {
		return BootSystem.construction().bsBootModule();
	}
	
	public static void activeModule(List<Module> modules, InfoFile info) {
		BootSystem.construction().bsActiveModule(modules, info);
	}
	
	
	
	protected Module module;

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBootModules(java.util.List)
	 */
	@Override
	public List<Module> bsBootModules(List<Boot> boots) {
		MessageSystem.out("Build modules ...");
		List<Module> modules = new ArrayList<Module>(128);
		
		MessageSystem.out("Load modules ...");
		for (Boot boot : boots) {
			if (boot.reflect().hasAnnotation(Info.class)) {
				modules.add(new Module(boot));
			}
		}
		return modules;
	}

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBootModulesSort(java.util.List)
	 */
	@Override
	public void bsBootModulesSort(List<Module> modules) {
		MessageSystem.out("Sort modules ...");
		Lists.sortASC(modules);
	}

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBootModulesDependencies(java.util.List)
	 */
	@Override
	public List<Module> bsBootModulesDependencies(List<Module> modules) {
		MessageSystem.out("Build dependencies ...");
		List<Module> dependencyTree = new ArrayList<Module>(modules.size());
		
		for (Module module : modules) {
			if (module.info().module()) {
				this.bsBootBuildModuleDependencies(dependencyTree, module);
			}
		}
		MessageSystem.out("Complete ...");
		return dependencyTree;
	}

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBootBuildModuleDependencies(java.util.List, TZ.System.Module)
	 */
	@Override
	public void bsBootBuildModuleDependencies(List<Module> dependencyTree, Module module) {
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
					Module dm = TZSystem.getModule(dependency);
					
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
							this.bsBootBuildModuleDependencies(dependencyTree, dm);
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
	public void bsBooting(List<Module> modules, List<Boot> boots) {
		for (Module module : modules) {
			if (module.isActive()) {
				this.module = module;
				if (module.info().boot().length() != 0) {
					module.boot().reflect().call(module.info().boot(), TZSystem.BOOT_ID, module, boots);
				}
			}
		}
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
	public void bsActiveModule(List<Module> modules, InfoFile info) {
		Version system = TZSystem.version;
		
		for (Module module : modules) {
			String active = info.info(module.id());
			
			if (active != null && active.equals("active")) {
				if (system.isCompatible(module.version())) {
					module.active(true);
				} else {
					MessageSystem.out("Version not compatible [" + module.id() + "]", MessageType.ERROR);
				}
			}
		}
	}
	
}

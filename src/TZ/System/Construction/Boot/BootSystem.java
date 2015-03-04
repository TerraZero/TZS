package TZ.System.Construction.Boot;

import java.util.ArrayList;
import java.util.List;

import TZ.System.Module;
import TZ.System.TZMessage;
import TZ.System.TZSystem;
import TZ.System.Annotations.Construction;
import TZ.System.Annotations.Functions.BootFunction;
import TZ.System.Lists.Lists;

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
	
	public static List<Module> bootModules(List<Module> classes) {
		return BootSystem.construction().bsBootModules(classes);
	}
	
	public static void bootModulesSort(List<Module> modules) {
		BootSystem.construction().bsBootModulesSort(modules);
	}
	
	public static List<Module> bootModulesDependencies(List<Module> modules) {
		return BootSystem.construction().bsBootModulesDependencies(modules);
	}
	
	public static void booting(List<Module> modules, List<Module> classes) {
		BootSystem.construction().bsBooting(modules, classes);
	}
	
	public static Module bootModule() {
		return BootSystem.construction().bsBootModule();
	}
	
	
	
	protected Module module;

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBootModules(java.util.List)
	 */
	@Override
	public List<Module> bsBootModules(List<Module> classes) {
		TZMessage.out("Build modules ...");
		List<Module> modules = new ArrayList<Module>(128);
		
		TZMessage.out("Load modules ...");
		for (Module classe : classes) {
			if (classe.isModule()) {
				modules.add(classe);
			}
		}
		return modules;
	}

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBootModulesSort(java.util.List)
	 */
	@Override
	public void bsBootModulesSort(List<Module> modules) {
		TZMessage.out("Sort modules ...");
		Lists.sortASC(modules);
	}

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBootModulesDependencies(java.util.List)
	 */
	@Override
	public List<Module> bsBootModulesDependencies(List<Module> modules) {
		TZMessage.out("Build dependencies ...");
		List<Module> dependencyTree = new ArrayList<Module>(modules.size());
		
		for (Module module : modules) {
			this.bsBootBuildModuleDependencies(dependencyTree, module);
		}
		TZMessage.out("Complete ...");
		return dependencyTree;
	}

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBootBuildModuleDependencies(java.util.List, TZ.System.Module)
	 */
	@Override
	public void bsBootBuildModuleDependencies(List<Module> dependencyTree, Module module) {
		TZMessage.out("Check '" + module.module() + "' ...");
		if (module.isChecked()) {
			TZMessage.respond("already checked");
		} else {
			boolean check = true;
			// WHEN module have dependencies THAN add dependencies first
			if (module.info().dependencies().length != 0) {
				for (String dependency :  module.info().dependencies()) {
					TZMessage.quest("\t'" + module.module() + "' dependence on '" + dependency + "'");
					Module dm = TZSystem.getModule(dependency);
					// WHEN module is NOT available
					if (dm == null) {
						TZMessage.respond("not found");
						check = false;
						break;
					// WHEN module have already been checked THAN ignore module
					} else if (!dm.isDependencies()) {
						TZMessage.respond("found");
						this.bsBootBuildModuleDependencies(dependencyTree, dm);
					} else {
						TZMessage.respond("already load");
					}
				}
			} else {
				TZMessage.respond("no dependencies");
			}
			module.dependencies(check);
			module.checked(true);
			dependencyTree.add(module);
		}
	}

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBooting(java.util.List)
	 */
	@Override
	public void bsBooting(List<Module> modules, List<Module> classes) {
		for (Module module : modules) {
			this.module = module;
			module.reflect().call(BootFunction.class, TZSystem.BOOT_ID, module, classes);
		}
	}

	/* 
	 * @see TZ.System.Boot.BootSystemConstruction#bsBootModule()
	 */
	@Override
	public Module bsBootModule() {
		return this.module;
	}
	
}

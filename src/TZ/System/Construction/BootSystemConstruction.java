package TZ.System.Construction;

import java.util.List;

import TZ.System.Module;

/**
 * 
 * @author terrazero
 * @created Mar 4, 2015
 * 
 * @file BootSystemConstruction.java
 * @project TZS
 * @identifier TZ.System.Boot
 *
 */
public interface BootSystemConstruction {

	public List<Module> bsBootModules(List<Module> classes);
	
	public void bsBootModulesSort(List<Module> modules);
	
	public List<Module> bsBootModulesDependencies(List<Module> modules);
	
	public void bsBootBuildModuleDependencies(List<Module> dependencyTree, Module module);
	
	public void bsBooting(List<Module> modules, List<Module> classes);
	
	public Module bsBootModule();
	
}

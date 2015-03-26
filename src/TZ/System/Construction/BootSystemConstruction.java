package TZ.System.Construction;

import java.util.List;

import TZ.System.LoadState;
import TZ.System.Module.Boot;
import TZ.System.Module.Module;

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
	
	public List<Module> bsBootModules(LoadState state, List<Boot> boots);
	
	public void bsActiveModule(LoadState state, List<Module> modules);
	
	public void bsBootModulesSort(LoadState state, List<Module> modules);
	
	public List<Module> bsBootModulesDependencies(LoadState state, List<Module> modules);
	
	public void bsBootBuildModuleDependencies(LoadState state, List<Module> dependencyTree, Module module);
	
	public void bsBooting(LoadState state, List<Module> modules, List<Boot> boots);
	
	public Module bsBootModule();
	
}

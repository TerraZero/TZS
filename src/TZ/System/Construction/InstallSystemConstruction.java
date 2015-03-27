package TZ.System.Construction;

import java.util.List;

import TZ.System.LoadState;
import TZ.System.File.CFid;
import TZ.System.Module.Boot;
import TZ.System.Module.Module;

/**
 * 
 * @author Terra
 * @created 04.03.2015
 * 
 * @file InstallSystemConstruction.java
 * @project TZS
 * @identifier TZ.System.Construction.Install
 *
 */
public interface InstallSystemConstruction {
	
	public void isInstall(LoadState state, List<Module> modules, List<Boot> boots);

	public void isInstallProfile(LoadState state, List<Module> modules, List<Boot> boots);
	
	public void isInstallSystem(LoadState state, List<Module> modules, List<Boot> boots);
	
	public void isInstallComplete(LoadState state, List<Module> modules, List<Boot> boots);
	
	public CFid isBase();
	
}

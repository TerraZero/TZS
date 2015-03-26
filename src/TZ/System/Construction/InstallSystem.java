package TZ.System.Construction;

import java.util.List;

import javax.swing.JOptionPane;

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
 * @file InstallSystem.java
 * @project TZS
 * @identifier TZ.System.Construction.Install
 *
 */
@Construction(name = "sysinstall", type = "installsystem", system = true)
public class InstallSystem implements InstallSystemConstruction {
	
	private static InstallSystemConstruction construction;
	
	public static InstallSystemConstruction construction() {
		if (InstallSystem.construction == null) {
			InstallSystem.construction = Sys.construction("installsystem");
		}
		return InstallSystem.construction;
	}
	
	public static void install(LoadState state, List<Module> modules, List<Boot> boots) {
		InstallSystem.construction().isInstall(state, modules, boots);
	}
	
	public static void installProfile(LoadState state, List<Module> modules, List<Boot> boots) {
		InstallSystem.construction().isInstallProfile(state, modules, boots);
	}
	
	public static void installSystem(LoadState state, List<Module> modules, List<Boot> boots) {
		InstallSystem.construction().isInstallSystem(state, modules, boots);
	}
	
	public static void installComplete(LoadState state, List<Module> modules, List<Boot> boots) {
		InstallSystem.construction().isInstallComplete(state, modules, boots);
	}
	

	
	public void isInstall(LoadState state, List<Module> modules, List<Boot> boots) {
		MessageSystem.out("Install system ...");
		// invoke installs
		for (Module module : modules) {
			if (module.info().install().length() != 0) {
				module.boot().reflect().call(module.info().install(), state, module, boots);
			}
		}
		MessageSystem.out("Complete");
	}

	/* 
	 * @see TZ.System.Construction.InstallSystemConstruction#isInstallFile(TZ.System.File.InfoFile)
	 */
	@Override
	public void isInstallProfile(LoadState state, List<Module> modules, List<Boot> boots) {
		MessageSystem.out("Install profile ...");
		// invoke install profiles
		for (Module module : modules) {
			if (module.info().installProfile().length() != 0) {
				module.boot().reflect().call(module.info().installProfile(), state, module, boots);
			}
		}
		MessageSystem.out("Complete");
	}
	
	/* 
	 * @see TZ.System.Construction.InstallSystemConstruction#isInstallSystem(TZ.System.LoadState, java.util.List, java.util.List)
	 */
	@Override
	public void isInstallSystem(LoadState state, List<Module> modules, List<Boot> boots) {
		MessageSystem.out("Install system ...");
		// TODO system install
		MessageSystem.out("Complete");
	}

	/* 
	 * @see TZ.System.Construction.InstallSystemConstruction#isInstallComplete(TZ.System.LoadState, java.util.List, java.util.List)
	 */
	@Override
	public void isInstallComplete(LoadState state, List<Module> modules, List<Boot> boots) {
		JOptionPane.showMessageDialog(null, "Complete install '" + state.data("program") + "'!");
		Sys.exit(0);
	}

}

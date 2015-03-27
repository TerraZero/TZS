package TZ.System.Construction;

import java.util.List;

import javax.swing.JOptionPane;

import TZ.System.LoadState;
import TZ.System.Sys;
import TZ.System.Annotations.Construction;
import TZ.System.Annotations.Interfaces.InstallProfile;
import TZ.System.File.CFid;
import TZ.System.File.InfoFile;
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
	
	public static CFid base() {
		return InstallSystem.construction().isBase();
	}
	
	
	
	protected String base;
	
	public void isInstall(LoadState state, List<Module> modules, List<Boot> boots) {
		MessageSystem.out("Install system ...");
		// invoke install profiles
		for (Module module : modules) {
			if (module.info().installInfo().length() != 0) {
				module.boot().reflect().call(module.info().installInfo(), state, module, boots);
			}
		}
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
		InstallProfile profile = null;
		for (Boot boot : boots) {
			if (boot.reflect().implement(InstallProfile.class)) {
				profile = boot.reflect().instantiate().getReflect();
				break;
			}
		}
		if (profile == null) {
			MessageSystem.out("No profile found!");
		} else {
			MessageSystem.out("Load profile '" + profile.name() + "' ...");
			MessageSystem.out("\tBuild profile base ...");
			profile.profileBase(state, InstallSystem.base());
			
			MessageSystem.out("\tBuild profile construction ...");
			profile.profileConstruction(state.infos("construction"));
			
			MessageSystem.out("\tBuild profile module ...");
			profile.profileBase(state, InstallSystem.base());
			
			MessageSystem.out("\tBuild profile mechnic ...");
			profile.profileBase(state, InstallSystem.base());
		}
		
		MessageSystem.out("Complete");
	}
	
	/* 
	 * @see TZ.System.Construction.InstallSystemConstruction#isInstallSystem(TZ.System.LoadState, java.util.List, java.util.List)
	 */
	@Override
	public void isInstallSystem(LoadState state, List<Module> modules, List<Boot> boots) {
		MessageSystem.out("Install system ...");
		this.base = state.data("path.default") + state.data("program");
		state.info().put("path.base", this.base);
		state.info().put("path.default", state.data("path.default"));
		state.info().put("path.user", state.data("path.user"));
		CFid fid = new CFid(this.base);
		InfoFile file = new InfoFile(fid.cDir("user", "defaults").cFile("construction.info"));
		Boot.forAnnotations(boots, Construction.class, (wrapper) -> {
			if (wrapper.info().system()) {
				file.info(wrapper.info().type(), wrapper.info().name());
			}
		});
		file.save();
		// TODO system install
		MessageSystem.out("Complete");
	}

	/* 
	 * @see TZ.System.Construction.InstallSystemConstruction#isInstallComplete(TZ.System.LoadState, java.util.List, java.util.List)
	 */
	@Override
	public void isInstallComplete(LoadState state, List<Module> modules, List<Boot> boots) {
		CFid fid = this.isBase().cFile(state.data("program") + ".info");
		new InfoFile(fid).infos(state.info()).save();
		JOptionPane.showMessageDialog(null, "Complete install '" + state.data("program") + "'!");
		Sys.exit(0);
	}

	/* 
	 * @see TZ.System.Construction.InstallSystemConstruction#isBase()
	 */
	@Override
	public CFid isBase() {
		return new CFid(this.base);
	}

}

package TZ.System.Construction;

import java.io.File;
import java.util.List;

import TZ.System.Module;
import TZ.System.TZSystem;
import TZ.System.Annotations.Construction;
import TZ.System.File.Fid;
import TZ.System.File.InfoFile;

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
@Construction(name = "installsystem", system = true)
public class InstallSystem implements InstallSystemConstruction {
	
	private static InstallSystemConstruction construction;
	
	public static InstallSystemConstruction construction() {
		if (InstallSystem.construction == null) {
			InstallSystem.construction = TZSystem.construction("installsystem");
		}
		return InstallSystem.construction;
	}
	
	public static String[] installFiles(String program, String root) {
		return InstallSystem.construction().isInstallFiles(program, root);
	}
	
	public static Fid installFid(String[] files) {
		return InstallSystem.construction().isInstallFid(files);
	}
	
	public static void installProfile(InfoFile info, List<Module> module) {
		InstallSystem.construction().isInstallProfile(info, module);
		InstallSystem.construction().isInstall(info, module);
	}
	
	public static InfoFile installing(Fid install) {
		return InstallSystem.construction().isInstalling(install);
	}
	
	

	/* 
	 * @see TZ.System.Construction.Install.InstallSystemConstruction#isInstallFiles(java.lang.String, java.lang.String)
	 */
	@Override
	public String[] isInstallFiles(String program, String root) {
		String[] files = {
			root + "/" + program + ".info.txt",
			root + "/" + program + "/" + program + ".info.txt",
			System.getProperty("user.home") + "/" + program + ".info.txt",
			root + "/tzs.info.txt",
			System.getProperty("user.home") + "/tzs.info.txt",
		};
		return files;
	}

	/* 
	 * @see TZ.System.Construction.Install.InstallSystemConstruction#isInstallFid(java.lang.String[])
	 */
	@Override
	public Fid isInstallFid(String[] files) {
		MessageSystem.out("Search info file:");
		for (int i = 0; i < files.length; i++) {
			Fid install = new Fid(files[i]);
			MessageSystem.quest(install + " ...");
			if (install.isExist()) return install;
			MessageSystem.respond("not found", MessageType.NOTICE);
		}
		return null;
	}

	/* 
	 * @see TZ.System.Construction.Install.InstallSystemConstruction#isInstalling(TZ.System.File.Fid)
	 */
	@Override
	public InfoFile isInstalling(Fid install) {
		if (install == null) {
			MessageSystem.out("Installing...");
			install = new Fid(new File("").getAbsolutePath() + "/" + TZSystem.machineProgram());
			MessageSystem.quest("Create base dir: " + install);
			if (install.createDirs()) {
				MessageSystem.respond(MessageType.SUCCESS);
			} else {
				MessageSystem.respond("Not created", MessageType.ERROR);
				this.isInstallAbort();
			}
			install = install.cd(TZSystem.machineProgram() + ".info.txt");
			MessageSystem.quest("Create info file: " + install);
			if (install.create()) {
				MessageSystem.respond(MessageType.SUCCESS);
			} else {
				MessageSystem.respond("Not created", MessageType.ERROR);
				this.isInstallAbort();
			}
			MessageSystem.out("Completed...");
		} else {
			MessageSystem.respond("found", MessageType.OK);
			MessageSystem.out("Info file: " + install);
		}
		return new InfoFile(install);
	}
	
	public void isInstall(InfoFile info, List<Module> modules) {
		// invoke installs
		for (Module module : modules) {
			if (module.info().install().length() != 0) {
				module.boot().reflect().call(module.info().install(), info, module);
			}
		}
	}

	/* 
	 * @see TZ.System.Construction.Install.InstallSystemConstruction#isInstallAbort()
	 */
	@Override
	public void isInstallAbort() {
		MessageSystem.out("Install abort!");
		TZSystem.exit(1);
	}

	/* 
	 * @see TZ.System.Construction.InstallSystemConstruction#isInstallFile(TZ.System.File.InfoFile)
	 */
	@Override
	public void isInstallProfile(InfoFile info, List<Module> modules) {
		info.info("program", TZSystem.program());
		info.info("base-path", info.fid().file().getParentFile().getAbsolutePath());
		
		// invoke install profiles
		for (Module module : modules) {
			if (module.info().installProfile().length() != 0) {
				module.boot().reflect().call(module.info().installProfile(), info, module);
			}
		}
	}

}

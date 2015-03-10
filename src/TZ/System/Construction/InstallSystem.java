package TZ.System.Construction;

import java.io.File;

import TZ.System.TZSystem;
import TZ.System.Annotations.Construction;
import TZ.System.File.Fid;

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
	
	public static void installing(Fid install) {
		InstallSystem.construction().isInstalling(install);
	}
	
	

	/* 
	 * @see TZ.System.Construction.Install.InstallSystemConstruction#isInstallFiles(java.lang.String, java.lang.String)
	 */
	@Override
	public String[] isInstallFiles(String program, String root) {
		String[] files = {
			root + "/" + program,
			System.getProperty("user.home") + "/" + program,
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
	public void isInstalling(Fid install) {
		if (install == null) {
			MessageSystem.out("Installing...");
			install = new Fid(new File("").getAbsolutePath() + "/" + TZSystem.machineProgram() + ".info.txt");
			MessageSystem.quest("Create info file: " + install);
			if (install.create()) {
				MessageSystem.respond(MessageType.SUCCESS);
			} else {
				MessageSystem.respond("Not created", MessageType.ERROR);
				this.isInstallAbort();
			}
			//InfoFile info = new InfoFile(install);
			// TODO 
			MessageSystem.out("Completed...");
		} else {
			MessageSystem.respond("found", MessageType.OK);
			MessageSystem.out("Info file: " + install);
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

}

package TZ.System.Construction;

import TZ.System.File.Fid;

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

	public String[] isInstallFiles(String program, String root);
	
	public Fid isInstallFid(String[] files);
	
	public void isInstalling(Fid install);
	
	public void isInstallAbort();
	
}

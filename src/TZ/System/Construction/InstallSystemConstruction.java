package TZ.System.Construction;

import java.util.List;

import TZ.System.Boot;
import TZ.System.File.Fid;
import TZ.System.File.InfoFile;

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
	
	public InfoFile isInstalling(Fid install);
	
	public void isInstallProfile(InfoFile info, List<Boot> boots);
	
	public void isInstallAbort();
	
}

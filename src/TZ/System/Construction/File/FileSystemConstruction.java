package TZ.System.Construction.File;

import TZ.System.File.Fid;

/**
 * 
 * @author terrazero
 * @created Mar 3, 2015
 * 
 * @file FileSystemConstruction.java
 * @project TZS
 * @identifier TZ.System.File
 *
 */
public interface FileSystemConstruction {

	public Fid fsGet(String context, String name, String dir);
	
	public String fsGetContextToken(String context);
	
	public String fsGetDirToken(String dir);
	
	public void fsAddSystemToken(boolean wrapper, String replace, String... tokens);
	
	public void fsAddSystemToken(String replace, String... tokens);
	
	public void fsAddContextToken(boolean wrapper, String replace, String... tokens);
	
	public void fsAddContextToken(String replace, String... tokens);
	
}

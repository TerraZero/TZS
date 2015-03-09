package TZ.System;

import TZ.System.Annotations.Construction;
import TZ.System.Annotations.Info;
import TZ.System.Construction.FileSystemConstruction;
import TZ.System.File.Fid;

/**
 * 
 * @author terrazero
 * @created Mar 9, 2015
 * 
 * @file TestFileSystem.java
 * @project TZS
 * @identifier TZ.System
 *
 */
//@Construction(name = "filesystem")
public class TestFileSystem implements FileSystemConstruction {

	public static void construction() {
		System.out.println("test");
	}
	
	/* 
	 * @see TZ.System.Construction.FileSystemConstruction#fsGet(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public Fid fsGet(String context, String name, String dir) {
		System.out.println("test-system");
		return null;
	}

	/* 
	 * @see TZ.System.Construction.FileSystemConstruction#fsGetContextToken(java.lang.String)
	 */
	@Override
	public String fsGetContextToken(String context) {
		return null;
	}

	/* 
	 * @see TZ.System.Construction.FileSystemConstruction#fsGetDirToken(java.lang.String)
	 */
	@Override
	public String fsGetDirToken(String dir) {
		return null;
	}

	/* 
	 * @see TZ.System.Construction.FileSystemConstruction#fsAddSystemToken(boolean, java.lang.String, java.lang.String[])
	 */
	@Override
	public void fsAddSystemToken(boolean wrapper, String replace, String... tokens) {
	}

	/* 
	 * @see TZ.System.Construction.FileSystemConstruction#fsAddSystemToken(java.lang.String, java.lang.String[])
	 */
	@Override
	public void fsAddSystemToken(String replace, String... tokens) {
	}

	/* 
	 * @see TZ.System.Construction.FileSystemConstruction#fsAddContextToken(boolean, java.lang.String, java.lang.String[])
	 */
	@Override
	public void fsAddContextToken(boolean wrapper, String replace, String... tokens) {
	}

	/* 
	 * @see TZ.System.Construction.FileSystemConstruction#fsAddContextToken(java.lang.String, java.lang.String[])
	 */
	@Override
	public void fsAddContextToken(String replace, String... tokens) {
	}

}

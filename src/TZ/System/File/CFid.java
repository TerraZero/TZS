package TZ.System.File;

import java.io.File;
import java.nio.file.Path;

/**
 * 
 * @author terrazero
 * @created Mar 19, 2015
 * 
 * @file CFid.java
 * @project TZS
 * @identifier TZ.System.File
 *
 */
public class CFid extends Fid {

	public CFid(String file) {
		super(file);
	}
	
	public CFid(String dir, String file) {
		super(dir, file);
	}
	
	public CFid(File file) {
		this(file.toPath());
	}
	
	public CFid(Path path) {
		super(path);
	}
	
	public CFid(Path path, String context, String name, String dir) {
		super(path, context, name, dir);
	}
	
	public CFid(Fid root, String... additionals) {
		super(root, additionals);
	}
	
	public CFid cFile(String file) {
		CFid newFile = new CFid(this, file);
		newFile.create();
		return newFile;
	}
	
	public CFid cDir(String... dirs) {
		CFid newFid = new CFid(this, dirs);
		newFid.createDirs();
		return newFid;
	}

}

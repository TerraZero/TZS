package TZ.System.File;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 * @author terrazero
 * @created Feb 23, 2015
 * 
 * @file Fid.java
 * @project TZS
 * @identifier TZ.System.File
 *
 */
public class Fid {

	protected Path path;
	
	public Fid(String file) {
		this(new File(file));
	}
	
	public Fid(String dir, String file) {
		this(Paths.get(dir, file));
	}
	
	public Fid(File file) {
		this(file.toPath());
	}
	
	public Fid(Path path) {
		this.path = path;
	}
	
	public Path getPath() {
		return this.path;
	}
	
	public boolean isExist() {
		return Files.exists(this.path);
	}
	
}

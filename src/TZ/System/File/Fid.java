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
	protected File file;
	
	protected String name;
	protected String context;
	protected String dir;
	
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
		this.file = path.toFile();
	}
	
	// for filesystem
	public Fid(Path path, String context, String name, String dir) {
		this.path = path;
		this.file = path.toFile();
		this.context = context;
		this.name = name;
		this.dir = dir;
	}
	
	public Path path() {
		return this.path;
	}
	
	public File file() {
		return this.file;
	}
	
	public String name() {
		return this.name;
	}
	
	public String context() {
		return this.context;
	}
	
	public String dir() {
		return this.dir;
	}
	
	public boolean isExist() {
		return Files.exists(this.path);
	}
	
}

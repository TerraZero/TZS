package TZ.System.File;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import TZ.System.TZSystem;
import TZ.System.Annotations.Info;
import TZ.System.Annotations.Functions.BootFunction;
import TZ.System.Annotations.Functions.InitFunction;
import TZ.System.Reflect.Args;
import TZ.System.Reflect.Boot.Module;

/**
 * 
 * @author terrazero
 * @created Feb 23, 2015
 * 
 * @file FileSystem.java
 * @project TZS
 * @identifier TZ.System.File
 *
 */
@Info
public class FileSystem {
	
	// TODO
	// .replace("~", System.getProperty("user.home"));
	// as alter function
	
	private static FileSystem filesystem;
	
	@BootFunction
	public static void bootFileSystem(String id, Module module, List<Module> classes) {
		FileSystem.filesystem = new FileSystem();
	}
	
	@InitFunction
	public static void initFileSystem(String id, Module module, List<Module> classes) {
		FileSystem.addSystemPath("~");
		// TODO init system paths
	}
	
	public static Fid get(String name, String... dirs) {
		return FileSystem.filesystem.fsGet(name, dirs);
	}
	
	public static void addSystemPath(String path) {
		FileSystem.filesystem.fsAddSystemPath(path);
	}
	
	protected List<String> systemPaths;
	
	public FileSystem() {
		this.systemPaths = new ArrayList<String>(16);
	}

	public Fid fsGet(String name, String... dirs) {
		Fid file = new Fid(this.fsGetPath(name));
		if (file.isExist()) return file;
		if (dirs.length == 0) {
			for (String sp : this.systemPaths) {
				Fid fid = new Fid(this.fsGetPath(sp, name));
				if (fid.isExist()) return fid;
			}
		} else {
			for (String dir : dirs) {
				Fid fid = new Fid(this.fsGetPath(dir, name));
				if (fid.isExist()) return fid;
			}
		}
		return null;
	}
	
	public void fsAddSystemPath(String path) {
		this.systemPaths.add(path);
	}
	
	public Path fsGetPath(String first, String... more) {
		first = this.fsReplace(first);
		for (int i = 0; i < more.length; i++) more[i] = this.fsReplace(more[i]);
		return Paths.get(first, more);
	}
	
	public String fsReplace(String str) {
		Args args = new Args(str);
		TZSystem.invoke("token-path", args);
		return args.dataString;
	}
	
	public static void main(String[] args) {
		TZSystem.execute();
		Fid fid = FileSystem.get("test.csv", "~");
		System.out.println(fid.getPath());
	}
	
}

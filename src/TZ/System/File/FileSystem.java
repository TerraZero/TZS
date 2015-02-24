package TZ.System.File;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import TZ.System.TZSystem;
import TZ.System.Annotations.Info;
import TZ.System.Annotations.Functions.BootFunction;
import TZ.System.Base.Tokens;
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
	
	public static final String DEFAULT_SYSTEM_TOKEN = "[default]";
	public static final String DEFAULT_CONTEXT_TOKEN = "[default]";
	
	public static void main(String[] args) {
		TZSystem.execute("TestProgram");
		Fid fid = FileSystem.get("[default]", "test");
		System.out.println(fid.file());
		System.out.println(fid.context());
	}
	
	private static FileSystem filesystem;
	
	@BootFunction
	public static void bootFileSystem(String id, Module module, List<Module> classes) {
		FileSystem.filesystem = new FileSystem();
	}
	
	public static Fid get(String context, String name) {
		return FileSystem.filesystem.fsGet(context, name, null);
	}
	
	public static Fid get(String context, String name, String dir) {
		return FileSystem.filesystem.fsGet(context, name, dir);
	}
	
	protected Tokens systemtokens;
	protected Tokens contexttokens;
	
	public FileSystem() {
		this.systemtokens = new Tokens();
		this.contexttokens = new Tokens();
		
		this.fsAddSystemToken(false, new File(TZSystem.program()).getAbsolutePath(), FileSystem.DEFAULT_SYSTEM_TOKEN);
		this.fsAddSystemToken(System.getProperty("user.home"), "user");
		this.fsAddSystemToken(false, System.getProperty("user.home"), "~");
		
		this.fsAddContextToken(false, "default", FileSystem.DEFAULT_CONTEXT_TOKEN);
	}
	
	public Fid fsGet(String context, String name, String dir) {
		return new Fid(Paths.get(this.fsGetDirToken(dir), this.fsGetContextToken(context), name), context, name, dir);
	}
	
	public String fsGetContextToken(String context) {
		if (context == null) {
			return this.contexttokens.token(FileSystem.DEFAULT_CONTEXT_TOKEN);
		} else {
			return this.contexttokens.token(context);
		}
	}
	
	public String fsGetDirToken(String dir) {
		if (dir == null) {
			return this.systemtokens.token(FileSystem.DEFAULT_SYSTEM_TOKEN);
		} else {
			return this.systemtokens.token(dir);
		}
	}
	
	public void fsAddSystemToken(boolean wrapper, String replace, String... tokens) {
		this.systemtokens.addToken(wrapper, replace, tokens);
	}
	
	public void fsAddSystemToken(String replace, String... tokens) {
		this.systemtokens.addToken(replace, tokens);
	}
	
	public void fsAddContextToken(boolean wrapper, String replace, String... tokens) {
		this.contexttokens.addToken(wrapper, replace, tokens);
	}
	
	public void fsAddContextToken(String replace, String... tokens) {
		this.contexttokens.addToken(replace, tokens);
	}
	
}

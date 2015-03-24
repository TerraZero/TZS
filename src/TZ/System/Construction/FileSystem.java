package TZ.System.Construction;

import java.nio.file.Paths;

import TZ.System.TZSystem;
import TZ.System.Annotations.Construction;
import TZ.System.Base.Tokens;
import TZ.System.File.Fid;
import TZ.System.File.InfoFile;
import TZ.System.Module.Module;

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
@Construction(name = "filesystem", system = true)
public class FileSystem implements FileSystemConstruction {
	
	public static final String DEFAULT_SYSTEM_TOKEN = "[default]";
	public static final String DEFAULT_CONTEXT_TOKEN = "[default]";
	
	public static void main(String[] args) {
		TZSystem.execute("TestProgram");
		Fid fid = FileSystem.get("modules", "test");
		System.out.println(fid.file());
	}
	
	private static FileSystemConstruction construction;
	
	public static FileSystemConstruction construction() {
		if (FileSystem.construction == null) {
			FileSystem.construction = TZSystem.construction("filesystem");
		}
		return FileSystem.construction;
	}
	
	public static void init(InfoFile info) {
		FileSystem.construction().fsInit(info);
	}
	
	public static Fid get(String name) {
		return FileSystem.construction().fsGet(null, name, null);
	}
	
	public static Fid get(String context, String name) {
		return FileSystem.construction().fsGet(context, name, null);
	}
	
	public static Fid get(String context, String name, String dir) {
		return FileSystem.construction().fsGet(context, name, dir);
	}
	
	public static Fid get(Module module, String name) {
		return FileSystem.construction().fsGet("modules/" + module.id(), name, null);
	}
	
	public static InfoFile getInfo(String context, String file) {
		return FileSystem.construction().fsGetInfo(context, file);
	}
	
	public static Fid getExist(String context, String name) {
		return FileSystem.get(context, name, null);
	}
	
	public static Fid getExist(String context, String name, String dir) {
		Fid fid = FileSystem.get(context, name, dir);
		if (fid.isExist()) return fid;
		return null;
	}
	
	protected Tokens systemtokens;
	protected Tokens contexttokens;
	protected String base;
	
	public FileSystem() {
		this.systemtokens = new Tokens();
		this.contexttokens = new Tokens();
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

	/* 
	 * @see TZ.System.Construction.FileSystemConstruction#fsInit(TZ.System.File.InfoFile)
	 */
	@Override
	public void fsInit(InfoFile info) {
		this.base = info.info("base-path");
				
		// system tokens
		this.fsAddSystemToken(false, this.base, FileSystem.DEFAULT_SYSTEM_TOKEN);
		this.fsAddSystemToken(System.getProperty("user.home"), "user");
		this.fsAddSystemToken(false, System.getProperty("user.home"), "~");
		
		// context tokens
		this.fsAddContextToken(false, "default", FileSystem.DEFAULT_CONTEXT_TOKEN);
	}

	/* 
	 * @see TZ.System.Construction.FileSystemConstruction#fsGetInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public InfoFile fsGetInfo(String context, String file) {
		return new InfoFile(this.fsGet(context + "/defaults", file, null), this.fsGet(context, file, null));
	}
	
}

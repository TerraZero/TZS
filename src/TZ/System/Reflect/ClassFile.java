package TZ.System.Reflect;

/**
 * 
 * @author terrazero
 * @created Feb 12, 2015
 * 
 * @file ClassFile.java
 * @project G7C
 * @identifier TZ.Reflect
 *
 */
public class ClassFile {
	
	protected String id;
	protected String name;
	protected ClassFileType type;
	
	public ClassFile(String path, String name, ClassFileType type) {
		this.id = path;
		if (type == ClassFileType.CLASS) {
			this.name = name.substring(0, name.length() - 6);
		} else {
			this.name = name;
		}
		this.type = type;
	}
	
	public String id() {
		return this.id;
	}
	
	public String fullname() {
		return this.id + this.name(true);
	}
	
	public String name() {
		return this.name(false);
	}
	
	public String name(boolean ending) {
		return this.name + (ending && this.isClass() ? ".class" : "");
	}
	
	public ClassFileType type() {
		return this.type;
	}
	
	public boolean isPackage() {
		return this.type == ClassFileType.PACKAGE;
	}
	
	public boolean isClass() {
		return this.type == ClassFileType.CLASS;
	}
	
	@Override
	public String toString() {
		return this.name(true) + " [" + this.type + "] [" + this.id + "]";
	}
	
}

enum ClassFileType {
	
	PACKAGE,
	CLASS,
	
}

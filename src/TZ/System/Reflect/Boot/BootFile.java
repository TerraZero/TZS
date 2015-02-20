package TZ.System.Reflect.Boot;

import java.util.HashMap;
import java.util.Map;

import TZ.System.Reflect.Reflect;

/**
 * 
 * @author Terra
 * @created 13.02.2015
 * 
 * @file BootFile.java
 * @project G7C
 * @identifier TZ.Reflect.Boot
 *
 */
@Boot(weight=1)
public class BootFile {

	protected String name;
	protected String path;
	protected boolean type;
	protected Map<String, BootFile> contains;
	protected BootFile parent;
	protected int weight;
	protected Reflect reflect;
	
	public BootFile() {
		this.contains = new HashMap<String, BootFile>();
	}
	
	public BootFile(String name, boolean type, String path) {
		this.name = name;
		this.type = type;
		if (type) {
			this.name = this.name.substring(0, this.name.length() - 6);
		}
		this.path = path;
		this.contains = new HashMap<String, BootFile>();
	}
	
	public void add(String name, BootFile file) {
		this.contains.put(name, file);
		file.parent = this;
	}
	
	public String name() {
		return this.name;
	}
	
	public String fullname() {
		if (this.isClass()) {
			return this.name + ".class";
		} else {
			return this.name;
		}
	}
	
	public String file() {
		return this.path + "/" + this.fullname();
	}
	
	public String id() {
		String file = this.path + "/" + this.name;
		return file.replace('/',  '.');
	}
	
	public String path() {
		return this.path;
	}
	
	public Map<String, BootFile> contains() {
		return this.contains;
	}
	
	public boolean isClass() {
		return this.type;
	}
	
	public boolean isPackage() {
		return !this.type;
	}
	
	public BootFile parent() {
		return this.parent;
	}
	
	public int weight() {
		return this.weight;
	}
	
	public void weight(int weight) {
		this.weight = weight;
	}
	
	public Reflect reflect() {
		if (this.reflect == null) {
			this.reflect = new Reflect(this.id());
		}
		return this.reflect;
	}
	
	public void reflect(Reflect reflect) {
		this.reflect = reflect;
	}
	
}

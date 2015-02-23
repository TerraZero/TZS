package TZ.System.Reflect.Boot;

import TZ.System.Lists.Weighted;
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
public class Module implements Weighted {
	
	public static String getNameFromFile(String name) {
		return name.substring(0, name.length() - 6);
	}

	protected String name;
	protected String path;
	protected int weight;
	protected Reflect reflect;
	
	public Module(String name, String path) {
		this.name = name;
		this.path = path;
	}
	
	public String name() {
		return this.name;
	}
	
	public String fullname() {
		return this.name + ".class";
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

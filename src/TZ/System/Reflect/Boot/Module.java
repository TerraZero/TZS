package TZ.System.Reflect.Boot;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import TZ.System.TZSystem;
import TZ.System.Annotations.Info;
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
	
	public static List<Module> getAnnotation(List<Module> modules, Class<? extends Annotation> annotation) {
		List<Module> moduleAnnots = new ArrayList<Module>(16);
		
		for (Module module : modules) {
			Annotation annot = module.reflect().getAnnotation(annotation);
			if (annot != null) {
				moduleAnnots.add(module);
			}
		}
		return moduleAnnots;
	}
	
	public static<annot extends Annotation> void forAnnotation(List<Module> modules, Class<annot> annotation, BiConsumer<Module, annot> consumer) {
		for (Module module : modules) {
			annot annot = module.reflect().getAnnotation(annotation);
			if (annot != null) {
				consumer.accept(module, annot);
			}
		}
	}

	protected String name;
	protected String path;
	protected int weight;
	protected Reflect reflect;
	protected Info info;
	
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
	
	public Info info() {
		if (this.info == null) {
			this.info = this.reflect().getAnnotation(Info.class);
		}
		return this.info;
	}
	
	public boolean isModule() {
		return this.info() != null;
	}
	
	public String module() {
		String module = this.info().name();
		if (module.length() == 0) {
			module = this.name();
		}
		return module;
	}
	
	public String moduleID() {
		return TZSystem.nameToID(this.module());
	}
	
}

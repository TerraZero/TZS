package TZ.System;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import TZ.System.Annotations.AnnotationWrapper;
import TZ.System.Annotations.Info;
import TZ.System.Lists.Weighted;

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
public class Module implements Weighted, AnnotationWrapper<Info> {
	
	public static String getNameFromFile(String name) {
		return name.substring(0, name.length() - 6);
	}
	
	public static List<Module> getAnnotation(List<Module> modules, Class<? extends Annotation> annotation) {
		List<Module> moduleAnnots = new ArrayList<Module>(16);
		
		for (Module module : modules) {
			Annotation annot = module.boot().reflect().getAnnotation(annotation);
			if (annot != null) {
				moduleAnnots.add(module);
			}
		}
		return moduleAnnots;
	}
	
	public static<annot extends Annotation> void forAnnotation(List<Module> modules, Class<annot> annotation, BiConsumer<Module, annot> consumer) {
		for (Module module : modules) {
			annot annot = module.boot().reflect().getAnnotation(annotation);
			if (annot != null) {
				consumer.accept(module, annot);
			}
		}
	}

	protected Boot boot;
	protected Info info;
	
	// dependencies
	protected boolean available;
	protected boolean checked;
	
	public Module(Boot boot) {
		this.boot = boot;
	}
	
	public int weight() {
		return this.info().weight();
	}
	
	public Boot boot() {
		return this.boot;
	}
	
	public Info info() {
		if (this.info == null) {
			this.info = this.boot.reflect().getAnnotation(Info.class);
		}
		return this.info;
	}
	
	public boolean isModule() {
		return this.info() != null;
	}
	
	public String name() {
		String name = this.info().name();
		if (name.length() == 0) {
			name = this.boot.name();
		}
		return name;
	}
	
	public String id() {
		return TZSystem.nameToID(this.name());
	}
	
	/**
	 * @return 
	 * 		TRUE - IF module have all dependencies available
	 */
	public boolean isAvailable() {
		return this.available;
	}
	
	public void available(boolean available) {
		this.available = available;
	}
	
	public boolean isChecked() {
		return this.checked;
	}
	
	public void checked() {
		this.checked = true;
	}
	
}

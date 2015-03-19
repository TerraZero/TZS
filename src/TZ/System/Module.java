package TZ.System;

import java.lang.annotation.Annotation;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import TZ.System.Annotations.Info;
import TZ.System.Annotations.Base.AnnotationWrapper;
import TZ.System.Construction.MessageSystem;
import TZ.System.File.CFid;
import TZ.System.File.Fid;
import TZ.System.File.InfoFile;
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
@Info(install = "installModule")
public class Module implements Weighted, AnnotationWrapper<Boot, Info> {
	
	private static InfoFile infofile;
	
	public static InfoFile infofile() {
		if (Module.infofile == null) {
			Module.infofile = new InfoFile(new Fid(Paths.get(TZSystem.info().info("base-path"), "system", "module.info")));
		}
		return Module.infofile;
	}
	
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
	
	public static void installModule(InfoFile info, Module module) {
		CFid root = new CFid(info.info("base-path"));
		root.cDir("system").cFile("module.info");
		MessageSystem.moduleOut(module, "Create module files");
	}

	protected Boot boot;
	protected Info info;
	
	protected boolean active;
	
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
	
	public boolean isActive() {
		return this.active;
	}
	
	public void active(boolean active) {
		this.active = active;
	}

	/* 
	 * @see TZ.System.Annotations.AnnotationWrapper#value()
	 */
	@Override
	public Boot value() {
		return this.boot;
	}
	
}

package TZ.System.Module;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import TZ.System.LoadState;
import TZ.System.Sys;
import TZ.System.Annotations.Info;
import TZ.System.Annotations.Base.AnnotationWrapper;
import TZ.System.Construction.FileSystem;
import TZ.System.Construction.MessageSystem;
import TZ.System.File.CFid;
import TZ.System.File.InfoFile;
import TZ.System.Lists.Weighted;
import TZ.System.Reflect.Reflect;
import TZ.System.Reflect.Invoke.Invokeable;

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
@Info(version = "1.x", compatible = "1.x", install = "installModule", module = false)
public class Module implements Weighted, AnnotationWrapper<Boot, Info>, Invokeable {
	
	private static InfoFile infofile;
	
	public static InfoFile infofile() {
		if (Module.infofile == null) {
			Module.infofile = FileSystem.getInfo("user", "module.info");
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
	
	public static void installModule(LoadState state, Module module, List<Boot> boots) {
		CFid base = new CFid(state.data("base-path"));
		base.cDir("user", "defaults").cFile("module.info");
		MessageSystem.moduleOut(module, "Create module files");
	}

	protected Boot boot;
	protected Info info;
	protected Version version;
	
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
		return Sys.nameToID(this.name());
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
	
	public Version version() {
		if (this.version == null) {
			this.version = new Version(this.info().compatible());
		}
		return this.version;
	}

	/* 
	 * @see TZ.System.Annotations.AnnotationWrapper#value()
	 */
	@Override
	public Boot value() {
		return this.boot;
	}

	/* 
	 * @see TZ.System.Reflect.Invokeable#reflect()
	 */
	@Override
	public Reflect reflect() {
		return this.boot.reflect();
	}
	
}

package TZ.System;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TZ.System.Annotations.Construction;
import TZ.System.Construction.BootSystem;
import TZ.System.Construction.ConstructionWrapper;
import TZ.System.Construction.ExitSystem;
import TZ.System.Construction.FileSystem;
import TZ.System.Construction.InitSystem;
import TZ.System.Construction.InstallSystem;
import TZ.System.Construction.MessageSystem;
import TZ.System.Exception.ReflectException;
import TZ.System.Exception.TZException;
import TZ.System.File.Fid;
import TZ.System.File.InfoFile;
import TZ.System.Lists.Lists;
import TZ.System.Mechnic.Mechnic;
import TZ.System.Module.Boot;
import TZ.System.Module.Module;
import TZ.System.Module.TZSystemLoader;
import TZ.System.Module.Version;

/**
 * 
 * @author Terra
 * @created 19.02.2015
 * 
 * @file TZSystem.java
 * @project TZS
 * @identifier TZ.System
 *
 */
public class TZSystem {
	
	public static final String BOOT_ID = "boot";
	public static final String INIT_ID = "init";
	public static final String EXIT_ID = "exit";
	public static final Version version = new Version("1.x");
	
	public static void main(String[] args) {
		TZSystem.execute("test");
	}
	
	private static TZSystem system;
	
	public static TZSystem getSystem() {
		if (TZSystem.system == null) {
			TZSystem.system = new TZSystem();
		}
		return TZSystem.system;
	}

	/**
	 * Invoke the TZS 
	 * 
	 * @param program - the program name
	 * @return IF execute successful THAN NULL ELSE the TZException
	 */
	public static TZException execute(String program) {
		try {
			TZSystem.getSystem().sysExecute(program);
		} catch (TZException e) {
			return e;
		}
		return null;
	}
	
	public static void exit(int code) {
		TZSystem.getSystem().sysExit(code);
	}
	
	public static String program() {
		return TZSystem.getSystem().sysProgram();
	}
	
	public static String machineProgram() {
		return TZSystem.getSystem().sysMachineProgram();
	}
	
	public static String nameToID(String name) {
		return TZSystem.getSystem().sysNameToID(name);
	}
	
	public static Module getModule(String name) {
		return TZSystem.getSystem().sysGetModule(name);
	}
	
	public static<type> type construction(String name) {
		return TZSystem.getSystem().sysConstruction(name);
	}
	
	public static InfoFile info() {
		return TZSystem.getSystem().sysInfo();
	}
	
	
	
	protected List<Boot> boots;
	protected Map<String, ConstructionWrapper> constructions;
	protected List<Module> modules;
	protected String program;
	protected InfoFile info;
	
	public void sysExecute(String program) {
		try {
			this.program = program;
			this.sysConstruction();
			this.sysConstructioning();
			
			MessageSystem.out("Loading modules...");
			this.modules = BootSystem.bootModules(this.boots);
			
			this.sysInstall();
			this.sysFile();
			this.sysBooting();
			this.sysIniting();
		} catch (ReflectException e) {
			Exception re = e.exception();
			if (re != null) {
				re.printStackTrace();
			} else {
				e.printStackTrace();
			}
		}
	}
	
	public void sysConstruction() {
		this.boots = new TZSystemLoader().boots();
		Map<String, List<ConstructionWrapper>> constructions = new HashMap<String, List<ConstructionWrapper>>();
		this.constructions = new HashMap<String, ConstructionWrapper>();
		
		// build construction map
		for (Boot boot : this.boots) {
			Construction construction = boot.reflect().getAnnotation(Construction.class);
			if (construction != null) {
				List<ConstructionWrapper> sc = constructions.get(construction.name());
				if (sc == null) {
					sc = new ArrayList<ConstructionWrapper>(8);
					constructions.put(construction.name(), sc);
				}
				sc.add(new ConstructionWrapper(boot, construction));
			}
		}
		
		// execute construction map 
		constructions.forEach((s, l) -> {
			// sort list after weight
			Lists.sortASC(l);
			
			ConstructionWrapper system = null;
			// get system module
			for (ConstructionWrapper m : l) {
				if (m.info().system()) {
					system = m;
					break;
				}
			}
			l.remove(system);
			
			// WHEN are other constructions available THAN get the last
			if (l.size() != 0) {
				ConstructionWrapper active = l.get(l.size() - 1);
				active.system(system);
				this.constructions.put(active.info().name(), active);
			// ELSE get the system construction
			} else {
				this.constructions.put(system.info().name(), system);
			}
		});
	}
	
	public void sysConstructioning() {
		this.constructions.forEach((s, c) -> {
			if (c.system().info().init().length() != 0) {
				c.system().boot().reflect().call(c.system().info().init());
			}
		});
	}
	
	public void sysInstall() {
		MessageSystem.out("Install system...");
		String[] files = InstallSystem.installFiles(TZSystem.machineProgram(), new File("").getAbsolutePath());
		Fid install = InstallSystem.installFid(files);
		if (install == null) {
			this.info = InstallSystem.installing(install);
			MessageSystem.out("Install profile...");
			InstallSystem.installProfile(this.info, this.modules, this.boots);
			this.info.save();
		} else {
			this.info = InstallSystem.installing(install);
		}
	}
	
	public void sysFile() {
		FileSystem.init(this.info);
	}
	
	public void sysBooting() {
		BootSystem.activeModule(this.modules, Module.infofile());
		
		BootSystem.bootModulesSort(this.modules);
		this.modules = BootSystem.bootModulesDependencies(this.modules);
		
		MessageSystem.quest("Booting Mechnic...");
		Mechnic.bootMechnic(this.boots);
		
		MessageSystem.out("Booting modules...");
		BootSystem.booting(this.modules, this.boots);
	}
	
	public void sysIniting() {
		MessageSystem.out("Initiating modules...");
		InitSystem.initing(this.modules, this.boots);
	}
	
	public Module sysGetModule(String name) {
		for (Module module : this.modules) {
			if (module.id().equals(name)) {
				return module;
			}
		}
		return null;
	}
	
	public void sysExit(int code) {
		ExitSystem.exiting(code, this.modules, this.boots);
		ExitSystem.exit(code);
	}
	
	public void develOut(List<Module> modules) {
		for (Module module : modules) {
			MessageSystem.out(module.name());
		}
	}
	
	public String sysProgram() {
		return this.program;
	}
	
	public String sysMachineProgram() {
		return this.sysNameToID(this.program);
	}
	
	public String sysNameToID(String name) {
		return name.replaceAll(" ", "-").toLowerCase();
	}
	
	@SuppressWarnings("unchecked")
	public<type> type sysConstruction(String name) {
		return (type)this.constructions.get(name).boot().reflect().instantiate().getReflect();
	}
	
	public InfoFile sysInfo() {
		return this.info;
	}
	
}

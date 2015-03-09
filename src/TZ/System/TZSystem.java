package TZ.System;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TZ.System.Annotations.Construction;
import TZ.System.Construction.BootSystem;
import TZ.System.Construction.ExitSystem;
import TZ.System.Construction.FileSystem;
import TZ.System.Construction.InitSystem;
import TZ.System.Construction.InstallSystem;
import TZ.System.Construction.MessageSystem;
import TZ.System.Exception.ReflectException;
import TZ.System.Exception.TZException;
import TZ.System.File.Fid;
import TZ.System.Lists.Lists;

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
	
	public static void main(String[] args) {
		TZSystem.execute("test");
		FileSystem.get("test");
		TZSystem.exit(0);
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
	
	
	protected List<Module> classes;
	protected Map<String, ConstrucktionModule> constructions;
	protected List<Module> modules;
	protected String program;
	
	public void sysExecute(String program) {
		try {
			this.program = program;
			this.sysConstruction();
			this.sysConstructioning();
			
			this.sysInstall();
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
		this.classes = new TZSystemLoader().boots();
		Map<String, List<ConstrucktionModule>> constructions = new HashMap<String, List<ConstrucktionModule>>();
		this.constructions = new HashMap<String, ConstrucktionModule>();
		
		// build construction map
		for (Module classe : this.classes) {
			Construction construction = classe.reflect().getAnnotation(Construction.class);
			if (construction != null) {
				List<ConstrucktionModule> sc = constructions.get(construction.name());
				if (sc == null) {
					sc = new ArrayList<ConstrucktionModule>(8);
					constructions.put(construction.name(), sc);
				}
				sc.add(new ConstrucktionModule(classe, construction));
			}
		}
		
		// execute construction map
		constructions.forEach((s, l) -> {
			// sort list after weight
			Lists.sortASC(l);
			
			ConstrucktionModule system = null;
			// get system module
			for (ConstrucktionModule m : l) {
				if (m.isSystem()) {
					system = m;
					break;
				}
			}
			l.remove(system);
			
			// WHEN are other constructions available THAN get the last
			if (l.size() != 0) {
				ConstrucktionModule active = l.get(l.size() - 1);
				active.system(system);
				this.constructions.put(active.name(), active);
			// ELSE get the system construction
			} else {
				this.constructions.put(system.name(), system);
			}
		});
	}
	
	public void sysConstructioning() {
		this.constructions.forEach((s, c) -> {
			System.out.println(c.name());
			System.out.println(c.system().info().init());
			if (c.system().info().init().length() != 0) {
				c.system().module().reflect().call(c.info().init());
			}
		});
	}
	
	public void sysInstall() {
		MessageSystem.out("Install system...");
		String[] files = InstallSystem.installFiles(TZSystem.machineProgram() + ".info.txt", new File("").getAbsolutePath());
		Fid install = InstallSystem.installFid(files);
		InstallSystem.installing(install);
	}
	
	public void sysBooting() {
		MessageSystem.out("Loading modules...");
		this.modules = BootSystem.bootModules(this.classes);
		BootSystem.bootModulesSort(this.modules);
		this.modules = BootSystem.bootModulesDependencies(this.modules);
		
		MessageSystem.out("Booting modules...");
		BootSystem.booting(this.modules, this.classes);
		
		// devel
		this.develOut(this.modules);
	}
	
	public void sysIniting() {
		MessageSystem.out("Initiating modules...");
		InitSystem.initing(this.modules, this.classes);
	}
	
	public Module sysGetModule(String name) {
		for (Module module : this.modules) {
			if (module.module().equals(name)) {
				return module;
			}
		}
		return null;
	}
	
	public void sysExit(int code) {
		ExitSystem.exiting(code, this.modules, this.classes);
		ExitSystem.exit(code);
	}
	
	public void develOut(List<Module> modules) {
		for (Module module : modules) {
			MessageSystem.out(module.module());
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
		return (type)this.constructions.get(name).system().module().reflect().instantiate().getReflect();
	}
	
}

package TZ.System;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TZ.System.Annotations.Construction;
import TZ.System.Annotations.Functions.ExitFunction;
import TZ.System.Construction.Boot.BootSystem;
import TZ.System.Construction.Init.InitSystem;
import TZ.System.File.Fid;
import TZ.System.Lists.Lists;
import TZ.System.Reflect.Exception.ReflectException;

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
	}
	
	private static TZSystem system;
	
	public static TZSystem getSystem() {
		if (TZSystem.system == null) {
			TZSystem.system = new TZSystem();
		}
		return TZSystem.system;
	}

	public static void execute(String programm) {
		TZSystem.getSystem().sysExecute(programm);
	}
	
	public static void exit(int code) {
		TZSystem.getSystem().sysExit(code);
	}
	
	public static Module activeModule() {
		return TZSystem.getSystem().module;
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
	
	// temp module
	protected Module module;
	
	public void sysExecute(String program) {
		try {
			this.program = program;
			this.sysConstruction();
			this.sysConstructioning();
			
			TZMessage.out("Install system...");
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
	
	public Fid getInstallFid() {
		String program = TZSystem.machineProgram() + ".info.txt";
		String root = new File("").getAbsolutePath();
		Fid install = null;
		String[] files = {
			root + "/" + program,
			System.getProperty("user.home") + "/" + program,
			root + "/tzs.info.txt",
			System.getProperty("user.home") + "/tzs.info.txt",
		};
		
		TZMessage.out("Search info file:");
		for (int i = 0; i < files.length; i++) {
			install = new Fid(files[i]);
			TZMessage.quest(install + " ...");
			if (install.isExist()) return install;
			TZMessage.respond("not found");
		}
		return null;
	}
	
	public void sysInstall() {
		Fid install = this.getInstallFid();
		if (install == null) {
			TZMessage.out("Installing...");
			install = new Fid(new File("").getAbsolutePath() + "/" + TZSystem.machineProgram() + ".info.txt");
			TZMessage.quest("Create info file: " + install);
			if (install.create()) {
				TZMessage.respond("Success");
			} else {
				TZMessage.respond("Failed");
				this.sysInstallEnd();
			}
			//InfoFile info = new InfoFile(install);
			// TODO 
			TZMessage.out("Completed...");
		} else {
			TZMessage.respond("found");
			TZMessage.out("Info file: " + install);
		}
	}
	
	public void sysInstallEnd() {
		TZMessage.out("Install abort!");
		this.sysExit(1);
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
			if (c.system().info().init().length() != 0) {
				c.system().module().reflect().call(c.info().init());
			}
		});
	}
	
	public void sysBooting() {
		TZMessage.out("Loading modules...");
		this.modules = BootSystem.bootModules(this.classes);
		BootSystem.bootModulesSort(this.modules);
		this.modules = BootSystem.bootModulesDependencies(this.modules);
		
		TZMessage.out("Booting modules...");
		BootSystem.booting(this.modules, this.classes);
		
		// devel
		this.develOut(this.modules);
	}
	
	public Module sysGetModule(String name) {
		for (Module module : this.modules) {
			if (module.module().equals(name)) {
				return module;
			}
		}
		return null;
	}
	
	public void sysIniting() {
		TZMessage.out("Initiating modules...");
		InitSystem.initing(this.modules, this.classes);
	}
	
	public void sysExit(int code) {
		this.sysExiting(code);
		TZMessage.out("Exit");
		System.exit(code);
	}
	
	public void sysExiting(int code) {
		if (this.modules != null) {
			for (Module module : this.modules) {
				module.reflect().call(ExitFunction.class, TZSystem.EXIT_ID, module, this.classes);
			}
		}
	}
	
	public void develOut(List<Module> modules) {
		for (Module module : modules) {
			TZMessage.out(module.module());
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

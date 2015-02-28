package TZ.System;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import TZ.System.Annotations.Info;
import TZ.System.Annotations.Functions.BootFunction;
import TZ.System.Annotations.Functions.ExitFunction;
import TZ.System.Annotations.Functions.InitFunction;
import TZ.System.Cache.Cache;
import TZ.System.File.Fid;
import TZ.System.File.InfoFile;
import TZ.System.Lists.Lists;
import TZ.System.Reflect.CallState;
import TZ.System.Reflect.Reflects;
import TZ.System.Reflect.Boot.Module;
import TZ.System.Reflect.Boot.BootLoader;
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
	
	public static void out(String out) {
		TZSystem.getSystem().sysOut(out);
	}
	
	public static void exit(int code) {
		TZSystem.getSystem().sysExit(code);
	}
	
	public static void moduleMessage(String message) {
		TZSystem.getSystem().sysModuleMessage(message);
	}
	
	public static String program() {
		return TZSystem.getSystem().fsProgram();
	}
	
	public static String machineProgram() {
		return TZSystem.getSystem().fsMachineProgram();
	}
	

	
	public static void invoke(String function, Object... parameters) {
		TZSystem.getSystem().sysInvoke(function, parameters);
	}
	
	protected List<Module> classes;
	protected List<Module> modules;
	protected Cache<List<CallState>> invokes;
	protected String program;
	
	// temp module
	protected Module module;
	
	public TZSystem() {
		this.invokes = new Cache<List<CallState>>("system-invoke");
	}
	
	public void sysExecute(String program) {
		try {
			this.program = program;
			
			this.sysMessage("Install System...");
			this.sysInstall();
			this.sysMessage("Loading Modules...");
			this.sysModules();
			this.sysMessage("Booting Modules...");
			this.sysBooting();
			this.sysMessage("Initiating Modules...");
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
		Fid install = new Fid(root + "/" + program);
		
		this.sysMessage("Search info file:");
		this.sysMessage(install + " ...");
		if (install.isExist()) return install;
		install = new Fid(System.getProperty("user.home") + "/" + program);
		this.sysMessage(install + " ...");
		if (install.isExist()) return install;
		install = new Fid(root + "/tzs.info.txt");
		this.sysMessage(install + " ...");
		if (install.isExist()) return install;
		install = new Fid(System.getProperty("user.home") + "/tzs.info.txt");
		this.sysMessage(install + " ...");
		if (install.isExist()) return install;
		return null;
	}
	
	public void sysInstall() {
		Fid install = this.getInstallFid();
		if (install == null) {
			this.sysMessage("Installing...");
			install = new Fid(new File("").getAbsolutePath() + "/" + TZSystem.machineProgram() + ".info.txt");
			this.sysMessage("Create Info File: " + install);
			if (install.create()) {
				this.sysMessage("Success");
			} else {
				this.sysMessage("Failed");
				this.sysInstallEnd();
			}
			InfoFile info = new InfoFile(install);
			// TODO 
			this.sysMessage("Completed...");
		} else {
			this.sysMessage("Info file: " + install);
		}
	}
	
	public void sysInstallEnd() {
		this.sysMessage("Install abort!");
		this.sysExit(1);
	}
	
	public void sysModules() {
		this.modules = new ArrayList<Module>(512);
		this.classes = new BootLoader().boots();
		
		for (Module classe : classes) {
			Info info = classe.reflect().getAnnotation(Info.class);
			if (info != null) {
				classe.weight(info.weight());
				this.modules.add(classe);
			}
		}
		Lists.sortASC(this.modules);
		this.develOut(this.modules);
	}
	
	public void sysBooting() {
		for (Module module : this.modules) {
			this.module = module;
			module.reflect().call(BootFunction.class, TZSystem.BOOT_ID, module, this.classes);
		}
	}
	
	public void sysIniting() {
		for (Module module : this.modules) {
			this.module = module;
			module.reflect().call(InitFunction.class, TZSystem.INIT_ID, module, this.classes);
		}
	}
	
	public void sysExit(int code) {
		this.sysExiting(code);
		this.sysMessage("Exit");
		System.exit(code);
	}
	
	public void sysExiting(int code) {
		if (this.modules != null) {
			for (Module module : this.modules) {
				module.reflect().call(ExitFunction.class, TZSystem.EXIT_ID, module, this.classes);
			}
		}
	}
	
	public void sysOut(String out) {
		System.out.println(out);
	}
	
	public void sysMessage(String step) {
		this.sysOut(step);
	}
	
	public void sysModuleMessage(String message) {
		this.sysOut(this.module.name() + ": " + message);
	}
	
	public void develOut(List<Module> modules) {
		for (Module module : modules) {
			this.sysOut(module.name());
		}
	}
	
	public void sysInvoke(String function, Object... parameters) {
		List<CallState> invokes = this.invokes.get(function);
		if (invokes == null) {
			invokes = new ArrayList<CallState>(8);
			for (Module module : this.modules) {
				CallState call = Reflects.getInvoke(module.reflect(), function);
				if (call.length() != 0) invokes.add(call);
			}
			this.invokes.cache(function, invokes);
		}
		for (CallState call : invokes) {
			call.call(parameters);
		}
	}
	
	public String fsProgram() {
		return this.program;
	}
	
	public String fsMachineProgram() {
		return this.program.replaceAll(" ", "-").toLowerCase();
	}
	
}

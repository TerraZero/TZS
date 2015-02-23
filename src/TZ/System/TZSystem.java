package TZ.System;

import java.util.ArrayList;
import java.util.List;

import TZ.System.Annotations.Info;
import TZ.System.Annotations.Functions.BootFunction;
import TZ.System.Annotations.Functions.ExitFunction;
import TZ.System.Annotations.Functions.InitFunction;
import TZ.System.Cache.Cache;
import TZ.System.Lists.Lists;
import TZ.System.Reflect.CallState;
import TZ.System.Reflect.Reflects;
import TZ.System.Reflect.Boot.Module;
import TZ.System.Reflect.Boot.BootLoader;

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
		TZSystem.execute();
	}
	
	private static TZSystem system;
	
	public static TZSystem getSystem() {
		if (TZSystem.system == null) {
			TZSystem.system = new TZSystem();
		}
		return TZSystem.system;
	}

	public static void execute() {
		TZSystem.getSystem().sysExecute();
	}
	
	public static void out(String out) {
		TZSystem.getSystem().sysOut(out);
	}
	
	public static void exit(int code) {
		TZSystem.getSystem().sysExit(code);
	}
	

	
	public static void invoke(String function, Object... parameters) {
		TZSystem.getSystem().sysInvoke(function, parameters);
	}
	
	protected List<Module> classes;
	protected List<Module> modules;
	protected Cache<List<CallState>> invokes;
	
	public TZSystem() {
		this.invokes = new Cache<List<CallState>>("system-invoke");
	}
	
	public void sysExecute() {
		this.bootStep("Loading Modules");
		this.sysModules();
		this.bootStep("Booting Modules");
		this.sysBooting();
		this.bootStep("Initiating Modules");
		this.sysIniting();
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
			module.reflect().call(BootFunction.class, TZSystem.BOOT_ID, module, this.classes);
		}
	}
	
	public void sysIniting() {
		for (Module module : this.modules) {
			module.reflect().call(InitFunction.class, TZSystem.INIT_ID, module, this.classes);
		}
	}
	
	public void sysExit(int code) {
		this.sysExiting(code);
		System.exit(code);
	}
	
	public void sysExiting(int code) {
		for (Module module : this.modules) {
			module.reflect().call(ExitFunction.class, TZSystem.EXIT_ID, module, this.classes);
		}
	}
	
	public void sysOut(String out) {
		System.out.println(out);
	}
	
	public void bootStep(String step) {
		this.sysOut(step + "...");
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
	
}

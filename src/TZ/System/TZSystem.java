package TZ.System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TZ.System.Boot.Boot;
import TZ.System.Boot.Exit;
import TZ.System.Boot.Init;
import TZ.System.Boot.Info;
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
	
	public static void main(String[] args) {
		TZSystem.execute();
		TZSystem.exit(5);
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
	
	protected List<Module> modules;
	protected Map<Class<?>, List<Module>> register;
	
	public void sysExecute() {
		this.modules = new ArrayList<Module>(512);
		this.register = new HashMap<Class<?>, List<Module>>();
		
		this.sysModules();
		this.sysBoot();
		this.sysBooting();
		this.sysInit();
		this.sysIniting();
	}
	
	public void sysModules() {
		List<Module> boots = new BootLoader().boots();
		
		for (Module boot : boots) {
			Info info = boot.reflect().getAnnotation(Info.class);
			if (info != null) {
				boot.weight(info.weight());
				this.modules.add(boot);
			}
		}
		// TODO sort modules by boot.weight()
	}
	
	public void sysBoot() {
		List<Module> list = new ArrayList<Module>(128);
		this.register.put(Boot.class, list);
		
		for (Module module : this.modules) {
			Boot info = module.reflect().getAnnotation(Boot.class);
			if (info != null) list.add(module);
		}
	}
	
	public void sysBooting() {
		for (Module module : this.register.get(Boot.class)) {
			Boot info = module.reflect().annotation(Boot.class);
			module.reflect().call(info.function(), module, this.modules);
		}
	}
	
	public void sysInit() {
		List<Module> list = new ArrayList<Module>(128);
		this.register.put(Init.class, list);
		
		for (Module module : this.modules) {
			Init info = module.reflect().getAnnotation(Init.class);
			if (info != null) list.add(module);
		}
	}
	
	public void sysIniting() {
		for (Module module : this.register.get(Boot.class)) {
			Init info = module.reflect().annotation(Init.class);
			module.reflect().call(info.function(), module, this.modules);
		}
	}
	
	public void sysExit(int code) {
		List<Module> list = new ArrayList<Module>(128);
		this.register.put(Exit.class, list);
		
		for (Module module : this.modules) {
			Exit info = module.reflect().getAnnotation(Exit.class);
			if (info != null) list.add(module);
		}
		this.sysExiting(code);
		System.exit(code);
	}
	
	public void sysExiting(int code) {
		for (Module module : this.register.get(Exit.class)) {
			Exit info = module.reflect().annotation(Exit.class);
			module.reflect().call(info.function(), module, this.modules, code);
		}
	}
	
	public void sysOut(String out) {
		System.out.println(out);
	}
	
}

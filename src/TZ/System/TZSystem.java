package TZ.System;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import TZ.System.Reflect.Reflect;
import TZ.System.Reflect.Boot.Boot;
import TZ.System.Reflect.Boot.BootFile;
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
@Boot(weight=-5)
public class TZSystem {
	
	public static void main(String[] args) {
		TZSystem.getSystem().sysBoot();
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
	
	protected List<Reflect> boots;
	
	public void sysExecute() {
		this.sysBoot();
		this.sysInit();
	}
	
	public void sysBoot() {
		Reflect r = null;
		Boot boot = null;
		this.boots = new ArrayList<Reflect>(128);
		BootFile root = new BootLoader().root();
		Queue<BootFile> queue = new LinkedList<BootFile>();
		queue.add(root);
		
		while (queue.size() > 0) {
	    	for (BootFile file : queue.remove().contains().values()) {
	    		if (file.isClass()) {
	    			r = file.reflect();
	    			if ((boot = r.getAnnotation(Boot.class)) != null) {
	    				file.weight(boot.weight());
	    			}
	    		} else {
	    			queue.add(file);
	    		}
	    	}
	    }
		// TODO 
		Collections.sort(this.boots, null);
	}
	
	public void sysInit() {
		
	}
	
	public void sysExit() {
		
	}
	
	public void sysOut(String out) {
		System.out.println(out);
	}
	
}

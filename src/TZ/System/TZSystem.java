package TZ.System;

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
	
	public void sysExecute() {
		this.sysBoot();
		this.sysInit();
	}
	
	public void sysBoot() {
		
	}
	
	public void sysInit() {
		
	}
	
	public void sysExit() {
		
	}
	
	public void sysOut(String out) {
		System.out.println(out);
	}
	
}

package TZ.System.Construction.Init;

import java.util.List;

import TZ.System.Module;

/**
 * 
 * @author Terra
 * @created 04.03.2015
 * 
 * @file InitSystemConstruction.java
 * @project TZS
 * @identifier TZ.System.Init
 *
 */
public interface InitSystemConstruction {

	public void isIniting(List<Module> modules, List<Module> classes);
	
	public Module isInitModule();
	
}

package TZ.System.Construction;

import java.util.List;

import TZ.System.Boot;
import TZ.System.Module;

/**
 * 
 * @author Terra
 * @created 04.03.2015
 * 
 * @file ExitSystemConstruction.java
 * @project TZS
 * @identifier TZ.System.Construction.Exit
 *
 */
public interface ExitSystemConstruction {

	public void esExiting(int code, List<Module> modules, List<Boot> boots);
	
	public void esExit(int code);
	
}

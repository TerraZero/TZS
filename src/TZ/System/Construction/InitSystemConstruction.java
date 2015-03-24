package TZ.System.Construction;

import java.util.List;

import TZ.System.Module.Boot;
import TZ.System.Module.Module;

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

	public void isIniting(List<Module> modules, List<Boot> boots);
	
	public Module isInitModule();
	
}

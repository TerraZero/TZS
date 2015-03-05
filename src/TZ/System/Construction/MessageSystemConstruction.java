package TZ.System.Construction;

import TZ.System.Module;

/**
 * 
 * @author Terra
 * @created 05.03.2015
 * 
 * @file MessageSystemConstruction.java
 * @project TZS
 * @identifier TZ.System.Construction
 *
 */
public interface MessageSystemConstruction {

	public void msOut(String out);
	
	public void msQuest(String quest);
	
	/**
	 * @param respond
	 * @param status DEFAULT true
	 */
	public void msRespond(String respond);
	
	/**
	 * @param respond
	 * @param status DEFAULT true
	 */
	public void msRespond(String respond, boolean status);
	
	public void msModuleOut(Module module, String out);
	
}

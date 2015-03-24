package TZ.System.Construction;

import TZ.System.Exception.TZException;
import TZ.System.Module.Module;

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
	
	public void msOut(String out, MessageType type);
	
	public void msQuest(String quest);

	public void msRespond(String respond, MessageType type, boolean status);
	
	public void msModuleOut(Module module, String out);
	
	public void msException(Exception e);
	
	public void msException(TZException e);
	
}

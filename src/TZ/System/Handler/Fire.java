package TZ.System.Handler;

/**
 * 
 * @author Terra
 * @created 23.02.2015
 * 
 * @file Fire.java
 * @project TZS
 * @identifier TZ.System.Handler
 *
 */
public interface Fire<listener, event> {

	public void fire(listener listener, event event);
	
}

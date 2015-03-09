package TZ.System.Exception;

/**
 * 
 * @author terrazero
 * @created Mar 9, 2015
 * 
 * @file BootLoaderException.java
 * @project TZS
 * @identifier TZ.System.Exception
 *
 */
public class BootLoaderException extends TZException {

	private static final long serialVersionUID = 1L;

	public BootLoaderException(Exception e, String message, String debug) {
		super(e, message, debug);
	}

}

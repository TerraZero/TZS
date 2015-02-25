package TZ.System;

/**
 * 
 * @author Terra
 * @created 19.02.2015
 * 
 * @file TZException.java
 * @project TZS
 * @identifier TZ.System
 *
 */
public class TZException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	protected Exception e;
	protected String message;
	protected String debug;
	
	public TZException(String message, String debug) {
		this.message = message;
		this.debug = debug;
	}
	
	public TZException(Exception e, String message, String debug) {
		this.e = e;
		this.message = message;
		this.debug = debug;
	}
	
	/* 
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return debug;
	}
	
	public Exception exception() {
		return this.e;
	}
	
}

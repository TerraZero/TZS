package TZ.System.File.IO;

import TZ.System.File.Fid;

/**
 * 
 * @author terrazero
 * @created Mar 10, 2015
 * 
 * @file FidIO.java
 * @project TZS
 * @identifier TZ.System.File.IO
 *
 */
public interface FidIO {
	
	public void open();
	
	public void close();
	
	public Fid fid();
	
	public boolean isOpen();

}

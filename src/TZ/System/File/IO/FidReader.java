package TZ.System.File.IO;

import TZ.System.File.Fid;

/**
 * 
 * @author terrazero
 * @created Feb 24, 2015
 * 
 * @file FidReader.java
 * @project TZS
 * @identifier TZ.System.File.IO
 *
 */
public interface FidReader {

	public void open();
	
	public void close();
	
	public Fid fid();
	
	public boolean isOpen();
	
}

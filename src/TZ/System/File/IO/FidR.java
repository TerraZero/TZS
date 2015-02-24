package TZ.System.File.IO;

import TZ.System.File.Fid;

/**
 * 
 * @author terrazero
 * @created Feb 24, 2015
 * 
 * @file FidR.java
 * @project TZS
 * @identifier TZ.System.File.IO
 *
 */
public class FidR implements FidReader {
	
	protected Fid fid;

	/* 
	 * @see TZ.System.File.IO.FidReader#open()
	 */
	@Override
	public void open() {
	}

	/* 
	 * @see TZ.System.File.IO.FidReader#close()
	 */
	@Override
	public void close() {
	}

	/* 
	 * @see TZ.System.File.IO.FidReader#fid()
	 */
	@Override
	public Fid fid() {
		return null;
	}

	/* 
	 * @see TZ.System.File.IO.FidReader#isOpen()
	 */
	@Override
	public boolean isOpen() {
		return false;
	}

}

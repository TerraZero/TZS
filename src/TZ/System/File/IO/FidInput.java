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
public abstract class FidInput implements FidReader {
	
	protected Fid fid;
	
	public FidInput(Fid fid) {
		this.fid = fid;
	}

	/* 
	 * @see TZ.System.File.IO.FidReader#fid()
	 */
	@Override
	public Fid fid() {
		return this.fid;
	}

}

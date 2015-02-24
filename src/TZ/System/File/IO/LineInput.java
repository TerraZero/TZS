package TZ.System.File.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import TZ.System.TZSystem;
import TZ.System.File.Fid;
import TZ.System.File.FileSystem;

/**
 * 
 * @author terrazero
 * @created Feb 24, 2015
 * 
 * @file LineInput.java
 * @project TZS
 * @identifier TZ.System.File.IO
 *
 */
public class LineInput extends FidInput implements LineReader {
	
	protected BufferedReader in;

	public LineInput(Fid fid) {
		super(fid);
	}

	/* 
	 * @see TZ.System.File.IO.FidReader#open()
	 */
	@Override
	public void open() {
		try {
			this.in = new BufferedReader(new FileReader(this.fid.file()));
		} catch (IOException e) {
			this.exception(e);
		}
	}

	/* 
	 * @see TZ.System.File.IO.FidReader#close()
	 */
	@Override
	public void close() {
		try {
			this.in.close();
		} catch (IOException e) {
			this.exception(e);
		}
		this.in = null;
	}

	/* 
	 * @see TZ.System.File.IO.FidReader#isOpen()
	 */
	@Override
	public boolean isOpen() {
		return this.in != null;
	}

	/* 
	 * @see TZ.System.File.IO.LineReader#readLine()
	 */
	@Override
	public String readLine() {
		try {
			return this.in.readLine();
		} catch (IOException e) {
			this.exception(e);
		}
		return null;
	}
	
	public void exception(Exception e) {
		TZSystem.out(e.toString());
	}

}

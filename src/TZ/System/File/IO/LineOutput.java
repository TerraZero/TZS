package TZ.System.File.IO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import TZ.System.Construction.MessageSystem;
import TZ.System.File.Fid;

/**
 * 
 * @author terrazero
 * @created Mar 10, 2015
 * 
 * @file LineOutput.java
 * @project TZS
 * @identifier TZ.System.File.IO
 *
 */
public class LineOutput extends FidInputOutput implements LineWriter {
	
	protected BufferedWriter out;

	public LineOutput(Fid fid) {
		super(fid);
	}

	/* 
	 * @see TZ.System.File.IO.FidIO#open()
	 */
	@Override
	public void open() {
		try {
			this.out = new BufferedWriter(new FileWriter(this.fid.file()));
		} catch (IOException e) {
			this.exception(e);
		}
	}

	/* 
	 * @see TZ.System.File.IO.FidIO#close()
	 */
	@Override
	public void close() {
		try {
			this.out.close();
		} catch (IOException e) {
			this.exception(e);
		}
		this.out = null;
	}

	/* 
	 * @see TZ.System.File.IO.FidIO#isOpen()
	 */
	@Override
	public boolean isOpen() {
		return this.out != null;
	}

	/* 
	 * @see TZ.System.File.IO.LineWriter#write(java.lang.String)
	 */
	@Override
	public void write(String line) {
		try {
			this.out.write(line);
		} catch (IOException e) {
			this.exception(e);
		}
	}
	
	public void writeLine() {
		try {
			this.out.newLine();
		} catch (IOException e) {
			this.exception(e);
		}
	}
	
	public void writeLine(String line) {
		this.write(line);
		this.writeLine();
	}
	
	public void exception(Exception e) {
		MessageSystem.exception(e);
	}

}

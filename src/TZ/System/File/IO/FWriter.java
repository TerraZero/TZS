package TZ.System.File.IO;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;

import TZ.System.File.Fid;

/**
 * 
 * @author Terra
 * @created 18.12.2014
 * 
 * @file FWriter.java
 * @project G7C
 * @identifier TZ.File
 *
 */
public class FWriter {

	protected Fid fid;
	protected Exception e;
	
	public FWriter(Fid fid) {
		this.fid = fid;
	}
	
	public Fid fid() {
		return this.fid;
	}
	
	public FWriter writeAll(List<String> lines) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fid.path().toFile()), Charset.defaultCharset()));
			for (String line : lines) {
				this.writeLine(writer, line);
			}
			writer.close();
		} catch (IOException e) {
			this.exception(e);
		}
		return this;
	}
	
	public void writeLine(BufferedWriter writer, String line) throws IOException {
		writer.write(line);
		writer.newLine();
	}
	
	public void exception(Exception e) {
		this.e = e;
	}
	
	public Exception exception() {
		return this.e;
	}
	
}

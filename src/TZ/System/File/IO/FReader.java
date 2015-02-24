package TZ.System.File.IO;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import TZ.System.File.Fid;

/**
 * 
 * @author Terra
 * @created 18.12.2014
 * 
 * @file FReader.java
 * @project G7C
 * @identifier TZ.File
 *
 */
public class FReader {

	protected Fid fid;
	protected Exception e;
	
	public FReader(Fid fid) {
		this.fid = fid;
	}
	
	public Fid fid() {
		return this.fid;
	}
	
	public List<String> readAll() {
		try (Stream<String> stream = Files.lines(this.fid.path(), Charset.defaultCharset())) {
			return stream.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void exception(Exception e) {
		this.e = e;
	}
	
	public Exception exception() {
		return this.e;
	}
	
}

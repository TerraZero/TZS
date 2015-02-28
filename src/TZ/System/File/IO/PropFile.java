package TZ.System.File.IO;

import java.util.Properties;

import TZ.System.File.Fid;

/**
 * 
 * @author Terra
 * @created 26.02.2015
 * 
 * @file PropFile.java
 * @project TZS
 * @identifier TZ.System.File.IO
 *
 */
public class PropFile {

	protected Properties props;
	protected Fid fid;
	
	public PropFile(Fid fid) {
		this(fid, true);
	}
	
	public PropFile(Fid fid, boolean load) {
		this.fid = fid;
		this.props = new Properties();
		if (load) {
			this.load();
		}
	}
	
	public PropFile load() {
		
		return this;
	}
	
	public PropFile save() {
		return this;
	}
	
}

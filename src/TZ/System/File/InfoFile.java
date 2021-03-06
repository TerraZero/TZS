package TZ.System.File;

import java.util.HashMap;
import java.util.Map;

import TZ.System.File.IO.LineInput;
import TZ.System.File.IO.LineOutput;

/**
 * 
 * @author terrazero
 * @created Feb 23, 2015
 * 
 * @file InfoFile.java
 * @project TZS
 * @identifier TZ.System.File
 *
 */
public class InfoFile {

	protected Fid fid;
	protected Map<String, String> infos;
	
	protected LineInput input;
	protected LineOutput output;
	
	public InfoFile(Fid fid, Fid... extend) {
		this.fid = fid;
		this.input = new LineInput(this.fid);
		this.output = new LineOutput(this.fid);
		
		for (int i = 0; i < extend.length; i++) {
			this.extend(extend[i]);
		}
	}
	
	public Fid fid() {
		return this.fid;
	}
	
	public Map<String, String> info() {
		if (this.infos == null) {
			this.load();
		}
		return this.infos;
	}
	
	public String info(String key) {
		return this.info().get(key);
	}
	
	public InfoFile info(String key, String value) {
		this.info().put(key, value);
		return this;
	}
	
	public InfoFile load() {
		this.load(false);
		return this;
	}
	
	public InfoFile load(boolean force) {
		if (this.infos == null || force) {
			this.infos = new HashMap<String, String>();
			
			input.open();
			String line = null;
			while ((line = input.readLine()) != null) {
				this.loading(line.trim());
			}
			input.close();
		}
		return this;
	}
	
	protected void loading(String line) {
		if (line.length() != 0 && !line.startsWith("#")) {
			String[] split = line.split("=");
			if (split.length == 2) {
				if (split[1].length() == 0) {
					this.infos.put(split[0].trim(), null); 
				} else {
					this.infos.put(split[0].trim(), split[1].trim());
				}
			}
		}
	}
	
	public InfoFile save() {
		this.output.open();
		this.infos.forEach((k, v) -> {
			this.saving(k, v);
		});
		this.output.close();
		return this;
	}
	
	public void extend(Fid file) {
		if (file.isExist()) {
			new InfoFile(file).info().forEach((key, value) -> {
				this.info(key, value);
			});;
		}
	}
	
	protected void saving(String key, String value) {
		this.output.writeLine(key + " = " + value);
	}
	
}

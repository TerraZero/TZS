package TZ.System.File;

import java.util.HashMap;
import java.util.Map;

import TZ.System.TZSystem;
import TZ.System.Construction.File.FileSystem;
import TZ.System.File.IO.LineInput;

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
	
	public static void main(String[] args) {
		TZSystem.execute("InfoFileTest");
		Fid fid = FileSystem.get("test.info.txt");
		System.out.println(fid);
		InfoFile file = new InfoFile(fid);
		Map<String, String> map = file.load();
		map.forEach((k, v) -> System.out.println(k + " : " + v));
	}

	protected Fid fid;
	protected Map<String, String> infos;
	protected LineInput input;
	
	public InfoFile(Fid fid) {
		this.fid = fid;
	}
	
	public Fid fid() {
		return this.fid;
	}
	
	public Map<String, String> load() {
		return this.load(false);
	}
	
	public Map<String, String> load(boolean force) {
		if (this.infos == null || force) {
			this.infos = new HashMap<String, String>();
			this.input = new LineInput(this.fid);
			
			input.open();
			String line = null;
			while ((line = input.readLine()) != null) {
				this.loading(line.trim());
			}
			input.close();
		}
		return this.infos;
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
	
}

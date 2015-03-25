package TZ.System;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author terrazero
 * @created Mar 25, 2015
 * 
 * @file Load.java
 * @project TZS
 * @identifier TZ.System
 *
 */
public class LoadState {
	
	private String current;

	private String program;
	
	private Map<String, String> data;
	
	public LoadState(String program) {
		this.program = program;
		this.current = "start";
	}
	
	public String program() {
		return this.program;
	}
	
	public String current() {
		return this.current;
	}
	
	public void current(String current) {
		this.current = current;
	}
	
	public Map<String, String> data() {
		if (this.data == null) {
			this.data = new HashMap<String, String>();
		}
		return this.data;
	}
	
	public String data(String key) {
		return this.data().get(key);
	}
	
	public void data(String key, String value) {
		this.data().put(key, value);
	}
	
}

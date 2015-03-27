package TZ.System;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public static final String CREATE_STATE = "create";
	public static final String INFO_STATE = "info";
	public static final String LOAD_STATE = "load";
	public static final String CONSTRUCTION_STATE = "construction";
	public static final String INSTALL_STATE = "install";
	public static final String BOOT_STATE = "boot";
	public static final String INIT_STATE = "init";
	public static final String RUN_STATE = "run";
	
	private int current;

	private String program;
	private String[] states;
	
	private Map<String, String> data;
	
	private Map<String, String> info;
	
	private Map<String, Map<String, String>> infos;
	
	public LoadState(String program) {
		this.program = program;
		this.current = 0;
		this.infos = new HashMap<String, Map<String, String>>();
		this.states = new String[] {
			LoadState.CREATE_STATE,
			LoadState.INFO_STATE,
			LoadState.LOAD_STATE,
			LoadState.CONSTRUCTION_STATE,
			LoadState.INSTALL_STATE,
			LoadState.BOOT_STATE,
			LoadState.INIT_STATE, 
			LoadState.RUN_STATE, 
		};
	}
	
	public String program() {
		return this.program;
	}
	
	public String current() {
		return this.states[this.current];
	}
	
	public void setCurrent(int current) {
		this.current = current;
	}
	
	public void setCurrent(String current) {
		for (int i = 0; i < this.states.length; i++) {
			if (this.states[i].equals(current)) {
				this.current = i;
				break;
			}
		}
	}
	
	public String next() {
		this.current++;
		return this.current();
	}
	
	public String nextState() {
		if (this.current + 1 == this.states.length) {
			return null;
		} else {
			return this.states[this.current + 1];
		}
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
	
	public String data(String key, String value) {
		this.data().put(key, value);
		return value;
	}
	
	public boolean dataIs(String key, String value) {
		String v = this.data(key);
		return v != null && v.equals(value);
	}
	
	public void setInfo(Map<String, String> file) {
		this.info = file;
	}
	
	public Map<String, String> info() {
		return this.info;
	}
	
	public void infos(String key, Map<String, String> infos) {
		this.infos.put(key, infos);
	}
	
	public Map<String, String> infos(String key) {
		return this.infos.get(key);
	}
	
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String string = "===================================================================\n"; 
		string += "Load State [" + this.current() + "::" + this.current + "/" + this.states.length + "]\n";
		string += "\tnext state:\t\t[" + this.nextState() + "]\n";
		string += "\tprogram:\t\t'" + this.program + "'\n";
		string += "\tdata:\t\t\t'" + this.data().size() + "'\n";
		for(Entry<String, String> entry : this.data().entrySet()) {
		    string += "\t\t[" + entry.getKey() + "] => '" + entry.getValue() + "'\n"; 
		}
		string += "===================================================================";
		return string;
	}
	
}

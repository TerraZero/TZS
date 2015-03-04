package TZ.System.Base.Data;

import java.util.List;
import java.util.Map;

import TZ.System.Module;
import TZ.System.TZSystem;
import TZ.System.Annotations.Info;
import TZ.System.Annotations.Functions.InitFunction;
import TZ.System.Mechnic.Mechnic;
import TZ.System.Mechnic.MechnicCreator;

/**
 * 
 * @author Terra
 * @created 28.02.2015
 * 
 * @file Daton.java
 * @project TZS
 * @identifier TZ.System.Base.Data
 *
 */
@Info
public class Daton {
	
	public static void main(String[] args) {
		TZSystem.execute("Test Daton");
		Daton root = new Daton();
		root.add("hier").add("type", "textfield").add("required", "false");
		root.add("test").add("type", "container").add("cols", "2").add("items").add("submit").add("type", "submit").add("text", "Best�tigen");
		root.get("test").get("items").add("text").add("type", "markup").add("markup", "irgend ein text");
		System.out.println(root.toTreeString());
	}
	
	protected static MechnicCreator<Map<String, Daton>> creator;
	
	@InitFunction
	public static void initDaton(String id, Module module, List<Module> classes) {
		Daton.creator = Mechnic.getCreator("daton", "map");
	}

	protected String value;
	protected Map<String, Daton> values;
	
	public Daton() {
		
	}
	
	public Daton(String value) {
		this.value(value);
	}
	
	public Daton value(String value) {
		this.value = value;
		this.values = null;
		return this;
	}
	
	public String value() {
		return this.value;
	}
	
	public Daton add(String key) {
		return this.add(key, new Daton());
	}
	
	public Daton add(String key, String value) {
		this.add(key, new Daton(value));
		return this;
	}
	
	public Daton add(String key, Daton values) {
		this.value = null;
		if (this.values == null) {
			this.values = Daton.creator.mechnic(null);
		}
		if (values == null) values = new Daton();
		this.values.put(key, values);
		return values;
	}
	
	public Daton get(String key) {
		return this.values.get(key);
	}
	
	public boolean isList() {
		return this.values != null;
	}
	
	public boolean isValue() {
		return this.value != null;
	}
	
	public boolean isNull() {
		return this.value == null && this.values == null;
	}
	
	public int length() {
		return this.values.size();
	}
	
	public String toTreeString() {
		return this.toTreeString("\n", "\t");
	}

	public String toTreeString(String seperator, String tab) {
		StringBuilder sb = new StringBuilder();
		this.toTreeStringItem(sb, seperator, tab, "");
		return sb.toString();
	}
	
	public void toTreeStringItem(StringBuilder sb, String seperator, String tab, String tabs) {
		if (this.isValue()) {
			sb.append(tabs).append(this.value);
		} else if (this.isList()) {
			this.values.forEach((k, v) -> {
				if (v.isList()) {
					sb.append(tabs).append(k).append(" : [").append(seperator);
					v.toTreeStringItem(sb, seperator, tab, tabs + tab);
					sb.append(tabs).append("]").append(seperator);
				} else {
					sb.append(tabs).append(k).append(" : ").append(v.value()).append(seperator);
				}
			});
		} else {
			sb.append(tabs).append("NULL");
		}
	}
	
	/* 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (this.isValue()) {
			return this.value;
		} else if (this.isList()) {
			return "[" + this.length() + "]";
		} else {
			return "NULL";
		}
	}
	
}

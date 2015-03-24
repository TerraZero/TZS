package TZ.System.Base.Data;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import TZ.System.TZSystem;
import TZ.System.Annotations.Info;
import TZ.System.Mechnic.Mechnic;
import TZ.System.Mechnic.MechnicCreator;
import TZ.System.Module.Module;

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
@Info(version = "1.x", compatible = "1.x", init = "initDaton")
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
	
	public static void initDaton(String id, Module module, List<Module> classes) {
		Daton.creator = Mechnic.getCreator("daton", "map");
	}

	protected String value;
	protected Map<String, Daton> values;
	
	public Daton() {
		this.values = Daton.creator.mechnic(null);
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
	
	public String get(String value, String fallback) {
		Daton get = this.values.get(value);
		if (get == null) {
			return fallback;
		}
		return get.value();
	}
	
	public int getInt(String value, int fallback) {
		Daton get = this.values.get(value);
		if (get == null) {
			return fallback;
		}
		return get.toInt();
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
	
	public boolean has(String key) {
		if (this.isList()) {
			return this.values.get(key) != null;
		}
		return false;
	}
	
	public Daton has(String key, String whenNot) {
		if (this.isList() && this.values.get(key) == null) {
			this.add(key, whenNot);
		}
		return this;
	}
	
	public Daton has(String key, Daton whenNot) {
		if (this.isList() && this.values.get(key) == null) {
			this.add(key, whenNot);
		}
		return this;
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
	
	public boolean isTrue() {
		return this.value.equals("true");
	}
	
	public boolean isFalse() {
		return this.value.equals("false");
	}
	
	public int toInt() {
		return Integer.parseInt(this.value);
	}
	
	public void forData(BiConsumer<String, Daton> consumer) {
		this.values.forEach((s, d) -> {
			if (s.startsWith("#")) {
				consumer.accept(s, d);
			}
		});
	}
	
	public void forNonData(BiConsumer<String, Daton> consumer) {
		this.values.forEach((s, d) -> {
			if (!s.startsWith("#")) {
				consumer.accept(s, d);
			}
		});
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

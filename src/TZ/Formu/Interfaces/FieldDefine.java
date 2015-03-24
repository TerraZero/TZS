package TZ.Formu.Interfaces;

import java.util.Map;

import javax.swing.JComponent;

import TZ.System.Base.Data.Daton;
import TZ.System.Mechnic.Mechnic;

/**
 * 
 * @author terrazero
 * @created Mar 11, 2015
 * 
 * @file FormuDefine.java
 * @project TZS
 * @identifier TZ.Formu.Interfaces
 *
 */
public interface FieldDefine<field> {

	public String type();
	
	public default String extended() {
		return null;
	}
	
	public default field create(String type, Daton data) {
		return Mechnic.get("formu", type);
	}
	
	public Map<String, FieldFunction<field>> buildFunctions();
	
	public void buildFunctionsCache(Map<String, FieldFunction<field>> functions);
	
	public default void execute(JComponent parent, field field, Daton data) {
		Map<String, FieldFunction<field>> functions = this.buildFunctions();
		data.forData((d, v) -> {
			FieldFunction<field> function = functions.get(d);
			if (function != null) {
				function.call(parent, field, v, data);
			}
		});
	}
	
	public Map<String, FieldFunction<field>> functions();
	
}

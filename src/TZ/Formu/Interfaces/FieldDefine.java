package TZ.Formu.Interfaces;

import javax.swing.JComponent;

import TZ.System.Base.Data.Daton;
import TZ.System.Lists.Weighted;
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
public interface FieldDefine<field> extends Weighted {

	public String type();
	
	public default String[] extended() {
		return new String[0];
	}
	
	public default field create(String type, Daton data) {
		return Mechnic.get("formu", type);
	}
	
	public void execute(JComponent parent, field field, Daton data);
	
}

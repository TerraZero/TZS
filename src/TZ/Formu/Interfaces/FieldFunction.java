package TZ.Formu.Interfaces;

import javax.swing.JComponent;

import TZ.System.Base.Data.Daton;

/**
 * 
 * @author terrazero
 * @created Mar 13, 2015
 * 
 * @file FieldFunctions.java
 * @project TZS
 * @identifier TZ.Formu.Interfaces
 *
 */
public interface FieldFunction<field> {

	public void call(JComponent parent, field field, Daton value, Daton data);
	
}

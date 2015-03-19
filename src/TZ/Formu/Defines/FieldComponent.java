package TZ.Formu.Defines;

import java.util.Map;

import javax.swing.JPanel;

import TZ.Formu.Interfaces.FieldFunction;

/**
 * 
 * @author terrazero
 * @created Mar 11, 2015
 * 
 * @file FormPanel.java
 * @project TZS
 * @identifier TZ.Formu.Defines
 *
 */
public class FieldComponent extends FieldCache<JPanel> {

	/* 
	 * @see TZ.Formu.Interfaces.FieldDefine#type()
	 */
	@Override
	public String type() {
		return "component";
	}

	/* 
	 * @see TZ.Formu.Defines.FieldCache#buildFunctionsCache(java.util.Map)
	 */
	@Override
	public void buildFunctionsCache(Map<String, FieldFunction<JPanel>> functions) {
		functions.put("#bounds", (parent, field, value, data) -> {
			field.setBounds(value.getInt("-x", field.getX()), value.getInt("-y", field.getY()), value.getInt("-width", field.getWidth()), value.getInt("-height", field.getHeight()));
		});
	}

}

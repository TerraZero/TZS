package TZ.Formu.Defines;

import java.util.Map;

import javax.swing.JPanel;

import TZ.Formu.Interfaces.FieldFunction;

/**
 * 
 * @author terrazero
 * @created Mar 13, 2015
 * 
 * @file FieldPanel.java
 * @project TZS
 * @identifier TZ.Formu.Defines
 *
 */
public class FieldPanel extends FieldCache<JPanel> {

	/* 
	 * @see TZ.Formu.Interfaces.FieldDefine#type()
	 */
	@Override
	public String type() {
		return "panel";
	}

	/* 
	 * @see TZ.Formu.Interfaces.FieldDefine#buildFunctionsCache(java.util.Map)
	 */
	@Override
	public void buildFunctionsCache(Map<String, FieldFunction<JPanel>> functions) {
		
	}

}

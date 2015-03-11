package TZ.Formu.Defines;

import javax.swing.JComponent;
import javax.swing.JPanel;

import TZ.Formu.Interfaces.FieldDefine;
import TZ.System.Base.Data.Daton;

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
public class FormPanel implements FieldDefine<JPanel> {

	/* 
	 * @see TZ.System.Lists.Weighted#weight()
	 */
	@Override
	public int weight() {
		return 0;
	}

	/* 
	 * @see TZ.Formu.Interfaces.FieldDefine#type()
	 */
	@Override
	public String type() {
		return "panel";
	}

	/* 
	 * @see TZ.Formu.Interfaces.FieldDefine#execute(javax.swing.JComponent, java.lang.Object, TZ.System.Base.Data.Daton)
	 */
	@Override
	public void execute(JComponent parent, JPanel field, Daton data) {
		Daton weight = data.get("#weight");
		if (weight != null) {
			field.setSize(weight.toInt(), field.getHeight());
		}
	}

}

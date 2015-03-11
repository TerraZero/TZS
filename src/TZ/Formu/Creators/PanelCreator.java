package TZ.Formu.Creators;

import java.awt.Color;

import javax.swing.JPanel;

import TZ.System.Mechnic.Mech;
import TZ.System.Mechnic.MechnicCreator;

/**
 * 
 * @author terrazero
 * @created Mar 11, 2015
 * 
 * @file PanelCreator.java
 * @project TZS
 * @identifier TZ.Formu.Creators
 *
 */
@Mech(option = "FormuPanel", mechnic = "panel")
public class PanelCreator implements MechnicCreator<JPanel> {

	/* 
	 * @see TZ.System.Mechnic.MechnicCreator#mechnic(java.lang.Object[])
	 */
	@Override
	public JPanel mechnic(Object[] args) {
		JPanel create = new JPanel();
		create.setLayout(null);
		create.setBackground(Color.BLUE);
		return create;
	}

}

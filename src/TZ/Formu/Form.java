package TZ.Formu;

import javax.swing.JPanel;

import TZ.System.Base.Data.Daton;

/**
 * 
 * @author terrazero
 * @created Mar 11, 2015
 * 
 * @file Form.java
 * @project TZS
 * @identifier TZ.Forms
 *
 */
public class Form {

	protected String id;
	protected Daton data;
	protected JPanel panel;
	
	public Form(String id) {
		this.id = id;
		this.data = new Daton();
	}
	
	public Form(String id, Daton data) {
		this.id = id;
		this.data = data;
	}
	
	public String id() {
		return this.id;
	}
	
	public Daton data() {
		return this.data;
	}
	
	
	
	public void execute() {
		this.data.has("#type", "panel");
		this.data.add("#width", "50");
		this.panel = Formu.build(null, this.data);
	}
	
	public JPanel panel() {
		return this.panel;
	}
	
}

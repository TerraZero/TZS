package TZ.Formu;

import java.util.List;

import TZ.System.TZSystem;
import TZ.System.Mechnic.Mechnic;

/**
 * 
 * @author terrazero
 * @created Mar 11, 2015
 * 
 * @file Forms.java
 * @project TZS
 * @identifier TZ.Forms
 *
 */
public class Forms {

	protected List<Form> steps;
	protected int current;
	
	public static void main(String[] args) {
		TZSystem.execute("form");
		
	}
	
	public Forms(Form... steps) {
		this.steps = Mechnic.get("formu", "list");
		for (Form step : steps) {
			this.steps.add(step);
		}
	}
	
	public void execute() {
		this.steps.get(this.current).execute();
	}
	
	
	
	public List<Form> steps() {
		return this.steps;
	}
	
	
	
	public void addStep(Form step) {
		this.steps.add(step);
	}
	
	public void addStep(Form step, int index) {
		this.steps.add(index, step);
	}
	
}

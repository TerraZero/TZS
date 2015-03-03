package TZ.System.Boot;

import TZ.System.Annotations.Construction;
import TZ.System.Lists.Weighted;

/**
 * 
 * @author terrazero
 * @created Mar 3, 2015
 * 
 * @file ConstrucktionModule.java
 * @project TZS
 * @identifier TZ.System.Boot
 *
 */
public class ConstrucktionModule implements Weighted {

	protected Module module;
	protected Construction construction;
	protected ConstrucktionModule system;
	
	public ConstrucktionModule(Module module, Construction construction) {
		this.module = module;
		this.construction = construction;
	}
	
	/* 
	 * @see TZ.System.Lists.Weighted#weight()
	 */
	@Override
	public int weight() {
		return this.construction.weight();
	}
	
	public String name() {
		return this.construction.name();
	}
	
	public boolean isSystem() {
		return this.construction.system();
	}
	
	public Module module() {
		return this.module;
	}
	
	public ConstrucktionModule system() {
		return this.system;
	}
	
	public void system(ConstrucktionModule cm) {
		this.system = cm;
	}

}

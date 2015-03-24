package TZ.System.Construction;

import TZ.System.Annotations.Construction;
import TZ.System.Annotations.Base.AnnotationWrapper;
import TZ.System.Lists.Weighted;
import TZ.System.Module.Boot;

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
public class ConstructionWrapper implements Weighted, AnnotationWrapper<Boot, Construction> {

	protected Boot boot;
	protected Construction construction;
	protected ConstructionWrapper system;
	
	public ConstructionWrapper(Boot boot, Construction construction) {
		this.boot = boot;
		this.construction = construction;
		this.system = this;
	}
	
	/* 
	 * @see TZ.System.Lists.Weighted#weight()
	 */
	@Override
	public int weight() {
		return this.construction.weight();
	}
	
	public Boot boot() {
		return this.boot;
	}
	
	public ConstructionWrapper system() {
		return this.system;
	}
	
	public void system(ConstructionWrapper cw) {
		this.system = cw;
	}

	/* 
	 * @see TZ.System.Annotations.AnnotationWrapper#info()
	 */
	@Override
	public Construction info() {
		return this.construction;
	}

	/* 
	 * @see TZ.System.Annotations.AnnotationWrapper#value()
	 */
	@Override
	public Boot value() {
		return this.boot;
	}

}

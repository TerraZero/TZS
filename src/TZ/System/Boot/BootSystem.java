package TZ.System.Boot;

import TZ.System.TZSystem;
import TZ.System.Annotations.Construction;

/**
 * 
 * @author terrazero
 * @created Mar 4, 2015
 * 
 * @file BootSystem.java
 * @project TZS
 * @identifier TZ.System.Boot
 *
 */
@Construction(name = "bootsystem", system = true)
public class BootSystem implements BootSystemConstruction {

	private static BootSystemConstruction construction;
	
	public static BootSystemConstruction construction() {
		if (BootSystem.construction == null) {
			BootSystem.construction = TZSystem.construction("bootsystem");
		}
		return BootSystem.construction;
	}
	
}

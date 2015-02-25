package TZ.System.Mechnic;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author terrazero
 * @created Feb 24, 2015
 * 
 * @file Mechnics.java
 * @project TZS
 * @identifier TZ.System.Mechnic
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mech {

	public String option();
	
	public String mechnic();
	
}

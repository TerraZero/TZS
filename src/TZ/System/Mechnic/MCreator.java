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
public @interface MCreator {

	/**
	 * The UNIQUE name of this creator
	 */
	public String option();
	
	/**
	 * The Mechnic name for this creator
	 */
	public String mechnic();
	
	/**
	 * The context for this creator
	 */
	public String context() default "";
	
	/**
	 * Define the creator as default creator
	 */
	public boolean base() default false;
	
}

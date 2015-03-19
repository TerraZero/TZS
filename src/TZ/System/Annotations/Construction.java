package TZ.System.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author terrazero
 * @created Mar 3, 2015
 * 
 * @file System.java
 * @project TZS
 * @identifier TZ.System.Annotations
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Construction {

	/**
	 * UNIQUE name of the construction type. The last construction of one type is the active construction.<br> 
	 * The "system" construction is only the first construction in the order.
	 * @return
	 */
	public String name();
	
	/**
	 * Ascending order of the construction
	 */
	public int weight() default 0;
	
	/**
	 * Marker to define this construction as "system" construction
	 * (WARNING: only one construction for the same name can be the "system" construction)
	 */
	public boolean system() default false;
	
	/**
	 * Call direct after construction are built for avoid loading when demand
	 * @return function
	 */
	public String init() default "";
	
}

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

	public String name();
	
	public int weight() default 0;
	
	public boolean system() default false;
	
}

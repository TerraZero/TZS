package TZ.System.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author terrazero
 * @created Mar 11, 2015
 * 
 * @file Install.java
 * @project TZS
 * @identifier TZ.System.Annotations
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Install {

	public String function();
	
}

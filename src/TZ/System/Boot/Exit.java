package TZ.System.Boot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Terra
 * @created 21.02.2015
 * 
 * @file Exit.java
 * @project TZS
 * @identifier TZ.System.Boot
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Exit {

	public String function() default "sysExit";
	
}

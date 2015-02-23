package TZ.System.Annotations.Functions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author terrazero
 * @created Feb 23, 2015
 * 
 * @file Invoke.java
 * @project TZS
 * @identifier TZ.System.Annotations.Functions
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InvokeFunction {

	public String[] functions();
	
}

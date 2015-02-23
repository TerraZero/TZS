package TZ.System.Annotations.Functions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Terra
 * @created 19.02.2015
 * 
 * @file Booter.java
 * @project G7C
 * @identifier TZ.Reflect.Boot
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BootFunction {
	
}

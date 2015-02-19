package TZ.System.Reflect.Boot;

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
@Target(ElementType.TYPE)
public @interface Booter {

	public String name();
	
}

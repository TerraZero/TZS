package TZ.System.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Terra
 * @created 21.02.2015
 * 
 * @file Info.java
 * @project TZS
 * @identifier TZ.System.Boot
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Info {
	
	/**
	 * Ascending order of the module
	 */
	public int weight() default 0;
	
	/**
	 * UNIQUE name of the module. WHEN is undefined use the TZSystem.nameToID function.
	 */
	public String name() default "";
	
	/**
	 * A list of modules from which this module depends on.
	 */
	public String[] dependencies() default {};
	
	/**
	 * @param String id - process id (TZSystem.BOOT_ID) 
	 * @param Module module - (this) the module
	 * @param List &lt;Boot&gt; boots - a list of all loaded classes
	 * @return function
	 */
	public String boot() default "";
	
	/**
	 * @param String id - process id (TZSystem.INIT_ID)
	 * @param Module module - (this) the module
	 * @param List &lt;Boot&gt; boots - a list of all loaded classes
	 * @return function
	 */
	public String init() default "";
	
	/**
	 * @param String id - process id (TZSystem.EXIT_ID)
	 * @param Module module - (this) the module
	 * @param List &lt;Boot&gt; boots - a list of all loaded classes
	 * @return function
	 */
	public String exit() default "";
	
	/**
	 * @param InfoFile info - system info
	 * @param Module module - (this) the module
	 * @return function
	 */
	public String install() default "";
	
	/**
	 * @param InfoFile info - system info file to edit
	 * @param Module module - (this) the module
	 * @return function
	 */
	public String installProfile() default "";
	
}

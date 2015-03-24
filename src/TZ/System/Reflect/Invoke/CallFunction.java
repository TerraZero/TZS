package TZ.System.Reflect.Invoke;

import java.lang.annotation.Annotation;

/**
 * 
 * @author terrazero
 * @created Mar 20, 2015
 * 
 * @file InvokeFunction.java
 * @project TZS
 * @identifier TZ.System.Reflect.Invoke
 *
 */
public interface CallFunction<annot extends Annotation> {

	public String call(annot annotation);
	
}

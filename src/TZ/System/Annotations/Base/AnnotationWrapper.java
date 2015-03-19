package TZ.System.Annotations.Base;

import java.lang.annotation.Annotation;

/**
 * 
 * @author terrazero
 * @created Mar 4, 2015
 * 
 * @file AnnotationWrapper.java
 * @project TZS
 * @identifier TZ.System.Annotations
 *
 */
public interface AnnotationWrapper<object, annot extends Annotation> {

	public annot info();
	
	public object value();
	
}

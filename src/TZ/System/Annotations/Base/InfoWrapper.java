package TZ.System.Annotations.Base;

import java.lang.annotation.Annotation;

/**
 * 
 * @author terrazero
 * @created Mar 11, 2015
 * 
 * @file InfoWrapper.java
 * @project TZS
 * @identifier TZ.System.Annotations
 *
 */
public class InfoWrapper<type, annot extends Annotation> implements AnnotationWrapper<type, annot> {
	
	protected type object;
	protected annot info;
	
	public InfoWrapper(type object, annot info) {
		this.object = object;
		this.info = info;
	}

	/* 
	 * @see TZ.System.Annotations.AnnotationWrapper#info()
	 */
	@Override
	public annot info() {
		return this.info;
	}
	
	public type value() {
		return this.object;
	}

}

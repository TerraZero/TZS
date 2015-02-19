package TZ.System.Reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import TZ.System.Reflect.Exception.ReflectException;

/**
 * 
 * @author Terra
 * @created 19.02.2015
 * 
 * @file Reflect.java
 * @project G7C
 * @identifier TZ.Reflect
 *
 */
public class Reflect {

	protected Class<?> reflectClass;
	protected Object reflect;
	
	public Reflect() {
		
	}
	
	public Reflect(String load) {
		this.reflect(load);
	}
	
	public Reflect(Object reflect) {
		this.reflect(reflect);
	}
	
	public Reflect reflect(String load) {
		try {
			this.reflectClass =  ClassLoader.getSystemClassLoader().loadClass(load);
		} catch (ClassNotFoundException e) {
			throw new ReflectException(e, "", "");
		}
		return this;
	}
	
	public Reflect reflect(Object reflect) {
		this.reflect = reflect;
		this.reflectClass = reflect.getClass();
		return this;
	}
	
	public Reflect instantiate(Object... args) {
		try {
			if (args.length == 0) {
				this.reflect = this.reflectClass.newInstance();
			} else {
				Constructor<?> c = Reflects.getConstructor(this.reflectClass, Reflects.extractClasses(args));
				this.reflect = c.newInstance(Reflects.getParameter(args, c.getParameterTypes(), c.isVarArgs()));
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ReflectException(e, "instantiate", "instantiate");
		}
		return this;
	}
	
	public <annot extends Annotation> annot annotation(Class<annot> annotation) {
		return this.reflectClass.getAnnotation(annotation);
	}
	
	public boolean hasAnnotation(Class<? extends Annotation> annotation) {
		return this.reflectClass.isAnnotationPresent(annotation);
	}
	
}

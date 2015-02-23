package TZ.System.Reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

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
	
	public Class<?> reflect() {
		return this.reflectClass;
	}
	
	public Object getReflect() {
		return this.reflect;
	}
	
	public Reflect reflect(String load) {
		try {
			this.reflectClass = ClassLoader.getSystemClassLoader().loadClass(load);
		} catch (ClassNotFoundException e) {
			throw new ReflectException(e, "reflect", "reflect");
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
	
	
	
	@SuppressWarnings("unchecked")
	public<type> type call(String function, Object... parameters) {
		try {
			Method method = Reflects.getFunctions(this.reflectClass, function, Reflects.extractClasses(parameters));
			return (type)method.invoke(this.reflect, parameters);
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ReflectException(e, "call", "call");
		}
	}
	
	public void call(Class<? extends Annotation> annotation, Object... parameters) {
		List<Method> functions = Reflects.getFunctions(this.reflectClass, annotation);
		
		if (!functions.isEmpty()) {
			for (Method function : functions) {
				try {
					function.invoke(this.reflect, parameters);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new ReflectException(e, "call", "call");
				}
			}
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	public<type> type get(String field) {
		try {
			return (type)this.reflectClass.getField(field).get(this.reflect);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new ReflectException(e, "get", "get");
		}
	}
	
	public Reflect set(String field, Object set) {
		try {
			this.reflectClass.getField(field).set(this.reflect, set);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new ReflectException(e, "set", "set");
		}
		return this;
	}
	
	
	
	public <annot extends Annotation> annot getAnnotation(Class<annot> annotation) {
		if (this.hasAnnotation(annotation)) {
			return this.reflectClass.getAnnotation(annotation);
		}
		return null;
	}
	
	public <annot extends Annotation> annot annotation(Class<annot> annotation) {
		return this.reflectClass.getAnnotation(annotation);
	}
	
	public boolean hasAnnotation(Class<? extends Annotation> annotation) {
		return this.reflectClass.isAnnotationPresent(annotation);
	}
	
}

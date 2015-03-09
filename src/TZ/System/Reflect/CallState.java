package TZ.System.Reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import TZ.System.Exception.ReflectException;

/**
 * 
 * @author terrazero
 * @created Feb 23, 2015
 * 
 * @file ReflectCall.java
 * @project TZS
 * @identifier TZ.System.Reflect
 *
 */
public class CallState {

	protected Class<?> reflectClass;
	protected Object reflect;
	
	protected List<Method> calls;
	
	public CallState(Reflect reflect) {
		this.reflectClass = reflect.reflect();
		this.reflect = reflect.getReflect();
	}
	
	public CallState(Reflect reflect, List<Method> calls) {
		this(reflect);
		this.setMethod(calls);
	}
	
	public CallState setMethod(List<Method> calls) {
		this.calls = calls;
		return this;
	}
	
	public void call(Object... parameters) {
		for (Method method : this.calls) {
			try {
				method.invoke(this.reflect, parameters);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				throw new ReflectException(e, "call", "call");
			}
		}
	}
	
	public int length() {
		return this.calls.size();
	}
	
}

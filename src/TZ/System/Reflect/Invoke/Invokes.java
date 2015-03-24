package TZ.System.Reflect.Invoke;

import java.lang.annotation.Annotation;
import java.util.List;

import TZ.System.Mechnic.Mechnic;

/**
 * 
 * @author terrazero
 * @created Mar 20, 2015
 * 
 * @file Invokes.java
 * @project TZS
 * @identifier TZ.System.Reflect
 *
 */
public class Invokes {

	public static<annot extends Annotation, result> List<result> getResults(List<Invokeable> invokes, Class<annot> annotation, CallFunction<annot> function, Object... parameters) {
		List<result> results = Mechnic.get("invokes", "list", 10);
		
		for (Invokeable invoke : invokes) {
			annot annot = invoke.reflect().getAnnotation(annotation);
			if (annot == null) {
				results.add(invoke.reflect().call(function.call(annot), parameters));
			}
		}
		return results;
	}
	
}

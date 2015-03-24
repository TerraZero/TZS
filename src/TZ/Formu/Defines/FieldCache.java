package TZ.Formu.Defines;

import java.util.Map;

import TZ.Formu.Interfaces.FieldDefine;
import TZ.Formu.Interfaces.FieldFunction;
import TZ.System.Mechnic.Mechnic;

/**
 * 
 * @author terrazero
 * @created Mar 13, 2015
 * 
 * @file FieldCache.java
 * @project TZS
 * @identifier TZ.Formu.Defines
 *
 */
public abstract class FieldCache<field> implements FieldDefine<field> {
	
	protected Map<String, FieldFunction<field>> functions;

	/* 
	 * @see TZ.Formu.Interfaces.FieldDefine#buildFunctions(java.util.Map)
	 */
	@Override
	public Map<String, FieldFunction<field>> buildFunctions() {
		if (this.functions == null) {
			this.functions = Mechnic.get("cache", "map");
			this.buildFunctionsCache(this.functions);
		}
		return this.functions;
	}
	
	public Map<String, FieldFunction<field>> functions() {
		return this.functions;
	}

}

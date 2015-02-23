package TZ.System.Cache;

import java.util.List;

/**
 * 
 * @author terrazero
 * @created Feb 23, 2015
 * 
 * @file KeyedCache.java
 * @project TZS
 * @identifier TZ.System.Cache
 *
 */
public abstract class KeyedCache<key, type> extends Cache<type> {

	public KeyedCache(String name) {
		super(name);
	}
	
	
	
	public abstract String getName(key key);
	
	
	
	public boolean is(key key) {
		return this.is(this.getName(key));
	}
	
	public type get(key key) {
		return this.get(this.getName(key));
	}
	
	public type cache(key key, type data) {
		return this.cache(this.getName(key), data);
	}
	
	public void clearKeys(List<key> keys) {
		for (key key : keys) {
			this.cache.remove(this.getName(key));
		}
	}

}

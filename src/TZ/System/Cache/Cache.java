package TZ.System.Cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author terrazero
 * @created Feb 12, 2015
 * 
 * @file Cache.java
 * @project G7C
 * @identifier TZ.Cache
 *
 */
public class Cache<type> {
	
	private static List<Cache<?>> register;
	
	static {
		Cache.register = new ArrayList<Cache<?>>();
	}
	
	public synchronized static void register(Cache<?> c) {
		Cache.register.add(c);
	}
	
	public static void clearAll() {
		Cache.clear("all");
	}
	
	public synchronized static void clear(String... clears) {
		if (clears[0] == "all") {
			for (Cache<?> c : Cache.register) {
				c.clear();
			}
		} else {
			Map<String, List<String>> clearMap = Cache.clearMap(clears);
			for (Cache<?> c : Cache.register) {
				List<String> fields = clearMap.get(c.name);
				boolean clear = clearMap.containsKey(c.name);
				if (fields == null && clear) {
					c.clear();
				} else if (clear) {
					c.clears(fields);
				}
			}
		}
	}
	
	private static Map<String, List<String>> clearMap(String... clears) {
		Map<String, List<String>> clearMap = new HashMap<String, List<String>>();
		String[] split = null;
		for (String clear : clears) {
			split = clear.split("::");
			List<String> list = clearMap.get(split[0]);
			if (split.length == 2) {
				if (list == null) {
					list = new ArrayList<String>();
				}
				list.add(split[1]);
			}
			clearMap.put(split[0], list);
		}
		return clearMap;
	}
	
	public static Cache<?> getCache(String name) {
		for (Cache<?> c : Cache.register) {
			if (c.name == name) {
				return c;
			}
		}
		return null;
	}
	
	public static List<String> getCaches() {
		List<String> caches = new ArrayList<String>();
		for (Cache<?> c : Cache.register) {
			caches.add(c.name);
		}
		return caches;
	}
	
	public static List<String> getCacheFields() {
		List<String> cacheFields = new ArrayList<String>();
		for (Cache<?> c : Cache.register) {
			c.cache.forEach((f, v) -> cacheFields.add(c.name + "::" + f));
		}
		return cacheFields;
	}

	protected String name;
	protected Map<String, type> cache;
	
	public Cache(String name) {
		Cache.register(this);
		this.name = name;
		this.cache = new HashMap<String, type>();
	}
	
	public boolean is(String name) {
		return this.cache.containsKey(name);
	}
	
	public type get(String name) {
		return this.cache.get(name);
	}
	
	public type cache(String name, type data) {
		System.out.println("cache: " + name);
		return this.cache.put(name, data);
	}
	
	public void clear() {
		this.cache.clear();
	}
	
	public void clears(List<String> clears) {
		for (String clear : clears) {
			this.cache.remove(clear);
		}
	}
	
}

package TZ.System.Cache;

/**
 * 
 * @author terrazero
 * @created Mar 23, 2015
 * 
 * @file LimitCache.java
 * @project TZS
 * @identifier TZ.System.Cache
 *
 */
public class LimitCache<type> extends Cache<type> {
	
	protected int limit;

	public LimitCache(String name) {
		this(name, 0);
	}
	
	public LimitCache(String name, int limit) {
		super(name);
		this.limit = limit;
	}
	
	public int limit() {
		return this.limit;
	}
	
	/* 
	 * @see TZ.System.Cache.Cache#cache(java.lang.String, java.lang.Object)
	 */
	@Override
	public type cache(String name, type data) {
		this.cache.put(name, data);
		if (this.limit != 0 && this.cache.size() == limit + 1) {
			this.cache.values().remove(this.cache.values().iterator().next());
		}
		return data;
	}
	
}

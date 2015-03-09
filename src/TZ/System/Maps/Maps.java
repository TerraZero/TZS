package TZ.System.Maps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import TZ.System.Lists.Weighted;

/**
 * 
 * @author terrazero
 * @created Mar 2, 2015
 * 
 * @file Maps.java
 * @project TZS
 * @identifier TZ.System.Maps
 *
 */
public class Maps {
	
	public static void main(String[] args) {
		Map<String, Weighted> test = new TreeMap<String, Weighted>();
		
		test.put("test-1", () -> 1);
		test.put("test-4", () -> 2);
		test.put("test-2", () -> 4);
		test.put("test-5", () -> 5);
		test.put("test-3", () -> 3);
		
		test.forEach((k, v) -> {
			System.out.println(k);
		});
		
		Maps.sortASC(test);
		
		test.forEach((k, v) -> {
			System.out.println(k);
		});
	}
	
	private static Comparator<Entry<?, ? extends Weighted>> mapASCComparator;

	public static Comparator<Entry<?, ? extends Weighted>> getASCComparator() {
		if (Maps.mapASCComparator == null) {
			Maps.mapASCComparator = (o1, o2) -> o1.getValue().weight() - o2.getValue().weight(); 
		}
		return Maps.mapASCComparator;
	}
	
	public static<key, type extends Weighted> void sortASC(Map<key, type> unsorted) {
		List<Entry<key, type>> list = new ArrayList<Entry<key, type>>(unsorted.entrySet());	
		Collections.sort(list, Maps.getASCComparator());
		unsorted.clear();
		unsorted.forEach((k, v) -> {
			System.out.println(k);
		});
		System.out.println("##");
		for (Entry<key, type> e : list) {
			System.out.println(e.getKey());
			unsorted.put(e.getKey(), e.getValue());
		}
		System.out.println("##");
		unsorted.forEach((k, v) -> {
			System.out.println(k);
		});
		System.out.println("##");
	}
	
}

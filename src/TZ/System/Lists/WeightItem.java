package TZ.System.Lists;

/**
 * 
 * @author terrazero
 * @created Feb 23, 2015
 * 
 * @file Weighted.java
 * @project TZS
 * @identifier TZ.System.Lists
 *
 */
public class WeightItem<type> implements Weighted {
	
	protected type data;
	protected int weight;
	
	public WeightItem() {
		
	}

	public WeightItem(type data) {
		this.data = data;
	}
	
	public WeightItem(type data, int weight) {
		this.data = data;
		this.weight = weight;
	}

	public WeightItem(int weight) {
		this.weight = weight;
	}

	public type data() {
		return this.data;
	}
	
	public int weight() {
		return this.weight;
	}
	
}

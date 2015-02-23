package TZ.System.Handler;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Terra
 * @created 23.02.2015
 * 
 * @file Handler.java
 * @project TZS
 * @identifier TZ.System.Handler
 *
 */
public class Handler<listener, event> {
	
	protected List<listener> listeners;
	protected Fire<listener, event> fire;
	
	public Handler(Fire<listener, event> fire) {
		this.listeners = new ArrayList<listener>(8);
		this.fire = fire;
	}
	
	public void fire(event event) {
		for (listener listener : this.listeners) {
			this.fire.fire(listener, event);
		}
	}
	
	public void add(listener listener) {
		this.listeners.add(listener);
	}
	
	public void remove(listener listener) {
		this.listeners.remove(listener);
	}
	
}

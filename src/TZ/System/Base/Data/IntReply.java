package TZ.System.Base.Data;

import java.util.ArrayList;
import java.util.List;

import TZ.System.Base.Ints;

/**
 * 
 * @author terrazero
 * @created Dec 15, 2014
 * 
 * @file IntSetting.java
 * @project G7C
 * @identifier TZ.Ints
 *
 */
public class IntReply {
	
	public static final int IS_NULL = Ints.setBit(0, 0, true);
	public static final String NULL = "NULL";
	public static final int IS_TRUE = Ints.setBit(0, 1, true);
	public static final String TRUE = "TRUE";
	public static final int IS_FALSE = Ints.setBit(0, 2, true);
	public static final String FALSE = "FALSE";
	
	public static String[] getBitsArray(IntReply reply) {
		List<String> bits = IntReply.getBits(reply);
		return bits.toArray(new String[bits.size()]);
	}
	
	public static List<String> getBits(IntReply reply) {
		List<String> bits = new ArrayList<String>();
		if (reply.get(0)) bits.add(IntReply.NULL);
		if (reply.get(1)) bits.add(IntReply.TRUE);
		if (reply.get(2)) bits.add(IntReply.FALSE);
		return bits;
	}
	
	public static IntReply getReply(List<String> bits) {
		IntReply reply = new IntReply();
		for (String bit : bits) {
			reply.set(bit, true);
		}
		return reply;
	}
	
	public static IntReply getReply(String... bits) {
		IntReply reply = new IntReply();
		for (String bit : bits) {
			reply.set(bit, true);
		}
		return reply;
	}

	protected int reply;
	
	public IntReply() {
		
	}
	
	public IntReply(boolean reply) {
		if (reply) {
			this.reply = IntReply.IS_TRUE;
		} else {
			this.reply = IntReply.IS_FALSE;
		}
	}
	
	public IntReply(int reply) {
		this.reply = reply;
	}
	
	public int reply() {
		return this.reply;
	}
	
	public IntReply set(int reply) {
		this.reply = reply;
		return this;
	}
	
	public boolean get(int pos) {
		return Ints.isBit(this.reply, pos);
	}
	
	public IntReply set(int pos, boolean set) {
		this.reply = Ints.setBit(this.reply, pos, set);
		return this;
	}
	
	public IntReply set(String bit, boolean set) {
		if (bit.equals(IntReply.NULL)) this.set(0, set);
		if (bit.equals(IntReply.TRUE)) this.set(1, set);
		if (bit.equals(IntReply.FALSE)) this.set(2, set);
		return this;
	}
	
	public boolean isNull() {
		return this.get(0);
	}
	
	public boolean isTrue() {
		return this.get(1);
	}
	
	public boolean isFalse() {
		return this.get(2);
	}
	
}

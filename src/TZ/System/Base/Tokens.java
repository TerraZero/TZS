package TZ.System.Base;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author terrazero
 * @created Feb 24, 2015
 * 
 * @file Tokens.java
 * @project TZS
 * @identifier TZ.System.Base
 *
 */
public class Tokens {

	protected Map<String, String> tokens;
	
	public Tokens() {
		this.tokens = new HashMap<String, String>();
	}
	
	public Map<String, String> map() {
		return this.tokens;
	}
	
	public String token(String token) {
		String replace = this.tokens.get(token);
		if (replace == null) {
			return token;
		} else {
			return replace;
		}
	}
	
	public void addToken(String replace, String... token) {
		this.addToken(true, replace, token);
	}
	
	public void addToken(boolean wrapper, String replace, String... token) {
		this.tokens.put(this.buildToken(wrapper, token), replace);
	}
	
	public String buildToken(boolean wrapper, String... tokens) {
		String token = "";
		
		for (String t : tokens) {
			token += ":" + t;
		}
		token = token.substring(1);
		if (wrapper) token = "[" + token + "]";
		return token;
	}
	
}

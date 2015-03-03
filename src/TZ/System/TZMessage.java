package TZ.System;

/**
 * 
 * @author terrazero
 * @created Mar 3, 2015
 * 
 * @file TZMessage.java
 * @project TZS
 * @identifier TZ.System
 *
 */
public class TZMessage {
	
	private static TZMessage message;
	
	public static TZMessage getMessage() {
		if (TZMessage.message == null) {
			TZMessage.message = new TZMessage();
		}
		return TZMessage.message;
	}

	public static void out(String out) {
		TZMessage.getMessage().mOut(out);
	}
	
	public static void moduleOut(String out) {
		TZMessage.getMessage().mModuleOut(out);
	}
	
	public static void quest(String out) {
		TZMessage.getMessage().mQuest(out);
	}
	
	public static void respond(String out) {
		TZMessage.getMessage().mRespond(out);
	}
	
	
	
	public void mOut(String out) {
		System.out.println(out);
	}
	
	public void mModuleOut(String out) {
		this.mOut(TZSystem.activeModule().module() + ": " + out);
	}
	
	public void mQuest(String out) {
		System.out.print(out);
	}
	
	public void mRespond(String out) {
		this.mOut("\t[" + out.toUpperCase() + "]");
	}
	
}

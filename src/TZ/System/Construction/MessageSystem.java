package TZ.System.Construction;

import TZ.System.Module;
import TZ.System.TZSystem;
import TZ.System.Annotations.Construction;

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
@Construction(name = "messagesystem", system = true)
public class MessageSystem implements MessageSystemConstruction {
	
	private static MessageSystemConstruction construction;
	
	public static MessageSystemConstruction construction() {
		if (MessageSystem.construction == null) {
			MessageSystem.construction = TZSystem.construction("messagesystem");
		}
		return MessageSystem.construction;
	}

	public static void out(String out) {
		MessageSystem.construction().msOut(out);
	}
	
	public static void moduleOut(Module module, String out) {
		MessageSystem.construction().msModuleOut(module, out);
	}
	
	public static void quest(String out) {
		MessageSystem.construction().msQuest(out);
	}
	
	public static void respond(String out) {
		MessageSystem.construction().msRespond(out);
	}
	
	public static void respond(String out, boolean status) {
		MessageSystem.construction().msRespond(out, status);
	}
	
	

	/* 
	 * @see TZ.System.Construction.MessageSystemConstruction#msOut(java.lang.String)
	 */
	@Override
	public void msOut(String out) {
		System.out.println(out);
	}

	/* 
	 * @see TZ.System.Construction.MessageSystemConstruction#msQuest(java.lang.String)
	 */
	@Override
	public void msQuest(String quest) {
		System.out.print(quest);
	}

	/* 
	 * @see TZ.System.Construction.MessageSystemConstruction#msRespond(java.lang.String)
	 */
	@Override
	public void msRespond(String respond) {
		this.msRespond(respond, true);
	}

	/* 
	 * @see TZ.System.Construction.MessageSystemConstruction#msRespond(java.lang.String, boolean)
	 */
	@Override
	public void msRespond(String respond, boolean status) {
		if (status) respond = "\t[" + respond.toUpperCase() + "]";
		System.out.println(respond);
	}

	/* 
	 * @see TZ.System.Construction.MessageSystemConstruction#msModuleOut(java.lang.String)
	 */
	@Override
	public void msModuleOut(Module module, String out) {
		System.out.println(module.name() + ": " + out);
	}
	
}

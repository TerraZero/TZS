package TZ.System.Construction;

import TZ.System.Module;
import TZ.System.TZSystem;
import TZ.System.Annotations.Construction;
import TZ.System.Exception.TZException;

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
	
	public static void respond(MessageType type) {
		MessageSystem.construction().msRespond(null, type, true);
	}
	
	public static void respond(String out) {
		MessageSystem.construction().msRespond(out, MessageType.OK, true);
	}
	
	public static void respond(String out, MessageType type) {
		MessageSystem.construction().msRespond(out, type, true);
	}
	
	public static void respond(String out, MessageType type, boolean status) {
		MessageSystem.construction().msRespond(out, type, status);
	}
	
	public static void exception(Exception e) {
		MessageSystem.construction().msException(e);
	}
	
	public static void exception(TZException e) {
		MessageSystem.construction().msException(e);
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
	 * @see TZ.System.Construction.MessageSystemConstruction#msRespond(java.lang.String, boolean)
	 */
	@Override
	public void msRespond(String respond, MessageType type, boolean status) {
		if (respond == null) {
			respond = (status ? "\t[" + type + "]" : type.toString());
		} else if (status) {
			respond = "\t[" + type + ": " + respond.toUpperCase() + "]";
		}
		this.msOut(respond);
	}

	/* 
	 * @see TZ.System.Construction.MessageSystemConstruction#msModuleOut(java.lang.String)
	 */
	@Override
	public void msModuleOut(Module module, String out) {
		this.msOut(module.name() + ": " + out);
	}

	/* 
	 * @see TZ.System.Construction.MessageSystemConstruction#msException(java.lang.Exception)
	 */
	@Override
	public void msException(Exception e) {
		System.err.println("MessageSystem:");
		e.printStackTrace();
	}

	/* 
	 * @see TZ.System.Construction.MessageSystemConstruction#msException(TZ.System.Exception.TZException)
	 */
	@Override
	public void msException(TZException e) {
		System.out.println(e.getMessage());
		e.printStackTrace();
		if (e.exception() != null) {
			e.printStackTrace();
		}
	}
	
}

enum MessageType {
	
	OK,
	SUCCESS,
	NOTICE,
	WARNING,
	ERROR, 
	
}

package TZ.Formu;

import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import TZ.Formu.Interfaces.FieldDefine;
import TZ.System.TZSystem;
import TZ.System.Annotations.Info;
import TZ.System.Base.Data.Daton;
import TZ.System.Mechnic.Mechnic;
import TZ.System.Module.Boot;
import TZ.System.Module.Module;

/**
 * 
 * @author terrazero
 * @created Mar 11, 2015
 * 
 * @file Formu.java
 * @project TZS
 * @identifier TZ.Forms
 *
 */
@Info(version = "1.x", compatible = "1.x", name = "Formu", dependencies = {"daton"}, init = "initFormu")
public class Formu {
	
	public static void main(String[] args) {
		TZSystem.execute("formu");
		JFrame frame = new JFrame();
		frame.setBounds(50, 50, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Forms s = new Forms(new Form("test"));
		s.execute();
		frame.getContentPane().setLayout(null);
		JPanel panel = s.steps.get(0).panel();
		frame.add(panel);
		frame.setVisible(true);
	}
	
	private static Map<String, FieldDefine<?>> defines;

	public static void initFormu(String id, Module module, List<Boot> boots) {
		Formu.defines = Mechnic.get("formu", "map");
		
		Boot.forImplement(boots, FieldDefine.class, (boot) -> {
			boot.reflect().instantiate();
			FieldDefine<?> define = boot.reflect().getReflect();
			Formu.defines.put(define.type(), define);
		});
	}
	
	public static void execute(Forms forms) {
		forms.execute();
	}
	
	/**
	 * Build component
	 * @param parent
	 * @param data
	 * @see buildType(String, JComponent, Daton)
	 */
	@SuppressWarnings("unchecked")
	public static<field extends JComponent> field build(JComponent parent, Daton data) {
		String type = "label";
		if (data.has("#type")) {
			type = data.get("#type").value();
		}
		
		// get field define for this type
		FieldDefine<field> define = (FieldDefine<field>)Formu.defines.get(type);
		
		// create the component
		field field = define.create(type, data);
		
		Formu.buildType(type, parent, data, field);
		
		return field;
	}
	
	@SuppressWarnings({"unchecked"})
	public static<field extends JComponent, type> void buildType(String type, JComponent parent, Daton data, field field) {
		// get field define for this type
		FieldDefine<field> define = (FieldDefine<field>)Formu.defines.get(type);
		
		// invoke extends
		if (define.extended() != null) {
			Formu.buildType(define.extended(), parent, data, field);
		}
		
		// invoke field defines
		define.execute(parent, field, data);
		
		// add child to parent
		if (parent != null) {
			parent.add(field);
		}
		
		// build children
		if (!data.has("#executed") || !data.get("#executed").isTrue()) {
			data.forNonData((n, child) -> {
				Formu.build(field, child);
			});
		}
		data.has("#executed", "true");
	}
	
}

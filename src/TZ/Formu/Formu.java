package TZ.Formu;

import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import TZ.Formu.Interfaces.FieldDefine;
import TZ.System.Boot;
import TZ.System.Module;
import TZ.System.TZSystem;
import TZ.System.Annotations.Info;
import TZ.System.Annotations.Functions.InitFunction;
import TZ.System.Base.Data.Daton;
import TZ.System.Lists.Lists;
import TZ.System.Mechnic.Mechnic;

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
@Info(name = "Formu", dependencies = {"Mechnic", "Daton"})
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
		panel.setSize(panel.getWidth(), 50);
		frame.add(panel);
		frame.setVisible(true);
	}
	
	private static Map<String, List<FieldDefine<?>>> defines;

	@InitFunction
	public static void initFormu(String id, Module module, List<Boot> boots) {
		Formu.defines = Mechnic.getContext("formu", "map");
		
		Boot.forImplement(boots, FieldDefine.class, (boot) -> {
			boot.reflect().instantiate();
			FieldDefine<?> define = boot.reflect().getReflect();
			List<FieldDefine<?>> list = Formu.defines.get(define.type());
			
			if (list == null) {
				list = Mechnic.get("list");
				Formu.defines.put(define.type(), list);
			}
			
			list.add(define);
		});
		
		// Sort defines
		Formu.defines.forEach((s, l) -> {
			Lists.sortASC(l);
		});
	}
	
	public static void execute(Forms forms) {
		forms.execute();
	}
	
	/**
	 * Build component
	 * @param parent
	 * @param data
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static<field extends JComponent, type> type build(JComponent parent, Daton data) {
		String type = "label";
		if (data.has("#type")) {
			type = data.get("#type").value();
		}
		
		// get field defines for this type
		List<FieldDefine<field>> define = (List)Formu.defines.get(type);
		
		// create component
		field field = define.get(define.size() - 1).create(type, data);
		
		// invoke field defines
		define.forEach((d) -> {
			d.execute(parent, field, data);
		});
		
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
		return (type)field;
	}
	
}

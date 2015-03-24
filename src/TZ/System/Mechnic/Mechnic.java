package TZ.System.Mechnic;

import java.util.List;

import TZ.System.Annotations.Info;
import TZ.System.Annotations.Base.InfoWrapper;
import TZ.System.Cache.Cache;
import TZ.System.Cache.LimitCache;
import TZ.System.Construction.FileSystem;
import TZ.System.Construction.InstallSystem;
import TZ.System.Construction.MessageSystem;
import TZ.System.Construction.MessageType;
import TZ.System.File.CFid;
import TZ.System.File.InfoFile;
import TZ.System.Module.Boot;
import TZ.System.Module.Module;

/**
 * 
 * @author Terra
 * @created 11.02.2015
 * 
 * @file Mechnik.java
 * @project G7C
 * @identifier TZ.Mechnic
 *
 */
@Info(version = "1.x", compatible = "1.x", install = "installMechnic", module = false)
public class Mechnic {
	
	private static Mechnic mechnic;
	private static InfoFile infofile;
	
	public static InfoFile infofile() { 
		if (Mechnic.infofile == null) {
			Mechnic.infofile = FileSystem.getInfo("user", "mechnic.info");
		} 
		return Mechnic.infofile;
	}
	
	public static void bootMechnic(List<Boot> boots) {
		Mechnic.mechnic = new Mechnic();
		
		Boot.forAnnotations(boots, MCreator.class, (wrapper) -> {
			Mechnic.mechnic.mechnicRegister(wrapper);
		});
		
		MessageSystem.respond(MessageType.SUCCESS);
	}
	
	public static void installMechnic(InfoFile info, Module module, List<Boot> boots) {
		CFid base = InstallSystem.base();
		InfoFile file = new InfoFile(base.cDir("user", "defaults").cFile("mechnic.info"));
		
		Boot.forAnnotations(boots, MCreator.class, (wrapper) -> {
			if (wrapper.info().base()) {
				String key = wrapper.info().mechnic();
				if (wrapper.info().context().length() != 0) {
					key = wrapper.info().context() + ":" + key;
				}
				file.info(key, wrapper.info().option());
			}
		});
		file.save();
		
		MessageSystem.moduleOut(module, "Create module files");
	}
	
	public static<type> type get(String context, String name, Object... args) {
		return Mechnic.mechnic.mechnicGet(context, name, args);
	}
	
	public static<type> MechnicCreator<type> getCreator(String context, String name) {
		return Mechnic.mechnic.mechnicCreator(context, name);
	}

	protected Cache<MechnicCreator<?>> options;
	protected Cache<MechnicCreator<?>> cache;
	
	public Mechnic() {
		this.options = new Cache<MechnicCreator<?>>("mechnic-options");
		this.cache = new LimitCache<MechnicCreator<?>>("mechnic-cache", 128);
	}
	
	protected void mechnicRegister(String option, MechnicCreator<?> creator) {
		this.options.cache(option, creator);
	}
	
	protected void mechnicRegister(InfoWrapper<Boot, MCreator> wrapper) {
		this.mechnicRegister(wrapper.info().option(), wrapper.value().reflect().instantiate().getReflect());
	}
	
	@SuppressWarnings("unchecked")
	public<type> MechnicCreator<type> mechnicCreator(String context, String name) {
		String key = (context == null || context.length() == 0 ? name : context + ":" + name);
		MechnicCreator<?> creator = this.cache.get(key);
		if (creator == null) {
			String option = Mechnic.infofile().info(key);
			if (option == null && context != null && context.length() != 0) {
				creator = this.mechnicCreator(null, name);
				return (MechnicCreator<type>)this.cache.cache(key, creator);
			} else {
				return (MechnicCreator<type>)this.cache.cache(key, this.options.get(option));
			}	
		}
		return (MechnicCreator<type>)creator;
	}
	
	@SuppressWarnings("unchecked")
	protected<type> type mechnicGet(String context, String name, Object... args) {
		return (type)this.mechnicCreator(context, name).mechnic(args);
	}
	
}

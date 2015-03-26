package TZ.System;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import TZ.System.Annotations.Construction;
import TZ.System.Annotations.Base.InfoWrapper;
import TZ.System.Construction.BootSystem;
import TZ.System.Construction.ExitSystem;
import TZ.System.Construction.InitSystem;
import TZ.System.Construction.InstallSystem;
import TZ.System.Construction.MessageSystem;
import TZ.System.Construction.MessageType;
import TZ.System.Exception.ReflectException;
import TZ.System.Exception.TZException;
import TZ.System.File.Fid;
import TZ.System.File.InfoFile;
import TZ.System.Module.Boot;
import TZ.System.Module.Module;
import TZ.System.Module.SysLoader;
import TZ.System.Module.Version;

/**
 * 
 * @author Terra
 * @created 19.02.2015
 * 
 * @file TZSystem.java
 * @project TZS
 * @identifier TZ.System
 *
 */
public class Sys {
	
	public static final Version version = new Version("1.x");
	
	public static void main(String[] args) {
		Sys.execute("test");
	}
	
	private static Sys system;
	
	public static Sys getSystem() {
		if (Sys.system == null) {
			Sys.system = new Sys();
		}
		return Sys.system;
	}

	/**
	 * Invoke the TZS 
	 * 
	 * @param program - the program name
	 * @return IF execute successful THAN NULL ELSE the TZException
	 */
	public static TZException execute(String program) {
		try {
			Sys.getSystem().sysExecute(program);
		} catch (TZException e) {
			return e;
		}
		return null;
	}
	
	public static void exit(int code) {
		Sys.getSystem().sysExit(code);
	}
	
	public static String program() {
		return Sys.getSystem().sysProgram();
	}
	
	public static String machineProgram() {
		return Sys.getSystem().sysMachineProgram();
	}
	
	public static String nameToID(String name) {
		return Sys.getSystem().sysNameToID(name);
	}
	
	public static Module getModule(String name) {
		return Sys.getSystem().sysGetModule(name);
	}
	
	public static<type> type construction(String name) {
		return Sys.getSystem().sysConstruction(name);
	}
	
	public static InfoFile info() {
		return Sys.getSystem().sysInfo();
	}
	
	public static int getTimestamp() {
		return (int) (System.currentTimeMillis() / 1000L);
	}
	
	
	
	private List<Boot> boots;
	private Map<String, InfoWrapper<Boot, Construction>> constructions;
	private List<Module> modules;
	private String program;
	private InfoFile info;
	private LoadState loadstate;
	
	public void sysExecute(String program) {
		try {
			this.program = program;
			this.loadstate = new LoadState(program);
			while (this.loadstate.nextState() != null) {
				switch (this.loadstate.next()) {
					case LoadState.INFO_STATE :
						if (!this.loadstate.dataIs(this.loadstate.current(), "true")) {
							this.loadstate.data(this.loadstate.current(), "true");
							this.sysInfo(this.loadstate);
						}
						break;
					case LoadState.LOAD_STATE :
						if (!this.loadstate.dataIs(this.loadstate.current(), "true")) {
							this.loadstate.data(this.loadstate.current(), "true");
							this.sysLoad(this.loadstate);
						}
						break;
					case LoadState.CONSTRUCTION_STATE :
						if (!this.loadstate.dataIs(this.loadstate.current(), "true")) {
							this.loadstate.data(this.loadstate.current(), "true");
							this.sysConstruction(this.loadstate);
						}
						break;
					case LoadState.BOOT_STATE :
						if (!this.loadstate.dataIs(this.loadstate.current(), "true")) {
							this.loadstate.data(this.loadstate.current(), "true");
							this.sysBoot(this.loadstate);
						}
						break;
					case LoadState.INSTALL_STATE :
						if (!this.loadstate.dataIs(this.loadstate.current(), "true")) {
							this.loadstate.data(this.loadstate.current(), "true");
							this.sysInstall(this.loadstate);
						}
						break;
					case LoadState.INIT_STATE :
						if (!this.loadstate.dataIs(this.loadstate.current(), "true")) {
							this.loadstate.data(this.loadstate.current(), "true");
							this.sysInit(this.loadstate);
						}
						break;
					case LoadState.RUN_STATE :
						if (!this.loadstate.dataIs(this.loadstate.current(), "true")) {
							this.loadstate.data(this.loadstate.current(), "true");
							
						}
						break;
					default :
						MessageSystem.out("Unknown load state '" + this.loadstate.current() + "'", MessageType.ERROR);
						Sys.exit(0);
				}
			}
		} catch (ReflectException e) {
			Exception re = e.exception();
			if (re != null) {
				re.printStackTrace();
			} else {
				e.printStackTrace();
			}
		}
	}
	
	public String[] sysInfoSearch(String base, String user, String program) {
		return new String[] {
			base + "/" + program + ".info",
			base + "/" + program + "/" + program + ".info",
			user + "/" + program + ".info",
			user + "/" + program + "/" + program + ".info",
		};
	}
	
	public String[] sysInfoSearchFile() {
		return new String[] {
			"construction",
			"module",
			"mechnic",
		};
	}
	
	public void sysInfo(LoadState state) {
		String base = state.data("default-path", SysLoader.defaultPath());
		String program = state.data("program");
		String user = state.data("user-path", System.getProperty("user.home"));
		state.data("start-time", Sys.getTimestamp() + "");
		
		Fid fid = Fid.search(this.sysInfoSearch(base, user, program));
		
		if (fid != null) {
			this.info = new InfoFile(fid);
			state.setInfoFile(info.info());
			this.sysInfos(state, base);
			state.data("new", "false");
		} else {
			state.data("new", "true");
		}
	}
	
	public void sysInfos(LoadState state, String base) {
		for (String name : this.sysInfoSearchFile()) {
			Fid defaultFile = Fid.search(base + "/user/default/" + name + ".info");
			if (defaultFile.isExist()) {
				InfoFile info = new InfoFile(defaultFile, Fid.search(base + "/user/" + name + ".info"));
				state.infos(name, info.info());
			}
		}
	}
	
	public void sysLoad(LoadState state) {
		if (state.dataIs("new", "false")) {
			String base = state.info().get("base-path");
			
			for (String name : this.sysInfoSearchFile()) {
				Fid constructions = new Fid(base, name + "s");
				if (constructions.file().exists() && constructions.file().isDirectory()) {
					for (File file : constructions.file().listFiles()) {
						if (file.isFile() && file.getName().endsWith(".jar")) {
							SysLoader.addLoaderSource(file);
						}
					}
				}
			}
		}
		this.boots = SysLoader.sysloader().load();
	}
	
	public void sysConstruction(LoadState state) {
		this.constructions = new HashMap<String, InfoWrapper<Boot, Construction>>();
		
		if (state.dataIs("new", "true")) {
			Boot.forAnnotations(this.boots, Construction.class, (wrapper) -> {
				if (wrapper.info().system()) {
					this.constructions.put(wrapper.info().type(), wrapper);
				}
			});
		} else {
			Map<String, String> info = state.infos("construction");
			Boot.forAnnotations(this.boots, Construction.class, (wrapper) -> {
				String name = info.get(wrapper.info().type());
				
				if (name == null && wrapper.info().system() || name != null && name.equals(wrapper.info().name())) {
					this.constructions.put(wrapper.info().type(), wrapper);
				}
			});
		}
	}
	
	public void sysBoot(LoadState state) {
		this.modules = BootSystem.bootModules(state, this.boots);
		BootSystem.activeModule(state, this.modules);
		BootSystem.bootModulesSort(state, this.modules);
		this.modules = BootSystem.bootModulesDependencies(state, this.modules);
		BootSystem.booting(state, this.modules, this.boots);
	}
	
	public void sysInstall(LoadState state) {
		if (state.dataIs("new", "true")) {
			InstallSystem.install(state, this.modules, this.boots);
			InstallSystem.installProfile(state, this.modules, this.boots);
			InstallSystem.installSystem(state, this.modules, this.boots);
			InstallSystem.installComplete(state, this.modules, this.boots);
		}
	}
	
	public void sysInit(LoadState state) {
		InitSystem.initing(state, this.modules, this.boots);
	}
	
	public void sysRun(LoadState state) {
		// TODO run system
	}
	
	public void sysExit(int code) {
		ExitSystem.exiting(this.loadstate, code, this.modules, this.boots);
		ExitSystem.exit(code);
	}
	
	public void develOut(List<Module> modules) {
		for (Module module : modules) {
			MessageSystem.out(module.name());
		}
	}
	
	public String sysProgram() {
		return this.program;
	}
	
	public String sysMachineProgram() {
		return this.sysNameToID(this.program);
	}
	
	public String sysNameToID(String name) {
		return name.replaceAll(" ", "-").toLowerCase();
	}
	
	@SuppressWarnings("unchecked")
	public<type> type sysConstruction(String name) {
		return (type)this.constructions.get(name).value().reflect().instantiate().getReflect();
	}
	
	public InfoFile sysInfo() {
		return this.info;
	}
	
	public Module sysGetModule(String name) {
		for (Module module : this.modules) {
			if (module.id().equals(name)) {
				return module;
			}
		}
		return null;
	}
	
}

package TZ.System.Module;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import TZ.System.Exception.LoaderException;

/**
 * 
 * @author Terra
 * @created 13.02.2015
 * 
 * @file BootLoader.java
 * @project G7C
 * @identifier TZ.Reflect.Boot
 *
 */
public class SysLoader {
	
	private static SysLoader sysloader;
	private static URLClassLoader loader;
	private static Method addMethod;
	private static URL defaultURL;
	
	public static SysLoader sysloader() {
		if (SysLoader.sysloader == null) {
			SysLoader.sysloader = new SysLoader();
		}
		return SysLoader.sysloader;
	}
	
	public static URLClassLoader loader() {
		if (SysLoader.loader == null) {
			SysLoader.loader = (URLClassLoader)ClassLoader.getSystemClassLoader();
			SysLoader.defaultURL = SysLoader.loader.getURLs()[0];
		}
		return SysLoader.loader;
	}
	
	public static void addLoaderSource(File file) {
		try {
			SysLoader.addLoaderSource(file.toURI().toURL());
		} catch (MalformedURLException e) {
			throw new LoaderException(e, "Couldn't transform file to URL", "Couldn't transform file to URL");
		}
	}
	
	public static void addLoaderSource(URL url) {
		if (SysLoader.addMethod == null) {
			try {
				SysLoader.addMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new LoaderException(e, "Couldn't get method addURL of class loader", "Couldn't get method addURL of class loader");
			}
			SysLoader.addMethod.setAccessible(true);
		}
		try {
			SysLoader.addMethod.invoke(SysLoader.loader(), new Object[] {url});
		} catch (Exception e) {
			throw new LoaderException(e, "Couldn't add URL to class loader", "Couldn't add URL to class loader");
		}
	}
	
	public static URL defaultURL() {
		if (SysLoader.defaultURL == null) {
			SysLoader.loader();
		}
		return SysLoader.defaultURL;
	}
	
	public static String defaultPath() {
		return SysLoader.defaultURL().getPath();
	}
	
	protected List<Boot> boots;
	
	private SysLoader() {
		
	}
	
	public List<Boot> load() {
		return this.load(false);
	}
	
	public List<Boot> load(boolean force) {
		if (force || this.boots == null) {
			this.init();
		}
		return this.boots;
	}
	
	public void init() {
		this.boots = new ArrayList<Boot>(1024);
		try {
			for (URL url : SysLoader.loader().getURLs()) {
				String file = url.getFile();
				
				if (file.endsWith(".jar")) {
					ZipInputStream zip = new ZipInputStream(url.openStream());
					this.loadZip(this.boots, zip);
					zip.close();
				} else {
					this.loadFile(this.boots, file, "");
				}
			}
		} catch (Exception e) {
			throw new LoaderException(e, "Unexpected Exception in SysLoader", "Unexpected Exception in SysLoader");
		}
	}
	
	public void loadZip(List<Boot> boots, ZipInputStream zip) throws IOException {
		ZipEntry entry = null;
		
		while ((entry = zip.getNextEntry()) != null) {
			if (entry.getName().endsWith(".class")) {
				boots.add(new Boot(entry));
			}
		}
	}
	
	public void loadFile(List<Boot> boots, String path, String internpath) {
		for (File f : new File(path).listFiles()) {
			if (f.isDirectory()) {
				this.loadFile(boots, path + "/" + f.getName(), internpath + "/" + f.getName());
			} else if (f.isFile() && f.getName().endsWith(".class")) {
				boots.add(new Boot(f, (internpath.length() == 0 ? "" : internpath.substring(1))));
			}
		}
	}
	
}

package TZ.System.Module;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JOptionPane;

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
public class TZSystemLoader {
	
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, TZSystemLoader.defaultPath());
	}
	
	private static URLClassLoader loader;
	private static Method addMethod;
	private static URL defaultURL;
	
	public static URLClassLoader loader() {
		if (TZSystemLoader.loader == null) {
			TZSystemLoader.loader = (URLClassLoader)ClassLoader.getSystemClassLoader();
			TZSystemLoader.defaultURL = TZSystemLoader.loader.getURLs()[0];
		}
		return TZSystemLoader.loader;
	}
	
	public static void addLoaderSource(File file) {
		try {
			TZSystemLoader.addLoaderSource(file.toURI().toURL());
		} catch (MalformedURLException e) {
			throw new LoaderException(e, "Couldn't transform file to URL", "Couldn't transform file to URL");
		}
	}
	
	public static void addLoaderSource(URL url) {
		if (TZSystemLoader.addMethod == null) {
			try {
				TZSystemLoader.addMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new LoaderException(e, "Couldn't get method addURL of class loader", "Couldn't get method addURL of class loader");
			}
			TZSystemLoader.addMethod.setAccessible(true);
		}
		try {
			TZSystemLoader.addMethod.invoke(TZSystemLoader.loader(), new Object[] {url});
		} catch (Exception e) {
			throw new LoaderException(e, "Couldn't add URL to class loader", "Couldn't add URL to class loader");
		}
	}
	
	public static URL defaultURL() {
		if (TZSystemLoader.defaultURL == null) {
			TZSystemLoader.loader();
		}
		return TZSystemLoader.defaultURL;
	}
	
	public static String defaultPath() {
		return TZSystemLoader.defaultURL().getPath();
	}
	
	protected List<Boot> boots;
	
	public List<Boot> boots() {
		if (this.boots == null) {
			this.init();
		}
		return this.boots;
	}
	
	public String[] getSystemPaths() {
		// TODO load from "boot.tzs.txt" 
		String[] sp = {"TZ"};
		return sp;
	}
	
	public void init() {
		this.boots = new ArrayList<Boot>(1024);
		try {
			for (String systempath : this.getSystemPaths()) {
				Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources(systempath);
				while (resources.hasMoreElements()) {
					String path = resources.nextElement().getFile();
					System.out.println(path);
					
					if (path.startsWith("file:")) {
						String[] location = this.getLocation(path);
						ZipInputStream zip = new ZipInputStream(new URL(location[0]).openStream());
						this.loadZipItem(zip, this.boots, systempath);
						zip.close();
					} else {
						this.loadFileItem(this.boots, path, systempath);
					}
				}
			}
		} catch (IOException e) {
			throw new LoaderException(e, "Unexpected Exception in BootLoader", "Unexpected Exception in BootLoader");
		}
	}
	
	public void loadZipItem(ZipInputStream zip, List<Boot> boots, String internpath) throws IOException {
		ZipEntry entry = null;
		
		while ((entry = zip.getNextEntry()) != null) {
			if (entry.getName().endsWith(".class")) {
				boots.add(new Boot(entry));
			}
		}
	}
	
	public String getEntryName(ZipEntry entry) {
		String[] parts = entry.getName().split("/");
		String name = parts[parts.length - 1];
		return Module.getNameFromFile(name);
	}
	
	public String getInternpath(ZipEntry entry) {
		String[] parts = entry.getName().split("/");
		
		return entry.getName().substring(0, entry.getName().length() - parts[parts.length - 1].length() - 1);
	}
	
	public void loadFileItem(List<Boot> boots, String path, String internpath) {
		for (File f : new File(path).listFiles()) {
			if (f.isDirectory()) {
				this.loadFileItem(boots, path + "/" + f.getName(), internpath + "/" + f.getName());
			} else if (f.isFile() && f.getName().endsWith(".class")) {
				boots.add(new Boot(f, internpath));
			}
		}
	}
	
	public String[] getLocation(String path) {
		return path.split("!");
	}
	
}

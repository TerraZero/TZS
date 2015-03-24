package TZ.System.Module;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import TZ.System.Exception.BootLoaderException;

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
		this.invokes();
		this.boots = new ArrayList<Boot>(1024);
		try {
			for (String systempath : this.getSystemPaths()) {
				Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources(systempath);
				while (resources.hasMoreElements()) {
					String path = resources.nextElement().getFile();
					
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
			throw new BootLoaderException(e, "Unexpected Exception in BootLoader", "Unexpected Exception in BootLoader");
		}
	}
	
	public void invokes() {
		for (File file : new File(new File("").getAbsolutePath()).listFiles()) {
			System.out.println(file.getAbsolutePath());
			if (file.isFile() && file.canRead() && file.getName().endsWith(".jar")) {
				try {
					InvokeLoader.addFile(file);
				} catch (IOException e) {
					System.out.println("exception");
					e.printStackTrace();
				}
			}
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

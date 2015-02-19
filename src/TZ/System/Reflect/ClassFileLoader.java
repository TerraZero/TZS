package TZ.System.Reflect;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import TZ.System.Base.Strings;
import TZ.System.Cache.Cache;

/**
 * 
 * @author terrazero
 * @created Feb 12, 2015
 * 
 * @file ClassFileLoader.java
 * @project G7C
 * @identifier TZ.Reflect
 *
 */
public class ClassFileLoader {
	
	private static ClassFileLoader singleton;
	
	public static ClassFileLoader getLoader() {
		if (ClassFileLoader.singleton == null) {
			ClassFileLoader.singleton = new ClassFileLoader();
		}
		return ClassFileLoader.singleton;
	}
	
	protected Cache<List<ClassFile>> cache;
	
	private ClassFileLoader() {
		this.cache = new Cache<List<ClassFile>>("classfile");
	}
	
	public List<ClassFile> load(String load) throws IOException {
		return this.load(load, false);
	}
	
	public List<ClassFile> load(String load, boolean reset) throws IOException {
		String id = this.getID(load);
		String path = this.getPath(load);
		List<ClassFile> list = this.cache.get(path);
		
		if (list == null || reset) {
			Enumeration<URL> res = this.getResources(path);
			list = this.createList();
			
			while (res.hasMoreElements()) {
				URL resource = res.nextElement();
				list.addAll(this.loadItems(resource.getFile(), id, path));
			}
			this.cache.cache(path, list);
		}
		return list;
	}
	
	protected List<ClassFile> loadItems(String dir, String id, String path) throws MalformedURLException, IOException {
		List<ClassFile> items = this.createList();
		
		if (dir.startsWith("file:")) {
			String[] location = this.getLocation(dir);
			ZipInputStream zip = this.getZipStream(location[0]);
			int deep = Strings.countChar(location[1], '/') + 1;
			
			location[1] = location[1].substring(1);
			ZipEntry entry = null;
			while ((entry = zip.getNextEntry()) != null) {
				if (entry.getName().startsWith(location[1]) && deep == Strings.countChar(entry.getName(), '/') && entry.isDirectory()) {
					if (entry.isDirectory()) {
						items.add(new ClassFile(id, this.getZipName(entry), ClassFileType.PACKAGE));
					} else if (entry.getName().endsWith(".class")) {
						items.add(new ClassFile(id, this.getZipName(entry), ClassFileType.CLASS));
					}
				}
			}
		} else {
			File[] files = new File(dir).listFiles();
			StringBuilder string = new StringBuilder(id).append('.');
			int length = id.length() + 1;
			
			for (File file : files) {
				string.setLength(length);
				string.append(file.getName());
				if (file.isDirectory()) {
					items.add(new ClassFile(id, string.toString(), ClassFileType.PACKAGE));
				} else if (file.isFile() && file.getName().endsWith(".class")) {
					items.add(new ClassFile(id, string.toString(), ClassFileType.CLASS));
				}
			}
		}
		return items;
	}
	
	protected String getZipName(ZipEntry entry) {
		return entry.getName().substring(0, entry.getName().length() - 1).replace('/', '.');
	}
	
	protected String[] getLocation(String dir) {
		String[] location = dir.split("!");
		
		location[1] = location[1].substring(1);
		return location;
	}
	
	protected ZipInputStream getZipStream(String location) throws MalformedURLException, IOException {
		return new ZipInputStream(new URL(location).openStream());
	}
	
	protected String getPath(String load) {
		return load.replace('.', '/');
	}
	
	protected String getID(String load) {
		return load.replace('/', '.');
	}
	
	public Enumeration<URL> getResources(String path) throws IOException {
		return ClassLoader.getSystemClassLoader().getResources(path);
	}
	
	protected List<ClassFile> createList() {
		return new ArrayList<ClassFile>();
	}
		
}

package TZ.System.Reflect.Boot;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JOptionPane;

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
@Boot(weight=-5)
public class BootLoader {
	
	public static void main(String[] args) {
		BootLoader l = new BootLoader();
		List<BootFile> boots = l.boots();
		BootLoader.out(boots, "");
		JOptionPane.showMessageDialog(null, "ende");
	}
	
	public static void out(List<BootFile> boots, String tab) {
		for (BootFile boot : boots) {
			test(tab + boot.file());
		}
	}
	
	private static String[] test;
	private static int count;
	
	public static void test(String out) {
		if (test == null) {
			test = new String[10];
			count = 0;
		}
		if (count == test.length) {
			count = 0;
			String tout = "";
			for (int i = 0; i < test.length; i++) {
				tout += test[i] + "\n";
			}
			JOptionPane.showMessageDialog(null, tout);
		}
		test[count++] = out;
	}
	
	protected List<BootFile> boots;
	
	public List<BootFile> boots() {
		if (this.boots == null) {
			this.init();
		}
		return this.boots;
	}
	
	public String[] getSystemPaths() {
		String[] sp = {"TZ"};
		return sp;
	}
	
	public void init() {
		this.boots = new ArrayList<BootFile>(1024);
		try {
			for (String systempath : this.getSystemPaths()) {
				Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources(systempath);
				while (resources.hasMoreElements()) {
					String path = resources.nextElement().getFile();
					if (path.startsWith("file:")) {
						String[] location = this.getLocation(path);
						ZipInputStream zip = new ZipInputStream(new URL(location[1]).openStream());
						this.loadZipItem(zip, this.boots, systempath);
						zip.close();
					} else {
						this.loadFileItem(this.boots, path, systempath);
					}
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void loadZipItem(ZipInputStream zip, List<BootFile> boots, String internpath) throws IOException {
		ZipEntry entry = null;
		
		while ((entry = zip.getNextEntry()) != null) {
			if (entry.getName().endsWith(".class")) {
				boots.add(new BootFile(this.getEntryName(entry), this.getInternpath(entry)));
			}
		}
	}
	
	public String getEntryName(ZipEntry entry) {
		return entry.getName();
	}
	
	public String getInternpath(ZipEntry entry) {
		return entry.getName();
	}
	
	public void loadFileItem(List<BootFile> boots, String path, String internpath) {
		for (File f : new File(path).listFiles()) {
			if (f.isDirectory()) {
				this.loadFileItem(boots, path + "/" + f.getName(), internpath + "/" + f.getName());
			} else if (f.isFile() && f.getName().endsWith(".class")) {
				boots.add(new BootFile(BootFile.getNameFromFile(f.getName()), internpath));
			}
		}
		/*
		//iterativ
		Queue<File> dirs = new LinkedList<File>();
		Queue<BootFile> boots = new LinkedList<BootFile>();
	    dirs.add(new File(path));
	    boots.add(item);
	    String relPath = path.substring(0, path.length() - internpath.length());

	    while (dirs.size() > 0) {
	    	BootFile bf = boots.remove();
	    	for (File file : dirs.remove().listFiles()) {
    			String p = file.getParent().substring(relPath.length());
	    		if (file.isDirectory()) {
	    			dirs.add(file);
	    			BootFile bfdir = new BootFile(file.getName(), false, p);
	    			bf.add(bfdir.name(), bfdir);
	    			boots.add(bfdir);
	    		} else {
	    			BootFile bffile = new BootFile(file.getName(), true, p);
	    			bf.add(bffile.name(), bffile);
	    		}
	    	}
	    }
	    //*/
	     /*
	     //recursiv
		for (File f : new File(path).listFiles()) {
			if (f.isDirectory()) {
				BootFile dir = new BootFile(f.getName(), false, internpath);
				item.add(dir.name(), dir);
				this.loadFileItem(dir, path + "/" + f.getName(), internpath + "/" + f.getName());
			} else if (f.isFile() && f.getName().endsWith(".class")) {
				BootFile file = new BootFile(f.getName(), true, internpath);
				item.add(file.name(), file);
			}
		}
		//*/
	}
	
	public String[] getLocation(String dir) {
		String[] location = dir.split("!");
		
		location[1] = location[1].substring(1);
		return location;
	}
	
}

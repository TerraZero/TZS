package TZ.System.Reflect.Boot;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.swing.JOptionPane;

import TZ.System.Base.Strings;
import TZ.System.Reflect.Reflect;

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
public class BootLoader {
	
	public static void main(String[] args) {
		BootLoader l = new BootLoader();
		BootFile bf = l.root();
		BootLoader.out(bf, "");
		JOptionPane.showMessageDialog(null, "ende");
	}
	
	public static void out(BootFile file, String tab) {
		file.contains().forEach((s, bf) -> {
			//test(tab + bf.file());
			System.out.println(tab + bf.file());
			BootLoader.out(bf, tab + "-|");
		});
	}
	
	private static String[] test;
	private static int count;
	
	public static void test(String out) {
		if (test == null) {
			test = new String[10];
			count = 0;
		}
		count++;
		if (count == test.length) {
			count = 0;
			String tout = "";
			for (int i = 0; i < test.length; i++) {
				tout += test[i] + "\n";
			}
			JOptionPane.showMessageDialog(null, tout);
		}
		test[count] = out;
	}
	
	protected BootFile root;
	
	public BootFile root() {
		if (this.root == null) {
			this.init();
		}
		return this.root;
	}
	
	public void boot() {
		BootFile root = this.root();
		this.booting(root);
	}
	
	public void booting(BootFile item) {
		if (item.isClass()) {
			Reflect r = new Reflect(item.id());
			if (r.hasAnnotation(Booter.class)) {
				Booter boot = r.annotation(Booter.class);
				System.out.println(boot.name());
			}
		} else {
			item.contains().forEach((n, bf) -> this.booting(bf));
		}
	}
	
	public String[] getSystemPaths() {
		String[] sp = {"TZ"};
		return sp;
	}
	
	public void init() {
		this.root = new BootFile();
		try {
			for (String systempath : this.getSystemPaths()) {
				Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources(systempath);
				while (resources.hasMoreElements()) {
					String path = resources.nextElement().getFile();
					if (path.startsWith("file:")) {
						String[] location = this.getLocation(path);
						ZipInputStream zip = new ZipInputStream(new URL(location[1]).openStream());
						this.loadZipItem(zip, this.root, path, systempath);
						zip.close();
					} else {
						this.loadFileItem(this.root, path, systempath);
					}
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	@SuppressWarnings("resource")
	public void loadZipItem(ZipInputStream zip, BootFile root, String path, String internpath) throws IOException {
		// TODO
		ZipFile zfile = new ZipFile(new File(path));
		Enumeration<? extends ZipEntry> entries = zfile.entries();
		int deep = 0;
		BootFile item = root;
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			int entryDeep = Strings.countChar(entry.getName(), '/');
			for (int i = deep - entryDeep; i >= 0; i--) {
				item = item.parent();
			}
			if (entry.isDirectory()) {
				BootFile dir = new BootFile(this.getEntryName(entry), false, this.getInternpath(entry));
				item.add(dir.name(), dir);
				item = dir;
			} else {
				BootFile file = new BootFile(this.getEntryName(entry), true, this.getInternpath(entry));
				item.add(file.name(), file);
			}
			deep = entryDeep;
		}
	}
	
	public String getEntryName(ZipEntry entry) {
		return entry.getName();
	}
	
	public String getInternpath(ZipEntry entry) {
		return entry.getName();
	}
	
	public void loadFileItem(BootFile item, String path, String internpath) {
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
	    /* recursiv
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

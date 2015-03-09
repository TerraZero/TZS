package TZ.System;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.zip.ZipEntry;

import TZ.System.Reflect.Reflect;

/**
 * 
 * @author terrazero
 * @created Mar 9, 2015
 * 
 * @file Boot.java
 * @project TZS
 * @identifier TZ.System
 *
 */
public class Boot {
	
	public static String getZipName(String zipname) {
		String[] parts = zipname.split("/");
		String name = parts[parts.length - 1];
		
		return name.substring(0, name.length() - 6);
	}
	
	public static String getZipPath(String zipname) {
		String[] parts = zipname.split("/");
		
		return zipname.substring(0, zipname.length() - parts[parts.length - 1].length() - 1);
	}
	
	public static String getFileName(String filename) {
		return filename.substring(0, filename.length() - 6);
	}
	
	
	
	public static<annot extends Annotation> void forAnnotation(List<Boot> boots, Class<annot> annotation, BiConsumer<Boot, annot> consumer) {
		for (Boot boot : boots) {
			annot annot = boot.reflect().getAnnotation(annotation);
			if (annot != null) {
				consumer.accept(boot, annot);
			}
		}
	}
	
	

	protected String name;
	protected String path;
	protected Reflect reflect;
	
	// source
	protected ZipEntry entry;
	protected File file;
	
	public Boot(ZipEntry entry) {
		this.entry = entry;
		this.name = Boot.getZipName(entry.getName());
		this.path = Boot.getZipPath(entry.getName());
	}
	
	public Boot(File file, String path) {
		this.file = file;
		this.name = Boot.getFileName(file.getName());
		this.path = path;
	}
	
	public boolean isZip() {
		return this.entry != null;
	}
	
	public boolean isFile() {
		return this.file != null;
	}
	
	public String name() {
		return this.name;
	}
	
	public String id() {
		String file = this.path + "/" + this.name;
		return file.replace('/',  '.');
	}
	
	public Reflect reflect() {
		if (this.reflect == null) {
			this.reflect = new Reflect(this.id());
		}
		return this.reflect;
	}
	
}

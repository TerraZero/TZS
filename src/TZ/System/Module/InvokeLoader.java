package TZ.System.Module;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import TZ.System.Reflect.Reflect;

/**
 * 
 * @author terrazero
 * @created Mar 24, 2015
 * 
 * @file InvokeLoader.java
 * @project TZS
 * @identifier TZ.System.Module
 *
 */
public class InvokeLoader {

    /**
     * Adds a file to the classpath.
     * @param s a String pointing to the file
     * @throws IOException
     */
    public static void addFile(String s) throws IOException {
        InvokeLoader.addFile(new File(s));
    }

    /**
     * Adds a file to the classpath
     * @param f the file to be added
     * @throws IOException
     */
    public static void addFile(File f) throws IOException {
        InvokeLoader.addURL(f.toURI().toURL());
    }

    /**
     * Adds the content pointed by the URL to the classpath.
     * @param u the URL pointing to the content to be added
     * @throws IOException
     */
    public static void addURL(URL u) throws IOException {
        URLClassLoader sysloader = (URLClassLoader)ClassLoader.getSystemClassLoader();
        Class<?> sysclass = URLClassLoader.class;
        try {
            Method method = sysclass.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(sysloader, new Object[] {u}); 
            Reflect r = new Reflect("TZ.Test");
            System.out.println(r.reflect());
            r.call("test", "sadhgd");
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Error, could not add URL to system classloader");
        }  
    }
	
}

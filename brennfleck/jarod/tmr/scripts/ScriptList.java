package brennfleck.jarod.tmr.scripts;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;

public class ScriptList {
	public static ArrayList<Class<? extends Script>> scriptList;
	
	@SuppressWarnings("resource")
	public static void getAllScripts() {
		try {
			File file = new File(Minecraft.getMinecraft().tmrDataDir, "scripts");
			URL url = file.toURI().toURL();
			ClassLoader classLoader = new URLClassLoader(new URL[]{url});
			for(String potentialClass : file.list()) {
				if(potentialClass.endsWith(".class")) {
					Class clazz = classLoader.loadClass(potentialClass.substring(0, potentialClass.lastIndexOf('.')));
					if(clazz.getSuperclass() == Script.class) addScript((Class<? extends Script>) clazz);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void addScript(Class<? extends Script> script) {
		if(script.getAnnotation(ScriptManifest.class) != null) scriptList.add(script);
	}
	public static int size() {
		return scriptList.size();
	}
	public static String[] getScriptManifest(int index) {
		ScriptManifest sm = scriptList.get(index).getAnnotation(ScriptManifest.class);
		return new String[] {sm.name(), sm.author(), sm.version(), sm.category()};
	}
	public static Script getInstantiatedScript(int index) {
		try {
			return scriptList.get(index).newInstance();
		} catch(InstantiationException e) {
			e.printStackTrace();
		} catch(IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void reset() {
		ScriptList.scriptList = new ArrayList<Class<? extends Script>>();
	}
}
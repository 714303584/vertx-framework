package com.ifreeshare.framework;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ifreeshare.framework.annotation.Controller;
import com.ifreeshare.util.FileAccess;
import com.ifreeshare.util.SystemUtil;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		System.out.println(SystemUtil.getUserDir());

		String pkg = "com.ifreeshare.framework.web.controller";

		String path = pkg.replace(".", "/");

		System.out.println(path);

		String[] libs = SystemUtil.getLibsPath();

		for (int i = 0; i < libs.length; i++) {
			String filePath = libs[i];
			// System.out.println(string);
			File file = new File(filePath);
			if (file.isDirectory()) {
				String controllerPath = filePath + "/" + path;
				File pkgDir = new File(controllerPath);
				System.out.println(controllerPath);
				List<File> list = new ArrayList<File>();
				FileAccess.getFiles(pkgDir, list, "class");
				Iterator<File> it = list.iterator();
				while (it.hasNext()) {
					File clazzFile = it.next();
					String absolutePath = clazzFile.getAbsolutePath().replace("\\", "/");
					String cpath = absolutePath.split(path)[1];
					String classpath = cpath.split("\\.")[0];
					String className = pkg + classpath.replace("/", ".");
					try {
						Class clazz = Class.forName(className);
						
						 Object obj = clazz.newInstance();
						 Controller conTag = obj.getClass().getAnnotation(Controller.class);
						 
//						 Controller.class.getField("value");
						 String controllName = conTag.value();
						 
						 System.out.println(controllName);
//						for (int j = 0; j < anns.length; j++) {
//							Annotation annotation = anns[j];
//						}
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (InstantiationException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}

				}

			}

		}

		// String enginesUrl = "";
		//
		// URLClassLoader classLoader = new URLClassLoader(new URL[]{new
		// URL(enginesUrl) }, Thread.currentThread().getContextClassLoader());

	}
}

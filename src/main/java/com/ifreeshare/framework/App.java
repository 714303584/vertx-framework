package com.ifreeshare.framework;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import com.ifreeshare.framework.annotation.Controller;
import com.ifreeshare.framework.web.annotation.RequestMapping;
import com.ifreeshare.framework.web.annotation.RequestMethod;
import com.ifreeshare.util.ReflectUtil;
import com.ifreeshare.util.SystemUtil;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {

		System.out.println(SystemUtil.getUserDir());

		String pkg = "com.ifreeshare.framework.web.controller";
//
//		String path = pkg.replace(".", "/");
//
//		System.out.println(path);
//
//		String[] libs = SystemUtil.getLibsPath();
		
		
		Vertx vertx = Vertx.vertx();
		HttpServer httpServer = vertx.createHttpServer();
		
		Router router = Router.router(vertx);
		
		HttpServerShell shell = new HttpServerShell(httpServer, router, pkg);
		
		shell.initRouter();
		
		httpServer.requestHandler(router::accept).listen(15888);
		
		
		
		
		

//		for (int i = 0; i < libs.length; i++) {
//			String filePath = libs[i];
//			// System.out.println(string);
//			File file = new File(filePath);
//			if (file.isDirectory()) {
//				String controllerPath = filePath + "/" + path;
//				File pkgDir = new File(controllerPath);
//				System.out.println(controllerPath);
//				List<File> list = new ArrayList<File>();
//				FileAccess.getFiles(pkgDir, list, "class");
//				Iterator<File> it = list.iterator();
//				while (it.hasNext()) {
//					File clazzFile = it.next();
//					String absolutePath = clazzFile.getAbsolutePath().replace("\\", "/");
//					String cpath = absolutePath.split(path)[1];
//					String classpath = cpath.split("\\.")[0];
//					String className = pkg + classpath.replace("/", ".");
//					try {
//						Class clazz = Class.forName(className);
//						Object obj = clazz.newInstance();
//						Controller conTag = obj.getClass().getAnnotation(Controller.class);
//
//						// Controller.class.getField("value");
//						String controllName = conTag.value();
//
//						System.out.println(controllName);
//						// for (int j = 0; j < anns.length; j++) {
//						// Annotation annotation = anns[j];
//						// }
//
//					} catch (ClassNotFoundException e) {
//						e.printStackTrace();
//					} catch (InstantiationException e) {
//						// TODO 自动生成的 catch 块
//						e.printStackTrace();
//					} catch (IllegalAccessException e) {
//						// TODO 自动生成的 catch 块
//						e.printStackTrace();
//					}
//
//				}
//
//			} else {
//				String jarName = file.getName();
//				
////				if ("jar".equals(FileAccess.getFileType(jarName))) {
////					try {
////						
////						while (it.hasNext()) {
////							System.out.println("+++"+it.next().getName());	
////						}
////					} catch (ClassNotFoundException e) {
////						e.printStackTrace();
////					} catch (IOException e) {
////						e.printStackTrace();
////					}
////
////				}
//			}
//
//		}
//			String pakg = "com.ifreeshare.framework.web.controller";
//			
//			ClassLoader classLoader = 
//					Thread.currentThread().getContextClassLoader();
//			
//			List<Class> classes;
//			try {
//				classes = getClasses(pakg);
//			} catch (ClassNotFoundException e) {
//				// TODO 自动生成的 catch 块
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO 自动生成的 catch 块
//				e.printStackTrace();
//			}

		// String enginesUrl = "";
		//
		// URLClassLoader classLoader = new URLClassLoader(new URL[]{new
		// URL(enginesUrl) }, Thread.currentThread().getContextClassLoader());

	}
}

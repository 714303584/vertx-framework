package com.ifreeshare.framework;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import com.ifreeshare.framework.annotation.Controller;
import com.ifreeshare.framework.web.annotation.RequestMapping;
import com.ifreeshare.framework.web.annotation.RequestMethod;
import com.ifreeshare.util.ReflectUtil;

/**
 * Web server shell
 * 
 * @author zhuss
 */
public class HttpServerShell {

	public String CONTROLLER_PACKAGE = "com.ifreeshare.spider.http.server.controller";

	private HttpServer httpServer;

	private Router router;

	// Template Engine (freemarker)
	FreeMarkerTemplateEngine freeMarkerTemplateEngine  = FreeMarkerTemplateEngine.create();

	public HttpServerShell(HttpServer httpServer, Router router, String packages) {
		this.httpServer = httpServer;
		this.router = router;
		this.CONTROLLER_PACKAGE = packages;
	}
	
	public FreeMarkerTemplateEngine getFreeMarkerTemplateEngine() {
		return freeMarkerTemplateEngine;
	}

	public void setFreeMarkerTemplateEngine(FreeMarkerTemplateEngine freeMarkerTemplateEngine) {
		this.freeMarkerTemplateEngine = freeMarkerTemplateEngine;
	}

	public HttpServer getHttpServer() {
		return httpServer;
	}

	public void setHttpServer(HttpServer httpServer) {
		this.httpServer = httpServer;
	}

	public Router getRouter() {
		return router;
	}

	public void setRouter(Router router) {
		this.router = router;
	}

	// Initialize the route
	public void initRouter() {
		try {
			List<Class> controllers = ReflectUtil.getClasses(CONTROLLER_PACKAGE);

			Iterator<Class> it = controllers.iterator();
			while (it.hasNext()) {
				Class clazz = it.next();
				//
				initRouteByClass(clazz, router);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	public void initRouteByClass(Class clazz, Router router) throws InstantiationException, IllegalAccessException {
		Controller controller = (Controller) clazz.getAnnotation(Controller.class);
		// This is a controller that needs to join the routing monitor
		if (controller != null) {
			if (controller != null) {
				// Reflection Creates an instance of a controller
				Object newInstance = clazz.newInstance();
				// Reflects the path of the controller
				RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
				String[] controllPath = requestMapping.value();
				// Get all the methods
				Method[] methods = clazz.getMethods();
				for (int i = 0; i < methods.length; i++) {
					Method method = methods[i];
					// Get the configuration parameters on the method
					RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
					if (methodMapping == null) {
						continue;
					}
					String[] methodPath = methodMapping.value();
					HttpMethod[] methodRM = methodMapping.method();
					for (int j = 0; j < methodRM.length; j++) {
						HttpMethod requestMethod = methodRM[j];
						router.route(requestMethod, controllPath[0] + methodPath[0]).handler(context -> {
							requestProccess(method, newInstance, context);
						});
					}
				}
				// System.out.println(requestMapping.value());

			}

		}

	}
	
	public void requestProccess(Method method, Object newInstance, RoutingContext context){
		try {
			Object returnValue = method.invoke(newInstance, context);
			if (returnValue != null) {
				HttpServerResponse response = context.response();
				if (returnValue instanceof JsonObject) {
					response.putHeader("Content-Type", "application/json");
					JsonObject returnJson = (JsonObject) returnValue;
					response.end(returnJson.toString());
				} else if (returnValue instanceof String) {
					String returnString = returnValue.toString();
					if(returnString.startsWith("template:")){
						String template = returnString.substring(9);
						freeMarkerTemplateEngine.render(context, template, res -> {
							if (res.succeeded()) {
								context.response().putHeader("content-type", "text/html");
								context.response().end(res.result());
							} else {
								context.fail(res.cause());
							}
						});
					}else if(returnString.startsWith("redirect:")){
						String url = returnString.substring(9);
						 response.putHeader("location", url).setStatusCode(302).end();
					}else{
						response.end(returnString);
					}
				} else{

				}
				// System.out.println(returnValue.getClass().getName());
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

}

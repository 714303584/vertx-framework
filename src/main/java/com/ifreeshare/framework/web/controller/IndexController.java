package com.ifreeshare.framework.web.controller;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

import com.ifreeshare.framework.annotation.Controller;
import com.ifreeshare.framework.web.annotation.RequestMapping;

@Controller("indexController")
@RequestMapping("/")
public class IndexController {
	
	@RequestMapping(value={"index.html"}, method={HttpMethod.GET})
	public void index(RoutingContext context){
		context.response().end("ok");
	}
}

package com.ifreeshare.framework.web.controller;

import io.vertx.ext.web.RoutingContext;

import com.ifreeshare.framework.annotation.Controller;
import com.ifreeshare.framework.web.annotation.RequestMapping;
import com.ifreeshare.framework.web.annotation.RequestMethod;

@Controller("indexController")
@RequestMapping("/")
public class IndexController {
	
	@RequestMapping(value={"index.html"}, method={RequestMethod.GET})
	public void index(RoutingContext context){
		context.response().end("ok");
	}
}

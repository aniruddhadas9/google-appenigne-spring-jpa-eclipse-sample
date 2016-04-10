package com.package.rest.controllers;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/dashboards")
public class DashboardController {
	
	private static final Logger log = Logger.getLogger(DashboardController.class.getName());
	
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String execute(Model model, HttpServletRequest request, HttpServletResponse response) {
		log.info("inside the execute method of the Dashboard class");
		return "home";
		
	}

	public String dashboard() {
		log.info("inside the dashboard method of the Dashboard class");
		return "home";
	}
	
	public String countLdap() {
		log.info("inside the countLdap method of the Dashboard class");
		return "home";
	}
	

}

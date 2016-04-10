package com.package.rest.controllers;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	private static final Logger logger = Logger.getLogger(HomeController.class.getName());
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "login";
	}

	@RequestMapping(value = "/favicon.ico", method = RequestMethod.GET)
	public String favicon(Locale locale, Model model) {
		logger.info("HomeController|favicon|favicon.ioc mapping called");
		return "login";
	}
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(Locale locale, Model model) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		return "index";
	}
}

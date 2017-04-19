package com.marsbase.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.marsbase.springboot.model.StatusUpdate;
import com.marsbase.springboot.service.StatusUpdateService;

@Controller
public class PageController {

	@Autowired
	private StatusUpdateService statusUpdateService;

	@RequestMapping("/")
	public String home() {
		return "app.homepage";
	}

	@RequestMapping("/about")
	public String about() {
		return "app.about";
	}

	@RequestMapping(value = "/addstatus", method = RequestMethod.GET)
	public ModelAndView addStatus(ModelAndView modelAndView,
			@ModelAttribute("statusUpdate") StatusUpdate statusUpdate) {
		modelAndView.setViewName("app.addStatus");

		StatusUpdate latestUpdate = statusUpdateService.getLatest();

		modelAndView.getModel().put("latestUpdate", latestUpdate);

		return modelAndView;
	}

	@RequestMapping(value = "/addstatus", method = RequestMethod.POST)
	public ModelAndView addStatus(ModelAndView modelAndView, @ModelAttribute("statusUpdate") StatusUpdate statusUpdate,
			String temp) {
		modelAndView.setViewName("app.addStatus");

		statusUpdateService.save(statusUpdate);

		StatusUpdate latestUpdate = statusUpdateService.getLatest();

		modelAndView.getModel().put("latestUpdate", latestUpdate);

		return modelAndView;
	}

}

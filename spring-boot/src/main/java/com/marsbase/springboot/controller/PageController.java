package com.marsbase.springboot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

		StatusUpdate latestUpdate = statusUpdateService.getLatest();

		modelAndView.setViewName("app.addStatus");
		modelAndView.getModel().put("latestUpdate", latestUpdate);

		return modelAndView;
	}

	@RequestMapping(value = "/addstatus", method = RequestMethod.POST)
	public ModelAndView addStatus(ModelAndView modelAndView, @Valid StatusUpdate statusUpdate, BindingResult result) {

		if (!result.hasErrors()) {
			statusUpdateService.save(statusUpdate);
			modelAndView.getModel().put("statusUpdate", new StatusUpdate());
		}

		StatusUpdate latestUpdate = statusUpdateService.getLatest();

		modelAndView.setViewName("app.addStatus");
		modelAndView.getModel().put("latestUpdate", latestUpdate);

		return modelAndView;
	}

	@RequestMapping(value = "/viewstatus", method = RequestMethod.GET)
	public ModelAndView viewStatus(ModelAndView modelAndView,
			@RequestParam(name = "p", defaultValue = "1") int pageNumber) {

		modelAndView.setViewName("app.viewStatus");

		System.out.println();
		System.out.println("**************pageNumber=" + pageNumber);
		System.out.println();

		return modelAndView;
	}

}

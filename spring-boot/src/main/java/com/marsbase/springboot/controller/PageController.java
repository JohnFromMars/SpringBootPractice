package com.marsbase.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.marsbase.springboot.model.entity.StatusUpdate;
import com.marsbase.springboot.service.StatusUpdateService;

@Controller
public class PageController {

	@Autowired
	private StatusUpdateService statusUpdateService;

	/**
	 * Home page, show the latest update
	 * 
	 * @param modelAndView
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView home(ModelAndView modelAndView) {

		StatusUpdate statusUpdate = statusUpdateService.getLatest();
		
		modelAndView.setViewName("app.homepage");
		modelAndView.getModel().put("statusUpdate", statusUpdate);

		return modelAndView;
	}

	@RequestMapping("/about")
	public String about() {
		return "app.about";
	}

}

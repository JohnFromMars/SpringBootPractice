package com.marsbase.springboot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.marsbase.springboot.model.SiteUser;
import com.marsbase.springboot.service.EmailService;
import com.marsbase.springboot.service.UserService;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;

	@RequestMapping("/login")
	public String login() {
		return "app.login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(ModelAndView modelAndView) {

		// create blank object
		SiteUser user = new SiteUser();

		modelAndView.setViewName("app.register");
		modelAndView.getModel().put("user", user);

		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(ModelAndView modelAndView,@ModelAttribute("user") @Valid SiteUser user, BindingResult result) {

		modelAndView.setViewName("app.register");

		if (!result.hasErrors()) {
			userService.register(user);
			
			emailService.sendVerificationMail(user.getEmail());
			
			modelAndView.setViewName("redirect:/login");
		}

		return modelAndView;
	}
}

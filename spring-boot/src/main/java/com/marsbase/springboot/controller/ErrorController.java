package com.marsbase.springboot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

	@Value("${message.access.denied}")
	private String accessDeniedMesaage;

	@Value("${message.invalid.user}")
	private String invalidUserMessage;

	@Value("${message.expired.token}")
	private String tokenExpiredMessage;

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accessDenied(ModelAndView modelAndView) {
		modelAndView.setViewName("app.message");
		modelAndView.getModel().put("message", accessDeniedMesaage);
		return modelAndView;
	}

	@RequestMapping(value = "/invaliduser", method = RequestMethod.GET)
	public ModelAndView invalidUser(ModelAndView modelAndView) {
		modelAndView.setViewName("app.message");
		modelAndView.getModel().put("message", invalidUserMessage);
		return modelAndView;
	}

	@RequestMapping(value = "/expiredtoken", method = RequestMethod.GET)
	public ModelAndView expiredToken(ModelAndView modelAndView) {
		modelAndView.setViewName("app.message");
		modelAndView.getModel().put("message", tokenExpiredMessage);
		return modelAndView;
	}

}

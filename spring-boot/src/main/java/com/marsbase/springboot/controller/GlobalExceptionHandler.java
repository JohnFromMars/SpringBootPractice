package com.marsbase.springboot.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice()
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest servletRequest, Exception e) {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.getModel().put("message", "Exception occur.");
		
		modelAndView.setViewName("app.message");

		return modelAndView;
	}
}

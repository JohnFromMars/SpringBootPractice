package com.marsbase.springboot.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice()
public class GlobalExceptionHandler {

	@Value("${message.error.exception}")
	private String exceptionMessage;

	@Value("${message.email.duplicated}")
	private String duplicateEmailExceptionMessage;

	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ModelAndView duplicateEmailRegistrationExceptionHandler(HttpServletRequest servletRequest, Exception e) {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.getModel().put("message", duplicateEmailExceptionMessage);
		modelAndView.getModel().put("url", servletRequest.getRequestURI());
		modelAndView.getModel().put("exception", e);
		modelAndView.setViewName("app.exception");

		return modelAndView;
	}

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultExceptionHandler(HttpServletRequest servletRequest, Exception e) {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.getModel().put("message", exceptionMessage);
		modelAndView.getModel().put("url", servletRequest.getRequestURI());
		modelAndView.getModel().put("exception", e);
		modelAndView.setViewName("app.exception");

		return modelAndView;
	}

	@ResponseBody
	@ExceptionHandler(value = MultipartException.class)
	public String fileUploadHandler(Exception e) {
		e.printStackTrace();
		return "Error occured!";
	}

}

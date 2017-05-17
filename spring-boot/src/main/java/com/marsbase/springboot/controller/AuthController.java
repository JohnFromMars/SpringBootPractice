package com.marsbase.springboot.controller;

import java.io.FileNotFoundException;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.marsbase.springboot.model.SiteUser;
import com.marsbase.springboot.model.VerificationToken;
import com.marsbase.springboot.service.EmailService;
import com.marsbase.springboot.service.UserService;

@Controller
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Value("${meesage.registration.confirmed}")
	private String registrationConfirmMessage;
	

	@RequestMapping("/login")
	public String login() {
		return "app.login";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(ModelAndView modelAndView) throws FileNotFoundException {

		if (true)
			throw new FileNotFoundException();

		// create blank object
		SiteUser user = new SiteUser();

		modelAndView.setViewName("app.register");
		modelAndView.getModel().put("user", user);

		return modelAndView;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(ModelAndView modelAndView, @ModelAttribute("user") @Valid SiteUser user,
			BindingResult result) {

		modelAndView.setViewName("app.register");

		if (!result.hasErrors()) {
			userService.register(user);

			String token = userService.createEmailVerficationToken(user);

			emailService.sendVerificationMail(user.getEmail(), token);

			modelAndView.setViewName("redirect:/verifyemail");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/verifyemail", method = RequestMethod.GET)
	public ModelAndView verifyEmail(ModelAndView modelAndView) {
		modelAndView.setViewName("app.verifyEmail");
		return modelAndView;
	}

	@RequestMapping("/confirmregister")
	public ModelAndView reegistrationConfirmed(ModelAndView modelAndView, @RequestParam("t") String token) {

		VerificationToken verificationToken = userService.getVerificationToken(token);

		// check if the verification is null
		if (verificationToken == null) {
			modelAndView.setViewName("redirect:/expiredtoken");
			return modelAndView;
		}

		// check the expire date
		Date expiryDate = verificationToken.getExpiry();

		if (expiryDate.before(new Date())) {
			modelAndView.setViewName("redirect:/expiredtoken");
			userService.deleteToken(token);
			return modelAndView;
		}

		// check the token user is null or not just in case.
		SiteUser user = verificationToken.getUser();

		if (user == null) {
			modelAndView.setViewName("redirect:/invaliduser");
			userService.deleteToken(token);
			return modelAndView;
		}

		// if all good, enable user, and go to confirm message
		user.setEnabled(true);
		userService.save(user);
		userService.deleteToken(token);

		modelAndView.setViewName("app.message");
		modelAndView.getModel().put("message", registrationConfirmMessage);
		return modelAndView;
	}


}

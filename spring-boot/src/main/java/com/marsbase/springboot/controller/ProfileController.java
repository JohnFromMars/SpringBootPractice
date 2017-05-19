package com.marsbase.springboot.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.marsbase.springboot.model.Profile;
import com.marsbase.springboot.model.SiteUser;
import com.marsbase.springboot.service.ProfileService;
import com.marsbase.springboot.service.UserService;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView showProfile(ModelAndView modelAndView, Principal principal) {

		String email = principal.getName();

		// get all SiteUser data by email from database
		SiteUser user = userService.getUser(email);

		// get Profile data by SiteUser
		Profile profile = profileService.getUserProfile(user);

		// if user has no profile
		// create blank profile and set user = current user
		// and save it to database
		if (profile == null) {
			profile = new Profile();
			profile.setUser(user);
			profileService.save(profile);
		}

		Profile profileCopy = new Profile();

		// using save copy to copy only profile.about
		profileCopy.safeCopyFrom(profile);

		modelAndView.getModel().put("profile", profileCopy);
		modelAndView.setViewName("app.profile");

		return modelAndView;
	}

	@RequestMapping(value = "/edit-profile-about", method = RequestMethod.GET)
	public ModelAndView editProfileAbout(ModelAndView modelAndView, Principal principal) {

		SiteUser user = userService.getUser(principal.getName());

		Profile profile = profileService.getUserProfile(user);

		// if user has no profile
		// create blank profile and set user = current user
		// and save it to database
		if (profile == null) {
			profile = new Profile();
			profile.setUser(user);
			profileService.save(profile);
		}

		Profile profileCopy = new Profile();

		profileCopy.safeCopyFrom(profile);

		// set model and view
		modelAndView.getModel().put("profile", profileCopy);
		modelAndView.setViewName("app.editProfileAbout");

		return modelAndView;
	}

	@RequestMapping(value = "/edit-profile-about", method = RequestMethod.POST)
	public ModelAndView editProfileAbout(ModelAndView modelAndView, @Valid Profile webProfile, BindingResult result,
			Principal principal) {

		modelAndView.setViewName("app.editProfileAbout");

		if (!result.hasErrors()) {

			// get the profile data from database by user name
			SiteUser user = userService.getUser(principal.getName());
			Profile profile = profileService.getUserProfile(user);

			// get the latest Profile.about from webProfile
			// and save it to database
			profile.safeMergeFrom(webProfile);
			profileService.save(profile);

			// go back to profile page
			modelAndView.setViewName("redirect:/profile");
		}

		return modelAndView;
	}
}

package com.marsbase.springboot.controller;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.Principal;

import javax.validation.Valid;

import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.marsbase.springboot.exception.InvalidFileException;
import com.marsbase.springboot.model.FileInfo;
import com.marsbase.springboot.model.Profile;
import com.marsbase.springboot.model.SiteUser;
import com.marsbase.springboot.service.FileService;
import com.marsbase.springboot.service.ProfileService;
import com.marsbase.springboot.service.UserService;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private FileService fileService;

	@Autowired
	private PolicyFactory policyFactory;

	@Value("${photo.upload.directory}")
	private String photoUploadDirectory;
	
	@Value("${photo.default.subdir}")
	private String defaultPhotoSubdir;
	
	@Value("${photo.default.name}")
	private String defaultPhotoName;
	
	@Value("${photo.default.height}")
	private int defaultHeight;
	
	@Value("${photo.default.width}")
	private int defaultWidth;

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
			profile.safeMergeFrom(webProfile, policyFactory);
			profileService.save(profile);

			// go back to profile page
			modelAndView.setViewName("redirect:/profile");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/upload-profile-photo", method = RequestMethod.POST)
	public ModelAndView uploadFile(ModelAndView modelAndView, @RequestParam("file") MultipartFile file,
			Principal principal) {

		modelAndView.setViewName("redirect:/profile");
		SiteUser user = userService.getUser(principal.getName());
		Profile profile = profileService.getUserProfile(user);
		Path oldPhotoPath = profile.getPhotoPath(photoUploadDirectory);

		try {
			FileInfo photoFileInfo = fileService.saveImageFile(file, photoUploadDirectory, "photo", "p" + user.getId(),
					defaultWidth, defaultHeight);
			System.out.println("photoFileInfo=" + photoFileInfo);
			profile.setPhotoDetail(photoFileInfo);
			profileService.save(profile);

			// delete old photo if it exsists
			if (oldPhotoPath != null) {
				Files.delete(oldPhotoPath);
			}

		} catch (InvalidFileException | IOException e) {
			e.printStackTrace();
		}

		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/profile-photo", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> servePhoto(Principal principal) throws IOException {

		SiteUser user = userService.getUser(principal.getName());
		Profile profile = profileService.getUserProfile(user);

		Path photoPath = Paths.get(photoUploadDirectory, defaultPhotoSubdir, defaultPhotoName);

		// if profile has no photo detail ,return default marsbase.png
		if (profile != null && profile.getPhotoPath(photoUploadDirectory) != null) {
			photoPath = profile.getPhotoPath(photoUploadDirectory);
		}

		//@formatter:off
		return ResponseEntity
				.ok()
				.contentLength(Files.size(photoPath))
				.contentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(photoPath.toString())))
				.body(new InputStreamResource(Files.newInputStream(photoPath, StandardOpenOption.READ)));
		
		//@formatter:on
	}

}

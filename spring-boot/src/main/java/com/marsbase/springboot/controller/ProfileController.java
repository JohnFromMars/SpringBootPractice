package com.marsbase.springboot.controller;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.marsbase.springboot.exception.ImageTooSmallException;
import com.marsbase.springboot.exception.InvalidFileException;
import com.marsbase.springboot.model.dto.FileInfo;
import com.marsbase.springboot.model.entity.Interest;
import com.marsbase.springboot.model.entity.Profile;
import com.marsbase.springboot.model.entity.SiteUser;
import com.marsbase.springboot.service.FileService;
import com.marsbase.springboot.service.InterestService;
import com.marsbase.springboot.service.ProfileService;
import com.marsbase.springboot.service.UserService;
import com.marsbase.springboot.status.PhotoUploadStatus;
import com.marsbase.springboot.util.StringFormatUtil;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private FileService fileService;

	@Autowired
	private StringFormatUtil stringFormatUtil;

	@Autowired
	private InterestService interestService;

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

	@Value("${photo.upload.ok}")
	private String photoStatusOk;

	@Value("${photo.upload.invalid}")
	private String photoStatusInvalid;

	@Value("${photo.upload.ioexception}")
	private String photoStatusIoException;

	@Value("${photo.upload.toosmall}")
	private String photoStatusTooSmall;

	private String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	private ModelAndView showProfile(SiteUser user) {
		ModelAndView modelAndView = new ModelAndView();

		// check if the user is null, if it is, redirect to home
		if (user == null) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}

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
		modelAndView.getModel().put("userId", user.getId());
		modelAndView.setViewName("app.profile");

		return modelAndView;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView showProfile() {

		String email = getUserName();

		// get all SiteUser data by email from database
		SiteUser user = userService.getUser(email);

		ModelAndView modelAndView = showProfile(user);
		modelAndView.getModel().put("ownProfile", true);

		return modelAndView;
	}

	@RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
	public ModelAndView showProfile(@PathVariable("id") Long id) {

		System.out.println("===================id=" + id);

		// get the profile from input id parameter
		SiteUser user = userService.getUser(id);
		ModelAndView modelAndView = showProfile(user);

		System.out.println("user id=" + user.getId() + ", param id=" + id);

		modelAndView.getModel().put("ownProfile", false);

		return modelAndView;
	}

	@RequestMapping(value = "/edit-profile-about", method = RequestMethod.GET)
	public ModelAndView editProfileAbout(ModelAndView modelAndView) {

		SiteUser user = userService.getUser(getUserName());

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
	public ModelAndView editProfileAbout(ModelAndView modelAndView, @Valid Profile webProfile, BindingResult result) {

		modelAndView.setViewName("app.editProfileAbout");

		if (!result.hasErrors()) {

			// get the profile data from database by user name
			SiteUser user = userService.getUser(getUserName());
			Profile profile = profileService.getUserProfile(user);

			// get the latest Profile.about from webProfile
			// and save it to database
			profile.safeMergeFrom(webProfile, stringFormatUtil);
			profileService.save(profile);

			// go back to profile page
			modelAndView.setViewName("redirect:/profile");
		}

		return modelAndView;
	}

	@ResponseBody // return JSON data
	@RequestMapping(value = "/upload-profile-photo", method = RequestMethod.POST)
	public ResponseEntity<PhotoUploadStatus> uploadFile(@RequestParam("file") MultipartFile file) {

		SiteUser user = userService.getUser(getUserName());
		Profile profile = profileService.getUserProfile(user);
		// get profile old photo path
		Path oldPhotoPath = profile.getPhotoPath(photoUploadDirectory);
		// default status set as ok
		PhotoUploadStatus status = new PhotoUploadStatus(photoStatusOk);

		try {
			// save image file which filename=/photoXXXX/pXXXX.xx
			FileInfo photoFileInfo = fileService.saveImageFile(file, photoUploadDirectory, "photo", "p" + user.getId(),
					defaultWidth, defaultHeight);
			System.out.println("photoFileInfo=" + photoFileInfo);

			// update profile photo filename and path then save it to database
			profile.setPhotoDetail(photoFileInfo);
			profileService.save(profile);

			// delete old photo if it exists
			if (oldPhotoPath != null) {
				Files.delete(oldPhotoPath);
			}

			// different error message for different exception
		} catch (InvalidFileException e) {
			status.setMessage(photoStatusInvalid);
			e.printStackTrace();

		} catch (IOException e) {
			status.setMessage(photoStatusIoException);
			e.printStackTrace();

		} catch (ImageTooSmallException e) {
			status.setMessage(photoStatusTooSmall);
			e.printStackTrace();
		}

		return new ResponseEntity<PhotoUploadStatus>(status, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/profile-photo/{id}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> servePhoto(@PathVariable("id") Long id) throws IOException {

		SiteUser user = userService.getUser(id);
		Profile profile = profileService.getUserProfile(user);

		// if profile has no photo detail ,return default marsbase.png
		Path photoPath = Paths.get(photoUploadDirectory, defaultPhotoSubdir, defaultPhotoName);

		// if profile has photo, then over write it to its own photo path
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

	@ResponseBody
	@RequestMapping(value = "/save-interest", method = RequestMethod.POST)
	public ResponseEntity<?> saveInterest(@RequestParam("name") String interestName) {

		SiteUser user = userService.getUser(getUserName());
		Profile profile = profileService.getUserProfile(user);

		// avoid script inject attack
		String cleanInterestName = stringFormatUtil.sanitize(interestName);

		Interest interest = interestService.createIfNotExist(cleanInterestName);

		profile.addInterest(interest);

		profileService.save(profile);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/delete-interest", method = RequestMethod.POST)
	public ResponseEntity<?> deleteInterest(@RequestParam("name") String interestName) {
		SiteUser user = userService.getUser(getUserName());
		Profile profile = profileService.getUserProfile(user);

		profile.removeInterest(interestName);

		profileService.save(profile);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}
}

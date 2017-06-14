package com.marsbase.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marsbase.springboot.model.dao.ProfileDao;
import com.marsbase.springboot.model.entity.Profile;
import com.marsbase.springboot.model.entity.SiteUser;

@Service
public class ProfileService {

	@Autowired
	private ProfileDao profileDao;

//	@PreAuthorize("isAuthenticated()")
	public void save(Profile profile) {
		profileDao.save(profile);
	}

//	@PreAuthorize("isAuthenticated()")
	public Profile getUserProfile(SiteUser user) {
		return profileDao.findByUser(user);
	}

}

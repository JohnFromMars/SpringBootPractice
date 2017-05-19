package com.marsbase.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marsbase.springboot.dao.ProfileDao;
import com.marsbase.springboot.model.Profile;
import com.marsbase.springboot.model.SiteUser;

@Service
public class ProfileService {

	@Autowired
	private ProfileDao profileDao;

	public void save(Profile profile) {
		profileDao.save(profile);
	}

	public Profile getUserProfile(SiteUser user) {
		return profileDao.findByUser(user);
	}

}

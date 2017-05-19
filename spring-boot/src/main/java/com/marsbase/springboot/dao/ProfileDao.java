package com.marsbase.springboot.dao;

import org.springframework.data.repository.CrudRepository;

import com.marsbase.springboot.model.Profile;
import com.marsbase.springboot.model.SiteUser;

public interface ProfileDao extends CrudRepository<Profile, Long> {
	Profile findByUser(SiteUser user);
}

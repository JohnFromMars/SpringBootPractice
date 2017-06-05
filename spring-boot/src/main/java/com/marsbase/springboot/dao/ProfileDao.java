package com.marsbase.springboot.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.marsbase.springboot.model.Profile;
import com.marsbase.springboot.model.SiteUser;

public interface ProfileDao extends CrudRepository<Profile, Long> {
	List<Profile> findByInterestsName(String text);

	Profile findByUser(SiteUser user);
}

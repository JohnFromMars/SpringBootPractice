package com.marsbase.springboot.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.marsbase.springboot.model.entity.Profile;
import com.marsbase.springboot.model.entity.SiteUser;

public interface ProfileDao extends CrudRepository<Profile, Long> {
	List<Profile> findByInterestsName(String text);

	Profile findByUser(SiteUser user);
}

package com.marsbase.springboot.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.marsbase.springboot.model.SiteUser;

@Repository
public interface UserDao extends CrudRepository<SiteUser, Long> {
	SiteUser findByEmail(String email);
	
}

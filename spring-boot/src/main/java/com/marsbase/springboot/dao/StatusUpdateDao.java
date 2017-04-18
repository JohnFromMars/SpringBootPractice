package com.marsbase.springboot.dao;

import org.springframework.data.repository.CrudRepository;

import com.marsbase.springboot.model.StatusUpdate;

public interface StatusUpdateDao extends CrudRepository<StatusUpdate, Long> {
	
	StatusUpdate findFirstByOrderByAddedDesc();
}

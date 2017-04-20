package com.marsbase.springboot.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.marsbase.springboot.model.StatusUpdate;

public interface StatusUpdateDao extends PagingAndSortingRepository<StatusUpdate, Long> {
	
	StatusUpdate findFirstByOrderByAddedDesc();
}

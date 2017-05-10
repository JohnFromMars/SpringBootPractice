package com.marsbase.springboot.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.marsbase.springboot.model.StatusUpdate;

@Repository
public interface StatusUpdateDao extends PagingAndSortingRepository<StatusUpdate, Long> {
	
	StatusUpdate findFirstByOrderByAddedDesc();
}

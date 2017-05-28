package com.marsbase.springboot.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.marsbase.springboot.model.Interest;

@Repository
public interface InterestDao extends CrudRepository<Interest, Long> {
	Interest findOneByName(String name);
}

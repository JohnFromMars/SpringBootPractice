package com.marsbase.springboot.model.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.marsbase.springboot.model.entity.VerificationToken;

@Repository
@Transactional
public interface VerificationDao extends CrudRepository<VerificationToken, Long> {

	VerificationToken findByToken(String token);

	void deleteByToken(String token);
}

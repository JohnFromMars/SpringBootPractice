package com.marsbase.springboot.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.marsbase.springboot.model.VerificationToken;

@Repository
@Transactional
public interface VerificationDao extends CrudRepository<VerificationToken, Long> {

	VerificationToken findByToken(String token);

	void deleteByToken(String token);
}

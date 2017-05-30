package com.marsbase.springboot.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marsbase.springboot.dao.InterestDao;
import com.marsbase.springboot.model.Interest;

@Service
public class InterestService {

	@Autowired
	private InterestDao interestDao;

	public Interest getInterest(String name) {
		return interestDao.findOneByName(name);
	}

	public void save(Interest interest) {
		interestDao.save(interest);
	}

	public Long count() {
		return interestDao.count();
	}

	@Transactional
	public Interest createIfNotExist(String interestText) {
		Interest interest = interestDao.findOneByName(interestText);

		if (interest == null) {
			interest = new Interest(interestText);
			interestDao.save(interest);
		}

		return interest;
	}
}

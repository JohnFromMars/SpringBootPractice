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

	private String cleanInterest(String interest) {
		return interest.trim().substring(0, 1).toUpperCase() + interest.trim().substring(1).toLowerCase();
	}

	public Interest getInterest(String name) {
		String cleanInterest = cleanInterest(name);
		return interestDao.findOneByName(cleanInterest);
	}

	public void save(Interest interest) {
		String cleanInterest = cleanInterest(interest.getName());
		interest.setName(cleanInterest);
		interestDao.save(interest);
	}

	public Long count() {
		return interestDao.count();
	}

	@Transactional
	public Interest createIfNotExist(String interestText) {

		String cleanInterest = cleanInterest(interestText);
		Interest interest = interestDao.findOneByName(cleanInterest);

		if (interest == null) {
			interest = new Interest(cleanInterest);
			interestDao.save(interest);
		}

		return interest;
	}
}

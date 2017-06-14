package com.marsbase.springboot.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marsbase.springboot.model.dao.InterestDao;
import com.marsbase.springboot.model.entity.Interest;
import com.marsbase.springboot.util.StringFormatUtil;

@Service
public class InterestService {

	@Autowired
	private InterestDao interestDao;
	
	@Autowired
	private StringFormatUtil stringFormatUtil;

	

	public Interest getInterest(String name) {
		String cleanInterest =stringFormatUtil.getUpperAndLowerCase(name);
		return interestDao.findOneByName(cleanInterest);
	}

	public void save(Interest interest) {
		String cleanInterest = stringFormatUtil.getUpperAndLowerCase(interest.getName());
		interest.setName(cleanInterest);
		interestDao.save(interest);
	}

	public Long count() {
		return interestDao.count();
	}

	@Transactional
	public Interest createIfNotExist(String interestText) {

		String cleanInterest = stringFormatUtil.getUpperAndLowerCase(interestText);
		Interest interest = interestDao.findOneByName(cleanInterest);

		if (interest == null) {
			interest = new Interest(cleanInterest);
			interestDao.save(interest);
		}

		return interest;
	}
}

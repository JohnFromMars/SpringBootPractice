package com.marsbase.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marsbase.springboot.dao.ProfileDao;
import com.marsbase.springboot.model.Profile;
import com.marsbase.springboot.util.StringFormatUtil;

@Service
public class SearchService {

	@Autowired
	private ProfileDao profileDao;

	@Autowired
	private StringFormatUtil stringFormatUtil;

	public List<Profile> searchInterest(String text) {
		String cleanText = stringFormatUtil.getUpperAndLowerCase(text);
		profileDao.findByInterestsName(cleanText).stream().forEach(System.out::println);;;
		return null;
	}

}

package com.marsbase.springboot.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marsbase.springboot.model.dao.ProfileDao;
import com.marsbase.springboot.model.dto.SearchResult;
import com.marsbase.springboot.util.StringFormatUtil;

@Service
public class SearchService {

	@Autowired
	private ProfileDao profileDao;

	@Autowired
	private StringFormatUtil stringFormatUtil;

	public List<SearchResult> searchInterest(String text) {
		String cleanText = stringFormatUtil.getUpperAndLowerCase(text);
		return profileDao.findByInterestsName(cleanText).stream().map(SearchResult::new).collect(Collectors.toList());
	}

}

package com.marsbase.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.marsbase.springboot.model.dto.SearchResult;
import com.marsbase.springboot.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(ModelAndView modelAndView, @RequestParam("s") String name) {
		modelAndView.setViewName("app.search");
		List<SearchResult> searchResult = searchService.searchInterest(name);

		modelAndView.getModel().put("searchResult", searchResult);
		
		return modelAndView;
	}

}

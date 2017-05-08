package com.marsbase.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.marsbase.springboot.dao.StatusUpdateDao;
import com.marsbase.springboot.model.StatusUpdate;

@Service
public class StatusUpdateService {
	private final static int PAGE_SIZE = 5;

	@Autowired
	private StatusUpdateDao statusUpdateDao;

	public void save(StatusUpdate statusUpdate) {
		statusUpdateDao.save(statusUpdate);
	}

	public StatusUpdate getLatest() {
		return statusUpdateDao.findFirstByOrderByAddedDesc();
	}

	public Page<StatusUpdate> getPage(int pageNumber) {
		//since pageRequest is 0 base, pageNumber should miner 1.
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "added");
		return statusUpdateDao.findAll(request);
	}
}

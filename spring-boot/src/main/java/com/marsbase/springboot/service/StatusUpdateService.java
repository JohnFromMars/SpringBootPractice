package com.marsbase.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marsbase.springboot.dao.StatusUpdateDao;
import com.marsbase.springboot.model.StatusUpdate;

@Service
public class StatusUpdateService {

	@Autowired
	private StatusUpdateDao statusUpdateDao;

	public void save(StatusUpdate statusUpdate) {
		statusUpdateDao.save(statusUpdate);
	}

	public StatusUpdate getLatest() {
		return statusUpdateDao.findFirstByOrderByAddedDesc();
	}
}

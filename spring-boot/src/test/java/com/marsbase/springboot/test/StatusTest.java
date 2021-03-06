package com.marsbase.springboot.test;

import static org.junit.Assert.assertNotNull;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.marsbase.springboot.App;
import com.marsbase.springboot.model.dao.StatusUpdateDao;
import com.marsbase.springboot.model.entity.StatusUpdate;

@Transactional
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(App.class)
public class StatusTest {

	@Autowired
	private StatusUpdateDao statusUpdateDao;

	@Test
	public void testSave() {

		StatusUpdate statusUpdate = new StatusUpdate("this test status");

		statusUpdateDao.save(statusUpdate);

		assertNotNull("id is null", statusUpdate.getId());
		assertNotNull("added date is null", statusUpdate.getAdded());

		StatusUpdate retrieved = statusUpdateDao.findOne(statusUpdate.getId());

		assertEquals("None-Matching statusUpdate", statusUpdate, retrieved);

	}

	@Test
	public void findLatest() {
		Calendar calendar = Calendar.getInstance();

		StatusUpdate lastUpdate = null;

		for (int i = 0; i < 10; i++) {
			calendar.add(Calendar.DAY_OF_YEAR, 1);

			StatusUpdate update = new StatusUpdate("status update " + i, calendar.getTime());

			statusUpdateDao.save(update);

			lastUpdate = update;
		}
		
		StatusUpdate retrieved = statusUpdateDao.findFirstByOrderByAddedDesc();
		
		System.out.println(retrieved);
		
		assertEquals("None-Matching statusUpdate",lastUpdate, retrieved);
	}
}

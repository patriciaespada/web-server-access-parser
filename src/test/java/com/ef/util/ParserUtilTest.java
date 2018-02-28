package com.ef.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.ef.model.ServerBlocked.ServerBlockedDurations;

public class ParserUtilTest {

	@Test
	public void testAddHour() {
		Date startDate = new Date();
		Date endDate = ParserUtil.addDuration(startDate, ServerBlockedDurations.HOURLY);
		
		assertTrue(startDate.compareTo(endDate) < 0);
	    long diffInMillies = endDate.getTime() - startDate.getTime();
	    assertEquals(Long.valueOf(1), Long.valueOf(TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS)));
	}
	
	@Test
	public void testAddDay() {
		Date startDate = new Date();
		Date endDate = ParserUtil.addDuration(startDate, ServerBlockedDurations.DAILY);
		
		assertTrue(startDate.compareTo(endDate) < 0);
		long diffInMillies = endDate.getTime() - startDate.getTime();
	    assertEquals(Long.valueOf(1), Long.valueOf(TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)));
	}
	
}

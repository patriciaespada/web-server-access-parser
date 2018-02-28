package com.ef.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ef.model.ServerBlocked.ServerBlockedDurations;

/**
 * Utility methods to the parser application.
 * @author patriciaespada
 *
 */
public class ParserUtil {
	
	public static SimpleDateFormat startDateFormatter = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
	public static SimpleDateFormat dbDateformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static final String START_DATE_ARG = "--startDate";
	public static final String DURATION_ARG = "--duration";
	public static final String THRESHOLD_ARG = "--threshold";
	public static final String ACCESSLOG_ARG = "--accesslog";
	
	private ParserUtil() { }
	
	/**
	 * Calculate end date by adding the duration into the start date.
	 * @param startDate
	 * @param duration
	 * @return
	 */
	public static Date addDuration(Date startDate, ServerBlockedDurations duration) {
		Date endDate = null;

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);

		if (duration.equals(ServerBlockedDurations.HOURLY)) {
			cal.add(Calendar.HOUR_OF_DAY, 1);
			endDate = cal.getTime();
		} else if (duration.equals(ServerBlockedDurations.DAILY)) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			endDate = cal.getTime();			
		}

		return endDate;
	}

}

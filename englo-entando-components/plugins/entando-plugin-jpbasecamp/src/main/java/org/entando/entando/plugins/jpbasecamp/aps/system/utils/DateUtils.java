package org.entando.entando.plugins.jpbasecamp.aps.system.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {
	
	
	/**
	 * Convert Strings like the following
	 * @param dateStr
	 * @return
	 */
	public static Date convertBasecampDate(String dateStr) throws Throwable {
		Date date = null;
		
		try {
			if (StringUtils.isNotBlank(dateStr)) {
				DateFormat sdf = new SimpleDateFormat(DATE_FMT_NO_DMZ);
				date = sdf.parse(dateStr);
			}
		} catch (ParseException t) {
			DateFormat sdf = new SimpleDateFormat(DATE_SIMPLE);
			date = sdf.parse(dateStr);
		}
		return date;
	}
	
	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String convertToBasecampDate(Date date) {
		String res = null;
		
		if (null != date) {
			DateFormat sdf = new SimpleDateFormat(DATE_FMT_NO_DMZ);
			res = sdf.format(date);
		}
		return res;
	}
	
	private final static String DATE_FMT_NO_DMZ = "yyyy-MM-dd'T'HH:mm:ss.SSS";
	private final static String DATE_SIMPLE = "yyyy-MM-dd";
}

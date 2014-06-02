package com.eallard.basic.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.sql.Timestamp;

public class DateUtil {

	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT = "HH:mm yyyy-MM-dd";
	public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	public static final String FORMAT_YYYY_MM = "yyyy-MM";
	public static final String FORMAT_YYYYMMDD = "yyyyMMdd";
	public static final String FORMAT_YYYYMM = "yyyyMM";
	public static final int DATETYPE_MONTH = 0; // 日期类型：月
	public static final int DATETYPE_DAY = 1; // 日期类型：日

	public static String string3String(String date) {
		String s = "";
		if (date.indexOf(".") > 0) {
			int pos = date.indexOf(".");
			s = date.substring(0, pos);

		}
		return s;
	}

	public static String string2String2(String date) {
		String s = date;
		if (s == null || s.equals("")) {
			return null;
		}
		String result1 = null;
		try {
			DateFormat format = null;
			if (s.length() > 15) {
				format = new SimpleDateFormat(DATETIME_FORMAT);
			} else if (s.length() > 8) {
				format = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
			} else if (s.length() > 4) {
				format = new SimpleDateFormat(FORMAT_YYYY_MM);
			} else {
				format = new SimpleDateFormat("yyyy");
			}
			result1 = date2String(format.parse(s), FORMAT_YYYY_MM_DD);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result1;
	}

	public static String string2String(String date) {
		String s = "";

		if (date == null || date.equals("")) {
			return null;
		}

		if (date.indexOf(".") > 0) {
			int pos = date.indexOf(".");
			s = date.substring(0, pos);

		}

		if (s == null || s.equals("")) {
			return null;
		}
		String result = null;
		try {
			DateFormat format = null;
			if (s.length() > 15) {
				format = new SimpleDateFormat(DATETIME_FORMAT);
			} else if (s.length() > 8) {
				format = new SimpleDateFormat(FORMAT_YYYY_MM_DD);
			} else if (s.length() > 4) {
				format = new SimpleDateFormat(FORMAT_YYYY_MM);
			} else {
				format = new SimpleDateFormat("yyyy");
			}
			result = date2String(format.parse(s), DATE_FORMAT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String date2String(Date date) {
		if (date == null) {
			return "";
		}
		String result = "";
		DateFormat dateFormat = new SimpleDateFormat(DATETIME_FORMAT);
		result = dateFormat.format(date);
		return result;
	}

	/**
	 * 日期转换为字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2String(Date date, String formatPra) {
		String format = formatPra;
		if (date == null) {
			return "";
		}
		if (format == null || format.equals("")) {
			format = DATETIME_FORMAT;
		}
		String result = "";
		DateFormat dateFormat = new SimpleDateFormat(format);
		result = dateFormat.format(date);
		return result;
	}

	public static String timeStamp2String(java.sql.Timestamp date) {
		if (date == null) {
			return "";
		}
		String result = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		result = dateFormat.format(date);
		return result;
	}

	public static String timeStamp2String(java.sql.Timestamp date, String format) {
		if (date == null) {
			return "";
		}
		String result = "";
		DateFormat dateFormat = new SimpleDateFormat(format);
		result = dateFormat.format(date);
		return result;
	}

	public static Date string2Date(String dateStr, String formatStr) {
		if (dateStr == null) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Timestamp string2Timestamp(String sdate) {
		if (sdate == null) {
			return null;
		}
		Timestamp ts = null;
		try {
			ts = Timestamp.valueOf(sdate);

		} catch (Exception e) {

		}
		return ts;
	}

	/**
	 * 获取当前月份往前推一定周期的日期字符串
	 * 
	 * @param frontNum
	 *            往前推frontNum个周期（日或月）
	 * @param dataDate
	 *            当前日期 月yyyymm 日yyyymmdd
	 * @param updateCycle
	 *            1日周期，2月周期
	 * @return 日或月字符串的List
	 */
	public static List<String> getAssignFrontDate(int frontNum,
			String dataDate, int updateCycle) {
		List<String> list = new LinkedList<String>();
		String dateStr = "";
		String formatStr = "";
		int dateType = -1;
		if (2 == updateCycle) {
			formatStr = FORMAT_YYYYMM;
			dateType = Calendar.MONTH;
		} else if (1 == updateCycle) {
			formatStr = FORMAT_YYYYMMDD;
			dateType = Calendar.DAY_OF_YEAR;
		}
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			Date date = format.parse(dataDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if (!StringUtil.isEmpty(dataDate)) {
				for (int i = 0; i < frontNum; i++) {
					dateStr = format.format(calendar.getTime());
					list.add(dateStr);
					calendar.add(dateType, -1);
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 获取当前月份往前推一定月份的日期字符串
	 * 
	 * @param monthNum
	 *            往前推monthNum个月（包括当前月份）
	 * @param dataDate
	 *            当前日期 格式为yyyymm
	 * @return yyyymm格式的日期字符串 dataDate是给查询条件用的，格式为yyyymm
	 * 
	 */
	public static String getFrontMonth(int monthNum, String dataDate) {
		String dateStr = "";
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYYMM);
		try {
			Date date = format.parse(dataDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if (!StringUtil.isEmpty(dataDate)) {
				calendar.add(Calendar.MONTH, -monthNum);
				dateStr = format.format(calendar.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * 获取往前推N周期(日或者月)
	 * 
	 * @param frontNum
	 *            往前推N个周期
	 * @param dataDate
	 *            日yyyymmdd，月yyyymm
	 * @param updateCycle
	 *            1日周期，2月周期
	 * @return
	 */
	public static String getFrontDate(int frontNum, String dataDate,
			int updateCycle) {
		String dateStr = "";
		String formatStr = "";
		int dateType = -1;
		if (2 == updateCycle) {
			formatStr = FORMAT_YYYYMM;
			dateType = Calendar.MONTH;
		} else if (1 == updateCycle) {
			formatStr = FORMAT_YYYYMMDD;
			dateType = Calendar.DAY_OF_YEAR;
		}
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			Date date = format.parse(dataDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if (!StringUtil.isEmpty(dataDate)) {
				calendar.add(dateType, -frontNum);
				dateStr = format.format(calendar.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	

	/**
	 * 获取当前日期往前推一定日期的日期字符串
	 * 
	 * @author YZ
	 * @param monthNum
	 * @param dataDate
	 *            当前日期 格式为yyyymmdd
	 * @return
	 * 
	 */
	public static String getFrontDay(int dayNum, String dataDate) {
		String dateStr = "";
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYYMMDD);
		try {
			Date date = format.parse(dataDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if (!StringUtil.isEmpty(dataDate)) {
				calendar.add(Calendar.DATE, -dayNum);
				dateStr = format.format(calendar.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * 
	 * @author YZ 获取当前日期往前推一定日期的日期字符串
	 * @param dayNum
	 * @param dataDate
	 * @param dateFormat
	 * @return
	 * 
	 */
	public static String getFrontDay(int dayNum, String dataDate,
			String dateFormat) {
		String dateStr = "";
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		try {
			Date date = format.parse(dataDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			if (!StringUtil.isEmpty(dataDate)) {
				calendar.add(Calendar.DATE, -dayNum);
				dateStr = format.format(calendar.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @author YZ
	 * @param date
	 * @return
	 * 
	 */
	public static Date lastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return cal.getTime();
	}

	/**
	 * 获取指定月份的最后一天的字符串
	 * 
	 * @param dataDate
	 *            yyyymm
	 * @return yyyymmdd
	 */
	public static String lastDayOfMonth(String dataDate) {
		String dayStr = "";
		SimpleDateFormat month_format = new SimpleDateFormat(FORMAT_YYYYMM);
		SimpleDateFormat day_format = new SimpleDateFormat(FORMAT_YYYYMMDD);
		try {
			Date monthDate = month_format.parse(dataDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(monthDate);
			int value = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			calendar.set(Calendar.DAY_OF_MONTH, value);
			dayStr = day_format.format(calendar.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayStr;
	}

	/**
	 * 
	 * @author DG
	 * @param date
	 * @return
	 * 
	 */
	public static Date firstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int value = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, value);
		return cal.getTime();
	}

	/**
	 * 获得指定日期的前几天的日期字符串
	 * 
	 * @param currentDay
	 *            当前日期
	 * @param num
	 *            当前日期前几天
	 * @return yyyyMMdd型的日期
	 * @throws Exception
	 */
	public static String getLastDate(String currentDay, int num) {
		DateFormat df = new SimpleDateFormat(FORMAT_YYYYMMDD);
		Date date = null;
		String dateStr = null;
		try {
			date = df.parse(currentDay);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int day = calendar.get(Calendar.DATE);
			calendar.set(Calendar.DATE, day - num); // 得到前几天
			Date lastDate = calendar.getTime();
			dateStr = df.format(lastDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}

	/**
	 * 获得当前日期的前一天的日期字符串
	 * 
	 * @return yyyyMMdd型的日期
	 * @throws Exception
	 */
	public static String getLastDate() {
		DateFormat df = new SimpleDateFormat(FORMAT_YYYYMMDD);
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day - 1); // 得到前一天
		Date lastDate = calendar.getTime();
		String dateStr = df.format(lastDate);
		return dateStr;
	}

	
	public static String getCurrentDay() {
		String date = date2String(new Date(), FORMAT_YYYY_MM_DD) + " 00:00:00";
		return date;
	}

	/**
	 * 获取当前时间 日期管理中添加，用于作为日期选择组件的截止日期
	 * 
	 * @return
	 * @author caihq 68434
	 */
	public static String getCurrentDayYYYYMMDD() {
		String date = date2String(new Date(), FORMAT_YYYYMMDD);
		return date;
	}

	/**
	 * 将yyyymm和yyyymmdd格式的日期转换成yyyy-mm和yyyy-mm-dd
	 * 
	 * @param dataDate
	 * @return
	 */
	public static String dateFormat(String dataDate) {
		String formatStr = "";
		String resultFormatStr = "";
		String dateStr = "";
		if (null != dataDate && !"".equals(dataDate)) {
			if (dataDate.length() == 6) {
				formatStr = FORMAT_YYYYMM;
				resultFormatStr = FORMAT_YYYY_MM;
			}
			if (dataDate.length() == 8) {
				formatStr = FORMAT_YYYYMMDD;
				resultFormatStr = FORMAT_YYYY_MM_DD;
			}
		} else {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		SimpleDateFormat resultFormat = new SimpleDateFormat(resultFormatStr);
		try {
			Date theDate = format.parse(dataDate);
			dateStr = resultFormat.format(theDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateStr;
	}

}
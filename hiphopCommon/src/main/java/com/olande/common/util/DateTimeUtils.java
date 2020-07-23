package com.olande.common.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateTimeUtils {
	// 默认时间格式
	public final static String DEFAULT_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	// 默认日期格式
	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public final static String YYYY_MM_DD = "yyyy/MM/dd";
	public final static String YYYYMMDD = "yyyyMMdd";
	public final static String YYYYMM = "yyyyMM";
	public final static String MM_DD_YYYY = "MM/dd/yyyy";
	public final static String DD_MM_YYYY = "dd/MM/yyyy";
	public final static String HH24_MM_SS = "HH:mm:ss";
	public final static String HH24_MM = "HH:mm";
	public final static String HH12_MM_SS = "hh:mm:ss";
	public final static String HH12_MM_A = "hh:mm a";
	public final static String HH12_MM_SS_A = "hh:mm:ss a";
	public final static String MM_DD_HH_MM = "MM-dd hh:mm";
	public final static String WEEK_FMT = "yyyy年MM月DD日 E HH:mm";

	private DateTimeUtils() {
		super();
	}

	/**
	 * 解析时间日期
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public final static String format(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public final static int formatToNumber(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		return Integer.parseInt(df.format(date));
	}

	/**
	 * 将字符串解析成java.util.Date
	 * 
	 * @param source
	 * @param format
	 * @return
	 */
	public final static Date parseToDate(String source, String format) {
		DateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将字符串解析成java.sql.Date
	 * 
	 * @param source
	 * @param format
	 * @return
	 */
	public final static java.sql.Date parseToSqlDate(String source,
			String format) {
		return new java.sql.Date(parseToDate(source, format).getTime());
	}

	/**
	 * 将字符串解析成java.sql.Time
	 * 
	 * @param source
	 * @param format
	 * @return
	 */
	public final static Time parseToTime(String source, String format) {
		return new Time(parseToDate(source, format).getTime());
	}

	/**
	 * 将字符串解析成java.sql.Timestamp
	 * 
	 * @param source
	 * @param format
	 * @return
	 */
	public final static Timestamp parseToTimestamp(String source, String format) {
		return new Timestamp(parseToDate(source, format).getTime());
	}

	/**
	 * 获取当月的天数
	 * 
	 * @return
	 */
	public final static int getMonthDays() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1); // 把日期设置为当月第一天
		a.roll(Calendar.DATE, -1); // 日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	public final static String formateDate(Timestamp time, String fmt) {
		String date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		date = sdf.format(time);
		return date;
	}

	public static String getDayRelation(int minusDays) {
		switch (minusDays) {
		case 0:
			return "今天";
		case 1:
			return "明天";
		case 2:
			return "后天";
		default:
			return "今天";
		}
	}

	public static String dayOfWeek(int day) {
		String strWeekOfDay = null;
		switch (day) {
		case 0:
			strWeekOfDay = "周日";
			break;
		case 1:
			strWeekOfDay = "周一";
			break;
		case 2:
			strWeekOfDay = "周二";
			break;
		case 3:
			strWeekOfDay = "周三";
			break;
		case 4:
			strWeekOfDay = "周四";
			break;
		case 5:
			strWeekOfDay = "周五";
			break;
		case 6:
			strWeekOfDay = "周六";
			break;
		default:
			strWeekOfDay = "周日";
			break;
		}
		return strWeekOfDay;

	}

	/**
	 * 云酷项目组:徐德
	 * 
	 * @param time
	 * @return
	 */
	public static String getTimeDesc(Timestamp time) {
		if (null == time) {
			return "未知";
		}
		Date date = new Date();
		long ser_time = time.getTime();
		long cur_time = date.getTime();
		long l = cur_time - ser_time;
		long year = l / (1000 * 60 * 60 * 24 * 365);
		long month = l / (1000 * 60 * 60 * 24 * 30);
		long day = l / (1000 * 60 * 60 * 24);
		long hour = l / (1000 * 60 * 60);
		long mintue = l / (1000 * 60);
		if (year >= 1) {
			return year + "年前";
		} else if (month >= 1) {
			return month + "个月前";
		} else if (day >= 1) {
			return day + "天前";
		} else if (hour >= 1) {
			return hour + "个小时前";
		} else if (mintue >= 1) {
			return mintue + "分钟前";
		} else {
			return "刚刚";
		}
	}
}

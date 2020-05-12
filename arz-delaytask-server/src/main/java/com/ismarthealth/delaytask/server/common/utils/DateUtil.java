package com.ismarthealth.delaytask.server.common.utils;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public static final String LONG_DATE_GBK_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒";
	public static final String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String OB_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String MONTH_DATE_FORMAT = "yyyy-MM";
	public static final String DATE_FORMAT_NUMBER = "yyyyMMdd";

	public static final String ALL_DATE_FORMAT = "yyyyMMddhhmmssSSS";
	public static final String ALL_DATE_FORMAT_UP = "yyyyMMddHHmmssSSS";
	public static final String DATE_FORMAT_SEQ = "yyyyMMddHHmmss";
	public static final String SHORTYEAR_MONTH_DAY = "yyMMdd";
	public static final String YEAR = "YYYY";
	public static final String ALL_DATE_FORMAT_NAME = "yyMMdd HH:mm";

	/**
	 * 将一个日期型转换为指定格式字串
	 *
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static final String toFormatDateString(Date date, String formatStr) {
		if (date == null){
			return StringUtils.EMPTY;
		}
		return new SimpleDateFormat(formatStr).format(date);

	}

	public static String getNow() {
		return toLongDateString(new Date());
	}

	public static String getShortDate() {
		return toFormatDateString(new Date(), DATE_FORMAT);
	}

	public static String getYear4Now() {
		return toFormatDateString(new Date(), YEAR);
	}

	/**
	 * 将一个日期型转换为'yyyy年MM月dd日 HH时mm分ss秒'格式字串
	 *
	 * @param date
	 * @return
	 */
	public static final String toLongDateGBKString(Date date) {
		return toFormatDateString(date, LONG_DATE_GBK_FORMAT);
	}

	public static final String toShortYearMonthDay(Date date) {
		return toFormatDateString(date, SHORTYEAR_MONTH_DAY);
	}

	public static final String toLongDateString(Date date) {
		return toFormatDateString(date, LONG_DATE_FORMAT);
	}

	public static final String toDateString(Date date) {
		return toFormatDateString(date, DATE_FORMAT);
	}

	public static final String toMonthDateString(Date date) {
		return toFormatDateString(date, MONTH_DATE_FORMAT);
	}

	public static final String toDateNumber(Date date) {
		return toFormatDateString(date, DATE_FORMAT_NUMBER);
	}

	public static final String toAllDateString4Name(Date date) {
		return toFormatDateString(date, ALL_DATE_FORMAT_NAME);
	}

	public static final String toAllDateString(Date date) {
		return toFormatDateString(date, ALL_DATE_FORMAT);
	}

	public static final String toAllDateStringUp(Date date) {
		return toFormatDateString(date, ALL_DATE_FORMAT_UP);
	}

	public static final String toAllDateStringSeq(Date date) {
		return toFormatDateString(date, DATE_FORMAT_SEQ);
	}

	public static final String toAllDateStringOb(Date date) {
		return toFormatDateString(date, OB_DATE_FORMAT);
	}

	public static final String getCurrentDate() {
		return toAllDateStringSeq(new Date());
	}

	public static final String getStringFormat(String parser) {
		return toDateString(parser(parser, "yyyy-MM-dd"));
	}

	public static final Date parser(String dateStr, String formatter) {
		if (StringUtils.isBlank(dateStr)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);

		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	public static final long getInterval(Date begin, Date end) {
		return end.getTime() - begin.getTime();
	}

	/**
	 * 获取当前日期的 减去 nextDate 日期
	 *
	 * @param nextDate
	 * @return
	 */
	public static final Date getNextDate(int nextDate) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - nextDate);
		Date endDate = null;
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return endDate;
	}

	public static final Date getAddDate(String curDate, int nextDate) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");

		Date beginDate = parser(curDate, "yyyy-MM-dd");

		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) + nextDate);
		Date endDate = null;
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return endDate;
	}

	public static final Date getAddMonth(String curDate, int month) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");

		Date beginDate = parser(curDate, "yyyy-MM-dd");

		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		// date.set(Calendar.MONTH, date.get(Calendar.MONTH) + month);
		date.add(Calendar.MONTH, month);
		Date endDate = null;
		System.out.println(date.getTime());
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return endDate;
	}

	public static final Date getAddYear(String curDate, int year) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");

		Date beginDate = parser(curDate, "yyyy-MM-dd");

		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.YEAR, date.get(Calendar.YEAR) + year);
		Date endDate = null;
		try {
			endDate = dft.parse(dft.format(date.getTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return endDate;
	}

	/**
	 * 根据生日获取年龄
	 *
	 * @param birthDay
	 * @return
	 */
	public static int getAge(String birthDay) {

		Date dateOfBirth = DateUtil.parser(birthDay, DateUtil.DATE_FORMAT);

		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (dateOfBirth != null) {
			now.setTime(new Date());
			born.setTime(dateOfBirth);
			if (born.after(now)) {
				// throw new IllegalArgumentException("Can't be born in the
				// future");
				return -1;
			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
				age -= 1;
			}
		}

		return age;
	}

	/**
	 * 取得n天时间
	 *
	 * @param n
	 *            n=0 今天 n=1 明天；n=0 今天 n=-1 昨天
	 * @return String 返回n天时间 yyyy-mm-dd
	 */
	public static String getDateList(int n) {

		GregorianCalendar gcDate = new GregorianCalendar();
		String sbDateTodayTime;
		int year = gcDate.get(1);
		int month = gcDate.get(2);
		int date = gcDate.get(5);
		GregorianCalendar oneWeek = new GregorianCalendar(year, month, date);
		oneWeek.add(5, n);
		Date date2 = oneWeek.getTime();
		sbDateTodayTime = toDateString(date2);
		return sbDateTodayTime;
	}

	/**
	 * 获取当前日期是星期几<br>
	 *
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(String date) {
		Date dt = parser(date, "yyyy-MM-dd");
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 获得指定日期的后一天 2015-11-08形式
	 *
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	/**
	 * <li>功能描述：时间相减得到天数
	 *
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static int getDaySub(String beginDateStr, String endDateStr) {
		int day = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate;
		Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (int) ((endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000));
			// System.out.println("相隔的天数="+day);
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * 获得指定日期的前一天
	 *
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {// 可以用new
																		// Date().toLocalString()传递参数
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 得到本月的第一天
	 * 
	 * @return
	 */
	public static String getMonthFirstDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		return dayBefore;
	}

	/**
	 * 得到本月的最后一天
	 * 
	 * @return
	 */
	public static String getMonthLastDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
		return dayBefore;
	}

	/**
	 * 
	 * @方法名称: getupMonth
	 * @功能描述:
	 * @作者:获取上个月时间
	 * @创建时间:2017年6月19日 下午6:25:12
	 * @return Date
	 */
	public static Date getupMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());// 设置当前日期
		calendar.add(Calendar.MONTH, -1);// 月份减一
		return calendar.getTime();
	}
    /**
     * 比较两个日期
     * @param DATE1
     * @param DATE2
     * @return
     */
	public static int compare_date(String DATE1, String DATE2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	public static int compare_date_Str(String DATE1, String DATE2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		try {
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime()) {
				System.out.println("dt1 在dt2前");
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
}

package org.gaixie.micrite.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtil {
	/***
	 * 格式yyyy-MM-dd
	 * */
	public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	/**两个日期之间的时间差；后者减去前者
	 * @param date1开始时间
	 * @param date2截止时间
	 * @return 负数如果截止时间小于开始时间；参数有空则返回null
	 * */
	public static Integer getDaysBetween(Date date1, Date date2) {
		if(date1==null||date2==null)return null;
		
		Calendar d1 = Calendar.getInstance();
		d1.setTime(date1);
		Calendar d2 = Calendar.getInstance();
		d2.setTime(date2);
		boolean swaped =false;
		if (d1.after(d2)) {
			// swap dates so that d1 is start and d2 is end
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
			swaped=true;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();

			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		if(swaped)return -(days);
		return days;
	}

	public static void main2(String[] args) {
		SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		// getTime()方法是取得当前的日期，其返回值是一个java.util.Date类的对象
		System.out.println(dformat.format(calendar.getTime()));
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, day + 30);
		System.out.println(dformat.format(calendar.getTime()));

		Date oldDate = new Date();
		calendar = Calendar.getInstance();
		calendar.setTime(oldDate);
		// getTime()方法是取得当前的日期，其返回值是一个java.util.Date类的对象
		// System.out.println(dformat.format(calendar.getTime()));
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month + 3);
		Date newDate = calendar.getTime();
		System.out.println(dformat.format(newDate));
	}
/**
 * 某个日期以后的几个月是几几年几月几号
 * */
	public static Date afterMonth(Date origDate, int monthNum) {
		if (origDate == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(origDate);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month + monthNum);
		Date newDate = calendar.getTime();
		return newDate;
	}
/**
 * 某个日期以后的多少天是几几年几月几号
 * */
	public static Date afterDay(Date origDate, int dayNum) {
		if (origDate == null)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(origDate);
		int days = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, days + dayNum);
		Date newDate = calendar.getTime();
		return newDate;
	}
	public static void main(String[] args) throws Exception{
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date origDate = df.parse("2010-9-9");
		Date endDate = df.parse("2011-2-9");
//		for (int i = 0; i < 1000; i++) {
//			System.out.println(df.format(afterDay(origDate,i)));
//		}
//		for (int i = 0; i < 100; i++) {
//			System.out.println(df.format(afterMonth(origDate,i)));
//		}
//		System.out.println(df.format(afterDay(origDate,10)));
//		System.out.println(df.format(afterDay(origDate,32)));
//		System.out.println(df.format(afterDay(origDate,150)));
//		System.out.println(df.format(afterMonth(origDate,1)));
//		System.out.println(df.format(afterMonth(origDate,10)));
		System.out.println(getDaysBetween(origDate, endDate));
		System.out.println(getDaysBetween(endDate, origDate));
//		for (int i = 0; i < 200; i++) {
//			Date endD=afterDay(origDate,i);
//			System.out.println(df.format(endD)+":"+(getDaysBetween(df.parse("2010-9-9"),endD)));	
//		}
		
	}
}

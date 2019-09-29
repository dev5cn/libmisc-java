/*
  Copyright 2019 www.dev5.cn, Inc. dev5@qq.com
 
  This file is part of X-MSG-IM.
 
  X-MSG-IM is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  X-MSG-IM is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
 
  You should have received a copy of the GNU Affero General Public License
  along with X-MSG-IM.  If not, see <https://www.gnu.org/licenses/>.
 */
package misc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateMisc
{
	public static final long SECOND = 1 * 1000;
	public static final long MINUTE = 60 * SECOND;
	public static final long HOUR = 60 * MINUTE;
	public static final long DAY = 24 * HOUR;
	public static final long WEEK = 7 * DAY;
	public static final TimeZone tz = TimeZone.getDefault();

	public static final Date parseLocale(String date)
	{
		if (date == null)
			return null;
		try
		{
			return new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(date);
		} catch (Exception e)
		{
			if (Log.isTrace())
				Log.trace("%s, date: %s", Log.trace(e), date);
			return null;
		}
	}

	public static final Date parse_yyyy(String date)
	{
		if (date == null)
			return null;
		try
		{
			return new SimpleDateFormat("yyyy").parse(date);
		} catch (Exception e)
		{
			if (Log.isTrace())
				Log.trace("%s, date: %s", Log.trace(e), date);
			return null;
		}
	}

	public static final Date parse_yyyy_mm(String date)
	{
		if (date == null)
			return null;
		try
		{
			return new SimpleDateFormat("yyyy-MM").parse(date);
		} catch (Exception e)
		{
			if (Log.isTrace())
				Log.trace("%s, date: %s", Log.trace(e), date);
			return null;
		}
	}

	public static final Date parse_yyyymmdd(String date)
	{
		if (date == null)
			return null;
		try
		{
			return new SimpleDateFormat("yyyyMMdd").parse(date);
		} catch (Exception e)
		{
			if (Log.isTrace())
				Log.trace("%s, date: %s", Log.trace(e), date);
			return null;
		}
	}

	public static final Date parse_yyyy_mm_dd(String date)
	{
		if (date == null)
			return null;
		try
		{
			return new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (Exception e)
		{
			if (Log.isTrace())
				Log.trace("%s, date: %s", Log.trace(e), date);
			return null;
		}
	}

	public static final Date parse_yyyymmddhh(String date)
	{
		if (date == null)
			return null;
		try
		{
			return new SimpleDateFormat("yyyyMMddHH").parse(date);
		} catch (Exception e)
		{
			if (Log.isTrace())
				Log.trace("%s, date: %s", Log.trace(e), date);
			return null;
		}
	}

	public static final Date parse_yyyymmddhhmm(String date)
	{
		if (date == null)
			return null;
		try
		{
			return new SimpleDateFormat("yyyyMMddHHmm").parse(date);
		} catch (Exception e)
		{
			if (Log.isTrace())
				Log.trace("%s, date: %s", Log.trace(e), date);
			return null;
		}
	}

	public static final Date parse_yyyy_mm_dd_hh_mm_ss(String date)
	{
		if (date == null)
			return null;
		try
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		} catch (Exception e)
		{
			if (Log.isTrace())
				Log.trace("%s, date: %s", Log.trace(e), date);
			return null;
		}
	}

	public static final Date parse_yyyy_mm_dd_hh_mm_ss_ms(String date)
	{
		if (date == null)
			return null;
		try
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(date);
		} catch (Exception e)
		{
			if (Log.isTrace())
				Log.trace("%s, date: %s", Log.trace(e), date);
			return null;
		}
	}

	public static final String yyyy_mm_dd()
	{
		return DateMisc.to_yyyy_mm_dd(new Date());
	}

	public static final String to_yyyy_mm_dd(Date date)
	{
		return DateMisc.to(new SimpleDateFormat("yyyy-MM-dd"), date);
	}

	public static final String yyyy_mm()
	{
		return DateMisc.to_yyyy_mm(new Date());
	}

	public static final String to_yyyy_mm(Date date)
	{
		return DateMisc.to(new SimpleDateFormat("yyyy-MM"), date);
	}

	public static final String yyyyxmm(Date date)
	{
		return DateMisc.to_yyyyxmm(new Date());
	}

	public static final String to_yyyyxmm(Date date)
	{
		return DateMisc.to(new SimpleDateFormat("yyyy/MM"), date);
	}

	public static final String yyyy_mm_dd_hh_mm_ss()
	{
		return DateMisc.to_yyyy_mm_dd_hh_mm_ss(new Date());
	}

	public static final String to_yyyy_mm_dd_hh_mm_ss(Date date)
	{
		return DateMisc.to(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), date);
	}

	public static final String yyyy_mm_dd_hh_mm_ss_ms()
	{
		return DateMisc.to_yyyy_mm_dd_hh_mm_ss_ms(new Date());
	}

	public static final String to_yyyy_mm_dd_hh_mm_ss_ms(Date date)
	{
		long ms = date.getTime() % 1000;
		return DateMisc.to(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), date) + "." + (ms > 99 ? ms : (ms > 9 ? ("0" + ms) : ("00" + ms)));
	}

	public static final String to_yyyyxmmxdd_hh_mm(Date date)
	{
		return DateMisc.to(new SimpleDateFormat("yyyy/MM/dd HH:mm"), date);
	}

	public static final String to_yyyymmdd(Date date)
	{
		return DateMisc.to(new SimpleDateFormat("yyyyMMdd"), date);
	}

	public static final String to_yyyymmddhh(Date date)
	{
		return DateMisc.to(new SimpleDateFormat("yyyyMMddHH"), date);
	}

	public static final String to_yyyymmddhhmmss(Date date)
	{
		return DateMisc.to(new SimpleDateFormat("yyyyMMddHHmmss"), date);
	}

	public static final String to_yyyymmddhhmm(Date date)
	{
		return DateMisc.to(new SimpleDateFormat("yyyyMMddHHmm"), date);
	}

	public static final String to_mmddhhmmss(Date date)
	{
		return DateMisc.to(new SimpleDateFormat("MMddHHmmss"), date);
	}

	public static final String to_hh_mm_ss(Date date)
	{
		return DateMisc.to(new SimpleDateFormat("HH:mm:ss"), date);
	}

	public static final String to_hh_mm_ss_ms(Date date)
	{
		long ms = date.getTime() % 1000;
		return DateMisc.to(new SimpleDateFormat("HH:mm:ss"), date) + "." + (ms > 99 ? ms : (ms > 9 ? ("0" + ms) : ("00" + ms)));
	}

	public static final String to_yyyymmddhhmmssms(Date date)
	{
		long ms = date.getTime() % 1000;
		return DateMisc.to_yyyymmddhhmmss(date) + (ms > 99 ? ms : (ms > 9 ? ("0" + ms) : ("00" + ms)));
	}

	public static final Date set000000(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime() - (date.getTime() % 1000));
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return cal.getTime();
	}

	public static final int getSeconds(Date date)
	{
		return (int) (date.getTime() / 1000L);
	}

	public static final String hour(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		return hour > 9 ? hour + "" : "0" + hour;
	}

	public static final int secondInt(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.SECOND);
	}

	public static final int minuteInt(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MINUTE);
	}

	public static final int hourInt(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static final int dayInt(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static final int weekInt(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static final int week(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		week -= 1;
		return week < 1 ? 7 : week;
	}

	public static final Date gotoMonday(Date date)
	{
		return new Date(DateMisc.set000000(date).getTime() - (DateMisc.week(date) - 1) * DateMisc.DAY);
	}

	public static final int monthInt(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	public static final int yearInt(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static final int yyyymm(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR) * 100 + (cal.get(Calendar.MONTH) + 1);
	}

	public static final int yyyymmdd(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH) + 1) * 100 + cal.get(Calendar.DAY_OF_MONTH);
	}

	public static final int monthDays(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static final Date montheLastDay(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar ret = Calendar.getInstance();
		ret.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		return ret.getTime();
	}

	public static final Date monthFirstDay000000(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar ret = Calendar.getInstance();
		ret.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return ret.getTime();
	}

	public static final Date monthLastDay235959(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar ret = Calendar.getInstance();
		ret.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
		return ret.getTime();
	}

	public static final Date yearFirstDay000000(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar ret = Calendar.getInstance();
		ret.set(cal.get(Calendar.YEAR), cal.getActualMinimum(Calendar.MONTH), cal.getActualMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return ret.getTime();
	}

	public static final Date yearLastDay235959(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Calendar ret = Calendar.getInstance();
		ret.set(cal.get(Calendar.YEAR), cal.getActualMaximum(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
		return ret.getTime();
	}

	public static final int getTimezone()
	{
		return (int) (DateMisc.tz.getRawOffset() / DateMisc.HOUR);
	}

	public static final long changeLocalTimeZone(long ts , int gmt )
	{
		return (ts - DateMisc.tz.getRawOffset() ) + (gmt * DateMisc.HOUR);
	}

	public static final Date changeLocalTimeZone2date(long ts , int gmt )
	{
		return new Date(DateMisc.changeLocalTimeZone(ts, gmt));
	}

	public static final Date nowGmt0()
	{
		return new Date(System.currentTimeMillis() - DateMisc.tz.getRawOffset() );
	}

	public static final Date gotoGmtxOld(Date date , int gmt )
	{
		return new Date(date.getTime() + gmt * DateMisc.HOUR);
	}

	public static final Date gotoGmt0Old(Date date , int gmt )
	{
		return new Date((date.getTime() - gmt * DateMisc.HOUR));
	}

	public static final Date gotoGmtx(long ts , int gmtSec )
	{
		return new Date((ts - DateMisc.tz.getRawOffset() ) + (gmtSec * DateMisc.SECOND ));
	}

	public static final Date gmtxGoto0(Date date , int gmtSec )
	{
		return new Date((date.getTime() - gmtSec * DateMisc.SECOND));
	}

	public static final Date gmt0Gotox(Date date , int gmtSec )
	{
		return new Date(date.getTime() + gmtSec * DateMisc.SECOND);
	}

	public static final Date gotoGmt0(Date date )
	{
		return new Date(date.getTime() - DateMisc.tz.getRawOffset());
	}

	public static final Date gotoLocal(Date date)
	{
		return new Date(date.getTime() + DateMisc.tz.getRawOffset());
	}

	public static final boolean isSameDay(Date arg0, Date arg1)
	{
		return (DateMisc.yearInt(arg0) == DateMisc.yearInt(arg1)) && 
				(DateMisc.monthInt(arg0) == DateMisc.monthInt(arg1)) && 
				(DateMisc.dayInt(arg0) == DateMisc.dayInt(arg1));
	}

	public static final Date createDate(int year, int month, int day, int hourOfDay, int minute, int second)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day, hourOfDay, minute, second);
		return cal.getTime();
	}

	public static final Date dateRollOfSecond(Date date, int amount, boolean up)
	{
		return up ? new Date(date.getTime() + ((long) amount) * DateMisc.SECOND) : new Date(date.getTime() - ((long) amount) * DateMisc.SECOND);
	}

	public static final Date dateRollOfMinute(Date date, int amount, boolean up)
	{
		return up ? new Date(date.getTime() + ((long) amount) * DateMisc.MINUTE) : new Date(date.getTime() - ((long) amount) * DateMisc.MINUTE);
	}

	public static final Date dateRollOfHour(Date date, int amount, boolean up)
	{
		return up ? new Date(date.getTime() + ((long) amount) * DateMisc.HOUR) : new Date(date.getTime() - ((long) amount) * DateMisc.HOUR);
	}

	public static final Date dateRollOfDay(Date date, int amount, boolean up)
	{
		return up ? new Date(date.getTime() + ((long) amount) * DateMisc.DAY) : new Date(date.getTime() - ((long) amount) * DateMisc.DAY);
	}

	public static final Date dateRollOfMonth(Date date, boolean up)
	{
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.roll(Calendar.MONTH, up);
		int m = DateMisc.monthInt(date);
		if (m == 1 && !up)
			ca.roll(Calendar.YEAR, false);
		if (m == 12 && up)
			ca.roll(Calendar.YEAR, true);
		return ca.getTime();
	}

	public static final Date dateRollOfYear(Date date, boolean up)
	{
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.roll(Calendar.YEAR, up);
		return ca.getTime();
	}

	public static final Date clearMinute(Date date)
	{
		return new Date(date.getTime() - (date.getTime() % DateMisc.HOUR));
	}

	public static final Date clearHour(Date date)
	{
		return DateMisc.set000000(date);
	}

	public static final long sec2msec(long sec)
	{
		return sec * 1000L;
	}

	private static final String to(SimpleDateFormat format, Date date)
	{
		try
		{
			return date == null ? null : format.format(date);
		} catch (Exception e)
		{
			if (Log.isTrace())
				Log.trace("%s", Log.trace(e));
			return null;
		}
	}
}

package com.backstage.common.utils.lang;

import com.backstage.common.utils.math.MathUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 时间日期格式工具
 */
public final class DateTimeUtil {
    public static final String YEAR = "yyyy";
    public static final String YEAR_MONTH = "yyyy-MM";
    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
    public static final String YEAR_MONTH_DAY_SIMPLE = "yyyyMMdd";
    public static final String YEAR_MONTH_DAY_HOUR = "yyyy-MM-dd HH";
    public static final String YEAR_MONTH_DAY_CN = "yyyy年MM月dd日";
    public static final String YEAR_MONTH_DAY_HOUR_CN = "yyyy年MM月dd日HH时";
    public static final String YEAR_MONTH_DAY_CN_NO_ZERO = "yyyy年M月d日";
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_SIMPLE = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YEAR_MONTH_DAY_HOUR_MINUTE_SECOND_COMPLEX = "MM/dd/yyHH:mm:SSS";
    public static final String FORMAT_YMD = "yyyy-MM-dd";
    public static final String MONTH_DAY_YEAR = "MM/dd/yy";
    public static final SimpleDateFormat SDF_YMD = new SimpleDateFormat("yyyy-MM-dd");
    public static final String FORMAT_HMS = "HH:mm:ss";
    public static final SimpleDateFormat SDF_HMS = new SimpleDateFormat("HH:mm:ss");
    public static final String FORMAT_YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final SimpleDateFormat SDF_YMD_HMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String FORMAT_YMD_HMSS = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final SimpleDateFormat SDF_YMD_HMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static final String HOUR_MINUTE_SECOND = "HH:mm:ss";
    public static final String HOUR_MINUTE = "HH:mm";
    public static final String MONTH_DAY = "M.d";
    private static final int DAY_SECOND = 86400;
    private static final int HOUR_SECOND = 3600;
    private static final int MINUTE_SECOND = 60;
    public static final long MILLIONSECOND_OF_SECOND = 1000L;
    public static final long MILLIONSECOND_OF_MINUTE = 60000L;
    public static final long MILLIONSECOND_OF_HOUR = 3600000L;
    public static final long MILLIONSECOND_OF_DAY = 86400000L;
    public static final String REG_EXP_DATE = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
    public static final long TIMEZONE_OFFSET = (long) (Calendar.getInstance().get(15) + Calendar.getInstance().get(16));
    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal();
    private static final Object object = new Object();


    public static String format(Date date, String pattern) {
        return date == null ? "" : (new DateTime(date)).toString(pattern);
    }

    public static Date formatDateString(String dateString, String pattern) {
        try {
            DateTimeFormatter e = DateTimeFormat.forPattern(pattern);
            return e.parseDateTime(dateString).toDate();
        } catch (Exception var3) {
            return null;
        }
    }

    public static Date formatDate2Str(String dateString, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(dateString);
        } catch (Exception var3) {
            return null;
        }
    }

    public static String getDayHourMinuteSecond(int second) {
        if (second == 0) {
            return "0秒";
        } else {
            StringBuilder sb = new StringBuilder();
            int days = second / 86400;
            if (days > 0) {
                sb.append(days);
                sb.append("天");
                second -= days * 86400;
            }

            int hours = second / 3600;
            if (hours > 0) {
                sb.append(hours);
                sb.append("小时");
                second -= hours * 3600;
            }

            int minutes = second / 60;
            if (minutes > 0) {
                sb.append(minutes);
                sb.append("分钟");
                second -= minutes * 60;
            }

            if (second > 0) {
                sb.append(second);
                sb.append("秒");
            }

            return sb.toString();
        }
    }

    public static String getDayHourMinute(int second) {
        if (second == 0) {
            return "0分钟";
        } else {
            StringBuilder sb = new StringBuilder();
            int days = second / 86400;
            if (days > 0) {
                sb.append(days);
                sb.append("天");
                second -= days * 86400;
            }

            int hours = second / 3600;
            if (hours > 0) {
                sb.append(hours);
                sb.append("小时");
                second -= hours * 3600;
            }

            int minutes = second / 60;
            if (minutes > 0) {
                sb.append(minutes);
                sb.append("分钟");
            }

            return sb.toString();
        }
    }

    public static DateTime getDateOnly(DateTime dateTime) {
        return new DateTime(dateTime.toString("yyyy-MM-dd"));
    }

    public static Date[] getMondayAndNextMonday() {
        DateTime dateTime = getDateOnly(new DateTime());
        DateTime monday = dateTime.dayOfWeek().withMinimumValue();
        DateTime nextMonday = monday.plusDays(7);
        return new Date[]{monday.toDate(), nextMonday.toDate()};
    }

    public static Date[] getMondayAndSunday(DateTime dateTime) {
        dateTime = getDateOnly(dateTime);
        DateTime monday = dateTime.dayOfWeek().withMinimumValue();
        DateTime sunday = monday.plusDays(6);
        return new Date[]{monday.toDate(), sunday.toDate()};
    }

    public static int compareDaysWithNow(Date date) {
        return Days.daysBetween(new DateTime(), new DateTime(date)).getDays();
    }

    public static int compareDaysWithToday(Date date) {
        DateTime today = new DateTime();
        today = new DateTime(today.getYear(), today.getMonthOfYear(), today.getDayOfMonth(), 0, 0, 0, 0);
        DateTime compareDay = new DateTime(date);
        compareDay = new DateTime(compareDay.getYear(), compareDay.getMonthOfYear(), compareDay.getDayOfMonth(), 0, 0, 0, 0);
        return Days.daysBetween(today, compareDay).getDays();
    }

    public static int compareDaysWithDay(Date a, Date b) {
        DateTime today = new DateTime(b);
        today = new DateTime(today.getYear(), today.getMonthOfYear(), today.getDayOfMonth(), 0, 0, 0, 0);
        DateTime compareDay = new DateTime(a);
        compareDay = new DateTime(compareDay.getYear(), compareDay.getMonthOfYear(), compareDay.getDayOfMonth(), 0, 0, 0, 0);
        return Days.daysBetween(today, compareDay).getDays();
    }

    public static boolean compareDateIgnoreMillisecond(Date date, Date compareDate) {
        return date == null && compareDate == null ? true : (date == null && compareDate != null ? false : (date != null && compareDate == null ? false : date.getTime() / 1000L == compareDate.getTime() / 1000L));
    }

    public static int getDay(int second) {
        return second / 86400;
    }

    public static String getCompareWithTodayDateString(Date date) {
        int days = Math.abs(compareDaysWithToday(date));
        String dateString = "";
        if (days == 0) {
            dateString = "今天";
        } else if (days == 1) {
            dateString = "昨天";
        } else if (days == 2) {
            dateString = "2天前";
        } else if (days == 3) {
            dateString = "3天前";
        } else if (days == 4) {
            dateString = "4天前";
        } else if (days == 5) {
            dateString = "5天前";
        } else if (days == 6) {
            dateString = "6天前";
        } else if (days > 6 && days <= 14) {
            dateString = "1周前";
        } else if (days > 14 && days <= 21) {
            dateString = "2周前";
        } else if (days > 21 && days <= 30) {
            dateString = "3周前";
        } else if (days > 30) {
            dateString = "1月前";
        } else if (days > 365) {
            dateString = "1年前";
        } else if (days > 1095) {
            dateString = "3年前";
        }

        return dateString;
    }

    public static int compareMinutes(Date now, Date compareDate) {
        return (int) (now.getTime() - compareDate.getTime()) / '\uea60';
    }

    public static int getDayOfMonth(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.getDayOfMonth();
    }

    public static int getDateOfMonth(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.dayOfMonth().getMaximumValue();
    }

    public static int compareYear(Date date) {
        DateTime btd = new DateTime(date);
        DateTime nowDate = new DateTime();
        int year = 0;
        if (nowDate.getMonthOfYear() > btd.getMonthOfYear()) {
            year = nowDate.getYear() - btd.getYear();
        } else if (nowDate.getMonthOfYear() < btd.getMonthOfYear()) {
            year = nowDate.getYear() - btd.getYear() - 1;
        } else if (nowDate.getMonthOfYear() == btd.getMonthOfYear()) {
            if (nowDate.getDayOfMonth() >= btd.getDayOfMonth()) {
                year = nowDate.getYear() - btd.getYear();
            } else {
                year = nowDate.getYear() - btd.getYear() - 1;
            }
        }

        return year;
    }

    public static String compareDaysWithDate(Date date, Date date2) {
        StringBuilder msg = new StringBuilder();
        int minutes = (int) Math.abs((date.getTime() - date2.getTime()) / 60000L);
        if (minutes / 60 > 0 && minutes / 60 / 24 <= 0) {
            msg.append(minutes / 60 + "小时");
        }

        if (minutes / 60 / 24 > 0) {
            msg.append(minutes / 60 / 24 + "天");
            msg.append(minutes / 60 % 24 + "小时");
        }

        return msg.toString();
    }


    public static Date identityCard2Date(String identityCard) {
        try {
            String e;
            if (identityCard.length() == 18) {
                e = identityCard.substring(6, 14);
                return formatDateString(e, "yyyyMMdd");
            } else if (identityCard.length() == 15) {
                e = identityCard.substring(6, 12);
                return formatDateString(e, "yyMMdd");
            } else {
                return null;
            }
        } catch (Exception var2) {
            return null;
        }
    }


    public static Date parseStrToDate(String dateStr, String formatStr) {
        Date retDate = null;
        if (StringUtils.isNotEmpty(dateStr)) {
            SimpleDateFormat simpleDateFormat = null;
            if (StringUtils.isNotEmpty(formatStr)) {
                if (formatStr.equals("yyyy-MM-dd")) {
                    simpleDateFormat = SDF_YMD;
                } else if (formatStr.equals("HH:mm:ss")) {
                    simpleDateFormat = SDF_HMS;
                } else if (formatStr.equals("yyyy-MM-dd HH:mm:ss")) {
                    simpleDateFormat = SDF_YMD_HMS;
                } else if (formatStr.equals("yyyy-MM-dd HH:mm:ss.SSS")) {
                    simpleDateFormat = SDF_YMD_HMSS;
                } else {
                    simpleDateFormat = new SimpleDateFormat(formatStr);
                }
            } else {
                simpleDateFormat = SDF_YMD_HMS;
            }

            try {
                retDate = simpleDateFormat.parse(dateStr);
            } catch (ParseException var5) {
                var5.printStackTrace();
            }
        }

        return retDate;
    }

    public static String formateDateToStr(Date srcDate, String formatStr) {
        String retStr = "";
        if (srcDate != null) {
            SimpleDateFormat simpleDateFormat = null;
            if (StringUtils.isNotEmpty(formatStr)) {
                if (formatStr.equals("yyyy-MM-dd")) {
                    simpleDateFormat = SDF_YMD;
                } else if (formatStr.equals("HH:mm:ss")) {
                    simpleDateFormat = SDF_HMS;
                } else if (formatStr.equals("yyyy-MM-dd HH:mm:ss")) {
                    simpleDateFormat = SDF_YMD_HMS;
                } else if (formatStr.equals("yyyy-MM-dd HH:mm:ss.SSS")) {
                    simpleDateFormat = SDF_YMD_HMSS;
                } else {
                    simpleDateFormat = new SimpleDateFormat(formatStr);
                }
            } else {
                simpleDateFormat = SDF_YMD_HMS;
            }

            retStr = simpleDateFormat.format(srcDate);
        }

        return retStr;
    }

    public static Date getNow() {
        Calendar now = Calendar.getInstance();
        return now.getTime();
    }

    public static String getNowDateString() {
        return formateDateToStr(getNow(), "yyyy-MM-dd");
    }

    public static String getNowDateTimeString() {
        return formateDateToStr(getNow(), "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getAdjustTime(Date date, int day, int hour, int minute, int second, long microsecond) {
        Date d;
        if (date == null) {
            d = getNow();
        } else {
            d = date;
        }

        return new Date(d.getTime() + 86400000L * (long) day + 3600000L * (long) hour + 60000L * (long) minute + 1000L * (long) second + microsecond);
    }

    public static void setAdjustTime(Date date, int day, int hour, int minute, int second, long microsecond) {
        if (date != null) {
            date.setTime(date.getTime() + 86400000L * (long) day + 3600000L * (long) hour + 60000L * (long) minute + 1000L * (long) second + microsecond);
        }
    }

    public static Date setDayFirstTime(Date date) {
        if (date == null) {
            return null;
        } else {
            long l = date.getTime();
            l -= (l + TIMEZONE_OFFSET) % 86400000L;
            date.setTime(l);
            return date;
        }
    }

    public static Date setDayLastTime(Date date) {
        if (date == null) {
            return null;
        } else {
            long l = date.getTime();
            l = l - (l + TIMEZONE_OFFSET) % 86400000L + 86400000L - 1L;
            date.setTime(l);
            return date;
        }
    }

    public static Date getYesterday(Date date) {
        return getAdjustTime(date, -1, 0, 0, 0, 0L);
    }

    public static Date getYesterday() {
        return getYesterday((Date) null);
    }

    public static Date getTomorrow(Date date) {
        return getAdjustTime(date, 1, 0, 0, 0, 0L);
    }

    public static Date getTomorrow() {
        return getTomorrow((Date) null);
    }


    public static Date getEndOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(1);
        int month = cal.get(2) + 1;
        if (month > 11) {
            ++year;
            month = 0;
        }

        cal.set(year, month, 0);
        return setDayLastTime(cal.getTime());
    }

    public static Date getStartOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(7);
        cal.add(5, -(dayOfWeek - 2));
        return setDayFirstTime(cal.getTime());
    }

    public static Date getEndOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(7);
        cal.add(5, -(dayOfWeek - 8));
        return setDayLastTime(cal.getTime());
    }

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(2, month - 1);
        cal.set(5, day);
        return cal.getTime();
    }

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(1);
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(2) + 1;
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(5);
    }

    public static Date getRollDate(Date date, int rollType, int amount) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.roll(rollType, amount);
        return c.getTime();
    }

    public static int compareDate(Date date1, Date date2) {
        if (date1 == date2) {
            return 0;
        } else if (date1 == null) {
            return -1;
        } else if (date2 == null) {
            return 1;
        } else {
            long l = date1.getTime() - date2.getTime();
            return l == 0L ? 0 : (l > 0L ? 1 : -1);
        }
    }

    public static Date getStartOfLastTwoWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(7);
        cal.add(5, -(dayOfWeek + 6));
        return cal.getTime();
    }

    public static Date getStartQuerterDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(2) + 1;
        byte startMonth;
        if (month >= 1 && month <= 3) {
            startMonth = 1;
        } else if (month >= 4 && month <= 6) {
            startMonth = 4;
        } else if (month >= 7 && month <= 9) {
            startMonth = 7;
        } else {
            startMonth = 10;
        }

        int startMonth1 = startMonth - 1;
        cal.set(2, startMonth1);
        cal.set(5, 1);
        Date startQuarterDay = cal.getTime();
        return startQuarterDay;
    }

    public static Date getEndQuerterDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(2) + 1;
        byte endMonth;
        if (month >= 1 && month <= 3) {
            endMonth = 3;
        } else if (month >= 4 && month <= 6) {
            endMonth = 6;
        } else if (month >= 7 && month <= 9) {
            endMonth = 9;
        } else {
            endMonth = 12;
        }

        int endMonth1 = endMonth - 1;
        cal.set(5, 1);
        cal.set(2, endMonth1);
        Date endQuarterDay = getEndOfMonth(cal.getTime());
        return endQuarterDay;
    }


    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }

        return formatDate;
    }

    public static String formatDateTime(Date date) {
        return formatDate(date, new Object[]{"yyyy-MM-dd HH:mm:ss"});
    }

    public static String getTime() {
        return formatDate(new Date(), new Object[]{"HH:mm:ss"});
    }

    public static String getDateTime() {
        return formatDate(new Date(), new Object[]{"yyyy-MM-dd HH:mm:ss"});
    }

    public static String getMonth() {
        return formatDate(new Date(), new Object[]{"MM"});
    }

    public static String getDay() {
        return formatDate(new Date(), new Object[]{"dd"});
    }

    public static String getWeek() {
        return formatDate(new Date(), new Object[]{"E"});
    }

    public static Date parseDate(String str, String parsePatterns) {
        SimpleDateFormat sdf = new SimpleDateFormat(parsePatterns);
        try {
            return sdf.parse(str);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    public static long pastDays(Date date) {
        long t = (new Date()).getTime() - date.getTime();
        return t / 86400000L;
    }

    public static long pastHour(Date date) {
        long t = (new Date()).getTime() - date.getTime();
        return t / 3600000L;
    }

    public static long pastMinutes(Date date) {
        long t = (new Date()).getTime() - date.getTime();
        return t / 60000L;
    }

    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / 86400000L;
        long hour = timeMillis / 3600000L - day * 24L;
        long min = timeMillis / 60000L - day * 24L * 60L - hour * 60L;
        long s = timeMillis / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
        long sss = timeMillis - day * 24L * 60L * 60L * 1000L - hour * 60L * 60L * 1000L - min * 60L * 1000L - s * 1000L;
        return (day > 0L ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (double) ((afterTime - beforeTime) / 86400000L);
    }

    private static SimpleDateFormat getDateFormat(String pattern) throws RuntimeException {
        SimpleDateFormat dateFormat = (SimpleDateFormat) threadLocal.get();
        if (dateFormat == null) {
            Object var2 = object;
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }

        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    private static int getInteger(Date date, int dateType) {
        int num = 0;
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            num = calendar.get(dateType);
        }

        return num;
    }


    public static Date StringToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(pattern).parse(date);
            } catch (Exception var4) {
                ;
            }
        }

        return myDate;
    }


    /**
     * @param startHour 00:22
     * @param endHour   00:44
     * @return 相差分钟数
     */
    public static int hourDiffByMin(String startHour, String endHour) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long min = 0;
        try {
            Date d1 = df.parse(endHour);
            Date d2 = df.parse(startHour);
            long diff = d1.getTime() - d2.getTime();
            min = diff / (1000 * 60);
        } catch (Exception e) {
        }
        return Integer.parseInt(new Long(min).toString());
    }

    /*
     * 返回相差小时数
     */
    public static int hourDiffByHOUR(String startHour, String endHour) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long hour = 0;
        try {
            Date d1 = df.parse(endHour);
            Date d2 = df.parse(startHour);
            long diff = d1.getTime() - d2.getTime();
            hour = diff / (1000 * 60 * 60);
        } catch (Exception e) {
        }
        return Integer.parseInt(new Long(hour).toString());
    }

    /*
     * 相差小时数
     */
    public static double diffHour(Date startTime, Date endTime) {
        double hour = 0;
        try {
            long diff = endTime.getTime() - startTime.getTime();
            return MathUtil.divideAndRound((double) diff, (double) 1000 * 60 * 60);
        } catch (Exception e) {
        }
        return hour;
    }

    /**
     * 获取小时
     *
     * @param date
     * @return
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 相差秒数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long diffSecond(Date startTime, Date endTime) {
        long second = 0;
        try {
            long diff = endTime.getTime() - startTime.getTime();
            second = diff / 1000;
        } catch (Exception e) {

        }
        return second;

    }

    /**
     * hour小时后的时间
     *
     * @param hour
     * @return
     */
    public static int getAddHour(int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, hour);
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取指定年月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * 获取指定年月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static Integer getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    /**
     * 获取当前月
     *
     * @return
     */
    public static Integer getCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * 获取昨天时间
     *
     * @return
     */
    public static String getYesterdayStr() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, -24);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime());
    }

    /**
     * 获取星期
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }

    /**
     * 日期格式转换
     *
     * @param dateStr
     * @param patternSource 原格式
     * @param patternDest   目标格式
     * @return
     */
    public static String changeStrFormat(String dateStr, String patternSource, String patternDest) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(patternSource);
            Date date = sdf.parse(dateStr);
            SimpleDateFormat sdfResult = new SimpleDateFormat(patternDest);
            return sdfResult.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 2019-09-27  转为 27Sep19
     *
     * @param time
     * @return
     */
    public static String converTo(String time) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMM", Locale.UK);
        String format = null;
        try {
            format = sdf2.format(sdf1.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String year = getCurrentYear().toString().substring(2, 4);
        return format + year;
    }


    /*
     * 是否在明天之后
     */
    public static boolean isAfterTomorrow(Date nowDate, Date compareDate) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(compareDate);
            cal.add(Calendar.DATE, 1);//明天
            Date time = cal.getTime();
            return nowDate.getTime() - time.getTime() > 0 ? true : false;
        } catch (Exception e) {

        }
        return false;
    }


    /**
     * 获取下个月的第一个星期?
     *
     * @param date
     * @param week 星期几
     * @return
     */
    public static String getNextMonthFirstWeek(Date date, int week) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, 1);//下个月
            cal.set(Calendar.DAY_OF_MONTH, 1);//天设为一个月的第一天
            while (true) {
                //星期?
                if (cal.get(Calendar.DAY_OF_WEEK) == week) {
                    return format(cal.getTime(), YEAR_MONTH_DAY);
                }
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (Exception e) {

        }
        return null;
    }


    /**
     * 获取指定日期所在周的周一
     *
     * @param date 日期
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            c.add(Calendar.DAY_OF_MONTH, -1);
        }
        c.add(Calendar.DATE, c.getFirstDayOfWeek() - c.get(Calendar.DAY_OF_WEEK) + 1);
        return c.getTime();
    }

    /**
     * 获取指定日期所在周的周日
     *
     * @param date 日期
     */
    public static String getLastDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // 如果是周日直接返回
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            return format(date, YEAR_MONTH_DAY);
        }
        c.add(Calendar.DATE, 7 - c.get(Calendar.DAY_OF_WEEK) + 1);
        return format(c.getTime(), YEAR_MONTH_DAY);
    }


    /**
     * 转换日期为Long
     *
     * @param time
     * @param pattern
     * @return
     */
    public static Long castDate2Long(String time, String pattern) {
        String defaultPattern = DateTimeUtil.YEAR_MONTH_DAY;
        if (StringUtils.isNotEmpty(pattern)) {
            defaultPattern = pattern;
        }
        return DateTimeUtil.formatDate2Str(time, defaultPattern).getTime();
    }

    /**
     * 两个时间相比较 大于等于
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean compareTime(String time1, String time2, String pattern) {
        if (StringUtils.isEmpty(time1) || StringUtils.isEmpty(time2)) {
            return false;
        }
        Long time1Long = castDate2Long(time1, pattern);
        Long time2Long = castDate2Long(time2, pattern);
        if (time1Long >= time2Long) {
            return true;
        }
        return false;
    }

    /**
     * 两个时间相比较 大于
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean compareTimeGreater(String time1, String time2, String pattern) {
        if (StringUtils.isEmpty(time1) || StringUtils.isEmpty(time2)) {
            return false;
        }
        Long time1Long = castDate2Long(time1, pattern);
        Long time2Long = castDate2Long(time2, pattern);
        if (time1Long > time2Long) {
            return true;
        }
        return false;
    }


    /**
     * 根据日期获取星期
     *
     * @param dateStr
     * @return
     */
    public static Integer castDateToWeek(String dateStr) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(dateStr);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            week = (week == 0 ? 7 : week);
            return week;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取日期差
     *
     * @param now
     * @param number 1  +1 明天  -1 昨天
     * @return
     */
    public static String getDate(String now, int number) {
        Date nowDate = DateTimeUtil.formatDate2Str(now, DateTimeUtil.YEAR_MONTH_DAY);
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(nowDate);
            cal.add(Calendar.DATE, number);
            return DateTimeUtil.format(cal.getTime(), DateTimeUtil.YEAR_MONTH_DAY);
        } catch (Exception e) {

        }
        return null;
    }


    /**
     * 几分钟后的时间
     *
     * @param timeStr
     * @param minutes
     * @return
     */
    public static String timeAddMins(String timeStr, int minutes) {
        Date date = formatDate2Str(timeStr, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        date.setTime(date.getTime() + minutes * 60 * 1000);
        return format(date, YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
    }

    /**
     * 得到当前时间的前后N小时
     * hour为正负值
     *
     * @param hour
     * @return
     */
    public static String getBeforeByHourTime(int hour) {
        String returnstr = "";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
        SimpleDateFormat df = new SimpleDateFormat(YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        returnstr = df.format(calendar.getTime());
        return returnstr;
    }


    /**
     * 计算某个时间的前后N小时
     * hour为正负值
     *
     * @param hour
     * @return
     */
    public static String getOffsetHourTime(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
        return DateTimeUtil.format(calendar.getTime(), DateTimeUtil.YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
    }


    /**
     * 是否满足时间区间
     *
     * @param hourSecondStart
     * @param hourSecondEnd
     * @return
     */
    public static boolean isTimeRange(String hourSecondStart, String hourSecondEnd) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        try {
            Date now = df.parse(df.format(new Date()));
            Date begin = df.parse(hourSecondStart);
            Date end = df.parse(hourSecondEnd);
            Calendar nowTime = Calendar.getInstance();
            nowTime.setTime(now);
            Calendar beginTime = Calendar.getInstance();
            beginTime.setTime(begin);
            Calendar endTime = Calendar.getInstance();
            endTime.setTime(end);
            if (nowTime.before(endTime) && nowTime.after(beginTime)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {

        }
        return false;
    }


    /**
     * 获取下个月
     *
     * @return
     */
    public static String nextMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);
        return format(calendar.getTime(), YEAR_MONTH);
    }

    /**
     * 获取上个月
     *
     * @return
     */
    public static String preMonthDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, -1);
        return format(calendar.getTime(), YEAR_MONTH);
    }

    /**
     * 获取过去/未来第几天的日期
     *
     * @param day
     * @return
     */
    public static String getPastOrAfterDate(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime());
    }

}


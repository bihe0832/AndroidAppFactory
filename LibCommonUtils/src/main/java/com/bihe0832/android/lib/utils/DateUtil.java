package com.bihe0832.android.lib.utils;

import com.bihe0832.android.lib.log.ZLog;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by zixie on 2017/11/10.
 * 各式时间转换
 */
public class DateUtil {

    private static final String TAG = "DateUtil";
    public static final int ONE_DAY_MILLSEC = 24 * 60 * 60 * 1000;

    public static String getDateEN(long currentTime, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.US);
        String date = format.format(new Date(currentTime));
        return date;
    }

    public static String getDateEN(long currentTime) {
        return getDateEN(currentTime, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentDateEN(String pattern) {
        return getDateEN(System.currentTimeMillis(), pattern);
    }


    public static String getCurrentDateEN() {
        return getCurrentDateEN("yyyy-MM-dd HH:mm:ss");
    }

    //单位：s
    public static int getDateDistance(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            return (int) (Math.abs(dt2.getTime() - dt1.getTime()) / 1000);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return -1;
    }

    public static long getTime(String dateString, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            Date dt = df.parse(dateString);
            return dt.getTime();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return -1;
    }

    public static int compareDate(Date date1, String date2, String pattern2) {
        try {
            long dt2 = getTime(date2, pattern2);
            long dt1 = date1.getTime();
            ZLog.d(TAG, "dt1.getTime:" + dt1 + ",dt2.getTime:" + dt2);
            if (dt1 >= dt2) {
                return 1;
            } else if (dt1 < dt2) {
                return -1;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static int compareDate(Date date1, String date2) {
        return compareDate(date1, date2, "yyyyMMddHHmmss");
    }

    //计算时长，结果为例如 61 结果为 1min 1s
    public static String formatElapsedTime(long elapsedSeconds) {
        long hours = 0;
        long minutes = 0;
        long seconds = 0;
        if (elapsedSeconds >= 3600) {
            hours = elapsedSeconds / 3600;
            elapsedSeconds -= hours * 3600;
        }
        if (elapsedSeconds >= 60) {
            minutes = elapsedSeconds / 60;
            elapsedSeconds -= minutes * 60;
        }
        seconds = elapsedSeconds;

        if (hours > 0) {
            return hours + "hour " + minutes + "min " + seconds + "s";
        } else {
            return minutes + "min " + seconds + "s";
        }
    }

    //计算时长，结果为例如 61 结果为01:01
    public static String getDateMMAndSS(long elapsedSeconds) {
        long minutes = 0;
        long seconds = 0;
        if (elapsedSeconds >= 60) {
            minutes = elapsedSeconds / 60;
            elapsedSeconds -= minutes * 60;
        }
        seconds = elapsedSeconds;
        return String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }


    public static String getDateCompareResult(long oldTimestamp) {
        long minute = 1000 * 60;
        long hour = minute * 60;
        long day = hour * 24;
        long month = day * 30;
        long year = month * 12;
        long currentTimestamp = System.currentTimeMillis();
        long diffValue = currentTimestamp - oldTimestamp;
        long yearC = diffValue / year;
        long monthC = diffValue / month;
        long weekC = diffValue / (7 * day);
        long dayC = diffValue / day;
        long hourC = diffValue / hour;
        long minC = diffValue / minute;
        if (yearC > 0) {
            return new SimpleDateFormat("yyyy年M月d日").format(new Date(oldTimestamp));
//            return yearC + "年前";
        } else if (monthC > 0) {
            return new SimpleDateFormat("M月d日").format(new Date(oldTimestamp));
//            return monthC + "月前";
        } else if (weekC > 0) {
            return new SimpleDateFormat("M月d日").format(new Date(oldTimestamp));
//            return weekC + "周前";
        } else if (dayC > 1) {
            return new SimpleDateFormat("M月d日").format(new Date(oldTimestamp));
        } else if (dayC > 0) {
            return "昨天";
        } else if (hourC > 0) {
            return hourC + "小时前";
        } else if (minC > 0) {
            return minC + "分钟前";
        } else {
            return "刚刚";
        }
    }

    public static String getDateCompareResult1(long oldTimestamp) {
        long minute = 1000 * 60;
        long hour = minute * 60;
        long day = hour * 24;
        long month = day * 30;
        long year = month * 12;
        long currentTimestamp = System.currentTimeMillis();
        long diffValue = currentTimestamp - oldTimestamp;
        long yearC = diffValue / year;
        long monthC = diffValue / month;
        long weekC = diffValue / (7 * day);
        long dayC = diffValue / day;
        long hourC = diffValue / hour;
        long minC = diffValue / minute;
        if (yearC > 0) {
            return yearC + "年前";
        } else if (monthC > 0) {
            return monthC + "月前";
        } else if (weekC > 0) {
            return weekC + "周前";
        } else if (dayC > 1) {
            return new SimpleDateFormat("M月d日").format(new Date(oldTimestamp));
        } else if (dayC > 0) {
            return "昨天";
        } else if (hourC > 0) {
            return hourC + "小时前";
        } else if (minC > 0) {
            return minC + "分钟前";
        } else {
            return "刚刚";
        }
    }

    public static boolean isToady(long timestamp) {
        Calendar pre = Calendar.getInstance();
        pre.setTimeInMillis(timestamp);
        Calendar cur = Calendar.getInstance();
        cur.setTimeInMillis(System.currentTimeMillis());
        ZLog.d(TAG, "isToady --" + pre.get(Calendar.YEAR) + "--" + cur.get(Calendar.YEAR) + "--" + cur
                .get(Calendar.DAY_OF_YEAR) + "--" + pre.get(Calendar.DAY_OF_YEAR));
        if (pre.get(Calendar.YEAR) == cur.get(Calendar.YEAR)) {
            int diffDay = cur.get(Calendar.DAY_OF_YEAR) - pre.get(Calendar.DAY_OF_YEAR);
            if (0 == diffDay) {
                return true;
            }
        }

        return false;
    }
}
package com.single.monthview;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by xiangcheng on 18/7/5.
 */

public class Utils {
    //计算每个月从星期几的算起
    public static int getWeek(int year, int month) {
        String strMonth;
        if (month < 10) {
            strMonth = "0" + String.valueOf(month);
        } else {
            strMonth = String.valueOf(month);
        }
        String parse = year + "-" + strMonth + "-01";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(parse));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return c.get(Calendar.DAY_OF_WEEK);
    }

    //计算每个月需要几行显示完
    public int getMonthLineCount(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1);
        return c.get(Calendar.DAY_OF_WEEK);
    }

//    private int getWeek(String pTime) {
//
//        String Week = "";
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar c = Calendar.getInstance();
//        try {
//
//            c.setTime(format.parse(pTime));
//
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
//            Week += "天";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
//            Week += "一";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
//            Week += "二";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
//            Week += "三";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
//            Week += "四";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
//            Week += "五";
//        }
//        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
//            Week += "六";
//        }
//        return Week;
//    }

    /**
     * 获取某月的天数
     *
     * @param year  年
     * @param month 月
     * @return 某月的天数
     */
    static int getMonthDaysCount(int year, int month) {
        int count = 0;
        //判断大月份
        if (month == 1 || month == 3 || month == 5 || month == 7
                || month == 8 || month == 10 || month == 12) {
            count = 31;
        }

        //判断小月
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            count = 30;
        }

        //判断平年与闰年
        if (month == 2) {
            if (isLeapYear(year)) {
                count = 29;
            } else {
                count = 28;
            }
        }
        return count;
    }

    /**
     * 是否是闰年
     *
     * @param year year
     * @return return
     */
    static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }
}

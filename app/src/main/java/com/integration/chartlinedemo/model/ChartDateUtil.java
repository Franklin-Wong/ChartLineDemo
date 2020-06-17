package com.integration.chartlinedemo.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Wongerfeng on 2020/5/18.
 */
public class ChartDateUtil {


    public static long getDateNow() {

        Date date = new Date();
        return date.getTime();
    }

    public static String getDate(int day) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        calendar.add(Calendar.DATE, -day);
        String date = simpleDateFormat.format(calendar.getTime());

        return date;

    }

}

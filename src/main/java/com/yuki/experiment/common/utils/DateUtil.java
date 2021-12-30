package com.yuki.experiment.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static boolean equals(Calendar calendar, Date date){
        return calendar.getTime().equals(date);
    }

    public static boolean equals(Date date,Calendar calendar){
        return equals(calendar,date);
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        Date parse = format.parse("2012-02-05");
        Calendar instance = Calendar.getInstance();
        instance.setTime(parse);
        System.out.println(instance.getTime());
        System.out.println(parse);
        System.out.println(equals(instance,parse));


    }
}

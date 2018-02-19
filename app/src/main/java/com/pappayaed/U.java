package com.pappayaed;

import android.util.Log;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by yasar on 19/4/17.
 */

public class U implements Serializable {
    private static final SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");

    public static String getTime(String s) {
        Date date = null;
        try {
            date = df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = df.format(date);

        return result;
    }

    public static Date getDate(String s) {
        Date date = null;
        try {
            date = df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getTime(Date date) {
        String result = df.format(date);
        return result;
    }


    public static Date getDatePlus30Min(String s) {
        Calendar now = Calendar.getInstance();

        Date date = getDate(s);

        now.setTime(date);

        now.add(Calendar.MINUTE, 30);

        Date teenMinutesFromNow = now.getTime();

        return teenMinutesFromNow;
    }

    public static Date getDateMins30Min(String s) {
        Calendar now = Calendar.getInstance();

        Date date = getDate(s);

        now.setTime(date);

        now.add(Calendar.MINUTE, -30);

        Date teenMinutesFromNow = now.getTime();

        return teenMinutesFromNow;
    }

    public static Date getDateMi(String s) {
        Calendar now = Calendar.getInstance();

        Date date = getDate(s);

        now.setTime(date);

        now.add(Calendar.MINUTE, -90);

        Date teenMinutesFromNow = now.getTime();

        return teenMinutesFromNow;
    }



    public static void Clustered() {

        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

        Log.e(TAG, "onCreate: " + currentDateTimeString);

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
        String formattedDate = df.format(c.getTime());

        Date cDate = null;
        try {
            cDate = df.parse("10:00 AM");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate1 = df.format(cDate);
        Log.e(TAG, "onCreate: " + formattedDate + "     " + formattedDate1);


    }

}

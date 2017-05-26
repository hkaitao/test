package com.stream.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dpingxian on 2017/5/24.
 */
public class SimsTime {

    private static long startTime;

    static {
        try {
            startTime = dateToStamp("2015-01-01 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static  long getTime(){
        startTime = startTime + 11l;
        return startTime;
    }

    public static void main(String[] args) throws ParseException {
        long time = SimsTime.getLong("2015-01-02 00:00:00");
        Date d = new Date(time+86400000);
        System.out.println(d);

    }

    private static long dateToStamp(String s) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        return ts;
    }

    public static long getLong(String s){
        long time = 0L;
        try {
            time =   dateToStamp(s);
        } catch (ParseException e) {
            System.exit(0);
        }
        return time;
    }
}

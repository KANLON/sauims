package com.fekpal.tool;

/**
 * Created by hasee on 2017/8/18.
 */
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTool {
    /**
     *   获取时间  返回毫秒级时间
     * @return 返回毫秒级时间
     */
    public static String getTime() {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        //获取毫秒时间
        Long date = calendar.getTime().getTime();

        //String dateStringPaString = sdf.format(date);
        //System.out.println(dateStringPaString);

        return date.toString();
    }

    /**
     * 判断时间是否已经过了10分钟
     * @param time 要比较的时间毫秒值
     * @return 返回比较结果
     */
    public static boolean cmpTime(String time) {
        long tempTime = Long.parseLong(time);

        //在获取现在的时间
        Calendar calendar = Calendar.getInstance();
        //获取毫秒时间
        Long date = calendar.getTime().getTime();

        if(date - tempTime > 600000 ) {   //10分钟
            return false;
        } else {
            return true;
        }

    }
}
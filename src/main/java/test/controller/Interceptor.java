package test.controller;

import org.junit.Test;

import java.util.Calendar;

import static java.lang.System.out;

public class Interceptor {
    @Test
    public void testIndexof(){
        String url = "/msg";
        out.println("/mssg/1".indexOf(url));
    }
    @Test
    public void testDate(){
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        out.println(c);
        out.println(c.getTime());
        out.print(Calendar.getInstance().getTimeInMillis());
    }

    @Test
    public void testA(){
        int x=4,y=0;
        if(Math.pow(x,2)==16){
            y=x;
        }
        if(Math.pow(x,2)<15){
            y=1/x;
        }
        if(Math.pow(x,2)>15){
            y=(int)Math.pow(x,2)+2;
            out.println(y);
        }

    }




}

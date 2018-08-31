package test.controller;

import org.junit.Test;

import java.util.Scanner;

import static java.lang.System.out;

/**
 * 实现二进制与十进制的互换
 * @author Canlong
 * @date 2018/4/13
 */
public class Test3 {

    /**
     * 1、 编写程序实现十进制小数x=0.7 的二进制转换，并分析其误差（即重新将二进制小数转成十进制小数，和原十进制小数进行比较。）
     2、编程实现十进制数 x=5.9的二进制转换。
     * @param args
     */
    public static void main(String[] args){

        double x = 0.7;

        //将十进制小数转化为二进制
        int[] binX = new int[10];
        String binXSB="";
        double x1 = x;
        double x2=0;
        for(int i=0;i<binX.length;i++){
            x2 = x1+x1;
            x1 =Math.floor(x2);
            binX[i]=(int)x1;
            x1=x2-x1;
            binXSB += Integer.toString(binX[i]);
        }
        String binXStr = binXSB.toString();
        out.println("0.7的近似二进制数为（精确到小数点后1000位）："+binXStr);

        //将二进制小数转化为十进制
        double decX = 0.0;
        //位数
        int k =0;
        for(int i=0;i<binXStr.length();i++){
            int exp = binXStr.charAt(i)-'0';
            exp = -(i+1)*exp;
            if(exp!=0) {
                decX += Math.pow(2, exp);
            }
        }
        out.println("二进制小数为;"+binXStr+"。\r\n其对应的十进制为："+decX);
        out.println("误差为："+(x-decX));


        //将十进制数转化为二进制

        double decX1 = 5.9;
        int decInt = (int) Math.floor(decX1);
        String  binInt = decInt2Bin(decInt);
        out.println("5对应的二进制为："+binInt);



    }
    //将十进制整数转换为二进制
    public static String decInt2Bin(int decInt){
        int index = 0;
        int rem = 1;
        String binStr="";
        while(rem!=0){
            rem = decInt%2;
            decInt = decInt/2;
            binStr += Integer.toString(rem);
        }
        return binStr;

    }

    @Test
    public void testStringSplit(){
        String logo = "eRr6Q47BHQDeAuY0VQlQ.png";
        String[] logos = logo.split("/.");

    }


}

package test.controller;

import com.fekpal.common.utils.TimeUtil;

import java.io.*;
import static java.lang.System.out;

/**
 * *构建信源模型
 *  @author Canlong
*/
public class Test2 {

    public static void main(String[] args){
        
        out.println(TimeUtil.currentTime());
//        array5Col();
//        try {
//            countNum();
//        } catch (IOException e){
//            throw new RuntimeException("读取不到文件");
//        }
    }


    /**
     * 统计文件中26个字母的频率并计算信息熵
     * @throws IOException
     */
    public static void countNum() throws IOException {
        //文件路径
        String strPath  = "C:/Users/hasee/Desktop/Types of Speech.txt";
        //26个字母出现的总次数
        double sumAllNum = 0;
        //存储频率
        double[] frequency = new double[26];
        //模型的信息熵 entropy
        double infoEntr = 0.0;

        //读取文件
        BufferedReader bw = new BufferedReader(new InputStreamReader(new FileInputStream(strPath),"UTF-8"));
        //存储文件的字符串
        String textStr = "";
        String line = null;
        while((line=bw.readLine())!= null){
            textStr += line;
        }
        out.println("要统计的字符串为：\r\n"+textStr);
        textStr = textStr.toLowerCase();
        //统计字符串各个字母的个数
        char[] textChar = textStr.toCharArray();
        //存放26个字母和对应的次数
        char[][] char26AndNum = new char[2][26];
        //将26个字母放入到字符数组
        for(int i=97;i<123;i++){
            char26AndNum[0][i-97]=(char)(i);
        }
        //比较字符串和26个字母的是否相等，并且计算次数
        for(int i=0;i<textChar.length;i++){
            //法一：循环26个字母，判断是否相等
//            for(int j=0;j<char26AndNum[0].length;j++){
//                //如果字符相等，则对应的二维数组+1
//                if(Character.toString(textChar[i]).equals(Character.toString(char26AndNum[0][j]))){
//                    char26AndNum[1][j]++;
//                }
//            }
            //法二，将26个字母ASCII码-'a'作为数组下标,当字母等于那个数组下标时，直接将该元素++
            if(textChar[i] >= 'a' && textChar[i]<='z'){
                char26AndNum[1][textChar[i]-'a']++;
            }
        }
        //输出26个字母及其所对应次数，即计算频数
        for(int i=0;i<char26AndNum[1].length;i++){
            sumAllNum += (double)char26AndNum[1][i];
        }
        out.println("总次数为："+sumAllNum);
        //计算频率
        for(int i=0;i<char26AndNum[1].length;i++) {
            frequency[i] = char26AndNum[1][i] / sumAllNum;
            out.println("字母为：" + char26AndNum[0][i] + "，对应出现的次数为：" + (int) char26AndNum[1][i] + ",其频率为：" + frequency[i]);
            if (frequency[i] != 0) {
                infoEntr -= frequency[i] * (Math.log(frequency[i]) / Math.log(2));
            }
        }
        out.println("信息熵为："+infoEntr);
        //计算信息熵，信息熵=频率1*log2（1/频率）
    }

    /**
     * 随机产生一个一行五列数组，使其恰好符合信源概率的要求
     */
    public static void array5Col(){
        //1的概率为0.2，2的概率为0.3，3的概率为0.5
        int array[] = {1,1,2,2,2,3,3,3,3,3};
        //一行五列的数组
        int array5[][] = new int[1][5];
        out.println("其信源概率为：1的概率为0.2，2的概率为0.3，3的概率为0.5。产生一个一行五列数组：");
        for(int i=0;i<5;i++) {
            int randomNum = (int) Math.ceil(Math.random() * 3);
            array5[0][i]=randomNum;
            out.print(array5[0][i]+"，");
        }
        //换行
        out.println();
    }
}

package com.fekpal.cons;

import java.io.File;

/**
 * Created by hasee on 2017/8/15.
 */
public class WebPath {

    //用于接收项目根目录

    //获取到D:/masterspring/SAU/target/classes/com/fekpal/cons/下的文件目录，基目录
    public final static String consPath =  WebPath.class.getResource("").getPath();

    //获取到D:\masterspring\SAU\target\classes\com\fekpal ，fekpal包下目录
    public final static String fekpalPath=  new File(consPath).getParent();

    //D:\masterspring\SAU\target\classes,项目classes目录
    public final static String classesPath =  new File(new File(fekpalPath).getParent()).getParent();

    //获取web项目的根目录  D:\masterspring\SAU.但是在idea中好像不可行，应该发布在服务器上可以了
    public final static String rootPath =  new File(new File(classesPath).getParent()).getParent();


}

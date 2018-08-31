package com.fekpal.common.utils;

import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.out;

/**
 * Created by APone on 2017/8/14.
 * word文档转html
 */

public class WordFileUtil {

    private static final String regex = "";

    private static final Pattern pattern = Pattern.compile(regex);


    /**
     * 转化为网页文件docx版,注意命名唯一性,存放路径必须存在,可能会存在异常，属于极个别现象
     *
     * @param wordFileName  String word文件的存放路径 如 X:/xxx/xxx.docx
     * @param htmlFileName  String 网页文件的存放路径 如 X:/xxx/xxx.html
     * @param htmlImageName String 图片文件夹的存放路径 如 X:/xxx/image/
     * @param imageBaseURL  String 网页中图片的路径，主要用于在网页中显示图片 如 image/
     * @return boolean
     */
    public static boolean convertToHTML(String wordFileName, String htmlFileName, String htmlImageName, String imageBaseURL) {

        OutputStreamWriter outputStreamWriter = null;
        try {
            //读取word文件流
            FileInputStream fileInputStream = new FileInputStream(wordFileName);
            out.println(wordFileName);
            if(fileInputStream==null){throw  new RuntimeException("输入流为空");}
            //创建文档对象，并创建word配置对象
            XWPFDocument document = new XWPFDocument(fileInputStream);
            XHTMLOptions options = XHTMLOptions.create();

            //设置word中的图片转化为网页图片后，网页图片存放的文件夹路径
            options.setExtractor(new FileImageExtractor(new File(htmlImageName)));

            //设置网页里图片的路径(即为<img src="???">)，一般为网页的相对路径，这个设置将影响网页图片的显示
            options.URIResolver(new BasicURIResolver(imageBaseURL));

            //设置网页文件的路径，即为新建网页文件，编码为utf8
            outputStreamWriter = new OutputStreamWriter(new FileOutputStream(htmlFileName), "utf-8");
            XHTMLConverter xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
            //将以上的配置注入，转化开始
            xhtmlConverter.convert(document, outputStreamWriter, options);

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (outputStreamWriter != null) {
                try {
                    outputStreamWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断该文件是否为word文档（格式为docx）
     * 例如*.docx正确 *docx.docx错误
     *
     * @param fileName 文件名
     * @return boolean
     */
    public static boolean isVaild(String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            return false;
        }
        Matcher matcher = pattern.matcher(fileName);
        return matcher.matches();
    }

    public static void main(String[] args) {
        try {
            convertToHTML("D:/00.docx", "D:/ac/00.html", "D:/ac/image/", "image/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

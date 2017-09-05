package com.fekpal.tool;

import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

/**
 * Created by APone on 2017/8/14.
 * word文档转html
 */

public class WordToHTML {


    /**
     * 转化为网页文件docx版,注意命名唯一性
     *
     * @param wordFileName  String word文件的路径 如 X:/xxx/xxx.docx
     * @param htmlFileName  String 网页文件的路径 如 X:/xxx/xxx.html
     * @param htmlImageName String 图片文件夹路径 如 X:/xxx/image/
     * @param imageBaseURL  String 网页中图片的路径 如 image/
     * @return boolean
     */
    public boolean docxToHtml(String wordFileName, String htmlFileName, String htmlImageName, String imageBaseURL) {

        OutputStreamWriter outputStreamWriter = null;
        try {
            //读取word文件流
            FileInputStream fileInputStream = new FileInputStream(wordFileName);
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

    public static void main(String[] args) {
        try {
            new WordToHTML().docxToHtml("D:/00.docx", "D:/ac/00.html", "D:/ac/image/", "image/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.fekpal.tool;



import com.fekpal.cons.WebPath;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.UUID;

/**
 * 对图片，文件进行base64编码和解码的工具类（还没弄好）
 * Created by hasee on 2017/8/15.
 */
public class Base64Image {

    /**
     * 解析base64，返回图片所在路径
     * @param base64Info
     *  @return
     */
    public String decodeBase64(String base64Info) {
        if (StringUtils.isEmpty(base64Info)) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        String[] arr = base64Info.split("base64,");
        File filePath = new File(WebPath.rootPath);
        String picPath = filePath + "/images/" + UUID.randomUUID().toString() + ".jpg";
        try {
            byte[] buffer = decoder.decodeBuffer(arr[1]);
            OutputStream os = new FileOutputStream(picPath);
            os.write(buffer);
            os.flush();
            os.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return picPath;
    }

    /**
     * 图片转化成base64字符串/ 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     * @param inputStream
     * @return
     */
    public static String GetImageStr(InputStream inputStream) {
        String imgFile = "C:/Users/Star/Desktop/test.png";// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
         // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

}

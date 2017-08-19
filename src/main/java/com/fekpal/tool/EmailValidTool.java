package com.fekpal.tool;

import org.apache.commons.lang3.StringUtils;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;


public class EmailValidTool {

    public static void main(String[] args) {

        System.out.println(new EmailValidTool().valid("2078697336@qq.com", "jootmir.org"));
        System.out.println(new EmailValidTool().valid("Canlong2015@126.com", "jootmir.org"));
        System.out.println(new EmailValidTool().valid("s19961234@126.com", "jootmir.org"));
        System.out.println(new EmailValidTool().valid("151612220@m.gduf.edu.cn", "jootmir.org"));        System.out.println(new EmailValidTool().valid("100582783@qq.com", "jootmir.org"));
        System.out.println(new EmailValidTool().valid("10;;0582783@qq.com", "jootmir.org"));
        System.out.println(new EmailValidTool().valid("as05as@127.com", "jootmir.org"));

    }

    /**
     * 验证邮箱是否存在
     * <br>
     * 由于要读取IO，会造成线程阻塞
     *
     * @param toMail
     *         要验证的邮箱
     * @param domain
     *         发出验证请求的域名(是当前站点的域名，可以任意指定)
     * @return
     *         邮箱是否可达
     */
    public static boolean valid(String toMail, String domain) {
        if(StringUtils.isBlank(toMail) || StringUtils.isBlank(domain)) return false;
        if(!StringUtils.contains(toMail, '@')) return false;
        String host = toMail.substring(toMail.indexOf('@') + 1);
        if(host.equals(domain)) return false;
        Socket socket = new Socket();
        try {
            // 查找mx记录
            Record[] mxRecords = new Lookup(host, Type.MX).run();
            if(ArrayUtils.isEmpty(mxRecords)) return false;
            // 邮件服务器地址
            String mxHost = ((MXRecord)mxRecords[0]).getTarget().toString();
            if(mxRecords.length > 1) { // 优先级排序
                List<Record> arrRecords = new ArrayList<Record>();
                Collections.addAll(arrRecords, mxRecords);
                Collections.sort(arrRecords, new Comparator<Record>() {

                    public int compare(Record o1, Record o2) {
                        return new CompareToBuilder().append(((MXRecord)o1).getPriority(), ((MXRecord)o2).getPriority()).toComparison();
                    }

                });
                mxHost = ((MXRecord)arrRecords.get(0)).getTarget().toString();
            }
            // 开始smtp
            socket.connect(new InetSocketAddress(mxHost, 25));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new BufferedInputStream(socket.getInputStream())));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // 超时时间(毫秒)
            long timeout = 6000;
            // 睡眠时间片段(50毫秒)
            int sleepSect = 50;

            // 连接(服务器是否就绪)
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 220) {
                return false;
            }

            // 握手
            bufferedWriter.write("HELO " + domain + "\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                return false;
            }
            // 身份
            bufferedWriter.write("MAIL FROM: <check@" + domain + ">\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                return false;
            }
            // 验证
            bufferedWriter.write("RCPT TO: <" + toMail + ">\r\n");
            bufferedWriter.flush();
            if(getResponseCode(timeout, sleepSect, bufferedReader) != 250) {
                return false;
            }
            // 断开
            bufferedWriter.write("QUIT\r\n");
            bufferedWriter.flush();
            return true;
        } catch (NumberFormatException e) {
        } catch (TextParseException e) {
        } catch (IOException e) {
        } catch (InterruptedException e) {
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
        return false;
    }

    private static int getResponseCode(long timeout, int sleepSect, BufferedReader bufferedReader) throws InterruptedException, NumberFormatException, IOException {
        int code = 0;
        for(long i = sleepSect; i < timeout; i += sleepSect) {
            Thread.sleep(sleepSect);
            if(bufferedReader.ready()) {
                String outline = bufferedReader.readLine();
                // FIXME 读完……
                while(bufferedReader.ready())
                    /*System.out.println(*/bufferedReader.readLine()/*)*/;
                /*System.out.println(outline);*/
                code = Integer.parseInt(outline.substring(0, 3));
                break;
            }
        }
        return code;
    }
}
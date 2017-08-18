
import java.util.ArrayList;
import java.util.List;

import com.fekpal.tool.EmailValidTool;
import com.fekpal.tool.MailHtmlTool;
import com.fekpal.tool.MailTool;
import com.fekpal.tool.ValidateCodeTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.MessagingException;


/**
 * Created by hasee on 2017/8/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)//@RunWith(SpringJUnit4ClassRunner.class) 来启动 Spring 对测试类的支持
@ContextConfiguration("/sauims-servlet.xml")//@ContextConfiguration 注释标签来指定 Spring 配置文件或者配置类的位置
public class MailTest {
    private static final Logger log = LoggerFactory.getLogger(MailTest.class);

    @Autowired
    private MailTool mailTool;
    @Autowired
    private MailHtmlTool mailHtmlTool;

    @Test
    public void sendSingleTest(){
        log.info("sendSingleTest");
        mailTool.send("Canlong2015@126.com", "我要发第四封", "这次的验证码是：<br/>"+ ValidateCodeTool.getCaptcha());
    }

    @Test
    public void sendMassTest(){
        log.info("sendMassTest");
        List<String> recipients=new ArrayList<String>();
        recipients.add("151612220@m.gduf.edu.cn");
        recipients.add("Canlong2015@126.com");
        recipients.add("2078697336@qq.com");
        mailTool.send(recipients, "This is a test mass mail", "Hello Mass!");
    }

    @Test
    public void sendHtmlMsg(){
        log.info("sendHtmlMsg");
        try {
            mailHtmlTool.sendHtml("Canlong2015@126.com", "我要发第五封（含html）", "这次的验证码是：<br/>"+ ValidateCodeTool.getCaptcha());
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    @Test
    public void testEmailVaild(){
        System.out.println(EmailValidTool.valid("2078697336@qq.com", "jootmir.org"));
        System.out.println(new EmailValidTool().valid("Canlong2015@126.com", "jootmir.org"));
        System.out.println(new EmailValidTool().valid("s19961234@126.com", "jootmir.org"));
        System.out.println(new EmailValidTool().valid("151612220@m.gduf.edu.cn", "jootmir.org"));        System.out.println(new EmailValidTool().valid("100582783@qq.com", "jootmir.org"));
        System.out.println(new EmailValidTool().valid("10;;0582783@qq.com", "jootmir.org"));
        System.out.println(new EmailValidTool().valid("as05as@127.com", "jootmir.org"));

    }
}
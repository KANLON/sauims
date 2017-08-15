import com.fekpal.cons.WebPath;
import com.fekpal.tool.BaseReturnData;
import com.fekpal.tool.ValidateCodeUtils;
import com.fekpal.web.controller.IndexPageController;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.PublicKey;
import java.util.Map;

import static java.lang.System.out;

/**
 * Created by hasee on 2017/8/14.
 */
public class TestCode {
    @Test
    public void testCode(){
        try {
            OutputStream out  = new FileOutputStream("1.jpg");
            ValidateCodeUtils.genNewCode(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public  void makerABC(){

        out.println((int) 'a');//97
        out.println((int) 'z');//122
        out.println((int) 'A');//65
        out.println((int) 'Z');//90
        for(int i =1;i<=8;i++)
        out.println((char) (90+i));

    }

    @Test
    public void testBaseReturn(){
        BaseReturnData loginReturn = new BaseReturnData();
        Map<String,Object> map = loginReturn.getMap();
        out.println("map："+map);
    }

    @Test
    public void testGetWebRoot(){
        out.println("consPath"+WebPath.consPath);;
        out.println("fekpalPath"+WebPath.fekpalPath);;
        out.println("classesPath"+WebPath.classesPath);
        out.println("rootPath"+WebPath.rootPath);;
    }

    @Test
    public  void testIndex(){
        IndexPageController indexPageController = new IndexPageController();
        Map<String,Object> map = indexPageController.getClubList();
        out.println(map);

    }

}

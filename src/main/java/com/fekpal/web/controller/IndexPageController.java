package com.fekpal.web.controller;

import com.fekpal.domain.ClubDetail;
import com.fekpal.domain.ClubListMsg;
import com.fekpal.tool.BaseReturnData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

/**
 * 首页的控制类
 * Created by hasee on 2017/8/15.
 */
@Controller
@RequestMapping("/index")
public class IndexPageController {
    Map<String,Object> map;


    @RequestMapping(value = "/club")
    public @ResponseBody Map<String,Object> getClubList(){
        //得到返回数据的模板
        BaseReturnData returnData = new BaseReturnData();

        // 创建封装社团列表信息的list集合
        List<ClubListMsg> list = new ArrayList<ClubListMsg>();

        //模拟数据
        ClubListMsg club1 = new ClubListMsg();
        club1.setClubId(1);
        club1.setClubView("1.jpg");
        club1.setDescription("这是社团1的描述");
        club1.setLikeNumber(20);
        club1.setMenbers(100);

        ClubListMsg club2 = new ClubListMsg();
        club2.setClubId(2);
        club2.setClubView("2.jpg");
        club2.setDescription("这是社团2的描述");
        club2.setLikeNumber(20);
        club2.setMenbers(100);

        //将社团加入到数据的list集合中
        list.add(club1);
        list.add(club2);

        //将list加入到数据中
        returnData.setData(list);

        return returnData.getMap();
    }


    @RequestMapping(value = "/club/{clubId}")
    public @ResponseBody Map<String,Object> getClubDetail(@PathVariable("clubId")Integer clubId){
        //得到返回数据的模板
        BaseReturnData returnData = new BaseReturnData();

        // 创建封装社团列表信息的list集合
        List<ClubDetail> list = new ArrayList<ClubDetail>();

        //模拟数据
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse("2017-10-10");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //模拟数据
        ClubDetail club1 = new ClubDetail();
        club1.setClubId(1);
        club1.setAdminName("张三");
        club1.setClubLogo("1.jpg");
        club1.setClubName("乒乓球协会");
        club1.setDescription("热爱乒乓球的聚集地");
        club1.setEmail("s19961234@126.com");
        club1.setFoundTime(date);
        club1.setMenbers(100);

        out.println("格式化前"+date);
        out.println("格式化后"+sdf.format(date));
        //将某个社团详细信息加入到数据中
        returnData.setData(club1);

        return returnData.getMap();
    }
    
    @RequestMapping("/time")
    public void testDate(@RequestParam Date date){
        out.println("格式化前"+date);
        out.println("格式化后"+new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

}


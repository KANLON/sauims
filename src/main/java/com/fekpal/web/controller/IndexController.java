package com.fekpal.web.controller;

import com.fekpal.cons.WebPath;
import com.fekpal.domain.Club;
import com.fekpal.domain.controllerDomain.ClubDetail;
import com.fekpal.domain.controllerDomain.ClubListMsg;
//import com.fekpal.service.ClubService;
//import com.fekpal.service.UserService;
import com.fekpal.tool.BaseReturnData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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
public class IndexController {

    //@Autowired
    //private ClubService clubService;

//    @Autowired
//    private UserService userService;

    /**
     * 得到社团列表信息
     * @return 社团列表信息（json数据）
     */
    @RequestMapping(value = "/club")
    public @ResponseBody Map<String,Object> getClubList(){
        //得到返回数据的模板
        BaseReturnData returnData = new BaseReturnData();

        // 创建封装社团列表信息的list集合
        List<ClubListMsg> list = new ArrayList<ClubListMsg>();

        //从service中得到对象，获取对象属性，放入对应中
        //List<Club> clubList=clubService.loadAllClub(0,50);
        Club clubs1 = new Club();
        clubs1.setClubId(123);
        clubs1.setClubName("摄影协会");
        clubs1.setClubView("123.png");
        clubs1.setDescription("定格光影，留住光阴");
        clubs1.setMembers(100);
        clubs1.setLikeNumber(20);
        Club clubs2 = new Club();
        clubs2.setClubId(124);
        clubs2.setClubName("棋牌社");
        clubs2.setClubView("124.png");
        clubs2.setDescription("人生是棋局，谁人为棋子");
        clubs2.setMembers(100);
        clubs2.setLikeNumber(20);

        List<Club> clubList= new ArrayList<Club>();
        clubList.add(clubs1);
        clubList.add(clubs2);

        for(Club club:clubList) {
            //模拟数据
            ClubListMsg club1 = new ClubListMsg();
            club1.setClubId(club.getClubId());
            club1.setClubName(club.getClubName());
            club1.setClubView(club.getClubView());
            club1.setDescription(club.getDescription());
            club1.setLikeNumber(club.getLikeNumber());
            club1.setMembers(club.getMembers());

            //将社团加入到数据的list集合中
            list.add(club1);
        }
        //将list加入到数据中
        returnData.setData(list);

        return returnData.getMap();
    }

    /**
     * 发送某个社团的详细信息
     * @param clubId 接受社团ID
     * @return 返回社团详细信息json
     */
    @RequestMapping(value = "/club/{clubId}")
    public @ResponseBody Map<String,Object> getClubDetail(@PathVariable("clubId")Integer clubId){
        //得到返回数据的模板
        BaseReturnData returnData = new BaseReturnData();

        // 创建封装社团列表信息的list集合
        List<ClubDetail> list = new ArrayList<ClubDetail>();

//        Club club=clubService.getClubByClubId(clubId);
            Club club = new Club();
            club.setClubId(123);
            club.setAdminName("张三");
            club.setClubLogo("123.jpg");
            club.setClubName("社团");
            club.setClubType("afda");
            club.setClubView("sa.jepg");
            club.setDescription("社团描述");
            club.setFoundTime(new Timestamp(232424322));
            club.setClubId(12);
            club.setLikeNumber(100);
            club.setMembers(100);
            club.setUserId(21);



        //模拟数据
        ClubDetail club1 = new ClubDetail();
        club1.setClubId(club.getClubId());
        club1.setAdminName(club.getAdminName());
        club1.setClubLogo(club.getClubLogo());
        club1.setClubName(club.getClubName());
        club1.setDescription(club.getDescription());

//        club1.setEmail(userService.getUserByUserId(club.getUserId()).getEmail());
        club1.setEmail("s19961234@126.com");

        club1.setFoundTime(new Date(club.getFoundTime().getTime()));
        club1.setMembers(club.getMembers());

        //将某个社团详细信息加入到数据中
        returnData.setData(club1);

        return returnData.getMap();
    }

    /**
     * 用于测试时间传输的格式和目录
     * @param date 前端传递过来的描述
     * @return  返回时间格式
     */
    @RequestMapping("/time")
    @ResponseBody
    public Date testDate(@RequestParam Date date){
        out.println(WebPath.consPath);
        out.println(WebPath.rootParentPath);

        out.println("格式化前"+date);
        out.println("格式化后"+new SimpleDateFormat("yyyy-MM-dd").format(date));
        return date;

    }

}


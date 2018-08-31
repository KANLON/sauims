package com.fekpal.web.controller;

import com.fekpal.api.ClubService;
import com.fekpal.api.OrgMemberService;
import com.fekpal.api.OrgService;
import com.fekpal.common.constant.ResponseCode;
import com.fekpal.common.json.JsonResult;
import com.fekpal.dao.model.Org;
import com.fekpal.web.model.OrgDetail;
import com.fekpal.web.model.OrgListMsg;
import com.fekpal.web.model.PageList;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页的控制类
 * Created by hasee on 2017/8/15.
 */
@Controller
public class IndexPageController {

    private static Logger logger = Logger.getLogger(IndexPageController.class);


    @Autowired
    private ClubService clubService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private OrgMemberService orgMemberService;

    /**
     * 得到社团列表信息
     *
     * @return 社团列表信息json
     */
    @ResponseBody
    @RequestMapping(value = "/index/club", method = RequestMethod.GET)
    public JsonResult<List<OrgListMsg>> getClubList(PageList page) {
        List<Org> clubList = orgService.loadAllOrg(page.getOffset(),page.getLimit());
        JsonResult<List<OrgListMsg>> result = new JsonResult<>();

        if (clubList != null ) {
            List<OrgListMsg> results = new ArrayList<>();
            for (Org club : clubList) {
                OrgListMsg clubs = new OrgListMsg();
                clubs.setOrgId(club.getOrgId());
                clubs.setOrgName(club.getOrgName());
                clubs.setView(club.getOrgView());
                clubs.setLogo(club.getOrgLogo());
                String[] descriptions = club.getDescription().split("。");
                clubs.setDescription(descriptions[0]);
                clubs.setLikeClick(club.getLikeClick());
                clubs.setMembers(club.getMembers());
                results.add(clubs);
            }
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "加载成功");
            result.setData(results);
        }
        logger.info("执行了获取社团列表信息");
        return result;
    }

    /**
     * 获取某社团信息
     *
     * @param id 组织标识
     * @return 社团信息封装
     */
    @ResponseBody
    @RequestMapping(value = "/index/club/{id}", method = RequestMethod.GET)
    public JsonResult<OrgDetail> getClubDetail(@PathVariable int id) {
        Org org = orgService.selectByPrimaryKey(id);
        JsonResult<OrgDetail> result = new JsonResult<>();

        if (org != null) {
            OrgDetail record = new OrgDetail();
            record.setOrgId(org.getOrgId());
            record.setAdminName(org.getAdminName());
            record.setLogo(org.getOrgLogo());
            record.setView(org.getOrgView());
            record.setOrgName(org.getOrgName());
            record.setEmail(org.getContactEmail());
            record.setFoundTime(org.getFoundTime());
            record.setMembers(org.getMembers());
            record.setOrgType(org.getOrgType());
            record.setPhone(org.getContactNumber());
            record.setLikeClick(org.getLikeClick());
            String[] description = org.getDescription().split("。");
            record.setHeadIntroduce(description[0]);
            record.setDescription(description[description.length-1]);
            record.setManNum(orgService.countOrgManNumByOrgId(id));
            record.setWomanNum(orgService.countOrgWomanNumByOrgId(id));
            int firstGradeNum = orgService.countOrgGradeNumByOrgId(1,id);
            int secondGradeNum = orgService.countOrgGradeNumByOrgId(2,id);
            int threeGradeNum = orgService.countOrgGradeNumByOrgId(3,id);
            int fourGradeNum = orgService.countOrgGradeNumByOrgId(4,id);
            //已经毕业的人数由社团总人数减去各个年级的人数
            int graduatedNum = org.getMembers()-firstGradeNum-secondGradeNum-threeGradeNum-fourGradeNum;
            record.setFirstGradeNum(firstGradeNum);
            record.setSecondGradeNum(secondGradeNum);
            record.setThreeGradeNum(threeGradeNum);
            record.setFourGradeNum(fourGradeNum);
            record.setGraduatedNum(graduatedNum >=0? graduatedNum : 0);

            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "加载成功");
            result.setData(record);
        }else {
            result.setStateCode(ResponseCode.REQUEST_ERROR,"获取错误！");
        }
        logger.info("执行了获取某社团信息");
        return result;
    }
}


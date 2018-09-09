package com.fekpal.web.controller.member;

import com.fekpal.api.LikeOrgService;
import com.fekpal.api.MemberOrgService;
import com.fekpal.api.OrgService;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.constant.ResponseCode;
import com.fekpal.common.json.JsonResult;
import com.fekpal.dao.model.LikeOrg;
import com.fekpal.dao.model.Org;
import com.fekpal.dao.model.PersonOrgView;
import com.fekpal.service.impl.MemberOrgServiceImpl;
import com.fekpal.web.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 普通成员社团信息的控制类
 * Created by hasee on 2017/8/23.
 * @author zhangcanlong
 * @date 2017/8/23
 */
@Controller
public class MemberOrgMsgController {

    private static Logger logger =   LogManager.getLogger(MemberOrgMsgController.class);

    @Autowired
    private OrgService orgService;

    @Autowired
    private MemberOrgService memberOrgService;

    @Autowired
    private LikeOrgService likeOrgService;

    private LikeOrg likeOrg;

    /**
     * 返回组织信息列表的方法
     *
     * @param page 分页封装
     * @return 组织信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/member/org", method = RequestMethod.GET)
    public JsonResult<List<OrgListMsg>> getAllOrgMsg(SearchPage page) {
        return searchMsg(page);
    }


    /**
     * 得到某个组织的详细信息
     *
     * @param id 组织id
     * @return 社团详细信息
     */
    @ResponseBody
    @RequestMapping(value = "/member/org/{id}", method = RequestMethod.GET)
    public JsonResult<MemberClubDetail> getOneOrgMsg(@PathVariable int id) {
        PersonOrgView org = orgService.selectByIdForPerson(id);

        JsonResult<MemberClubDetail> result = new JsonResult<>();
        if (org != null) {
            MemberClubDetail detail = new MemberClubDetail();
            detail.setOrgId(org.getOrgId());
            detail.setOrgName(org.getOrgName());
            detail.setAdminName(org.getAdminName());
            detail.setPhone(org.getContactNumber());
            detail.setEmail(org.getContactEmail());
            detail.setFoundTime(org.getFoundTime());
            detail.setLogo(org.getOrgLogo());
            detail.setOrgType(org.getOrgType());
            detail.setMembers(org.getMembers());
            detail.setLikeClick(org.getLikeClick());
            detail.setView(org.getOrgView());
            detail.setJoinState(org.getJoinState());
            LikeOrg likeState = likeOrgService.selectByOrgId(org.getOrgId());
            detail.setIsLike(likeState.getAvailable());
            String[] description = org.getDescription().split("。");
            detail.setHeadIntroduce(description[0]);
            detail.setDescription(description[description.length-1]);
            detail.setManNum(orgService.countOrgManNumByOrgId(id));
            detail.setWomanNum(orgService.countOrgWomanNumByOrgId(id));
            int firstGradeNum = orgService.countOrgGradeNumByOrgId(1,id);
            int secondGradeNum = orgService.countOrgGradeNumByOrgId(2,id);
            int threeGradeNum = orgService.countOrgGradeNumByOrgId(3,id);
            int fourGradeNum = orgService.countOrgGradeNumByOrgId(4,id);
            //已经毕业的人数由社团总人数减去各个年级的人数
            int graduatedNum = org.getMembers()-firstGradeNum-secondGradeNum-threeGradeNum-fourGradeNum;
            detail.setFirstGradeNum(firstGradeNum);
            detail.setSecondGradeNum(secondGradeNum);
            detail.setThreeGradeNum(threeGradeNum);
            detail.setFourGradeNum(fourGradeNum);
            detail.setGraduatedNum(graduatedNum >=0? graduatedNum : 0);

            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "加载成功");
            result.setData(detail);
        }else {
            result.setStateCode(ResponseCode.REQUEST_ERROR,"获取错误！");
        }
        return result;
    }

    /**
     * 用户加入组织
     *
     * @param id 组织id
     * @return 返回时候加入成功，等待审核
     */
    @ResponseBody
    @RequestMapping(value = "/member/org/{id}/join", method = RequestMethod.POST)
    public JsonResult<String> joinClub(@PathVariable int id) {
        int state = memberOrgService.joinOrganizationByOrgId(id);
        JsonResult<String> result = new JsonResult<>();
        if (state == Operation.SUCCESSFULLY) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "申请成功，请留意审核通知！");
        } else if (state == Operation.FAILED) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "已经加入了或已经申请该社团了！");
        } else if (state == Operation.INPUT_INCORRECT) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "请完善资料再进行申请！");
        }
        return result;
    }

    /**
     * 用户喜爱组织
     *
     * @param id 要喜爱的组织id
     * @return 返回时候是否喜爱成功和zai数据里面加上点赞人数
     */
    @ResponseBody
    @RequestMapping(value = "/member/org/{id}/star", method = RequestMethod.POST)
    public JsonResult<Integer> likeClub(@PathVariable int id) {
        int state = likeOrgService.likeByOrgIdAndState(id);


        JsonResult<Integer> result = new JsonResult<>();
        if (state == Operation.SUCCESSFULLY) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "点赞成功");
            PersonOrgView org = orgService.selectByIdForPerson(id);
            Integer likeClick = org.getLikeClick();
            result.setData(likeClick);
        } else if (state == Operation.FAILED) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "点赞失败");
        } else if (state == Operation.CANCEL) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "取消点赞");
            PersonOrgView org = orgService.selectByIdForPerson(id);
            Integer likeClick = org.getLikeClick();
            result.setData(likeClick);
        }
        return result;
    }

    /**
     * 根据查找内容查找组织
     *
     * @param page 查找封装
     * @return 查找的组织的列表对象信息
     */
    @ResponseBody
    @RequestMapping(value = "/member/org/search", method = RequestMethod.GET)
    public JsonResult<List<OrgListMsg>> searchMsg(SearchPage page) {
        JsonResult<List<OrgListMsg>> result = new JsonResult<>();
        List<Org> orgList = orgService.selectByOrgName(page.getFindContent(), page.getOffset(), page.getLimit());
        Integer orgNum = orgService.countByOrgName(page.getFindContent());
        if (orgList != null ) {
            List<OrgListMsg> lists = new ArrayList<>();
            for (Org org : orgList) {
                OrgListMsg msg = new OrgListMsg();
                msg.setOrgId(org.getOrgId());
                msg.setOrgName(org.getOrgName());
                msg.setMembers(org.getMembers());
                msg.setLogo(org.getOrgLogo());
                msg.setLikeClick(org.getLikeClick());
                likeOrg = likeOrgService.selectByOrgId(org.getOrgId());
                msg.setIsClick(likeOrg.getAvailable());

                lists.add(msg);
            }
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, orgNum.toString());
            result.setData(lists);
        }
        return result;
    }
}

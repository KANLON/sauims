package com.fekpal.web.controller.club;

import com.fekpal.api.ClubService;
import com.fekpal.api.OrgService;
import com.fekpal.common.constant.ResponseCode;
import com.fekpal.common.json.JsonResult;
import com.fekpal.dao.model.Org;
import com.fekpal.dao.model.PersonOrgView;
import com.fekpal.web.model.OrgDetail;
import com.fekpal.web.model.OrgListMsg;
import com.fekpal.web.model.SearchPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


/**
 * 社团管理员的的社团信息的控制类
 *
 * @author zhangcanlong
 * @date 2018/4/8
 */
@Controller
public class ClubOrgMsgController {

    @Autowired
    private ClubService clubService;

    @Autowired
    private OrgService orgService;

    /**
     * 返回社团信息列表的方法
     *
     * @return 社团信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/club/other", method = RequestMethod.GET)
    public JsonResult<List<OrgListMsg>> getAllClubMsg(SearchPage page) {
        return searchMsg(page);
    }


    /**
     * 根据社团id得到某个社团的详细信息
     *
     * @param id 社团ID
     * @return 社团详细信息
     */
    @ResponseBody
    @RequestMapping(value = "/club/other/{id}", method = RequestMethod.GET)
    public JsonResult<OrgDetail> getOneClubMsg(@PathVariable int id) {
        PersonOrgView org = clubService.selectByIdForPerson(id);
        JsonResult<OrgDetail> result = new JsonResult<>();
        if (org == null) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "无结果");
        } else {
            OrgDetail detail = new OrgDetail();
            detail.setOrgId(org.getOrgId());
            detail.setOrgName(org.getOrgName());
            detail.setAdminName(org.getAdminName());
            detail.setDescription(org.getDescription());
            detail.setPhone(org.getContactNumber());
            detail.setEmail(org.getContactEmail());
            detail.setFoundTime(org.getFoundTime());
            detail.setLogo(org.getOrgLogo());
            detail.setOrgType(org.getOrgType());
            detail.setMembers(org.getMembers());
            detail.setLikeClick(org.getLikeClick());
            detail.setView(org.getOrgView());
            detail.setJoinState(org.getJoinState());
            String[] description = org.getDescription().split("。");
            detail.setHeadIntroduce(description[0]);
            detail.setDescription(description[description.length - 1]);
            detail.setManNum(orgService.countOrgManNumByOrgId(id));
            detail.setWomanNum(orgService.countOrgWomanNumByOrgId(id));
            int firstGradeNum = orgService.countOrgGradeNumByOrgId(1, id);
            int secondGradeNum = orgService.countOrgGradeNumByOrgId(2, id);
            int threeGradeNum = orgService.countOrgGradeNumByOrgId(3, id);
            int fourGradeNum = orgService.countOrgGradeNumByOrgId(4, id);
            //已经毕业的人数由社团总人数减去各个年级的人数
            int graduatedNum = org.getMembers() - firstGradeNum - secondGradeNum - threeGradeNum - fourGradeNum;
            detail.setFirstGradeNum(firstGradeNum);
            detail.setSecondGradeNum(secondGradeNum);
            detail.setThreeGradeNum(threeGradeNum);
            detail.setFourGradeNum(fourGradeNum);
            detail.setGraduatedNum(graduatedNum >= 0 ? graduatedNum : 0);

            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "加载成功");
            result.setData(detail);
        }
        return result;
    }

    /**
     * 根据查找内容查找某个社团
     *
     * @param page 查找内容及查找条数信息
     * @return 查找的社团的列表对象信息
     */
    @ResponseBody
    @RequestMapping(value = "/club/other/search", method = RequestMethod.GET)
    public JsonResult<List<OrgListMsg>> searchMsg(SearchPage page) {
        List<Org> orgList = clubService.queryByClubName(page.getFindContent(), page.getOffset(), page.getLimit());
        Integer orgNum = clubService.countByClubName(page.getFindContent());
        JsonResult<List<OrgListMsg>> result = new JsonResult<>();
        List<OrgListMsg> lists = new ArrayList<>();
        if (orgList != null) {
            for (Org org : orgList) {
                OrgListMsg msg = new OrgListMsg();
                msg.setOrgId(org.getOrgId());
                msg.setOrgName(org.getOrgName());
                msg.setMembers(org.getMembers());
                msg.setLogo(org.getOrgLogo());
                msg.setLikeClick(org.getLikeClick());
                lists.add(msg);
            }
        }
        result.setStateCode(ResponseCode.RESPONSE_SUCCESS, orgNum.toString());
        result.setData(lists);
        return result;
    }


}

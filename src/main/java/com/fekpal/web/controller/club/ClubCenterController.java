package com.fekpal.web.controller.club;

import com.fekpal.api.ClubService;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.constant.ResponseCode;
import com.fekpal.common.json.JsonResult;
import com.fekpal.dao.model.Org;
import com.fekpal.service.model.domain.ClubMsg;
import com.fekpal.web.model.OrgDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * 校社联中心信息的控制类
 * Created by hasee on 2017/8/19.
 */
@Controller
public class ClubCenterController {


    @Autowired
    private ClubService clubService;


    /**
     * 得到社团中心的信息的方法
     *
     * @return 社团的一些基本信息
     */
    @ResponseBody
    @RequestMapping(value = "/club/center/info",method = RequestMethod.GET)
    public JsonResult<OrgDetail> getClubsCenterMsg() {
        JsonResult<OrgDetail> result = new JsonResult<>();
        Org org = clubService.selectByPrimaryId();
        if (org == null) {result.setStateCode(ResponseCode.RESPONSE_ERROR, "无结果");return result; }
        OrgDetail detail = new OrgDetail();
        detail.setOrgId(org.getOrgId());
        detail.setOrgName(org.getOrgName());
        detail.setLogo(org.getOrgLogo());
        detail.setDescription(org.getDescription());
        detail.setAdminName(org.getAdminName());
        detail.setEmail(org.getContactEmail());
        detail.setPhone(org.getContactNumber());
        detail.setFoundTime(org.getFoundTime());
        detail.setMembers(org.getMembers());
        detail.setView(org.getOrgView());
        detail.setOrgType(org.getOrgType());
        detail.setLikeClick(org.getLikeClick());
        detail.setManNum(clubService.countClubManNum());
        detail.setWomanNum(clubService.countClubWomanNum());
        int firstGradeNum = clubService.countClubGradeNum(1);
        int secondGradeNum = clubService.countClubGradeNum(2);
        int threeGradeNum = clubService.countClubGradeNum(3);
        int fourGradeNum = clubService.countClubGradeNum(4);
        //已经毕业的人数由社团总人数减去各个年级的人数
        int graduatedNum = org.getMembers()-firstGradeNum-secondGradeNum-threeGradeNum-fourGradeNum;
        detail.setFirstGradeNum(firstGradeNum);
        detail.setSecondGradeNum(secondGradeNum);
        detail.setThreeGradeNum(threeGradeNum);
        detail.setFourGradeNum(fourGradeNum);
        detail.setGraduatedNum(graduatedNum >=0? graduatedNum : 0);

         result.setCode(ResponseCode.RESPONSE_SUCCESS);
         result.setData(detail);
         return result;
    }

    /**
     * 上传社团头像的方法
     *
     * @param msg 社团信息封装
     * @return 图片文件名
     */
    @ResponseBody
    @RequestMapping(value = "/club/center/info/logo", method = RequestMethod.POST)
    public JsonResult<String> uploadLogo(@ModelAttribute ClubMsg msg) {
        JsonResult<String> result = new JsonResult<>();
        String logoName = clubService.updateLogo(msg);
        if (logoName == null) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "修改头像失败");
        } else {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "修改头像成功");
            result.setData(logoName);
        }
        return result;
    }

    /**
     * 上传社团展示图片的方法
     *
     * @return 图片文件名
     */
    @ResponseBody
    @RequestMapping(value = "/club/center/info/view", method = RequestMethod.POST)
    public JsonResult<String> uploadView(@ModelAttribute ClubMsg msg) {
        JsonResult<String> result = new JsonResult<>();
        String viewName = clubService.updateView(msg);
        if (viewName == null) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "修改展示失败");
        } else {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "修改展示成功");
            result.setData(viewName);
        }
        return result;
    }

    /**
     * 社团用来提交修改社团中心的信息
     *
     * @return 是否提交成功
     */
    @ResponseBody
    @RequestMapping(value = "/club/center/info", method = RequestMethod.PUT)
    public JsonResult<OrgDetail> subNewCenterMsg(@RequestBody ClubMsg msg) {
        int state = clubService.updateClubInfo(msg);
        JsonResult<OrgDetail> result = new JsonResult<>();
        if (state == Operation.SUCCESSFULLY) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "修改成功");
            //获取一次自己的信息返回给前端修改
            JsonResult<OrgDetail> getClubsCenterMsgResult = this.getClubsCenterMsg();
            result.setData(getClubsCenterMsgResult.getData());
        } else if (state == Operation.FAILED) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "修改失败");
        } else if (state == Operation.INPUT_INCORRECT) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "社团名称已被使用");
        }
        return result;
    }
}

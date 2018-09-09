package com.fekpal.web.controller.sau;


import com.fekpal.api.ClubService;
import com.fekpal.api.SauService;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.constant.ResponseCode;
import com.fekpal.common.json.JsonResult;
import com.fekpal.dao.model.Org;
import com.fekpal.service.model.domain.SauMsg;
import com.fekpal.web.controller.member.MemberCenterController;
import com.fekpal.web.model.OrgDetail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 校社联中心信息的控制类
 * Created by hasee on 2017/8/19.
 * @author zhangcanlong
 */
@Controller
public class SauCenterController {

    @Autowired
    private SauService sauService;

    @Autowired
    private ClubService clubService;

    private static Logger logger =     LogManager.getLogger(SauCenterController.class);


    /**
     * 得到校社联中心的信息
     *
     * @return 校社联的一些基本信息
     */
    @ResponseBody
    @RequestMapping(value = "/sau/center/info", method = RequestMethod.GET)
    public JsonResult<OrgDetail> getSauCenterMsg() {
        Org org = sauService.selectByPrimaryId();

        JsonResult<OrgDetail> result = new JsonResult<>();
        if (org != null) {
            OrgDetail detail = new OrgDetail();
            detail.setOrgName(org.getOrgName());
            detail.setLogo(org.getOrgLogo());
            detail.setDescription(org.getDescription());
            detail.setAdminName(org.getAdminName());
            detail.setEmail(org.getContactEmail());
            detail.setPhone(org.getContactNumber());
            detail.setFoundTime(org.getFoundTime());
            detail.setMembers(org.getMembers());
            detail.setManNum(clubService.countClubManNum());
            detail.setWomanNum(clubService.countClubWomanNum());
            int firstGradeNum = clubService.countClubGradeNum(1);
            int secondGradeNum = clubService.countClubGradeNum(2);
            int threeGradeNum = clubService.countClubGradeNum(3);
            int fourGradeNum = clubService.countClubGradeNum(4);
            //校社联内已经毕业了的学生的人数
            int graduatedNum = org.getMembers()-firstGradeNum-secondGradeNum-threeGradeNum-fourGradeNum;
            detail.setFirstGradeNum(firstGradeNum);
            detail.setSecondGradeNum(secondGradeNum);
            detail.setThreeGradeNum(threeGradeNum);
            detail.setFourGradeNum(fourGradeNum);
            detail.setGraduatedNum(graduatedNum>0?graduatedNum:0);
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "加载成功");
            result.setData(detail);
        }else {
            result.setStateCode(ResponseCode.REQUEST_ERROR,"获取错误！");
        }
        return result;
    }

    /**
     * 修改校社联信息
     *
     * @param msg 校社联修改信息封装
     * @return 是否提交成功
     */
    @ResponseBody
    @RequestMapping(value = "/sau/center/info", method = RequestMethod.PUT)
    public JsonResult<String> subNewCenterMsg(@RequestBody SauMsg msg) {
        int state = sauService.updateSauInfo(msg);

        JsonResult<String> result = new JsonResult<>();
        if (state == Operation.SUCCESSFULLY) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "修改成功");
        } else if (state == Operation.FAILED) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "修改失败");
        } else if (state == Operation.INPUT_INCORRECT) {
            result.setStateCode(ResponseCode.RESPONSE_ERROR, "校社联名称已被使用");
        }
        return result;
    }

    /**
     * 上传校社联头像的
     *
     * @param msg 校社联修改信息封装
     * @return 图片文件名
     */
    @ResponseBody
    @RequestMapping(value = "/sau/center/info/head", method = RequestMethod.POST)
    public JsonResult<String> uploadLogo(@ModelAttribute SauMsg msg) {
        String logoName = sauService.updateLogo(msg);
        JsonResult<String> result = new JsonResult<>();
        if (logoName == null) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "修改头像失败");
        } else {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "修改头像成功");
            result.setData(logoName);
        }
        return result;
    }

    /**
     * 上传校社联展示图片的方法
     *
     * @return 图片文件名
     */
    @ResponseBody
    @RequestMapping(value = "/sau/center/info/view", method = RequestMethod.POST)
    public JsonResult<String> uploadView(@ModelAttribute SauMsg msg) {
        JsonResult<String> result = new JsonResult<>();
        String viewName = sauService.updateView(msg);
        if (viewName == null) {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "修改展示失败");
        } else {
            result.setStateCode(ResponseCode.RESPONSE_SUCCESS, "修改展示成功");
            result.setData(viewName);
        }
        return result;
    }
}

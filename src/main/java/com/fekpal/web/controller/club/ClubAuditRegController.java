package com.fekpal.web.controller.club;

import com.fekpal.api.OrgMemberService;
import com.fekpal.api.ClubService;
import com.fekpal.api.PersonService;
import com.fekpal.api.UserService;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.constant.ResponseCode;
import com.fekpal.common.json.JsonResult;
import com.fekpal.dao.model.OrgMember;
import com.fekpal.dao.model.Person;
import com.fekpal.dao.model.User;
import com.fekpal.web.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 注册审核的控制类
 *
 * @author zhangcanlong
 * @date 2018/5/4
 */
@Controller
public class ClubAuditRegController {

    @Autowired
    private OrgMemberService orgMemberService;

    @Autowired
    PersonService personService;

    @Autowired
    UserService userService;

    /**
     * 查看全部审核的信息的方法
     *
     * @param page 获取条数和跳过条数
     * @return 审核信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/club/audit/join", method = RequestMethod.GET)
    public JsonResult<List<ClubRegisterAuditListMsg>> getAllAuditMsg(SearchPage page) {
        return searchAuditMsg(page);
    }

    /**
     * 根据某个审核消息id查看审核信息的具体内容
     *
     * @param auditMsgId 审核信息的id
     * @return 审核信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/club/audit/join/{auditMsgId}", method = RequestMethod.GET)
    public JsonResult<PersonJoinAuditDetail> getAuditMsgDetail(@PathVariable("auditMsgId") int auditMsgId) {
        JsonResult<PersonJoinAuditDetail> result = new JsonResult<>();
        PersonJoinAuditDetail personDetail = new PersonJoinAuditDetail();
        OrgMember orgMember = orgMemberService.selectById(auditMsgId);
        if (orgMember == null) {
            result.setStateCode(ResponseCode.REQUEST_ERROR, "操作非法");
            return result;
        }
        personDetail.setAuditId(auditMsgId);
        Person person = personService.selectByPrimaryKey(orgMember.getPersonId());
        User user = userService.selectByPrimaryKey(person.getUserId());
        personDetail.setAddress(person.getAddress());
        personDetail.setBirthday(person.getBirthday());
        personDetail.setDepartmentName(person.getDepartment());
        personDetail.setEmail(user.getEmail());
        personDetail.setGender(person.getGender() == 0 ? "男" : "女");
        personDetail.setMajorName(person.getMajor());
        personDetail.setPersonLogo(person.getLogo());
        personDetail.setPhone(user.getPhone());
        personDetail.setRealName(person.getRealName());
        personDetail.setRegisterTime(orgMember.getJoinTime());
        personDetail.setStudentId(person.getStudentId());
        personDetail.setUserName(user.getUserName());
        personDetail.setAuditSate(person.getPersonState());

        result.setData(personDetail);
        result.setCode(ResponseCode.RESPONSE_SUCCESS);
        return result;
    }

    /**
     * 发送审核结果，得到审核结果
     *
     * @param auditMsgId  审核消息id
     * @param auditResult 结果的map集合
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/club/audit/join/{auditMsgId}", method = RequestMethod.PUT)
    public JsonResult<String> sendAuditMsgResult(@PathVariable("auditMsgId") int auditMsgId,
            @RequestBody @Validated AuditResult auditResult) {
        JsonResult<String> result = new JsonResult<>();
        if (auditResult == null) {
            result.setStateCode(ResponseCode.REQUEST_ERROR, "发送审核结果错误");
        }
        int state = orgMemberService.passOrRejectAuditByIdAndModel(auditMsgId, auditResult);
        if (state == Operation.FAILED) {
            result.setStateCode(ResponseCode.REQUEST_ERROR, "操作非法");
            return result;
        }
        result.setCode(ResponseCode.RESPONSE_SUCCESS);
        return result;
    }

    /**
     * 根据查找内容查找审核消息
     *
     * @param page 查找内容
     * @return 返回审核消息列表
     */
    @ResponseBody
    @RequestMapping(value = "/club/audit/join/search", method = RequestMethod.GET)
    public JsonResult<List<ClubRegisterAuditListMsg>> searchAuditMsg(SearchPage page) {
        JsonResult<List<ClubRegisterAuditListMsg>> result = new JsonResult<>();
        List<ClubRegisterAuditListMsg> auditList = new ArrayList<>();
        List<OrgMember> orgMemberList = orgMemberService.queryByRealName(page.getFindContent(), page.getOffset(), page.getLimit());
        Integer memberNum = orgMemberService.countAuditByRealName(page.getFindContent());
        if (orgMemberList != null) {
            for (OrgMember orgMember : orgMemberList) {
                ClubRegisterAuditListMsg audit = new ClubRegisterAuditListMsg();
                audit.setAuditMsgId(orgMember.getId());
                audit.setAuditState(orgMember.getAvailable());
                int personId = orgMember.getPersonId();
                Person person = personService.selectByPrimaryKey(personId);
                audit.setAuditTitle(person.getRealName());
                audit.setRegisterTime(orgMember.getJoinTime());
                auditList.add(audit);
            }
        }
        result.setStateCode(ResponseCode.RESPONSE_SUCCESS, memberNum.toString());
        result.setData(auditList);
        return result;
    }

}

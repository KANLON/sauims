package com.fekpal.web.controller.sau;

import com.fekpal.api.AnniversaryAuditService;
import com.fekpal.api.ClubService;
import com.fekpal.api.SauService;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.constant.ResponseCode;
import com.fekpal.common.json.JsonResult;
import com.fekpal.common.utils.TimeUtil;
import com.fekpal.dao.model.AnniversaryAudit;
import com.fekpal.dao.model.Org;
import com.fekpal.web.model.AnnAuditListModel;
import com.fekpal.web.model.SauAnnAuditDetail;
import com.fekpal.web.model.SearchPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 年度审核的控制类
 * @author zhangcanlong
 * @date 2018/4/6
 */

@Controller
public class SauAnnAuditController {

    @Autowired
    private SauService sauService;

    @Autowired
    private AnniversaryAuditService auditService;



    @Autowired
    private ClubService clubService;

    /**
     * 查看全部审核的信息的方法
     *
     * @return 审核信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/sau/audit/ann", method = RequestMethod.GET)
    public JsonResult<List<AnnAuditListModel>> getAllAuditMsg(SearchPage page) {
        return searchAuditMsg(page);
    }

    /**
     * 根据某个审核消息id查看年度审核信息的具体内容
     *
     * @param auditMsgId 审核信息的id
     * @return 审核信息列表
     */
    @ResponseBody
    @RequestMapping(value = "/sau/audit/ann/{auditMsgId}", method = RequestMethod.GET)
    public JsonResult<SauAnnAuditDetail> getAuditMsgDetail(@PathVariable("auditMsgId") int auditMsgId) {
        JsonResult<SauAnnAuditDetail> result = new JsonResult<>();
        AnniversaryAudit audit= auditService.selectByAuditId(auditMsgId);

        if(audit==null){ result.setStateCode(ResponseCode.REQUEST_ERROR,"所获取的id不存在");return result;}
        Org org = clubService.selectByPrimaryKey(audit.getOrgId());
        SauAnnAuditDetail detail = new SauAnnAuditDetail();

        detail.setAuditMsgId(audit.getId());
        detail.setAdminName(org.getAdminName());
        detail.setClubName(org.getOrgName());
        detail.setDescription(audit.getSubmitDescription());
        detail.setFileName(audit.getFileName());
        detail.setSubmitTime(audit.getSubmitTime());
        detail.setAuditResult(audit.getAuditResult());
        detail.setAuditState(audit.getAuditState());
        detail.setAuditTime(audit.getAuditTime());

        result.setCode(ResponseCode.RESPONSE_SUCCESS);
        result.setData(detail);
        return result;
    }

    /**
     * 发送年度审核结果，得到审核结果
     *
     * @param auditMsgId 审核消息id
     * @param detail  前端返回结果的集合
     * @return 是否成功
     */
    @ResponseBody
    @RequestMapping(value = "/sau/audit/ann/{auditMsgId}", method = RequestMethod.PUT)
    public JsonResult sendAuditMsgResult(@PathVariable("auditMsgId") int auditMsgId, @RequestBody SauAnnAuditDetail detail) {
        JsonResult result = new JsonResult();
        if(detail==null){result.setStateCode(ResponseCode.REQUEST_ERROR,"没有收到审核结果");return result;}
        AnniversaryAudit audit = new AnniversaryAudit();
        audit.setId(auditMsgId);
        audit.setAuditResult(detail.getAuditResult());
        audit.setAuditState(detail.getAuditState());
        audit.setAuditTime(new Timestamp(TimeUtil.currentTime()));
        int state = auditService.updateAuditStateByAnnModel(audit);
        if(state == Operation.SUCCESSFULLY){
            result.setCode(ResponseCode.RESPONSE_SUCCESS);
        }else{
            result.setStateCode(ResponseCode.REQUEST_ERROR,"更新失败");
        }
        return result;
    }

    /**
     * 根据查找内容查找年度审核消息
     *
     * @param page 查找内容
     * @return 返回审核消息列表
     */
    @ResponseBody
    @RequestMapping(value = "/sau/audit/ann/reg/search", method = RequestMethod.GET)
    public JsonResult<List<AnnAuditListModel>> searchAuditMsg(SearchPage page) {
        JsonResult<List<AnnAuditListModel>> result = new JsonResult<>();
        List<AnniversaryAudit> auditList = auditService.queryByAuditTitle(page.getFindContent(),page.getOffset(),page.getLimit());
        Integer auditNum = auditService.countByAuditTitle(page.getFindContent());
        List<AnnAuditListModel> sauAuditList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        if(auditList!=null) {
            for (AnniversaryAudit audit : auditList) {
                AnnAuditListModel sauAudit = new AnnAuditListModel();
                sauAudit.setAuditMsgId(audit.getId());
                sauAudit.setAuditState(audit.getAuditState());
                Org org = clubService.selectByPrimaryKey(audit.getOrgId());
                //获取提交年份-1的年份作为年度审核的年份
                c.setTime(audit.getSubmitTime());
                int year = c.get(Calendar.YEAR) - 1;
                sauAudit.setRegisterName(year + org.getOrgName());
                sauAudit.setRegisterTime(audit.getSubmitTime());
                sauAudit.setRegisterTitle(audit.getAuditTitle());
                sauAudit.setRegisterName(org.getAdminName());
                sauAuditList.add(sauAudit);
            }
        }
        result.setStateCode(ResponseCode.RESPONSE_SUCCESS,auditNum.toString());
        result.setData(sauAuditList);
        return result;
    }
    /**
     * 在线预览审核文件，
     *
     * @param auditMsgId 审核消息id
     * @param response   响应
     *                   html文件的输出流，直接输出到浏览器
     */
    @RequestMapping(value = "/sau/audit/ann/{auditMsgId}/file/online", method = RequestMethod.GET)
    public void openOnlineFile(@PathVariable("auditMsgId") int auditMsgId, HttpServletResponse response) {
        int state = auditService.viewAuditFileById(auditMsgId,response);
        if(state == Operation.FAILED){
            throw new RuntimeException("预览失败，请重新预览");
        }
    }

    /**
     * 下载某个审核消息的审核文件，向浏览器输出下载信息
     *
     * @param auditMsgId 审核消息id
     * @param response   响应
     */
    @RequestMapping(value = "/sau/audit/ann/{auditMsgId}/file", method = RequestMethod.GET)
    public void downFile(@PathVariable int auditMsgId, HttpServletResponse response) {
        int state = auditService.getAuditFileById(auditMsgId,response);
        if(state == Operation.FAILED){
            throw new RuntimeException("下载失败，请重新下载");
        }
    }


}

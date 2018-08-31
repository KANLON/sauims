package com.fekpal.service.impl;

import com.fekpal.api.ClubAuditService;
import com.fekpal.common.base.BaseServiceImpl;
import com.fekpal.common.base.CRUDException;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.common.constant.AuditState;
import com.fekpal.common.constant.FIleDefaultPath;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.utils.FileUtil;
import com.fekpal.common.utils.WordFileUtil;
import com.fekpal.dao.mapper.ClubAuditMapper;
import com.fekpal.dao.mapper.OrgMapper;
import com.fekpal.dao.mapper.UserMapper;
import com.fekpal.dao.model.ClubAudit;
import com.fekpal.dao.model.Org;
import com.fekpal.dao.model.User;
import com.fekpal.web.model.ClubAuditResultMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author APone
 * @date 2017/9/17
 */
@Service
public class ClubAuditServiceImpl extends BaseServiceImpl<ClubAuditMapper, ClubAudit> implements ClubAuditService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    OrgMapper orgMapper;

    @Override
    public ClubAudit selectByClubId(int clubId) {
        ExampleWrapper<ClubAudit> example = new ExampleWrapper<>();
        example.eq("club_id", clubId).ne("audit_state",AuditState.DELETE).orderBy("register_time",false);
        return mapper.selectFirstByExample(example);
    }

    @Override
    public List<ClubAudit> queryByClubName(String clubName, int offset, int limit) {
        List<ClubAudit> clubAuditList = new ArrayList<>();
        ExampleWrapper<Org> orgExample = new ExampleWrapper<>();
        orgExample.like("org_name",clubName).and().eq("org_state",AuditState.AUDITING);
        List<Org> orgList = orgMapper.selectByExample(orgExample,0,100000);
        if(orgList ==null ){return  null;}
        for (Org org : orgList){
            ExampleWrapper<ClubAudit> example = new ExampleWrapper<>();
            example.like("org_id",Integer.toString(org.getOrgId())).ne("audit_state",AuditState.DELETE).orderBy("register_time",false);
            List<ClubAudit> clubAudits = mapper.selectByExample(example, 0, 100000);
            if(clubAudits == null) {return null;}
            clubAuditList.addAll(clubAudits);
        }

        //从list集合中根据offset和limit的值来获取元素
        List<ClubAudit> clubAuditResultList = new ArrayList<>();
        if(offset>clubAuditList.size()){return null;}
        int maxIndex = Math.min(limit+offset-1,clubAuditList.size());
        for(int i=offset-1;i<maxIndex;i++){
            clubAuditResultList.add(clubAuditList.get(i));
        }
        return clubAuditResultList;
    }

    @Override
    public List<ClubAudit> loadAllClubAudit(int offset, int limit) {
        ExampleWrapper<ClubAudit> example = new ExampleWrapper<>();
        example.ne("audit_state",AuditState.DELETE).orderBy("register_time",false);
        return mapper.selectByExample(example, offset, limit);
    }

    /**
     * 查看所有未审核的社团审核消息
     *
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 社团注册审核记录集
     */
    @Override
    public List<ClubAudit> loadAllUnAudit(int offset, int limit) {
        ExampleWrapper<ClubAudit> example = new ExampleWrapper<>();
        example.eq("audit_state", AuditState.AUDITING).ne("audit_state",AuditState.DELETE).orderBy("register_time",false);
        return mapper.selectByExample(example,offset,limit);
    }

    /**
     * 计算还没审核的消息数
     *
     * @return 未审核数
     */
    @Override
    public int countUnAudit() {
        ExampleWrapper<ClubAudit> example = new ExampleWrapper<>();
        example.eq("audit_state", AuditState.AUDITING);
        return mapper.countByExample(example);
    }

    /**
     * 这个功能暂时不需要
     * 发送社团审核给校社联
     *
     * @param clubAudit 社团审核内容
     * @return Operation.SUCCESSFULLY; 发送成功 Operation.FAILED 发送失败
     */
    @Override
    public int sendClubAudit(ClubAudit clubAudit) {
        return 0;
    }

    /**
     * 根据审核id删除某个社团的审核
     *
     * @param id 审核id
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int deleteClubAuditById(int id) {
        ClubAudit clubAudit = mapper.selectByPrimaryKey(id);
        clubAudit.setAuditState(AuditState.DELETE);
        int row = mapper.updateByPrimaryKey(clubAudit);
        if(row > 1){throw new CRUDException("删除审核消息异常,删除"+row +"行");}
        return row==1 ? Operation.SUCCESSFULLY : Operation.FAILED;
    }

    /**
     * 通过审核id和社团审核类更新审核状态信息
     *
     * @param id        审核id
     * @param clubAudit 社团更新内容
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int updateClubAuditById(int id, ClubAudit clubAudit) {
        clubAudit.setId(id);
        int row = mapper.updateByPrimaryKeySelective(clubAudit);
        if(row > 1){throw new CRUDException("通过审核id和社团审核类更新审核状态信息异常,更新"+row +"行");}
        return row==1 ? Operation.SUCCESSFULLY : Operation.FAILED;
    }

    /**
     * 通过审核id和响应获取社团审核的文件
     *
     * @param id       审核id
     * @param response 响应
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    @Override
    public int getClubAuditFileById(int id, HttpServletResponse response) {
        ClubAudit clubAudit  = mapper.selectByPrimaryKey(id);
        if (clubAudit == null){return Operation.FAILED;}
        String fileName = clubAudit.getFile();
        //存放社团审核的路径地址
        String filePath = FIleDefaultPath.CLUB_AUDIT_FILE;
        try {
            FileUtil.downFile(filePath,fileName,response);
        }catch (Exception e){
            return Operation.FAILED;
        }
        return Operation.SUCCESSFULLY;
    }

    /**
     * 该功能也暂时不能实现
     * 通过审核id和响应在线预览社团审核文件
     *
     * @param id       审核id
     * @param response 响应
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    @Override
    public int viewClubAuditFileById(int id, HttpServletResponse response) {
        ClubAudit audit = mapper.selectByPrimaryKey(id);
        if(audit==null) {return Operation.FAILED;}
        String fileName = audit.getFile();
        String wordFilePath = FIleDefaultPath.CLUB_AUDIT_FILE;
        String htmlFilePath = FIleDefaultPath.CLUB_AUDIT_OVERVIEW_FILE;
        String htmlFileName = audit.getAuditTitle()+".html";
        WordFileUtil.convertToHTML(wordFilePath+fileName,htmlFileName+htmlFileName,htmlFileName+"image/","ann_ol/image/");
        try {
            InputStream in = new FileInputStream(new File(htmlFilePath,htmlFileName));
            OutputStream out = response.getOutputStream();
            byte[] byteBuffer = new byte[1024];
            int len;
            while ((len = in.read(byteBuffer)) != -1) {
                out.write(byteBuffer, 0, len);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("文件找不到");
        }

        File deleteFile = new File(htmlFileName+htmlFileName) ;
        boolean deleteState =  deleteFile.delete();
        if(!deleteState){
            throw new RuntimeException("文件删除失败");
        }
        return Operation.SUCCESSFULLY;
    }

    /**
     * 通过审核id和审核信息类对审核消息进行通过或拒绝
     *
     * @param id        审核id
     * @param resultMsg 审核结果信息
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public Integer passOrRejectClubAuditByIdAndResultMsg(int id, ClubAuditResultMsg resultMsg) {
        //如果这个审核已经是通过，删除，或者是拒绝的，则不能操作了
        ExampleWrapper<ClubAudit> example = new ExampleWrapper<>();
        example.eq("id",id).and().ne("audit_state",AuditState.AUDITING);
        if(mapper.countByExample(example) == 1) { return Operation.FAILED;}

        ClubAudit clubAudit = mapper.selectByPrimaryKey(id);
        int orgId = clubAudit.getOrgId();
        Org org = orgMapper.selectByPrimaryKey(orgId);
        int userId = org.getUserId();
        User user = userMapper.selectByPrimaryKey(userId);
        int state = resultMsg.getAuditState();
        if(state == AuditState.PASS){
            //如果状态为通过
            clubAudit.setAuditState(AuditState.PASS);
            org.setOrgState(AuditState.PASS);
            user.setUserState(AuditState.PASS);
        }else {
            //如果状态为拒绝
            clubAudit.setAuditState(AuditState.REJECT);
            org.setOrgState(AuditState.REJECT);
            user.setUserState(AuditState.REJECT);
        }
        int row = mapper.updateByPrimaryKey(clubAudit);
        row += orgMapper.updateByPrimaryKey(org);
        row += userMapper.updateByPrimaryKey(user);
        if(row!=3){throw new RuntimeException("操作失败，不能成功更新三行");}
        return Operation.SUCCESSFULLY;
    }
}

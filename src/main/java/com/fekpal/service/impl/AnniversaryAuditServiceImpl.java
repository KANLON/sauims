package com.fekpal.service.impl;

import com.fekpal.api.AnniversaryAuditService;
import com.fekpal.common.base.BaseServiceImpl;
import com.fekpal.common.base.CRUDException;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.common.constant.AuditState;
import com.fekpal.common.constant.FIleDefaultPath;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.constant.SystemRole;
import com.fekpal.common.session.SessionLocal;
import com.fekpal.common.utils.FileUtil;
import com.fekpal.common.utils.TimeUtil;
import com.fekpal.common.utils.WordFileUtil;
import com.fekpal.dao.mapper.AnniversaryAuditMapper;
import com.fekpal.dao.model.AnniversaryAudit;
import com.fekpal.web.model.ClubSubmitAnnMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * 年度审核服务的实现类，实现年度审核的各种事务
 *
 * @author zhangcanlong
 * @date 2018/4/6
 */
@Service
public class AnniversaryAuditServiceImpl extends BaseServiceImpl<AnniversaryAuditMapper, AnniversaryAudit> implements AnniversaryAuditService {

    @Autowired
    private HttpSession session;

    /**
     * 发送年度审核消息给校社联
     *
     * @param submitAudit 提交的年度审核消息内容
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    @Override
    public int sendAuditMessage(ClubSubmitAnnMsg submitAudit) {
        int orgId = SessionLocal.local(session).getUserIdentity().getUid();
        int row;
        AnniversaryAudit audit = new AnniversaryAudit();
        audit.setAuditState(AuditState.AUDITING);
        audit.setOrgId(orgId);
        audit.setAuditTitle(submitAudit.getAuditTitle());
        audit.setSubmitDescription(submitAudit.getDescription());
        audit.setSubmitTime(new Timestamp(TimeUtil.currentTime()));
        audit.setAuditResult("无");

        String fileName = TimeUtil.getYear() - 1 + "_" + Integer.toString(orgId);
        try {
            fileName = FileUtil.fileHandle(submitAudit.getAnnAuditFile(), FIleDefaultPath.CLUB_ANN_AUDIT_FILE, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return Operation.FAILED;
        }
        audit.setFileName(fileName);

        //如果存在，如果是该年的年度审核已经通过，则不能提交了。如果审核状态是拒绝或审核中了，则修改。否则不存在。则增加。
        ExampleWrapper<AnniversaryAudit> example = new ExampleWrapper<>();
        String auditYear = Integer.toString(TimeUtil.getYear() - 1);
        example.eq("org_id", orgId).and().like("audit_title", auditYear);
        int exitsState = mapper.countByExample(example);
        if (exitsState == 1) {
            //存在
            ExampleWrapper<AnniversaryAudit> examplePass = new ExampleWrapper<>();
            examplePass.eq("org_id", orgId).and().like("audit_title", auditYear).and().eq("audit_state", AuditState.PASS);
            int passState = mapper.countByExample(examplePass);
            if (passState == 1) {
                return Operation.FAILED;
            }
        } else {
            //不存在
            row = mapper.insert(audit);
            if (row != 1) {
                throw new CRUDException("插入年度审核时出错，插入了" + row + "行;");
            }
        }
        //如果执行到这里，则审核状态是拒绝或审核中，则修改
        ExampleWrapper<AnniversaryAudit> exampleMod = new ExampleWrapper<>();
        exampleMod.eq("org_id", orgId).and().like("audit_title", auditYear);
        row = mapper.updateByExampleSelective(audit, exampleMod);
        if (row != 1) {
            throw new CRUDException("更新年度审核时出错，插入了" + row + "行;");
        }
        return Operation.SUCCESSFULLY;
    }

    /**
     * 加载全部审核内容，除了删除的
     *
     * @param offset 开始数
     * @param limit  查询条数
     * @return 审核消息的类的list集合
     */
    @Override
    public List<AnniversaryAudit> loadAllAudit(int offset, int limit) {
        int auth = SessionLocal.local(session).getUserIdentity().getAuth();
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<AnniversaryAudit> example = new ExampleWrapper<>();
        //如果是校社联，查询全部
        if (auth == SystemRole.SAU) {
            example.ne("audit_state", AuditState.DELETE).orderBy("submit_time", false);
        } else {
            //否则是社团，查询该社团的审核信息
            example.eq("org_id", uid).orderBy("submit_time", false);
        }
        return mapper.selectByExample(example, offset, limit);
    }

    @Override
    public Integer countAllAudit() {
        int auth = SessionLocal.local(session).getUserIdentity().getAuth();
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<AnniversaryAudit> example = new ExampleWrapper<>();
        //如果是校社联，查询全部
        if (auth == SystemRole.SAU) {
            example.ne("audit_state", AuditState.DELETE).orderBy("submit_time", false);
        } else {
            //否则是社团，查询该社团的审核信息
            example.eq("org_id", uid).orderBy("submit_time", false);
        }
        return mapper.countByExample(example);
    }

    /**
     * 加载全部未审核的社团年度审核消息
     *
     * @param offset 开始数
     * @param limit  查询条数
     * @return 审核消息的类的list集合
     */
    @Override
    public List<AnniversaryAudit> loadAllUnAudit(int offset, int limit) {
        ExampleWrapper<AnniversaryAudit> example = new ExampleWrapper<>();
        example.ne("audit_state", AuditState.AUDITING).orderBy("submit_time", false);
        return mapper.selectByExample(example, offset, limit);
    }

    /**
     * 加载全部已经审核的年度通过审核消息
     *
     * @param offset 开始数
     * @param limit  查询条数
     * @return 审核消息的类的list集合
     */
    @Override
    public List<AnniversaryAudit> loadAllHaveAudit(int offset, int limit) {
        ExampleWrapper<AnniversaryAudit> example = new ExampleWrapper<>();
        example.eq("audit_state", AuditState.PASS).orderBy("submit_time", false);
        return mapper.selectByExample(example, offset, limit);
    }

    /**
     * 通过审核标题查找年度审核消息
     *
     * @param auditTitle 审核标题
     * @param offset     开始数
     * @param limit      查询条数
     * @return 审核消息的类的list集合
     */
    @Override
    public List<AnniversaryAudit> queryByAuditTitle(String auditTitle, int offset, int limit) {
        int auth = SessionLocal.local(session).getUserIdentity().getAuth();
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        //如果为空，则返回所有年度审核消息
        if (StringUtils.isEmpty(auditTitle)) {
            return loadAllAudit(offset, limit);
        }
        ExampleWrapper<AnniversaryAudit> example = new ExampleWrapper<>();
        //如果是校社联，查询全部
        if (auth == SystemRole.SAU) {
            example.ne("audit_state", AuditState.DELETE).and().like("audit_title", auditTitle).orderBy("submit_time", false);
        } else {
            //否则是社团，查询该社团的审核信息
            example.eq("org_id", uid).and().like("audit_title", auditTitle).orderBy("submit_time", false);
        }
        return mapper.selectByExample(example, offset, limit);
    }

    @Override
    public Integer countByAuditTitle(String auditTitle) {
        int auth = SessionLocal.local(session).getUserIdentity().getAuth();
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        if (StringUtils.isEmpty(auditTitle)) {
            return countAllAudit();
        }
        ExampleWrapper<AnniversaryAudit> example = new ExampleWrapper<>();
        //如果是校社联，查询全部
        if (auth == SystemRole.SAU) {
            example.ne("audit_state", AuditState.DELETE).and().like("audit_title", auditTitle).orderBy("submit_time", false);
        } else {
            //否则是社团，查询该社团的审核信息
            example.eq("org_id", uid).and().like("audit_title", auditTitle).orderBy("submit_time", false);
        }
        return mapper.countByExample(example);
    }

    /**
     * 通过社团id查询某个社团的全部审核消息
     *
     * @param orgId  社团id
     * @param offset 开始数
     * @param limit  查询条数
     * @return 审核消息的类的list集合
     */
    @Override
    public List<AnniversaryAudit> selectByOrgId(int orgId, int offset, int limit) {
        ExampleWrapper<AnniversaryAudit> example = new ExampleWrapper<>();
        example.eq("org_id", orgId).orderBy("submit_time", false);
        return mapper.selectByExample(example, offset, limit);
    }

    /**
     * 通过年度审核id，查询某个年度审核
     *
     * @param auditId 年度审核id
     * @return 年度审核类
     */
    @Override
    public AnniversaryAudit selectByAuditId(int auditId) {
        int auth = SessionLocal.local(session).getUserIdentity().getAuth();
        int orgId = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<AnniversaryAudit> example = new ExampleWrapper<>();
        //如果是校社联用户
        if (auth == SystemRole.SAU) {
            example.ne("audit_state", AuditState.DELETE).and().eq("id", auditId);
        } else {
            //如果是社团用户
            example.eq("id", auditId).and().eq("org_id", orgId);
        }
        return mapper.selectFirstByExample(example);
    }

    /**
     * 未审核的年度审核数量
     *
     * @return 未审核年度审核消息数
     */
    @Override
    public int countUnAuditNum() {
        ExampleWrapper<AnniversaryAudit> example = new ExampleWrapper<>();
        example.eq("audit_state", AuditState.AUDITING);
        return mapper.countByExample(example);
    }

    /**
     * 通过审核消息id删除某条审核消息
     *
     * @param auditId 审核消息id
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int deleteById(int auditId) {
        AnniversaryAudit anniversaryAudit = new AnniversaryAudit();
        anniversaryAudit.setId(auditId);
        anniversaryAudit.setAuditState(AuditState.DELETE);
        int row = mapper.updateByPrimaryKeySelective(anniversaryAudit);
        if(row > 1){throw new CRUDException("删除审核消息异常,删除"+row +"行");}
        return row == 1 ? Operation.SUCCESSFULLY : Operation.FAILED;
    }

    /**
     * 得到年度审核的附件
     *
     * @param auditId  年度审核id
     * @param response 响应
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    @Override
    public int getAuditFileById(int auditId, HttpServletResponse response) {
        int auth = SessionLocal.local(session).getUserIdentity().getAuth();
        int orgId = SessionLocal.local(session).getUserIdentity().getUid();
        AnniversaryAudit anniversaryAudit = null;
        if (auth == SystemRole.CLUB) {
            //如果用户是社团，则只能下载该社团的年度审核信息
            ExampleWrapper<AnniversaryAudit> example = new ExampleWrapper<>();
            example.eq("id", auditId).and().eq("org_id", orgId);
            anniversaryAudit = mapper.selectFirstByExample(example);
        } else if (auth == SystemRole.SAU) {
            //如果用户是校社联，则可以下载全部年度审核
            anniversaryAudit = mapper.selectByPrimaryKey(auditId);
        }
        if (anniversaryAudit == null) {
            return Operation.FAILED;
        }
        String fileName = anniversaryAudit.getFileName();
        //存放年度审核的路径地址
        String filePath = FIleDefaultPath.CLUB_ANN_AUDIT_FILE;
        try {
            FileUtil.downFile(filePath, fileName, response);
        } catch (Exception e) {
            return Operation.FAILED;
        }
        return Operation.SUCCESSFULLY;
    }

    /**
     * 在线预览年度审核文件
     *
     * @param auditId  审核id
     * @param response 响应 返回html文件流
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    @Override
    public int viewAuditFileById(int auditId, HttpServletResponse response) {
        AnniversaryAudit audit = mapper.selectByPrimaryKey(auditId);
        if (audit == null) {
            return Operation.FAILED;
        }
        String fileName = audit.getFileName();
        String wordFilePath = FIleDefaultPath.CLUB_ANN_AUDIT_OVERVIEW_FILE;
        String htmlFilePath = FIleDefaultPath.CLUB_ANN_AUDIT_OVERVIEW_FILE;
        String htmlFileName = audit.getAuditTitle() + ".html";
        WordFileUtil.convertToHTML(wordFilePath + fileName, htmlFileName + htmlFileName, htmlFileName + "image/", "ann_ol/image/");
        try {
            InputStream in = new FileInputStream(new File(htmlFilePath, htmlFileName));
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
        File deleteFile = new File(htmlFileName + htmlFileName);
        Boolean deleteState = deleteFile.delete();
        if (!deleteState) {
            throw new RuntimeException("预览失败");
        }
        return Operation.SUCCESSFULLY;
    }

    /**
     * 通过年度审核类来更新年度审核信息
     *
     * @param anniversaryAudit 年度审核类
     * @return Operation.SUCCESSFULLY; 删除成功 Operation.FAILED 删除失败
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int updateAuditStateByAnnModel(AnniversaryAudit anniversaryAudit) {
        ExampleWrapper<AnniversaryAudit> example = new ExampleWrapper<>();
        example.eq("audit_state", AuditState.PASS).or().eq("audit_state", AuditState.REJECT).and().eq("id", anniversaryAudit.getId());
        int auditNum = mapper.countByExample(example);
        //如果之前的年度注册信息已经是通过或者是拒绝了的，则不能再次审核
        if (auditNum >= 1) {
            return Operation.FAILED;
        }
        int row = mapper.updateByPrimaryKeySelective(anniversaryAudit);
        if(row > 1){throw new CRUDException("更新审核消息异常,更新"+row +"行");}
        return row == 1 ? Operation.SUCCESSFULLY : Operation.FAILED;
    }
}

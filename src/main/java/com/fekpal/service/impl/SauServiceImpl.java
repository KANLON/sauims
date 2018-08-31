package com.fekpal.service.impl;

import com.fekpal.api.SauService;
import com.fekpal.common.base.BaseServiceImpl;
import com.fekpal.common.base.CRUDException;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.common.constant.DefaultField;
import com.fekpal.common.constant.FIleDefaultPath;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.session.SessionLocal;
import com.fekpal.common.utils.ImageFileUtil;
import com.fekpal.dao.mapper.MemberMapper;
import com.fekpal.dao.mapper.OrgMapper;
import com.fekpal.dao.model.Org;
import com.fekpal.service.model.domain.SauMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

/**
 * SauService实现类
 * @author zhangcanlong
 * @author 2018/8/20
 */
@Service
public class SauServiceImpl extends BaseServiceImpl<OrgMapper, Org> implements SauService {

    @Autowired
    private HttpSession session;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Org selectByPrimaryId() {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        return mapper.selectByPrimaryKey(uid);
    }

    @Override
    public boolean isExitSauName(String name) {
        ExampleWrapper<Org> example = new ExampleWrapper<>();
        example.eq("org_name", name);
        int row = mapper.countByExample(example);
        return row >= 1;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int updateSauInfo(SauMsg msg) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<Org> example = new ExampleWrapper<>();
        example.eq("org_name", msg.getSauName()).and().ne("org_id",uid);
        int row = mapper.countByExample(example);
        if (row != 0) return Operation.INPUT_INCORRECT;


        Org org = new Org();
        org.setOrgId(uid);
        org.setOrgName(msg.getSauName());
        org.setDescription(msg.getDescription());
        org.setAdminName(msg.getAdminName());
        org.setFoundTime(msg.getFoundTime());
        row = mapper.updateByPrimaryKeySelective(org);

        if (row > 1) throw new CRUDException("更新校社联信息失败：" + row);
        return row == 0 ? Operation.FAILED : Operation.SUCCESSFULLY;
    }

    @Override
    public String updateLogo(SauMsg msg) {
        if(msg.getLogo() == null){ return null;}
        try {
            int uid = SessionLocal.local(session).getUserIdentity().getUid();
            Org org = mapper.selectByPrimaryKey(uid);
            //存入数据库的是带后缀的，进行存储的时候是不能带后缀的，要以上传文件的后缀为后缀
            String[] orgLogos = org.getOrgLogo().split("\\.");
            String logo = "";
            if( org.getOrgLogo().equalsIgnoreCase(DefaultField.DEFAULT_LOGO)) {
                logo = ImageFileUtil.handle(msg.getLogo(), FIleDefaultPath.PERSON_LOGO_FILE);
            }else{
                logo = ImageFileUtil.handle(msg.getLogo(), FIleDefaultPath.PERSON_LOGO_FILE,orgLogos[0]);
            }
            org.setOrgLogo(logo);
            mapper.updateByPrimaryKey(org);
            return logo;
        } catch (Exception e) {
            throw new CRUDException(e.getMessage());
        }
    }

    @Override
    public String updateView(SauMsg msg) {
        if(msg.getView() == null){ return null;}
        try {
            int uid = SessionLocal.local(session).getUserIdentity().getUid();
            Org org = mapper.selectByPrimaryKey(uid);
            //存入数据库的是带后缀的，进行存储的时候是不能带后缀的，要以上传文件的后缀为后缀
            String[] orgViews = org.getOrgView().split("\\.");
            String view = "";
            if( org.getOrgView().equalsIgnoreCase(DefaultField.DEFAULT_CLUB_OVERVIEW)) {
                view = ImageFileUtil.handle(msg.getLogo(), FIleDefaultPath.SAU_VIEW_FILE);
            }else{
                view = ImageFileUtil.handle(msg.getLogo(), FIleDefaultPath.SAU_VIEW_FILE,orgViews[0]);
            }
            org.setOrgView(view);
            mapper.updateByPrimaryKey(org);
            return view;
        } catch (Exception e) {
            throw new CRUDException(e.getMessage());
        }
    }

}



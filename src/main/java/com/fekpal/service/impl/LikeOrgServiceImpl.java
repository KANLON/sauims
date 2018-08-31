package com.fekpal.service.impl;

import com.fekpal.api.LikeOrgService;
import com.fekpal.api.OrgService;
import com.fekpal.common.base.BaseServiceImpl;
import com.fekpal.common.base.CRUDException;
import com.fekpal.common.base.ExampleWrapper;
import com.fekpal.common.constant.AvailableState;
import com.fekpal.common.constant.Operation;
import com.fekpal.common.session.SessionLocal;
import com.fekpal.dao.mapper.LikeOrgMapper;
import com.fekpal.dao.model.LikeOrg;
import com.fekpal.dao.model.Org;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

/**
 * @author zhangcanlong
 * 喜欢社团的实现类
 */
@Service
public class LikeOrgServiceImpl extends BaseServiceImpl<LikeOrgMapper, LikeOrg> implements LikeOrgService {

    @Autowired
    private LikeOrgMapper likeOrgMapper;

    @Autowired
    private HttpSession session;

    @Autowired
    private OrgService orgService;

    Logger logger = Logger.getLogger(LikeOrgServiceImpl.class);

    /**
     * 根据根据社团id和用户自己的查看该社团的点赞状态
     *
     * @param orgId 社团id
     * @return 喜爱社团记录
     */
    @Override
    public LikeOrg selectByOrgId(int orgId) {
        logger.info("session为："+session);
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<LikeOrg> example = new ExampleWrapper<>();
        example.eq("person_id",uid).and().eq("like_org.org_id",orgId);
        int row = mapper.countByExample(example);
        LikeOrg likeOrg = new LikeOrg();
        //如果数据库内不存在用户该记录,返回没点赞，并向数据库插入用户与社团在点赞表之间的记录
        if(row == 0 ) {
            LikeOrg insertLikeOrg = new LikeOrg();
            insertLikeOrg.setPersonId(uid);
            insertLikeOrg.setOrgId(orgId);
            insertLikeOrg.setAvailable(0);
            mapper.insert(insertLikeOrg);

            likeOrg.setAvailable(AvailableState.UNAVAILABLE);
            return  likeOrg;
        }
        //如果数据库存在该记录，查询喜欢状态       Available
        ExampleWrapper<LikeOrg> exampleLikeState = new ExampleWrapper<>();
        exampleLikeState.eq("person_id",uid).and().eq("available",0).and().eq("like_org.org_id",orgId);
        int rowLikeState = mapper.countByExample(exampleLikeState);
        //如果查询到了，证明还没点赞
        if(rowLikeState != 0){
            likeOrg.setAvailable(AvailableState.UNAVAILABLE);
            return  likeOrg;
        }
        //否则已经点赞
        likeOrg.setAvailable(AvailableState.AVAILABLE);

        return likeOrg;
    }

    /**
     * 取消该用户对某社团的点赞
     *
     * @param orgId 社团id
     * @return 取消状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int cancelLikeById(int orgId) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<LikeOrg> example = new ExampleWrapper<>();
        example.eq("like_org.org_id",orgId).and().eq("person_id",uid);
        LikeOrg likeOrg = new LikeOrg();
        likeOrg.setAvailable(0);
        int row = mapper.updateByExampleSelective(likeOrg,example);
        //社团信息表的点赞数减1
        Org org= orgService.selectByPrimaryKey(orgId);
        org.setLikeClick(org.getLikeClick()-1);
        orgService.updateByPrimaryKey(org);

        if (row > 1) throw new CRUDException("取消点赞出错：" + row);
        return row == 0 ? Operation.FAILED : Operation.SUCCESSFULLY;
    }

    /**
     * 该用户对某社团的点赞
     *
     * @param orgId 社团id
     * @return 点赞 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int likeById(int orgId) {
        int uid = SessionLocal.local(session).getUserIdentity().getUid();
        ExampleWrapper<LikeOrg> example = new ExampleWrapper<>();
        example.eq("person_id",uid).and().eq("like_org.org_id",orgId);
        LikeOrg likeOrg = new LikeOrg();
        likeOrg.setAvailable(AvailableState.AVAILABLE);
        int row = mapper.updateByExampleSelective(likeOrg,example);
        //社团信息表的点赞数加1
        Org org= orgService.selectByPrimaryKey(orgId);
        org.setLikeClick(org.getLikeClick()+1);
        orgService.updateByPrimaryKey(org);

        if (row > 1) { throw new CRUDException("点赞出错：" + row);}
        return row == 0 ? Operation.FAILED : Operation.SUCCESSFULLY;
    }

    /**
     * 根据社团id和他自身的点赞状态，来决定该用户对某社团是点赞还是取消点赞
     *
     * @param orgId     社团id  ambiguous
     * @return 点赞 Operation.SUCCESSFULLY 点赞成功 Operation.FAILED 失败  Operation.CANCEL 取消点赞成功
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {Exception.class})
    public int likeByOrgIdAndState(int orgId) {
        LikeOrg likeOrg = this.selectByOrgId(orgId);
        int available = likeOrg.getAvailable();

        if(available == 0){
            this.likeById(orgId);
            return Operation.SUCCESSFULLY;
        }
        if(available == 1){
            this.cancelLikeById(orgId);
            return Operation.CANCEL;
        }
        return Operation.FAILED;
    }
}

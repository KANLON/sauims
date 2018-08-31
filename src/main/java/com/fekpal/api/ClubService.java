package com.fekpal.api;

import com.fekpal.common.base.BaseService;
import com.fekpal.dao.model.Org;
import com.fekpal.dao.model.Person;
import com.fekpal.dao.model.PersonOrgView;
import com.fekpal.service.model.domain.ClubMsg;

import java.util.List;

/**
 * Created by APone on 2017/9/5.
 * 社团用户信息接口
 * 该接口主要提功社团用户信息增删查操作
 */
public interface ClubService extends BaseService<Org> {

    /**
     * 根据社团用户标识获得个人
     *
     * @return 社团用户记录
     */
    Org selectByPrimaryId();

    /**
     * 根据社团用户标识更新社团头像
     *
     * @param msg 社团修改信息封装
     *            传入参数：头像文件logo
     * @return 头像名
     */
    String updateLogo(ClubMsg msg);

    /**
     * 根据社团用户标识更新社团预览图
     *
     * @param msg 社团修改信息封装
     *            传入参数：头像文件logo
     * @return 头像名
     */
    String updateView(ClubMsg msg);

    /**
     * 更新社团用户信息
     *
     * @param msg 社团修改信息封装
     *            传入参数：
     * @return 更新状态 Operation.SUCCESSFULLY 成功 Operation.FAILED 失败 Operation.INPUT_INCORRECT存在相同的社团名称
     */
    int updateClubInfo(ClubMsg msg);

    /**
     * 根据社团名称获得社团信息记录
     *
     * @param name 社团名称
     * @return 社团信息记录
     */
    Org selectByClubName(String name);

    /**
     * 根据社团名称进行模糊搜索社团信息记录，分页读取
     *
     * @param name   社团名称
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 社团信息记录集
     */
    List<Org> queryByClubName(String name, int offset, int limit);

    /**
     * 根据社团名称进行模糊搜索统计社团信息记录
     *
     * @param name   社团名称
     * @return 社团信息记录数
     */
    Integer countByClubName(String name);
    /**
     * 是否有相同的社团名称的社团
     *
     * @param name 社团名称
     * @return 是否存在
     */
    boolean isExitClubName(String name);

    /**
     * 获得所有的社团信息记录,分页读取
     *
     * @param offset 跳过读数
     * @param limit  读取数
     * @return 社团信息记录集
     */
    List<Org> loadAllClub(int offset, int limit);

    /**
     * 统计所有的社团信息记录数
     * @return 社团信息记录数
     */
    Integer countAllClub();

    /**
     * 只用于社团用户和普通用户用，返回不括校社联的信息
     * 根据社团id返回社团详细信息
     * @param orgId 社团id
     * @return 组织信息和加入状态
     */
    PersonOrgView selectByIdForPerson(int orgId);

    /**
     * 得到本社团所有人的个人信息，仅仅社团用户用,分页获取
     * @param offset 跳过数
     * @param limit 获取条数
     * @return 本社团社员个人信息记录集
     */
    List<Person> getClubAllMemberPersonMsg(Integer offset,Integer limit);

    /**
     * 统计本社团所有人的个人信息记录数，仅仅社团用户用,
     * @return 本社团所有社员个人信息记录数
     */
    Integer countClubAllMemberPersonMsg();

    /**
     * 根据社团id计算社团内部男生的数量
     * @return 社团内男生的人数
     */
    int countClubManNum();

    /**
     * 计算社团内部女生的数量
     * @return 社团内女生的人数
     */
    int countClubWomanNum();


    /**
     * 根据年级数计算社团内部年级的数量
     *
     * @param grade 年级 如1,2,3,4,
     * @return 社团内各个年级的人数
     */
    int countClubGradeNum(int grade);

}

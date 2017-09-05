package com.fekpal.dao;

import com.fekpal.domain.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by APone on 2017/8/17.
 * RoleDao层接口,暂时不存在权限和资源的接口
 * 现在只获取结果集，还未有增删改功能
 */
@Repository
public interface RoleDao {

    /**
     * 加载所有权限的资源
     *
     * @return List
     */
    List<Role> loadAll();
}

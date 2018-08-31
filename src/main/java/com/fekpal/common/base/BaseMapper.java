package com.fekpal.common.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * dao基础增删查改操作，所有dao的必须继承,按需求实现方法
 *
 * @param <Record>
 */
public interface BaseMapper<Record> {

    /**
     * 根据主键获取记录
     *
     * @param id 主键
     * @return 记录集
     */
    Record selectByPrimaryKey(Integer id);


    /**
     * 根据条件获取第一条记录
     *
     * @param example 条件
     * @return 第一条记录
     */
    Record selectFirstByExample(@Param(ExampleWrapper.PARAM_NAME) ExampleWrapper<Record> example);

    /**
     * 根据条件获取记录
     *
     * @param example 条件
     * @param offset  跳过数量
     * @param limit   查询数量
     * @return 记录集
     */
    List<Record> selectByExample(@Param(ExampleWrapper.PARAM_NAME) ExampleWrapper<Record> example, @Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 根据条件统计数量
     *
     * @param example 条件例子
     * @return 记录集数量
     */
    int countByExample(@Param(ExampleWrapper.PARAM_NAME) ExampleWrapper<Record> example);

    /**
     * 根据主键更新记录
     *
     * @param record 记录
     * @return 更新数量
     */
    int updateByPrimaryKey(Record record);

    /**
     * 根据主键更新有效字段记录
     *
     * @param record 记录
     * @return 更新数量
     */
    int updateByPrimaryKeySelective(Record record);


    /**
     * 根据条件更新记录
     *
     * @param record  记录
     * @param example 条件
     * @return 更新数量
     */
    int updateByExample(@Param("obj") Record record, @Param(ExampleWrapper.PARAM_NAME) ExampleWrapper<Record> example);

    /**
     * 根据条件更新有效字段记录
     *
     * @param record  记录
     * @param example 条件
     * @return 更新数量
     */
    int updateByExampleSelective(@Param("obj") Record record, @Param(ExampleWrapper.PARAM_NAME) ExampleWrapper<Record> example);

    /**
     * 根据主键删除记录
     *
     * @param id 主键
     * @return 更新数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 根据条件删除
     *
     * @param example 条件
     * @return 更新数量
     */
    int deleteByExample(@Param(ExampleWrapper.PARAM_NAME) ExampleWrapper<Record> example);

    /**
     * 添加记录
     *
     * @param record 实体类
     * @return 添加数量
     */
    int insert(Record record);

    /**
     * 批量添加记录
     *
     * @param records 记录集
     * @return 添加数量
     */
    int insertLoop(List<Record> records);
}

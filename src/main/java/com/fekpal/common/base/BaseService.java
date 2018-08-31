package com.fekpal.common.base;


import java.util.List;

/**
 * Created by APone on 2018/1/31 15:40.
 * 基础服务接口,定义了各个服务的通用操作
 */
public interface BaseService<Record> {

    /**
     * 通用获取，获取范围为本表
     *
     * @param id 主键id
     * @return 记录集
     */
    Record selectByPrimaryKey(Integer id);

    /**
     * 根据条件获取第一条记录
     *
     * @param example 条件
     * @return 第一条记录
     */
    Record selectFirstByExample(ExampleWrapper<Record> example);

    /**
     * 根据条件获取记录
     *
     * @param example 条件
     * @param offset  跳过数量
     * @param limit   查询数量
     * @return 记录集
     */
    List<Record> selectByExample(ExampleWrapper<Record> example, Integer offset, Integer limit);

    /**
     * 根据条件统计数量
     *
     * @param example 条件例子
     * @return 记录集数量
     */
    int countByExample(ExampleWrapper<Record> example);


    /**
     * 通用根据主键id更新记录
     *
     * @param record 记录
     * @return 更新数量
     */
    int updateByPrimaryKey(Record record);

    /**
     * 通用根据主键id更新记录有效字段
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
    int updateByExample(Record record, ExampleWrapper<Record> example);

    /**
     * 根据条件更新有效字段记录
     *
     * @param record  记录
     * @param example 条件
     * @return 更新数量
     */
    int updateByExampleSelective(Record record, ExampleWrapper<Record> example);

    /**
     * 通用根据主键id删除记录
     *
     * @param id 主键id
     * @return 更新数量
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 根据条件删除
     *
     * @param example 条件
     * @return 更新数量
     */
    int deleteByExample(ExampleWrapper<Record> example);

    /**
     * 通用插入记录，并记录获得生成的主键id
     *
     * @param record 记录
     * @return 插入数量
     */
    int insert(Record record);

    /**
     * 通用批量插入记录，并所有记录获得生成的主键id
     *
     * @param records 记录集
     * @return 插入数量
     */
    int insertLoop(List<Record> records);
}
